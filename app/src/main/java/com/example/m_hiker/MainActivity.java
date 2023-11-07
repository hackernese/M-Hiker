package com.example.m_hiker;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.m_hiker.Dialogs.SocialMedia;
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
    }

}