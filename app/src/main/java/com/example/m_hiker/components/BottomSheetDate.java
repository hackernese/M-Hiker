package com.example.m_hiker.components;

import android.content.Context;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hiker.MultiChoiceBottomSheet.MultiChoiceSheet;
import com.example.m_hiker.R;
import com.example.m_hiker.components.MultiChoiceDialog.MultiChoiceSheetAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BottomSheetDate {

    Context context;
    View view;

    public BottomSheetDate(View view, Context context) {
        this.view = view;
        this.context = context;
    }

    Callback callback;

    public BottomSheetDate setcallback(Callback callback){
        this.callback = callback;
        return this;
    }

    public static interface Callback{
        void onchange(String date, long fdate);
    }

    public void show(long default_date){
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View dialogview = LayoutInflater.from(context).inflate(
                R.layout.datebottomsheet,
                (LinearLayout)view.findViewById(R.id.datecontainer)
        );


        CalendarView calander = dialogview.findViewById(R.id.calanderbottomsheet);
        calander.setDate(default_date);
        calander.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(y, m, d);

                callback.onchange(d + "/" + m + "/" + y, calendar.getTimeInMillis());
            }
        });

        // Setting default value
        dialog.setContentView(dialogview);
        dialog.show();
    }
}
