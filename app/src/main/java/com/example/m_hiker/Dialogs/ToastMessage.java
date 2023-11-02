package com.example.m_hiker.Dialogs;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.m_hiker.R;

public class ToastMessage extends AppCompatActivity {

    public static Context context;
    public static LayoutInflater inflater;

    public static void success(View view, String text){
        View layout = inflater.inflate(R.layout.successtoast, view.findViewById(R.id.toastokroot));

        TextView textv = layout.findViewById(R.id.successlabel);
        textv.setText(text.trim());

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0,300);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        toast.show();
    }

    public static  void error(View view, String text, int yoffset){
        View layout = inflater.inflate(R.layout.toasterror, view.findViewById(R.id.toasterrorroot));

        TextView textv = layout.findViewById(R.id.errorlabel);
        textv.setText(text.trim());

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0,yoffset);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        toast.show();
    }

    public static void error(View view, String text){
        error(view, text, 300);
    }

}
