package com.example.m_hiker.CreateObservation.screens;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.example.m_hiker.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateObservationDate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateObservationDate extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateObservationDate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateObservationDate.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateObservationDate newInstance(String param1, String param2) {
        CreateObservationDate fragment = new CreateObservationDate();
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
        View view = inflater.inflate(R.layout.fragment_create_observation_date, container, false);

        TextInputEditText timedatebox = (TextInputEditText)view.findViewById(R.id.timebox);
        timedatebox.setFocusable(false);

        view.findViewById(R.id.timebox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Popping up a time picker
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                        // When the user selects a new hour and minute, it will call this

                    }
                };

                // Extracting the current time
                Calendar inst =  Calendar.getInstance();

                TimePickerDialog dialog = new TimePickerDialog(getContext(),
                        onTimeSetListener, inst.get(Calendar.HOUR_OF_DAY), inst.get(Calendar.MINUTE), true);
                dialog.show();

            }
        });

        return view;
    }
}