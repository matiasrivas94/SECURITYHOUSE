package com.example.matiasezequiel.security_house;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PremiumFragment extends Fragment {

    Button btnPremium1, btnPremium3, btnVerificarImg;
    EditText emailPremium;
    ImageView ivVerificar;
    TextView tvEstadoPremium;
    String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_premium, container, false);

        btnPremium1 = (Button)v.findViewById(R.id.btnPremium1);
        btnPremium3 = (Button)v.findViewById(R.id.btnPremium3);
        emailPremium = (EditText)v.findViewById(R.id.etEmailPremium);
        ivVerificar = (ImageView)v.findViewById(R.id.ivVerificarImagen);
        btnVerificarImg = (Button)v.findViewById(R.id.btnVerificarEmail);
        tvEstadoPremium = (TextView)v.findViewById(R.id.tvEstadoPremium);

        btnVerificarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailPremium.getText().toString();
                email = email.replace(" ","");
                ivVerificar.setVisibility(View.VISIBLE);
                if(email.equals("mauryntvg7@gmail.com")){
                    ivVerificar.setImageResource(R.drawable.ic_done_green);
                    btnPremium3.setEnabled(false);
                    btnPremium1.setEnabled(false);
                    tvEstadoPremium.setVisibility(View.VISIBLE);
                    //editar premium 1
                }
                else{
                    ivVerificar.setImageResource(R.drawable.ic_error);
                    btnPremium3.setEnabled(true);
                    btnPremium1.setEnabled(true);
                    tvEstadoPremium.setVisibility(View.GONE);
                    //editar premium 0
                }
            }
        });

        btnPremium1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.mercadopago.com/mla/checkout/start?pref_id=214445251-b9c69b6b-e345-4274-bced-3efaec17f58a");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        btnPremium3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.mercadopago.com/mla/checkout/start?pref_id=214445251-8585df23-a3fb-46c8-86b8-8d6c850fc978");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.hideFloatingActionButton(); //oculto boton
            FloatingActionButton fab = mainActivity.findViewById(R.id.fab);
        }
    }


}