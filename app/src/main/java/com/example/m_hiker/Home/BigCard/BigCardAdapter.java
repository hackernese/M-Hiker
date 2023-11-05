package com.example.m_hiker.Home.BigCard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.example.m_hiker.Home.HikesCard.HikeCardAdapter;
import com.example.m_hiker.R;
import com.example.m_hiker.database.Hikes;

import java.util.ArrayList;

public class BigCardAdapter extends BaseAdapter {



    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public BigCardAdapter(Context context, ArrayList<Hikes> items, View parentView, FragmentManager manager, HikeCardAdapter parentAdapter){
        this.items = items;
        this.context = context;
        this.parentAdapter = parentAdapter;
    }

    HikeCardAdapter parentAdapter;

    LayoutInflater inflater;
    Context context;

    public ArrayList<Hikes> items;

    private ImageView favor;
    private ImageView unfavor;
    private Button clickimage;
    private ImageView selected;
    private ImageView unselected;
    private ImageView thumbnail;
    private TextView title;
    private TextView location;
    private TextView description;

    public void unfavorcard(){
        unfavor.setVisibility(View.VISIBLE);
        favor.setVisibility(View.GONE);
    }
    public void favorcard(){
        unfavor.setVisibility(View.GONE);
        favor.setVisibility(View.VISIBLE);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.hike_card, null);

//        description = view.findViewById(R.id.describehuge);
//        location = view.findViewById(R.id.biglocation);
//        title = view.findViewById(R.id.titlebig);
//        thumbnail = view.findViewById(R.id.thumbnailbig);
//        unselected = view.findViewById(R.id.unselected);
//        selected = view.findViewById(R.id.selectedicon);
//        clickimage = view.findViewById(R.id.clickhuge);
//        favor = view.findViewById(R.id.unselectedheart);
//        unfavor = view.findViewById(R.id.favoritebig);
//
//        // Setting initial information for each card
//        Hikes item = items.get(i);
//        description.setText(item.description);
//        location.setText(item.location);
//        title.setText(item.name);
//        if(item.islove)
//            favorcard();
//
//        // Setting actions
//        unfavor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                parentAdapter.holderlist.get(i).favorite.callOnClick();
//            }
//        });

        return view;
    }
}
