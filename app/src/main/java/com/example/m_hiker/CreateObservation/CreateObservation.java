package com.example.m_hiker.CreateObservation;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.m_hiker.CreateObservation.ImageGrid.ImageGridAdapter;
import com.example.m_hiker.CreateObservation.ImageGrid.ImageItem;
import com.example.m_hiker.CreateObservation.screens.CreateObservationDate;
import com.example.m_hiker.CreateObservation.screens.CreateObservationSlideDetails;
import com.example.m_hiker.CreateObservation.screens.CreateObservationSlideMedia;
import com.example.m_hiker.R;
import com.example.m_hiker.database.DatabaseMHike;
import com.example.m_hiker.database.Observation;
import com.example.m_hiker.database.ObservationMedia;
import com.example.m_hiker.utils.debug;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateObservation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateObservation extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateObservation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateObservation.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateObservation newInstance(String param1, String param2) {
        CreateObservation fragment = new CreateObservation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    DatabaseMHike db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Grabbing the database instance
        db = DatabaseMHike.init(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_observation, container, false);

        // Extracting the current hike ID

        Integer hike_id = -1;
        Bundle args = getArguments();

        if(args!= null && args.containsKey("id"))
            hike_id = args.getInt("id");

        // Initialize ViewPager2 for sliding contents
        ViewPager2 pager = (ViewPager2) view.findViewById(R.id.createobserveslider);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new CreateObservationSlideMedia());
        fragments.add(new CreateObservationSlideDetails());
        fragments.add(new CreateObservationDate());


        // Creating an adapter so the PageView2 can work properly
        CreateObservationAdapter adapter = new CreateObservationAdapter(this, fragments);
        pager.setAdapter(adapter);

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

                TextView labeltitle = (TextView)view.findViewById(R.id.observationtitletext);
                ImageView img0 = (ImageView)view.findViewById(R.id.bullet0);
                ImageView img1 = (ImageView)view.findViewById(R.id.bullet1);
                ImageView img2 = (ImageView)view.findViewById(R.id.bullet2);

                img0.setImageResource(R.drawable.circle_empty);
                img1.setImageResource(R.drawable.circle_empty);
                img2.setImageResource(R.drawable.circle_empty);


                // Changing stuffs
                ImageView selected = position==0 ? img0 : position==1 ? img1 : img2;
                selected.setImageResource(R.drawable.circle_full);

                String title = position==0 ? "Basic info" : position==1 ? "Date and time" : "Media files";

                labeltitle.setText(title);

            }
        });

        // Click to create observation
        view.findViewById(R.id.createobservationbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view_local) {

                // Generate faker data first to make sure it works properly
                String title = debug.generate_faker_text(10,12);
                String category = "Testhike";
                String weather = "Rainy";
                String date = "11/23/2023";
                String time = "11:11";
                String comments = debug.generate_faker_text(20, 50);

                // Extracting all exsiting images and video from the media one to put into the ObservationMedia table
                // First access the Adapter of the Slider first
                GridView v = pager.findViewById(R.id.imagegrid);
                ImageGridAdapter adapter = (ImageGridAdapter)v.getAdapter();
                List<ImageItem> medialist = adapter.getItems();

                // COnstruct a new Observation object to add into the database later
                Observation newob = new Observation(
                        title,
                        category,
                        weather,
                        date,
                        time,
                        comments,
                        false, 1
                );
                long ob_id = db.insert(newob);

                // Inserting media into the database with the provided observation id
                for(ImageItem item : medialist)
                {
                    Log.d("debug", item.uripath.getPath());

                    ObservationMedia new_media = new ObservationMedia(
                        ob_id,
                        item.uripath.getPath(),
                            false
                    );
                    db.insert(new_media);
                }

                // Simply navigate back to the hike page
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}