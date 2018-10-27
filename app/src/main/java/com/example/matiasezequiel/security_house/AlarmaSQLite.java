package com.example.matiasezequiel.security_house;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmaSQLite extends SQLiteOpenHelper {

    public String alarma = "CREATE TABLE alarma(idAlarma INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT,tipo TEXT,numTelefono TEXT,clave TEXT,"+
            "cantZonas INTEGER)";
    public String zona = "CREATE TABLE zona(idZona INTEGER PRIMARY KEY AUTOINCREMENT,idAlarma INTEGER,nombre TEXT,estado INTEGER,"+
            "notificacion INTEGER)";
    public String camara = "CREATE TABLE camara(idCamara INTEGER PRIMARY KEY AUTOINCREMENT,ip TEXT,usuario TEXT,password TEXT,"+
            "puerto TEXT)";

    public AlarmaSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(alarma);
        sqLiteDatabase.execSQL(zona);
        sqLiteDatabase.execSQL(camara);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



}
