package com.example.matiasezequiel.security_house;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;


public class ConfigFragment extends Fragment {

    MiMensaje mm = new MiMensaje();
    Spinner opciones;
    String numero = "02664857207";
    String clave = "1234AP";
    //String numero = mm.idMensaje;
    String activar = clave + "AP";
    String desactivar = clave + "DS";
    String nuevaclave="0987";

    ImageView iv_desactivar, iv_activar, iv_cambiar, iv_estado, iv_volver, iv_salir;

    int tipoConfig;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_config, container, false);


            /*BOTON ACTIVAR
            iv_activar = (ImageView) v.findViewById(R.id.btnActivar);
            iv_activar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mm.enviarMensaje(numero, activar);
                }
            });*/

            /*BOTON DESACTIVAR
            iv_desactivar = (ImageView) v.findViewById(R.id.btnDesactivar);
            iv_desactivar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mm.enviarMensaje(numero, desactivar);
                }
            })*/

            /*BOTON CAMBIAR CLAVE
            iv_cambiar = (ImageView) v.findViewById(R.id.btnCambiarClave);
            iv_cambiar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mm.enviarMensaje("Clave Vieja" + clave + "CU", "Clave Nueva" + nuevaclave);
                }
            });*/


        iv_activar = (ImageView)v.findViewById(R.id.btnActivar);
        iv_activar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"ACTIVAR", Toast.LENGTH_SHORT).show();
            }
        });
        iv_desactivar = (ImageView)v.findViewById(R.id.btnDesactivar);
        iv_desactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"DESACTIVAR", Toast.LENGTH_SHORT).show();
            }
        });
        iv_cambiar = (ImageView)v.findViewById(R.id.btnCambiarClave);
        iv_cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"CAMBIAR", Toast.LENGTH_SHORT).show();
            }
        });
        iv_estado = (ImageView)v.findViewById(R.id.btnEstado);
        iv_estado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"ESTADO", Toast.LENGTH_SHORT).show();
            }
        });
        iv_volver = (ImageView)v.findViewById(R.id.btnAtras);
        iv_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"VOLVER", Toast.LENGTH_SHORT).show();
            }
        });
        iv_salir = (ImageView)v.findViewById(R.id.btnSalir);
        iv_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"SALIR", Toast.LENGTH_SHORT).show();
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
