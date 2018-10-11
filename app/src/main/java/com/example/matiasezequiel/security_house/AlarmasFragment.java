package com.example.matiasezequiel.security_house;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.text.Html;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class AlarmasFragment extends Fragment {
    //cosas del ListView Alarmas
    private ListView lista;
    TextView tvTitulo;
    long cant=0;

    //nuevo
    ArrayList<Alarma> alarmas;
    ArrayList<DatosItemAlarma> datosAlarma;

    // cosas de la alarma al hacer click ena alarma de la lista
    private int usuarioSelecionado = -1;
    private Object mActionMode;
    int idAlarm=0,deleteAlarma,deleteAlarm=0,deleteAlarmLista;

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
        datosAlarma = new ArrayList<>();
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
        for(int x = 0; x < alarmas.size(); x++){
            datosAlarma.add(new DatosItemAlarma(x , alarmas.get(x).getNombre()+" -- "+alarmas.get(x).getTipo()," "));
        }
        AdaptadorAlarma adapter = new AdaptadorAlarma(this.getActivity(), datosAlarma);
        lista.setAdapter(adapter);
    }


    //clase interna para manejar el item de la lista de las alarmas
    private class AdaptadorAlarma extends BaseAdapter implements View.OnCreateContextMenuListener {
        Context contexto;
        List<DatosItemAlarma> listaObjetos;

        public AdaptadorAlarma(Context contexto, List<DatosItemAlarma> listaObjetos) {
            this.contexto = contexto;
            this.listaObjetos = listaObjetos;
        }
        @Override
        public int getCount() {
            return listaObjetos.size(); //retorna cantidad de la lista
        }
        @Override
        public Object getItem(int position) {
            return listaObjetos.get(position); //retorna el objeto de la posicion indicada
        }
        @Override
        public long getItemId(int position) {
            return listaObjetos.get(position).getId();
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View vista = convertView;

            LayoutInflater inflate = LayoutInflater.from(contexto);
            vista = inflate.inflate(R.layout.items_listview, null);

            Button nombreAlarma = (Button) vista.findViewById(R.id.btNombreAlarma);
            Button iconoMenu = (Button) vista.findViewById(R.id.btIconoMenu);

            nombreAlarma.setText(listaObjetos.get(position).getNombre());
            iconoMenu.setText(listaObjetos.get(position).getIcono());


            registerForContextMenu(iconoMenu);

            //Boton del menu del item de la lista de las alarmas
            iconoMenu.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    usuarioSelecionado = position;
                    //Shared para saber el idAlarma al hacer click sobre el listView
                    Alarma alar = alarmas.get(usuarioSelecionado);
                    idAlarm = alar.getIdAlarma();

                    //Shared para quedarme con la posicion de la lista al seleccionar una alarma
                    SharedPreferences.Editor editor1 = getContext().getSharedPreferences("deleteAlarmList",Context.MODE_PRIVATE).edit();
                    editor1.putLong("deleteAlarLista", usuarioSelecionado);
                    editor1.commit();


                    //Shared para eliminar una alarma con el menu contextual
                    SharedPreferences.Editor editor = getContext().getSharedPreferences("eliminarAlarma",Context.MODE_PRIVATE).edit();
                    editor.putLong("elimAlarm", idAlarm);
                    editor.commit();


                    // Menu con alert builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                    //builder.setTitle("Elegir Opcion");
                    builder.setTitle(Html.fromHtml("<font color='#000000'>Elegir Opción</font>"));
                    // add a list
                    String[] animals = {"Modificar Alarma", "Eliminar Alarma", "Configuracion General"};
                    builder.setItems(animals, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    //Shared para editar la alarma
                                    SharedPreferences.Editor editor2 = getContext().getSharedPreferences("cadenaEditar",Context.MODE_PRIVATE).edit();
                                    editor2.putString ("editarString","update");
                                    editor2.commit();

                                    FragmentTransaction fr1 = getFragmentManager().beginTransaction();
                                    fr1.replace(R.id.contenedor, new CrearAlarmaFragment(),"CrearAlarma").addToBackStack(null);
                                    fr1.commit();
                                    break;
                                case 1:
                                    //Abrir un alertDialog preguntando si desea cancelar
                                    //Log.d("prueba2","comprobar true");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                                    builder.setMessage("Confirmar! \n");
                                    builder.setTitle(Html.fromHtml("<font color='#000000'>Eliminar la Alarma</font>"));

                                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //Shared para quedarme con el idAlarma seleccionada para eliminar
                                            SharedPreferences prefs1 = getContext().getSharedPreferences("deleteAlarmList", Context.MODE_PRIVATE);
                                            deleteAlarmLista = (int) prefs1.getLong("deleteAlarLista", -1);

                                            //Shared para quedarme con la posicion de la lista de la alarma seleccionada para aliminar
                                            SharedPreferences prefs = getContext().getSharedPreferences("eliminarAlarma", Context.MODE_PRIVATE);
                                            deleteAlarm = (int) prefs.getLong("elimAlarm", -1);

                                            //int x = deleteAlarm;
                                            //Toast.makeText(getActivity(),"Posiscion de la alarma seleccionada en la lista: " + deleteAlarmLista,Toast.LENGTH_LONG).show();
                                            AlarmaSQLite bd = new AlarmaSQLite(getActivity(), "alarma", null, 1);
                                            if (bd != null) {
                                                SQLiteDatabase db = bd.getReadableDatabase();
                                                Alarma alarm = alarmas.get(deleteAlarmLista);
                                                long response = db.delete("alarma", "idAlarma=" + alarm.getIdAlarma(), null);
                                                if (response > 0) {
                                                    Toast.makeText(getActivity(), "Eliminado con exito", Toast.LENGTH_LONG).show();
                                                    alarmas.removeAll(alarmas);
                                                    llenarLista();
                                                } else {
                                                    Toast.makeText(getActivity(), "Fallo", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }});
                                    builder.create();
                                    builder.show();
                                    break;
                                case 2:
                                    //shared para mostrar el tipo de la alarma en el spinner de configuracion
                                    SharedPreferences.Editor editor = getContext().getSharedPreferences("tipoAlarmaSpinner",Context.MODE_PRIVATE).edit();
                                    editor.putLong("configSpinner", deleteAlarma);
                                    editor.commit();

                                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                                    fr.replace(R.id.contenedor, new ConfigFragment(),"Configuacion").addToBackStack(null);
                                    fr.commit();
                            }
                        }
                    });
                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            //Boton del menu del item de las alarmas que maneja el nombre para llevar al
            //fragmentTazZonas para ver las zonas que tiene cada alarma de la lista
            nombreAlarma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(contexto,"Posicion: " + position,Toast.LENGTH_SHORT).show();
                    usuarioSelecionado = position;
                    //Shared para saber el idAlarma al hacer click sobre el listView
                    Alarma alar = alarmas.get(usuarioSelecionado);
                    idAlarm = alar.getIdAlarma();
                    String nomA = alar.getNombre();

                    //Shareds para el fragment principal
                    SharedPreferences.Editor editor = getContext().getSharedPreferences("cc",Context.MODE_PRIVATE).edit();
                    editor.putLong("idAlarma", idAlarm);
                    editor.commit();
                    SharedPreferences.Editor editor1 = getContext().getSharedPreferences("ee",Context.MODE_PRIVATE).edit();
                    editor1.putString("nombreAlarma", nomA);
                    editor1.commit();

                    // COSAS DEL FRAGMENT
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contenedor, new PrincipalFragment(),"Principal").addToBackStack(null);
                    fr.commit();
                }
            });

            return vista;
        }

        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            // empty implementation
        }
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
            fab.setImageResource(R.drawable.add3); //Cambiar icono
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Shared para editar la alarma
                    SharedPreferences.Editor editor2 = getContext().getSharedPreferences("cadenaEditar",Context.MODE_PRIVATE).edit();
                    editor2.putString ("editarString","insert");
                    editor2.commit();

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contenedor, new CrearAlarmaFragment(),"CrearAlarma").addToBackStack(null);
                    fr.commit();
                }
            });
        }
    }

}
