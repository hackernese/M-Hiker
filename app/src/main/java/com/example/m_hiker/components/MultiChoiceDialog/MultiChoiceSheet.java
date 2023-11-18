package com.example.m_hiker.MultiChoiceBottomSheet;

import android.content.Context;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hiker.R;
import com.example.m_hiker.components.MultiChoiceDialog.Choice;
import com.example.m_hiker.components.MultiChoiceDialog.MultiChoiceSheetAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class MultiChoiceSheet{

    Context context;
    View view;

    String title = "Untitled";

    boolean is_media = false; // Set this to true and it will show icons on each Choice
    public MultiChoiceSheet setType(boolean type){
        this.is_media = type;
        return this;
    }

    public MultiChoiceSheet(View view, Context context, String title) {
        this.view = view;
        this.context = context;
        this.title = title;
    }

    MultiChoiceSheetAdapter adapter;

    List<MultiChoiceSheetAdapter.Option> options = new ArrayList<>();

    public MultiChoiceSheet option(String key){
        return this.option(key, false);
    }
    public MultiChoiceSheet option(String key, boolean value){

        MultiChoiceSheetAdapter.Option op = new MultiChoiceSheetAdapter.Option();
        op.key = key;
        op.value = value;
        options.add(op);
        return this;
    }
    public MultiChoiceSheet option(String key, int imageid){
        MultiChoiceSheetAdapter.Option op = new MultiChoiceSheetAdapter.Option();
        op.key = key;
        op.value = false;
        op.imageid = imageid;
        options.add(op);
        return this;
    }



    public static interface Callback{
        void onchange(String key, boolean value);
    }

    public BottomSheetDialog dialog;

    public void show(Callback callback){
        dialog = new BottomSheetDialog(context);
        View dialogview = LayoutInflater.from(context).inflate(
                R.layout.bottomsheet_multichoice,
                (LinearLayout)view.findViewById(R.id.containeritems)
        );



        ( (TextView)dialogview.findViewById(R.id.titlesheet) ).setText(title);

        // Setting default value
        dialog.setContentView(dialogview);
        dialog.show();

        RecyclerView recyler;


        recyler = dialogview.findViewById(R.id.listitems);
        recyler.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MultiChoiceSheetAdapter(context, options, callback);

        adapter.is_media = is_media;
        recyler.setAdapter(adapter);

    }

}
