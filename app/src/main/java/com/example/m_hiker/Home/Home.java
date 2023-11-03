package com.example.m_hiker.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.example.m_hiker.R;
import com.example.m_hiker.components.HikesCard.HikeCardAdapter;
import com.example.m_hiker.database.DatabaseMHike;
import com.example.m_hiker.database.Hikes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    DatabaseMHike db;

    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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

        Log.d("debug", "Initialized dashboard fragment");

        db = DatabaseMHike.init(getContext());

    }
    boolean is_search_on = false;
    ArrayList<Hikes> allhikes;

    // Conditions for searching go here
    boolean switched_to_favorite = false;
    String search_date ="";
    long search_date_long = 0; // for miliseconds
    String search_location = "";
    int search_length = 0;
    String search_unit = "";
    String search_name = "";

    private void reapply_adapter_with_search_params(RecyclerView recyler, HikeCardAdapter adapter){

        HashMap<String, String> params = new HashMap<>();
        HashMap<String, Integer> paramsint = new HashMap<>();


        Log.d("debug", " " + switched_to_favorite);

        if(switched_to_favorite) {
            paramsint.put("islove", switched_to_favorite ? 1 : 0);
        }
        if(search_length > 0){
            paramsint.put("length", search_length );
            params.put("units", search_unit);
        }
        if(search_name.length() > 0)
            params.put("name", search_name);
        if(search_date.length() > 0)
            params.put("date", search_date);
        if(search_location.length() > 0)
            params.put("location", search_location);

        ArrayList<Hikes> temphikes = Hikes.query(params, paramsint);
        adapter.setnewitems(temphikes);
        recyler.setAdapter(adapter);
    }

    SpeechRecognizer recognizer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        view.findViewById(R.id.addhikebtn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Navigate to the create new hike page
                Navigation.findNavController(view).navigate(R.id.action_home2_to_createHikeIndex);
            }
        });

        // Grabbing the bottom view
        RecyclerView list_of_hike_view = view.findViewById(R.id.listofhikes);
        list_of_hike_view.setLayoutManager(new LinearLayoutManager(getContext()));

        allhikes = Hikes.query();
        HikeCardAdapter adapter = new HikeCardAdapter(getContext(), allhikes, view).setManager(getParentFragmentManager());
        adapter.setcallback(new HikeCardAdapter.Callback() {
            @Override
            public void onchange() {
                adapter.items = Hikes.query();
                list_of_hike_view.setAdapter(adapter);
            }
        });

        list_of_hike_view.setAdapter(adapter);

        // Detect if the recycle view is at its top
        View overlaytop = view.findViewById(R.id.overlaytop);
        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
        Animation fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fadeout);
        list_of_hike_view.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int Xpos) {
                int first_card_visibility = ((LinearLayoutManager)((RecyclerView)view).getLayoutManager()).findFirstVisibleItemPosition();

                Log.d("debug", "" + Xpos + " " + first_card_visibility);

                if(Xpos < 0){
                    // Scrolling downward
                    if(overlaytop.getVisibility() == View.INVISIBLE){
                        overlaytop.setVisibility(View.VISIBLE);
                        overlaytop.startAnimation(fadeIn);
                    }

                }else if(Xpos > 0){
                    // SCrolling upward
                    if(first_card_visibility==0) {
                        overlaytop.setVisibility(View.INVISIBLE);
                        overlaytop.startAnimation(fadeOut);
                    }
                }
                else{
                    // First initialized
                    overlaytop.setVisibility(View.INVISIBLE);
                }

            }
        });


        // Setting up the speech
        recognizer = new SpeechRecognizer(getActivity());

        // Search with voice if possible
        view.findViewById(R.id.voiceicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                try {
//                    startActivityForResult(recognizer.recognizerIntent, 100);
//                } catch (ActivityNotFoundException e) {
//                    Log.d("debug", "WAD");
//                }
            }
        });

        // Check whether the user has switched to "Favorite"
        ((BottomNavigationView)view.findViewById(R.id.bottomNavigationViewHome)).setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                Log.d("debug", "Switching to panel of : " + id);


                switched_to_favorite = id==R.id.favoriteselector;

                reapply_adapter_with_search_params(list_of_hike_view, adapter);

                return true;
            }
        });

        // Apply to click actions to filter chips
        Chip datefilterchip =  view.findViewById(R.id.datefilterchip);
        Chip locationchip =  view.findViewById(R.id.locationchip);
        Chip lengthchip =  view.findViewById(R.id.lengthchip);

        FilterDialog dialogmanager = new FilterDialog(getContext(), view, new FilterDialog.Observer(){
            @Override
            public void lengthcallback(int length, String unit) {
                search_length = length;
                search_unit = unit;
                if(length > 0){
                    lengthchip.setText(length + unit);
                }else{
                    lengthchip.setText("Length");
                }

                reapply_adapter_with_search_params(list_of_hike_view, adapter);
            }

            @Override
            public void locationcallback(String location) {
                search_location = location;
                if(location.length() > 0){
                    locationchip.setText(location);
                }else{
                    locationchip.setText("Location");
                }

                reapply_adapter_with_search_params(list_of_hike_view, adapter);
            }

            @Override
            public void datecallback(String date, long milis) {
                search_date = date;
                search_date_long = milis;

                if(date.length() > 0){
                    datefilterchip.setText(date);
                }else{
                    datefilterchip.setText("Date");
                }
                reapply_adapter_with_search_params(list_of_hike_view, adapter);
            }
        });

        datefilterchip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogmanager.open_fore_menu(new FilterDialog.ForceMenuReply(){
                    @Override
                    public void none() {
                       search_date_long = 0;
                       search_date = "";
                       reapply_adapter_with_search_params(list_of_hike_view, adapter);
                    }

                    @Override
                    public void accept() {
                        dialogmanager.date(search_date_long);
                    }
                }, !search_date.equals(""));

            }
        });

        locationchip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogmanager.open_fore_menu(new FilterDialog.ForceMenuReply(){
                    @Override
                    public void none() {
                        search_location = "";
                        reapply_adapter_with_search_params(list_of_hike_view, adapter);
                    }

                    @Override
                    public void accept() {
                        dialogmanager.location(search_location);
                    }
                }, !search_location.equals("") );
            }
        });

        lengthchip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogmanager.open_fore_menu(new FilterDialog.ForceMenuReply(){
                    @Override
                    public void none() {
                        search_length = 0;
                        search_unit = "";
                        reapply_adapter_with_search_params(list_of_hike_view, adapter);
                    }

                    @Override
                    public void accept() {
                        dialogmanager.length(search_length, search_unit);
                    }
                }, !(search_length > 0));
            }
        });

        // Apply search detection
        View original_home_bar = view.findViewById(R.id.topbarcommonlayout);
        View search_bar = view.findViewById(R.id.searchsection);
        EditText searchbox = view.findViewById(R.id.searchbox);
        searchbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search_name = charSequence.toString().trim();
                reapply_adapter_with_search_params(list_of_hike_view, adapter);
            }
        });

        view.findViewById(R.id.closesearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(searchbox.getText().toString().trim().length() > 0){
                    searchbox.setText("");
                    return;
                }

                search_bar.setVisibility(View.GONE);
                original_home_bar.setVisibility(View.VISIBLE);
                is_search_on = false;
            }
        });

        view.findViewById(R.id.searchicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!is_search_on){
                    search_bar.setVisibility(View.VISIBLE);
                    original_home_bar.setVisibility(View.GONE);

                    searchbox.requestFocus();
                }else{
                    search_bar.setVisibility(View.GONE);
                    original_home_bar.setVisibility(View.VISIBLE);
                }

                is_search_on = !is_search_on;
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String recognizedText = result.get(0); // Get the first result
                Log.d("debug", recognizedText);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (recognizer.speechRecognizer != null) {
            recognizer.speechRecognizer.destroy();
        }
    }
}