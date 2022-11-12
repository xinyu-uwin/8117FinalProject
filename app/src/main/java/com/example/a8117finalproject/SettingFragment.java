package com.example.a8117finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class SettingFragment extends Fragment {


    EditText etTemp;
    EditText etWeekday;
    EditText etWeekend;
    Button etSaveRoom;
    Button etDelRoom;



    String temp;
    String weekdayAlarm;
    String WeekendAlarm;





    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_setting, container, false);


        Bundle bundle = getArguments();



        return view;



    }
}
