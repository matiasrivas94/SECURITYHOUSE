package com.example.matiasezequiel.security_house.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.matiasezequiel.security_house.Alarma;
import com.example.matiasezequiel.security_house.App;
import com.example.matiasezequiel.security_house.Camara;
import com.example.matiasezequiel.security_house.Zona;

import java.util.ArrayList;

public class DBAdapter {
    private DBHelper dbHelper;
    private SQLiteDatabase sqlDB;

    private final static int DB_VERSION=1;
    private final static String DB_NAME="AdminSoft";

    private AlarmaAdapter alarma;
    private ZonaAdapter zona;
    private CamaraAdapter camara;
    private AppAdapter app;


    public DBAdapter(Context context){
        dbHelper = new DBHelper(context);
    }

    public void open(){
        sqlDB = dbHelper.getWritableDatabase();

        alarma = new AlarmaAdapter(sqlDB);
        zona = new ZonaAdapter(sqlDB);
        camara = new CamaraAdapter(sqlDB);
        app = new AppAdapter(sqlDB);
    }

    public void close(){
        sqlDB.close();
    }

    public boolean alarmaIsEmpty(){
        return alarma.isEmpty();
    }

    public boolean alarmaInsert(String nombre, String tipo, String numTelefono, String clave){
        return alarma.insert(nombre, tipo, numTelefono, clave);
    }

    public boolean zonaInsert(int idAlarma, int estado, int notificacion){
        return zona.insert( idAlarma, estado, notificacion);
    }
    public boolean camaraInsert(String nombre, String ip, String usuario, String password, int puerto){
        return camara.insert(nombre, ip, usuario, password, puerto);
    }


    //Alarma
    public Cursor getNombresTabla1CursorAlarma(){
        return alarma.getNombres();
    }

    public Cursor getDatosAlarma(){
        return alarma.getDatos();
    }

    public boolean borrarAlarma(int id){
        return alarma.delete(id);
    }

    //Zona
    public Cursor getNombresTabla1CursorZona(){
        return zona.getNombres();
    }

    public Cursor getDatosZona(){
        return zona.getDatos();
    }

    public boolean borrarZona(int id){
        return zona.delete(id);
    }

    //Camara
    public Cursor getNombresTabla1CursorCamara(){
        return camara.getNombres();
    }

    public Cursor getDatosCamara(){
        return camara.getDatos();
    }

    public boolean borrarCamara(int id){
        return camara.delete(id);
    }


    //Metodos de consulta a la BD

    //devuelvo alarma segun id
    public Alarma getAlarma(int idAlarma){
        return alarma.getAlarma(idAlarma);
    }

    public App getApp(int idApp){
        return app.getApp(idApp);
    }
    //devuelvo una alarma segun numTelefono
    public ArrayList<Alarma> getAlarmaNum(String numTelefono){
        return alarma.getAlarmaNumTelefono(numTelefono);
    }
    public Camara getCamara(int idCamara){
        return camara.getCamara(idCamara);
    }

    //obtengo las zonas segun idAlarma
    public ArrayList<Zona> getZonas(int idAlarma){
        return zona.getZonas(idAlarma);
    }


    //para actualizar tablas
    public long updateAlarma(int idAlarma, String nombre, String tipo, String numTelefono, String clave){
        ContentValues val = new ContentValues();
        val.put("nombre",nombre);
        val.put("tipo",tipo);
        val.put("numTelefono",numTelefono);
        val.put("clave",clave);
        long response = sqlDB.update("alarma",val,"idAlarma="+idAlarma,null);
        return response;
    }

    public long updateAlarmaContraseña(int idAlarma, String clave){
        ContentValues val = new ContentValues();
        val.put("clave",clave);
        long response = sqlDB.update("alarma",val,"idAlarma="+idAlarma,null);
        return response;
    }

    public long updateZona(int idZona,int idAlarma, String nombre, int estado, int notificacion){
        ContentValues val = new ContentValues();
        val.put("idAlarma",idAlarma);
        val.put("nombre",nombre);
        val.put("estado",estado);
        val.put("notificacion",notificacion);
        long response = sqlDB.update("zona",val,"idZona="+idZona,null);
        return response;
    }

    public long updateCamara(int idCamara, String nombre, String ip, String usuario, String contraseña, int puerto){
        ContentValues val = new ContentValues();
        val.put("nombre",nombre);
        val.put("ip",ip);
        val.put("usuario",usuario);
        val.put("password",contraseña);
        val.put("puerto",puerto);
        long response = sqlDB.update("camara",val,"idCamara="+idCamara,null);
        return response;
    }

    //actulizo notificacion de una zona
    public long updateNotiZona(int idZona, int noti){
        ContentValues val = new ContentValues();
        val.put("notificacion",noti);
        long response = sqlDB.update("zona",val,"idZona="+idZona,null);
        return response;
    }

    //actualizo el nombre de las zonas
    public long updateNombreZona(int idZona, String nombre){
        ContentValues val = new ContentValues();
        val.put("nombre",nombre);
        long response = sqlDB.update("zona",val,"idZona="+idZona,null);
        return response;
    }

    //actualizo el nombre de las zonas
    public long updateEstadoZona(int idZona, int estado){
        ContentValues val = new ContentValues();
        val.put("estado",estado);
        long response = sqlDB.update("zona",val,"idZona="+idZona,null);
        return response;
    }

    //actualizo el atributo premium de la tabla app
    public long updatePremiumApp(int idApp, int premium){
        ContentValues val = new ContentValues();
        val.put("premium",premium);
        long response = sqlDB.update("app",val,"idApp="+idApp,null);
        return response;
    }

    //devuelvo el idAlarma de la ultima alarma creada
    public int ultimaAlarmaIngresada(){
        return alarma.ultimaAlarmaIngresada();
    }



    private class DBHelper extends SQLiteOpenHelper {


        public DBHelper (Context context){
            super(context,DB_NAME,null,DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(AlarmaAdapter.CR_TABLE);
            db.execSQL(ZonaAdapter.CR_TABLE);
            db.execSQL(CamaraAdapter.CR_TABLE);
            db.execSQL(AppAdapter.CR_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table ifexists"+AlarmaAdapter.CR_TABLE);
            db.execSQL("drop table ifexists"+ZonaAdapter.CR_TABLE);
            db.execSQL("drop table ifexists"+CamaraAdapter.CR_TABLE);
            db.execSQL("drop table ifexists"+AppAdapter.CR_TABLE);
        }
    }
}
