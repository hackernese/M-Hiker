package com.example.m_hiker.CreateObservation.ImageGrid;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

public class ImageItem {

    public String path;
    public Uri uripath;
    Boolean is_add = false;

    public ImageItem(String name){
        this.path = name;
    }

    public ImageItem(){

    }
    public ImageItem(Uri path){
        this.uripath = path;
    }

    public ImageItem setIsAdd(Boolean value){
        this.is_add = value;
        return this;
    }

    public Uri craft_image_resource(){
        return Uri.fromFile(new File(this.path));
    }


}
