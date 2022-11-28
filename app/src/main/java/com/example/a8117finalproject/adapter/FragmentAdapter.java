package com.example.a8117finalproject.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.a8117finalproject.roomPage_home.BlackFragment;
import com.example.a8117finalproject.roomPage_home.RedFragment;
import com.example.a8117finalproject.roomPage_home.YellowFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    public static int roomNumber = 0;
    private static String[] color;
    private static String[] backColor;
    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {//,} int length, String[] color) {
        super(fragmentActivity);
        //roomNumber = length;
        //this.color = color;
    }
    public void setValue(int length, String[] colorValue, String[] backColor) {
        roomNumber = length;
        color = colorValue;
        this.backColor = backColor;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        BlackFragment bf =  new BlackFragment();
        BlackFragment.newInstance(color[position], backColor[position]);
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

    public void update(String[] color) {
        this.color = color;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
