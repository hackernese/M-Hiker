package com.example.m_hiker.CreateObservation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class CreateObservationAdapter extends FragmentStateAdapter {
    private ArrayList<Fragment> fragments;

    public CreateObservationAdapter(Fragment fragmentActivity, ArrayList<Fragment> fragments ){
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
