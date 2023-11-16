package com.example.m_hiker.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.m_hiker.R;

public class DeleteWarning extends AppCompatDialogFragment {

    public static interface Callback{
        void cancel();
        void agree();
    }

    Callback callback;

    public DeleteWarning(Callback callback) {
        super();
        this.callback = callback;
    }

    public DeleteWarning(int contentLayoutId) {
        super(contentLayoutId);
    }

    public TextView maintext;

    public String bodytext = "";

    public void setText(String text){
        bodytext = text;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog ret = super.onCreateDialog(savedInstanceState);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.permanentlydelete, null);

        maintext = view.findViewById(R.id.maintext);

        if(bodytext.length()>0)
            maintext.setText(bodytext);

        builder.setView(view).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.agree();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            callback.cancel();
            }
        });
        return builder.create();
    }
}
