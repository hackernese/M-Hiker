package com.example.m_hiker.Home.Cards;

import android.util.Log;
import android.view.View;

import com.example.m_hiker.Home.GridCardAdapter;
import com.example.m_hiker.R;
import com.example.m_hiker.database.DatabaseMHike;
import com.example.m_hiker.database.Hikes;

import java.util.HashMap;

public class ListCard extends Common {

    @Override
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public ListCard(Hikes item, GridCardAdapter adapter) {
        super(item, adapter);

        view = inflater.inflate(R.layout.hike_card, null);
        title = view.findViewById(R.id.hike_card_title);
        country = view.findViewById(R.id.location);
        describe = view.findViewById(R.id.bio);
        favorite = view.findViewById(R.id.lovethishike);
        navigatehike = view.findViewById(R.id.navigatortohikebtn);
        thumbnail = view.findViewById(R.id.thumbnailcard);
        selected = view.findViewById(R.id.fullselected);
        unselected = view.findViewById(R.id.unselected);


        // Click listeners below
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.OnLove(adapter, self);
            }
        });
        navigatehike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.OnClick(adapter, self);
            }
        });
        navigatehike.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                callback.OnLongClick(adapter, self);

                favorite.setVisibility(View.GONE);

                return true;
            }
        });
    }

    @Override
    public View getView() {

        Log.d("debug", "DAWDAWD");


        return super.getView();
    }
}
