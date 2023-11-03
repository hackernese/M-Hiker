package com.example.m_hiker.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.m_hiker.MainActivity;
import com.example.m_hiker.R;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.File;

public class storex extends AppCompatActivity {

    public static MainActivity activity ;
    public static FragmentManager manager;

    public static String folder;
    public static File folderFile;


    public static void setmainActivity(MainActivity activity, FragmentManager manager){
        storex.activity = activity;
        storex.manager = manager;
    }

    public storex(){
//        getSupportFragmentManager();
    }

}
