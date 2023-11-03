package com.example.m_hiker.Hike.ObservationCard;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.m_hiker.R;
import com.example.m_hiker.database.ObservationMedia;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ObservationThumbnail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObservationThumbnail extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ObservationThumbnail() {
        // Required empty public constructor
    }

    ObservationMedia media;

    public ObservationThumbnail setmedia(ObservationMedia media){
        this.media = media;
        return this;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ObservationCardImage.
     */
    // TODO: Rename and change types and number of parameters
    public static ObservationThumbnail newInstance(String param1, String param2) {
        ObservationThumbnail fragment = new ObservationThumbnail();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_observation_card_image, container, false);


        File file = new File(media.path);
        Uri uri = Uri.fromFile(file);


        ImageView image = view.findViewById(R.id.obimage);
        image.setImageURI(uri);

        return view;
    }
}