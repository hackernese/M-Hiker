package com.example.m_hiker;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.m_hiker.Dialogs.ToastMessage;
import com.example.m_hiker.database.DatabaseMHike;
import com.example.m_hiker.utils.storex;
import com.google.android.gms.maps.SupportMapFragment;

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

    }

}