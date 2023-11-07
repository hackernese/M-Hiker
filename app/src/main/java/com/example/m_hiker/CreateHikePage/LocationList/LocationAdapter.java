package com.example.m_hiker.CreateHikePage.LocationList;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hiker.R;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationViewHolder> {


    Context context;
    List<LocationItem> items;

    public LocationAdapter(Context context, ArrayList<LocationItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {

    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationViewHolder(LayoutInflater.from(context).inflate(R.layout.location_card, parent, false));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}