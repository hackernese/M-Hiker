package com.example.m_hiker.Observation.Edit;

import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.example.m_hiker.CreateObservation.ImageGrid.ImageGridAdapter;
import com.example.m_hiker.CreateObservation.ImageGrid.ImageItem;
import com.example.m_hiker.Dialogs.DeleteWarning;
import com.example.m_hiker.Dialogs.MediaPicker;
import com.example.m_hiker.Dialogs.ToastMessage;
import com.example.m_hiker.MultiChoiceBottomSheet.MultiChoiceSheet;
import com.example.m_hiker.R;
import com.example.m_hiker.components.BottomSheetDate;
import com.example.m_hiker.database.DatabaseMHike;
import com.example.m_hiker.database.Observation;
import com.example.m_hiker.database.ObservationMedia;
import com.example.m_hiker.utils.func;
import com.example.m_hiker.utils.storex;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditObservation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditObservation extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditObservation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditObservation.
     */
    // TODO: Rename and change types and number of parameters
    public static EditObservation newInstance(String param1, String param2) {
        EditObservation fragment = new EditObservation();
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

        db = DatabaseMHike.init(getContext());

    }


    ImageButton backbtn;
    Button savebtn;

    TextInputEditText titlebox;
    TextInputEditText datebox;
    TextInputEditText categorybox;
    TextInputEditText timebox;
    TextInputEditText weatherbox;
    TextInputEditText commentbox;

    Button selectdate;
    Button selectcategory;
    Button selecttime;

    View editmedia;
    View editgeneral;

    GridView imagegrid;

    private long current_date_as_long;

    EditObservationAdapter adapter;

    ArrayList<ImageItem> items;

    ImageButton addnewmediabtn;

    ImageButton deletebtn;

    private Fragment parentFragment = this;

    private Observation object = null;

    ImageButton closeselectedbtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_observation, container, false);

        backbtn = view.findViewById(R.id.backeditob);
        savebtn = view.findViewById(R.id.saveobbtn);
        BottomNavigationView bottommenu = view.findViewById(R.id.bottommenu);


        // Grabbing necessary views
        titlebox = view.findViewById(R.id.titlebox);
        datebox = view.findViewById(R.id.datebox);
        categorybox = view.findViewById(R.id.categorybox);
        timebox = view.findViewById(R.id.timebox);
        weatherbox = view.findViewById(R.id.weatherbox);
        commentbox = view.findViewById(R.id.commentbox);
        editmedia = view.findViewById(R.id.mediasection);
        editgeneral = view.findViewById(R.id.generalsection);
        imagegrid = view.findViewById(R.id.editmedia);
        addnewmediabtn = view.findViewById(R.id.addnewmedia);
        deletebtn = view.findViewById(R.id.deletebtneditob);
        closeselectedbtn = view.findViewById(R.id.exitbtn);
        commentbox.setText("\n\n\n\n\n\n");

        // Grabbing the bundle data
        Bundle bundle = getArguments();
        if(bundle!=null){
            object = ((Observation.ParcelObservation) getArguments().getParcelable("object")).object;

            titlebox.setText(object.title);
            datebox.setText(object.date);
            timebox.setText(object.time);
            weatherbox.setText(object.weather);
            commentbox.setText(object.comments + "\n\n\n\n\n\n");
            categorybox.setText(object.category);
        }


        // Setting the defafult date here
        // Create a Calendar instance and set its time to the timestamp
        current_date_as_long = System.currentTimeMillis();
        Date date = new Date(current_date_as_long);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formatteddate = formatter.format(date);
        datebox.setText(formatteddate);

        // Overlay buttons
        selectdate = view.findViewById(R.id.selectdate);
        selectcategory = view.findViewById(R.id.selectcat);
        selecttime = view.findViewById(R.id.selecttime);


        // Creating a grid adapter
        items = new ArrayList<>();

        // Rendering all existing medias
        if(object!=null) {
            object.getmedias().forEach(o -> {
                ImageItem item = new ImageItem(o.toUri());
                item.object = o;
                items.add(item);
            });
        }

        // Capture the long hold of the user to know when user is trying to select multiple media files

        Runnable closeSelection = new Runnable() {
            @Override
            public void run() {
                closeselectedbtn.setVisibility(View.GONE);
                backbtn.setVisibility(View.VISIBLE);
                savebtn.setVisibility(View.VISIBLE);
                deletebtn.setVisibility(View.GONE);

                adapter.selected = false;

                adapter.images.forEach(img -> {
                    img.setIsAdd(false);
                });

                adapter.allviews.forEach(v->{
                    ImageView empty = v.findViewById(R.id.checkempty);
                    ImageView full = v.findViewById(R.id.checkfullimage);
                    empty.setVisibility(View.GONE);
                    full.setVisibility(View.GONE);
                });
            }
        };

        closeselectedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeSelection.run();
            }
        });
        adapter = new EditObservationAdapter(getContext(), items, new EditObservationAdapter.Callback() {
            @Override
            public void start(View item, ImageItem item2) {

                closeselectedbtn.setVisibility(View.VISIBLE);
                backbtn.setVisibility(View.INVISIBLE);
                savebtn.setVisibility(View.GONE);
                deletebtn.setVisibility(View.VISIBLE);

                adapter.allviews.forEach(v->{
                    ImageView empty = v.findViewById(R.id.checkempty);
                    empty.setVisibility(View.VISIBLE);
                });

                item2.setIsAdd(true);
                item.setVisibility(View.VISIBLE);
            }

            @Override
            public void end() {
                closeSelection.run();
            }
        });
        imagegrid.setAdapter(adapter);

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteWarning dialog = new DeleteWarning(new DeleteWarning.Callback() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void agree() {
                        // Deleting out of the database
                        adapter.images.stream().filter(o->o.isadded()).forEach(obj->{
                            obj.object.delete();
                        });

                        // Make sure that all items are unselected when this happens
                        closeSelection.run();

                        // Remove out of the UI
                        adapter.images.removeIf(o->o.isadded());
                        imagegrid.setAdapter(adapter);
                        ToastMessage.success(view, "Successfully deleted selected items");
                    }
                });

                dialog.show(getActivity().getSupportFragmentManager(), "Warning");
            }
        });

        // Save action
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titlebox.getText().toString().trim();
                String date = datebox.getText().toString().trim();
                String cat = categorybox.getText().toString().trim();
                String time = timebox.getText().toString().trim();
                String weather = weatherbox.getText().toString().trim();
                String comment = commentbox.getText().toString().trim();
                                View errorview =
                        title.length()==0 ? titlebox :
                        time.length()==0 ? timebox :
                        date.length()==0 ? datebox : null;

                // Checking if necessary field is empty
                if(errorview != null){
                    ((TextInputEditText)errorview).setError("This field cannot be empty");
                    bottommenu.setSelectedItemId(0);
                    ToastMessage.error(view, "Please fill in the necessary fields.");

                    return;
                }

                cat = cat.length()==0 ? "None" : cat;


                HashMap<String, String> params = new HashMap<>();
                params.put("category", cat);
                params.put("weather", weather);
                params.put("title", title);
                params.put("time", time);
                params.put("date", date);
                params.put("comments", comment);
                db.update(object, params);

                ToastMessage.success(view, "Successfully updated information");

                object.is_new_change = true;
                object.category = cat;
                object.title = title;
                object.weather = weather;
                object.comments = comment;
                object.date = date;
                object.time = time;

            }
        });

        // When new files are being picked, they will show here
        MediaPicker picker = new MediaPicker(this, o->{

            List<Uri> selected = ((List<Uri>)o);

            if (!selected.isEmpty()) {
                // When users has selected media
                for(Uri uri : selected) {

                    InputStream inputStream;
                    try{
                        inputStream = getContext().getContentResolver().openInputStream(uri);
                    }catch (Exception e){
                        e.printStackTrace();
                        return;
                    }

                    String extension = getContext().getContentResolver().getType(uri);
                    String[] splitted = uri.toString().split("/");
                    String filename = splitted[splitted.length - 1] +
                            ( extension.startsWith("image") ? ".jpg" : ".mp4" );

                    if(func.copyInputStream(inputStream, filename)){
                        Log.d("debug", "Successfully cached file " + filename);
                    }else{
                        Log.d("debug", "Failed to cache file " + filename);
                    }

                    String fullpath = storex.folder + File.separator + filename;
                    ImageItem item = new ImageItem(Uri.fromFile(new File(fullpath)));
                    item.path = fullpath;
                    items.add(item);

                    ObservationMedia media = new ObservationMedia(object.id, fullpath, false);
                    db.insert(media);

                    item.object = media;
                }

                Log.d("debug", adapter.images.size() + "");
                adapter.images = items;
                imagegrid.setAdapter(adapter);

                ToastMessage.success(view, "Successfully added new media files");

                object.is_new_change = true;
            }

        });


        // Setting click listeners
        addnewmediabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.show();
            }
        });
        selectcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.example.m_hiker.MultiChoiceBottomSheet.MultiChoiceSheet dialog = new MultiChoiceSheet(view, getContext(), "Category");

                dialog.option("None", object.category.length() <= 0 ? true : false)
                        .option("Animal", object.category.equals("Animal"))
                        .option("Landscape", object.category.equals("Landscape"))
                        .option("Weather", object.category.equals("Weather"))
                        .option("Discovery", object.category.equals("Discovery"));

                dialog.show(new MultiChoiceSheet.Callback() {
                    @Override
                    public void onchange(String key, boolean value) {
                        categorybox.setText(key);
                    }
                });
            }
        });

        selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BottomSheetDate(view, getContext()).setcallback(new BottomSheetDate.Callback() {
                    @Override
                    public void onchange(String date, long fdate) {
                        Log.d("debug", "" + fdate);
                        current_date_as_long = fdate;
                        datebox.setText(date);
//                        Log.d("debug", date + " " + fdate);
                    }
                }).show(current_date_as_long);
            }
        });
        selecttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Popping up a time picker
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                        // When the user selects a new hour and minute, it will call this
                        timebox.setText(hour + ":" + minute);

                    }
                };

                // Extracting the current time
                Calendar inst =  Calendar.getInstance();

                TimePickerDialog dialog = new TimePickerDialog(getContext(),
                        onTimeSetListener, inst.get(Calendar.HOUR_OF_DAY), inst.get(Calendar.MINUTE), true);
                dialog.show();
            }
        });

        bottommenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if(id==R.id.generalitemob){
                    editgeneral.setVisibility(View.VISIBLE);
                    editmedia.setVisibility(View.GONE);
                }else{
                    editgeneral.setVisibility(View.GONE);
                    editmedia.setVisibility(View.VISIBLE);
                }

                return true;
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}