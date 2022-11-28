package com.example.a8117finalproject.roomPage_home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.a8117finalproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YellowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YellowFragment extends Fragment {

    static TextView color;
    static Button change;
    static EditText textChange;
    private static String colorValue;
    private static String backColor;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public YellowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YellowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YellowFragment newInstance(String param1, String param2) {
        YellowFragment fragment = new YellowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        colorValue = param1;
        args.putString(ARG_PARAM2, param2);
        backColor = param2;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_yellow, container, false);

        color = view.findViewById(R.id.color_yellow);
        change = view.findViewById(R.id.button_yellow);
        textChange = view.findViewById(R.id.change_yellow);
        int background = BlackFragment.getId(backColor, R.color.class);
        view.setBackgroundColor(Color.parseColor(String.format("#%06X", (0xFFFFFF & background))));
        String info = colorValue;
        color.setText(info);//backColor+" is set to Background.");//+"\n"+Integer.toHexString(background));//BlackFragment.getName(backColor, R.color.class));
        textChange.setText(backColor);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String change = textChange.getText().toString();
                color.setText(change.toCharArray(), 0, change.length());
            }
        });
        return view;
    }
}