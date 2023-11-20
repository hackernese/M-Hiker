package com.example.m_hiker.CreateObservation.ImageGrid;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.m_hiker.database.CommonTable;

import java.io.File;

public class ImageItem {

    public CommonTable object;

    public String path;
    public Uri uripath;
    public Boolean is_add = false;

    public ImageItem(String name){
        this.path = name;
    }

    public ImageItem(){

    }
    public ImageItem(Uri path){
        this.uripath = path;
    }

    public boolean isadded(){
        return this.is_add;
    }

    public ImageItem setIsAdd(Boolean value){
        this.is_add = value;
        return this;
    }

    public Uri craft_image_resource(){
        return Uri.fromFile(new File(this.path));
    }


}
