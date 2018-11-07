package com.example.matiasezequiel.security_house;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.matiasezequiel.security_house.Aplication.BaseAplication;


public class ConfigFragment extends Fragment {

    MiMensaje mm = new MiMensaje();
    String numero, clave="1234", activar, desactivar, nuevaclave, claveBase, cambiarClave, estado;
    int idAlarmaConfig, idAlarma;
    ImageView iv_desactivar, iv_activar, iv_cambiar, iv_estado, iv_volver, iv_salir;
    EditText etClaveDialog, etClaveVieja, etClaveNueva;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_config, container, false);

        //Shared para quedarme con el idAlarma seleccionada para modificar
        SharedPreferences prefs1 = getContext().getSharedPreferences("eliminarAlarma",Context.MODE_PRIVATE);
        idAlarmaConfig = (int)prefs1.getLong("elimAlarm",-1);

        Log.d("idalarma", String.valueOf(idAlarmaConfig));
        Alarma alarm = ((BaseAplication) getActivity().getApplication()).getAlarma(idAlarmaConfig);
        numero = alarm.getNumTelefono();
        claveBase = alarm.getClave();
        activar = clave + "AP";
        desactivar = clave + "DP";
        estado = clave + "ES";

        iv_activar = (ImageView)v.findViewById(R.id.btnActivar);
        iv_activar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ACTIVAR(clave+AP) = 1234AP
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View vistaClave = getLayoutInflater().inflate(R.layout.dialog_clave, null);
                final Button cancelarSMS = (Button)vistaClave.findViewById(R.id.btnCancelarSMS);
                final Button enviarSMS = (Button)vistaClave.findViewById(R.id.btnEnviarSMS);
                builder.setView(vistaClave);
                final AlertDialog dialog = builder.create();
                cancelarSMS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                enviarSMS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etClaveDialog = vistaClave.findViewById(R.id.etClaveAlarma);
                        clave = etClaveDialog.getText().toString();
                        if(claveBase.equals(clave)){
                            //mm.enviarMensaje(numero, activar);
                            Toast.makeText(getContext(),"Enviando sms: "+ activar, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(getContext(), "Clave Incorrecta", Toast.LENGTH_SHORT).show();
                            etClaveDialog.setSelectAllOnFocus(true);
                        }
                    }
                });
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            }
        });

        iv_desactivar = (ImageView)v.findViewById(R.id.btnDesactivar);
        iv_desactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View vistaClave = getLayoutInflater().inflate(R.layout.dialog_clave, null);
                final Button cancelarSMS = (Button)vistaClave.findViewById(R.id.btnCancelarSMS);
                final Button enviarSMS = (Button)vistaClave.findViewById(R.id.btnEnviarSMS);
                enviarSMS.setText("Desactivar Panel");
                enviarSMS.setWidth(150);
                builder.setView(vistaClave);
                final AlertDialog dialog = builder.create();
                cancelarSMS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                enviarSMS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etClaveDialog = vistaClave.findViewById(R.id.etClaveAlarma);
                        clave = etClaveDialog.getText().toString();
                        if(claveBase.equals(clave)){
                            //mm.enviarMensaje(numero, activar);
                            Toast.makeText(getContext(),"Enviando sms: "+desactivar, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(getContext(), "Clave Incorrecta", Toast.LENGTH_SHORT).show();
                            etClaveDialog.setSelectAllOnFocus(true);
                        }
                    }
                });
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            }
        });
        iv_cambiar = (ImageView)v.findViewById(R.id.btnCambiarClave);
        iv_cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View vistaClave = getLayoutInflater().inflate(R.layout.dialog_cambiar_clave, null);
                final Button cancelarSMS = (Button)vistaClave.findViewById(R.id.btnCancelarSMS);
                final Button enviarSMS = (Button)vistaClave.findViewById(R.id.btnEnviarSMS);
                builder.setView(vistaClave);
                final AlertDialog dialog = builder.create();

                etClaveVieja = vistaClave.findViewById(R.id.etClaveVieja);
                etClaveNueva = vistaClave.findViewById(R.id.etClaveNueva);
                etClaveNueva.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {
                            clave = etClaveVieja.getText().toString();
                            //Toast.makeText(getContext().getApplicationContext(), clave, Toast.LENGTH_LONG).show();
                            if(!claveBase.equals(clave)){
                                etClaveVieja.setError("La clave es incorrecta");
                                etClaveVieja.setSelectAllOnFocus(true);
                                etClaveVieja.requestFocus();
                            }
                        }
                    }
                });

                cancelarSMS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                enviarSMS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etClaveNueva = vistaClave.findViewById(R.id.etClaveNueva);
                        cambiarClave = clave + "CU" + etClaveNueva.getText().toString();
                        if(claveBase.equals(clave)){
                            Toast.makeText(getContext(),"Enviando sms: "+cambiarClave, Toast.LENGTH_SHORT).show();
                            //mm.enviarMensaje(numero, cambiarClave);
                            dialog.dismiss();
                        }
                        else
                            Toast.makeText(getContext(),"Clave Incorrecta", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            }
        });
        iv_estado = (ImageView)v.findViewById(R.id.btnEstado);
        iv_estado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                builder.setTitle("Desea consultar el estado de la alarma?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), estado, Toast.LENGTH_SHORT).show();
                    }
                });
                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            }
        });
        iv_volver = (ImageView)v.findViewById(R.id.btnAtras);
        iv_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.contenedor, new AlarmasFragment(),"Alarmas");
                fr.commit();
            }
        });
        iv_salir = (ImageView)v.findViewById(R.id.btnSalir);
        iv_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                builder.setTitle("Desea Salir de la Aplicacion?");
                String[] opcion = {"Salir", "Cancelar"};
                builder.setItems(opcion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                getActivity().finish();
                                break;
                            case 1:
                                dialog.cancel();
                                break;
                        }
                    }
                });
                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
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

