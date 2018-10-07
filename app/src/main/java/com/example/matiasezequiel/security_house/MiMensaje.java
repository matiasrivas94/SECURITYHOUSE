package com.example.matiasezequiel.security_house;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;


public class MiMensaje extends BroadcastReceiver {

    String celular;
    String textomensaje;

    char ultimo;
    Context context;

    String nombreAlarm;
    //Recibir y Leer Mensajes
    @Override
    public void onReceive(Context context, Intent intent){


        Log.d("MiMensaje","SMS Recibido");

        Bundle b = intent.getExtras(); //https://developer.android.com/reference/android/os/Bundle

        if(b!= null){
            Object[] pdus = (Object[]) b.get("pdus");

            SmsMessage[] mensajes = new SmsMessage[pdus.length];

            for(int i=0;i<mensajes.length;i++){

                //https://developer.android.com/reference/android/os/Build.VERSION_CODES
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    String format = b.getString("format");
                    mensajes[i] = SmsMessage.createFromPdu((byte[])pdus[i],format);
                }
                else{
                    mensajes[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                }

                celular = mensajes[i].getOriginatingAddress();// https://developer.android.com/reference/android/telephony/SmsMessage#getOriginatingAddress()
                textomensaje = mensajes[i].getMessageBody();// Devuelve el cuerpo del mensaje como una Cadena, si existe y está basada en texto.

                AlarmaSQLite bd = new AlarmaSQLite(context,"alarma",null,1);
                SQLiteDatabase db = bd.getReadableDatabase();
                String nomCelular = celular;

                SQLiteStatement s1 = db.compileStatement("SELECT nombre FROM alarma WHERE numTelefono LIKE '%"+celular+"'");
                nombreAlarm = s1.simpleQueryForString();

                //Funcion para recuperar el ultimo caracter(entero) del mensaje para saber que ZONA hay actividad
                if((mensajes[i].getOriginatingAddress()).equals(celular))
                {
                    ultimo = textomensaje.charAt(textomensaje.length()-1);
                    int x = ultimo - '0';
                    if((x >= 1) && (x <= 6)) {
                        //verificar cantidad de zonas
                        SQLiteStatement s2 = db.compileStatement("SELECT cantZonas FROM alarma WHERE numTelefono LIKE '%"+celular+"'");
                        int canZonas = (int)s2.simpleQueryForLong();
                        if(canZonas >= 1 && x <= canZonas ) {
                            Notificar(context);
                        }
                    }
                }
            }
        }
    }

    //Enviar Mensajes
    public void enviarMensaje(String numero, String mensaje)
    {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(numero, null, mensaje, null, null);
        //Toast.makeText(context, "SEND", Toast.LENGTH_LONG).show();
    }

    //Notificacion de Mensaje Entrante
    public void Notificar(Context context){

        String canal = "MiCanal";
        NotificationCompat.Builder mBuilder;
        NotificationManager mNotifyMgr =(NotificationManager)context.getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        int icono = R.mipmap.ic_launcher;
        Intent i=new Intent(String.valueOf(context));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);
        //PendingIntent: Una descripción de una acción de intención y objetivo para realizar con ella.

        mBuilder =new NotificationCompat.Builder(context.getApplicationContext(), canal)
                .setContentIntent(pendingIntent)
                .setSmallIcon(icono)
                .setContentTitle(nombreAlarm)
                .setContentText("Se ha activado la ZONA "+ultimo)
                .setAutoCancel(true);
        mNotifyMgr.notify(1, mBuilder.build());
    }
}
