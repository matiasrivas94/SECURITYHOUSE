package com.example.matiasezequiel.security_house;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Spinner;
import android.widget.Toast;


public class CrearAlarmaFragment extends Fragment {

    MiMensaje mm = new MiMensaje();
	Spinner opciones;
    EditText nombre, numTelefono, clave, cantZonas;
    MainActivity mainActivity = (MainActivity)getActivity();
    int aux = 0;
    int cantZona;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crear_alarma, container, false);

        nombre = (EditText) v.findViewById(R.id.ETNombre);
        opciones = (Spinner) v.findViewById(R.id.SPTipo);
        numTelefono = (EditText) v.findViewById(R.id.ETNumTelefono);
        clave = (EditText) v.findViewById(R.id.ETClave);
        cantZonas = (EditText) v.findViewById(R.id.ETCantZonas);

        Button btnCrearAlarma =(Button)v.findViewById(R.id.btnCrearAlarma);

            btnCrearAlarma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(ComprobarCampos()) {
                        aux = Integer.parseInt(cantZonas.getText().toString());
                        if((aux < 1) || (aux > 6)) {
                            cantZonas.setText("");
                            cantZonas.setError("Maximo 6");
                            cantZonas.isFocusable();
                        }
                        else {
                            FragmentTransaction fr = getFragmentManager().beginTransaction();
                            fr.replace(R.id.contenedor, new PrincipalFragment(),"Principal");
                            fr.commit();
                            agregar(v);
                        }
                    }
                    else {
                        Toast.makeText(v.getContext(),"Hay campos vacios, por favor ingrese datos",Toast.LENGTH_LONG).show();
                    }
                }
            });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.opciones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.preference_category);
        opciones.setAdapter(adapter);

        return v;
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
                con.put("numTelefono",numTel);
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

}
