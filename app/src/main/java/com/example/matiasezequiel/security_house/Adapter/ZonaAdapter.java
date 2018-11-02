package com.example.matiasezequiel.security_house.Adapter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.matiasezequiel.security_house.Zona;

import java.util.ArrayList;

public class ZonaAdapter {
    private static final String NAME="zona";
    private SQLiteDatabase sqlDB;

    public ZonaAdapter(SQLiteDatabase sqlDB) {
        this.sqlDB = sqlDB;
    }


    private class Columns implements BaseColumns {

        public final static String IDZ = "idZona";
        public final static String IDA = "idAlarma";
        public final static String NOMBRE = "nombre";
        public final static String ESTADO = "estado";
        public final static String NOTIFICACION = "notificacion";
    }

    private final static String[] COLUMNS={
            ZonaAdapter.Columns.IDZ,ZonaAdapter.Columns.IDA, ZonaAdapter.Columns.NOMBRE, Columns.ESTADO, Columns.NOTIFICACION
    };

    public final static String CR_TABLE="create table if not exists "+ NAME + "(" +
            ZonaAdapter.Columns.IDZ + " integer primary key autoincrement, "
            + Columns.IDA + " integer, "
            + ZonaAdapter.Columns.NOMBRE + " text not null, "
            + Columns.ESTADO + " integer, "
            + Columns.NOTIFICACION + " integer)";


    //inserto 6 zonas exactas
    public boolean insert(int idAlarma, int estado, int notificacion){
        boolean res = false;
        ContentValues values= new ContentValues();
        for(int x = 1; x < 7; x++) {
            values.put(Columns.IDA, idAlarma);
            values.put(Columns.NOMBRE, "Zona "+ x);
            values.put(Columns.ESTADO, estado);
            values.put(Columns.NOTIFICACION, notificacion);
            res = sqlDB.insert(NAME,null,values)>0;
        }
        return res;
    }


    public boolean delete (int id){

        String whereClause="idZona=?";
        String[] whereArgs={String.valueOf(id)};

        return  sqlDB.delete(NAME,whereClause,whereArgs)>0;
    }

    public Cursor getNombres(){

        String [] columns ={ZonaAdapter.Columns.NOMBRE};

        return sqlDB.query(NAME,columns,null,null,null,null,null);
    }

    public String getName(){
        return NAME;
    }

    public static String[] getCOLUMNS() {
        return COLUMNS;
    }

    public boolean isEmpty(){ return sqlDB.query(NAME,COLUMNS,null,null,null,null,null).getCount()==0; }

    public Cursor getDatos(){
        return sqlDB.query(NAME,COLUMNS,null,null,null,null,null);
    }

    //busco las zonas segun idAlarma
    public ArrayList<Zona> getZonas(int idAlarma){
        ArrayList<Zona> zonas = new ArrayList<>();
        Cursor c = sqlDB.rawQuery("SELECT * FROM zona where idAlarma="+idAlarma,null);
        if(c.moveToFirst()){
            do{
                zonas.add(new Zona(c.getInt(0),c.getInt(1),c.getString(2),c.getInt(3),c.getInt(4)));
            }while(c.moveToNext());
        }
        return zonas;
    }





}
