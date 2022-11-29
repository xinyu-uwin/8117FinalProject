package com.example.a8117finalproject.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.a8117finalproject.roomPage_home.roomPageHome;

import org.json.JSONArray;

public class FragmentAdapter extends FragmentStateAdapter {
    public static int roomNumber = 0;
    String[] roomList;
    String userName;
    JSONArray roomDetails;
    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {//,} int length, String[] color) {
        super(fragmentActivity);
        //roomNumber = length;
        //this.color = color;
    }
    public void setValue(int roomNumber, String[] roomList, String userName, JSONArray roomDetails) {
        this.roomNumber = roomNumber;
        this.roomList = roomList;
        this.userName = userName;
        this.roomDetails = roomDetails;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        roomPageHome bf =  new roomPageHome();
        roomPageHome.newInstance(userName, roomList[position], roomDetails);
        return bf;
    }

    @Override
    public int getItemCount() {
        return roomNumber;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
