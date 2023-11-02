package com.example.m_hiker.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.m_hiker.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import javax.security.auth.callback.Callback;

public class SocialMedia {

    BottomSheetDialog dialog;
    View dialogview;
    public SocialMedia(Context context, View view){
        dialog = new BottomSheetDialog(context);
        dialogview = LayoutInflater.from(context).inflate(
                R.layout.social_media,
                (LinearLayout)view.findViewById(R.id.socialcontainer)
        );
        // Setting default value
        dialog.setContentView(dialogview);
        dialog.show();
    }

    public static class Callback {
        public void instagram(){}
        public void twitter(){}
        public void pinterest(){}
        public void facebook(){}
    }

    public void setcallback(Callback callback){

        dialogview.findViewById(R.id.facebooksocial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.facebook();
            }
        });

        dialogview.findViewById(R.id.twittersocial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.twitter();

            }
        });
        dialogview.findViewById(R.id.pinterestsocial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.pinterest();

            }
        });
        dialogview.findViewById(R.id.instagramsocial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.instagram();

            }
        });

    }

}
