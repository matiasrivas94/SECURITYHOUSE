package com.example.matiasezequiel.security_house;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


public class VerCamarasFragment extends Fragment {
    ImageView imageView2, imageView4, imageView6;
    ViewPager viewPager;
    PagerViewAdapter pagerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ver_camaras, container, false);

        imageView2 = (ImageView)v.findViewById(R.id.iv_dos);
        imageView4 = (ImageView)v.findViewById(R.id.iv_cuatro);
        imageView6 = (ImageView)v.findViewById(R.id.iv_seis);

        imageView2.setImageResource(R.drawable.act1);

        viewPager = (ViewPager)v.findViewById(R.id.fragment_container);

        pagerViewAdapter = new PagerViewAdapter(getFragmentManager());

        viewPager.setAdapter(pagerViewAdapter);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                onChangeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        return v;
    }

    private void onChangeTab(int position) {
        if (position == 0){
            imageView2.setImageResource(R.drawable.act1);
            imageView4.setImageResource(R.drawable.four);
            imageView6.setImageResource(R.drawable.six);
        }
        if (position == 1){
            imageView2.setImageResource(R.drawable.one);
            imageView4.setImageResource(R.drawable.act4);
            imageView6.setImageResource(R.drawable.six);
        }
        if (position == 2){
            imageView2.setImageResource(R.drawable.one);
            imageView4.setImageResource(R.drawable.four);
            imageView6.setImageResource(R.drawable.act6);
        }
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
