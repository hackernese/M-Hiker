package com.example.m_hiker.components.HikesCard;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hiker.Dialogs.DeleteWarning;
import com.example.m_hiker.Dialogs.ToastMessage;
import com.example.m_hiker.Home.HomeDirections;
import com.example.m_hiker.R;
import com.example.m_hiker.database.DatabaseMHike;
import com.example.m_hiker.database.Hikes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class HikeCardAdapter extends RecyclerView.Adapter<HikeCardViewHolder>{

    Context context;
    public ArrayList<Hikes> items;
    DatabaseMHike db;

    View parentView;

    public HikeCardAdapter(Context context, ArrayList<Hikes> items, View parentView){
        this.context = context;
        this.items = items;
        db = DatabaseMHike.init(context);
        this.parentView = parentView;
    }

    public void setnewitems(ArrayList<Hikes> items){
        this.items = items;
    }

    View inflated_view;
    private List<Hikes> totalselected = new ArrayList<>();


    @NonNull
    @Override
    public HikeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflated_view = LayoutInflater.from(context).inflate(R.layout.hike_card, parent, false);
        return new HikeCardViewHolder(inflated_view);
    }

    private List<Integer> selected = new ArrayList<>();

    private List<HikeCardViewHolder> holderlist = new ArrayList<>();

    private boolean inselectedmode = false;

    private boolean isselectedall = false;

    View openbtn;
    View deletebtn;
    View editbtn;

    private void showall(){
        openbtn.setVisibility(View.VISIBLE);
        deletebtn.setVisibility(View.VISIBLE);
        editbtn.setVisibility(View.VISIBLE);
    }
    private void hideall(){

    }

    private void checkbiggerthan1select(){

        long itemscount = holderlist.stream().filter(obj -> obj.isselected).count();

        if(itemscount > 1){

            Log.d("debug", itemscount + "");

            // Disasble edit and open
            editbtn.setVisibility(View.GONE);
            openbtn.setVisibility(View.GONE);
        }
        else if(itemscount==0){
            parentView.findViewById(R.id.closeselect).callOnClick();
        }
        else{
            showall();
        }
    }

    FragmentManager parentManager;

    public HikeCardAdapter setManager(FragmentManager manager){
        this.parentManager = manager;
        return this;
    }

    public static interface Callback{
        void onchange();
    }

    Callback callback;

    public HikeCardAdapter setcallback(Callback c){
        this.callback = c;
        return this;
    }

    private void navigate_to_hike(int pos){
        Bundle bundle = new Bundle();

        bundle.putString("name", items.get(pos).name);
        bundle.putInt("id", items.get(pos).id);

        try{
            Navigation.findNavController(inflated_view).navigate(R.id.action_homepage_to_viewHike, bundle);
        }catch (Exception e){
            Log.e("e", e.toString());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HikeCardViewHolder holder, int position) {

        holderlist.add(holder);

        Hikes item = items.get(position);

        holder.position = position;
        holder.location.setText(item.location);
        holder.description.setText(item.description);
        holder.title.setText(item.name);

        // Buttons on the bottom bar when user selects
        editbtn = parentView.findViewById(R.id.editoption);
        openbtn = parentView.findViewById(R.id.opensection);
        deletebtn = parentView.findViewById(R.id.deletesection);

        parentView.findViewById(R.id.editbtnhike).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Reading the currently selected object and redirect to its edit page

                Hikes o = holderlist.stream().filter(obj->obj.isselected).findFirst().get().obj;
                Hikes.ParcelHike hike = o.getParcelObject(); // Extract the parcel object

                // Passing the object
                Bundle bundle = new Bundle();
                bundle.putParcelable("hike", hike);

                // Redirect
                Navigation.findNavController(parentView).navigate(R.id.action_homepage_to_editHike, bundle);
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
                        holderlist.stream().filter(obj->obj.isselected).forEach(obj ->{
                            obj.obj.delete();
                        });
                        callback.onchange();
                        ToastMessage.success(parentView, "Successfully deleted selected items");
                    }
                });
                dialog.show(parentManager, "DDDD");
            }
        });


        // Query the bars
        View topsearchbar = parentView.findViewById(R.id.searchsection);
        View topbar = parentView.findViewById(R.id.topbarcommonlayout);
        View selectbar = parentView.findViewById(R.id.selectallpanel);
        FloatingActionButton Fab = (FloatingActionButton)parentView.findViewById(R.id.addhikebtn);
        BottomNavigationView bottommenu = parentView.findViewById(R.id.bottomNavigationViewHome);
        View longholdmenu = parentView.findViewById(R.id.bottomnavigationLonghold);
        TextView itemselected = parentView.findViewById(R.id.itemstext);
        View selectallpanel = parentView.findViewById(R.id.selectallpanel);
        Button selectallbutton = parentView.findViewById(R.id.selectall);
        ImageView selecticon = parentView.findViewById(R.id.selecticon);

        Animation slideup = AnimationUtils.loadAnimation(context, R.anim.slide_up_anime);

        selectallbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isselectedall = !isselectedall;
                holderlist.forEach((c)->{
                    c.showcheckedmark(isselectedall).showunchecked(!isselectedall);
                });
                selecticon.setImageResource(isselectedall ? R.drawable.select_check_box_fill0_wght400_grad0_opsz24
                        : R.drawable.baseline_crop_square_24
                        );
                checkbiggerthan1select();
            }
        });

        // CLose button for selected items
        parentView.findViewById(R.id.closeselect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inselectedmode = false;

                holder.selectthis(false);
                holderlist.forEach((c)->{
                    c.selectmode(false);
                });

                selectbar.setVisibility(View.GONE);
                topsearchbar.setVisibility(View.GONE);
                topbar.setVisibility(View.VISIBLE);
                Fab.setVisibility(View.VISIBLE);

                bottommenu.setVisibility(View.VISIBLE);
                longholdmenu.setVisibility(View.GONE);
                bottommenu.startAnimation(slideup);
                showall();

            }
        });


        // Favorite button
        holder.update_favorite_status(item.islove);
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> values = new HashMap<String, String>();
                values.put("islove", holder.islove ? "0" : "1");

                int affected = db.update(item, values);

                Log.d("debug", " " + affected);

                if(affected > 0){
                    // Success
                    item.islove = !item.islove;
                    holder.update_favorite_status(item.islove);
                }

            }
        });

        holder.navigatetohike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(inselectedmode){
                    holder.toggle();
                    long itemscount = holderlist.stream().filter(obj -> obj.isselected).count();
                    itemselected.setText(itemscount + " items selected");

                    checkbiggerthan1select();

                    return;
                }

                navigate_to_hike(position);
            }
        });

        holder.obj = items.get(position);

        holder.navigatetohike.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                inselectedmode = true;

                holder.selectthis(true);
                holderlist.forEach((c)->{
                    c.selectmode(true);
                });

                selectbar.setVisibility(View.VISIBLE);
                topsearchbar.setVisibility(View.GONE);
                topbar.setVisibility(View.GONE);
                Fab.setVisibility(View.GONE);

                bottommenu.setVisibility(View.GONE);
                longholdmenu.setVisibility(View.VISIBLE);

                longholdmenu.startAnimation(slideup);

                long count = holderlist.stream().filter(obj -> obj.isselected).count();
                itemselected.setText(count + " items selected");

                checkbiggerthan1select();

                return true;
            }
        });

        //        DeleteWarning d = new DeleteWarning();
//        d.show( getParentFragmentManager(), "delete");

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
