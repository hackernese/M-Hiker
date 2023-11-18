package com.example.m_hiker.Hike;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hiker.Hike.ObservationCard.ObservationCardAdapter;
import com.example.m_hiker.R;
import com.example.m_hiker.database.Observation;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewHike#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewHike extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewHike() {
        // Required empty public constructor
    }

    public static ViewHike newInstance(String param1, String param2) {
        ViewHike fragment = new ViewHike();
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
    String name = "temp";
    int hike_id = 1;
    List<Observation> allobservation;


    View emptysection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_view_hike, container, false);

        // Extracting arguments;
        if(getArguments().containsKey("name"))
            name = getArguments().getString("name");
        if(getArguments().containsKey("id"))
            hike_id = getArguments().getInt("id");

        // Setting the correct text labels of this
        TextView toolbar = view.findViewById(R.id.toolbarhikedetails);
        toolbar.setText(name);

        // Extracting all observations and binding to the RecylerView
        allobservation = Observation.query("hike_id", ""+hike_id);
        RecyclerView observationlist = view.findViewById(R.id.observationlist);
        observationlist.setLayoutManager(new LinearLayoutManager(getContext()));
        Context context = getContext();

        ObservationCardAdapter adapter =  new ObservationCardAdapter(context, allobservation, this, view);
        observationlist.setAdapter(adapter);

        emptysection = view.findViewById(R.id.emptyob);

        adapter.setcallback( new ObservationCardAdapter.Callback() {
            @Override
            public void delete() {

                // After deleting, reset the interface

                adapter.observations = Observation.query("hike_id", ""+hike_id);
                observationlist.setAdapter(adapter);

                // If no observation is available left, appear the old "Not found" label
                if(adapter.observations.size()==0){
                    emptysection.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void share() {

            }
        });



        // -------- click events ---------------
        view.findViewById(R.id.addnewob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", hike_id);
                Navigation.findNavController(view).navigate(R.id.action_viewHike_to_createObservation, bundle);
            }
        });
        view.findViewById(R.id.backbtnnavigate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}