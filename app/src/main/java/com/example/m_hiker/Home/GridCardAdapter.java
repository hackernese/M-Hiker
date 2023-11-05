package com.example.m_hiker.Home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.example.m_hiker.Dialogs.DeleteWarning;
import com.example.m_hiker.Dialogs.ToastMessage;
import com.example.m_hiker.Home.Cards.Common;
import com.example.m_hiker.Home.Cards.ListCard;
import com.example.m_hiker.Home.HikesCard.HikeCardAdapter;
import com.example.m_hiker.R;
import com.example.m_hiker.database.DatabaseMHike;
import com.example.m_hiker.database.Hikes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class GridCardAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    public HikeCardAdapter parentAdapter;
    public LayoutInflater inflater;
    public Context context;
    public ArrayList<Hikes> items;


    public View openbtn;
    public View deletebtn;
    public View editbtn;

    public View topsearchbar;
    public View topbar;
    public View selectbar;
    public FloatingActionButton Fab;
    public BottomNavigationView bottommenu;
    public View longholdmenu;
    public TextView itemselected;
    public View selectallpanel;
    public Button selectallbutton;
    public ImageView selecticon;
    public ImageButton closebtn;
    public Animation slideup;

    public DatabaseMHike db;

    View parentView;

    private void checkSelect(long count){
        itemselected.setText(count + " items selected");

        boolean is_selectall = count == allcards.size();

        selecticon.setImageResource(is_selectall ? R.drawable.select_check_box_fill0_wght400_grad0_opsz24
                : R.drawable.baseline_crop_square_24
        );

        if(count == 0)
            closebtn.callOnClick();
    }

    public GridCardAdapter(Context context, ArrayList<Hikes> items, View parentView, FragmentManager manager){
        this.items = items;
        this.context = context;
        this.allcards = new ArrayList<>();
        this.parentView = parentView;

        // Grabbing database instance
        db = DatabaseMHike.init(context);

        // Grabbing elements from parent view first
        editbtn = parentView.findViewById(R.id.editoption);
        openbtn = parentView.findViewById(R.id.opensection);
        deletebtn = parentView.findViewById(R.id.deletesection);
        topsearchbar = parentView.findViewById(R.id.searchsection);
        topbar = parentView.findViewById(R.id.topbarcommonlayout);
        selectbar = parentView.findViewById(R.id.selectallpanel);
        Fab = (FloatingActionButton)parentView.findViewById(R.id.addhikebtn);
        bottommenu = parentView.findViewById(R.id.bottomNavigationViewHome);
        longholdmenu = parentView.findViewById(R.id.bottomnavigationLonghold);
        itemselected = parentView.findViewById(R.id.itemstext);
        selectallpanel = parentView.findViewById(R.id.selectallpanel);
        selectallbutton = parentView.findViewById(R.id.selectall);
        selecticon = parentView.findViewById(R.id.selecticon);

        // Load necessary animation for reuse
        slideup= AnimationUtils.loadAnimation(context, R.anim.slide_up_anime);

        selectallbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                allcards.forEach((c)->{
                    c.select();
                });

                long count = allcards.stream().filter(obj -> obj.is_selected).count();
                checkSelect(count);
            }
        });
        parentView.findViewById(R.id.editbtnhike).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Reading the currently selected object and redirect to its edit page

//                Hikes o = holderlist.stream().filter(obj->obj.isselected).findFirst().get().obj;
//                Hikes.ParcelHike hike = o.getParcelObject(); // Extract the parcel object
//
//                // Passing the object
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("hike", hike);
//
//                // Redirect
//                Navigation.findNavController(parentView).navigate(R.id.action_homepage_to_editHike, bundle);
            }
        });
        parentView.findViewById(R.id.openbuttonhike).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate_to_hike(holderlist.stream().filter(obj->obj.isselected).findFirst().get().position);
            }
        });
        parentView.findViewById(R.id.deletebtnhike).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DeleteWarning dialog = new DeleteWarning(new DeleteWarning.Callback() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void agree() {
//                        holderlist.stream().filter(obj->obj.isselected).forEach(obj ->{
//                            obj.obj.delete();
//                        });
//                        callback.onchange();
                        ToastMessage.success(parentView, "Successfully deleted selected items");
                    }
                });
                dialog.show(manager, "delete hike");
            }
        });

        // CLose button for selected items
        closebtn = parentView.findViewById(R.id.closeselect);
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectmode = false;
                allcards.forEach((c)->{
                    c.stopselect();
                });
                selectbar.setVisibility(View.GONE);
                topsearchbar.setVisibility(View.GONE);
                topbar.setVisibility(View.VISIBLE);
                Fab.setVisibility(View.VISIBLE);
                bottommenu.setVisibility(View.VISIBLE);
                longholdmenu.setVisibility(View.GONE);
                bottommenu.startAnimation(slideup);
            }
        });
    }

    private int state = 1;// 1 = normal, 2 = grid, 3 = huge grid
    private boolean selectmode = false;

    public ArrayList<Common> allcards;
    public void setnewitems(ArrayList<Hikes> hikes){
        this.items = hikes;
        this.notifyDataSetChanged();
    }

    private void navigate_to_hike(Hikes obj){
        Bundle bundle = new Bundle();

        bundle.putString("name", obj.name);
        bundle.putInt("id", obj.id);

        try{
            Navigation.findNavController(parentView).navigate(R.id.action_homepage_to_viewHike, bundle);
        }catch (Exception e){
            Log.e("e", e.toString());
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Hikes item = items.get(i);
        Common card = state==1 ? new ListCard(item, this) :
                state==2 ?  new ListCard(item, this)  :
                        new ListCard(item, this);

        // Setting callback for each view
        card.setCallback(new Common.Callback() {
            @Override
            public void OnLongClick(GridCardAdapter parent, Common child) {

                if(selectmode)
                    return;

                selectmode = true;

                allcards.forEach((c)->{
                    c.unselect();
                });
                child.select();

                selectbar.setVisibility(View.VISIBLE);
                topsearchbar.setVisibility(View.GONE);
                topbar.setVisibility(View.GONE);
                Fab.setVisibility(View.GONE);
                bottommenu.setVisibility(View.GONE);
                longholdmenu.setVisibility(View.VISIBLE);
                longholdmenu.startAnimation(slideup);

                long count = allcards.stream().filter(obj -> obj.is_selected).count();
                checkSelect(count);

            }

            @Override
            public void OnClick(GridCardAdapter parent, Common child) {

                if(selectmode){
                    if(child.is_selected)
                        child.unselect();
                    else
                        child.select();

                    long count = allcards.stream().filter(obj -> obj.is_selected).count();
                    checkSelect(count);
                    return;
                }

                navigate_to_hike(child.item);
            }

            @Override
            public void OnLove(GridCardAdapter parent, Common child) {
                HashMap<String, String> values = new HashMap<String, String>();
                values.put("islove", item.islove ? "0" : "1");

                int affected = db.update(item, values);
                Log.d("debug", "Updated rows : " + affected);

                if(affected > 0){
                    // Success
                    item.islove = !item.islove;
                    if(item.islove){
                        card.favorite.setImageResource(R.drawable.favorite);
                    }else{
                        card.favorite.setImageResource(R.drawable.heart);
                    }
                }
            }
        });

        // caching back he views
        if(view==null) {
            view = card.getView();
            allcards.add(card);
        }

        return view;
    }
}
