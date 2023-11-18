package com.example.m_hiker.CreateObservation.screens;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
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
import com.example.m_hiker.Dialogs.MediaPlayer.Media;
import com.example.m_hiker.R;
import com.example.m_hiker.utils.func;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import com.example.m_hiker.utils.storex;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateObservationSlideMedia#newInstance} factory method to
 * create an instance of this fragment.
 *
 *

 *
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

    public ImageGridAdapter adapter;

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

    private ActivityResultLauncher<Intent> camlauncher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Initialize the the callback whenever the activity finishes
        camlauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            String path = storex.folder + File.separator + captured_image_uri.getPath().split(File.separator)[2];
                            Uri i = Uri.fromFile(new File(path));

                            ImageItem item = new ImageItem(i);
                            item.path = path;

                            // Setting the correct path
                            items.add(item);

                            // Hide the Empty labels
                            fab.setVisibility(View.GONE);
                            emptymediatitle.setVisibility(View.GONE);

                            // Reset the adapter and image grid
                            adapter.updateItems(items);
                            imagegrid.setAdapter(adapter);

                        }
                    }
                });

    }

    public View emptymediatitle;
    public Uri captured_image_uri;

    public ArrayList<ImageItem> items;
    public GridView imagegrid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentview = inflater.inflate(R.layout.fragment_create_observation_slide_media, container, false);
        View view = parentview;
        imagegrid = view.findViewById(R.id.imagegrid);

        items = new ArrayList<>();
        ActivityResultContracts.PickMultipleVisualMedia picker = new ActivityResultContracts.PickMultipleVisualMedia();

        emptymediatitle = view.findViewById(R.id.emptytitlemedia);
        fab = view.findViewById(R.id.addnewmediatempbutton);

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

                    // Processing the filename
                    String extension = getContext().getContentResolver().getType(uri);
                    String[] splitted = uri.toString().split("/");
                    String filename = splitted[splitted.length - 1] +
                            ( extension.startsWith("image") ? ".jpg" : ".mp4" );

                    // Copying / backing up the file
                    if(func.copyInputStream(inputStream, filename)){
                        Log.d("debug", "Successfully cached file " + filename);
                    }else{
                        Log.d("debug", "Failed to cache file " + filename);
                    }

                    // Creaeting a card
                    ImageItem item = new ImageItem(uri);
                    item.path = storex.folder + File.separator + filename;
                    items.add(item);
                }

                // Make sure to show the correct thing ( if it's empty, show the "empty" label )
                if(items.isEmpty()){
                    fab.setVisibility(View.VISIBLE);
                    emptymediatitle.setVisibility(View.VISIBLE);
                }else{
                    fab.setVisibility(View.GONE);
                    emptymediatitle.setVisibility(View.GONE);
                }


                // Update the recycler view
                adapter.updateItems(items);
                imagegrid.setAdapter(adapter);
            }

            setSpinnerState(false);

        });

        Runnable pickmedia = new Runnable() {
            @Override
            public void run() {
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
        adapter = new ImageGridAdapter(getContext(), new ArrayList<ImageItem>(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickmedia.run();
            }
        });
        imagegrid.setAdapter(adapter);

        // Responsible for adding new videos and images to the database later on
        fab = view.findViewById(R.id.addnewmediatempbutton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.example.m_hiker.MultiChoiceBottomSheet.MultiChoiceSheet sheet = new com.example.m_hiker.MultiChoiceBottomSheet.MultiChoiceSheet(view, getActivity(), "Options");


                sheet.setType(true);
                 sheet
                         .option("Capture from camera", R.drawable.add_camera_photo)
                        .option("Browse photos and videos", R.drawable.addphoto);
                 sheet.show(new com.example.m_hiker.MultiChoiceBottomSheet.MultiChoiceSheet.Callback() {
                     @Override
                     public void onchange(String key, boolean value) {
                         if(key.equals("Capture from camera") && value){

                             // Capture from camera

                             Dexter.withContext(getContext()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
                                 @Override
                                 public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                     // Tell device where to save it
                                     File file = new File(storex.folder + File.separator + func.getfilename_based_on_date() + ".png");

                                     captured_image_uri = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".provider", file);


                                     Intent takepicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                     takepicture.putExtra(MediaStore.EXTRA_OUTPUT, captured_image_uri); // This is how the code will recognize where to save it

                                     if(takepicture.resolveActivity(requireActivity().getPackageManager()) != null){
                                         camlauncher.launch(takepicture);
                                     }
                                 }

                                 @Override
                                 public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                                 }

                                 @Override
                                 public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                                 }
                             }).check();

//

                         }

                         if(key.equals("Browse photos and videos") && value){

                             // Browse photo and videos
                             pickmedia.run();
                         }

                         sheet.dialog.dismiss();

                     }
                 });
            }
        });

        return view;
    }
}