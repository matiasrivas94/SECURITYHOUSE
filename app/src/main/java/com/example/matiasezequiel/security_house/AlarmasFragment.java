package com.example.matiasezequiel.security_house;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class AlarmasFragment extends Fragment {
    //cosas del ListView Alarmas
    private ListView lista;
    TextView tvTitulo;
    long cant=0;

    //nuevo
    ArrayList<Alarma> alarmas;

    // cosas de la alarma al hacer click ena alarma de la lista
    private int usuarioSelecionado = -1;
    private Object mActionMode;
    int idAlarm=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_alarmas, container, false);

        lista = (ListView) v.findViewById(R.id.LVMostrar);
        tvTitulo = (TextView)v.findViewById(R.id.tvTitulo);

        llenarLista();

        //nuevo
        onClick();

        //Shared de la cantidad de alarmar que va al TabZonaFragment
        //SharedPreferences.Editor editor = getContext().getSharedPreferences("aa",Context.MODE_PRIVATE).edit();
        //editor.putLong("cant", cant);
        //editor.commit();

        return v;
    }

    public void llenarLista(){
        //conexion a la base de datos
        AlarmaSQLite bd = new AlarmaSQLite(this.getActivity(),"alarma",null,1);
        //nuevo
        alarmas = new ArrayList<>();
        if(bd!=null){
            SQLiteDatabase db = bd.getReadableDatabase();
            //selecciono todas las alarmas almacenadas
            Cursor c = db.rawQuery("SELECT * FROM alarma",null);
            if(c.moveToFirst()){
                tvTitulo.setVisibility(View.INVISIBLE);
                do{
                    //alarmas.add(new Alarma(c.getInt(0),c.getString(1),c.getString(2),c.getInt(3)));
                    alarmas.add(new Alarma(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5)));
                }while(c.moveToNext());
            }
        }
        String[] arreglo = new String[alarmas.size()];
        for (int i = 0;i<arreglo.length;i++){
            //arreglo[i] = alarmas.get(i).getIdAlarma()+" -- "+alarmas.get(i).getNombre()+" -- "+alarmas.get(i).getClave();
            arreglo[i] = alarmas.get(i).getNombre();
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this.getActivity() ,android.R.layout.simple_list_item_1,arreglo);
        lista.setAdapter(adaptador);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            onResume();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (!getUserVisibleHint()) {
            mainActivity.showFloatingActionButton(); //fuerza la visibilidad
        }
       // MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.showFloatingActionButton(); //fuerza la visibilidad
            FloatingActionButton fab = mainActivity.findViewById(R.id.fab);
            //fab.setImageResource(R.drawable.ic_send_black_24dp); //Cambiar icono
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contenedor, new CrearAlarmaFragment()).addToBackStack(null);
                    fr.commit();
                }
            });
        }
    }

    //nuevo
    public void onClick(){
        lista.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
         //Haciendo un solo click sobre un item de la lista
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                usuarioSelecionado = position;

                //Shared para saber el idAlarma al hacer click sobre el listView
                Alarma alar = alarmas.get(usuarioSelecionado);
                idAlarm = alar.getIdAlarma();
                String nomA = alar.getNombre();
                //Toast.makeText(getActivity(),"ID de la Alarma Seleccionada: " + idAlarm, Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = getContext().getSharedPreferences("cc",Context.MODE_PRIVATE).edit();
                editor.putLong("idAlarma", idAlarm);
                editor.commit();

                SharedPreferences.Editor editor1 = getContext().getSharedPreferences("ee",Context.MODE_PRIVATE).edit();
                editor1.putString("nombreAlarma", nomA);
                editor1.commit();

                // COSAS DEL FRAGMENT
                FragmentTransaction fr = getFragmentManager().beginTransaction().addToBackStack(null);
                fr.replace(R.id.contenedor, new PrincipalFragment());
                fr.commit();
            }
        });
    }

}
