package com.example.m_hiker.Dialogs.MediaPlayer;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.m_hiker.R;
import com.example.m_hiker.database.ObservationMedia;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MediaSlider{
    AlertDialog.Builder builder;
    AlertDialog dialog;
    ViewPager2 slider;
    Context context;

    public static class Adapter extends FragmentStateAdapter {

        ArrayList<Fragment> fragments;
        public Adapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> fragments) {
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

    TextView datetime;

    public MediaSlider(Context context, ArrayList<ObservationMedia> media) {

        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.mediaslider, null);
        slider = view.findViewById(R.id.slidermedia);
        datetime = view.findViewById(R.id.datetime);

        // Setting the adapter for grid view
        ArrayList<Fragment> fragments = new ArrayList<>();
        media.forEach(e->{
            fragments.add(new Media().setmedia(e));
        });

        // Watch whenever user swiches page
        slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // This method will be called every time the user swipes to a different page.
                Media temp = ((Media)fragments.get(position));

                temp.video.start();
                datetime.setText(temp.media.created);
            }
        });

        Adapter adapter = new Adapter((FragmentActivity)context, fragments);
        slider.setAdapter(adapter);


        // Close button
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        builder = new AlertDialog.Builder(context);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

}
