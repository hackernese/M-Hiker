package com.example.m_hiker.Home.HikesCard.cards;
import android.view.View;
import androidx.annotation.NonNull;
import com.example.m_hiker.R;
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
