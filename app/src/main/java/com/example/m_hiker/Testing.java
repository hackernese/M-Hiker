package com.example.m_hiker;
import java.util.*;

import android.content.pm.ApplicationInfo;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.FileInputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Testing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Testing extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Testing() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Testing.
     */
    // TODO: Rename and change types and number of parameters
    public static Testing newInstance(String param1, String param2) {
        Testing fragment = new Testing();
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
        View view = inflater.inflate(R.layout.fragment_testing, container, false);


        // Load the local.properties file
        Properties properties = new Properties();
        String token = "";
        try {
            properties.load(new FileInputStream("local.properties"));
            token = properties.getProperty("MAPS_API_KEY");
        } catch (IOException e) {
           Log.d("debug", e.toString());
        }

        Log.d("debug", token);

//
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url("https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyDJtdZZqKshP83NfY23NU790t2JFSIIVLM&address=Wall%20Street").build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                Log.d("debug", "Failed");
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//
//                if(!response.isSuccessful())
//                    return;
//                Log.d("debug", response.body().string());
//            }
//        });

//        Geocoder geocoder = new Geocoder(getContext());
//
//        try {
//            List<Address> addresses = geocoder.getFromLocationName("Vietnam", 1);
//
//            if (!addresses.isEmpty()) {
//                Address address = addresses.get(0);
//
//                Log.d("debug", address.getAddressLine(0));
//                double latitude = address.getLatitude();
//                double longitude = address.getLongitude();
//                // Use latitude and longitude for your purposes
//            } else {
//                // Address not found
//                Log.d("debug", "Not found");
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle exception
//        }

        return view;
    }
}