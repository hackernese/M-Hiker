package com.example.m_hiker.Dialogs.MediaPlayer;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.example.m_hiker.R;
import com.example.m_hiker.database.DatabaseMHike;
import com.example.m_hiker.database.Hikes;
import com.example.m_hiker.database.Observation;
import com.example.m_hiker.database.ObservationMedia;
import java.util.ArrayList;
import java.util.HashMap;

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

    ImageView bookmark;
    DatabaseMHike db;

    private  boolean isbookmark = false;

    public MediaSlider(Context context, ArrayList<ObservationMedia> media, int index, Observation observation) {

        this.context = context;

        // Grabbing database instance
        db = DatabaseMHike.init(context);

        View view = LayoutInflater.from(context).inflate(R.layout.mediaslider, null);
        slider = view.findViewById(R.id.slidermedia);
        datetime = view.findViewById(R.id.datetime);
        bookmark = view.findViewById(R.id.bookmarkbtn);

        // First extracting what hike is this belonging to
        HashMap<String, String> tempstr = new HashMap<>();
        HashMap<String, Integer> tempint = new HashMap<>();
        tempint.put("id", observation.hike_id);
        Hikes currenthike = Hikes.query(tempstr, tempint).get(0);

        // Setting the adapter for grid view
        ArrayList<Fragment> fragments = new ArrayList<>();
        media.forEach(e->{
            fragments.add(new Media().setmedia(e));
        });

        // Watch whenever user swiches page
        slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // This method will be called every time the user sw ipes to a different page.
                Media temp = ((Media) fragments.get(position));

                if (temp.video == null) return;

                Log.d("debug", temp.media.created);

                temp.video.start();
                datetime.setText(temp.media.created);

                // Checking if this is the currently bookmarked one
                if (temp.media.id == currenthike.thumbnail_id){
                    isbookmark = true;
                    bookmark.setImageResource(R.drawable.bookmarkfull);
                }else {
                    isbookmark = false;
                    bookmark.setImageResource(R.drawable.baseline_bookmark_border_24);
                }

            }
        });

        Adapter adapter = new Adapter((FragmentActivity)context, fragments);
        slider.setAdapter(adapter);


        // Setting default slide
        Media temp = ((Media) fragments.get(index));
        datetime.setText(temp.media.created);
        slider.setCurrentItem(index, false);
        if(currenthike.thumbnail_id == temp.media.id){
            bookmark.setImageResource(R.drawable.bookmarkfull);
            isbookmark = true;
        }


        // Bookmark button
        view.findViewById(R.id.bookmarkbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int curerntitem = slider.getCurrentItem();
                int media_id = media.get(curerntitem).id;

//                if(bookmark.)

                HashMap<String, String> items = new HashMap<>();
                items.put("thumbnail_id", "" + ( isbookmark ? "null" : media_id) );

                db.update(currenthike, items);
                currenthike.thumbnail_id = media_id;

                // Checking if the bookmark is currently checked
                if (!isbookmark){
                    isbookmark = true;
                    bookmark.setImageResource(R.drawable.bookmarkfull);
                }else {
                    isbookmark = false;
                    bookmark.setImageResource(R.drawable.baseline_bookmark_border_24);
                }

            }
        });


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
