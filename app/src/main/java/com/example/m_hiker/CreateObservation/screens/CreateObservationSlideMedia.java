package com.example.m_hiker.CreateObservation.screens;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.m_hiker.CreateObservation.ImageGrid.ImageGridAdapter;
import com.example.m_hiker.CreateObservation.ImageGrid.ImageItem;
import com.example.m_hiker.R;
import com.example.m_hiker.utils.func;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import com.example.m_hiker.utils.storex;

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

    View parentview;

    @Nullable
    @Override
    public View getView() {
        return parentview;
    }


    View spinningView;
    FloatingActionButton fab;

    public void setSpinnerState(boolean state){

        fab.setVisibility(state ? View.GONE : View.VISIBLE);
        spinningView.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentview = inflater.inflate(R.layout.fragment_create_observation_slide_media, container, false);
        View view = parentview;
        GridView imagegrid = (GridView)view.findViewById(R.id.imagegrid);

        ArrayList<ImageItem> items = new ArrayList<>();
        ActivityResultContracts.PickMultipleVisualMedia picker = new ActivityResultContracts.PickMultipleVisualMedia();


         // Registers a photo picker activity launcher in single-select mode.
        ActivityResultLauncher<PickVisualMediaRequest> pickMedia = registerForActivityResult(picker, selectedMedia -> {

            if (!selectedMedia.isEmpty()) {
                // When users has selected media
                for(Uri uri : selectedMedia) {

                    InputStream inputStream;
                    try{
                        inputStream = getContext().getContentResolver().openInputStream(uri);
                    }catch (Exception e){
                        e.printStackTrace();
                        return;
                    }

                    String extension = getContext().getContentResolver().getType(uri);
                    String[] splitted = uri.toString().split("/");
                    String filename = splitted[splitted.length - 1] +
                            ( extension.startsWith("image") ? ".jpg" : ".mp4" );

                    if(func.copyInputStream(inputStream, filename)){
                        Log.d("debug", "Successfully cached file " + filename);
                    }else{
                        Log.d("debug", "Failed to cache file " + filename);
                    }
                    ImageItem item = new ImageItem(uri);
                    item.path = storex.folder + File.separator + filename;
                    items.add(item);
                }
                View emptymediatitle = view.findViewById(R.id.emptytitlemedia);

                fab = view.findViewById(R.id.addnewmediatempbutton);
                if(items.isEmpty()){
                    fab.setVisibility(View.VISIBLE);
                    emptymediatitle.setVisibility(View.VISIBLE);
                }else{
                    fab.setVisibility(View.GONE);
                    emptymediatitle.setVisibility(View.GONE);
                }

                adapter.updateItems(items);
                imagegrid.setAdapter(adapter);
            }

            setSpinnerState(false);

        });

        View.OnClickListener LaunchFileExplorer = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts
                                .PickVisualMedia.ImageAndVideo.INSTANCE)
                        .build());

                setSpinnerState(true);
            }
        };

        Animation spin = AnimationUtils.loadAnimation(getContext(), R.anim.spin_animation);
        ImageView spinner = view.findViewById(R.id.spinner);
        spinner.startAnimation(spin);

        spinningView = view.findViewById(R.id.Spinning);

        // Creating a grid adapter
        adapter = new ImageGridAdapter(getContext(), new ArrayList<ImageItem>(),LaunchFileExplorer);
        imagegrid.setAdapter(adapter);

        // Responsible for adding new videos and images to the database later on
        fab = view.findViewById(R.id.addnewmediatempbutton);
        fab.setOnClickListener(LaunchFileExplorer);

        return view;
    }
}