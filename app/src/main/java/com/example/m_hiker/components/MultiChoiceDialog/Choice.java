package com.example.m_hiker.components.MultiChoiceDialog;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hiker.R;

public class Choice extends RecyclerView.ViewHolder {

    public TextView text;
    public ImageView selecticon;
    public Button onclick;
    public boolean selected = false;

    public void setState(boolean state){
        selecticon.setVisibility(state ? View.VISIBLE : View.INVISIBLE);
    }

    public Choice(@NonNull View itemView) {
        super(itemView);

        selecticon = itemView.findViewById(R.id.checkedicon);
        text = itemView.findViewById(R.id.textview);
        onclick = itemView.findViewById(R.id.onclick);

    }
}
