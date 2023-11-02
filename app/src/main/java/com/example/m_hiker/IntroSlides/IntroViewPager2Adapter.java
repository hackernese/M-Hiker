package com.example.m_hiker.IntroSlides;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class IntroViewPager2Adapter extends FragmentStateAdapter {

    private ArrayList<Fragment> fragments;


    public IntroViewPager2Adapter(Fragment fragmentActivity, ArrayList<Fragment> fragments){
        super(fragmentActivity);
        this.fragments = fragments;
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }
}
