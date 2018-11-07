package com.example.matiasezequiel.security_house;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class PagerViewAdapter extends FragmentStatePagerAdapter {


    public PagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new TabCam1Fragment();
                break;
            case 1:
                fragment = new TabCam4Fragment();
                break;
            case 2:
                fragment = new TabCam6Fragment();
                break;
        }
        return fragment;

    }

    @Override
    public int getCount() {
        return 3;
    }
}
