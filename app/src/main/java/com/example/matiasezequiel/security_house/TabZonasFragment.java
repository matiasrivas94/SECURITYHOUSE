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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class TabZonasFragment extends Fragment {

    Button boton;
    EditText et1,et2,et3,et4,et5,et6;
    ListView list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_zonas, container, false);

        et1 = (EditText)v.findViewById(R.id.et1);
        et2 = (EditText)v.findViewById(R.id.et2);
        et3 = (EditText)v.findViewById(R.id.et3);
        et4 = (EditText)v.findViewById(R.id.et4);

        boton = (Button)v.findViewById(R.id.btnGuardarZonas);


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ComprobarCampos()) {
                    Toast.makeText(v.getContext(),"Zonas guardadas correctamente",Toast.LENGTH_LONG).show();
                    et1.setEnabled(false);
                    et2.setEnabled(false);
                    et3.setEnabled(false);
                    et4.setEnabled(false);
                }
                else
                    Toast.makeText(v.getContext(),"Hay zonas sin nombre, ingrese nuevamente",Toast.LENGTH_LONG).show();
            }
        });


        return v;
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
        }
    }

    public boolean ComprobarCampos(){
        if(et1.getText().toString().isEmpty() || et2.getText().toString().isEmpty() || et3.getText().toString().isEmpty() ||
                et4.getText().toString().isEmpty()){
            return false;
        }else{
            return true;
        }
    }
}
