package com.example.m_hiker.Dialogs;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.content.Context;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

public class MediaPicker {

    private Fragment fragment;
    ActivityResultContracts.PickMultipleVisualMedia picker = new ActivityResultContracts.PickMultipleVisualMedia();
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    public MediaPicker(Fragment fragment, ActivityResultCallback callback){
        this.fragment = fragment;
        this.pickMedia = fragment.registerForActivityResult(picker, callback);
    }

    public void show(){
        pickMedia.launch(new PickVisualMediaRequest.Builder().build());
    }

}
