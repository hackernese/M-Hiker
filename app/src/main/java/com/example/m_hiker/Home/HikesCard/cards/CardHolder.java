package com.example.m_hiker.Home.HikesCard.cards;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hiker.R;
import com.example.m_hiker.database.Hikes;

public class CardHolder  extends RecyclerView.ViewHolder {

    public TextView location;
    public View self;
    public TextView title;
    public TextView description;
    public ImageView favorite;
    public Hikes obj;
    public Button navigatetohike;

    public ImageView unselected;
    public ImageView fullselected;

    public boolean isselected = false;

    public boolean islove = false;

    public CardHolder showfavorite(boolean s){
        favorite.setVisibility(s ? View.VISIBLE : View.GONE);
        return this;
    }
    public CardHolder showcheckedmark(boolean s){
        fullselected.setVisibility(s ? View.VISIBLE : View.GONE);
        isselected = true;
        return this;
    }
    public CardHolder showunchecked(boolean s){
        unselected.setVisibility(s ? View.VISIBLE : View.GONE);
        isselected = false;
        return this;
    }
    public void selectmode(boolean mode){
        favorite.setVisibility(mode ? View.GONE : View.VISIBLE);
        unselected.setVisibility(mode ? View.VISIBLE : View.GONE);

        if(!mode) {
            fullselected.setVisibility(View.GONE);
            isselected = false;
        }
    }
    public void  toggle(){
        if(!isselected){
            fullselected.setVisibility(View.VISIBLE);
            unselected.setVisibility(View.GONE);
        }else{
            fullselected.setVisibility(View.GONE);
            unselected.setVisibility(View.VISIBLE);
        }

        isselected = !isselected;
    }

    public void selectthis(boolean state){

        isselected = state;

        if(state){
            favorite.setVisibility(View.GONE);
            fullselected.setVisibility(View.VISIBLE);
            unselected.setVisibility(View.GONE);
            return;
        }

        favorite.setVisibility(View.VISIBLE);
        fullselected.setVisibility(View.GONE);
        unselected.setVisibility(View.GONE);
    }

    public ImageView image;

    public int position;
    public void update_favorite_status(boolean status){
        this.islove = status;
        if(islove){
            favorite.setImageResource(R.drawable.favorite);
        }else{
            favorite.setImageResource(R.drawable.heart);
        }
    }



    public CardHolder(@NonNull View itemView) {
        super(itemView);
        fullselected = itemView.findViewById(R.id.fullselected);
        unselected = itemView.findViewById(R.id.unselected);
        self = itemView.findViewById(R.id.cardviewhike);
        favorite = itemView.findViewById(R.id.lovethishike);
        title = itemView.findViewById(R.id.hike_card_title);
        description = itemView.findViewById(R.id.bio);
        location = itemView.findViewById(R.id.location);
        navigatetohike = itemView.findViewById(R.id.navigatortohikebtn);
    }



}
