package com.example.matiasezequiel.security_house;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteStatement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class TabZonasFragment extends Fragment {

    Button boton;
    int cantZ=0,clickAlarma=0;
    TextView tv1, tv2, tv3, tv4, tv5, tv6;
    int cant=0;

    String estadoAlarma;
    SharedPreferences prefs4;
    String actualizarListaZonas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_zonas, container, false);

        //cantidadZonas();
        tv1 = (TextView) v.findViewById(R.id.tvName1);
        tv2 = (TextView) v.findViewById(R.id.tvName2);
        tv3 = (TextView) v.findViewById(R.id.tvName3);
        tv4 = (TextView) v.findViewById(R.id.tvName4);
        tv5 = (TextView) v.findViewById(R.id.tvName5);
        tv6 = (TextView) v.findViewById(R.id.tvName6);

        //Shared de la Cantidad de Zonas
        SharedPreferences prefs1 = getContext().getSharedPreferences("bb",Context.MODE_PRIVATE);
        cantZ=(int)prefs1.getLong("cantZ",-1);
        //Toast.makeText(this.getActivity(),"Cantidad de Zonas: " + cantZ, Toast.LENGTH_SHORT).show();

        //nuevo
        //Shared para saber el id de la alarma clickeada en la lista de las alarmas
        SharedPreferences prefs2 = getContext().getSharedPreferences("cc",Context.MODE_PRIVATE);
        clickAlarma=(int)prefs2.getLong("idAlarma",-1);
        //Toast.makeText(this.getActivity(),"ID de la Alarma Seleccionada: " + clickAlarma, Toast.LENGTH_SHORT).show();

        //Shared para identificar el estado de zonas
        /*SharedPreferences prefs3 = getContext().getSharedPreferences("zonas",Context.MODE_PRIVATE);
        estado = prefs3.getBoolean("estadoZona",false);
        if(estado)
            Toast.makeText(this.getActivity(),"Estado es TRUE", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this.getActivity(),"Estado es FALSE", Toast.LENGTH_SHORT).show();
        */
        //Shared para saber el id de la alarma clickeada en la lista de las alarmas
        prefs4 = getContext().getSharedPreferences("dd",Context.MODE_PRIVATE);
        estadoAlarma=prefs4.getString("estadoZonaString"," ");

        //nuevo metodo para ver los TextView
        visibilidadSoloTextView(v);


        boton = (Button)v.findViewById(R.id.btnGuardarZonas);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Campos llenos!!!",Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    public void visibilidadSoloTextView(View v) {
        //Parte de la Alarma, busco el id de la ultima alarma insertada
        AlarmaSQLite bdA = new AlarmaSQLite(this.getActivity(),"alarma",null,1);
        SQLiteDatabase dbA = bdA.getReadableDatabase();

        //busco el idAlarma de la ultima alarma ingresada
        SQLiteStatement s = dbA.compileStatement( "SELECT MAX(idAlarma) FROM alarma");
        cant = (int)s.simpleQueryForLong();

        int idAla = cant;
        SQLiteStatement s1 = dbA.compileStatement("SELECT cantZonas FROM alarma WHERE idAlarma="+idAla);
        int cantidad = (int)s1.simpleQueryForLong();
        //Toast.makeText(getContext(),"Cantidad de Zonas de la ultima alarma insertada: " + cantidad,Toast.LENGTH_LONG).show();

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

            switch (cantidad) {
                case 1:
                    //Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidad, Toast.LENGTH_SHORT).show();
                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 1");
                    dbZ.insert("zona", null, conZ);

                    tv1.setText("Zona 1");
                    tv1.setVisibility(View.VISIBLE);

                    prefs4.edit().remove("estadoZonaString").commit();
                    break;
                case 2:
                    //Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidad, Toast.LENGTH_SHORT).show();
                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 1");
                    dbZ.insert("zona", null, conZ);

                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 2");
                    dbZ.insert("zona", null, conZ);

                    tv1.setText("Zona 1");
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setText("Zona 2");
                    tv2.setVisibility(View.VISIBLE);

                    prefs4.edit().remove("estadoZonaString").commit();
                    break;
                case 3:
                    //Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidad, Toast.LENGTH_SHORT).show();
                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 1");
                    dbZ.insert("zona", null, conZ);

                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 2");
                    dbZ.insert("zona", null, conZ);

                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 3");
                    dbZ.insert("zona", null, conZ);

                    tv1.setText("Zona 1");
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setText("Zona 2");
                    tv2.setVisibility(View.VISIBLE);
                    tv3.setText("Zona 3");
                    tv3.setVisibility(View.VISIBLE);

                    prefs4.edit().remove("estadoZonaString").commit();
                    break;
                case 4:
                    //Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidad, Toast.LENGTH_SHORT).show();
                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 1");
                    dbZ.insert("zona", null, conZ);

                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 2");
                    dbZ.insert("zona", null, conZ);

                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 3");
                    dbZ.insert("zona", null, conZ);

                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 4");
                    dbZ.insert("zona", null, conZ);

                    tv1.setText("Zona 1");
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setText("Zona 2");
                    tv2.setVisibility(View.VISIBLE);
                    tv3.setText("Zona 3");
                    tv3.setVisibility(View.VISIBLE);
                    tv4.setText("Zona 4");
                    tv4.setVisibility(View.VISIBLE);

                    prefs4.edit().remove("estadoZonaString").commit();
                    break;
                case 5:
                    //Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidad, Toast.LENGTH_SHORT).show();
                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 1");
                    dbZ.insert("zona", null, conZ);

                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 2");
                    dbZ.insert("zona", null, conZ);

                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 3");
                    dbZ.insert("zona", null, conZ);

                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 4");
                    dbZ.insert("zona", null, conZ);

                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 5");
                    dbZ.insert("zona", null, conZ);

                    tv1.setText("Zona 1");
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setText("Zona 2");
                    tv2.setVisibility(View.VISIBLE);
                    tv3.setText("Zona 3");
                    tv3.setVisibility(View.VISIBLE);
                    tv4.setText("Zona 4");
                    tv4.setVisibility(View.VISIBLE);
                    tv5.setText("Zona 5");
                    tv5.setVisibility(View.VISIBLE);

                    prefs4.edit().remove("estadoZonaString").commit();
                    break;
                case 6:
                    //Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidad, Toast.LENGTH_SHORT).show();
                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 1");
                    dbZ.insert("zona", null, conZ);

                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 2");
                    dbZ.insert("zona", null, conZ);

                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 3");
                    dbZ.insert("zona", null, conZ);

                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 4");
                    dbZ.insert("zona", null, conZ);

                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 5");
                    dbZ.insert("zona", null, conZ);

                    conZ.put("idAlarma", cant);
                    conZ.put("nombre", "Zona 6");
                    dbZ.insert("zona", null, conZ);

                    tv1.setText("Zona 1");
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setText("Zona 2");
                    tv2.setVisibility(View.VISIBLE);
                    tv3.setText("Zona 3");
                    tv3.setVisibility(View.VISIBLE);
                    tv4.setText("Zona 4");
                    tv4.setVisibility(View.VISIBLE);
                    tv5.setText("Zona 5");
                    tv5.setVisibility(View.VISIBLE);
                    tv6.setText("Zona 6");
                    tv6.setVisibility(View.VISIBLE);

                    prefs4.edit().remove("estadoZonaString").commit();
                    break;
            }
        }

        // Se crean los TextView segun la alarma que se clickee en la lista de alarmas
        if(estadoAlarma == " ")
        {
            //Shareds para el fragment principal
            SharedPreferences.Editor editor = getContext().getSharedPreferences("idAlarmaPrin",Context.MODE_PRIVATE).edit();
            editor.putLong("idAlarmaPrincipal", clickAlarma);
            editor.commit();

            //selecciono todas las zonas almacenadas segun el id de la alarma que traigo al crear la alarma
            //AlarmaSQLite bd1 = new AlarmaSQLite(getActivity(),"zona",null,1);
            ArrayList<Zona> zonas = new ArrayList<>();
            //SQLiteDatabase db1 = bdZ.getWritableDatabase();
            Cursor c = dbZ.rawQuery("SELECT * FROM zona where idAlarma="+clickAlarma,null);
            if(c.moveToFirst()){
                do{
                    zonas.add(new Zona(c.getInt(0),c.getInt(1),c.getString(2)));
                }while(c.moveToNext());
            }
            //String arreglo="";
            ArrayList<String> arreglo = new ArrayList<>();
            for (int i = 0;i<zonas.size();i++){
                arreglo.add (zonas.get(i).getNombre());
            }
            //Toast.makeText(this.getActivity(),"Zonas: " + arreglo ,Toast.LENGTH_LONG).show();
            int cantidadZ = arreglo.size();
            //Toast.makeText(this.getActivity(),"Cantidad de Zonas: " + cantidadZ ,Toast.LENGTH_LONG).show();
            switch (cantidadZ) {
                case 1:
                    //Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidadZ, Toast.LENGTH_SHORT).show();
                    tv1.setText(arreglo.get(0));
                    tv1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    //Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidadZ, Toast.LENGTH_SHORT).show();
                    tv1.setText(arreglo.get(0));
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setText(arreglo.get(1));
                    tv2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    //Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidadZ, Toast.LENGTH_SHORT).show();
                    tv1.setText(arreglo.get(0));
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setText(arreglo.get(1));
                    tv2.setVisibility(View.VISIBLE);
                    tv3.setText(arreglo.get(2));
                    tv3.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    //Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidadZ, Toast.LENGTH_SHORT).show();
                    tv1.setText(arreglo.get(0));
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setText(arreglo.get(1));
                    tv2.setVisibility(View.VISIBLE);
                    tv3.setText(arreglo.get(2));
                    tv3.setVisibility(View.VISIBLE);
                    tv4.setText(arreglo.get(3));
                    tv4.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    //Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidadZ, Toast.LENGTH_SHORT).show();
                    tv1.setText(arreglo.get(0));
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setText(arreglo.get(1));
                    tv2.setVisibility(View.VISIBLE);
                    tv3.setText(arreglo.get(2));
                    tv3.setVisibility(View.VISIBLE);
                    tv4.setText(arreglo.get(3));
                    tv4.setVisibility(View.VISIBLE);
                    tv5.setText(arreglo.get(4));
                    tv5.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    //Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidadZ, Toast.LENGTH_SHORT).show();
                    tv1.setText(arreglo.get(0));
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setText(arreglo.get(1));
                    tv2.setVisibility(View.VISIBLE);
                    tv3.setText(arreglo.get(2));
                    tv3.setVisibility(View.VISIBLE);
                    tv4.setText(arreglo.get(3));
                    tv4.setVisibility(View.VISIBLE);
                    tv5.setText(arreglo.get(4));
                    tv5.setVisibility(View.VISIBLE);
                    tv6.setText(arreglo.get(5));
                    tv6.setVisibility(View.VISIBLE);
                    break;
            }
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
