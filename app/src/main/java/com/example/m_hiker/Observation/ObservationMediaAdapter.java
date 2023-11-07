package com.example.m_hiker.Observation;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hiker.Dialogs.MediaPlayer.MediaSlider;
import com.example.m_hiker.R;
import com.example.m_hiker.database.ObservationMedia;
import java.util.ArrayList;

public class ObservationMediaAdapter extends RecyclerView.Adapter<ObservationMediaAdapter.Holder> {

    public static class Holder extends RecyclerView.ViewHolder{

        public ImageView image;
        public View videotag;

        Button viewme;

        public Holder(@NonNull View itemView) {
            super(itemView);
            videotag = itemView.findViewById(R.id.videotag);
            image = itemView.findViewById(R.id.thumbnailmedia);
            viewme = itemView.findViewById(R.id.view);
        }
    }

    Context context;
    ArrayList<ObservationMedia> items;
    View inflated_view;
    public ObservationMediaAdapter(Context context, ArrayList<ObservationMedia> items) {
        super();
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflated_view = LayoutInflater.from(context).inflate(R.layout.mediacard, parent, false);
        return new Holder(inflated_view);
    }

    MediaMetadataRetriever media = new MediaMetadataRetriever();

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        ObservationMedia mediafile = items.get(position);

        if(mediafile.path.endsWith(".jpg"))
            holder.image.setImageURI(mediafile.toUri());
        else{
            // Extracting the thumbnail of the video as image
            media.setDataSource(context, mediafile.toUri());
            Bitmap thumbnail = media.getFrameAtTime();
            holder.image.setImageBitmap(thumbnail);
            holder.videotag.setVisibility(View.VISIBLE);
        }

        holder.viewme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MediaSlider(context, items);
            }
        });



    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
