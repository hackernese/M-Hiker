package com.example.m_hiker.Home.HikesCard.cards;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.m_hiker.R;

public class HugeCard extends CardHolder{
    public HugeCard(@NonNull View itemView) {
        super(itemView);
        fullselected = itemView.findViewById(R.id.hugeselected);
        unselected = itemView.findViewById(R.id.hugeunselected);
        self = itemView.findViewById(R.id.cardviewhikehuge);
        favorite = itemView.findViewById(R.id.hugefavorite);
        title = itemView.findViewById(R.id.hugetitle);
        description = itemView.findViewById(R.id.hugedescribe);
        location = itemView.findViewById(R.id.hugelocation);
        navigatetohike = itemView.findViewById(R.id.hugeclick);
    }
}