package com.example.m_hiker.Home.Cards;

import android.content.Context;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.m_hiker.Home.GridCardAdapter;
import com.example.m_hiker.R;
import com.example.m_hiker.database.DatabaseMHike;
import com.example.m_hiker.database.Hikes;

public class Test {

    public View view;
    public LayoutInflater inflater;
    public Context context;
    public DatabaseMHike db;
    public GridCardAdapter parentadapter;

    public TextView title;
    public TextView country;
    public TextView describe;
    public ImageView favorite;
    public Button navigatehike;
    public ImageView thumbnail;
    public ImageView selected;
    public ImageView unselected;
    public Callback callback;

    public static  interface  Callback{
        void OnLongClick( Test child);
        void OnClick(Test child);
        void OnLove(Test child);
    }
    public boolean is_selected = false;

    public void setCallback(Callback callback){
        this.callback = callback;
    };
    public void select(){
        unselected.setVisibility(View.GONE);
        selected.setVisibility(View.VISIBLE);
        favorite.setVisibility(View.GONE);
        is_selected = true;
    };
    public Test self;
    public void unselect(){
        unselected.setVisibility(View.VISIBLE);
        selected.setVisibility(View.GONE);
        favorite.setVisibility(View.GONE);
        is_selected = false ;
    };
    public void stopselect(){
        unselected.setVisibility(View.GONE);
        selected.setVisibility(View.GONE);
        favorite.setVisibility(View.VISIBLE);
        is_selected = false;
    };



    public Hikes item;
    public Test(View view, Hikes item) {
        this.db = DatabaseMHike.init(context);
        this.item = item;

        title = view.findViewById(R.id.hike_card_title);
        country = view.findViewById(R.id.location);
        describe = view.findViewById(R.id.bio);
        favorite = view.findViewById(R.id.lovethishike);
        navigatehike = view.findViewById(R.id.navigatortohikebtn);
        thumbnail = view.findViewById(R.id.thumbnailcard);
        selected = view.findViewById(R.id.fullselected);
        unselected = view.findViewById(R.id.unselected);
        this.self = this;

        // Click listeners below
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.OnLove(self);
            }
        });
        navigatehike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.OnClick(self);
            }
        });
        navigatehike.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                callback.OnLongClick(self);

                favorite.setVisibility(View.GONE);

                return true;
            }
        });
        title.setText(item.name);
        country.setText(item.location);
        describe.setText(item.description);

        if(item.islove){
            favorite.setImageResource(R.drawable.favorite);
        }else{
            favorite.setImageResource(R.drawable.heart);
        }
    }

}
