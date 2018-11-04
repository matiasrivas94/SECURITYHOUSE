package com.example.matiasezequiel.security_house;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.matiasezequiel.security_house.Aplication.BaseAplication;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;


public class MiMensaje extends BroadcastReceiver {
    String celular;
    String textomensaje;

    char ultimo;
    Context context;
    ArrayList<Alarma> a;
    String nombreAlarm;
    //Recibir y Leer Mensajes
    @Override
    public void onReceive(Context context, Intent intent){
        //Log.d("MiMensaje","SMS Recibido");

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

                //Cel tien los ultimos 10 numeros del numero del mensaje recibido de la alarma
                String cel = celular.substring(celular.length()-10);

                //Funcion para recuperar el ultimo caracter(entero) del mensaje para saber que ZONA hay actividad
                if((mensajes[i].getOriginatingAddress()).equals(celular)) {
                    ultimo = textomensaje.charAt(textomensaje.length() - 1);
                    int x = ultimo - '0';
                    if ((x >= 1) && (x <= 6)) {
                        a = ((BaseAplication) context.getApplicationContext()).getAlarmaNum(cel);
                        if((a.size() == 1)) {
                            //metodo que devuelve una alarma segun el numero de telefono
                            nombreAlarm = a.get(0).getNombre();

                            //selecciono todas las zonas almacenadas segun el id de la alarma que traigo de arriba
                            //
                            ArrayList<Zona> zonas = ((BaseAplication) context.getApplicationContext()).getZonas(a.get(0).getIdAlarma());
                            final ArrayList<Integer> idZonas = new ArrayList<>();
                            for (int y = 0; y < zonas.size(); y++) {
                                idZonas.add(zonas.get(y).getIdZona());
                            }
                            int y = idZonas.get(x - 1);
                            for (int z = 0; z < idZonas.size(); z++) {
                                if (idZonas.get(z) == y) {
                                    long res = ((BaseAplication) context.getApplicationContext()).updateNotiZona(idZonas.get(z), 1);
                                    /*if (res > 0)
                                        Toast.makeText(context, "Noti Actualizada", Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(context, "No se pudo actualizar la Notificacion", Toast.LENGTH_LONG).show();*/
                                }
                            }
                            SharedPreferences.Editor editor = context.getSharedPreferences("cc", Context.MODE_PRIVATE).edit();
                            editor.putLong("idAlarma", a.get(0).getIdAlarma());
                            editor.commit();

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
        Intent i = new Intent(context,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);
        //PendingIntent: Una descripción de una acción de intención y objetivo para realizar con ella.

        mBuilder =new NotificationCompat.Builder(context.getApplicationContext(), canal)
                .setContentIntent(pendingIntent)
                .setSmallIcon(icono)
                .setContentTitle(nombreAlarm)
                .setContentText("Se ha activado la ZONA "+ultimo)
                .setAutoCancel(true);
        mNotifyMgr.notify(1, mBuilder.build());

        //Shared para el MainActivity
        SharedPreferences.Editor editor1 = context.getSharedPreferences("noti",Context.MODE_PRIVATE).edit();
        editor1.putString("notificacion", "dale");
        editor1.commit();
		SharedPreferences.Editor editor2 = context.getSharedPreferences("ee",Context.MODE_PRIVATE).edit();
        editor2.putString("nombreAlarma", nombreAlarm);
        editor2.commit();
    }
}
