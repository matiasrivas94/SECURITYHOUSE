package com.example.matiasezequiel.security_house;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PremiumFragment extends Fragment {

    Button btnPremium1, btnPremium3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_premium, container, false);

        btnPremium1 = (Button)v.findViewById(R.id.btnPremium1);
        btnPremium3 = (Button)v.findViewById(R.id.btnPremium3);

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


}