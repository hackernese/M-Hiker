package com.example.m_hiker.Home.HikesCard.cards;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hiker.R;
import com.example.m_hiker.database.Hikes;

public class ListCard extends CardHolder {
    public ListCard(@NonNull View itemView) {
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
