package com.example.matiasezequiel.security_house;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class AlarmasFragment extends Fragment {

    ListView list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_alarmas, container, false);

        list = (ListView)v.findViewById(R.id.listview);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Casa");
        arrayList.add("Oficina");
        arrayList.add("Tienda");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(arrayAdapter);

        Button btnCrearAlarma =(Button)v.findViewById(R.id.btnNuevaAlarma);
        btnCrearAlarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.contenedor, new CrearAlarmaFragment()).addToBackStack(null);
                fr.commit();
            }
        });

        return v;
    }



}
