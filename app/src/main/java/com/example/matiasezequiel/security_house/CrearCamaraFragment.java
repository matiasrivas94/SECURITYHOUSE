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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.matiasezequiel.security_house.Aplication.BaseAplication;

public class CrearCamaraFragment extends Fragment {

    EditText etNombreCam, etIP, etUser, etPass;
    Spinner spPuerto;
    Button btnAceptarCam;
    Activity a = (Activity)getActivity();
    MainActivity ma = (MainActivity)getActivity();
    int auxiliar = 0,modicamara;
    SharedPreferences.Editor editor;
    String estadoEditarCamara="default";
    SharedPreferences prefs4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crear_camara, container, false);

        etNombreCam = (EditText)v.findViewById(R.id.etNombreCam);
        etIP = (EditText)v.findViewById(R.id.etIP);
        etUser = (EditText)v.findViewById(R.id.etUsuario);
        etPass = (EditText)v.findViewById(R.id.etPass);

        spPuerto = (Spinner) v.findViewById(R.id.SPPuerto);
        String[] puerto = {"554"};
        spPuerto.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, puerto));


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

        //Shared para modificar la camara de camarasFragment
        SharedPreferences prefs1 = getContext().getSharedPreferences("deleteCamList",Context.MODE_PRIVATE);
        modicamara = (int)prefs1.getLong("deleteCamLista",-1);

        //shared para editar la camara segun el click en la lista de las camaras
        prefs4 = getContext().getSharedPreferences("cadenaEditarCamara",Context.MODE_PRIVATE);
        estadoEditarCamara=prefs4.getString("editarStringCamara"," ");

        btnAceptarCam = (Button)v.findViewById(R.id.btnAceptarCam);
        btnAceptarCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Usar metodo comprobar campos de los otros fragments
                if(estadoEditarCamara.equals("insert")) {
                    if (!comprobarCampos()) {
                        SharedPreferences.Editor editor = getContext().getSharedPreferences("camCreada", Context.MODE_PRIVATE).edit();
                        editor.putLong("auxCam", 1);
                        editor.commit();
                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.contenedor, new CamarasFragment(), "Camaras");
                        fr.commit();
                        InputMethodManager mgr = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.hideSoftInputFromWindow(etPass.getWindowToken(), 0);
                        agregar(v);
                    } else {
                        Toast.makeText(v.getContext(), "Por favor complete los campos.", Toast.LENGTH_LONG).show();
                    }
                    prefs4.edit().remove("editarStringCamara").commit();
                }
                if(estadoEditarCamara.equals("update")){
                    editar(v);
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contenedor, new CamarasFragment(), "Camaras");
                    fr.commit();
                    InputMethodManager mgr = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(etPass.getWindowToken(), 0);
                }
            }
        });

        if(estadoEditarCamara.equals("update")){
            btnAceptarCam.setText("Modificar");
            reflejarCampos();
            prefs4.edit().remove("editarStringCamara").commit();
        }

        return v;
    }

    public void agregar(View v){
        if(!comprobarCampos()){
            //inserto la camara
            try{
                boolean res =((BaseAplication)this.getActivity().getApplication()).insertarCamara(etNombreCam.getText().toString(),etIP.getText().toString(),etUser.getText().toString(),etPass.getText().toString(),Integer.parseInt(spPuerto.getSelectedItem().toString()));
                if(res){
                    Toast.makeText(v.getContext(), "Cámara Creada", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(v.getContext(), "Error al ingresar Alarma", Toast.LENGTH_LONG).show();
                }
            }catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this.getActivity(),"hay campos vacios",Toast.LENGTH_LONG).show();
        }
        etNombreCam.requestFocus();
        etIP.setText("");
        etUser.setText("");
        etPass.setText("");
        spPuerto.setSelection(0);
    }

    public boolean comprobarCampos() {
        if(etNombreCam.getText().toString().isEmpty() || etIP.getText().toString().isEmpty() || etUser.getText().toString().isEmpty() || etPass.getText().toString().isEmpty()) {
            return true;
        }
        else
            return false;
    }

    //metodos para mostrar datos en los campos y editarlos
    public void reflejarCampos(){
        //inserto la camara
        try{
            Camara c =((BaseAplication)getActivity().getApplication()).getCamara(modicamara);
            etNombreCam.setText(c.getNombre());
            etIP.setText(c.getIp());
            etUser.setText(c.getUsuario());
            etPass.setText(c.getContraseña());
            spPuerto.setSelection(0);
        }catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
    }
    public void editar(View v){
        int puerto = spPuerto.getSelectedItemPosition();
        try{
            long response = ((BaseAplication)getActivity().getApplication()).updateCamara(modicamara,etNombreCam.getText().toString(),etIP.getText().toString(),etUser.getText().toString(),etPass.getText().toString(),puerto);
            if(response>0){
                Toast.makeText(this.getActivity(),"Editado con exito",Toast.LENGTH_LONG).show();
                etNombreCam.requestFocus();
                etIP.setText("");
                etUser.setText("");
                etPass.setText("");
                spPuerto.setSelection(0);
            }else{
                Toast.makeText(this.getActivity(),"Ocurrio un error",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
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
