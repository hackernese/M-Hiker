package com.example.m_hiker.components.MultiChoiceDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.m_hiker.MultiChoiceBottomSheet.MultiChoiceSheet;
import com.example.m_hiker.R;
import com.example.m_hiker.components.HikesCard.HikeCardViewHolder;
import android.view.View;
import com.example.m_hiker.components.MultiChoiceDialog.Choice;

public class MultiChoiceSheetAdapter extends  RecyclerView.Adapter<Choice>{


    View inflated_view;
    Context context;

    List<Integer> items;

    public static class Option{
        public String key;
        public boolean value;
    }

    List<Option> options = new ArrayList<>();
    List<Choice> choices = new ArrayList<>();

    MultiChoiceSheet.Callback callback;

    public MultiChoiceSheetAdapter(Context context, List<Option> items, MultiChoiceSheet.Callback callback) {
        super();
        this.context = context;
        this.options = items;
        this.callback = callback;
    }

    @NonNull
    @Override
    public Choice onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflated_view = LayoutInflater.from(context).inflate(R.layout.choice, parent, false);
        return new Choice(inflated_view);
    }

    @Override
    public void onBindViewHolder(@NonNull Choice holder, int position) {

        choices.add(holder);
        holder.setState(options.get(position).value);
        holder.text.setText(options.get(position).key);
        holder.onclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choices.forEach(choice ->{
                    choice.setState(choice==holder);
                    callback.onchange(choice.text.getText().toString().trim(), choice==holder);
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }
}
