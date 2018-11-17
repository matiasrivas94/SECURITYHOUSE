package com.example.matiasezequiel.security_house.Adapter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.example.matiasezequiel.security_house.Alarma;

import java.util.ArrayList;

public class AlarmaAdapter {
    private static final String NAME="alarma";
    private SQLiteDatabase sqlDB;

    public AlarmaAdapter(SQLiteDatabase sqlDB) {
        this.sqlDB = sqlDB;
    }


    private class Columns implements BaseColumns {

        public final static String IDA = "idAlarma";
        public final static String NOMBRE = "nombre";
        public final static String TIPO = "tipo";
        public final static String NUMTELEFONO = "numTelefono";
        public final static String CLAVE = "clave";
    }

    private final static String[] COLUMNS={
            Columns.IDA,Columns.NOMBRE,Columns.TIPO,Columns.NUMTELEFONO,Columns.CLAVE
    };

    public final static String CR_TABLE="create table if not exists "+ NAME + "(" +
            Columns.IDA + " integer primary key autoincrement, "
            +Columns.NOMBRE + " text not null, "
            +Columns.TIPO + " text not null, "
            +Columns.NUMTELEFONO + " text not null,"
            +Columns.CLAVE + " text not null)";


    public boolean insert(String nombre, String tipo, String numTelefono, String clave){

        ContentValues values= new ContentValues();
        values.put(Columns.NOMBRE,nombre);
        values.put(Columns.TIPO,tipo);
        values.put(Columns.NUMTELEFONO,numTelefono);
        values.put(Columns.CLAVE,clave);

        return  sqlDB.insert(NAME,null,values)>0;
    }

    public boolean delete (int id){
        String whereClause="idAlarma=?";
        String[] whereArgs={String.valueOf(id)};

        return  sqlDB.delete(NAME,whereClause,whereArgs)>0;
    }


    //devuelvo una alarma segun el idAlarma para modificarla
    public Alarma getAlarma(int idAlarma){
        Cursor c = sqlDB.rawQuery("SELECT * FROM alarma WHERE idAlarma = "+idAlarma,null);
        c.moveToFirst();
        return new Alarma(c.getInt(c.getColumnIndex("idAlarma")),c.getString(c.getColumnIndex("nombre")),c.getString(c.getColumnIndex("tipo")),c.getString(c.getColumnIndex("numTelefono")),c.getString(c.getColumnIndex("clave")));
    }

    //devuelvo una alarma segun el telefono
    /*public Alarma getAlarmaNumTelefono(String numTelefono){
        Cursor c = sqlDB.rawQuery("SELECT * FROM alarma WHERE numTelefono LIKE '%"+numTelefono+"'",null);
        c.moveToFirst();
        return new Alarma(c.getInt(c.getColumnIndex("idAlarma")),c.getString(c.getColumnIndex("nombre")),c.getString(c.getColumnIndex("tipo")),c.getString(c.getColumnIndex("numTelefono")),c.getString(c.getColumnIndex("clave")));
    }*/

    public ArrayList<Alarma> getAlarmaNumTelefono(String numTelefono){
        ArrayList<Alarma> alarma = new ArrayList<>();
        Cursor c = sqlDB.rawQuery("SELECT * FROM alarma WHERE numTelefono LIKE '%"+numTelefono+"'",null);
        if(c.moveToFirst()){
            do{
                alarma.add(new Alarma(c.getInt(c.getColumnIndex("idAlarma")),c.getString(c.getColumnIndex("nombre")),c.getString(c.getColumnIndex("tipo")),c.getString(c.getColumnIndex("numTelefono")),c.getString(c.getColumnIndex("clave"))));
            }while(c.moveToNext());
        }
        return alarma;
    }

    //devuelvo el idAlarma de la ultima alarma ingresada
    public int ultimaAlarmaIngresada(){
        SQLiteStatement s = sqlDB.compileStatement( "SELECT MAX(idAlarma) FROM alarma");
        int idAlarma = (int)s.simpleQueryForLong();
        return idAlarma;
    }

    public Cursor getNombres(){
        String [] columns ={Columns.NOMBRE};
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
        Cursor c = sqlDB.rawQuery("SELECT * FROM alarma",null);
        //return sqlDB.query(NAME,COLUMNS,null,null,null,null,null);
        return  c;
    }
}
