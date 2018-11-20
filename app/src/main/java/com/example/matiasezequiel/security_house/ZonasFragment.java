package com.example.matiasezequiel.security_house;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matiasezequiel.security_house.Aplication.BaseAplication;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class ZonasFragment extends Fragment {
    TextView titulo;
    ImageView iv_edit;
    Button aplicarZonas;
    static int agregando = 0;

    //----- VARIABLES DEL TAB ZONAS ----//
    ListView listaZonas;
    int clickAlarma=0;
    String estadoAlarma;
    SharedPreferences prefs4, prefs2;
    ArrayList<Zona> tabZonas;
    ArrayList<DatosItemZona> datosZonas;
    //------- FIN VARIABLES -------//

    int idAlarmaTabZona=0;
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
        final View view = inflater.inflate(R.layout.fragment_zonas, container, false);

        //------ TODO DEL TAB ZONAS --------//
        listaZonas = (ListView)view.findViewById(R.id.LVMostrarZonas);
        //Shared para saber el id de la alarma clickeada en la lista de las alarmas
        prefs2 = getContext().getSharedPreferences("cc",Context.MODE_PRIVATE);
        clickAlarma=(int)prefs2.getLong("idAlarma",-1);
        //Toast.makeText(this.getActivity(),"ID de la Alarma Seleccionada: " + clickAlarma, Toast.LENGTH_SHORT).show();

        //Shared para saber el id de la alarma clickeada en la lista de las alarmas
        prefs4 = getContext().getSharedPreferences("dd",Context.MODE_PRIVATE);
        estadoAlarma=prefs4.getString("estadoZonaString"," ");

        visibilidadSoloTextView(view);

        //--TODO DEL TAB ZONAS--//

        iv_edit = view.findViewById(R.id.view_edit);
        aplicarZonas = (Button) view.findViewById(R.id.btnAplicarZonas);

        //para cambiarle el encabezado al principal fragment con el nombre de la nueva alamra insertada
        titulo = (TextView) view.findViewById(R.id.textTituloAlarma);
        //Shared para saber el id de la alarma clickeada en la lista de las alarmas

        SharedPreferences prefs = getContext().getSharedPreferences("ee",Context.MODE_PRIVATE);
        String nombreAlarm = prefs.getString("nombreAlarma"," ");
        titulo.setText(nombreAlarm);


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

                //selecciono todas las zonas almacenadas segun el id de la alarma que traigo
                ArrayList<Zona> zonas = ((BaseAplication) getActivity().getApplication()).getZonas(idAlarmaTabZona);

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
                                prefsZ1 = getContext().getSharedPreferences("zona1",Context.MODE_PRIVATE);
                                Integer idZ1 = (int)prefsZ1.getLong("idZona1",-1);
                                //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ1, Toast.LENGTH_LONG).show();
                                long res = ((BaseAplication) getActivity().getApplication()).updateNombreZona(idZ1, etZona.getText().toString());
                                if (res > 0)
                                    Toast.makeText(getActivity(), "Nombre Actualizado", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getActivity(), "No se pudo actualizar el nombre", Toast.LENGTH_LONG).show();
                                prefsZ1.edit().remove("idZona1").commit();

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
                                prefsZ2 = getContext().getSharedPreferences("zona2",Context.MODE_PRIVATE);
                                Integer idZ2 = (int)prefsZ2.getLong("idZona2",-1);
                                //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ2, Toast.LENGTH_LONG).show();
                                long res = ((BaseAplication) getActivity().getApplication()).updateNombreZona(idZ2, etZona.getText().toString());
                                if (res > 0)
                                    Toast.makeText(getActivity(), "Nombre Actualizado", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getActivity(), "No se pudo actualizar el nombre", Toast.LENGTH_LONG).show();
                                prefsZ2.edit().remove("idZona2").commit();

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
                                prefsZ3 = getContext().getSharedPreferences("zona3",Context.MODE_PRIVATE);
                                Integer idZ3 = (int)prefsZ3.getLong("idZona3",-1);
                                //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ3, Toast.LENGTH_LONG).show();
                                long res = ((BaseAplication) getActivity().getApplication()).updateNombreZona(idZ3, etZona.getText().toString());
                                if (res > 0)
                                    Toast.makeText(getActivity(), "Nombre Actualizado", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getActivity(), "No se pudo actualizar el nombre", Toast.LENGTH_LONG).show();
                                prefsZ3.edit().remove("idZona3").commit();

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
                                prefsZ4 = getContext().getSharedPreferences("zona4",Context.MODE_PRIVATE);
                                Integer idZ4 = (int)prefsZ4.getLong("idZona4",-1);
                                //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ4, Toast.LENGTH_LONG).show();
                                long res = ((BaseAplication) getActivity().getApplication()).updateNombreZona(idZ4, etZona.getText().toString());
                                if (res > 0)
                                    Toast.makeText(getActivity(), "Nombre Actualizado", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getActivity(), "No se pudo actualizar el nombre", Toast.LENGTH_LONG).show();
                                prefsZ4.edit().remove("idZona4").commit();

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
                                prefsZ5 = getContext().getSharedPreferences("zona5",Context.MODE_PRIVATE);
                                Integer idZ5 = (int)prefsZ5.getLong("idZona5",-1);
                                //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ5, Toast.LENGTH_LONG).show();
                                long res = ((BaseAplication) getActivity().getApplication()).updateNombreZona(idZ5, etZona.getText().toString());
                                if (res > 0)
                                    Toast.makeText(getActivity(), "Nombre Actualizado", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getActivity(), "No se pudo actualizar el nombre", Toast.LENGTH_LONG).show();
                                    prefsZ5.edit().remove("idZona5").commit();

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
                                prefsZ6 = getContext().getSharedPreferences("zona6",Context.MODE_PRIVATE);
                                Integer idZ6 = (int)prefsZ6.getLong("idZona6",-1);
                                //Toast.makeText(getActivity(), "ID ZONA del SHARED: " + idZ6, Toast.LENGTH_LONG).show();
                                long res = ((BaseAplication) getActivity().getApplication()).updateNombreZona(idZ6, etZona.getText().toString());
                                if (res > 0)
                                    Toast.makeText(getActivity(), "Nombre Actualizado", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getActivity(), "No se pudo actualizar el nombre", Toast.LENGTH_LONG).show();
                                prefsZ6.edit().remove("idZona6").commit();

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
                        fr.replace(R.id.contenedor, new ZonasFragment(), "Zonas");
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
                        fr.replace(R.id.contenedor, new ZonasFragment(), "Zonas");
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

        if(c1.isChecked()) {
            try{
                long res = ((BaseAplication) getActivity().getApplication()).updateEstadoZona(idZona.get(0), 1);
                //Toast.makeText(getContext(), "CheckBox 1 ''SI'' está Tildado", Toast.LENGTH_LONG).show();
            }catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }else {
            try{
                long res = ((BaseAplication) getActivity().getApplication()).updateEstadoZona(idZona.get(0), 0);
            }catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }
        if(c2.isChecked()) {
            try{
                long res = ((BaseAplication) getActivity().getApplication()).updateEstadoZona(idZona.get(1), 1);
                //Toast.makeText(getContext(), "CheckBox 1 ''SI'' está Tildado", Toast.LENGTH_LONG).show();
            }catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }else {
            try{
                long res = ((BaseAplication) getActivity().getApplication()).updateEstadoZona(idZona.get(1), 0);
            }catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }
        if(c3.isChecked()) {
            try{
                long res = ((BaseAplication) getActivity().getApplication()).updateEstadoZona(idZona.get(2), 1);
                //Toast.makeText(getContext(), "CheckBox 1 ''SI'' está Tildado", Toast.LENGTH_LONG).show();
            }catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }else {
            try{
                long res = ((BaseAplication) getActivity().getApplication()).updateEstadoZona(idZona.get(2), 0);
            }catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }
        if(c4.isChecked()) {
            try{
                long res = ((BaseAplication) getActivity().getApplication()).updateEstadoZona(idZona.get(3), 1);
                //Toast.makeText(getContext(), "CheckBox 1 ''SI'' está Tildado", Toast.LENGTH_LONG).show();
            }catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }else {
            try{
                long res = ((BaseAplication) getActivity().getApplication()).updateEstadoZona(idZona.get(3), 0);
            }catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }
        if(c5.isChecked()) {
            try{
                long res = ((BaseAplication) getActivity().getApplication()).updateEstadoZona(idZona.get(4), 1);
                //Toast.makeText(getContext(), "CheckBox 1 ''SI'' está Tildado", Toast.LENGTH_LONG).show();
            }catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }else {
            try{
                long res = ((BaseAplication) getActivity().getApplication()).updateEstadoZona(idZona.get(4), 0);
            }catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }
        if(c6.isChecked()) {
            try{
                long res = ((BaseAplication) getActivity().getApplication()).updateEstadoZona(idZona.get(5), 1);
                //Toast.makeText(getContext(), "CheckBox 1 ''SI'' está Tildado", Toast.LENGTH_LONG).show();
            }catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }else {
            try{
                long res = ((BaseAplication) getActivity().getApplication()).updateEstadoZona(idZona.get(5), 0);
            }catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void actualizarZonas2(View v){
        try{
            ArrayList<Zona> zonas = ((BaseAplication) getActivity().getApplication()).getZonas(idAlarmaTabZona);
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
        }catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
    }
    public void eliminarShared(View v){
        prefs2 = getContext().getSharedPreferences("idAlarmaPrin",Context.MODE_PRIVATE);
        idAlarmaTabZona=(int)prefs2.getLong("idAlarmaPrincipal",-1);
        //Toast.makeText(getContext(),"ID Alarma: "+idAlarmaTabZona,Toast.LENGTH_LONG).show();
    }

    //----------- METODOS DEL TAB ZONAS --------- //

    public void visibilidadSoloTextView(View v) {

        int ultiIdAlarma=-1;
        //busco el idAlarma de la ultima alarma ingresada
        try{
            ultiIdAlarma = ((BaseAplication) getActivity().getApplication()).ultimaAlarmaIngresada();
        }catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        // Se crean los TextView cuando se crea una alarma
        if(estadoAlarma != " ")
        {
            //Shareds para el fragment principal
            SharedPreferences.Editor editor = getContext().getSharedPreferences("idAlarmaPrin",Context.MODE_PRIVATE).edit();
            editor.putLong("idAlarmaPrincipal", ultiIdAlarma);
            editor.commit();

            try{
                boolean insertZona = ((BaseAplication) getActivity().getApplication()).insertarZona(ultiIdAlarma,1,0);
            /*if(insertZona)
                Toast.makeText(this.getActivity(), "Zonas INSERTADAS", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this.getActivity(), "Error al insertar zonas", Toast.LENGTH_SHORT).show();
            */
            }catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            llenarLista(ultiIdAlarma);
            prefs4.edit().remove("estadoZonaString").commit();
        }

        // Se muestran las zonas segun la alarma que se clickee en la lista de alarmas
        if(estadoAlarma == " ")
        {
            //Shareds para el fragment principal
            SharedPreferences.Editor editor = getContext().getSharedPreferences("idAlarmaPrin",Context.MODE_PRIVATE).edit();
            editor.putLong("idAlarmaPrincipal", clickAlarma);
            editor.commit();
            llenarLista(clickAlarma);
        }
    }

    //llenarLista de Zonas
    public void llenarLista(int idAla){
        try{
            tabZonas = ((BaseAplication) getActivity().getApplication()).getZonas(idAla);
            datosZonas = new ArrayList<>();

            for(int x = 0; x < tabZonas.size(); x++){
                datosZonas.add(new DatosItemZona(x,tabZonas.get(x).getNombre(), R.drawable.snooze1));
            }
            AdaptadorZona adapter = new AdaptadorZona(this.getActivity(), datosZonas);
            listaZonas.setAdapter(adapter);
        }catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
    }
    //clase interna para manejar el item de la lista de las zonas
    private class AdaptadorZona extends BaseAdapter {
        Context contexto;
        List<DatosItemZona> listaObjetos;
        ArrayList<Zona> zonas;

        public AdaptadorZona(Context contexto, List<DatosItemZona> listaObjetos) {
            this.contexto = contexto;
            this.listaObjetos = listaObjetos;
        }
        @Override
        public int getCount() {
            return listaObjetos.size(); //retorna cantidad de la lista
        }
        @Override
        public Object getItem(int position) {
            return listaObjetos.get(position); //retorna el objeto de la posicion indicada
        }
        @Override
        public long getItemId(int position) {
            return listaObjetos.get(position).getId();
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View vista = convertView;

            LayoutInflater inflate = LayoutInflater.from(contexto);
            vista = inflate.inflate(R.layout.items_zonas, null);

            Button nombreZona = (Button) vista.findViewById(R.id.btNombreZona);
            ImageView estadoZona = (ImageView) vista.findViewById(R.id.ivNotificacionZona);

            nombreZona.setText(listaObjetos.get(position).getNombreZona());
            estadoZona.setImageResource(listaObjetos.get(position).getImagen());

            estadoZona.setVisibility(vista.INVISIBLE);

            //selecciono todas las zonas almacenadas segun el id de la alarma que traigo del tabZonas
            try{
                zonas = ((BaseAplication) getActivity().getApplication()).getZonas(clickAlarma);
            }catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            final ArrayList<Integer> noti = new ArrayList<>();
            final ArrayList<Integer> idZonas = new ArrayList<>();

            if(agregando == 0) {
                for (int i = 0; i < zonas.size(); i++) {
                    idZonas.add(zonas.get(i).getIdZona());
                    noti.add(zonas.get(i).getNotificacion());
                }
                //Log.d("Probando log", "Agregando 0");
            }else{
                for (int i = 0; i < zonas.size(); i++) {
                    idZonas.add(zonas.get(i).getIdZona());
                    estadoZona.setVisibility(View.INVISIBLE);
                    //noti.add(0);
                }
                //Log.d("Probando log",position+" ");
                if(position == 5){
                    agregando=0;
                }
            }
            if(noti.size() > 0) {
                if (noti.get(position).equals(1)) {
                    estadoZona.setVisibility(vista.VISIBLE);
                    new TextoParpadeante(getContext(),estadoZona);
                    //Boton del menu del item de la lista de las alarmas
                    estadoZona.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            SharedPreferences prefs1 = getContext().getSharedPreferences("time", Context.MODE_PRIVATE);
                            String detallesMje = prefs1.getString("hora", "Error");
                            detallesMje.substring(4,29); // no funciona
                            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                            final View vistaDetalles = getLayoutInflater().inflate(R.layout.dialog_detalles, null);
                            final Button aceptarDet = (Button)vistaDetalles.findViewById(R.id.btnAceptarDetalles);
                            final Button verCam = (Button)vistaDetalles.findViewById(R.id.btnVerCamaras);
                            final TextView detalles = (TextView)vistaDetalles.findViewById(R.id.tvDetallesMje);
                            detalles.setText("La hora y fecha en que la zona se activó es: \n"+detallesMje);
                            builder.setView(vistaDetalles);
                            final android.app.AlertDialog dialog = builder.create();

                            aceptarDet.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //estadoZona.setVisibility(v.INVISIBLE);
                                    long res =((BaseAplication)getActivity().getApplication()).updateNotiZona(idZonas.get(position),0);
                                    if(res > 0)
                                         Toast.makeText(getActivity().getApplication(), "Noti Actualizada", Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(getActivity().getApplication(), "No se pudo actualizar la Notificacion", Toast.LENGTH_LONG).show();

                                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                                    fr.replace(R.id.contenedor, new ZonasFragment(), "Zonas");
                                    fr.commit();
                                    dialog.dismiss();
                                }
                            });
                            verCam.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                                    fr.replace(R.id.contenedor, new CamarasFragment(), "Camaras");
                                    fr.commit();
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                            dialog.setCanceledOnTouchOutside(false);
                        }
                    });
                }
            }
            return vista;
        }

    }
    //------------- FIN METODOS TAB ZONA ----------//


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

