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


public class AlarmasFragment extends Fragment {
    //cosas del ListView Alarmas
    private ListView lista;
    TextView tvTitulo;

    //nuevo
    ArrayList<Alarma> alarmas;

    // cosas de la alarma al hacer click ena alarma de la lista
    private int usuarioSelecionado = -1;
    int idAlarm=0,deleteAlarm=0,deleteAlarmLista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_alarmas, container, false);

        lista = (ListView) v.findViewById(R.id.LVMostrar);
        tvTitulo = (TextView)v.findViewById(R.id.tvTitulo);

        llenarLista();

        return v;
    }

    public void llenarLista(){
        alarmas = ((BaseAplication) getActivity().getApplication()).nombresAlarmas();
        if((alarmas.size() == 0) || (alarmas == null)) {
            tvTitulo.setVisibility(View.VISIBLE);
            AdapterLista ld = new AdapterLista();
            lista.setAdapter(ld);
        }else {
            tvTitulo.setVisibility(View.INVISIBLE);
            AdapterLista ld = new AdapterLista();
            lista.setAdapter(ld);
        }
    }

    //clase interna para manejar el item de la lista de las alarmas
    private class AdapterLista extends ArrayAdapter<Alarma>
    {
        public AdapterLista() {
            super(getActivity(), R.layout.items_listview, alarmas);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView=convertView;
            if (itemView == null){
                itemView= getLayoutInflater().inflate(R.layout.items_listview, parent,false);
            }
            Alarma itemActual= alarmas.get(position);

            Button nombreAlarma = (Button) itemView.findViewById(R.id.btNombreAlarma);
            Button iconoMenu = (Button) itemView.findViewById(R.id.btIconoMenu);
            ImageView iconoAlarma = (ImageView) itemView.findViewById(R.id.ivIconoAlarma);

            //Conexion a la Base de datos para la tabla Alarmas
            ArrayList<String> tipoAlarma = new ArrayList<>();
            for (int i = 0;i<alarmas.size();i++){
                tipoAlarma.add (alarmas.get(i).getTipo());
            }
            if(tipoAlarma.get(position).equals("Casa")){
                iconoAlarma.setImageResource(R.drawable.home);
            }
            if(tipoAlarma.get(position).equals("Oficina")){
                iconoAlarma.setImageResource(R.drawable.busines);
            }
            if(tipoAlarma.get(position).equals("Tienda")){
                iconoAlarma.setImageResource(R.drawable.store);
            }

            nombreAlarma.setText(itemActual.getNombre());

            //Boton del menu del item de la lista de las alarmas
            iconoMenu.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    usuarioSelecionado = position;
                    //Shared para saber el idAlarma al hacer click sobre el listView
                    Alarma alar = alarmas.get(usuarioSelecionado);
                    idAlarm = alar.getIdAlarma();

                    //Shared para quedarme con la posicion de la lista al seleccionar una alarma
                    SharedPreferences.Editor editor1 = getContext().getSharedPreferences("deleteAlarmList",Context.MODE_PRIVATE).edit();
                    editor1.putLong("deleteAlarLista", usuarioSelecionado);
                    editor1.commit();


                    //Shared para eliminar una alarma con el menu contextual
                    SharedPreferences.Editor editor = getContext().getSharedPreferences("eliminarAlarma",Context.MODE_PRIVATE).edit();
                    editor.putLong("elimAlarm", idAlarm);
                    editor.commit();


                    // Menu con alert builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    //builder.setTitle("Elegir Opcion");
                    builder.setTitle("Elegir Opción");
                    // add a list
                    String[] itemsAlarma = {"Modificar Alarma", "Eliminar Alarma", "Configuracion General"};
                    builder.setItems(itemsAlarma, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    //Shared para editar la alarma
                                    SharedPreferences.Editor editor2 = getContext().getSharedPreferences("cadenaEditar",Context.MODE_PRIVATE).edit();
                                    editor2.putString ("editarString","update");
                                    editor2.commit();

                                    FragmentTransaction fr1 = getFragmentManager().beginTransaction();
                                    fr1.replace(R.id.contenedor, new CrearAlarmaFragment(),"CrearAlarma").addToBackStack(null);
                                    fr1.commit();
                                    break;
                                case 1:
                                    //Abrir un alertDialog preguntando si desea cancelar
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Confirmar! \n");
                                    builder.setTitle("Eliminar la Alarma");

                                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //Shared para quedarme con el idAlarma seleccionada para eliminar
                                            SharedPreferences prefs1 = getContext().getSharedPreferences("deleteAlarmList", Context.MODE_PRIVATE);
                                            deleteAlarmLista = (int) prefs1.getLong("deleteAlarLista", -1);

                                            //Shared para quedarme con la posicion de la lista de la alarma seleccionada para aliminar
                                            SharedPreferences prefs = getContext().getSharedPreferences("eliminarAlarma", Context.MODE_PRIVATE);
                                            deleteAlarm = (int) prefs.getLong("elimAlarm", -1);

                                            Alarma alarm = alarmas.get(deleteAlarmLista);
                                            boolean response = ((BaseAplication) getActivity().getApplication()).borrarAlarma(alarm.getIdAlarma());
                                            if (response) {
                                                Toast.makeText(getActivity(), "Eliminado con exito", Toast.LENGTH_LONG).show();
                                                alarmas.removeAll(alarmas);
                                                llenarLista();
                                            } else {
                                                Toast.makeText(getActivity(), "Fallo", Toast.LENGTH_LONG).show();
                                            }
                                        }});
                                    builder.create();
                                    builder.show();
                                    break;
                                case 2:
                                    //shared para mostrar el tipo de la alarma en el spinner de configuracion
                                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                                    fr.replace(R.id.contenedor, new ConfigFragment(),"Configuracion").addToBackStack(null);
                                    fr.commit();
                            }
                        }
                    });
                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            //Boton del menu del item de las alarmas que maneja el nombre para llevar al
            //fragmentTabZonas para ver las zonas que tiene cada alarma de la lista
            nombreAlarma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    usuarioSelecionado = position;
                    //Shared para saber el idAlarma al hacer click sobre el listView
                    Alarma alar = alarmas.get(usuarioSelecionado);
                    idAlarm = alar.getIdAlarma();
                    String nomA = alar.getNombre();

                    //Shareds para el fragment tabZonas
                    SharedPreferences.Editor editor = getContext().getSharedPreferences("cc",Context.MODE_PRIVATE).edit();
                    editor.putLong("idAlarma", idAlarm);
                    editor.commit();
                    //Shareds para el fragment principal
                    SharedPreferences.Editor editor1 = getContext().getSharedPreferences("ee",Context.MODE_PRIVATE).edit();
                    editor1.putString("nombreAlarma", nomA);
                    editor1.commit();

                    // COSAS DEL FRAGMENT
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contenedor, new ZonasFragment(),"Zonas").addToBackStack(null);
                    fr.commit();
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
        MainActivity mainActivity = (MainActivity) getActivity();
        if (!getUserVisibleHint()) {
            mainActivity.showFloatingActionButton(); //fuerza la visibilidad
        }
        if (mainActivity != null) {
            mainActivity.showFloatingActionButton(); //fuerza la visibilidad
            FloatingActionButton fab = mainActivity.findViewById(R.id.fab);
            fab.setImageResource(R.drawable.add3); //Cambiar icono
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Shared para editar la alarma
                    SharedPreferences.Editor editor2 = getContext().getSharedPreferences("cadenaEditar",Context.MODE_PRIVATE).edit();
                    editor2.putString ("editarString","insert");
                    editor2.commit();

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contenedor, new CrearAlarmaFragment(),"CrearAlarma").addToBackStack(null);
                    fr.commit();
                }
            });
        }
    }
}
