package com.example.m_hiker.Hike;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.m_hiker.CreateHikePage.LocationList.LocationAdapter;
import com.example.m_hiker.CreateHikePage.LocationList.LocationItem;
import com.example.m_hiker.Dialogs.ToastMessage;
import com.example.m_hiker.MultiChoiceBottomSheet.MultiChoiceSheet;
import com.example.m_hiker.R;
import com.example.m_hiker.database.DatabaseMHike;
import com.example.m_hiker.database.Hikes;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditHike#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditHike extends Fragment implements OnMapReadyCallback {

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

        db = DatabaseMHike.init(getContext());

    }


    // Map section
    private GoogleMap mMap;
    // Used for extracting local location later
    FusedLocationProviderClient locationclient;

    String exist_location = null;
    double exist_long = 0;
    double exist_lat = 0;
    private boolean ignore_show_location_list = false;

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {


        mMap = googleMap;

        if(exist_location==null)
            return;

        ignore_show_location_list = true;
        editbox.setText(exist_location);

        clearbuttonedit.setVisibility(View.VISIBLE);
        voiceedit.setVisibility(View.INVISIBLE);
        listcardview.setVisibility(View.INVISIBLE);

        setlocation(exist_lat, exist_long);
    }
    @Override
    public void onResume() {
        super.onResume();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googlemapsection);
        mapFragment.getMapAsync(this);

        // Extracting local location
        locationclient = LocationServices.getFusedLocationProviderClient(getContext());
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
    Switch parking;
    TextInputEditText companion;
    TextInputEditText description;


    ActivityResultLauncher<Intent> launcher;
    EditText editbox;
    ImageButton voiceedit;
    ImageButton clearbuttonedit;
    View listcardview;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String ret = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);

                    editbox.setText(ret);
                }
            }
        });
    }

    private void setlocation(double lat, double long_){
        mMap.clear();

        LatLng location_ = new LatLng(lat, long_);
        CameraUpdate update =  CameraUpdateFactory.newLatLngZoom(location_, 12.0f);
        mMap.moveCamera(update);

        // You can also add a marker at a specific location:
        MarkerOptions markerOptions = new MarkerOptions()
                .position(location_)
                .title("Curent location")
                .snippet("Long:" + long_ + " Lat:" + lat);
        mMap.addMarker(markerOptions);
        // You can customize other map settings as well, like zoom level, map type, etc.
        mMap.setMapType(mMap.MAP_TYPE_NORMAL);
        mMap.setMinZoomPreference(10.0f);

        // You can also enable user interactions like zooming and panning:
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        mMap.animateCamera(update);
    }
    View locationlistview;

    DatabaseMHike db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_hike, container, false);

        // Common variables
        Geocoder geocoder = new Geocoder(getContext());

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

        // Grabbing the recycler view inside of the Location page
        RecyclerView localist = view.findViewById(R.id.locationlistedit); // The recyclerview
        locationlistview = view.findViewById(R.id.locationlistcardedit); // The card containing the recylerview
        ArrayList<LocationItem> l = new ArrayList<>();

        l.add(new LocationItem());

        // LISTING LOCATION ( ADAPTER HERE )
        LocationAdapter adapter = new LocationAdapter(getContext(), l).setcallback(new LocationAdapter.Callback() {
            @Override
            public void call(String name, double lat, double long_) {

                Log.d("debug", "DAWDAWD");

                locationlistview.setVisibility(View.INVISIBLE);

                if(name.trim().length()==0){
                    // Grab the current virtual address ( Does not exist / recognize )
                    exist_location = editbox.getText().toString().trim();
                    exist_lat = 0;
                    exist_long = 0;
                    return;
                }
                ignore_show_location_list = true;
                editbox.setText(name);
                exist_location = name.trim();
                exist_lat = lat;
                exist_long = long_;
                setlocation(lat, long_);
            }
        });
        localist.setLayoutManager(new LinearLayoutManager(getContext()));
        localist.setAdapter(adapter);

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
        parking.setChecked(hike.parking);
        companion.setText(hike.companion + "");
        description.setText(hike.description.trim() + defaultline);

        // Setting default location
        exist_location = hike.location;
        exist_lat = hike.lat;
        exist_long = hike.longtitude;

        editbox = view.findViewById(R.id.searchlocationedit);
        voiceedit = view.findViewById(R.id.voiceedit);
        clearbuttonedit = view.findViewById(R.id.clearbuttonedit);
        listcardview = view.findViewById(R.id.locationlistcardedit);

        clearbuttonedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editbox.setText("");
            }
        });

        voiceedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent speech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speech.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start speaking");
                launcher.launch(speech);
            }
        });

        editbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(ignore_show_location_list){
                    ignore_show_location_list = false;
                    return;
                }

                String data = charSequence.toString();

                if(data.trim().length()==0){
                    clearbuttonedit.setVisibility(View.INVISIBLE);
                    voiceedit.setVisibility(View.VISIBLE);
                    listcardview.setVisibility(View.INVISIBLE);
                }else{
                    clearbuttonedit.setVisibility(View.VISIBLE);
                    voiceedit.setVisibility(View.INVISIBLE);
                    listcardview.setVisibility(View.VISIBLE);
                }

                try {
                    List<Address> addresses = geocoder.getFromLocationName(data, 20);
                    if(addresses==null)return;

                    adapter.setlocationlist(addresses);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle exception
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String new_name = name.getText().toString().trim();
                String new_date = date.getText().toString().trim();
                String new_length = length.getText().toString().trim();
                String new_unit = unit.getText().toString().trim();
                String new_level = difficulty.getText().toString().trim();
                boolean new_park = parking.isChecked();
                String new_comp = companion.getText().toString().trim();
                String new_describe = description.getText().toString().trim();

                TextInputEditText errorinput =
                        new_name.length() == 0 ? name :
                        new_date.length() == 0 ? date :
                        new_length.length() == 0 ? length :
                        new_unit.length() == 0 ? unit :
                        new_level.length() == 0 ? difficulty :
                        new_comp.length() == 0 ? companion :
                        new_describe.length() == 0 ? description : null;

                if(errorinput!=null){
                    // Error detected
                    errorinput.setError("This field cannot be empty");
                    return;
                }

                HashMap<String, String> params_str = new HashMap<>();
                HashMap<String, Integer> params_int = new HashMap<>();


                params_str.put("name", new_name);
                params_str.put("date", new_date);
                params_str.put("units", new_unit);
                params_str.put("level", new_level);
                params_str.put("location", exist_location);
                params_str.put("description", new_describe);

                params_str.put("lat", exist_lat + "");
                params_str.put("long", exist_long + "");
                params_str.put("parking", new_park ? "1" : "0" );
                params_str.put("length", new_length + "");
                params_str.put("companions", new_comp + "");


                db.update(hike, params_str);

                ToastMessage.success(view, "Successfully updated information");

            }
        });


        // Picker for units and Difficulty
        view.findViewById(R.id.pickunit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.example.m_hiker.MultiChoiceBottomSheet.MultiChoiceSheet sheet = new MultiChoiceSheet(view, getActivity(), "Units");
                sheet
                        .option("Kilometers")
                        .option("Miles")
                        .option("Meters");
                sheet.show(new MultiChoiceSheet.Callback() {
                    @Override
                    public void onchange(String key, boolean value) {
                        String unittext = key.equals("Kilometers") ? "km" :
                                key.equals("Miles") ? "mile" : "m";

                        if(value){
                           unit.setText(unittext);
                        }

                    }
                });
            }
        });

        view.findViewById(R.id.pickdifficulty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.example.m_hiker.MultiChoiceBottomSheet.MultiChoiceSheet sheet = new MultiChoiceSheet(view, getActivity(), "Units");
                sheet
                        .option("Easy")
                        .option("Intermediate")
                        .option("Hard");
                sheet.show(new MultiChoiceSheet.Callback() {
                    @Override
                    public void onchange(String key, boolean value) {
                        if(value){
                            difficulty.setText(key.trim().toLowerCase());
                        }

                    }
                });
            }
        });

        return view;
    }
}