package com.example.m_hiker.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Observation implements CommonTable {
    public static DatabaseMHike db;

    public ContentValues insertValues(){
        ContentValues values = new ContentValues();

        values.put("title", this.time);
        values.put("category", this.category);
        values.put("weather", this.weather);
        values.put("date", this.date);
        values.put("time", this.time);
        values.put("comments", this.comments);
        values.put("is_thumbnail", this.is_thumbnail ? 1 : 0);
        values.put("hike_id", this.hike_id);

        return values;
    }

    public int getid(){
        return this.id;
    }

    public void insert(){

    }
    public int delete(){
        return 0;
    }
    public static ArrayList<Observation> query(){
        ArrayList<Observation> ret = new ArrayList<>();
        SQLiteDatabase query = db.getReadableDatabase();
        String[] projection = {
                "id",
                "title",
                "category",
                "weather",
                "time",
                "date",
                "comments",
                "is_thumbnail",
                "hike_id"
        };

        String[] arguments = {}; // Used used in WHERE clause

        Cursor cursor = query.query(
                tablename,
                projection,
                "",  arguments,
                null, null, ""
        );

        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
            String weather = cursor.getString(cursor.getColumnIndexOrThrow("weather"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String comments = cursor.getString(cursor.getColumnIndexOrThrow("comments"));
            boolean is_thumbnail = cursor.getInt(cursor.getColumnIndexOrThrow("is_thumbnail")) == 1 ? true : false;
            int hike_id = cursor.getInt(cursor.getColumnIndexOrThrow("hike_id"));

            Observation temp = new Observation(
                    title,
                    category,
                    weather,
                    date,
                    time,
                    comments,
                    is_thumbnail,
                    hike_id
            );
            temp.id = id;
            ret.add(temp);
        }

        return ret;

    }

    String created;
    String modified;

    // Properties of this specific instance
    public String title; // Details / title of the observation
    public String category; // Category of this observation
    public String weather; // Weather situation of this observation
    public String date; // explain it yourself
    public String time; // Again, self-explanatory
    public String comments; // Comments
    public boolean is_thumbnail;
    // This should be unique since only one can have a thumbnail

    // References to another table / foreign key
    public int hike_id; // Tell where does this observation belong to

    public Observation(
            String title,
            String category,
            String weather,
            String date,
            String time,
            String comments,
            boolean is_thumbnail,
            int hike_id
    ){
        this.time = time;
        this.is_thumbnail = is_thumbnail;
        this.hike_id = hike_id;
        this.date = date;
        this.weather = weather;
        this.title = title;
        this.category = category;
        this.comments = comments;
    }
    public int id;
    public String gettablename(){
        return tablename;
    }
    public static String tablename = "observation";
    public static final String CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS " + tablename +"  (" +
        "id           INTEGER PRIMARY KEY, " +
        "title        TEXT    NOT NULL, " +
        "category     TEXT    NOT NULL, " +
        "weather      TEXT, " +
        "time         TEXT    NOT NULL, " +
        "date         TEXT NOT NULL," +
        "comments     TEXT, " +
        "is_thumbnail INTEGER, " +
        "hike_id      INTEGER NOT NULL," +
        "created      TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
        "modified     TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
        "FOREIGN KEY (hike_id) REFERENCES hikes(id) ON DELETE CASCADE" +
                ");" +
    "CREATE TRIGGER update_modified_timestamp AFTER UPDATE ON "+tablename + " " +
    "FOR EACH ROW " +
    "BEGIN " +
    "UPDATE " + tablename + " SET modified = CURRENT_TIMESTAMP WHERE id = NEW.id; " +
    "END;"; // The last ones are for updating the modified date

    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + tablename;

}
