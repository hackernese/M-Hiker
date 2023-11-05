package com.example.m_hiker.Home.Cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.m_hiker.Home.GridCardAdapter;
import com.example.m_hiker.R;
import com.example.m_hiker.database.DatabaseMHike;
import com.example.m_hiker.database.Hikes;

public class BigCard extends Common{

    View view;
    LayoutInflater inflater;
    Context context;
    DatabaseMHike db;
    GridCardAdapter parentadapter;
    public Callback callback;
    @Override
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void select() {

    }

    @Override
    public void unselect() {

    }

    @Override
    public void stopselect() {

    }

    public BigCard(Hikes item, GridCardAdapter adapter) {
        super(item, adapter);

        view = inflater.inflate(R.layout.bigcarditem, null);

        TextView description = view.findViewById(R.id.describehuge); ;
        TextView location = view.findViewById(R.id.biglocation); ;
        TextView title = view.findViewById(R.id.titlebig); ;
        ImageView thumbnail = view.findViewById(R.id.thumbnailbig); ;
        ImageView unselected = view.findViewById(R.id.unselected); ;
        ImageView selected = view.findViewById(R.id.selectedicon); ;
        Button clickimage = view.findViewById(R.id.clickhuge); ;
        ImageView favor = view.findViewById(R.id.unselectedheart); ;
        ImageView unfavor = view.findViewById(R.id.favoritebig); ;

        // Setting initial information for each card
        description.setText(item.description);
        location.setText(item.location);
        title.setText(item.name);

        // Setting actions
        unfavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public View getView() {
        return view;
    }
}
