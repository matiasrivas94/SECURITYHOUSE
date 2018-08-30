package com.example.matiasezequiel.security_house;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class AlarmasFragment extends Fragment {
    //cosas del ListView Alarmas
    private ArrayList<Alarma> alarmas = new ArrayList<>();
    private ListView lista;

    ListView list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_alarmas, container, false);

        lista = (ListView) v.findViewById(R.id.LVMostrar);
        
        llenarLista();

        return v;
    }

    public void llenarLista(){
        //conexion a la base de datos
        AlarmaSQLite bh = new AlarmaSQLite(this.getActivity(),"alarma",null,1);
        if(bh!=null){
            Log.d("","Paso por el if");
            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM alarma",null);
            if(c.moveToFirst()){
                do{
                    //alarmas.add(new Alarma(c.getInt(0),c.getString(1),c.getString(2),c.getInt(3)));
                    alarmas.add(new Alarma(c.getInt(0),c.getString(1),c.getString(2),c.getString(3)));
                }while(c.moveToNext());
            }
        }
        String[] arreglo = new String[alarmas.size()];
        for (int i = 0;i<arreglo.length;i++){
            arreglo[i] = alarmas.get(i).getNombre();
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this.getActivity() ,android.R.layout.simple_list_item_1,arreglo);
        lista.setAdapter(adaptador);
    }
    

}
