package com.example.m_hiker.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.m_hiker.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class SortPanel extends AppCompatDialogFragment {

    public static interface Callback{
        void cancel();
        void agree(int type, int order);
    }

    Callback callback;

    public SortPanel(Callback callback) {
        super();
        this.callback = callback;
    }

    public SortPanel(int contentLayoutId) {
        super(contentLayoutId);
    }


    ChipGroup name;
    ChipGroup order;

    public int type = 0; // 1 = name, 2 = date
    public int sortorder = 0; // 1 = a to Z, 2 = Z to A

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog ret = super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.sorting_panel, null);
        name = view.findViewById(R.id.namesort);
        order = view.findViewById(R.id.sortgroup);


        // Select a default chip for Name group
        switch (type){
            case 1:
                ((Chip)view.findViewById(R.id.namesortchip)).setChecked(true);
                break;
            case 2:
                ((Chip)view.findViewById(R.id.datesort)).setChecked(true);
                break;
            default:
                break;
        }

        // Select a default chip for the Sort group
        switch (sortorder){
            case 1:
                ((Chip)view.findViewById(R.id.aToZ)).setChecked(true);
                break;
            case 2:
                ((Chip)view.findViewById(R.id.ZtoA)).setChecked(true);
                break;
            default:
                break;
        }


        order.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {

                if(checkedIds.isEmpty()){
                    sortorder = 0;
                    return;
                }

                int id = checkedIds.get(0);

                if(id==R.id.aToZ){
                    sortorder = 1;
                }else if(id==R.id.ZtoA){
                    sortorder = 2;
                }
            }
        });

        name.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {

                if(checkedIds.isEmpty()){
                    type = 0;
                    return;
                }

                int id = checkedIds.get(0);

                if(id==R.id.namesortchip){
                    type = 1;
                }else if(id==R.id.datesort){
                    type = 2;
                }
            }
        });

        builder.setView(view).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.agree(type, sortorder);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.cancel();
            }
        });

        Dialog dialog = builder.create();

//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }
}
