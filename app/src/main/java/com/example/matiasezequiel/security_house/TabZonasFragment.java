package com.example.matiasezequiel.security_house;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteStatement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class TabZonasFragment extends Fragment {

    Button boton;
    int clickAlarma=0;
    ListView listaZonas;
    int cant=0;

    String estadoAlarma;
    SharedPreferences prefs4, prefs2;

    //nuevo
    ArrayList<Zona> tabZonas;
    ArrayList<DatosItemZona> datosZonas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_zonas, container, false);

        listaZonas = (ListView)v.findViewById(R.id.LVMostrarZonas);

        //Shared para saber el id de la alarma clickeada en la lista de las alarmas
        prefs2 = getContext().getSharedPreferences("cc",Context.MODE_PRIVATE);
        clickAlarma=(int)prefs2.getLong("idAlarma",-1);
        //Toast.makeText(this.getActivity(),"ID de la Alarma Seleccionada: " + clickAlarma, Toast.LENGTH_SHORT).show();

        //Shared para saber el id de la alarma clickeada en la lista de las alarmas
        prefs4 = getContext().getSharedPreferences("dd",Context.MODE_PRIVATE);
        estadoAlarma=prefs4.getString("estadoZonaString"," ");

        visibilidadSoloTextView(v);

        return v;
    }

    public void visibilidadSoloTextView(View v) {
        //Parte de la Alarma, busco el id de la ultima alarma insertada
        AlarmaSQLite bdA = new AlarmaSQLite(this.getActivity(),"alarma",null,1);
        SQLiteDatabase dbA = bdA.getReadableDatabase();

        //busco el idAlarma de la ultima alarma ingresada
        SQLiteStatement s = dbA.compileStatement( "SELECT MAX(idAlarma) FROM alarma");
        cant = (int)s.simpleQueryForLong();

        //Conexion a la base de Zona
        AlarmaSQLite bdZ = new AlarmaSQLite(this.getActivity(),"zona",null,1);
        SQLiteDatabase dbZ = bdZ.getWritableDatabase();
        ContentValues conZ = new ContentValues();

        // Se crean los TextView cuando se crea una alarma
        if(estadoAlarma != " ")
        {
            //Shareds para el fragment principal
            SharedPreferences.Editor editor = getContext().getSharedPreferences("idAlarmaPrin",Context.MODE_PRIVATE).edit();
            editor.putLong("idAlarmaPrincipal", cant);
            editor.commit();

            //Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidad, Toast.LENGTH_SHORT).show();
            conZ.put("idAlarma", cant);
            conZ.put("nombre", "Zona 1");
            conZ.put("estado", 1);
            dbZ.insert("zona", null, conZ);

            conZ.put("idAlarma", cant);
            conZ.put("nombre", "Zona 2");
            conZ.put("estado", 1);
            dbZ.insert("zona", null, conZ);

            conZ.put("idAlarma", cant);
            conZ.put("nombre", "Zona 3");
            conZ.put("estado", 1);
            dbZ.insert("zona", null, conZ);

            conZ.put("idAlarma", cant);
            conZ.put("nombre", "Zona 4");
            conZ.put("estado", 1);
            dbZ.insert("zona", null, conZ);

            conZ.put("idAlarma", cant);
            conZ.put("nombre", "Zona 5");
            conZ.put("estado", 1);
            dbZ.insert("zona", null, conZ);

            conZ.put("idAlarma", cant);
            conZ.put("nombre", "Zona 6");
            conZ.put("estado", 1);
            dbZ.insert("zona", null, conZ);

            llenarLista(cant);
            prefs4.edit().remove("estadoZonaString").commit();
        }

        // Se muestran las zonas segun la alarma que se clickee en la lista de alarmas
        if(estadoAlarma == " ")
        {
            //Shareds para el fragment principal
            SharedPreferences.Editor editor = getContext().getSharedPreferences("idAlarmaPrin",Context.MODE_PRIVATE).edit();
            editor.putLong("idAlarmaPrincipal", clickAlarma);
            editor.commit();
            llenarLista(clickAlarma);
        }
    }

    public void llenarLista(int idAla){
        datosZonas = new ArrayList<>();
        //conexion a la base de datos
        AlarmaSQLite bd = new AlarmaSQLite(this.getActivity(),"zona",null,1);
        //nuevo
        tabZonas = new ArrayList<>();
        if(bd!=null){
            SQLiteDatabase db = bd.getReadableDatabase();
            //selecciono todas las alarmas almacenadas
            Cursor c = db.rawQuery("SELECT * FROM zona WHERE idAlarma="+idAla,null);
            if(c.moveToFirst()){
                //tvTitulo.setVisibility(View.INVISIBLE);
                do{
                    tabZonas.add(new Zona(c.getInt(0),c.getInt(1),c.getString(2),c.getInt(3),c.getInt(4)));
                }while(c.moveToNext());
            }
        }
        bd.close();
        //Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + tabZonas.size(), Toast.LENGTH_SHORT).show();
        for(int x = 0; x < tabZonas.size(); x++){
            datosZonas.add(new DatosItemZona(x,tabZonas.get(x).getNombre()+" -- "+tabZonas.get(x).getEstado(), R.drawable.snooze1));
        }
        AdaptadorZona adapter = new AdaptadorZona(this.getActivity(), datosZonas);
        listaZonas.setAdapter(adapter);
    }
    //clase interna para manejar el item de la lista de las zonas
    private class AdaptadorZona extends BaseAdapter {
        Context contexto;
        List<DatosItemZona> listaObjetos;

        public AdaptadorZona(Context contexto, List<DatosItemZona> listaObjetos) {
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
            vista = inflate.inflate(R.layout.items_zonas, null);

            Button nombreZona = (Button) vista.findViewById(R.id.btNombreZona);
            ImageView estadoZona = (ImageView) vista.findViewById(R.id.ivNotificacionZona);

            nombreZona.setText(listaObjetos.get(position).getNombreZona());
            estadoZona.setImageResource(listaObjetos.get(position).getImagen());

            estadoZona.setVisibility(vista.INVISIBLE);
            //conexion a la base para traer la notificacion de las zonas
            final AlarmaSQLite bdZ = new AlarmaSQLite(getActivity(),"zona",null,1);
            final SQLiteDatabase dbZ = bdZ.getWritableDatabase();
            final ContentValues conZ = new ContentValues();
            //selecciono todas las zonas almacenadas segun el id de la alarma que traigo del tabZonas
            ArrayList<Zona> zonas = new ArrayList<>();
            Cursor c = dbZ.rawQuery("SELECT * FROM zona where idAlarma="+clickAlarma,null);
            if(c.moveToFirst()){
                do{
                    zonas.add(new Zona(c.getInt(0),c.getInt(1),c.getString(2),c.getInt(3),c.getInt(4)));
                }while(c.moveToNext());
            }
            final ArrayList<Integer> noti = new ArrayList<>();
            final ArrayList<Integer> idZonas = new ArrayList<>();
            for (int i = 0;i<zonas.size();i++){
                idZonas.add (zonas.get(i).getIdZona());
                noti.add(zonas.get(i).getNotificacion());
            }

            if(noti.size() > 0) {
                if (noti.get(position).equals(1)) {
                    estadoZona.setVisibility(vista.VISIBLE);
                    new TextoParpadeante(getContext(),estadoZona);
                    //Boton del menu del item de la lista de las alarmas
                    estadoZona.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            // Menu con alert builder
                            AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                            //builder.setTitle("Elegir Opcion");
                            builder.setTitle("Informacion de la Zona");
                            // add a list
                            String[] animals = {"Modificar Alarma", "Eliminar Alarma"};
                            builder.setItems(animals, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            //estadoZona.setVisibility(v.INVISIBLE);
                                            conZ.put("notificacion", 0);
                                            long response = dbZ.update("zona", conZ, "idZona="+idZonas.get(position), null);
                                            if(response>0){
                                                Toast.makeText(getActivity(),"Editado con exito",Toast.LENGTH_LONG).show();
                                            }else{
                                                Toast.makeText(getActivity(),"Ocurrio un error",Toast.LENGTH_LONG).show();
                                            }
                                            FragmentTransaction fr = getFragmentManager().beginTransaction();
                                            fr.replace(R.id.contenedor, new PrincipalFragment(), "Principal");
                                            fr.commit();

                                            break;
                                        case 1:

                                            break;
                                        case 2:

                                    }
                                }
                            });
                            // create and show the alert dialog
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                }
            }
            return vista;
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
        if (!getUserVisibleHint()) {
            return;
        }
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.hideFloatingActionButton(); //oculto boton
            FloatingActionButton fab = mainActivity.findViewById(R.id.fab);
        }
    }
}
