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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.matiasezequiel.security_house.Aplication.BaseAplication;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class ReproducirFragment extends Fragment {

    ProgressDialog pDialog = null;
    VideoView vVTab1 = null;
    TextView tvMensajeCam = null;
    TextView tvTituloCam = null;

    SharedPreferences prefs1,prefs2 = null;
    int reproducir;
    ProgressBar progressBar = null;

    String tituloCamara;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reproducir, container, false);

        tvMensajeCam = (TextView)v.findViewById(R.id.tvTituloCamaras1);
        vVTab1 = (VideoView)v.findViewById(R.id.videoViewTab1);
        tvTituloCam = (TextView)v.findViewById(R.id.tvNombreTituloCam);

        //Shared para quedarme con el idCamara seleccionada para reproducir
        prefs1 = getContext().getSharedPreferences("ReproCamList", Context.MODE_PRIVATE);
        reproducir = (int) prefs1.getLong("reproCamLista", -1);

        prefs2 = getContext().getSharedPreferences("nomCamara", Context.MODE_PRIVATE);
        tituloCamara = prefs2.getString("nombreCamara", " ");

        tvTituloCam.setText(tituloCamara);

        progressBar = (ProgressBar) v.findViewById(R.id.progressbar);
        addCamView(reproducir);

        return v;
    }


    public void addCamView(int idCam){

        try{
            Camara c =((BaseAplication)getActivity().getApplication()).getCamara(idCam);
            String videoUrl = "rtsp://"+c.getIp()+":554/video.3gp";
            Uri videoUri = Uri.parse(videoUrl);
            vVTab1.setVideoURI(videoUri);
            vVTab1.start();

            progressBar.setVisibility(View.VISIBLE);
            vVTab1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    mp.start();
                    mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                       int arg2) {
                            // TODO Auto-generated method stub
                            progressBar.setVisibility(View.GONE);
                            mp.start();
                        }
                    });
                }
            });
        }catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
    }


    //metodo para agregar el video view
    public void addCamVideoView(int idCam){
        //consulta a la base para traer la camara a mostrar
        try {
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
        //prefs1.edit().remove("reproCamLista").commit();
    }




}
