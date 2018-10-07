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

    private int alarmaEditar;
    Button boton;
    int cantZ=0,clickAlarma=0, idAlarma=0;
    ListView list;
    EditText ed1, ed2, ed3, ed4, ed5, ed6;
    TextView tv1, tv2, tv3, tv4, tv5, tv6;
    int cant=0;
    int idUltAlarmaIngresada=0;
    boolean estado;
    String estadoAlarma="default";
    SharedPreferences prefs4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_zonas, container, false);

        //cantidadZonas();
        ed1 = (EditText) v.findViewById(R.id.edName1);
        ed2 = (EditText) v.findViewById(R.id.edName2);
        ed3 = (EditText) v.findViewById(R.id.edName3);
        ed4 = (EditText) v.findViewById(R.id.edName4);
        ed5 = (EditText) v.findViewById(R.id.edName5);
        ed6 = (EditText) v.findViewById(R.id.edName6);
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

        visibilidadEditText(v);
        //mostrarZonas();

        boton = (Button)v.findViewById(R.id.btnGuardarZonas);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ComprobarCampos()) {
                    visibilidadTextView(v);
                }
                else {
                    Toast.makeText(v.getContext(),"Hay campos vacios, por favor ingrese nuevamente",Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }


    public void visibilidadEditText(View view) {

        AlarmaSQLite bd = new AlarmaSQLite(this.getActivity(),"alarma",null,1);
        SQLiteDatabase db = bd.getReadableDatabase();

        //busco el idAlarma de la ultima alarma ingresada
        SQLiteStatement s = db.compileStatement( "SELECT MAX(idAlarma) FROM alarma");
        cant = (int)s.simpleQueryForLong();

        int idAla = cant;
        SQLiteStatement s1 = db.compileStatement("SELECT cantZonas FROM alarma WHERE idAlarma="+idAla);
        int cantidad = (int)s1.simpleQueryForLong();
        //Toast.makeText(getContext(),"Cantidad de Zonas de la ultima alarma insertada: " + cantidad,Toast.LENGTH_LONG).show();

        // Se crean los EditText cuando se crea una alarma
        if(estadoAlarma != " ")
        {
            //int cant = cantZ;
            switch (cantidad) {
                case 1:
                    Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidad, Toast.LENGTH_SHORT).show();
                    if (cantidad == 1) {
                        ed1.setVisibility(View.VISIBLE);
                        prefs4.edit().remove("estadoZonaString").commit();
                    }
                    break;
                case 2:
                    Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidad, Toast.LENGTH_SHORT).show();
                    if (cantidad == 2) {
                        ed1.setVisibility(View.VISIBLE);
                        ed2.setVisibility(View.VISIBLE);
                        prefs4.edit().remove("estadoZonaString").commit();
                    }
                    break;
                case 3:
                    Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidad, Toast.LENGTH_SHORT).show();
                    if (cantidad == 3) {
                        ed1.setVisibility(View.VISIBLE);
                        ed2.setVisibility(View.VISIBLE);
                        ed3.setVisibility(View.VISIBLE);
                        prefs4.edit().remove("estadoZonaString").commit();
                    }
                    break;
                case 4:
                    Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidad, Toast.LENGTH_SHORT).show();
                    if (cantidad == 4) {
                        ed1.setVisibility(View.VISIBLE);
                        ed2.setVisibility(View.VISIBLE);
                        ed3.setVisibility(View.VISIBLE);
                        ed4.setVisibility(View.VISIBLE);
                        prefs4.edit().remove("estadoZonaString").commit();
                    }
                    break;
                case 5:
                    Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidad, Toast.LENGTH_SHORT).show();
                    if (cantidad == 5) {
                        ed1.setVisibility(View.VISIBLE);
                        ed2.setVisibility(View.VISIBLE);
                        ed3.setVisibility(View.VISIBLE);
                        ed4.setVisibility(View.VISIBLE);
                        ed5.setVisibility(View.VISIBLE);
                        prefs4.edit().remove("estadoZonaString").commit();
                    }
                    break;
                case 6:
                    Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidad, Toast.LENGTH_SHORT).show();
                    if (cantidad == 6) {
                        ed1.setVisibility(View.VISIBLE);
                        ed2.setVisibility(View.VISIBLE);
                        ed3.setVisibility(View.VISIBLE);
                        ed4.setVisibility(View.VISIBLE);
                        ed5.setVisibility(View.VISIBLE);
                        ed6.setVisibility(View.VISIBLE);
                        prefs4.edit().remove("estadoZonaString").commit();
                    }
                    break;
                }

        }

        // Se crean los EditText segun la alarma que se clickee en la lista de alarmas
        if(estadoAlarma == " ")
        {
            //selecciono todas las zonas almacenadas segun el id de la alarma que traigo al crear la alarma
            AlarmaSQLite bd1 = new AlarmaSQLite(getActivity(),"zona",null,1);
            ArrayList<Zona> zonas = new ArrayList<>();
            SQLiteDatabase db1 = bd1.getWritableDatabase();
            Cursor c = db1.rawQuery("SELECT * FROM zona where idAlarma="+clickAlarma,null);
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
            Toast.makeText(this.getActivity(),"Cantidad de Zonas: " + cantidadZ ,Toast.LENGTH_LONG).show();
            switch (cantidadZ) {
                case 1:
                    Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidadZ, Toast.LENGTH_SHORT).show();
                    tv1.setText(arreglo.get(0));
                    tv1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidadZ, Toast.LENGTH_SHORT).show();
                    tv1.setText(arreglo.get(0));
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setText(arreglo.get(1));
                    tv2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidadZ, Toast.LENGTH_SHORT).show();
                    tv1.setText(arreglo.get(0));
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setText(arreglo.get(1));
                    tv2.setVisibility(View.VISIBLE);
                    tv3.setText(arreglo.get(2));
                    tv3.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidadZ, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidadZ, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(this.getActivity(), "Cantidad de Zonas: " + cantidadZ, Toast.LENGTH_SHORT).show();
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

    public void visibilidadTextView(View v){

        String n1, n2, n3, n4, n5, n6;

        n1 = ed1.getText().toString();
        n2 = ed2.getText().toString();
        n3 = ed3.getText().toString();
        n4 = ed4.getText().toString();
        n5 = ed5.getText().toString();
        n6 = ed6.getText().toString();

        AlarmaSQLite bd = new AlarmaSQLite(this.getActivity(),"zona",null,1);

        if(bd!=null) {
            SQLiteDatabase db = bd.getWritableDatabase();
            ContentValues con = new ContentValues();

            // Se toman los datos de los EditText, se insertan en la base y se crean los TextView
            // cuando se crea una alarma
            //if(estado == true) {
                Toast.makeText(this.getActivity(), "ID de la Ultima alarma Ingresada: " + cant, Toast.LENGTH_SHORT).show();
                if (ed1.getVisibility() == View.VISIBLE) {
                    con.put("idAlarma", cant);
                    con.put("nombre", n1);
                    db.insert("zona", null, con);

                    tv1.setText(ed1.getText());
                    ed1.setVisibility(View.GONE);
                    tv1.setVisibility(View.VISIBLE);
                }
                if (ed2.getVisibility() == View.VISIBLE) {
                    con.put("idAlarma", cant);
                    con.put("nombre", n2);
                    db.insert("zona", null, con);

                    tv2.setText(ed2.getText());
                    ed2.setVisibility(View.GONE);
                    tv2.setVisibility(View.VISIBLE);
                }
                if (ed3.getVisibility() == View.VISIBLE) {
                    con.put("idAlarma", cant);
                    con.put("nombre", n3);
                    db.insert("zona", null, con);

                    tv3.setText(ed3.getText());
                    ed3.setVisibility(View.GONE);
                    tv3.setVisibility(View.VISIBLE);
                }
                if (ed4.getVisibility() == View.VISIBLE) {
                    con.put("idAlarma", cant);
                    con.put("nombre", n4);
                    db.insert("zona", null, con);

                    tv4.setText(ed4.getText());
                    ed4.setVisibility(View.GONE);
                    tv4.setVisibility(View.VISIBLE);
                }
                if (ed5.getVisibility() == View.VISIBLE) {
                    con.put("idAlarma", cant);
                    con.put("nombre", n5);
                    db.insert("zona", null, con);

                    tv5.setText(ed5.getText());
                    ed5.setVisibility(View.GONE);
                    tv5.setVisibility(View.VISIBLE);
                }
                if (ed6.getVisibility() == View.VISIBLE) {
                    con.put("idAlarma", cant);
                    con.put("nombre", n6);
                    db.insert("zona", null, con);

                    tv6.setText(ed6.getText());
                    ed6.setVisibility(View.GONE);
                    tv6.setVisibility(View.VISIBLE);
                }

        }
    }

    public boolean ComprobarCampos() {
        Log.d("PROBANDO", "ENTRO EN COMPROBAR CAMPOS");
        int cant = cantZ;
        boolean resp = true;
        switch (cant) {
            case 1:
                if (cant == 1) {
                    if (ed1.getText().toString().isEmpty()) {
                        ed1.setError("Complete este campo");
                        resp = false;
                    }
                    else
                        resp = true;
                }
                break;
            case 2:
                if (cant == 2) {
                    if (ed1.getText().toString().isEmpty() || ed2.getText().toString().isEmpty()) {
                        if (ed1.getText().toString().isEmpty()) {
                            ed1.setError("Complete este campo");
                            resp = false;
                        }
                        if(ed2.getText().toString().isEmpty()) {
                            resp = false;
                        }
                    }
                    else
                        resp = true;
                }
                break;
            case 3:
                if (cant == 3) {
                    if (ed1.getText().toString().isEmpty() || ed2.getText().toString().isEmpty() || ed3.getText().toString().isEmpty()) {
                        if (ed1.getText().toString().isEmpty()) {
                            ed1.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed2.getText().toString().isEmpty()) {
                            ed2.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed3.getText().toString().isEmpty()) {
                            ed3.setError("Complete este campo");
                            resp = false;
                        }
                    }
                    else
                        resp = true;
                }
                break;
            case 4:
                if (cant == 4) {
                    if (ed1.getText().toString().isEmpty() || ed2.getText().toString().isEmpty() || ed3.getText().toString().isEmpty() || ed4.getText().toString().isEmpty()) {
                        if (ed1.getText().toString().isEmpty()) {
                            ed1.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed2.getText().toString().isEmpty()) {
                            ed2.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed3.getText().toString().isEmpty()) {
                            ed3.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed4.getText().toString().isEmpty()) {
                            ed4.setError("Complete este campo");
                            resp = false;
                        }
                    }
                    else
                        resp = true;
                }
                break;
            case 5:
                if (cant == 5) {
                    if (ed1.getText().toString().isEmpty() || ed2.getText().toString().isEmpty() || ed3.getText().toString().isEmpty() || ed4.getText().toString().isEmpty() || ed5.getText().toString().isEmpty()) {
                        if (ed1.getText().toString().isEmpty()) {
                            ed1.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed2.getText().toString().isEmpty()) {
                            ed2.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed3.getText().toString().isEmpty()) {
                            ed3.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed4.getText().toString().isEmpty()) {
                            ed4.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed5.getText().toString().isEmpty()) {
                            ed5.setError("Complete este campo");
                            resp = false;
                        }
                    }
                    else
                        resp = true;
                }
                break;
            case 6:
                if (cant == 6) {
                    if (ed1.getText().toString().isEmpty() || ed2.getText().toString().isEmpty() || ed3.getText().toString().isEmpty() || ed4.getText().toString().isEmpty() || ed5.getText().toString().isEmpty() || ed6.getText().toString().isEmpty()) {
                        if (ed1.getText().toString().isEmpty()) {
                            ed1.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed2.getText().toString().isEmpty()) {
                            ed2.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed3.getText().toString().isEmpty()) {
                            ed3.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed4.getText().toString().isEmpty()) {
                            ed4.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed5.getText().toString().isEmpty()) {
                            ed5.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed6.getText().toString().isEmpty()) {
                            ed6.setError("Complete este campo");
                            resp = false;
                        }
                    }
                    else
                        resp = true;
                }
                break;
        }
        return resp;
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

    // Metodos para mostrar y editar las zonas
    /*
    public void reflejarCampos(){
        sqlite bh  = new sqlite(EditarActivity.this,"usuarios",null,1);
        if(bh!=null){
            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM usuarios WHERE idusuario = "+usuarioEditar,null);
            try{
                if(c.moveToNext()){
                    nombre.setText(c.getString(1));
                    apellidos.setText(c.getString(2));
                    edad.setText(c.getString(3));
                }
            }finally {

            }
        }
    }
    public void editar(View v){
        sqlite bh = new sqlite(EditarActivity.this,"usuarios",null,1);
        if(bh!=null){
            SQLiteDatabase db = bh.getWritableDatabase();
            ContentValues val = new ContentValues();
            val.put("nombre",nombre.getText().toString());
            val.put("apellidos",apellidos.getText().toString());
            val.put("edad",Integer.parseInt(edad.getText().toString()));
            long response = db.update("usuarios",val,"idusuario="+usuarioEditar,null);
            if(response>0){
                Toast.makeText(EditarActivity.this,"Editado con exito",Toast.LENGTH_LONG).show();
                nombre.setText("");
                apellidos.setText("");
                edad.setText("");
            }else{
                Toast.makeText(EditarActivity.this,"Ocurrio un error",Toast.LENGTH_LONG).show();
            }
        }
    }

    */




}
