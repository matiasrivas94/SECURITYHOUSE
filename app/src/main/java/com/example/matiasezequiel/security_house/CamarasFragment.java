package com.example.matiasezequiel.security_house;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matiasezequiel.security_house.Aplication.BaseAplication;

import java.util.ArrayList;


public class CamarasFragment extends Fragment {

    ListView lvCamaras;
    ArrayList<Camara> camaras;
    TextView tvTitulo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_camaras, container, false);

         tvTitulo = (TextView)v.findViewById(R.id.tvTituloCamaras);
        lvCamaras = (ListView)v.findViewById(R.id.ListViewCamaras);
        llenarLista();

        return v;
    }

    public void llenarLista(){
        camaras = ((BaseAplication) getActivity().getApplication()).nombresCamaras();
        //Toast.makeText(this.getActivity(),"Alarmas:"+alarmas.size(),Toast.LENGTH_SHORT).show();
        if((camaras.size() == 0) || (camaras == null)) {
            tvTitulo.setVisibility(View.VISIBLE);
            AdapterLista ld = new AdapterLista();
            lvCamaras.setAdapter(ld);
        }else {
            tvTitulo.setVisibility(View.INVISIBLE);
            AdapterLista ld = new AdapterLista();
            lvCamaras.setAdapter(ld);
        }
    }

    private class AdapterLista extends ArrayAdapter<Camara> {
        public AdapterLista() {
            super(getActivity(), R.layout.items_listcam, camaras);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.items_listcam, parent, false);
            }

            ImageView ivIconoCam = (ImageView) itemView.findViewById(R.id.ivIconoCamara);
            Button nombreCamara = (Button) itemView.findViewById(R.id.btNombreCam);
            Button ivIconoMenuCam = (Button) itemView.findViewById(R.id.btIconoMenuCam);

            ArrayList<String> nombreCam = new ArrayList<>();
            for (int i = 0; i < camaras.size(); i++) {
                nombreCam.add(camaras.get(i).getNombre());
            }
            nombreCamara.setText(camaras.get(position).getNombre());

            //Boton que selecciona la camara que se va a reproducir
            ivIconoMenuCam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Menu para elimianr y modificar camara", Toast.LENGTH_SHORT).show();
                }
            });
            return itemView;
        }
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
            mainActivity.showFloatingActionButton(); //fuerza la visibilidad
            FloatingActionButton fab = mainActivity.findViewById(R.id.fab);
            fab.setImageResource(R.drawable.ic_add_camera);
            //fab.setImageResource(R.drawable.ic_send_black_24dp); //Cambiar icono
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contenedor, new CrearCamaraFragment(),"CrearCamara");
                    fr.commit();
                }
            });
        }
    }
}
