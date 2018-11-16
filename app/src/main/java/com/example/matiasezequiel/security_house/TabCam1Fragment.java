package com.example.matiasezequiel.security_house;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.matiasezequiel.security_house.Aplication.BaseAplication;

import java.util.ArrayList;


public class TabCam1Fragment extends Fragment {


    VideoView vVTab1;
    ImageView ivAddCam;
    TextView tvTituloCam;
    ListView lista;

    private int camaraSeleccionada = -1;
    int idCam=-1;

    ArrayList<Camara> camaras;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_cam1, container, false);

        ivAddCam = (ImageView)v.findViewById(R.id.agregarCam1);
        tvTituloCam = (TextView)v.findViewById(R.id.tvTituloCamaras1);
        vVTab1 = (VideoView)v.findViewById(R.id.videoViewTab1);

        //imageView para hacer click en el signo +, abre un dialog para seleccionar de la lista de camaras
        //la camara que quiero que se reproduzca
        ivAddCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View viewCamaras = getLayoutInflater().inflate(R.layout.lista_dialog_camaras, null);
                lista = (ListView)viewCamaras.findViewById(R.id.LVCamaras);
                final Button btnSelect = (Button)viewCamaras.findViewById(R.id.btnSeleccionarCam);

                builder.setView(viewCamaras);

                final AlertDialog dialog = builder.create();

                btnSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProgressDialog pDialog;
                        dialog.dismiss();
                    }
                });

                llenarLista();
                dialog.show();
            }
        });

        //Toast.makeText(this.getActivity(),"IDCAM: "+idCam,Toast.LENGTH_SHORT).show();
        addCamVideoView(1);

        return v;
    }

    //metodo para agregar el video view
    public void addCamVideoView(int idCam){
        //consulta a la base para traer la camara a mostrar
        Camara c =((BaseAplication)getActivity().getApplication()).getCamara(idCam);

        //String VideoURL = "rtsp://"+c.getIp()+"//video.3gp";
        String VideoURL = "rtsp://"+c.getIp()+":554/video.3gp";
        // Create a progressbar
        //pDialog = new ProgressDialog(this.getContext());
        // Set progressbar title
        //pDialog.setTitle("Espere por favor");
        // Set progressbar message
        //pDialog.setMessage("Almacenando...");
        //pDialog.setIndeterminate(false);
        //pDialog.setCancelable(false);
        // Show progressbar
        //pDialog.show();

        try {
            // Start the MediaController
            //MediaController mediacontroller = new MediaController(this.getContext());
            //mediacontroller.setAnchorView(vVTab1);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(VideoURL);
            //vVTab1.setMediaController(mediacontroller);
            vVTab1.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        vVTab1.requestFocus();
        vVTab1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                //pDialog.dismiss();
                vVTab1.start();
            }
        });
    }

    public void llenarLista(){
        camaras = ((BaseAplication) getActivity().getApplication()).nombresCamaras();
        if((camaras.size() == 0) || (camaras == null)) {
            AdapterLista ld = new AdapterLista();
            lista.setAdapter(ld);
        }else {
            AdapterLista ld = new AdapterLista();
            lista.setAdapter(ld);
        }
    }

    private class AdapterLista extends ArrayAdapter<Camara>
    {
        public AdapterLista() {

            super(getActivity(), R.layout.items_listcam_dialog, camaras);
        }

        @Nullable
        @Override
        public Camara getItem(int position) {
            return super.getItem(idCam);
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView=convertView;
            if (itemView == null){
                itemView= getLayoutInflater().inflate(R.layout.items_listcam_dialog, parent,false);
            }

            Camara itemActual= camaras.get(position);

            ImageView ivIconoCam = (ImageView) itemView.findViewById(R.id.ivIconoCamara);
            Button nombreCamara = (Button) itemView.findViewById(R.id.btNombreCam);
            RadioButton rbCamSelect = (RadioButton) itemView.findViewById(R.id.rbCamSeleccionada);

            ArrayList<String> nombreCam = new ArrayList<>();
            for (int i = 0;i<camaras.size();i++){
                nombreCam.add (camaras.get(i).getNombre());
            }
            nombreCamara.setText(camaras.get(position).getNombre());

            //Boton que selecciona la camara que se va a reproducir
            nombreCamara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(),"toast para elegir la camara",Toast.LENGTH_SHORT).show();
                    camaraSeleccionada = position;
                    Camara cam = camaras.get(camaraSeleccionada);
                    idCam = cam.getIdCamara();

                    Fragment verCamaras = new VerCamarasFragment();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.contenedor, verCamaras,"VerCamaras");
                    fr.commit();


                    //Toast.makeText(getContext(),"ID Camara: "+camaraSeleccionada,Toast.LENGTH_SHORT).show();
                    //ivAddCam.setVisibility(View.INVISIBLE);
                    //addCamVideoView(camaraSeleccionada);
                }
            });
            return itemView;
        }
    }



}
