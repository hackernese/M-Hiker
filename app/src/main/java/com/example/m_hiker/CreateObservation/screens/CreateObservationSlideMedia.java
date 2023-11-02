package com.example.m_hiker.CreateObservation.screens;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.m_hiker.CreateObservation.ImageGrid.ImageGridAdapter;
import com.example.m_hiker.CreateObservation.ImageGrid.ImageItem;
import com.example.m_hiker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateObservationSlideMedia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateObservationSlideMedia extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateObservationSlideMedia() {
        // Required empty public constructor
    }

    public static CreateObservationSlideMedia newInstance(String param1, String param2) {
        CreateObservationSlideMedia fragment = new CreateObservationSlideMedia();
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

    ImageGridAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_observation_slide_media, container, false);
        GridView imagegrid = (GridView)view.findViewById(R.id.imagegrid);

         // Registers a photo picker activity launcher in single-select mode.
        ActivityResultLauncher<PickVisualMediaRequest> pickMedia = registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(), selectedMedia -> {

            if (!selectedMedia.isEmpty()) {

                // When users has selected media
                ArrayList<ImageItem> items = new ArrayList<>();
                for(Uri uri : selectedMedia)
                    items.add(new ImageItem(uri));

                FloatingActionButton fab = view.findViewById(R.id.addnewmediatempbutton);
                View emptymediatitle = view.findViewById(R.id.emptytitlemedia);

                if(items.isEmpty()){
                    fab.setVisibility(View.VISIBLE);
                    emptymediatitle.setVisibility(View.VISIBLE);
                }else{
                    fab.setVisibility(View.INVISIBLE);
                    emptymediatitle.setVisibility(View.INVISIBLE);
                }

                adapter.updateItems(items);
                imagegrid.setAdapter(adapter);
            }
        });
        View.OnClickListener LaunchFileExplorer = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts
                                .PickVisualMedia.ImageAndVideo.INSTANCE)
                        .build());
            }
        };

        // Creating a grid adapter
        adapter = new ImageGridAdapter(getContext(), new ArrayList<ImageItem>(),LaunchFileExplorer);
        imagegrid.setAdapter(adapter);

        // Responsible for adding new videos and images to the database later on
        view.findViewById(R.id.addnewmediatempbutton).setOnClickListener(LaunchFileExplorer);

        return view;
    }
}