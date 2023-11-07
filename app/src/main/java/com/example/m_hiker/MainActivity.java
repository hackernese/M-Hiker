package com.example.m_hiker;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.example.m_hiker.Dialogs.MediaPlayer.MediaSlider;
import com.example.m_hiker.Dialogs.ToastMessage;
import com.example.m_hiker.database.DatabaseMHike;
import com.example.m_hiker.utils.storex;

import java.io.File;


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

//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        intent.setType("image/* video/*");
//        startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);

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

        // Enabling Places API
//        Places.initialize(getApplicationContext(), storex.apikey);
//        PlacesClient placeclient = Places.createClient(this);

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