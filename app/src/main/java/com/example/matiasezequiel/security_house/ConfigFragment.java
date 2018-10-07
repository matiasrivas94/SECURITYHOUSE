package com.example.matiasezequiel.security_house;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.widget.Spinner;
import android.widget.Toast;


public class ConfigFragment extends Fragment {

    MiMensaje mm = new MiMensaje();
    Spinner opciones;
    String numero = "02664857207";
    String clave = "1234AP";
    //String numero = mm.idMensaje;
    String activar = clave + "AP";
    String desactivar = clave + "DS";
    String nuevaclave="0987";

    int tipoConfig;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_config, container, false);

        opciones = (Spinner) v.findViewById(R.id.spPrueba);

        //Shared para mostrar el tipo de la alarma en el spinner
        SharedPreferences prefs = getContext().getSharedPreferences("tipoAlarmaSpinner",Context.MODE_PRIVATE);
        tipoConfig = (int)prefs.getLong("configSpinner",-1);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.alarmas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.preference_category);
        opciones.setAdapter(adapter);


            //BOTON ACTIVAR
            Button btnActivar = (Button) v.findViewById(R.id.btnActivar);
            btnActivar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mm.enviarMensaje(numero, activar);
                }
            });

            //BOTON DESACTIVAR
            Button btnDesactivar = (Button) v.findViewById(R.id.btnDesactivar);
            btnDesactivar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mm.enviarMensaje(numero, desactivar);
                }
            });

            //BOTON CAMBIAR CLAVE
            Button btnCambiar = (Button) v.findViewById(R.id.btnCambiarClave);
            btnCambiar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mm.enviarMensaje("Clave Vieja" + clave + "CU", "Clave Nueva" + nuevaclave);
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
