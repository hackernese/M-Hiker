package com.example.m_hiker.Dialogs;

import android.content.Context;
import android.content.Intent;

public class SocialMedia {


    private Context context;

    public SocialMedia(Context context){
        this.context = context;
    }


    public void share(String title){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, title);
        intent.setType("text/plain");

        Intent share = Intent.createChooser(intent, "Share via");

        if(share.resolveActivity(context.getPackageManager()) != null ){
            context.startActivity(share);
        }
    }

}
