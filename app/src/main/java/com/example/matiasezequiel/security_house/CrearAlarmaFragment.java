package com.example.matiasezequiel.security_house;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matiasezequiel.security_house.Aplication.BaseAplication;

import java.util.ArrayList;


public class CrearAlarmaFragment extends Fragment {

    MiMensaje mm = new MiMensaje();
    EditText nombre, numTelefono, clave;
    MainActivity mainActivity = (MainActivity)getActivity();
    int auxiliar = 0;
    int modiAlarma;
    String estadoEditarAlarma="default";
    SharedPreferences prefs4;
    SharedPreferences.Editor editor1;

    //Cosas del Spinner
    Spinner opciones;
    private ArrayList<DatosItemZona> mZonas;
    private AdaptadorZonas mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crear_alarma, container, false);


        //cosas del SPINNER
        initList();
        opciones = (Spinner) v.findViewById(R.id.SPTipo);
        mAdapter = new AdaptadorZonas(this.getActivity(),mZonas);
        opciones.setAdapter(mAdapter);

        nombre = (EditText) v.findViewById(R.id.ETNombre);
        opciones = (Spinner) v.findViewById(R.id.SPTipo);
        numTelefono = (EditText) v.findViewById(R.id.ETNumTelefono);
        clave = (EditText) v.findViewById(R.id.ETClave);

        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (nombre.length() > 0) {
                    auxiliar = -1;
                    editor1 = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor1.putLong("auxiliar", auxiliar);
                    editor1.commit();
                }
                else {
                    auxiliar = 0;
                    editor1 = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor1.putLong("auxiliar", auxiliar);
                    editor1.commit();
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        numTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (numTelefono.length() > 0) {
                    auxiliar = -1;
                    editor1 = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor1.putLong("auxiliar", auxiliar);
                    editor1.commit();
                }
                else {
                    auxiliar = 0;
                    editor1 = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor1.putLong("auxiliar", auxiliar);
                    editor1.commit();
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        clave.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (clave.length() > 0) {
                    auxiliar = -1;
                    editor1 = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor1.putLong("auxiliar", auxiliar);
                    editor1.commit();
                }
                else {
                    auxiliar = 0;
                    editor1 = getContext().getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                    editor1.putLong("auxiliar", auxiliar);
                    editor1.commit();
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        //Shared para quedarme con el idAlarma seleccionada para modificar
        SharedPreferences prefs1 = getContext().getSharedPreferences("eliminarAlarma",Context.MODE_PRIVATE);
        modiAlarma = (int)prefs1.getLong("elimAlarm",-1);

        //Shared para modificar la alarma de alarmasFragment
        prefs4 = getContext().getSharedPreferences("cadenaEditar",Context.MODE_PRIVATE);
        estadoEditarAlarma=prefs4.getString("editarString"," ");


        Button btnCrearAlarma =(Button)v.findViewById(R.id.btnCrearAlarma);

            btnCrearAlarma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(estadoEditarAlarma.equals("insert")) {
                        if (ComprobarCampos()) {
                            ZonasFragment.agregando=1;
                            FragmentTransaction fr = getFragmentManager().beginTransaction();
                            fr.replace(R.id.contenedor, new ZonasFragment(), "Zonas");
                            fr.commit();
                            InputMethodManager mgr = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            mgr.hideSoftInputFromWindow(clave.getWindowToken(),0);
                            agregar(v);
                        } else {
                            Toast.makeText(v.getContext(), "Hay campos vacios, por favor ingrese datos", Toast.LENGTH_LONG).show();
                        }
                        prefs4.edit().remove("editarString").commit();
                    }
                    if(estadoEditarAlarma.equals("update")){
                        editar(v);
                        ZonasFragment.agregando=0;
                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.contenedor, new AlarmasFragment(), "Alarmas");
                        fr.commit();
                    }
                }
            });

        if(estadoEditarAlarma.equals("update")){
            btnCrearAlarma.setText("Modificar");
            reflejarCampos();
            prefs4.edit().remove("editarString").commit();
        }

        return v;
    }

    public void initList(){
        mZonas = new ArrayList<>();
        mZonas.add(new DatosItemZona(0,"Casa",R.drawable.home));
        mZonas.add(new DatosItemZona(1,"Oficina",R.drawable.busines));
        mZonas.add(new DatosItemZona(2,"Tienda",R.drawable.store));
    }

    //clase interna para manejar el item de la lista de las alarmas
    public class AdaptadorZonas extends ArrayAdapter<DatosItemZona> {

        public AdaptadorZonas(Context contexto, ArrayList<DatosItemZona> listaObjetos) {
            super(contexto,0, listaObjetos);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return initView(position, convertView, parent);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return initView(position, convertView, parent);
        }

        private View initView(int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.items_spinner_tipo_alarmas,parent,false);
            }
            ImageView iconoZona = (ImageView)convertView.findViewById(R.id.ivIconoZona);
            TextView tipoZona = (TextView)convertView.findViewById(R.id.tvTipoZona);

            DatosItemZona currentItem = getItem(position);

            if(currentItem != null) {
                iconoZona.setImageResource(currentItem.getImagen());
                tipoZona.setText(currentItem.getNombreZona());
            }
            return convertView;
        }
    }

    public void agregar(View v){
        if(ComprobarCampos()){
            int ipo = opciones.getSelectedItemPosition();

            //inserto la alarma
            boolean res =((BaseAplication)this.getActivity().getApplication()).insertarAlarma(nombre.getText().toString(),mZonas.get(ipo).nombreZona.toString(),numTelefono.getText().toString(),clave.getText().toString());
            if(res){
                Toast.makeText(v.getContext(), "Alarma Creada", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(v.getContext(), "Error al ingresar Alarma", Toast.LENGTH_LONG).show();
            }

            //Shared para el string de la alrma ingresada
            SharedPreferences.Editor editor2 = getContext().getSharedPreferences("dd",Context.MODE_PRIVATE).edit();
            editor2.putString ("estadoZonaString","sapeeeeeeeee");
            editor2.commit();

            //Shared para el nombre de la alarma
            SharedPreferences.Editor editor = getContext().getSharedPreferences("ee",Context.MODE_PRIVATE).edit();
            editor.putString ("nombreAlarma",nombre.getText().toString());
            editor.commit();

        }else{
            Toast.makeText(this.getActivity(),"hay campos vacios",Toast.LENGTH_LONG).show();
        }
        nombre.requestFocus();
        nombre.setText("");
        numTelefono.setText("");
        opciones.setSelection(0);
        clave.setText("");
    }
    public boolean ComprobarCampos(){
        if(nombre.getText().toString().isEmpty() || numTelefono.getText().toString().isEmpty() || clave.getText().toString().isEmpty()) {
            if(nombre.getText().toString().isEmpty())
            {
                nombre.setError("Escriba el nombre de la alarma");
            }
            if(numTelefono.getText().toString().isEmpty())
            {
                numTelefono.setError("Escriba el numero de telefono");
            }
            if(clave.getText().toString().isEmpty())
            {
                clave.setError("Escriba la clave");
            }
            return false;
        }else{
            return true;
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
            mainActivity.hideFloatingActionButton(); //oculto boton
            FloatingActionButton fab = mainActivity.findViewById(R.id.fab);
            //fab.setImageResource(R.drawable.ic_); //Cambiar icono
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contenedor, new CrearAlarmaFragment()).addToBackStack(null);
                    fr.commit();*/
                    //NUEVA FUNCIONALIDAD
                }
            });
        }
    }

    //metodos para mostrar datos en los campos y editarlos
    public void reflejarCampos(){

        //inserto la alarma
        Alarma a =((BaseAplication)getActivity().getApplication()).getAlarma(modiAlarma);
        nombre.setText(a.getNombre());
        String tipo = a.getTipo();
        if (tipo.equals("Casa")) {
            opciones.setSelection(0);
        }
        if (tipo.equals("Oficina")) {
            opciones.setSelection(1);
        }
        if (tipo.equals("Tienda")) {
            opciones.setSelection(2);
        }
        numTelefono.setText(a.getNumTelefono());
        clave.setText(a.getClave());
    }
    public void editar(View v){
        int ipo = opciones.getSelectedItemPosition();
        long response = ((BaseAplication)getActivity().getApplication()).updateAlarma(modiAlarma,nombre.getText().toString(),mZonas.get(ipo).nombreZona,numTelefono.getText().toString(),clave.getText().toString());
        if(response>0){
            Toast.makeText(this.getActivity(),"Editado con exito",Toast.LENGTH_LONG).show();
            nombre.requestFocus();
            nombre.setText("");
            numTelefono.setText("");
            opciones.setSelection(0);
            clave.setText("");
        }else{
            Toast.makeText(this.getActivity(),"Ocurrio un error",Toast.LENGTH_LONG).show();
        }
    }

    //ocultar boton action_settings
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (menu.findItem(R.id.action_settings) != null)
            menu.findItem(R.id.action_settings).setVisible(false);
    }

}
