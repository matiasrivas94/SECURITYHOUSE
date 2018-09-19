package com.example.matiasezequiel.security_house;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class AlarmasFragment extends Fragment {
    //cosas del ListView Alarmas
    private ListView lista;
    TextView tvTitulo;

    ListView list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_alarmas, container, false);

        lista = (ListView) v.findViewById(R.id.LVMostrar);
        tvTitulo = (TextView)v.findViewById(R.id.tvTitulo);

        llenarLista();

        return v;
    }

    public void llenarLista(){
        //conexion a la base de datos
        AlarmaSQLite bh = new AlarmaSQLite(this.getActivity(),"alarma",null,1);
        ArrayList<Alarma> alarmas = new ArrayList<>();
        if(bh!=null){
            Log.d("","Paso por el if");
            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM alarma",null);
            if(c.moveToFirst()){
                tvTitulo.setVisibility(View.INVISIBLE);
                do{
                    //alarmas.add(new Alarma(c.getInt(0),c.getString(1),c.getString(2),c.getInt(3)));
                    alarmas.add(new Alarma(c.getInt(0),c.getString(1),c.getString(2),c.getString(3)));
                }while(c.moveToNext());
            }

        }
        String[] arreglo = new String[alarmas.size()];
        for (int i = 0;i<arreglo.length;i++){
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
        if (!getUserVisibleHint()) {
            return;
        }
        MainActivity mainActivity = (MainActivity) getActivity();
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


}
