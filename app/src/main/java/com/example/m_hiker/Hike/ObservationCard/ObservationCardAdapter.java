package com.example.m_hiker.Hike.ObservationCard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hiker.R;
import com.example.m_hiker.components.HikesCard.HikeCardViewHolder;
import com.example.m_hiker.database.Hikes;
import com.example.m_hiker.database.Observation;

import java.util.ArrayList;
import java.util.List;

public class ObservationCardAdapter extends RecyclerView.Adapter<ObservationCardHolder> {

    Context context;
    List<Observation> observations;
    View inflated_view;

    Fragment activity;
    View parentView;

    public ObservationCardAdapter(Context context, List<Observation> observations, Fragment activity, View parentView){
        this.context = context;
        this.observations = observations;
        this.activity = activity;
        this.parentView = parentView;
    }

    @NonNull
    @Override
    public ObservationCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflated_view = LayoutInflater.from(context).inflate(R.layout.observation_card, parent, false);
        return new ObservationCardHolder(inflated_view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationCardHolder holder, int position) {

        Observation ob  = observations.get(position);
        holder.setpager(ob, activity);

        holder.parentView = parentView;
        holder.id = ob.id;
        holder.category.setText(ob.category);
        holder.title.setText(ob.title);
        holder.context = context;

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new ObservationCardImage());
        fragments.add(new ObservationCardImage());
        fragments.add(new ObservationCardImage());

        ObservationThumbnailAdapter adapter = new ObservationThumbnailAdapter(activity, fragments);
        holder.obpager.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return observations.size();
    }
}
