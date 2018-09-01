package com.example.matiasezequiel.security_house;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


public class MiMensaje extends BroadcastReceiver {

    String idMensaje;
    String TextoMensaje;

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

                idMensaje = mensajes[i].getOriginatingAddress();// https://developer.android.com/reference/android/telephony/SmsMessage#getOriginatingAddress()
                TextoMensaje = mensajes[i].getMessageBody();// Devuelve el cuerpo del mensaje como una Cadena, si existe y está basada en texto.

                    Log.i("ReceptorSMS","Remitente: "+idMensaje);
                    Log.i("ReceptorSMS", "Mensaje: "+ TextoMensaje);

                //Funcion para recuperar el ultimo caracter(entero) del mensaje para saber que ZONA hay actividad
                 if((mensajes[i].getOriginatingAddress()).equals(idMensaje))
                 {
                     char ultimo = TextoMensaje.charAt(TextoMensaje.length()-1);
                     if((((int)ultimo >= 1) && ((int)ultimo <= 6)))
                     Toast.makeText(context, "Zona"+ultimo, Toast.LENGTH_LONG).show();
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
}
