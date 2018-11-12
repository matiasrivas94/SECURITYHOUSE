package com.example.matiasezequiel.security_house;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CrearCamaraFragment extends Fragment {

    EditText etNombreCam, etIP, etUser, etPass, etPort;
    Button btnAceptarCam, btnCancelarCam;
    Activity a = (Activity)getActivity();
    MainActivity ma = (MainActivity)getActivity();
    int auxiliar = 0;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crear_camara, container, false);

        etNombreCam = (EditText)v.findViewById(R.id.etNombreCam);
        etIP = (EditText)v.findViewById(R.id.etIP);
        etUser = (EditText)v.findViewById(R.id.etUsuario);
        etPass = (EditText)v.findViewById(R.id.etPass);
        etPort = (EditText)v.findViewById(R.id.etPuerto);


        etNombreCam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etIP.length() > 0) {
                    auxiliar = -1;
                    editor = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor.putLong("auxiliar", auxiliar);
                    editor.commit();
                }
                else {
                    auxiliar = 0;
                    editor = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor.putLong("auxiliar", auxiliar);
                    editor.commit();
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        etIP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etIP.length() > 0) {
                    auxiliar = -1;
                    editor = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor.putLong("auxiliar", auxiliar);
                    editor.commit();
                }
                else {
                    auxiliar = 0;
                    editor = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor.putLong("auxiliar", auxiliar);
                    editor.commit();
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        etUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etUser.length() > 0) {
                    auxiliar = -1;
                    editor = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor.putLong("auxiliar", auxiliar);
                    editor.commit();
                }
                else {
                    auxiliar = 0;
                    editor = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor.putLong("auxiliar", auxiliar);
                    editor.commit();
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        etPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPass.length() > 0) {
                    auxiliar = -1;
                    editor = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor.putLong("auxiliar", auxiliar);
                    editor.commit();
                }
                else {
                    auxiliar = 0;
                    editor = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor.putLong("auxiliar", auxiliar);
                    editor.commit();
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        etPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPort.length() > 0) {
                    auxiliar = -1;
                    editor = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor.putLong("auxiliar", auxiliar);
                    editor.commit();
                }
                else {
                    auxiliar = 0;
                    editor = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor.putLong("auxiliar", auxiliar);
                    editor.commit();
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        btnAceptarCam = (Button)v.findViewById(R.id.btnAceptarCam);
        btnAceptarCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Usar metodo comprobar campos de los otros fragments
                if(!comprobarCampos()) {
                    Toast.makeText(v.getContext(),"Camara Creada",Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = getContext().getSharedPreferences("camCreada",Context.MODE_PRIVATE).edit();
                    editor.putLong("auxCam", 1);
                    editor.commit();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contenedor, new CamarasFragment(), "Camaras");
                    fr.commit();
                    InputMethodManager mgr = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(etPort.getWindowToken(),0);
                    agregar(v);
                }
                else
                    Toast.makeText(v.getContext(),"Por favor complete los campos.",Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

    public void agregar(View v){
        if(!comprobarCampos()){
            String nombre,ip,user,pass,puerto;
            nombre = etNombreCam.getText().toString();
            ip = etIP.getText().toString();
            user = etUser.getText().toString();
            pass = etPass.getText().toString();
            puerto = etPort.getText().toString();


        }else{
            Toast.makeText(this.getActivity(),"hay campos vacios",Toast.LENGTH_LONG).show();
        }
        etNombreCam.requestFocus();
        etIP.setText("");
        etUser.setText("");
        etPass.setText("");
        etPort.setText("");
    }

    public boolean comprobarCampos() {
        if(etNombreCam.getText().toString().isEmpty() || etIP.getText().toString().isEmpty() || etUser.getText().toString().isEmpty() || etPass.getText().toString().isEmpty() || etPort.getText().toString().isEmpty()) {
            //Log.d("prueba", "paso por aca");
            return true;
        }
        else
            return false;
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
