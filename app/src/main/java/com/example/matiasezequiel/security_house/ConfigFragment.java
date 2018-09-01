package com.example.matiasezequiel.security_house;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class ConfigFragment extends Fragment {

    Spinner opciones;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_config, container, false);

        opciones = (Spinner) v.findViewById(R.id.spPrueba);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.opciones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.preference_category);
        opciones.setAdapter(adapter);

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
