package com.example.matiasezequiel.security_house;

import android.content.Context;
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

public class CrearCamaraFragment extends Fragment {

    EditText etIP, etUser, etPass, etPort;
    Button btnCrearCamara;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crear_camara, container, false);

        etIP = (EditText)v.findViewById(R.id.etIP);
        etUser = (EditText)v.findViewById(R.id.etUsuario);
        etPass = (EditText)v.findViewById(R.id.etPass);
        etPort = (EditText)v.findViewById(R.id.etPuerto);
        btnCrearCamara = (Button)v.findViewById(R.id.btnCrearCam);
                btnCrearCamara.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(),"Camara Creada",Toast.LENGTH_LONG).show();
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
