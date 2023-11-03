package com.example.m_hiker.utils;

import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class func {


    private func(){

    }

    public static void enable_text_label(View label){

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
