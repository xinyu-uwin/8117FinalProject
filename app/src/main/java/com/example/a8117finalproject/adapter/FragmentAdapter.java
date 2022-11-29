package com.example.a8117finalproject.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.a8117finalproject.roomPage_home.BlackFragment;
import com.example.a8117finalproject.roomPage_home.RedFragment;
import com.example.a8117finalproject.roomPage_home.YellowFragment;

import org.json.JSONArray;
import org.json.JSONException;

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
        BlackFragment bf =  new BlackFragment();
        BlackFragment.newInstance(userName, roomList[position], roomDetails);
        return bf;
        /*switch(position%3)
        {
            case 0:
            case 1:
                RedFragment rf = new RedFragment();
                RedFragment.newInstance(color[position], backColor[position]);
                return rf;
            default:
                YellowFragment yf = new YellowFragment();
                YellowFragment.newInstance(color[position], backColor[position]);
                return yf;
        }*/
    }

    /*public Fragment createFragment(int position, int t) {
        switch(position)
        {
            case 0:
                return new BlueFragment();
            case 1:
                return new RedFragment();
            case 2:
                return new BlackFragment();
            default:
                return new YellowFragment();
        }
    }*/

    @Override
    public int getItemCount() {
        return roomNumber;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
