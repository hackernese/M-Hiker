package com.example.m_hiker.CreateHikePage.LocationList;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hiker.R;

import org.w3c.dom.Text;

public class LocationViewHolder  extends RecyclerView.ViewHolder {

    public TextView country;
    public TextView address;

    public Button selectbtn;

    public LocationViewHolder(@NonNull View itemView) {
        super(itemView);

        this.address = itemView.findViewById(R.id.addressline);
        this.country = itemView.findViewById(R.id.country);
        this.selectbtn = itemView.findViewById(R.id.selectlocation);
    }
}
