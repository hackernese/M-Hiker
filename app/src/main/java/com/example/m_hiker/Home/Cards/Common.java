package com.example.m_hiker.Home.Cards;

import android.content.Context;
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

public class Common {
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
        void OnLongClick(GridCardAdapter parent, Common child);
        void OnClick(GridCardAdapter parent, Common child);
        void OnLove(GridCardAdapter parent, Common child);
    }

    public boolean is_selected = false;

    public void setCallback(Callback callback){};
    public void select(){
        unselected.setVisibility(View.GONE);
        selected.setVisibility(View.VISIBLE);
        favorite.setVisibility(View.GONE);
        is_selected = true;
    };
    public void unselect(){
        unselected.setVisibility(View.VISIBLE);
        selected.setVisibility(View.GONE);
        title.setText("DAWDAWD");
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
    public Common self;

    public Common(Hikes item, GridCardAdapter adapter) {
        this.inflater = adapter.inflater;
        this.context = adapter.context;
        this.db = DatabaseMHike.init(context);
        this.parentadapter = adapter;
        this.item = item;
        this.self = this;
    }

    public View getView(){

        title.setText(item.name);
        country.setText(item.location);
        describe.setText(item.description);

        if(item.islove){
            favorite.setImageResource(R.drawable.favorite);
        }else{
            favorite.setImageResource(R.drawable.heart);
        }

        return view;
    }
}
