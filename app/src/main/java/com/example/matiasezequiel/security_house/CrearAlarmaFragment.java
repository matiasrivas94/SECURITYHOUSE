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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CrearAlarmaFragment extends Fragment {

    MiMensaje mm = new MiMensaje();
    EditText nombre, numTelefono, clave, cantZonas;
    MainActivity mainActivity = (MainActivity)getActivity();
    int aux, auxiliar = 0;
    int cantZona,modiAlarma;
    String estadoEditarAlarma="default";
    SharedPreferences prefs4;
    SharedPreferences.Editor editor1;

    //Cosas del Spinner
    Spinner opciones;
    List<String> listaTipos;
    ArrayAdapter<String> adapterSpinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crear_alarma, container, false);

        //cosas del SPINNER
        //opciones = (Spinner) v.findViewById(R.id.SPTipo);
        opciones = (Spinner) v.findViewById(R.id.SPTipo);
        listaTipos = new ArrayList<>();

        //Arreglo String de tipos
        String[] tipos = {"Casa", "Oficina", "Tienda"};
        //Cargo las frutas en listaTipos
        Collections.addAll(listaTipos, tipos);
        //Paso los valores a mi adapter
        adapterSpinner = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, listaTipos);
        //Linea de código secundario sirve para asignar un layout a los ítems
        adapterSpinner.setDropDownViewResource(android.R.layout.preference_category);
        //Muestro los ítems en el spinner, obtenidos gracias al adapter
        opciones.setAdapter(adapterSpinner);

        //String que almacena el nombre de la fruta donde inicializara el valor ítem del spinner
        //ESTE STRING LO PODEMOS OBTENER DE DIFERENTES FORMAS (DESDE UN BASE DE DATOS, UN ARREGLO, ...)
        String inicializarItem = "Casa";


        nombre = (EditText) v.findViewById(R.id.ETNombre);
        opciones = (Spinner) v.findViewById(R.id.SPTipo);
        numTelefono = (EditText) v.findViewById(R.id.ETNumTelefono);
        clave = (EditText) v.findViewById(R.id.ETClave);
        cantZonas = (EditText) v.findViewById(R.id.ETCantZonas);

        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (nombre.length() > 0) {
                    auxiliar = -1;
                    editor1 = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor1.putLong("auxiliar", auxiliar);
                    editor1.commit();
                }
                else {
                    auxiliar = 0;
                    editor1 = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor1.putLong("auxiliar", auxiliar);
                    editor1.commit();
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        numTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (numTelefono.length() > 0) {
                    auxiliar = -1;
                    editor1 = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor1.putLong("auxiliar", auxiliar);
                    editor1.commit();
                }
                else {
                    auxiliar = 0;
                    editor1 = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor1.putLong("auxiliar", auxiliar);
                    editor1.commit();
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        clave.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (clave.length() > 0) {
                    auxiliar = -1;
                    editor1 = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor1.putLong("auxiliar", auxiliar);
                    editor1.commit();
                }
                else {
                    auxiliar = 0;
                    editor1 = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor1.putLong("auxiliar", auxiliar);
                    editor1.commit();
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        cantZonas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cantZonas.length() > 0) {
                    auxiliar = -1;
                    editor1 = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor1.putLong("auxiliar", auxiliar);
                    editor1.commit();
                }
                else {
                    auxiliar = 0;
                    editor1 = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor1.putLong("auxiliar", auxiliar);
                    editor1.commit();
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        //Shared para quedarme con el idAlarma seleccionada para modificar
        SharedPreferences prefs1 = getContext().getSharedPreferences("eliminarAlarma",Context.MODE_PRIVATE);
        modiAlarma = (int)prefs1.getLong("elimAlarm",-1);

        //Shared para modificar la alarma de alarmasFragment
        prefs4 = getContext().getSharedPreferences("cadenaEditar",Context.MODE_PRIVATE);
        estadoEditarAlarma=prefs4.getString("editarString"," ");


        Button btnCrearAlarma =(Button)v.findViewById(R.id.btnCrearAlarma);

            btnCrearAlarma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(estadoEditarAlarma.equals("insert")) {
                        if (ComprobarCampos()) {
                            aux = Integer.parseInt(cantZonas.getText().toString());
                            if ((aux < 1) || (aux > 6)) {
                                cantZonas.setText("");
                                cantZonas.setError("Maximo 6");
                                cantZonas.isFocusable();
                            } else {
                                FragmentTransaction fr = getFragmentManager().beginTransaction();
                                fr.replace(R.id.contenedor, new PrincipalFragment(), "Principal");
                                fr.commit();
                                agregar(v);
                            }
                        } else {
                            Toast.makeText(v.getContext(), "Hay campos vacios, por favor ingrese datos", Toast.LENGTH_LONG).show();
                        }
                        prefs4.edit().remove("editarString").commit();
                    }
                    if(estadoEditarAlarma.equals("update")){
                        editar(v);
                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.contenedor, new AlarmasFragment(), "Alarmas");
                        fr.commit();
                    }
                }
            });

        if(estadoEditarAlarma.equals("update")){
            btnCrearAlarma.setText("Modificar");
            reflejarCampos();
            prefs4.edit().remove("editarString").commit();
        }

        return v;
    }

    //Método para obtener la posición de un ítem del spinner
    public int obtenerPosicionItem(Spinner spinner, String tipo) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String tipo`
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spinner.getItemAtPosition(i).toString().equals(tipo)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
    }

    public void agregar(View v){
        if(ComprobarCampos()){
            String nom,numTel,tipo,password;
            //int cantZona;

            nom = nombre.getText().toString();
            numTel = numTelefono.getText().toString();
            tipo = opciones.getSelectedItem().toString();
            password = clave.getText().toString();
            cantZona = Integer.parseInt(cantZonas.getText().toString());

            //sqlite bh = new sqlite(AgregarActivity.this,"usuarios",null,1);
            AlarmaSQLite bd = new AlarmaSQLite(this.getActivity(),"alarma",null,1);
            if(bd!=null){
                SQLiteDatabase db = bd.getWritableDatabase();
                ContentValues con = new ContentValues();
                con.put("nombre",nom);
                con.put("tipo",tipo);
                con.put("numTelefono","+549"+numTel);
                con.put("clave",password);
                con.put("cantZonas",cantZona);

                //Shared para el string de la alrma ingresada
                SharedPreferences.Editor editor2 = getContext().getSharedPreferences("dd",Context.MODE_PRIVATE).edit();
                editor2.putString ("estadoZonaString","sapeeeeeeeee");
                editor2.commit();

                SharedPreferences.Editor editor1 = getContext().getSharedPreferences("bb",Context.MODE_PRIVATE).edit();
                editor1.putLong("cantZ", cantZona);
                editor1.commit();

                //Shared para el nombre de la alarma
                SharedPreferences.Editor editor = getContext().getSharedPreferences("ee",Context.MODE_PRIVATE).edit();
                editor.putString ("nombreAlarma",nom);
                editor.commit();

                long insertado = db.insert("alarma",null,con);
                if(insertado>0){
                    Toast.makeText(this.getActivity(),"Alarma Insertada", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this.getActivity(),"No se inserto",Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(this.getActivity(),"hay campos vacios",Toast.LENGTH_LONG).show();
        }
        nombre.requestFocus();
        nombre.setText("");
        numTelefono.setText("");
        opciones.setSelection(0);
        clave.setText("");
        cantZonas.setText("");
    }
    public boolean ComprobarCampos(){
        if(nombre.getText().toString().isEmpty() || numTelefono.getText().toString().isEmpty() || clave.getText().toString().isEmpty() || cantZonas.getText().toString().isEmpty()){
            if(nombre.getText().toString().isEmpty())
            {
                nombre.setError("Escriba el nombre de la alarma");
            }
            if(numTelefono.getText().toString().isEmpty())
            {
                numTelefono.setError("Escriba el numero de telefono");
            }
            if(clave.getText().toString().isEmpty())
            {
                clave.setError("Escriba la clave");
            }
            return false;
        }else{
            return true;
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
            //fab.setImageResource(R.drawable.ic_); //Cambiar icono
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contenedor, new CrearAlarmaFragment()).addToBackStack(null);
                    fr.commit();*/
                    //NUEVA FUNCIONALIDAD
                }
            });
        }
    }


    //metodos para mostrar datos en los campos y editarlos
    public void reflejarCampos(){
        AlarmaSQLite bh  = new AlarmaSQLite(this.getActivity(),"alarma",null,1);
        int idAlarmaModi = modiAlarma;
        if(bh!=null){
            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM alarma WHERE idAlarma = "+idAlarmaModi,null);
            try{
                if(c.moveToNext()){
                    nombre.setText(c.getString(1));

                    String algo = c.getString(2);
                    int pos=-1;
                    if (algo.equals("Casa")) {
                        pos = obtenerPosicionItem(opciones, algo);
                        opciones.setSelection(pos);
                    }
                    if (algo.equals("Oficina")) {
                        pos = obtenerPosicionItem(opciones, algo);
                        opciones.setSelection(pos);
                    }
                    if (algo.equals("Tienda")) {
                        pos = obtenerPosicionItem(opciones, algo);
                        opciones.setSelection(pos);
                    }

                    numTelefono.setText(c.getString(3));
                    clave.setText(c.getString(4));
                    cantZonas.setText(c.getString(5));
                }
            }finally {

            }
        }
    }
    public void editar(View v){
        AlarmaSQLite bh = new AlarmaSQLite(this.getActivity(),"alarma",null,1);
        int idAlarmaModi = modiAlarma;
        if(bh!=null){
            SQLiteDatabase db = bh.getWritableDatabase();
            ContentValues val = new ContentValues();
            val.put("nombre",nombre.getText().toString());
            val.put("tipo",opciones.getSelectedItem().toString());
            val.put("numTelefono",numTelefono.getText().toString());
            val.put("clave",clave.getText().toString());
            //val.put("cantZonas",Integer.parseInt(cantZonas.getText().toString()));

            long response = db.update("alarma",val,"idAlarma="+idAlarmaModi,null);
            if(response>0){
                Toast.makeText(this.getActivity(),"Editado con exito",Toast.LENGTH_LONG).show();
                nombre.requestFocus();
                nombre.setText("");
                numTelefono.setText("");
                opciones.setSelection(0);
                clave.setText("");
                //cantZonas.setText("");
            }else{
                Toast.makeText(this.getActivity(),"Ocurrio un error",Toast.LENGTH_LONG).show();
            }
        }
    }

}
