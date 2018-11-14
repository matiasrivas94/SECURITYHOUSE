package com.example.matiasezequiel.security_house.Adapter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class CamaraAdapter {
    private static final String NAME="camara";
    private SQLiteDatabase sqlDB;

    public CamaraAdapter(SQLiteDatabase sqlDB) {
        this.sqlDB = sqlDB;
    }


    private class Columns implements BaseColumns {

        public final static String IDC = "idCamara";
        public final static String NOMBRE = "nombre";
        public final static String IP = "ip";
        public final static String USUARIO = "usuario";
        public final static String PASSWORD = "password";
        public final static String PUERTO = "puerto";
    }

    private final static String[] COLUMNS={
            CamaraAdapter.Columns.IDC,Columns.NOMBRE, Columns.IP, Columns.USUARIO, Columns.PASSWORD, Columns.PUERTO
    };

    public final static String CR_TABLE="create table if not exists "+ NAME + "(" +
            CamaraAdapter.Columns.IDC + " integer primary key autoincrement, "
            + Columns.NOMBRE + " text not null, "
            + Columns.IP + " text not null, "
            + Columns.USUARIO + " text not null, "
            + Columns.PASSWORD + " text not null, "
            + Columns.PUERTO + " integer)";


    public boolean insert(String nombre, String ip, String usuario, String password, int puerto){

        ContentValues values= new ContentValues();
        values.put(Columns.NOMBRE, nombre);
        values.put(Columns.IP, ip);
        values.put(Columns.USUARIO, usuario);
        values.put(Columns.PASSWORD, password);
        values.put(Columns.PUERTO, puerto);

        return  sqlDB.insert(NAME,null,values)>0;
    }

    public boolean delete (int id){

        String whereClause="idCamara=?";
        String[] whereArgs={String.valueOf(id)};

        return  sqlDB.delete(NAME,whereClause,whereArgs)>0;
    }

    public Cursor getNombres(){

        String [] columns ={Columns.IP};

        return sqlDB.query(NAME,columns,null,null,null,null,null);
    }

    public String getName(){
        return NAME;
    }

    public static String[] getCOLUMNS() {
        return COLUMNS;
    }

    public boolean isEmpty(){

        return sqlDB.query(NAME,COLUMNS,null,null,null,null,null).getCount()==0;
    }

    public Cursor getDatos(){
        Cursor c = sqlDB.rawQuery("SELECT * FROM camara",null);
        return c;
    }
}
