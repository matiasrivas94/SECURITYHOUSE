package com.example.matiasezequiel.security_house;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CrearCamaraFragment extends Fragment {

    EditText etIP, etUser, etPass, etPort;
    Button btnAceptarCam, btnCancelarCam;
    Activity a = (Activity)getActivity();
    MainActivity ma = (MainActivity)getActivity();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crear_camara, container, false);

        etIP = (EditText)v.findViewById(R.id.etIP);
        etUser = (EditText)v.findViewById(R.id.etUsuario);
        etPass = (EditText)v.findViewById(R.id.etPass);
        etPort = (EditText)v.findViewById(R.id.etPuerto);
        btnAceptarCam = (Button)v.findViewById(R.id.btnAceptarCam);
        btnAceptarCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Usar metodo comprobar campos de los otros fragments
                if(!comprobarCampos()) {
                    Toast.makeText(v.getContext(),"Camara Creada",Toast.LENGTH_LONG).show();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contenedor, new PrincipalFragment(), "Principal");
                    fr.commit();
                }
                else
                    Toast.makeText(v.getContext(),"Por favor complete los campos.",Toast.LENGTH_LONG).show();
            }
        });
        btnCancelarCam = (Button)v.findViewById(R.id.btnCancelarCam);
                btnCancelarCam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Controlar que no haya escrito nada para no perder los datos
                        //Abrir un alertDialog preguntando si desea cancelar
                        if(comprobarCampos2()) {
                            FragmentTransaction fr = getFragmentManager().beginTransaction();
                            fr.replace(R.id.contenedor, new PrincipalFragment(), "Principal");
                            fr.commit();
                        }
                        else{
                            Log.d("prueba2","comprobar true");
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Tus datos no se guardaron. \n Estas seguro que quieres cancelar?");
                            builder.setTitle(Html.fromHtml("<font color='#000000'>Configurar cámara IP</font>"));
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                                    fr.replace(R.id.contenedor, new PrincipalFragment(), "Principal");
                                    fr.commit();
                                }
                            });

                            builder.create();
                            builder.show();
                        }


                    }
                });


        return v;
    }

    public boolean comprobarCampos() {
        if(etIP.getText().toString().isEmpty() || etUser.getText().toString().isEmpty() || etPass.getText().toString().isEmpty() || etPort.getText().toString().isEmpty()) {
            Log.d("prueba", "paso por aca");
            return true;
        }
        else
            return false;
    }

    public boolean comprobarCampos2() {
        if(!etIP.getText().toString().isEmpty() || !etUser.getText().toString().isEmpty() || !etPass.getText().toString().isEmpty() || !etPort.getText().toString().isEmpty()) {
            Log.d("prueba", "paso por el 2");
            return false;
        }
        else
        {

            Log.d("prueba","2.2");
            return true;

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
