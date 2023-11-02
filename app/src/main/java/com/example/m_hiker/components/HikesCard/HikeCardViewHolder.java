package com.example.m_hiker.components.HikesCard;

import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hiker.R;
import com.example.m_hiker.database.Hikes;

import org.w3c.dom.Text;

public class HikeCardViewHolder extends RecyclerView.ViewHolder {

    TextView location;
    View self;
    TextView title;
    TextView description;
    ImageView favorite;

    public Hikes obj;

    Button navigatetohike;

    ImageView unselected;
    ImageView fullselected;

    public boolean isselected = false;

    public boolean islove = false;

    // SHould have gone for Builder pattern
    public HikeCardViewHolder showfavorite(boolean s){
        favorite.setVisibility(s ? View.VISIBLE : View.GONE);
        return this;
    }
    public HikeCardViewHolder showcheckedmark(boolean s){
        fullselected.setVisibility(s ? View.VISIBLE : View.GONE);
        isselected = true;
        return this;
    }
    public HikeCardViewHolder showunchecked(boolean s){
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

    public int position;
    public void update_favorite_status(boolean status){
        this.islove = status;
        if(islove){
            favorite.setImageResource(R.drawable.favorite);
        }else{
            favorite.setImageResource(R.drawable.heart);
        }
    }

    public HikeCardViewHolder(@NonNull View itemView) {
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
