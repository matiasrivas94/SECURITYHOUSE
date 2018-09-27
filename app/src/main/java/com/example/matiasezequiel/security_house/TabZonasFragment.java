package com.example.matiasezequiel.security_house;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class TabZonasFragment extends Fragment {

    Button boton;
    int id=0, cantZ=0;
    ListView list;
    EditText ed1, ed2, ed3, ed4, ed5, ed6;
    TextView tv1, tv2, tv3, tv4, tv5, tv6;
    long cant=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_zonas, container, false);

        //cantidadZonas();
        ed1 = (EditText) v.findViewById(R.id.edName1);
        ed2 = (EditText) v.findViewById(R.id.edName2);
        ed3 = (EditText) v.findViewById(R.id.edName3);
        ed4 = (EditText) v.findViewById(R.id.edName4);
        ed5 = (EditText) v.findViewById(R.id.edName5);
        ed6 = (EditText) v.findViewById(R.id.edName6);
        tv1 = (TextView) v.findViewById(R.id.tvName1);
        tv2 = (TextView) v.findViewById(R.id.tvName2);
        tv3 = (TextView) v.findViewById(R.id.tvName3);
        tv4 = (TextView) v.findViewById(R.id.tvName4);
        tv5 = (TextView) v.findViewById(R.id.tvName5);
        tv6 = (TextView) v.findViewById(R.id.tvName6);

        //SharedPreferences
        SharedPreferences prefs = getContext().getSharedPreferences("aa",Context.MODE_PRIVATE);
        id=(int)prefs.getLong("cant",-1);
        //Toast.makeText(this.getActivity(),"ID DE LA ULTIMA ALARMA: " + id, Toast.LENGTH_SHORT).show();

        //Shared de la Cantidad de Zonas
        SharedPreferences prefs1 = getContext().getSharedPreferences("bb",Context.MODE_PRIVATE);
        cantZ=(int)prefs1.getLong("cantZ",-1);
        //Toast.makeText(this.getActivity(),"Cantidad de Zonas: " + cantZ, Toast.LENGTH_SHORT).show();

        visibilidadEditText(v);

        boton = (Button)v.findViewById(R.id.btnGuardarZonas);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ComprobarCampos()) {
                    visibilidadTextView(v);
                }
                else {
                    Toast.makeText(v.getContext(),"Hay campos vacios, por favor ingrese nuevamente",Toast.LENGTH_LONG).show();
                };
            }
        });


        return v;
    }


    public void visibilidadEditText(View view) {
        int cant = cantZ;
        switch(cant){
            case 1:
                Toast.makeText(this.getActivity(),"Cantidad de Zonas: " + cant, Toast.LENGTH_SHORT).show();
                if(cant == 1) {
                    ed1.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                Toast.makeText(this.getActivity(),"Cantidad de Zonas: " + cant, Toast.LENGTH_SHORT).show();
                if(cant == 2) {
                    ed1.setVisibility(View.VISIBLE);
                    ed2.setVisibility(View.VISIBLE);
                }
                break;
            case 3:
                Toast.makeText(this.getActivity(),"Cantidad de Zonas: " + cant, Toast.LENGTH_SHORT).show();
                if(cant == 3) {
                    ed1.setVisibility(View.VISIBLE);
                    ed2.setVisibility(View.VISIBLE);
                    ed3.setVisibility(View.VISIBLE);
                }
                break;
            case 4:
                Toast.makeText(this.getActivity(),"Cantidad de Zonas: " + cant, Toast.LENGTH_SHORT).show();
                if(cant == 4) {
                    ed1.setVisibility(View.VISIBLE);
                    ed2.setVisibility(View.VISIBLE);
                    ed3.setVisibility(View.VISIBLE);
                    ed4.setVisibility(View.VISIBLE);
                }
                break;
            case 5:
                Toast.makeText(this.getActivity(),"Cantidad de Zonas: " + cant, Toast.LENGTH_SHORT).show();
                if(cant == 5) {
                    ed1.setVisibility(View.VISIBLE);
                    ed2.setVisibility(View.VISIBLE);
                    ed3.setVisibility(View.VISIBLE);
                    ed4.setVisibility(View.VISIBLE);
                    ed5.setVisibility(View.VISIBLE);
                }
                break;
            case 6:
                Toast.makeText(this.getActivity(),"Cantidad de Zonas: " + cant, Toast.LENGTH_SHORT).show();
                if(cant == 6) {
                    ed1.setVisibility(View.VISIBLE);
                    ed2.setVisibility(View.VISIBLE);
                    ed3.setVisibility(View.VISIBLE);
                    ed4.setVisibility(View.VISIBLE);
                    ed5.setVisibility(View.VISIBLE);
                    ed6.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public void visibilidadTextView(View v){

        //if(ComprobarCampos())
        //{
        String n1, n2, n3, n4, n5, n6;

        n1 = ed1.getText().toString();
        n2 = ed2.getText().toString();
        n3 = ed3.getText().toString();
        n4 = ed4.getText().toString();
        n5 = ed5.getText().toString();
        n6 = ed6.getText().toString();

        AlarmaSQLite bd = new AlarmaSQLite(this.getActivity(),"zona",null,1);

        if(bd!=null) {
            SQLiteDatabase db = bd.getWritableDatabase();
            ContentValues con = new ContentValues();

            if (ed1.getVisibility() == View.VISIBLE) {
                con.put("idAlarma", id);
                con.put("nombre", n1);
                db.insert("zona",null,con);

                tv1.setText(ed1.getText());
                ed1.setVisibility(View.GONE);
                tv1.setVisibility(View.VISIBLE);
            }
            if (ed2.getVisibility() == View.VISIBLE) {
                con.put("idAlarma", id);
                con.put("nombre", n2);
                db.insert("zona",null,con);

                tv2.setText(ed2.getText());
                ed2.setVisibility(View.GONE);
                tv2.setVisibility(View.VISIBLE);
            }
            if (ed3.getVisibility() == View.VISIBLE) {
                con.put("idAlarma", id);
                con.put("nombre", n3);
                db.insert("zona",null,con);

                tv3.setText(ed3.getText());
                ed3.setVisibility(View.GONE);
                tv3.setVisibility(View.VISIBLE);
            }
            if (ed4.getVisibility() == View.VISIBLE) {
                con.put("idAlarma", id);
                con.put("nombre", n4);
                db.insert("zona",null,con);

                tv4.setText(ed4.getText());
                ed4.setVisibility(View.GONE);
                tv4.setVisibility(View.VISIBLE);
            }
            if (ed5.getVisibility() == View.VISIBLE) {
                con.put("idAlarma", id);
                con.put("nombre", n5);
                db.insert("zona",null,con);

                tv5.setText(ed5.getText());
                ed5.setVisibility(View.GONE);
                tv5.setVisibility(View.VISIBLE);
            }
            if (ed6.getVisibility() == View.VISIBLE) {
                con.put("idAlarma", id);
                con.put("nombre", n6);
                db.insert("zona",null,con);

                tv6.setText(ed6.getText());
                ed6.setVisibility(View.GONE);
                tv6.setVisibility(View.VISIBLE);
            }

        }
        //}else{
        //    Toast.makeText(this.getActivity(),"hay campos vacios",Toast.LENGTH_LONG).show();
        //}
    }


    public void mostrarZonas(){

        //selecciono todas las zonas almacenadas segun el id de la alarma que traigo al crear la alarma
        AlarmaSQLite bd = new AlarmaSQLite(this.getActivity(),"zona",null,1);
        ArrayList<Zona> zonas = new ArrayList<>();
        SQLiteDatabase db = bd.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM zona where idAlarma="+id,null);
        if(c.moveToFirst()){
            do{
                zonas.add(new Zona(c.getInt(0),c.getInt(1),c.getString(2)));
            }while(c.moveToNext());
        }
        String arreglo = null;
        for (int i = 0;i<zonas.size();i++){
            arreglo += zonas.get(i).getNombre()+ " --";
        }

        Toast.makeText(this.getActivity(),"Zonas: " + arreglo ,Toast.LENGTH_LONG).show();
    }

    public boolean ComprobarCampos() {
        int cant = cantZ;
        boolean resp = true;
        switch (cant) {
            case 1:
                if (cant == 1) {
                    if (ed1.getText().toString().isEmpty()) {
                        ed1.setError("Complete este campo");
                        resp = false;
                    }
                    else
                        resp = true;
                }
                break;
            case 2:
                if (cant == 2) {
                    if (ed1.getText().toString().isEmpty() || ed2.getText().toString().isEmpty()) {
                        if (ed1.getText().toString().isEmpty()) {
                            ed1.setError("Complete este campo");
                            resp = false;
                        }
                        if(ed2.getText().toString().isEmpty()) {
                            ed2.setError("Complete este campo");
                            resp = false;
                        }
                    }
                    else
                        resp = true;
                }
                break;
            case 3:
                if (cant == 3) {
                    if (ed1.getText().toString().isEmpty() || ed2.getText().toString().isEmpty() || ed3.getText().toString().isEmpty()) {
                        if (ed1.getText().toString().isEmpty()) {
                            ed1.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed2.getText().toString().isEmpty()) {
                            ed2.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed3.getText().toString().isEmpty()) {
                            ed3.setError("Complete este campo");
                            resp = false;
                        }
                    }
                    else
                        resp = true;
                }
                break;
            case 4:
                if (cant == 4) {
                    if (ed1.getText().toString().isEmpty() || ed2.getText().toString().isEmpty() || ed3.getText().toString().isEmpty() || ed4.getText().toString().isEmpty()) {
                        if (ed1.getText().toString().isEmpty()) {
                            ed1.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed2.getText().toString().isEmpty()) {
                            ed2.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed3.getText().toString().isEmpty()) {
                            ed3.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed4.getText().toString().isEmpty()) {
                            ed4.setError("Complete este campo");
                            resp = false;
                        }
                    }
                    else
                        resp = true;
                }
                break;
            case 5:
                if (cant == 5) {
                    if (ed1.getText().toString().isEmpty() || ed2.getText().toString().isEmpty() || ed3.getText().toString().isEmpty() || ed4.getText().toString().isEmpty() || ed5.getText().toString().isEmpty()) {
                        if (ed1.getText().toString().isEmpty()) {
                            ed1.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed2.getText().toString().isEmpty()) {
                            ed2.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed3.getText().toString().isEmpty()) {
                            ed3.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed4.getText().toString().isEmpty()) {
                            ed4.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed5.getText().toString().isEmpty()) {
                            ed5.setError("Complete este campo");
                            resp = false;
                        }
                    }
                    else
                        resp = true;
                }
                break;
            case 6:
                if (cant == 6) {
                    if (ed1.getText().toString().isEmpty() || ed2.getText().toString().isEmpty() || ed3.getText().toString().isEmpty() || ed4.getText().toString().isEmpty() || ed5.getText().toString().isEmpty() || ed6.getText().toString().isEmpty()) {
                        if (ed1.getText().toString().isEmpty()) {
                            ed1.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed2.getText().toString().isEmpty()) {
                            ed2.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed3.getText().toString().isEmpty()) {
                            ed3.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed4.getText().toString().isEmpty()) {
                            ed4.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed5.getText().toString().isEmpty()) {
                            ed5.setError("Complete este campo");
                            resp = false;
                        }
                        if (ed6.getText().toString().isEmpty()) {
                            ed6.setError("Complete este campo");
                            resp = false;
                        }
                    }
                    else
                        resp = true;
                }
                break;
        }
        return resp;
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
}
