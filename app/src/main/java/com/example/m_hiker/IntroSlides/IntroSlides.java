package com.example.m_hiker.IntroSlides;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.m_hiker.IntroSlides.screens.Slide1;
import com.example.m_hiker.IntroSlides.screens.Slide2;
import com.example.m_hiker.IntroSlides.screens.Slide3;
import com.example.m_hiker.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntroSlides#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroSlides extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IntroSlides() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntroSlides.
     */
    // TODO: Rename and change types and number of parameters
    public static IntroSlides newInstance(String param1, String param2) {
        IntroSlides fragment = new IntroSlides();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro_slides, container, false);

        ViewPager2 viewpager = view.findViewById(R.id.intropager);

        // Creating a slide of fragments
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new Slide1());
        fragments.add(new Slide2());
        fragments.add(new Slide3());

        // Build an adapter
        IntroViewPager2Adapter adapter = new IntroViewPager2Adapter(this, fragments);
        viewpager.setAdapter(adapter);

        // Actions
        view.findViewById(R.id.prevslide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int item = viewpager.getCurrentItem();
                if(item==0)
                    return;
                viewpager.setCurrentItem(item-1, true);
            }
        });


        Button next = view.findViewById(R.id.nextslide);

        viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if(position==2)
                    next.setVisibility(View.GONE);
                else
                    next.setVisibility(View.VISIBLE);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int item = viewpager.getCurrentItem();
                if(item==2) {
                    return;
                }
                viewpager.setCurrentItem(item+1, true);
            }
        });


        return view;
    }
}