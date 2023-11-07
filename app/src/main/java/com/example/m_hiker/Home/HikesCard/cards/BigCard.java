package com.example.m_hiker.Home.HikesCard.cards;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.m_hiker.R;

public class BigCard extends CardHolder{
    public BigCard(@NonNull View itemView) {
        super(itemView);
        fullselected = itemView.findViewById(R.id.selectedicon);
        unselected = itemView.findViewById(R.id.unselected);
        self = itemView.findViewById(R.id.cardviewhikebig);
        favorite = itemView.findViewById(R.id.favoritebig);
        title = itemView.findViewById(R.id.titlebig);
        description = itemView.findViewById(R.id.describehuge);
        location = itemView.findViewById(R.id.biglocation);
        navigatetohike = itemView.findViewById(R.id.clickhuge);
    }
}
