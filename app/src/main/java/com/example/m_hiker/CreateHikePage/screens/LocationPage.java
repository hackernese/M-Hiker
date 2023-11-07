package com.example.m_hiker.CreateHikePage.screens;

import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.m_hiker.CreateHikePage.LocationList.LocationAdapter;
import com.example.m_hiker.CreateHikePage.LocationList.LocationItem;
import com.example.m_hiker.R;
import com.example.m_hiker.utils.storex;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocationPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationPage extends Fragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LocationPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationPage.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationPage newInstance(String param1, String param2) {
        LocationPage fragment = new LocationPage();
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

    private GoogleMap mMap ;
    ViewPager2 pager;

    public LocationPage setpager(ViewPager2 pager){
        this.pager = pager;
        return this;
    }

    private double latitude = 10.774680946235982; // Default latitude
    private double longtitude = 106.70544541745964; // Default longtitude

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {


        mMap = googleMap;

        LatLng location = new LatLng(latitude, longtitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

        // You can also add a marker at a specific location:
        MarkerOptions markerOptions = new MarkerOptions()
                .position(location)
                .title("Temporary location")
                .snippet("symbol of Saigon");
        mMap.addMarker(markerOptions);
        // You can customize other map settings as well, like zoom level, map type, etc.
        mMap.setMapType(mMap.MAP_TYPE_NORMAL);
        mMap.setMinZoomPreference(10.0f);

        // You can also enable user interactions like zooming and panning:
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);


        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                pager.setUserInputEnabled(true);
            }
        });

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                pager.setUserInputEnabled(false);
            }
        });
//        pager.setUserInputEnabled(false);

    }

    // Used for extracting local location later
    FusedLocationProviderClient locationclient;

    private void getCurrentLocation(){
        Task<Location> task = locationclient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                // Decode the location first
                if(location==null)
                    return;

//                        .url("https://maps.googleapis.com/maps/api/geocode/xml?key=AIzaSyDJtdZZqKshP83NfY23NU790t2JFSIIVLM&address=Hue")
//                        .build();
                // Clear old setting
                mMap.clear();

                LatLng location_ = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate update =  CameraUpdateFactory.newLatLngZoom(location_, 12.0f);
                mMap.moveCamera(update);

                // You can also add a marker at a specific location:
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(location_)
                        .title("Curent location")
                        .snippet("Long:" + location.getLongitude() + " Lat:" + location.getLatitude());
                mMap.addMarker(markerOptions);
                // You can customize other map settings as well, like zoom level, map type, etc.
                mMap.setMapType(mMap.MAP_TYPE_NORMAL);
                mMap.setMinZoomPreference(10.0f);

                // You can also enable user interactions like zooming and panning:
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setCompassEnabled(true);

                mMap.animateCamera(update);

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googlemap);
        mapFragment.getMapAsync(this);

        // Extracting local location
        locationclient = LocationServices.getFusedLocationProviderClient(getContext());

        // Requesting permissions
        Dexter.withContext(getContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                    getCurrentLocation();
                    Log.d("debug", "requested completed");
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).check();

    }

    HikeRunnable callback;

    public LocationPage setcallback(HikeRunnable callback){
        this.callback = callback;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_page, container, false);


        // Animation
        Animation slidedown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
        Animation slideup = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        Animation fadein = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
        Animation fadeout = AnimationUtils.loadAnimation(getContext(), R.anim.fadeout);

        // Common variables
        Geocoder geocoder = new Geocoder(getContext());

        // Creating an adapter for listing location
        RecyclerView localist = view.findViewById(R.id.locationlist); // The recyclerview
        View locationlistview = view.findViewById(R.id.locationlistcard); // The card containing the recylerview
        ArrayList<LocationItem> l = new ArrayList<>();

        l.add(new LocationItem());
        l.add(new LocationItem());
        l.add(new LocationItem());
        l.add(new LocationItem());
        l.add(new LocationItem());
        l.add(new LocationItem());
        l.add(new LocationItem());


        LocationAdapter adapter = new LocationAdapter(getContext(), l);
        localist.setLayoutManager(new LinearLayoutManager(getContext()));
        localist.setAdapter(adapter);


        view.findViewById(R.id.voice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
//                        Locale.getDefault());
//                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");
//
            }
        });

        ((EditText)view.findViewById(R.id.searchlocation)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String ret = charSequence.toString();


                if(ret.length()==0){
                    locationlistview.setVisibility(View.INVISIBLE);
                }else{
                    locationlistview.setVisibility(View.VISIBLE);
                }

                callback.location(ret);

                try {
                    List<Address> addresses = geocoder.getFromLocationName(ret, 20);
                    if(addresses==null)return;

                    if (!addresses.isEmpty()) {

                        addresses.forEach(e->{
                            Log.d("debug", e.getAddressLine(0) + " " + e.getMaxAddressLineIndex());
                        });

//                        Address address = addresses.get(0);
//
//                        Log.d("debug", address.getAddressLine(0));
//                        double latitude = address.getLatitude();
//                        double longitude = address.getLongitude();
//                        // Use latitude and longitude for your purposes
                    } else {
                        // Address not found
                        Log.d("debug", "Not found");

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle exception
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }
}