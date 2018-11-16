package com.example.matiasezequiel.security_house;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.matiasezequiel.security_house.Aplication.BaseAplication;

import java.util.ArrayList;


public class ReproducirFragment extends Fragment {

    ProgressDialog pDialog;
    VideoView vVTab1;
    TextView tvTituloCam;

    SharedPreferences prefs1;
    private int camaraSeleccionada = -1;
    int idCam=-1,reproducir;

    ArrayList<Camara> camaras;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reproducir, container, false);

        tvTituloCam = (TextView)v.findViewById(R.id.tvTituloCamaras1);
        vVTab1 = (VideoView)v.findViewById(R.id.videoViewTab1);

        //Shared para quedarme con el idCamara seleccionada para reproducir
        prefs1 = getContext().getSharedPreferences("ReproCamList", Context.MODE_PRIVATE);
        reproducir = (int) prefs1.getLong("reproCamLista", -1);

        addCamVideoView(reproducir);

        return v;
    }

    //metodo para agregar el video view
    public void addCamVideoView(int idCam){
        //consulta a la base para traer la camara a mostrar
        Camara c =((BaseAplication)getActivity().getApplication()).getCamara(idCam);

        //String VideoURL = "rtsp://"+c.getIp()+"//video.3gp";
        String VideoURL = "rtsp://"+c.getIp()+":554/video.3gp";
        // Create a progressbar
        pDialog = new ProgressDialog(this.getContext());
        // Set progressbar title
        pDialog.setTitle("Espere por favor");
        // Set progressbar message
        pDialog.setMessage("Almacenando...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();

        try {
            // Get the URL from String VideoURL
            Uri video = Uri.parse(VideoURL);
            vVTab1.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        vVTab1.requestFocus();
        vVTab1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                vVTab1.start();
            }
        });
        prefs1.edit().remove("reproCamLista").commit();
    }
}
