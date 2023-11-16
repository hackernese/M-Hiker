package com.example.m_hiker.CreateHikePage.LocationList;

import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    Callback callback;

    public static interface Callback{
        void call(String name, double lat, double long_);
    }

    public LocationAdapter setcallback(Callback callback){
        this.callback = callback;
        return this;
    }

    public void setlocationlist(List<Address> addresses){

        this.items = new ArrayList<>();
        this.items.add(new LocationItem());

        if (addresses.isEmpty()) {
            this.notifyDataSetChanged();
            return;
        }
        addresses.forEach(e->{
            items.add(new LocationItem(e));
            Log.d("debug", e.toString());
        });

        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        LocationItem item = items.get(position);

        holder.selectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.is_default){
                    // This is the "Use current location"
                    callback.call("", 0, 0);
                    return;
                }

                callback.call(item.name, item.latitude, item.longtitude);
            }
        });

        if(item.is_default)
            return;

        holder.country.setText(item.country);
        holder.address.setText(item.name);
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