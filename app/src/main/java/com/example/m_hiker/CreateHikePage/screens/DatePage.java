package com.example.m_hiker.CreateHikePage.screens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.m_hiker.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DatePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationPage.
     */
    // TODO: Rename and change types and number of parameters
    public static DatePage newInstance(String param1, String param2) {
        DatePage fragment = new DatePage();
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
    HikeRunnable callback;

    public DatePage setcallback(HikeRunnable callback){
        this.callback = callback;
        return this;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_page, container, false);

        CalendarView datepicker = (CalendarView)view.findViewById(R.id.calanderitem);

        Date selectedDate = new Date(datepicker.getDate());

        // Format the Date as a string
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        String dateString = dateFormat.format(selectedDate);
        callback.date(dateString);

        datepicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {
                callback.date(d + "/" + m + "/" + y);
            }
        });

        return view;
    }
}