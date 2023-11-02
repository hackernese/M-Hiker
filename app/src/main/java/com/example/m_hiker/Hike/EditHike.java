package com.example.m_hiker.Hike;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.m_hiker.Dialogs.ToastMessage;
import com.example.m_hiker.R;
import com.example.m_hiker.database.Hikes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditHike#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditHike extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditHike() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditHike.
     */
    // TODO: Rename and change types and number of parameters
    public static EditHike newInstance(String param1, String param2) {
        EditHike fragment = new EditHike();
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

    View optional;
    View location;
    View general;
    ImageButton backbtn;
    ImageButton savebtn;
    TextInputEditText name;
    TextInputEditText date;
    TextInputEditText length;
    TextInputEditText unit;
    TextInputEditText difficulty;
    TextInputEditText parking;
    TextInputEditText companion;
    TextInputEditText description;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_hike, container, false);

        backbtn = view.findViewById(R.id.backmain);
        savebtn = view.findViewById(R.id.savebtn);
        name = view.findViewById(R.id.name);
        date = view.findViewById(R.id.date);
        length = view.findViewById(R.id.length);
        unit = view.findViewById(R.id.unit);
        difficulty = view.findViewById(R.id.difficultybox);
        parking = view.findViewById(R.id.parking);
        companion = view.findViewById(R.id.companion);
        description = view.findViewById(R.id.description);
        optional = view.findViewById(R.id.optionalsection);
        general = view.findViewById(R.id.generalsection);
        location = view.findViewById(R.id.locationsection);
        BottomNavigationView navigator = view.findViewById(R.id.navigatoredit);

        String defaultline = "\n\n\n\n\n\n\n\n\n\n\n\n";
        description.setText(defaultline);

        navigator.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                optional.setVisibility(View.GONE);
                general.setVisibility(View.GONE);
                location.setVisibility(View.GONE);

                if(id==R.id.optionalitem){
                    optional.setVisibility(View.VISIBLE);
                }else if(id==R.id.generalitem){
                    general.setVisibility(View.VISIBLE);
                }else{
                    location.setVisibility(View.VISIBLE);
                }

                return true;
            }
        });

        // Setting default stuffs
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        // Grabbing parcel data
        Bundle bundle = getArguments();
        if(bundle==null)
            return view;
        Hikes hike =  ((Hikes.ParcelHike)bundle.getParcelable("hike")).object;

        name.setText(hike.name.trim());
        date.setText(hike.date.trim());
        length.setText(hike.length + "");
        unit.setText(hike.units);
        difficulty.setText(hike.level);
        parking.setText(hike.parking ? "Yes" : "No");
        companion.setText(hike.companion + "");
        description.setText(hike.description.trim() + defaultline);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String new_name = name.getText().toString().trim();
                String new_date = date.getText().toString().trim();
                String new_length = length.getText().toString().trim();
                String new_unit = unit.getText().toString().trim();
                String new_level = difficulty.getText().toString().trim();
                String new_park = parking.getText().toString().trim();
                String new_comp = companion.getText().toString().trim();
                String new_describe = description.getText().toString().trim();

                TextInputEditText errorinput =
                        new_name.length() == 0 ? name :
                        new_date.length() == 0 ? date :
                        new_length.length() == 0 ? length :
                        new_unit.length() == 0 ? unit :
                        new_level.length() == 0 ? difficulty :
                        new_park.length() == 0 ? parking :
                        new_comp.length() == 0 ? companion :
                        new_describe.length() == 0 ? description : null;

                if(errorinput!=null){
                    // Error detected
                    errorinput.setError("This field cannot be empty");
                    return;
                }
            }
        });


        return view;
    }
}