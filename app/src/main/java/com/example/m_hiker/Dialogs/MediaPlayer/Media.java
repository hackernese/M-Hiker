package com.example.m_hiker.Dialogs.MediaPlayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.m_hiker.R;
import com.example.m_hiker.database.ObservationMedia;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Media#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Media extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ObservationMedia media;
    public Media() {
        // Required empty public constructor
    }

    public Media setmedia(ObservationMedia media){
        this.media = media;
        return this;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Media.
     */
    // TODO: Rename and change types and number of parameters
    public static Media newInstance(String param1, String param2) {
        Media fragment = new Media();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public VideoView video;
    public ImageView img;
    public TextView datetime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_media, container, false);
        img = view.findViewById(R.id.imagemedia);
        video = view.findViewById(R.id.videoView);

        if(media.path.endsWith("jpg")){

            // Images
            img.setImageURI(media.toUri());
            img.setVisibility(View.VISIBLE);

        }else{

            video.setVisibility(View.VISIBLE);
            // make it visible

            // Video
            Log.d("debug", "Video here");

            video.setVideoURI(media.toUri()); // Setting the URI to the video


//            MediaController mediacontroller = new MediaController(); // Allowing the video to have a controller bar
//            // to either stop, play or continue
//            mediacontroller.setAnchorView(video);
//            // Anchor the controller to the parent view of where the "video" is standing
//            video.setMediaController(mediacontroller);


            video.start();

        }

        return view;
    }
}