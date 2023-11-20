package com.example.m_hiker;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import com.example.m_hiker.Dialogs.MediaPlayer.MediaSlider;
import com.example.m_hiker.Dialogs.SocialMedia;
import com.example.m_hiker.Dialogs.ToastMessage;
import com.example.m_hiker.database.DatabaseMHike;
import com.example.m_hiker.utils.func;
import com.example.m_hiker.utils.storex;
import com.karumi.dexter.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.BasePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database
        DatabaseMHike db = DatabaseMHike.init(MainActivity.this);

        storex.setmainActivity(this, getSupportFragmentManager());

        ToastMessage.context = getApplicationContext();
        ToastMessage.inflater = getLayoutInflater();

        // Creating media folder
        storex.apikey = getApiKeyFromManifest();
        storex.abpath = getFilesDir() + File.separator;
        storex.folder = getFilesDir() + File.separator + "media";
        storex.folderFile = new File(storex.folder);

        if(!storex.folderFile.exists()){

            // Not existed
            Log.d("debug", "Creating new media folder");

            // Create the custom folder
            boolean folderCreated = storex.folderFile.mkdirs();
            if (folderCreated) {
                // The folder has been created successfully
                Log.d("debug", "Folder created successfully");
            } else {
                // Failed to create the folder
                Log.e("debug", "Failed to create folder");
            }
        }else{
            Log.e("debug", "Media folder has already existed");
        }


        Log.d("debug", func.getfilename_based_on_date());

//        func.getcoords("Wall Streets");

        // Enabling Places API
//        Places.initialize(getApplicationContext(), storex.apikey);
//        PlacesClient placeclient = Places.createClient(this);

//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_SEND);
//        intent.putExtra(Intent.EXTRA_TEXT, "DAWD");
//        intent.putExtra(Intent.EXTRA_STREAM, uri);
//        intent.setType("image/*");
//        Intent share = Intent.createChooser(intent, "Share via");
//        startActivity(share);

    }

    private String getApiKeyFromManifest() {
        String apiKey = null;
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo.metaData != null) {
                apiKey = applicationInfo.metaData.getString("com.google.android.geo.API_KEY");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return apiKey;
    }

}