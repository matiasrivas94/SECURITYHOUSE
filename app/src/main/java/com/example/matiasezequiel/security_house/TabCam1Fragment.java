package com.example.matiasezequiel.security_house;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.matiasezequiel.security_house.Aplication.BaseAplication;

import java.util.ArrayList;


public class TabCam1Fragment extends Fragment {

    VideoView v1;
    ImageView ivAddCam;
    TextView tvTituloCam;
    ListView lista;

    ArrayList<Camara> camaras;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_cam1, container, false);

        ivAddCam = (ImageView)v.findViewById(R.id.agregarCam1);
        tvTituloCam = (TextView)v.findViewById(R.id.tvTituloCamaras);



        ivAddCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View viewCamaras = getLayoutInflater().inflate(R.layout.lista_dialog_camaras, null);
                lista = (ListView)viewCamaras.findViewById(R.id.LVCamaras);

                builder.setView(viewCamaras);
                llenarLista();
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return v;
    }

    public void llenarLista(){
        camaras = ((BaseAplication) getActivity().getApplication()).nombresCamaras();
        Toast.makeText(this.getActivity(),"Camaras:"+camaras.size(),Toast.LENGTH_SHORT).show();
        if((camaras.size() == 0) || (camaras == null)) {
            //tvTituloCam.setVisibility(v.VISIBLE);
            AdapterLista ld = new AdapterLista();
            lista.setAdapter(ld);
        }else {
            //tvTituloCam.setVisibility(v.INVISIBLE);
            AdapterLista ld = new AdapterLista();
            lista.setAdapter(ld);
        }
    }

    private class AdapterLista extends ArrayAdapter<Camara>
    {
        public AdapterLista() {
            super(getActivity(), R.layout.items_listcam_dialog, camaras);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView=convertView;
            if (itemView == null){
                itemView= getLayoutInflater().inflate(R.layout.items_listcam_dialog, parent,false);
            }

            ImageView ivIconoCam = (ImageView) itemView.findViewById(R.id.ivIconoCamara);
            Button nombreCamara = (Button) itemView.findViewById(R.id.btNombreCam);
            ImageView ivCamActivada = (ImageView) itemView.findViewById(R.id.ivCamActivada);

            ArrayList<String> nombreCam = new ArrayList<>();
            for (int i = 0;i<camaras.size();i++){
                nombreCam.add (camaras.get(i).getNombre());
            }
            nombreCamara.setText(camaras.get(position).getNombre());

            //Boton que selecciona la camara que se va a reproducir
            nombreCamara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"toast para elegir la camara",Toast.LENGTH_SHORT).show();
                    /*usuarioSelecionado = position;
                    //Shared para saber el idAlarma al hacer click sobre el listView
                    Alarma alar = alarmas.get(usuarioSelecionado);
                    idAlarm = alar.getIdAlarma();*/

                    // COSAS DEL FRAGMENT
                    /*FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contenedor, new ZonasFragment(),"Zonas").addToBackStack(null);
                    fr.commit();*/

                }
            });
            return itemView;
        }
    }

}
