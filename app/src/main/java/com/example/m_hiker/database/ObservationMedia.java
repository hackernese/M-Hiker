package com.example.m_hiker.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

public class ObservationMedia implements CommonTable{
    public static DatabaseMHike db;

    public ContentValues insertValues(){
        ContentValues values = new ContentValues();
        values.put("highlight", this.is_hightlight ? 1 : 0);
        values.put("observeid", this.observation_id);
        values.put("path", this.path);
        return values;
    }


    public void insert(){

    }
    public int getid(){
        return this.id;
    }


    public int delete(){
        return 0;

    }
    public static void query(){

    }

    public int id;
    // Select a photo or video to be the highlight
    public boolean is_hightlight; // Only one can be highlight, every other thing should not be

    public String path;

    // Foriegn key to an observation
    public int observation_id;
    String created;
    String modified;

    public ObservationMedia(int observeid, String path, boolean highlight){
        this.is_hightlight = highlight;
        this.observation_id = observeid;
        this.path = path;
    }
    public ObservationMedia(long observeid, String path, boolean highlight){
        this.is_hightlight = highlight;
        this.observation_id = (int)observeid;
        this.path = path;
    }

    public ObservationMedia(){

    }
    public String gettablename(){
        return tablename;
    }
    public static final String tablename = "media";
    public static final String CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS " + tablename +"  (" +
        "id           INTEGER PRIMARY KEY, " +
        "path         TEXT NOT NULL," +
        "highlight    INTEGER DEFAULT 0," +
        "observeid    INTEGER NOT NULL," +
        "created      TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
        "modified     TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
        "FOREIGN KEY (observeid) REFERENCES observation(id) ON DELETE CASCADE" +
    ");" +
    "CREATE TRIGGER update_modified_timestamp AFTER UPDATE ON "+tablename + " " +
    "FOR EACH ROW " +
    "BEGIN " +
    "UPDATE " + tablename + " SET modified = CURRENT_TIMESTAMP WHERE id = NEW.id; " +
    "END;"; // The last ones are for updating the modified date
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + tablename;

}

