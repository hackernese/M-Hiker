package com.example.m_hiker.Hike.ObservationCard;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class ObservationThumbnailAdapter extends FragmentStateAdapter {

    private ArrayList<Fragment> fragments;

    public ObservationThumbnailAdapter(Fragment fragmentActivity, ArrayList<Fragment> fragments ){
        super(fragmentActivity);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();

    }

}
