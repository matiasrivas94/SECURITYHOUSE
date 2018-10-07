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

    EditText nombreContacto, emailContacto, asuntoContacto, mjeContacto;
    Button btnEnviar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contacto, container, false);


        nombreContacto = (EditText)v.findViewById(R.id.etNombreContacto);
        emailContacto = (EditText)v.findViewById(R.id.etEmail);
        asuntoContacto = (EditText)v.findViewById(R.id.etAsunto);
        mjeContacto = (EditText)v.findViewById(R.id.etMensajeContacto);
        btnEnviar = (Button)v.findViewById(R.id.btnEnviarMensaje);

        //enviar email a securityhouse@gmail.com

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombreContacto.getText().toString().isEmpty() || emailContacto.getText().toString().isEmpty() ||
                        asuntoContacto.getText().toString().isEmpty() || mjeContacto.getText().toString().isEmpty())
                {
                    Toast.makeText (getContext(),"Hay Campos Vacios",Toast.LENGTH_LONG).show();
                }
                else{
                    enviarMail();
                }
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

    public void enviarMail(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { "sh2018app@gmail.com" });
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_REFERRER_NAME,nombreContacto.getText().toString());
        intent.putExtra(Intent.EXTRA_SUBJECT, asuntoContacto.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT,mjeContacto.getText().toString());
        try {
            startActivity(intent.createChooser(intent,"Correo Enviado a Security House"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
