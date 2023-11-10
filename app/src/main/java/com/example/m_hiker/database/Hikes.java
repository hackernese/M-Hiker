package com.example.m_hiker.database;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Hikes implements CommonTable {


    public static DatabaseMHike db;

    public ContentValues insertValues(){

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("location",location);
        values.put("date", date);
        values.put("length", length);
        values.put("units", units);
        values.put("level", level);
        values.put("parking", parking);
        values.put("companions", companion);
        values.put("description", description);
        values.put("lat", lat);
        values.put("long", longtitude);
        values.put("islove", islove);

        return values;

    }

    public void insert(){

    }
    public int getid(){
        return this.id;
    }

    public int delete(){
        SQLiteDatabase mydb = db.getWritableDatabase();
        String selection = "id LIKE ?";
        String[] args = {"" + this.id};
        return mydb.delete(this.tablename, selection, args);
    }

    public static class ParcelHike implements Parcelable {

        public Hikes object;

        public ParcelHike setobject(Hikes object){
            this.object = object;
            return this;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Parcelable.Creator<ParcelHike> CREATOR = new Parcelable.Creator<ParcelHike>(){
            @Override
            public ParcelHike createFromParcel(Parcel parcel) {
                return null;
            }

            @Override
            public ParcelHike[] newArray(int i) {
                return new ParcelHike[0];
            }
        };

        @Override
        public void writeToParcel(@NonNull Parcel parcel, int i) {

        }
    };

    public ParcelHike getParcelObject(){
        return (new ParcelHike()).setobject(this);
    }

    public static ArrayList<Hikes> query(HashMap<String, String> params, HashMap<String, Integer> paramsint)
    {

        /*

            Params = List of params that has string as values
            paramsInt = List of params that have integer as values

         */

        // COnstructing a query object
        ArrayList<Hikes> ret = new ArrayList<>();
        SQLiteDatabase mydb = db.getReadableDatabase();


        // Crafting the filtering SQL statement
        String query = "SELECT * FROM " + tablename;
        ArrayList<String> paramslist = new ArrayList<>();
        if(params.size() > 0 || paramsint.size() > 0){
            query += " WHERE ";
        }

        for(Map.Entry<String, String> entry : params.entrySet()){
            query += " " + entry.getKey() + " LIKE ? AND ";
            paramslist.add("%" + entry.getValue() + "%");
        }
        for(Map.Entry<String, Integer> entry : paramsint.entrySet()){
            query += " " + entry.getKey() + " = ? AND ";
            paramslist.add(""+entry.getValue());
        }
        query = query.trim(); // Getting rid of weird spaces

        if(query.endsWith("AND"))
            query = query.substring(0, query.length() - 3);

        // Start querying
        String[] paramslist_strlist = paramslist.toArray(new String[paramslist.size()]);
        Cursor cursor = mydb.rawQuery(query, paramslist_strlist);


        // Extracting and returning data
        if(cursor == null){
            return ret;
        }
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            int length = cursor.getInt(cursor.getColumnIndexOrThrow("length"));
            String units = cursor.getString(cursor.getColumnIndexOrThrow("units"));
            String level = cursor.getString(cursor.getColumnIndexOrThrow("level"));
            boolean parking = cursor.getInt(cursor.getColumnIndexOrThrow("parking")) == 1 ? true : false;
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            boolean islove = cursor.getInt(cursor.getColumnIndexOrThrow("islove")) == 1 ? true : false;
            String thumbnail = cursor.getString(cursor.getColumnIndexOrThrow("thumbnail"));
            int thumbnail_id = cursor.getInt(cursor.getColumnIndexOrThrow("thumbnail_id"));
            double lat = cursor.getDouble(cursor.getColumnIndexOrThrow("lat"));
            double longtitude = cursor.getDouble(cursor.getColumnIndexOrThrow("long"));
            int companion = cursor.getInt(cursor.getColumnIndexOrThrow("companions"));
            Hikes temp = new Hikes(
                    name, location, date, length, units, level, parking, description, islove, thumbnail, companion, lat, longtitude
            );
            temp.id = id;
            temp.thumbnail_id = thumbnail_id;
            ret.add(temp);

        }

        return ret;
    }

    public static ArrayList<Hikes> query(){
        ArrayList<Hikes> ret = new ArrayList<>();
        SQLiteDatabase query = db.getReadableDatabase();
        String[] projection = {
                "id",
                "name",
                "location",
                "date",
                "length",
                "units",
                "level",
                "parking",
                "companions",
                "description",
                "thumbnail",
                "thumbnail_id",
                "lat",
                "long",
                "islove"
        };

        String[] arguments = {}; // Used used in WHERE clause

        Cursor cursor = query.query(
                tablename,
                projection,
                "",  arguments,
                null, null, "created DESC"
        );

        while(cursor.moveToNext()){
            int thumbnail_id = cursor.getInt(cursor.getColumnIndexOrThrow("thumbnail_id"));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            int length = cursor.getInt(cursor.getColumnIndexOrThrow("length"));
            String units = cursor.getString(cursor.getColumnIndexOrThrow("units"));
            String level = cursor.getString(cursor.getColumnIndexOrThrow("level"));
            boolean parking = cursor.getInt(cursor.getColumnIndexOrThrow("parking")) == 1 ? true : false;
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            boolean islove = cursor.getInt(cursor.getColumnIndexOrThrow("islove")) == 1 ? true : false;
            String thumbnail = cursor.getString(cursor.getColumnIndexOrThrow("thumbnail"));
            double lat = cursor.getDouble(cursor.getColumnIndexOrThrow("lat"));
            double longtitude = cursor.getDouble(cursor.getColumnIndexOrThrow("long"));
            int companion = cursor.getInt(cursor.getColumnIndexOrThrow("companions"));
            Hikes temp = new Hikes(
                    name, location, date, length, units, level, parking, description, islove, thumbnail, companion, lat, longtitude, thumbnail_id
            );
            temp.id = id;
            ret.add(temp);

        }

        return ret;

    }

    public int id;

    // Properties of this specific instance
    public int thumbnail_id;
    public String name; // Name of this hike
    public String location;  // location of the hike
    public String date; // Date of this hike
    public int length; // length of the hike
    public String units; // Km / m
    public String level; // Difficulty, Intermediate, Easy
    public boolean parking; // Is parking avilable
    public String description; // Description of the hike
    public boolean islove; // is favorited
    public String thumbnail; // The path to the thumbnail image

    public double lat; // Latitude of the hike

    public double longtitude; // longitude of the hike

    public int companion;
    String created;
    String modified;

    public Hikes(String name,
                 String location,
                 String date,
                 int length,
                 String units,
                 String level,
                 boolean parking,
                 String description,
                 boolean islove,
                 String thumbnail_image,
                 int companion,
                 double lat,
                 double longtitude,
                 int thumbnail_id){
        this.lat = lat;
        this.longtitude = longtitude;
        this.name = name;
        this.location = location;
        this.date = date;
        this.length = length;
        this.units = units;
        this.level = level;
        this.parking = parking;
        this.description = description;
        this.islove = islove;
        this.thumbnail = thumbnail_image;
        this.companion = companion;
        this.thumbnail_id = thumbnail_id;
    }
    public Hikes(String name,
                 String location, String date,
                 int length, String units,
                 String level,
                 boolean parking,
                 String description,
                 boolean islove,
                 String thumbnail_image,
                 int companion,
                 double lat,
                 double longtitude){
        this.lat = lat;
        this.longtitude = longtitude;
        this.name = name;
        this.location = location;
        this.date = date;
        this.length = length;
        this.units = units;
        this.level = level;
        this.parking = parking;
        this.description = description;
        this.islove = islove;
        this.thumbnail = thumbnail_image;
        this.companion = companion;
    }

    public Hikes(){}

    public String gettablename(){
        return tablename;
    }

    public static String tablename = "hikes";
    public static final String CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS " + tablename +"  (" +
        "id          INTEGER PRIMARY KEY, " +
        "name        TEXT    NOT NULL, " +
        "location    TEXT    NOT NULL, " +
        "date        TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
        "length      INTEGER NOT NULL, " +
        "units       TEXT    NOT NULL, " +
        "level       TEXT    NOT NULL, " +
        "parking     INTEGER NOT NULL," +
        "companions   INTEGER," +
        "thumbnail   TEXT," +
        "thumbnail_id INTEGER,"  +
        "description TEXT , " +
        "lat TEXT, " +
        "long TEXT , " +
        "islove      INTEGER DEFAULT 0," +
        "created     TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
        "modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
        "FOREIGN KEY (thumbnail_id) REFERENCES media(id)" +
    ");" +
    "CREATE TRIGGER update_modified_timestamp AFTER UPDATE ON "+tablename + " " +
    "FOR EACH ROW " +
    "BEGIN " +
        "UPDATE " + tablename + " SET modified = CURRENT_TIMESTAMP WHERE id = NEW.id; " +
    "END;"; // The last ones are for updating the modified date

    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + tablename;
}
