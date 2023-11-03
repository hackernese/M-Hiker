package com.example.m_hiker.Observation.Edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.example.m_hiker.CreateObservation.ImageGrid.ImageItem;
import com.example.m_hiker.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EditObservationAdapter extends BaseAdapter {


    public List<ImageItem> images;
    List<ImageItem> original_uris;

    public static  interface Callback{
        void start(View item, ImageItem item2);
        void end();
    }

    Callback callback;

    public EditObservationAdapter(Context context, ArrayList<ImageItem> images, Callback callback){
        this.images = images;
        this.context = context;
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    LayoutInflater inflater;
    Context context;

    public boolean selected;

//    public void updateItems(ArrayList<ImageItem> items){
//        this.images = items;
//        this.original_uris =  new ArrayList<>(items);
//        if(!this.images.isEmpty())
//            this.images.add((new ImageItem()).setIsAdd(true));
//    }

    public List<View> allviews = new ArrayList<>();

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.image_item, null);

        allviews.add(view);

        ImageItem item = images.get(i);
        ImageView imageview = view.findViewById(R.id.card_picture);
        String imagepath = images.get(i).uripath.getPath();
        Uri uri = Uri.fromFile(new File(imagepath));

        Button hover = view.findViewById(R.id.hovereffect);
        hover.setVisibility(View.VISIBLE);

        ImageView checked = view.findViewById(R.id.checkfullimage);
        ImageView empty = view.findViewById(R.id.checkempty);


        // When the user has already long-clicked one item, everything will be in selected mode
        // and each click will select one item, hence the following callback
        hover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                callback.before();
//                selected = false;
                item.setIsAdd(!item.isadded());
                if(item.isadded())
                    checked.setVisibility(View.VISIBLE);
                else
                    checked.setVisibility(View.GONE);

                if(images.stream().filter(o->o.isadded()).count() ==0 ){

                    // Users has stopped selecting stuffs
                    selected = false;
                    callback.end();
                }

//                empty.setVisibility(View.GONE);
//                callback.after();
            }
        });

        hover.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(selected){
                    return true;
                }

                callback.start(checked, item);
                selected = true;
                return true;
            }
        });


        if(imagepath.endsWith("mp4"))
        {
            // Video file

            View layout = view.findViewById(R.id.timestamp_label);
            layout.setVisibility(View.VISIBLE);

            // Extracting the thumbnail of the video as image
            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(context, uri);
            Bitmap thumbnail = media.getFrameAtTime();
            imageview.setImageBitmap(thumbnail);

        }else{

            imageview.setImageURI(uri);
        }

        return view;
    }
}
