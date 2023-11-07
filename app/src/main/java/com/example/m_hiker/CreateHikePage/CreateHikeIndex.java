package com.example.m_hiker.CreateHikePage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m_hiker.CreateHikePage.screens.DatePage;
import com.example.m_hiker.CreateHikePage.screens.HikeRunnable;
import com.example.m_hiker.CreateHikePage.screens.LengthAndDifficulty;
import com.example.m_hiker.CreateHikePage.screens.LocationPage;
import com.example.m_hiker.CreateHikePage.screens.NamePage;
import com.example.m_hiker.Dialogs.ToastMessage;
import com.example.m_hiker.R;
import com.example.m_hiker.database.DatabaseMHike;
import com.example.m_hiker.database.Hikes;
import com.example.m_hiker.utils.debug;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateHikeIndex#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateHikeIndex extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseMHike db;

    public CreateHikeIndex() {
        // Required empty public constructor
    }

    public static CreateHikeIndex newInstance(String param1, String param2) {
        CreateHikeIndex fragment = new CreateHikeIndex();
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

        db = DatabaseMHike.init(getContext());

    }


    private void create_hike(){

        // The following lines are for debugging only, not for production
        String name = debug.generate_faker_text(5,10);
        String location = "National park, Vietnam";
        String date = Integer.toString(debug.generate_faker_int(1,30)) + "/" + Integer.toString(debug.generate_faker_int(1,12)) + "/" + "2020";
        Integer length = debug.generate_faker_int(1,10);
        String unit = "km";
        Integer companion = debug.generate_faker_int(1,20);
        String level = "hard";
        Boolean parking = true;
        String comment = debug.generate_faker_text(10, 50);
        double lat = (double)debug.generate_faker_int(100,1000);
        double long_ = (double)debug.generate_faker_int(100,1000);
        Boolean islove = false;
        String thumbnail_path = "";

        // Create a new hike
        Hikes new_hike = new Hikes(
                name, location, date, length, unit, level, parking, comment, islove, thumbnail_path, companion, lat, long_
        );

        db.insert(new_hike);

    }

    ImageView bullet1;
    ImageView bullet2;
    ImageView bullet3;
    ImageView bullet4;

    private void checkbullet(int state){

        bullet1.setImageResource(R.drawable.dragbar);
        bullet2.setImageResource(R.drawable.dragbar);
        bullet3.setImageResource(R.drawable.dragbar);
        bullet4.setImageResource(R.drawable.dragbar);

        ImageView selected =
                state==0 ? bullet1 :
                state==1 ? bullet2 :
                state==2 ? bullet3 :
                state==3 ? bullet4 : null;

        if(selected!=null)
            selected.setImageResource(R.drawable.fulldragbar);

    }

    // User input information here
    String new_name = "";
    String new_date = "";
    String new_location = "";
    int new_length = 0;
    String new_unit = "";
    String new_difficulty = "";
    String new_description = "";
    boolean isparking = false;
    int new_companions = 0;

    private void openconfirmdialog(View view){
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        View dialogview = LayoutInflater.from(getContext()).inflate(
                R.layout.confirmation_bottomsheet,
                (LinearLayout)view.findViewById(R.id.confirmcontainer)
        );

        // Setting default value
        dialog.setContentView(dialogview);
        dialog.show();
    }


    View view;

    private void showtoasterror(String texterror){

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toasterror, view.findViewById(R.id.toasterrorroot));

        ((TextView) layout.findViewById(R.id.errorlabel)).setText(texterror);

        // Switch to that slide and show the error
        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.BOTTOM, 0 ,50);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_create_hike_index, container, false);

//        openconfirmdialog(view);

        // Grabbing the ViewPager2
        ViewPager2 viewpager = view.findViewById(R.id.slideshow);

        // Grabbing elements like next and back button
        Button nextbtn = view.findViewById(R.id.nextclickbtn);
        Button back = view.findViewById(R.id.backclickbtn);
        TextView text = view.findViewById(R.id.nextbtn);
        Runnable disableNextbtnstate = new Runnable() {
            @Override
            public void run() {
                text.setTextColor(getResources().getColor(R.color.primary));
                viewpager.setUserInputEnabled(true);
            }
        };
        Runnable enableNextbtnstate = new Runnable() {
            @Override
            public void run() {
                text.setTextColor(getResources().getColor(R.color.black));
                viewpager.setUserInputEnabled(true);
            }
        };
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewpager.getCurrentItem()==3){
                    // Finish button
//                    create_hike();

                    if(new_name.length()==0){
                        viewpager.setCurrentItem(0);
                        showtoasterror("Name cannot be empty");
                        return;
                    }

                    if(new_date.length()==0){
                        viewpager.setCurrentItem(2);
                        showtoasterror("Date cannot be empty");
                        return;
                    }
                    if(new_location.length()==0){
                        viewpager.setCurrentItem(1);
                        showtoasterror("Location cannot be empty");
                        return;
                    }
                    if(new_length<=0){
                        viewpager.setCurrentItem(3);
                        showtoasterror("Length has to be valid");
                        return;
                    }
                    if(new_unit.length()==0){
                        viewpager.setCurrentItem(3);
                        showtoasterror("Please pick a unit");
                        return;
                    }
                    if(new_difficulty.length()==0){
                        viewpager.setCurrentItem(3);
                        showtoasterror("Difficulty cannot be empty");
                        return;
                    }

                    // Create a new hike
                    Hikes new_hike = new Hikes(
                            new_name, new_location, new_date, new_length, new_unit, new_difficulty, isparking,
                            new_description, false, "", new_companions, 0, 0
                    );

                    db.insert(new_hike);
                    Navigation.findNavController(view).navigate(R.id.action_createNewHike_to_homepage);
                    ToastMessage.success(view, "Successfully insert new record");

                }else{
                    // Go next
                    viewpager.setCurrentItem(viewpager.getCurrentItem()+1, true);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewpager.getCurrentItem()==0){
                    getActivity().onBackPressed();
                }else{
                    viewpager.setCurrentItem(viewpager.getCurrentItem()-1, true);

                }
            }
        });


        // Creating a slide of fragments
        ArrayList<Fragment> fragments = new ArrayList<>();

        Fragment namefrag = (new NamePage()).setcallback(new HikeRunnable(){
            @Override
            public void name(String name) {
                new_name = name;
            }
        });
        Fragment datefrag = (new DatePage()).setcallback(new HikeRunnable(){
            @Override
            public void date(String date) {
                new_date = date.trim();
            }
        });
        Fragment locadrag = ((LocationPage)new LocationPage()).setpager(viewpager).setcallback(new HikeRunnable(){
            @Override
            public void location(String location) {
                new_location = location.trim();
            }
        });
        Fragment extradrag =  new LengthAndDifficulty().setcallback(new HikeRunnable(){
            @Override
            public void extra(int l, String u, String des, boolean park, int com, String difficult) {
                new_length = l;
                new_unit = u.trim();
                new_description = des.trim();
                isparking = park;
                new_companions = com;
                new_difficulty = difficult;
            }
        });
        fragments.add(namefrag);
        fragments.add(locadrag);
        fragments.add(datefrag);
        fragments.add(extradrag);

        // Creating an adapter
        FragmentStateAdapter adapter = new CreateHikeAdapter(this, fragments);
        viewpager.setAdapter(adapter);

        // Adding click events to some stuffs
        Runnable check_last_slide = new Runnable() {
            @Override
            public void run() {
                TextView text = ((TextView)view.findViewById(R.id.nextbtn));

                if(viewpager.getCurrentItem()==3){
                    text.setText("Finish");
                    text.setTextColor(getResources().getColor(R.color.primary));
                }else{
                    ((TextView)view.findViewById(R.id.nextbtn)).setText("Next");
                    text.setTextColor(getResources().getColor(R.color.black));

                }
            }
        };

        // Actions and variables relating to scrolling back and forth
        bullet2 = view.findViewById(R.id.bulletbar2);
        bullet1 = view.findViewById(R.id.bulletbar1);
        bullet3 = view.findViewById(R.id.bulletbar3);
        bullet4 = view.findViewById(R.id.bulletbar4);

        viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                checkbullet(position);

                switch (position){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                            default:
                        break;
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                check_last_slide.run();
            }
        });

        View.OnClickListener backaction = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int item = viewpager.getCurrentItem();
                if(item == 0) {
                    Navigation.findNavController(view).navigate(R.id.homepage);
                    return;
                }

                viewpager.setUserInputEnabled(true);
                viewpager.setCurrentItem(item - 1,true);

            }
        };

        view.findViewById(R.id.backbtn).setOnClickListener(backaction);
        view.findViewById(R.id.backarrow).setOnClickListener(backaction);


        return view;
    }
}
