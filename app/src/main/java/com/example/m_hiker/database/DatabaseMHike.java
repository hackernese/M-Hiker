package com.example.m_hiker.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


// importing local classes
import com.example.m_hiker.database.Hikes;
import com.example.m_hiker.database.Observation;
import com.example.m_hiker.database.ObservationMedia;
import com.google.android.gms.common.internal.service.Common;

import android.database.SQLException;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseMHike extends  SQLiteOpenHelper{

    private Context context;
    public static final String DATABASE_NAME  = "MHike.db";
    public static final int DATABASE_VERSION  = 1;

    private static DatabaseMHike dbobject = null;
    private DatabaseMHike(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        Log.d("debug", "DatabaseMHike received new signals");

    }

    public static DatabaseMHike init(@Nullable Context context){
        if(dbobject==null){
            // Not initialized yet
            dbobject = new DatabaseMHike(context);
            dbobject.getWritableDatabase(); // Create table

            Hikes.db = dbobject;
            ObservationMedia.db = dbobject;
            Observation.db = dbobject;

        }

        return dbobject;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        // Allowing foreign key for easy data manipulation ( CASCADE, etc... )
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
        Log.d("debug", "Enabled foreign key in database");
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        try{
            db.execSQL(Hikes.CREATE_TABLE);
            db.execSQL(Observation.CREATE_TABLE);
            db.execSQL(ObservationMedia.CREATE_TABLE);
            Log.d("debug", "Successfully created all tables");
        }catch (SQLException e){
            Log.e("sqlerror", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version){
        try{
            db.execSQL(Hikes.DELETE_TABLE);
            db.execSQL(Observation.DELETE_TABLE);
            db.execSQL(ObservationMedia.DELETE_TABLE);
            Log.d("debug", "Successfully dropped all tables");
        }catch (SQLException e){
            Log.e("sqlerror", e.getMessage());
        }
        onCreate(db);
    }


    // Overloading methods for inserting into the database
    public int update(CommonTable instance, HashMap<String, String> values){
        SQLiteDatabase cursor = this.getWritableDatabase();

        // Crafting the table
        ContentValues vals = new ContentValues();
        values.forEach((key, value)->{
            vals.put(key, value);
        });

        // Arguments which will be passed in later
        String[] args = {"" + instance.getid()};

        int count = cursor.update(
                instance.gettablename(),
                vals,
                "id LIKE ?",args

        );

        return count;
    }
    public long insert(CommonTable record){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues vals = record.insertValues();
        return db.insert(record.gettablename(), null, vals);
    }

}
