package com.example.m_hiker.CreateHikePage.screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.m_hiker.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LengthAndDifficulty#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LengthAndDifficulty extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LengthAndDifficulty() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LengthAndDifficulty.
     */
    // TODO: Rename and change types and number of parameters
    public static LengthAndDifficulty newInstance(String param1, String param2) {
        LengthAndDifficulty fragment = new LengthAndDifficulty();
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

    public LengthAndDifficulty setcallback(HikeRunnable callback){
        this.callback = callback;
        return this;
    }

    private String unit;
    private String difficulty;
    private String description;
    private int companions;
    private int length;
    private boolean switched;

    private void invoke(){
        callback.extra(length,unit,description,switched,companions, difficulty);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_length_and_difficulty, container, false);


        EditText descriptionedit = ((EditText)view.findViewById(R.id.descriptionbox));

        EditText lengthbox = ((EditText)view.findViewById(R.id.length));

        ((EditText)view.findViewById(R.id.unitbox)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                unit = charSequence.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lengthbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int temp = Integer.parseInt(charSequence.toString().trim());
                    if(temp > 0)
                        length = temp;
                    else
                        lengthbox.setText("");
                }catch (NumberFormatException e){
                    lengthbox.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ((EditText)view.findViewById(R.id.difficultybox)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                difficulty = charSequence.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        descriptionedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                description = charSequence.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ((EditText)view.findViewById(R.id.companions)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                companions = Integer.parseInt(charSequence.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        descriptionedit.setText("\n\n\n\n\n\n");

        ((Switch)view.findViewById(R.id.parkswitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switched = b;
            }
        });

        return view;
    }
}