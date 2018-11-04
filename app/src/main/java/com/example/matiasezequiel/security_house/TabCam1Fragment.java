package com.example.matiasezequiel.security_house;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;


public class TabCam1Fragment extends Fragment {

    VideoView v1, v2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_cam1, container, false);

        /*v1 = (VideoView)v.findViewById(R.id.videoView);
        String path1 = "android.resource://"+ getActivity().getPackageName()+ "/"+R.drawable.atras1;
        Uri uri = Uri.parse(path1);
        v1.setVideoURI(uri);

        v2 = (VideoView)v.findViewById(R.id.videoView2);
        String path2 = "android.resource://"+ getActivity().getPackageName()+ "/"+R.drawable.add3;
        Uri uri2 = Uri.parse(path2);
        v2.setVideoURI(uri2);

        MediaController media = new MediaController(this.getContext());
        v1.setMediaController(media);
        media.setAnchorView(v1);

        MediaController media2 = new MediaController(this.getContext());
        v2.setMediaController(media2);
        media2.setAnchorView(v2);
*/

        return v;
    }

}
