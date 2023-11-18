package com.example.m_hiker.utils;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class func {


    private func(){

    }

    public static void enable_text_label(View label){

    }

    public static void getcoords(String search){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://maps.googleapis.com/maps/api/geocode/json?key=" + storex.apikey + "&address="
        + URLEncoder.encode(search, StandardCharsets.UTF_8)).build(); // ENcoding the query to fit URL-encoding scheme
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("debug", "Failed");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(!response.isSuccessful())
                    return;

                // JSON string below
                String json_response = response.body().string();
                Log.d("debug", json_response);

                try{
                    JSONObject obj = new JSONObject(json_response);
                    JSONArray ret = obj.getJSONArray("results");
                    JSONObject retobject = ret.getJSONObject(0);
                    JSONObject geometry = retobject.getJSONObject("geometry");

                    JSONObject location = geometry.getJSONObject("location");

                }catch (Exception e){
                    Log.d("debug", e.toString());
                }
            }
        });
    }

    public static String getfilename_based_on_date(){

        // Extract current time
        LocalDateTime dt = LocalDateTime.now();

        // Build a formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss:SSS");

        // FOrmat it
        return dt.format(formatter);

    }


    public static boolean copyInputStream(InputStream inputStream, String filename) {
        try {
            // Create the destination folder if it doesn't exist
            File destFolder = new File(storex.folder);
            if (!destFolder.exists()) {
                if (!destFolder.mkdirs()) {
                    // Failed to create the destination folder
                    return false;
                }
            }

            // Create the destination file path
            File destFile = new File(destFolder, filename);

            // Create a FileOutputStream for the destination file
            FileOutputStream outputStream = new FileOutputStream(destFile);

            // Copy the contents of the InputStream to the destination file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            // Close the streams
            inputStream.close();
            outputStream.close();

            return true; // Successfully copied the InputStream to the destination file
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Failed to copy the InputStream to the destination file
        }
    }

}
