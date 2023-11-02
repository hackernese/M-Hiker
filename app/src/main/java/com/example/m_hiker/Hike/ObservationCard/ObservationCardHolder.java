package com.example.m_hiker.Hike.ObservationCard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.m_hiker.R;
import com.example.m_hiker.database.Observation;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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

    public void setpager(Observation ob, Fragment activity){
        fragments.add(new ObservationCardImage());
        fragments.add(new ObservationCardImage());
        fragments.add(new ObservationCardImage());
        ObservationThumbnailAdapter adapter = new ObservationThumbnailAdapter(activity, fragments);
        obpager.setAdapter(adapter);
    }
    public ObservationCardHolder(@NonNull View itemView) {
        super(itemView);
        self = itemView;
        obpager = itemView.findViewById(R.id.observationpager);

        category = itemView.findViewById(R.id.categoryobcard);
        title = itemView.findViewById(R.id.titleobcard);

        itemView.findViewById(R.id.accessobbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog dialog = new BottomSheetDialog(context);
                View dialogview = LayoutInflater.from(context).inflate(
                        R.layout.hike_ob_bottomsheet,
                        (LinearLayout)view.findViewById(R.id.obcontainer)
                );

                Button delete = dialogview.findViewById(R.id.deletebtn);
                Button share = dialogview.findViewById(R.id.sharebtn);
                Button edit = dialogview.findViewById(R.id.editbtnhike);
                Button viewbtn = dialogview.findViewById(R.id.viewbtn);

                // Create a bundle to pass to other view
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                viewbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Navigation.findNavController(parentView).navigate(R.id.action_viewHike_to_viewObservation, bundle);
                        dialog.dismiss();
                    }
                });

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });


                // Setting default value
                dialog.setContentView(dialogview);
                dialog.show();
            }
        });

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
