package com.example.matiasezequiel.security_house;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import static android.Manifest.permission.RECEIVE_SMS;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private FloatingActionButton fab;
    FragmentManager fr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        validaPermisos();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Infla el AlarmasFragment como Fragment principal
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        SharedPreferences prefs = getSharedPreferences("noti", Context.MODE_PRIVATE);
        String n = prefs.getString("notificacion", " ");
        //Toast.makeText(this,"Noti: "+n, Toast.LENGTH_SHORT).show();
        if (n.equals("dale")) {
            //Toast.makeText(this,"Voy al TAB ZONAS", Toast.LENGTH_SHORT).show();
            fragmentManager.beginTransaction().replace(R.id.contenedor, new ZonasFragment(), "Zonas").commit();
            prefs.edit().remove("notificacion").commit();
        } else {
            //Toast.makeText(this,"Llego mal,VOY AL ALARMAS", Toast.LENGTH_SHORT).show();
            fragmentManager.beginTransaction().replace(R.id.contenedor, new AlarmasFragment(), "Alarmas").commit();
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                fragmentManager.beginTransaction().replace(R.id.contenedor, new CrearAlarmaFragment(), "CrearAlarma").addToBackStack(null)
                        .commit();
            }
        });

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    private void handleIntent(Intent intent) {
        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();
        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null){
            //metodo_pelado
        }
    }

    public void showFloatingActionButton(){
        fab.show();
    }
    public void hideFloatingActionButton(){
        fab.hide();
    }


/*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    public String getVisibleFragment() {
        fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment.getTag().toString();
            }
        }
        return null;
    }

    private void clearBackStack() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        else {
            if (getVisibleFragment() == "Alarmas") {
                // Menu con alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //builder.setTitle("Elegir Opcion");
                builder.setTitle("Desea Salir de la Aplicacion?");
                // add a list
                String[] opcion = {"Salir", "Cancelar"};
                builder.setItems(opcion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                finish();
                                break;
                            case 1:
                                //Abrir un alertDialog preguntando si desea cancelar
                                dialog.cancel();
                                break;
                        }
                    }
                });
                // create and show the alert dialog

                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                return;
            }

            if (getVisibleFragment().equals("Zonas")) {
                fragmentManager.beginTransaction().replace(R.id.contenedor, new AlarmasFragment(), "Alarmas").commit();
                clearBackStack();
                return;
            }
            if(getVisibleFragment().equals("Camaras")){
                // Menu con alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //builder.setTitle("Elegir Opcion");
                builder.setTitle("Desea Salir de la Aplicacion?");
                // add a list
                String[] opcion = {"Salir", "Cancelar"};
                builder.setItems(opcion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                finish();
                                break;
                            case 1:
                                //Abrir un alertDialog preguntando si desea cancelar
                                dialog.cancel();
                                break;
                        }
                    }
                });
                // create and show the alert dialog

                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                return;
            }
            if (getVisibleFragment().equals("VerCamaras")) {
                fragmentManager.beginTransaction().replace(R.id.contenedor, new AlarmasFragment(), "Alarmas").commit();
                clearBackStack();
                return;
            }
            if (getVisibleFragment().equals("Config")) {
                fragmentManager.beginTransaction().replace(R.id.contenedor, new AlarmasFragment(), "Alarmas").commit();
                clearBackStack();
                return;
            }
            if (getVisibleFragment().equals("Contacto")) {
                fragmentManager.beginTransaction().replace(R.id.contenedor, new AlarmasFragment(), "Alarmas").commit();
                clearBackStack();
                return;
            }
            if (getVisibleFragment().equals("Camaras")) {
                // Menu con alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //builder.setTitle("Elegir Opcion");
                builder.setTitle("Desea Salir de la Aplicacion?");
                // add a list
                String[] opcion = {"Salir", "Cancelar"};
                builder.setItems(opcion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                finish();
                                break;
                            case 1:
                                //Abrir un alertDialog preguntando si desea cancelar
                                dialog.cancel();
                                break;
                        }
                    }
                });
                // create and show the alert dialog

                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                return;
            }
            if (getVisibleFragment().equals("QuienesSomos")) { fragmentManager.beginTransaction().replace(R.id.contenedor, new AlarmasFragment(), "Alarmas").commit();
                clearBackStack();
                return;
            }
            if(getVisibleFragment().equals("Reproducir")){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Desea cerrar la transmision?");
                // add a list
                String[] opcion = {"Si", "No"};
                builder.setItems(opcion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                fragmentManager.beginTransaction().replace(R.id.contenedor, new CamarasFragment(), "Camaras").commit();
                                clearBackStack();
                                break;
                            case 1:
                                dialog.cancel();
                                break;
                        }
                    }
                });
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                return;
            }
            if (getVisibleFragment().equals("Premium")) {
                fragmentManager.beginTransaction().replace(R.id.contenedor, new AlarmasFragment(), "Alarmas").commit();
                clearBackStack();
                return;
            }
            if (getVisibleFragment().equals("CrearCamara")) {
                int auxiliar = 0;
                SharedPreferences prefs1 = this.getSharedPreferences("aaa", Context.MODE_PRIVATE);
                auxiliar = (int) prefs1.getLong("auxiliar", 0); //error nose porque, controlar
                prefs1.edit().remove("auxiliar").commit();

                if (auxiliar == -1) {

                    // Menu con alert builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Los datos no se guardaron, salir de todos modos?");
                    // add a list
                    String[] opcion = {"Aceptar", "Cancelar"};
                    builder.setItems(opcion, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    fragmentManager.beginTransaction().replace(R.id.contenedor, new CamarasFragment(), "Camaras").commit();
                                    clearBackStack();
                                    break;
                                case 1:
                                    //Abrir un alertDialog preguntando si desea cancelar
                                    SharedPreferences.Editor editor1 = getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                                    editor1.putLong("auxiliar", -1);
                                    editor1.commit();
                                    dialog.cancel();
                                    break;
                            }
                        }
                    });
                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    return;
                }
                if (auxiliar == 0) {
                    fragmentManager.beginTransaction().replace(R.id.contenedor, new CamarasFragment(), "Camaras").commit();
                    return;
                }
            }
            if (getVisibleFragment() == "CrearAlarma") {
                int auxiliar = 0;
                SharedPreferences prefs1 = this.getSharedPreferences("aaa", Context.MODE_PRIVATE);
                auxiliar = (int) prefs1.getLong("auxiliar", 0);
                prefs1.edit().remove("auxiliar").commit();

                if (auxiliar == -1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Los datos no se guardaron. \nEstas seguro que desea cancelar?");
                    builder.setTitle("Cancelar nueva alarma");
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor1 = getSharedPreferences("aaa", Context.MODE_PRIVATE).edit();
                            editor1.putLong("auxiliar", -1);
                            editor1.commit();
                            dialog.cancel();
                        }
                    });
                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fragmentManager.beginTransaction().replace(R.id.contenedor, new AlarmasFragment(), "Alarmas").commit();
                            clearBackStack();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    return;
                }
                if (auxiliar == 0) {
                    fragmentManager.beginTransaction().replace(R.id.contenedor, new AlarmasFragment(), "Alarmas").commit();
                    clearBackStack();
                    return;
                }

            }
            //super.onBackPressed();
            //Toast.makeText(this, getVisibleFragment() + "2", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //Toast.makeText(this,"inflandospiolas", Toast.LENGTH_SHORT).show();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            QuienesSomosFragment somos = new QuienesSomosFragment();
            fragmentManager.beginTransaction().replace(R.id.contenedor, somos, "QuienesSomos").addToBackStack(null)
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }

        if (id == R.id.nav_camera) {
            AlarmasFragment alarmas = new AlarmasFragment();
            fragmentManager.beginTransaction().replace(R.id.contenedor,alarmas,"Alarmas" ).addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_gallery) { //Lista de camaras
            CamarasFragment camaras = new CamarasFragment();
            fragmentManager.beginTransaction().replace(R.id.contenedor,camaras,"Camaras" ).addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_share) {
            ContactoFragment contacto = new ContactoFragment();
            fragmentManager.beginTransaction().replace(R.id.contenedor, contacto, "Contacto").addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_send) {
            QuienesSomosFragment somos = new QuienesSomosFragment();
            fragmentManager.beginTransaction().replace(R.id.contenedor, somos, "QuienesSomos").addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_premium) {
            PremiumFragment premium = new PremiumFragment();
            fragmentManager.beginTransaction().replace(R.id.contenedor, premium, "Premium").addToBackStack(null)
                    .commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private boolean validaPermisos() {

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(RECEIVE_SMS)== PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(RECEIVE_SMS))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{RECEIVE_SMS},100);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED){

            }else{
                solicitarPermisosManual();
            }
        }

    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(MainActivity.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(MainActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{RECEIVE_SMS},100);
            }
        });
        dialogo.show();
    }

}
