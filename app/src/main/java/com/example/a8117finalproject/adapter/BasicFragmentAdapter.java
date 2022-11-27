package com.example.a8117finalproject.adapter;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import com.example.a8117finalproject.SettingFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Logger;

/**
 * Fragment adapter
 *
 */
public class BasicFragmentAdapter extends FragmentStatePagerAdapter {


    private final FragmentManager fm;
    String[] titleArr;
    List<Fragment> mFragmentList;


    public BasicFragmentAdapter(FragmentManager fm, List<Fragment> list, String[] titleArr) {
        super(fm);
        this.fm = fm;
        mFragmentList = list;
        this.titleArr = titleArr;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList != null ? mFragmentList.size() : 0;
    }

    public void refresh(List<Fragment> fragmentList) {
        if (this.mFragmentList != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment fragment : this.mFragmentList) {
                ft.remove(fragment);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.mFragmentList = fragmentList;
        this.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleArr[position];
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
  super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}