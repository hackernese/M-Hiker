package com.example.m_hiker.Hike.ObservationCard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hiker.Dialogs.DeleteWarning;
import com.example.m_hiker.Dialogs.SocialMedia;
import com.example.m_hiker.Dialogs.ToastMessage;
import com.example.m_hiker.R;
import com.example.m_hiker.database.Observation;
import com.example.m_hiker.database.ObservationMedia;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class  ObservationCardAdapter extends RecyclerView.Adapter<ObservationCardHolder> {

    Context context;
    public List<Observation> observations;
    View inflated_view;

    Fragment activity;
    View parentView;

    Callback callback;

    View emptyob;
    public void setcallback(Callback callback){
        this.callback = callback;
    }

    public ObservationCardAdapter(Context context, List<Observation> observations, Fragment activity, View parentView){
        this.context = context;
        this.observations = observations;
        this.activity = activity;
        this.parentView = parentView;
        emptyob = parentView.findViewById(R.id.emptyob);

    }

    public static interface Callback{
        void delete();
        void share();

    }


    @NonNull
    @Override
    public ObservationCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflated_view = LayoutInflater.from(context).inflate(R.layout.observation_card, parent, false);
        return new ObservationCardHolder(inflated_view);
    }

    ImageView placeholder;

    @Override
    public void onBindViewHolder(@NonNull ObservationCardHolder holder, int position) {

        Observation ob  = observations.get(position);
        holder.setfragmentmanager(activity.getParentFragmentManager());
        holder.setpager(ob, activity);


        // Extracting all medias first
        ArrayList<ObservationMedia> allmedias = ob.getmedias();
        if(allmedias.size() > 0){
            holder.placeholder.setVisibility(View.GONE);
        }


        holder.parentView = parentView;
        holder.id = ob.id;
        holder.category.setText(ob.category);
        holder.title.setText(ob.title);
        holder.context = context;

        // When the user clicks on the card
        holder.self.findViewById(R.id.accessobbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog dialog = new BottomSheetDialog(context);
                View dialogview = LayoutInflater.from(context).inflate(
                        R.layout.hike_ob_bottomsheet,
                        (LinearLayout)view.findViewById(R.id.obcontainer)
                );

                Button delete = dialogview.findViewById(R.id.deletebtn);
                Button share = dialogview.findViewById(R.id.sharebtn);
                Button edit = dialogview.findViewById(R.id.editbtnhike);
                Button viewbtn = dialogview.findViewById(R.id.viewbtn);


                // Parcelable bundle
                Bundle bundle = new Bundle();
                bundle.putParcelable("object", ob.getParcelObject());

                // Delete option
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DeleteWarning dialogdelete = new DeleteWarning(new DeleteWarning.Callback() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void agree() {
                                ob.delete();
                                ToastMessage.success(view, "Successfully deleted observation");
                                callback.delete();
                            }
                        });

                        dialogdelete.show(activity.getParentFragmentManager(), "Warning");
                        dialog.dismiss();
                    }
                });

                // Sharing on social medias
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ArrayList<String> uris = new ArrayList<>();
                        allmedias.forEach(e->{
                            uris.add(e.path);
                        });

                        new SocialMedia(context).share(ob.comments, ob.title, uris);
                        dialog.dismiss();
                    }
                });

                // Viewing the observation
                viewbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Navigation.findNavController(parentView).navigate(R.id.action_viewHike_to_viewObservation, bundle);
                        dialog.dismiss();
                    }
                });

                // Edit the observation
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Navigation.findNavController(parentView).navigate(R.id.action_viewHike_to_editObservation2, bundle);
                        dialog.dismiss();
                    }
                });


                // Setting default value
                dialog.setContentView(dialogview);
                dialog.show();
            }
        });


        // Creating and binding the ViewPager2 inside of the card for swiping
        ArrayList<Fragment> fragments = new ArrayList<>();
        allmedias.forEach(observationMedia -> {
            Log.d("debug", observationMedia.path);
            fragments.add( ( new ObservationThumbnail() ).setmedia(observationMedia));
        });

        Log.d("debug", fragments.size() + " awdwad");
        ObservationThumbnailAdapter adapter = new ObservationThumbnailAdapter(activity, fragments);
        holder.obpager.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {

        int size = observations.size();

        Log.d("debug", size + " here");

        if(size>0)
            emptyob.setVisibility(View.GONE);
        else
            emptyob.setVisibility(View.VISIBLE);

        return size;

    }
}
