package com.example.m_hiker.Hike.ObservationCard;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.m_hiker.R;
import com.example.m_hiker.database.Observation;

import java.util.ArrayList;

public class ObservationCardHolder extends RecyclerView.ViewHolder {


    View self;
    ImageButton back;
    ImageButton next;

    public int id; // Hike id

    public ViewPager2 obpager;

    public TextView category;
    public TextView title;
    public Context context;

    ArrayList<Fragment> fragments = new ArrayList<>();
    View parentView;

    Observation object;

    public void setpager(Observation ob, Fragment activity){
        ObservationThumbnailAdapter adapter = new ObservationThumbnailAdapter(activity, fragments);
        obpager.setAdapter(adapter);
    }

    FragmentManager manager;


    ImageButton bookmark;
    ImageButton bookmarkselected;

    public ObservationCardHolder setfragmentmanager(FragmentManager frag){
        this.manager = frag;
        return this;
    }

    public ObservationCardHolder(@NonNull View itemView) {
        super(itemView);
        self = itemView;
        obpager = itemView.findViewById(R.id.observationpager);
        category = itemView.findViewById(R.id.categoryobcard);
        title = itemView.findViewById(R.id.titleobcard);


        itemView.findViewById(R.id.obcardback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int item = obpager.getCurrentItem();
                if(item==0)return;

                obpager.setCurrentItem(item-1, true);
            }
        });

        itemView.findViewById(R.id.obcardnext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int item = obpager.getCurrentItem();
                if(item==fragments.size()-1)return;

                obpager.setCurrentItem(item+1, true);
            }
        });
    }
}
