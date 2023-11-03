package com.example.m_hiker.Observation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.m_hiker.Dialogs.DeleteWarning;
import com.example.m_hiker.R;
import com.example.m_hiker.components.SocialMedia;
import com.example.m_hiker.database.Observation;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewObservation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewObservation extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewObservation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewObservation.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewObservation newInstance(String param1, String param2) {
        ViewObservation fragment = new ViewObservation();
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

    TextView describeob;
    TextView timeob;
    TextView dateob;
    TextView weather;
    TextView category;
    TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Context context = getContext();
        View view = inflater.inflate(R.layout.fragment_view_observation, container, false);

        Bundle bundle = getArguments();
        Observation ob = ((Observation.ParcelObservation)bundle.getParcelable("object")).object;

        describeob = view.findViewById(R.id.describeob);
        timeob = view.findViewById(R.id.timeob);
        dateob = view.findViewById(R.id.calenderob);
        weather = view.findViewById(R.id.weather);
        category = view.findViewById(R.id.categoryob);
        title = view.findViewById(R.id.titleob);

        title.setText(ob.title);
        timeob.setText(ob.time);
        dateob.setText(ob.date);
        describeob.setText(ob.comments.trim().length()==0  ? "No comments available" : ob.comments.trim());
        category.setText(ob.category);
        weather.setText(ob.weather.trim().length()==0 ? "Unknown" : ob.weather.trim());



        view.findViewById(R.id.sharebtnob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SocialMedia(getContext(), view);
            }
        });

        view.findViewById(R.id.deletebtnob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DeleteWarning dialog = new DeleteWarning(new DeleteWarning.Callback() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void agree() {

                    }
                });

                dialog.show(getParentFragmentManager(), "Warning");
            }
        });

        view.findViewById(R.id.editbtnob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("object", ob.getParcelObject());
                Navigation.findNavController(view).navigate(R.id.action_viewObservation_to_editObservation2, bundle);
            }
        });

        view.findViewById(R.id.backbtnob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}