package com.example.m_hiker.Home;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.m_hiker.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FilterDialog {

    Context context;

    View view;

    String state; // Extra data

    public static class Observer{

        public void lengthcallback(int length, String unit){

        }

        public void locationcallback(String location){

        }

        public void datecallback(String date, long milis){

        }
    };

    Observer callback;

    String currentdate; // Date data
    long currentdate_milis; // Same as above but in the miliseconds format

    public FilterDialog(Context context, View view, Observer callback){
        this.context = context;
        this.view = view;
        this.callback = callback;
    }


    public static class ForceMenuReply{
        public ForceMenuReply(){}

        public void none(){

        }
        public void accept(){

        }
    }
    public final void open_fore_menu(ForceMenuReply reply, boolean isfilered){

        // isfilered checks whether the user has applied filter

        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View dialogview = LayoutInflater.from(context).inflate(
                R.layout.filter_options_bottomdialog,
                (LinearLayout)view.findViewById(R.id.optionscontainer)
        );

        Button applybtn = dialogview.findViewById(R.id.applyfilterbtn);
        Button nonebtn = dialogview.findViewById(R.id.sharebtn);

        if(isfilered){
            applybtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked, 0, 0, 0);
        }else{
            nonebtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked, 0, 0, 0);

        }

        applybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reply.accept();
                dialog.dismiss();
                applybtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked, 0, 0, 0);
                nonebtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        });

        nonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reply.none();
                dialog.dismiss();
            }
        });

        // Setting default value
        dialog.setContentView(dialogview);
        dialog.show();

    }

    public final void date(long date){
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View dialogview = LayoutInflater.from(context).inflate(
                R.layout.filter_date_bottomdialog,
                (LinearLayout)view.findViewById(R.id.datecontainer)
        );

        // Setting default value
        dialog.setContentView(dialogview);
        dialog.show();

        CalendarView timepicker = dialogview.findViewById(R.id.datefiltercalender);
        if(date != 0) {
            timepicker.setDate(date);
        }
        timepicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                // Create a Calendar instance and set the selected date
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Create a SimpleDateFormat object to format the date as "DD/MM/YYYY"
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                currentdate_milis = calendar.getTimeInMillis();

                // Convert the Calendar date to a formatted string
                String formattedDate = dateFormat.format(calendar.getTime());

                currentdate = formattedDate;

            }
        });

        dialogview.findViewById(R.id.closebtndate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialogview.findViewById(R.id.confirmbtndate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                callback.datecallback(currentdate,currentdate_milis);
            }
        });

    }
    public final void length(int length, String unit){
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View dialogview = LayoutInflater.from(context).inflate(
                R.layout.filter_length_bottomdialog,
                (LinearLayout)view.findViewById(R.id.locationdialogcontainer)
        );

        // Setting onClickListener
        EditText lengthbox = dialogview.findViewById(R.id.lengthbox);
        if(lengthbox.getText().toString().trim().length()==0){
            // Empty
            lengthbox.setText("0");
        }
        lengthbox.requestFocus();


        dialogview.findViewById(R.id.increasebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lengthbox.setText("" + ( Integer.parseInt(lengthbox.getText().toString().trim()) + 1 ));
            }
        });

        dialogview.findViewById(R.id.decreasebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int val = (Integer.parseInt(lengthbox.getText().toString().trim()) - 1 );
                if(val < 0){
                    return;
                }

                lengthbox.setText("" + val);

            }
        });

        // Extracting the chips
        Chip kmchip = dialogview.findViewById(R.id.kmchip);
        Chip mchip = dialogview.findViewById(R.id.meterchip);
        Chip milechip = dialogview.findViewById(R.id.milechip);
        state = "km";

        kmchip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kmchip.setCloseIconVisible(true);
                mchip.setCloseIconVisible(false);
                milechip.setCloseIconVisible(false);
                state = "km";
            }
        });

        mchip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kmchip.setCloseIconVisible(false);
                mchip.setCloseIconVisible(true);
                milechip.setCloseIconVisible(false);
                state = "m";
            }
        });

        milechip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kmchip.setCloseIconVisible(false);
                mchip.setCloseIconVisible(false);
                milechip.setCloseIconVisible(true);
                state = "mile";
            }
        });


        // Handling closing
        dialogview.findViewById(R.id.savebtnlength).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

                String temp = lengthbox.getText().toString().trim();

                callback.lengthcallback((temp.length() == 0 ? 0 : Integer.parseInt(temp)), state);
            }
        });

        dialog.setContentView(dialogview);
        dialog.show();

    }

    public final void location(String location){
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View dialogview = LayoutInflater.from(context).inflate(
                R.layout.filter_location_bottomdialog,
                (LinearLayout)view.findViewById(R.id.filterlengthcontainer)
        );
        dialog.setContentView(dialogview);
        dialog.show();

        EditText locationbox = dialogview.findViewById(R.id.locationbox);
        // On click listeners here
        if(location.length() > 0)
            locationbox.setText(location);

        dialogview.findViewById(R.id.clearbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationbox.setText("");
            }
        });

        dialogview.findViewById(R.id.cancelbtnfilterlocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                callback.locationcallback(locationbox.getText().toString().trim());
            }
        });

        locationbox.requestFocus();

    }

}
