package com.example.matiasezequiel.security_house;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.microedition.khronos.egl.EGLDisplay;


public class ContactoFragment extends Fragment {

    EditText nombreContacto, emailContacto, mjeContacto;
    Button btnEnviar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contacto, container, false);

        nombreContacto = (EditText)v.findViewById(R.id.etNombreContacto);
        emailContacto = (EditText)v.findViewById(R.id.etEmail);
        mjeContacto = (EditText)v.findViewById(R.id.etMensajeContacto);

        //enviar email a securityhouse@gmail.com o a cada uno de los integrantes
        btnEnviar = (Button)v.findViewById(R.id.btnEnviarMensaje);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),
                        "Mensaje Enviado.", Toast.LENGTH_SHORT).show();
                nombreContacto.setText("");
                emailContacto.setText("");
                mjeContacto.setText("");
            }
        });
        return v;
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
