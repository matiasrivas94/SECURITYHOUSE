package com.example.matiasezequiel.security_house;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matiasezequiel.security_house.Aplication.BaseAplication;

import java.util.ArrayList;


public class CamarasFragment extends Fragment {

    ListView lvCamaras;
    ArrayList<Camara> camaras;
    TextView tvTitulo;

    int camaraSeleccionada,idCamara=0,deleteCamList;
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
        try{
            camaras = ((BaseAplication) getActivity().getApplication()).nombresCamaras();
            if((camaras.size() == 0) || (camaras == null)) {
                tvTitulo.setVisibility(View.VISIBLE);
                AdapterLista ld = new AdapterLista();
                lvCamaras.setAdapter(ld);
            }else {
                tvTitulo.setVisibility(View.INVISIBLE);
                AdapterLista ld = new AdapterLista();
                lvCamaras.setAdapter(ld);
            }
        }catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
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
                    camaraSeleccionada = position;
                    //Shared para saber el idAlarma al hacer click sobre el listView
                    Camara cam = camaras.get(camaraSeleccionada);
                    idCamara = cam.getIdCamara();

                    //Shared para quedarme con la posicion de la lista al seleccionar una camara
                    SharedPreferences.Editor editor1 = getContext().getSharedPreferences("deleteCamList", Context.MODE_PRIVATE).edit();
                    editor1.putLong("deleteCamLista", idCamara);
                    editor1.commit();

                    // Menu con alert builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Elegir Opción");
                    // add a list
                    String[] itemsCamara = {"Reproducir","Modificar Camara", "Eliminar Camara"};
                    builder.setItems(itemsCamara, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    SharedPreferences.Editor editor1 = getContext().getSharedPreferences("ReproCamList", Context.MODE_PRIVATE).edit();
                                    editor1.putLong("reproCamLista", idCamara);
                                    editor1.commit();

                                    FragmentTransaction fr2 = getFragmentManager().beginTransaction();
                                    fr2.replace(R.id.contenedor, new ReproducirFragment(),"Reproducir");
                                    fr2.commit();
                                    break;
                                case 1:
                                    //Shared para editar la alarma
                                    SharedPreferences.Editor editor2 = getContext().getSharedPreferences("cadenaEditarCamara", Context.MODE_PRIVATE).edit();
                                    editor2.putString ("editarStringCamara","update");
                                    editor2.commit();

                                    FragmentTransaction fr1 = getFragmentManager().beginTransaction();
                                    fr1.replace(R.id.contenedor, new CrearCamaraFragment(),"CrearCamara");
                                    fr1.commit();
                                    break;
                                case 2:
                                    //Abrir un alertDialog preguntando si desea cancelar
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Confirmar! \n");
                                    builder.setTitle("Eliminar la Camara");

                                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //Shared para quedarme con el idCamara seleccionada para eliminar
                                            SharedPreferences prefs1 = getContext().getSharedPreferences("deleteCamList", Context.MODE_PRIVATE);
                                            deleteCamList = (int) prefs1.getLong("deleteCamLista", -1);
                                            try{
                                                boolean response = ((BaseAplication) getActivity().getApplication()).borrarCamara(deleteCamList);
                                                if (response) {
                                                    Toast.makeText(getActivity(), "Eliminado con exito", Toast.LENGTH_LONG).show();
                                                    camaras.removeAll(camaras);
                                                    llenarLista();
                                                } else {
                                                    Toast.makeText(getActivity(), "Fallo", Toast.LENGTH_LONG).show();
                                                }
                                            }catch (Exception e) {
                                                Log.e("Error", e.getMessage());
                                                e.printStackTrace();
                                            }
                                        }});
                                    builder.create();
                                    builder.show();
                                    break;
                            }
                        }
                    });
                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();


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

                    //Shared para editar la alarma
                    SharedPreferences.Editor editor2 = getContext().getSharedPreferences("cadenaEditarCamara",Context.MODE_PRIVATE).edit();
                    editor2.putString ("editarStringCamara","insert");
                    editor2.commit();

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contenedor, new CrearCamaraFragment(),"CrearCamara");
                    fr.commit();
                }
            });
        }
    }
}
