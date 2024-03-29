package com.example.m_hiker.CreateObservation.ImageGrid;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.m_hiker.R;

import java.util.ArrayList;
import java.util.List;

public class ImageGridAdapter extends BaseAdapter {

    Context context;
    List<ImageItem> uris;
    List<ImageItem> original_uris;
    LayoutInflater inflater;

    View.OnClickListener launch;

    public List<ImageItem> getItems(){
        return original_uris;
    }

    public void updateItems(ArrayList<ImageItem> items){

        this.uris = items;
        this.original_uris =  new ArrayList<>(items);

//        Log.d("debug", this.uris.size() + "");
//
//        // If the url lists
//        if(!this.uris.isEmpty()){
//            // Readding
//
//            for (ImageItem imageItem : this.uris) {
//
//                if(imageItem.is_add)
//                    this.uris.remove(imageItem);
//
////                Log.d("debug", imageItem.is_add.toString());
//            }
//            this.uris.add((new ImageItem()).setIsAdd(true));
//
//        }

        this.notifyDataSetChanged();

    }

    public ImageGridAdapter(Context context, ArrayList<ImageItem> uris, View.OnClickListener launch){
        this.uris = uris;
        this.original_uris = new ArrayList<>(uris);
        this.context = context;
        this.launch = launch;
    }

    @Override
    public int getCount() {

        int i = uris.size();

        if(i==0)
            return 0;

        return uris.size() + 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(i==uris.size()){



            Log.d("debug", "dadawdawd");
            view = inflater.inflate(R.layout.image_item_add, null);
            view.setOnClickListener(launch);
            return view;
        }
        if (view == null)
            view = inflater.inflate(R.layout.image_item, null);


        ImageView imageview = view.findViewById(R.id.card_picture);

        ImageItem temp = uris.get(i);
        String type = context.getContentResolver().getType(temp.uripath);


        if(type==null){
            // Very likely that this was a captured image
            imageview.setImageURI(temp.uripath);
            return view;
        }

        if(type.startsWith("video"))
        {
            View layout = view.findViewById(R.id.timestamp_label);
            layout.setVisibility(View.VISIBLE);

            // Extracting the thumbnail of the video as image
            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(context, temp.uripath);
            Bitmap thumbnail = media.getFrameAtTime();

            imageview.setImageBitmap(thumbnail);

        }else{
            imageview.setImageURI(temp.uripath);
        }

        return view;
    }
}
