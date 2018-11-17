package com.example.matiasezequiel.security_house.Adapter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.example.matiasezequiel.security_house.App;

public class AppAdapter {
    private static final String NAME="app";
    private SQLiteDatabase sqlDB;

    public AppAdapter(SQLiteDatabase sqlDB) {
        this.sqlDB = sqlDB;
    }


    private class Columns implements BaseColumns {

        public final static String IDA = "idApp";
        public final static String PREMIUM = "premium";
    }

    private final static String[] COLUMNS={
            Columns.IDA, Columns.PREMIUM
    };

    public final static String CR_TABLE="create table if not exists "+ NAME + "(" +
            Columns.IDA + " integer primary key autoincrement, "
            + Columns.PREMIUM + " integer)";

    public boolean insert(int idApp, int premium){

        ContentValues values= new ContentValues();
        values.put(Columns.IDA,idApp);
        values.put(Columns.PREMIUM,premium);

        return  sqlDB.insert(NAME,null,values)>0;
    }

    public boolean delete (int id){
        String whereClause="idApp=?";
        String[] whereArgs={String.valueOf(id)};

        return  sqlDB.delete(NAME,whereClause,whereArgs)>0;
    }

    public App getApp(int idApp){
        Cursor c = sqlDB.rawQuery("SELECT * FROM app WHERE idApp = "+idApp,null);
        c.moveToFirst();
        return new App(c.getInt(c.getColumnIndex("idApp")),c.getInt(c.getColumnIndex("premium")));
    }

    public Cursor getNombres(){
        String [] columns ={Columns.PREMIUM};
        return sqlDB.query(NAME,columns,null,null,null,null,null);
    }

    public String getName(){
        return NAME;
    }

    public static String[] getCOLUMNS() {
        return COLUMNS;
    }

    public boolean isEmpty(){ return sqlDB.query(NAME,COLUMNS,null,null,null,null,null).getCount()==0; }

}
