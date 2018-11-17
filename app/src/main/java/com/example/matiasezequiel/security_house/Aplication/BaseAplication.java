package com.example.matiasezequiel.security_house.Aplication;

import android.app.Application;
import android.database.Cursor;

import com.example.matiasezequiel.security_house.Adapter.DBAdapter;
import com.example.matiasezequiel.security_house.Alarma;
import com.example.matiasezequiel.security_house.App;
import com.example.matiasezequiel.security_house.Camara;
import com.example.matiasezequiel.security_house.Zona;

import java.util.ArrayList;

public class BaseAplication extends Application {
    DBAdapter dbAdapter;

    @Override
    public void onCreate() {

        dbAdapter= new DBAdapter(getApplicationContext());
        dbAdapter.open();
        super.onCreate();
    }

    @Override
    public void onTerminate() {

        dbAdapter.close();
        super.onTerminate();
    }

    public void rellenarDatosTabla1(){
        if(dbAdapter.alarmaIsEmpty()){
            for(int i=0; i<30;i++){

                String nombre= "nombre"+ String.valueOf(i);
                String tipo= "tipo"+ String.valueOf(i);
                String numTelefono= "numTelefono"+ String.valueOf(i);
                String clave = "clave"+ String.valueOf(i);

                dbAdapter.alarmaInsert(nombre,tipo,numTelefono,clave);
            }
        }
    }

    //insert
    public boolean insertarAlarma(String nombre, String tipo, String numTelefono, String clave){
        return dbAdapter.alarmaInsert(nombre,tipo,numTelefono,clave);
    }

    public boolean insertarZona(int idAlarma, int estado, int notificacion){
        return dbAdapter.zonaInsert(idAlarma,estado,notificacion);
    }

    public boolean insertarCamara(String nombre, String ip, String usuario, String password, int puerto){
        return dbAdapter.camaraInsert(nombre,ip,usuario,password,puerto);
    }

    //listar
    public ArrayList<Alarma> nombresAlarmas(){
        ArrayList<Alarma> lista= new ArrayList<Alarma>();
        Cursor c = dbAdapter.getDatosAlarma();
        Alarma alarm;

        if(c.moveToFirst()){
            do{
                alarm = new Alarma(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4));
                lista.add(alarm);
            } while (c.moveToNext());
        }
        return lista;
    }

    public ArrayList<Zona> nombresZonas(){
        ArrayList<Zona> lista= new ArrayList<Zona>();
        Cursor c = dbAdapter.getDatosAlarma();
        Zona zona;

        if(c.moveToFirst()){
            do{
                zona = new Zona(c.getInt(0),c.getInt(1),c.getString(2),c.getInt(3),c.getInt(4));
                lista.add(zona);
            } while (c.moveToNext());
        }
        return lista;
    }

    public ArrayList<Camara> nombresCamaras(){

        ArrayList<Camara> lista= new ArrayList<Camara>();
        Cursor c = dbAdapter.getDatosCamara();
        Camara cam;

        if(c.moveToFirst()){
            do{
                cam = new Camara(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5));
                lista.add(cam);
            } while (c.moveToNext());
        }
        return lista;
    }

    //buscar por ID
    public Alarma getAlarma(int idAlarma){
        return dbAdapter.getAlarma(idAlarma);
    }
    public Camara getCamara(int idCamara){
        return dbAdapter.getCamara(idCamara);
    }

    public App getApp(int idApp){
        return dbAdapter.getApp(idApp);
    }

    //busco por NUMTELEFONO
    public ArrayList<Alarma> getAlarmaNum(String numTelefono){
        return dbAdapter.getAlarmaNum(numTelefono);
    }

    //busco zonas por idAlarma
    public ArrayList<Zona> getZonas(int idAlarma){
        return dbAdapter.getZonas(idAlarma);
    }

    //devuelvo el idAlarma de la ultima alarma creada
    public int ultimaAlarmaIngresada(){
        return dbAdapter.ultimaAlarmaIngresada();
    }

    //UPDATE TABLES
    public long updateAlarma(int idAlarma, String nombre, String tipo, String numTelefono, String clave){
        return dbAdapter.updateAlarma(idAlarma,nombre,tipo,numTelefono,clave);
    }
    public long updateAlarmaContraseña(int idAlarma, String clave){
        return dbAdapter.updateAlarmaContraseña(idAlarma,clave);
    }

    public long updateNotiZona(int idZona, int noti){
        return dbAdapter.updateNotiZona(idZona,noti);
    }

    public long updateNombreZona(int idZona, String nombre){
        return dbAdapter.updateNombreZona(idZona,nombre);
    }

    public long updateEstadoZona(int idZona, int estado){
        return dbAdapter.updateEstadoZona(idZona,estado);
    }

    public long updateCamara(int idCamara, String nombre, String ip, String usuario, String contraseña, int puerto){
        return dbAdapter.updateCamara(idCamara,nombre,ip,usuario,contraseña,puerto);
    }

    //DELETE TABLES
    public boolean borrarAlarma(int idAlarma){
        return dbAdapter.borrarAlarma(idAlarma);
    }
    public boolean borrarZona(int idZona){
        return dbAdapter.borrarZona(idZona);
    }
    public boolean borrarCamara(int idCamara){
        return dbAdapter.borrarCamara(idCamara);
    }

}
