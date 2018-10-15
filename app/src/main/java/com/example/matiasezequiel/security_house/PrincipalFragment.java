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
    ImageView iv_edit;
    Button aplicarZonas;

    int idAlarmaTabZona=0;
    SharedPreferences prefs2;
    TextView t1,t2,t3,t4,t5,t6;

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_principal, container, false);


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(new PagerAdapter(getFragmentManager(),2));

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        iv_edit = view.findViewById(R.id.view_edit);
        aplicarZonas = (Button) view.findViewById(R.id.btnAplicarZonas);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //para cambiarle el encabezado al principal fragment con el nombre de la nueva alamra insertada
        titulo = (TextView) view.findViewById(R.id.textTituloAlarma);
        //Shared para saber el id de la alarma clickeada en la lista de las alarmas

        SharedPreferences prefs = getContext().getSharedPreferences("ee",Context.MODE_PRIVATE);
        String nombreAlarm = prefs.getString("nombreAlarma"," ");
        titulo.setText(nombreAlarm);


        //Boton general de Edotar Zonas
        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarShared(v);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.modif_zonas, null);
                CheckBox c1 = (CheckBox)mView.findViewById(R.id.cbZ1);
                CheckBox c2 = (CheckBox)mView.findViewById(R.id.cbZ2);
                CheckBox c3 = (CheckBox)mView.findViewById(R.id.cbZ3);
                CheckBox c4 = (CheckBox)mView.findViewById(R.id.cbZ4);
                CheckBox c5 = (CheckBox)mView.findViewById(R.id.cbZ5);
                CheckBox c6 = (CheckBox)mView.findViewById(R.id.cbZ6);
                Button b1 = (Button)mView.findViewById(R.id.btnE1);
                Button b2 = (Button)mView.findViewById(R.id.btnE2);
                Button b3 = (Button)mView.findViewById(R.id.btnE3);
                Button b4 = (Button)mView.findViewById(R.id.btnE4);
                Button b5 = (Button)mView.findViewById(R.id.btnE5);
                Button b6 = (Button)mView.findViewById(R.id.btnE6);
                t1 = (TextView)mView.findViewById(R.id.tvZ1);
                t2 = (TextView)mView.findViewById(R.id.tvZ2);
                t3 = (TextView)mView.findViewById(R.id.tvZ3);
                t4 = (TextView)mView.findViewById(R.id.tvZ4);
                t5 = (TextView)mView.findViewById(R.id.tvZ5);
                t6 = (TextView)mView.findViewById(R.id.tvZ6);
                Button btnAplicar = (Button)mView.findViewById(R.id.btnAplicarZonas);


                //Conexin a la BASE
                //Tabla ALARMA
                AlarmaSQLite bdA = new AlarmaSQLite(getActivity(),"alarma",null,1);
                SQLiteDatabase dbA = bdA.getReadableDatabase();

                int idAla = idAlarmaTabZona;
                SQLiteStatement s1 = dbA.compileStatement("SELECT cantZonas FROM alarma WHERE idAlarma="+idAla);
                int cantidad = (int)s1.simpleQueryForLong();
                //Toast.makeText(getContext(),"Cantidad de Zonas de la ultima alarma insertada: " + cantidad,Toast.LENGTH_LONG).show();

                //Tabla ZONA
                final AlarmaSQLite bdZ = new AlarmaSQLite(getActivity(),"zona",null,1);
                final SQLiteDatabase dbZ = bdZ.getWritableDatabase();
                final ContentValues conZ = new ContentValues();
                //selecciono todas las zonas almacenadas segun el id de la alarma que traigo del tabZonas
                ArrayList<Zona> zonas = new ArrayList<>();
                Cursor c = dbZ.rawQuery("SELECT * FROM zona where idAlarma="+idAlarmaTabZona,null);
                if(c.moveToFirst()){
                    do{
                        zonas.add(new Zona(c.getInt(0),c.getInt(1),c.getString(2)));
                    }while(c.moveToNext());
                }
                final ArrayList<String> arreglo = new ArrayList<>();
                final ArrayList<Integer> idZona = new ArrayList<>();
                for (int i = 0;i<zonas.size();i++){
                    arreglo.add (zonas.get(i).getNombre());
                    idZona.add(zonas.get(i).getIdZona());
                }

                actualizarZonas(v);

                switch (cantidad) {
                    case 1:

                        t1.setText(arreglo.get(0));
                        c2.setChecked(false);
                        c3.setChecked(false);
                        c4.setChecked(false);
                        c5.setChecked(false);
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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(0));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(0));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona1",Context.MODE_PRIVATE);
                                            Integer idZ1 = (int)prefs.getLong("idZona1",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ1, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ1, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona1").commit();
                                        }
                                        actualizarZonas(viewEZ);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        break;
                    case 2:
                        c3.setChecked(false);
                        c4.setChecked(false);
                        c5.setChecked(false);
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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(0));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(0));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona1",Context.MODE_PRIVATE);
                                            Integer idZ1 = (int)prefs.getLong("idZona1",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ1, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ1, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona1").commit();
                                        }
                                        actualizarZonas(viewEZ);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        b2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences.Editor editor2 = getContext().getSharedPreferences("zona2",Context.MODE_PRIVATE).edit();
                                editor2.putLong("idZona2", idZona.get(1));
                                editor2.commit();
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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(1));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(1));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona2",Context.MODE_PRIVATE);
                                            Integer idZ2 = (int)prefs.getLong("idZona2",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ2, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ2, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona2").commit();
                                        }
                                        actualizarZonas(viewEZ);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        break;
                    case 3:
                        c4.setChecked(false);
                        c5.setChecked(false);
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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(0));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(0));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona1",Context.MODE_PRIVATE);
                                            Integer idZ1 = (int)prefs.getLong("idZona1",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ1, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ1, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona1").commit();
                                        }
                                        actualizarZonas(viewEZ);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        b2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences.Editor editor2 = getContext().getSharedPreferences("zona2",Context.MODE_PRIVATE).edit();
                                editor2.putLong("idZona2", idZona.get(1));
                                editor2.commit();

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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(1));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(1));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona2",Context.MODE_PRIVATE);
                                            Integer idZ2 = (int)prefs.getLong("idZona2",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ2, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ2, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona2").commit();
                                        }
                                        actualizarZonas(viewEZ);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        b3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences.Editor editor3 = getContext().getSharedPreferences("zona3",Context.MODE_PRIVATE).edit();
                                editor3.putLong("idZona3", idZona.get(2));
                                editor3.commit();
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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(2));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(2));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona3",Context.MODE_PRIVATE);
                                            Integer idZ3 = (int)prefs.getLong("idZona3",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ3, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ3, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona3").commit();
                                        }
                                        actualizarZonas(viewEZ);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        break;
                    case 4:
                        c5.setChecked(false);
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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(0));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(0));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona1",Context.MODE_PRIVATE);
                                            Integer idZ1 = (int)prefs.getLong("idZona1",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ1, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ1, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona1").commit();
                                        }
                                        actualizarZonas(viewEZ);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        b2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences.Editor editor2 = getContext().getSharedPreferences("zona2",Context.MODE_PRIVATE).edit();
                                editor2.putLong("idZona2", idZona.get(1));
                                editor2.commit();

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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(1));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(1));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona2",Context.MODE_PRIVATE);
                                            Integer idZ2 = (int)prefs.getLong("idZona2",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ2, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ2, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona2").commit();
                                        }
                                        actualizarZonas(viewEZ);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        b3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences.Editor editor3 = getContext().getSharedPreferences("zona3",Context.MODE_PRIVATE).edit();
                                editor3.putLong("idZona3", idZona.get(2));
                                editor3.commit();

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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(2));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(2));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona3",Context.MODE_PRIVATE);
                                            Integer idZ3 = (int)prefs.getLong("idZona3",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ3, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ3, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona3").commit();
                                        }
                                        actualizarZonas(viewEZ);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        b4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences.Editor editor4 = getContext().getSharedPreferences("zona4",Context.MODE_PRIVATE).edit();
                                editor4.putLong("idZona4", idZona.get(3));
                                editor4.commit();
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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(3));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(3));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona4",Context.MODE_PRIVATE);
                                            Integer idZ4 = (int)prefs.getLong("idZona4",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ4, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ4, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona4").commit();
                                        }
                                        actualizarZonas(viewEZ);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        break;
                    case 5:
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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(0));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(0));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona1",Context.MODE_PRIVATE);
                                            Integer idZ1 = (int)prefs.getLong("idZona1",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ1, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ1, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona1").commit();
                                        }
                                        actualizarZonas(viewEZ);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        b2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences.Editor editor2 = getContext().getSharedPreferences("zona2",Context.MODE_PRIVATE).edit();
                                editor2.putLong("idZona2", idZona.get(1));
                                editor2.commit();

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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(1));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(1));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona2",Context.MODE_PRIVATE);
                                            Integer idZ2 = (int)prefs.getLong("idZona2",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ2, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ2, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona2").commit();
                                        }
                                        actualizarZonas(viewEZ);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        b3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences.Editor editor3 = getContext().getSharedPreferences("zona3",Context.MODE_PRIVATE).edit();
                                editor3.putLong("idZona3", idZona.get(2));
                                editor3.commit();

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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(2));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(2));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona3",Context.MODE_PRIVATE);
                                            Integer idZ3 = (int)prefs.getLong("idZona3",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ3, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ3, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona3").commit();
                                        }
                                        actualizarZonas(viewEZ);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        b4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences.Editor editor4 = getContext().getSharedPreferences("zona4",Context.MODE_PRIVATE).edit();
                                editor4.putLong("idZona4", idZona.get(3));
                                editor4.commit();

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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(3));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(3));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona4",Context.MODE_PRIVATE);
                                            Integer idZ4 = (int)prefs.getLong("idZona4",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ4, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ4, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona4").commit();
                                        }
                                        actualizarZonas(viewEZ);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        b5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences.Editor editor5 = getContext().getSharedPreferences("zona5",Context.MODE_PRIVATE).edit();
                                editor5.putLong("idZona5", idZona.get(4));
                                editor5.commit();

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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(4));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(4));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona5",Context.MODE_PRIVATE);
                                            Integer idZ5 = (int)prefs.getLong("idZona5",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ5, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ5, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona5").commit();
                                        }
                                        actualizarZonas(viewEZ);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        break;
                    case 6:
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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(0));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(0));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona1",Context.MODE_PRIVATE);
                                            Integer idZ1 = (int)prefs.getLong("idZona1",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ1, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ1, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona1").commit();
                                        }
                                        actualizarZonas(viewEZ);
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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(1));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(1));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona2",Context.MODE_PRIVATE);
                                            Integer idZ2 = (int)prefs.getLong("idZona2",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ2, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ2, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona2").commit();
                                        }
                                        actualizarZonas(viewEZ);
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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(2));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(2));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona3",Context.MODE_PRIVATE);
                                            Integer idZ3 = (int)prefs.getLong("idZona3",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ3, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ3, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona3").commit();
                                        }
                                        actualizarZonas(viewEZ);
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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(3));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(3));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona4",Context.MODE_PRIVATE);
                                            Integer idZ4 = (int)prefs.getLong("idZona4",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ4, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ4, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona4").commit();
                                        }
                                        actualizarZonas(viewEZ);
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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(4));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(4));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona5",Context.MODE_PRIVATE);
                                            Integer idZ5 = (int)prefs.getLong("idZona5",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ5, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ5, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona5").commit();
                                        }
                                        actualizarZonas(viewEZ);
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
                                ArrayList<String> zonas = actualizarZonas(viewEZ);
                                //Seteo texto de la zona en el TextView
                                tvZona.setText(zonas.get(5));
                                //Seteo texto de la zona en el EditText
                                etZona.setText(zonas.get(5));
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
                                            SharedPreferences prefs = getContext().getSharedPreferences("zona6",Context.MODE_PRIVATE);
                                            Integer idZ6 = (int)prefs.getLong("idZona6",-1);
                                            //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ6, Toast.LENGTH_LONG).show();
                                            conZ.put("nombre", etZona.getText().toString());

                                            long response = dbZ.update("zona", conZ, "idZona=" + idZ6, null);
                                            if (response > 0) {
                                                Toast.makeText(getActivity(), "Editado con exito", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                                            }
                                            prefs.edit().remove("idZona6").commit();
                                        }
                                        actualizarZonas(viewEZ);
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        break;
                }

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                btnAplicar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.contenedor, new PrincipalFragment(), "Principal");
                        fr.commit();
                    }
                });


                //evito que se cierre al presionar fuera del dialog
                dialog.setCanceledOnTouchOutside(false);
            }
        });

        return view;
    }

    public ArrayList<String> actualizarZonas(View v){
        //Tabla ZONA
        final AlarmaSQLite bdZ = new AlarmaSQLite(getActivity(),"zona",null,1);
        final SQLiteDatabase dbZ = bdZ.getWritableDatabase();
        final ContentValues conZ = new ContentValues();
        //selecciono todas las zonas almacenadas segun el id de la alarma que traigo del tabZonas
        ArrayList<Zona> zonas = new ArrayList<>();
        Cursor c = dbZ.rawQuery("SELECT * FROM zona where idAlarma="+idAlarmaTabZona,null);
        if(c.moveToFirst()){
            do{
                zonas.add(new Zona(c.getInt(0),c.getInt(1),c.getString(2)));
            }while(c.moveToNext());
        }
        final ArrayList<String> arreglo = new ArrayList<>();
        for (int i = 0;i<zonas.size();i++){
            arreglo.add (zonas.get(i).getNombre());
        }

        if(arreglo.size() == 1) {
            t1.setText(arreglo.get(0));
        }
        if(arreglo.size() == 2) {
            t1.setText(arreglo.get(0));
            t2.setText(arreglo.get(1));
        }
        if(arreglo.size() == 3) {
            t1.setText(arreglo.get(0));
            t2.setText(arreglo.get(1));
            t3.setText(arreglo.get(2));
        }
        if(arreglo.size() == 4) {
            t1.setText(arreglo.get(0));
            t2.setText(arreglo.get(1));
            t3.setText(arreglo.get(2));
            t4.setText(arreglo.get(3));
        }
        if(arreglo.size() == 5) {
            t1.setText(arreglo.get(0));
            t2.setText(arreglo.get(1));
            t3.setText(arreglo.get(2));
            t4.setText(arreglo.get(3));
            t5.setText(arreglo.get(4));
        }
        if(arreglo.size() == 6) {
            t1.setText(arreglo.get(0));
            t2.setText(arreglo.get(1));
            t3.setText(arreglo.get(2));
            t4.setText(arreglo.get(3));
            t5.setText(arreglo.get(4));
            t6.setText(arreglo.get(5));
        }
        return arreglo;
    }

    public void eliminarShared(View v){
        prefs2 = getContext().getSharedPreferences("idAlarmaPrin",Context.MODE_PRIVATE);
        idAlarmaTabZona=(int)prefs2.getLong("idAlarmaPrincipal",-1);
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
        //Toast.makeText(getContext(),"Fragment Principal",Toast.LENGTH_LONG).show();
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

