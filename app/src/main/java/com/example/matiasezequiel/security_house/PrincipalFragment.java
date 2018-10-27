package com.example.matiasezequiel.security_house;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class PrincipalFragment extends Fragment {

    TextView titulo;
    ViewPager mViewPager;
    ImageView iv_edit, iv_edit2;
    Button aplicarZonas;
    TabLayout tabLayout;


    int idAlarmaTabZona=0;
    SharedPreferences prefs2;
    TextView t1,t2,t3,t4,t5,t6;
    Button b1, b2, b3, b4, b5, b6, btnVolver, btnGuardarEstadoZonas;
    SharedPreferences prefsZ1, prefsZ2, prefsZ3, prefsZ4, prefsZ5, prefsZ6;
    CheckBox c1, c2, c3, c4, c5, c6;

    //Saber los estados de las zonas para asignar al checkbox
    ArrayList<Integer> estado, idZona;
    ArrayList<String> arreglo;

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_principal, container, false);


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(new PagerAdapter(getFragmentManager(),2));

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        iv_edit = view.findViewById(R.id.view_edit);
        iv_edit2 = view.findViewById(R.id.view_edit2);


        aplicarZonas = (Button) view.findViewById(R.id.btnAplicarZonas);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));



        //para cambiarle el encabezado al principal fragment con el nombre de la nueva alamra insertada
        titulo = (TextView) view.findViewById(R.id.textTituloAlarma);
        //Shared para saber el id de la alarma clickeada en la lista de las alarmas

        SharedPreferences prefs = getContext().getSharedPreferences("ee",Context.MODE_PRIVATE);
        String nombreAlarm = prefs.getString("nombreAlarma"," ");
        titulo.setText(nombreAlarm);

        //actualizarZonas2(view);
        //boton Editar Camara
        iv_edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Holis",Toast.LENGTH_SHORT).show();
            }
        });

        //Boton general de Editar Zonas
        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eliminarShared(v);
                //actualizarZonas2(v);

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.modif_zonas, null);

                c1 = (CheckBox)mView.findViewById(R.id.cbZ1);
                c2 = (CheckBox)mView.findViewById(R.id.cbZ2);
                c3 = (CheckBox)mView.findViewById(R.id.cbZ3);
                c4 = (CheckBox)mView.findViewById(R.id.cbZ4);
                c5 = (CheckBox)mView.findViewById(R.id.cbZ5);
                c6 = (CheckBox)mView.findViewById(R.id.cbZ6);
                b1 = (Button)mView.findViewById(R.id.btnE1);
                b2 = (Button)mView.findViewById(R.id.btnE2);
                b3 = (Button)mView.findViewById(R.id.btnE3);
                b4 = (Button)mView.findViewById(R.id.btnE4);
                b5 = (Button)mView.findViewById(R.id.btnE5);
                b6 = (Button)mView.findViewById(R.id.btnE6);

                t1 = (TextView)mView.findViewById(R.id.tvZ1);
                t2 = (TextView)mView.findViewById(R.id.tvZ2);
                t3 = (TextView)mView.findViewById(R.id.tvZ3);
                t4 = (TextView)mView.findViewById(R.id.tvZ4);
                t5 = (TextView)mView.findViewById(R.id.tvZ5);
                t6 = (TextView)mView.findViewById(R.id.tvZ6);

                btnVolver = (Button)mView.findViewById(R.id.btnAplicarZonas);
                btnGuardarEstadoZonas = (Button)mView.findViewById(R.id.btnGuardarEstadosZonas);

                //Conexin a la BASE
                //Tabla ALARMA
                AlarmaSQLite bdA = new AlarmaSQLite(getActivity(),"alarma",null,1);
                SQLiteDatabase dbA = bdA.getReadableDatabase();

                //Tabla ZONA
                final AlarmaSQLite bdZ = new AlarmaSQLite(getActivity(),"zona",null,1);
                final SQLiteDatabase dbZ = bdZ.getWritableDatabase();
                final ContentValues conZ = new ContentValues();

                //Toast.makeText(getActivity(), "ID ALARMA del SHARED: " + idAlarmaTabZona, Toast.LENGTH_LONG).show();
                //selecciono todas las zonas almacenadas segun el id de la alarma que traigo del tabZonas
                ArrayList<Zona> zonas = new ArrayList<>();
                Cursor c = dbZ.rawQuery("SELECT * FROM zona where idAlarma="+idAlarmaTabZona,null);
                if(c.moveToFirst()){
                    do{
                        zonas.add(new Zona(c.getInt(0),c.getInt(1),c.getString(2),c.getInt(3),c.getInt(4)));
                    }while(c.moveToNext());
                }
                arreglo = new ArrayList<>();
                estado = new ArrayList<>();
                idZona = new ArrayList<>();
                for (int i = 0;i<zonas.size();i++){
                    arreglo.add(zonas.get(i).getNombre());
                    idZona.add(zonas.get(i).getIdZona());
                    estado.add(zonas.get(i).getEstado());
                }
                t1.setText(arreglo.get(0));
                t2.setText(arreglo.get(1));
                t3.setText(arreglo.get(2));
                t4.setText(arreglo.get(3));
                t5.setText(arreglo.get(4));
                t6.setText(arreglo.get(5));

                //seteo los checkboxs
                if(estado.get(0) == 1)
                    c1.setChecked(true);
                else
                    c1.setChecked(false);
                if(estado.get(1) == 1)
                    c2.setChecked(true);
                else
                    c2.setChecked(false);
                if(estado.get(2) == 1)
                    c3.setChecked(true);
                else
                    c3.setChecked(false);
                if(estado.get(3) == 1)
                    c4.setChecked(true);
                else
                    c4.setChecked(false);
                if(estado.get(4) == 1)
                    c5.setChecked(true);
                else
                    c5.setChecked(false);
                if(estado.get(5) == 1)
                    c6.setChecked(true);
                else
                    c6.setChecked(false);


                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor1 = getContext().getSharedPreferences("zona1", Context.MODE_PRIVATE).edit();
                        editor1.putLong("idZona1", idZona.get(0));
                        editor1.commit();
                        //Toast.makeText(getContext(), "Zona 1", Toast.LENGTH_SHORT).show();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        final View viewEZ = getLayoutInflater().inflate(R.layout.dialog_editar_zona, null);

                        final EditText etZona = (EditText)viewEZ.findViewById(R.id.etEditZona);
                        final Button btnCancelar = (Button)viewEZ.findViewById(R.id.btnCancelar);
                        final Button btnAceptar = (Button)viewEZ.findViewById(R.id.btnAceptar);
                        final TextView tvZona = (TextView)viewEZ.findViewById(R.id.tvEditZ);

                        builder.setView(viewEZ);
                        final AlertDialog dialog = builder.create();
                        //Seteo texto de la zona en el TextView
                        //ArrayList<String> zonas = actualizarZonas(viewEZ);
                        //Seteo texto de la zona en el TextView
                        tvZona.setText(arreglo.get(0));
                        //Seteo texto de la zona en el EditText
                        etZona.setText(arreglo.get(0));
                        //aparece el cursor al final de la palabra en el EditText
                        etZona.setSelection(etZona.getText().length());
                        dialog.show();

                        btnCancelar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                                }
                        });
                        btnAceptar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(bdZ!=null) {
                                    prefsZ1 = getContext().getSharedPreferences("zona1",Context.MODE_PRIVATE);
                                    Integer idZ1 = (int)prefsZ1.getLong("idZona1",-1);
                                    //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ1, Toast.LENGTH_LONG).show();
                                    conZ.put("nombre", etZona.getText().toString());

                                    long response = dbZ.update("zona", conZ, "idZona=" + idZ1, null);
                                    if (response > 0) {
                                        Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                    }
                                    prefsZ1.edit().remove("idZona1").commit();
                                }
                                actualizarZonas2(viewEZ);
                                dialog.dismiss();
                            }
                        });
                    }
                });
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor1 = getContext().getSharedPreferences("zona2", Context.MODE_PRIVATE).edit();
                        editor1.putLong("idZona2", idZona.get(1));
                        editor1.commit();
                        //Toast.makeText(getContext(), "Zona 2", Toast.LENGTH_SHORT).show();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        final View viewEZ = getLayoutInflater().inflate(R.layout.dialog_editar_zona, null);

                        final EditText etZona = (EditText)viewEZ.findViewById(R.id.etEditZona);
                        final Button btnCancelar = (Button)viewEZ.findViewById(R.id.btnCancelar);
                        final Button btnAceptar = (Button)viewEZ.findViewById(R.id.btnAceptar);
                        final TextView tvZona = (TextView)viewEZ.findViewById(R.id.tvEditZ);

                        builder.setView(viewEZ);
                        final AlertDialog dialog = builder.create();
                        //Seteo texto de la zona en el TextView
                        //ArrayList<String> zonas = actualizarZonas(viewEZ);
                        //Seteo texto de la zona en el TextView
                        tvZona.setText(arreglo.get(1));
                        //Seteo texto de la zona en el EditText
                        etZona.setText(arreglo.get(1));
                        //aparece el cursor al final de la palabra en el EditText
                        etZona.setSelection(etZona.getText().length());
                        dialog.show();

                        btnCancelar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel(); }
                        });
                        btnAceptar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(bdZ!=null) {
                                    prefsZ2 = getContext().getSharedPreferences("zona2",Context.MODE_PRIVATE);
                                    Integer idZ2 = (int)prefsZ2.getLong("idZona2",-1);
                                    //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ2, Toast.LENGTH_LONG).show();
                                    conZ.put("nombre", etZona.getText().toString());

                                    long response = dbZ.update("zona", conZ, "idZona=" + idZ2, null);
                                    if (response > 0) {
                                        Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                    }
                                    prefsZ2.edit().remove("idZona2").commit();
                                }
                                actualizarZonas2(viewEZ);
                                dialog.dismiss();
                            }
                        });
                    }
                });
                b3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor1 = getContext().getSharedPreferences("zona3", Context.MODE_PRIVATE).edit();
                        editor1.putLong("idZona3", idZona.get(2));
                        editor1.commit();
                        //Toast.makeText(getContext(), "Zona 3", Toast.LENGTH_SHORT).show();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        final View viewEZ = getLayoutInflater().inflate(R.layout.dialog_editar_zona, null);

                        final EditText etZona = (EditText)viewEZ.findViewById(R.id.etEditZona);
                        final Button btnCancelar = (Button)viewEZ.findViewById(R.id.btnCancelar);
                        final Button btnAceptar = (Button)viewEZ.findViewById(R.id.btnAceptar);
                        final TextView tvZona = (TextView)viewEZ.findViewById(R.id.tvEditZ);

                        builder.setView(viewEZ);
                        final AlertDialog dialog = builder.create();
                        //Seteo texto de la zona en el TextView
                        //ArrayList<String> zonas = actualizarZonas(viewEZ);
                        //Seteo texto de la zona en el TextView
                        tvZona.setText(arreglo.get(2));
                        //Seteo texto de la zona en el EditText
                        etZona.setText(arreglo.get(2));
                        //aparece el cursor al final de la palabra en el EditText
                        etZona.setSelection(etZona.getText().length());
                        dialog.show();

                        btnCancelar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                    }
                        });
                        btnAceptar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(bdZ!=null) {
                                    prefsZ3 = getContext().getSharedPreferences("zona3",Context.MODE_PRIVATE);
                                    Integer idZ3 = (int)prefsZ3.getLong("idZona3",-1);
                                    //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ3, Toast.LENGTH_LONG).show();
                                    conZ.put("nombre", etZona.getText().toString());

                                    long response = dbZ.update("zona", conZ, "idZona=" + idZ3, null);
                                    if (response > 0) {
                                        Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                    }
                                    prefsZ3.edit().remove("idZona3").commit();
                                }
                                actualizarZonas2(viewEZ);
                                dialog.dismiss();
                            }
                        });
                    }
                });
                b4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor1 = getContext().getSharedPreferences("zona4", Context.MODE_PRIVATE).edit();
                        editor1.putLong("idZona4", idZona.get(3));
                        editor1.commit();
                        //Toast.makeText(getContext(), "Zona 4", Toast.LENGTH_SHORT).show();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        final View viewEZ = getLayoutInflater().inflate(R.layout.dialog_editar_zona, null);

                        final EditText etZona = (EditText)viewEZ.findViewById(R.id.etEditZona);
                        final Button btnCancelar = (Button)viewEZ.findViewById(R.id.btnCancelar);
                        final Button btnAceptar = (Button)viewEZ.findViewById(R.id.btnAceptar);
                        final TextView tvZona = (TextView)viewEZ.findViewById(R.id.tvEditZ);

                        builder.setView(viewEZ);
                        final AlertDialog dialog = builder.create();
                        //Seteo texto de la zona en el TextView
                        //ArrayList<String> zonas = actualizarZonas(viewEZ);
                        //Seteo texto de la zona en el TextView
                        tvZona.setText(arreglo.get(3));
                        //Seteo texto de la zona en el EditText
                        etZona.setText(arreglo.get(3));
                        //aparece el cursor al final de la palabra en el EditText
                        etZona.setSelection(etZona.getText().length());
                        dialog.show();

                        btnCancelar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                    }
                                });
                        btnAceptar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(bdZ!=null) {
                                    prefsZ4 = getContext().getSharedPreferences("zona4",Context.MODE_PRIVATE);
                                    Integer idZ4 = (int)prefsZ4.getLong("idZona4",-1);
                                    //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ4, Toast.LENGTH_LONG).show();
                                    conZ.put("nombre", etZona.getText().toString());

                                    long response = dbZ.update("zona", conZ, "idZona=" + idZ4, null);
                                    if (response > 0) {
                                        Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                    }
                                    prefsZ4.edit().remove("idZona4").commit();
                                }
                                actualizarZonas2(viewEZ);
                                dialog.dismiss();
                            }
                        });
                    }
                });
                b5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor1 = getContext().getSharedPreferences("zona5", Context.MODE_PRIVATE).edit();
                        editor1.putLong("idZona5", idZona.get(4));
                        editor1.commit();
                        //Toast.makeText(getContext(), "Zona 5", Toast.LENGTH_SHORT).show();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        final View viewEZ = getLayoutInflater().inflate(R.layout.dialog_editar_zona, null);

                        final EditText etZona = (EditText)viewEZ.findViewById(R.id.etEditZona);
                        final Button btnCancelar = (Button)viewEZ.findViewById(R.id.btnCancelar);
                        final Button btnAceptar = (Button)viewEZ.findViewById(R.id.btnAceptar);
                        final TextView tvZona = (TextView)viewEZ.findViewById(R.id.tvEditZ);

                        builder.setView(viewEZ);
                        final AlertDialog dialog = builder.create();
                        //Seteo texto de la zona en el TextView
                        //ArrayList<String> zonas = actualizarZonas(viewEZ);
                        //Seteo texto de la zona en el TextView
                        tvZona.setText(arreglo.get(4));
                        //Seteo texto de la zona en el EditText
                        etZona.setText(arreglo.get(4));
                        //aparece el cursor al final de la palabra en el EditText
                        etZona.setSelection(etZona.getText().length());
                        dialog.show();

                        btnCancelar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                    }
                                });
                        btnAceptar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(bdZ!=null) {
                                    prefsZ5 = getContext().getSharedPreferences("zona5",Context.MODE_PRIVATE);
                                    Integer idZ5 = (int)prefsZ5.getLong("idZona5",-1);
                                    //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ5, Toast.LENGTH_LONG).show();
                                    conZ.put("nombre", etZona.getText().toString());

                                    long response = dbZ.update("zona", conZ, "idZona=" + idZ5, null);
                                    if (response > 0) {
                                        Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                    }
                                    prefsZ5.edit().remove("idZona5").commit();
                                }
                                actualizarZonas2(viewEZ);
                                dialog.dismiss();
                            }
                        });
                    }
                });
                b6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor1 = getContext().getSharedPreferences("zona6", Context.MODE_PRIVATE).edit();
                        editor1.putLong("idZona6", idZona.get(5));
                        editor1.commit();
                        //Toast.makeText(getContext(), "Zona 6", Toast.LENGTH_SHORT).show();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        final View viewEZ = getLayoutInflater().inflate(R.layout.dialog_editar_zona, null);

                        final EditText etZona = (EditText)viewEZ.findViewById(R.id.etEditZona);
                        final Button btnCancelar = (Button)viewEZ.findViewById(R.id.btnCancelar);
                        final Button btnAceptar = (Button)viewEZ.findViewById(R.id.btnAceptar);
                        final TextView tvZona = (TextView)viewEZ.findViewById(R.id.tvEditZ);

                        builder.setView(viewEZ);
                        final AlertDialog dialog = builder.create();
                        //Seteo texto de la zona en el TextView
                        //ArrayList<String> zonas = actualizarZonas(viewEZ);
                        //Seteo texto de la zona en el TextView
                        tvZona.setText(arreglo.get(5));
                        //Seteo texto de la zona en el EditText
                        etZona.setText(arreglo.get(5));
                        //aparece el cursor al final de la palabra en el EditText
                        etZona.setSelection(etZona.getText().length());
                        dialog.show();

                        btnCancelar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });
                        btnAceptar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(bdZ!=null) {
                                    prefsZ6 = getContext().getSharedPreferences("zona6",Context.MODE_PRIVATE);
                                    Integer idZ6 = (int)prefsZ6.getLong("idZona6",-1);
                                    //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ6, Toast.LENGTH_LONG).show();
                                    conZ.put("nombre", etZona.getText().toString());

                                    long response = dbZ.update("zona", conZ, "idZona=" + idZ6, null);
                                    if (response > 0) {
                                        Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                    }
                                    prefsZ6.edit().remove("idZona6").commit();
                                }
                                actualizarZonas2(viewEZ);
                                dialog.dismiss();
                            }
                        });
                    }
                });

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                btnVolver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actualizarZonas2(view);
                        dialog.dismiss();
                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.contenedor, new PrincipalFragment(), "Principal");
                        fr.commit();
                    }
                });
                btnGuardarEstadoZonas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getContext(),"Guardar Estados Zonas",Toast.LENGTH_LONG).show();
                        verificaCheckBoxs();

                        //shared para listar zonas
                        /*SharedPreferences.Editor editor1 = getContext().getSharedPreferences("editarZona",Context.MODE_PRIVATE).edit();
                        editor1.putString("updateZona", "actualizado");
                        editor1.commit();*/
                        //Shareds para el fragment tabzonas para listar zonas
                        SharedPreferences.Editor editor = getContext().getSharedPreferences("cc",Context.MODE_PRIVATE).edit();
                        editor.putLong("idAlarma", idAlarmaTabZona);
                        editor.commit();

                        dialog.dismiss();
                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.contenedor, new PrincipalFragment(), "Principal");
                        fr.commit();

                        //actualizarZonas(view);
                    }
                });


                eliminarShared(view);
                //evito que se cierre al presionar fuera del dialog
                dialog.setCanceledOnTouchOutside(false);
            }
        });
        return view;
    }

    public void verificaCheckBoxs(){
        final AlarmaSQLite bdZ = new AlarmaSQLite(getActivity(),"zona",null,1);
        final SQLiteDatabase dbZ = bdZ.getWritableDatabase();
        final ContentValues conZ = new ContentValues();

        if(c1.isChecked()) {
            conZ.put("estado", 1);
            dbZ.update("zona", conZ, "idZona="+idZona.get(0), null);
            //Toast.makeText(getContext(), "CheckBox 1 ''SI'' está Tildado", Toast.LENGTH_LONG).show();
        }else {
            conZ.put("estado", 0);
            dbZ.update("zona", conZ, "idZona=" + idZona.get(0), null);
        }
        if(c2.isChecked()) {
            conZ.put("estado", 1);
            dbZ.update("zona", conZ, "idZona="+idZona.get(1), null);
            //Toast.makeText(getContext(), "CheckBox 1 ''SI'' está Tildado", Toast.LENGTH_LONG).show();
        }else {
            conZ.put("estado", 0);
            dbZ.update("zona", conZ, "idZona=" + idZona.get(1), null);
        }
        if(c3.isChecked()) {
            conZ.put("estado", 1);
            dbZ.update("zona", conZ, "idZona="+idZona.get(2), null);
            //Toast.makeText(getContext(), "CheckBox 1 ''SI'' está Tildado", Toast.LENGTH_LONG).show();
        }else {
            conZ.put("estado", 0);
            dbZ.update("zona", conZ, "idZona=" + idZona.get(2), null);
        }
        if(c4.isChecked()) {
            conZ.put("estado", 1);
            dbZ.update("zona", conZ, "idZona="+idZona.get(3), null);
            //Toast.makeText(getContext(), "CheckBox 1 ''SI'' está Tildado", Toast.LENGTH_LONG).show();
        }else {
            conZ.put("estado", 0);
            dbZ.update("zona", conZ, "idZona=" + idZona.get(3), null);
        }
        if(c5.isChecked()) {
            conZ.put("estado", 1);
            dbZ.update("zona", conZ, "idZona="+idZona.get(4), null);
            //Toast.makeText(getContext(), "CheckBox 1 ''SI'' está Tildado", Toast.LENGTH_LONG).show();
        }else {
            conZ.put("estado", 0);
            dbZ.update("zona", conZ, "idZona=" + idZona.get(4), null);
        }
        if(c6.isChecked()) {
            conZ.put("estado", 1);
            dbZ.update("zona", conZ, "idZona="+idZona.get(5), null);
            //Toast.makeText(getContext(), "CheckBox 1 ''SI'' está Tildado", Toast.LENGTH_LONG).show();
        }else {
            conZ.put("estado", 0);
            dbZ.update("zona", conZ, "idZona=" + idZona.get(5), null);
        }
    }

    public void actualizarZonas2(View v){
        //Tabla ZONA
        final AlarmaSQLite bdZ = new AlarmaSQLite(getActivity(),"zona",null,1);
        final SQLiteDatabase dbZ = bdZ.getWritableDatabase();
        final ContentValues conZ = new ContentValues();
        //selecciono todas las zonas almacenadas segun el id de la alarma que traigo del tabZonas
        ArrayList<Zona> zonas = new ArrayList<>();
        Cursor c = dbZ.rawQuery("SELECT * FROM zona where idAlarma="+idAlarmaTabZona,null);
        if(c.moveToFirst()){
            do{
                zonas.add(new Zona(c.getInt(0),c.getInt(1),c.getString(2),c.getInt(3),c.getInt(4)));
            }while(c.moveToNext());
        }
        bdZ.close();
        arreglo = new ArrayList<>();
        estado = new ArrayList<>();
        idZona = new ArrayList<>();
        for (int i = 0;i<zonas.size();i++){
            arreglo.add(zonas.get(i).getNombre());
            idZona.add(zonas.get(i).getIdZona());
            estado.add(zonas.get(i).getEstado());
        }
        t1.setText(arreglo.get(0));
        t2.setText(arreglo.get(1));
        t3.setText(arreglo.get(2));
        t4.setText(arreglo.get(3));
        t5.setText(arreglo.get(4));
        t6.setText(arreglo.get(5));
    }
    public void eliminarShared(View v){
        prefs2 = getContext().getSharedPreferences("idAlarmaPrin",Context.MODE_PRIVATE);
        idAlarmaTabZona=(int)prefs2.getLong("idAlarmaPrincipal",-1);
        //Toast.makeText(getContext(),"ID Alarma: "+idAlarmaTabZona,Toast.LENGTH_LONG).show();
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }


        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    TabZonasFragment tab2 = new TabZonasFragment();
                    return tab2;
                case 1:
                    TabCamarasFragment tab3 = new TabCamarasFragment();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }




    @Override
    public void onResume() {
        super.onResume();
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:{
                        iv_edit.setVisibility(View.VISIBLE);
                        iv_edit2.setVisibility(View.GONE);}
                    break;
                    case 1:{
                        iv_edit.setVisibility(View.GONE);
                        iv_edit2.setVisibility(View.VISIBLE);}
                    break;
                    default: break;
                }
                super.onTabSelected(tab);
            }
        });
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

