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

public class BigCard extends Common{

    @Override
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public BigCard(Hikes item, GridCardAdapter adapter) {
        super(item, adapter);
        view = inflater.inflate(R.layout.bigcarditem, null);
        title = view.findViewById(R.id.titlebig);
        country = view.findViewById(R.id.biglocation);
        describe = view.findViewById(R.id.describehuge);
        favorite = view.findViewById(R.id.favoritebig);
        navigatehike = view.findViewById(R.id.clickhuge);
        thumbnail = view.findViewById(R.id.thumbnailbig);
        selected = view.findViewById(R.id.selectedicon);
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
        return super.getView();
    }
}
