package com.example.m_hiker.Dialogs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.content.FileProvider;

import java.io.File;
import java.util.ArrayList;

public class SocialMedia {


    private Context context;

    public SocialMedia(Context context){
        this.context = context;
    }


    public void share(String title, String subject, ArrayList<String> uris){


        ArrayList<Uri> urisObjlist = new ArrayList<>();

        uris.forEach(path->{
            File file = new File(path);
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            urisObjlist.add(uri);
        });

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.putExtra(Intent.EXTRA_TEXT, title);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, urisObjlist);
        intent.setType("*/*");

        Intent share = Intent.createChooser(intent, "Share via");

        if(share.resolveActivity(context.getPackageManager()) != null ){
            context.startActivity(share);
        }
    }

}
