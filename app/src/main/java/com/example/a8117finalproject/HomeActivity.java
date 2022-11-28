package com.example.a8117finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.TextView;

import com.example.a8117finalproject.R;
import com.example.a8117finalproject.adapter.FragmentAdapter;
import com.example.a8117finalproject.roomPage_home.BlackFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private TextView color;
    private TabLayout tb;
    private ViewPager2 vp;
    private PagerAdapter pa;
    private Fragment testFragment;
    private BlackFragment bf;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] title = new String[]{"room1", "room2", "room3", "room4", "room5", "room1", "room2", "room3", "room4", "room5"};
    private String[] value = new String[]{
            "Here is new blue fragment", "Here is new red fragment",
            "Here is new yellow fragment", "Here is new black fragment",
            "Here is new white fragment",
            "Here is new blue fragment", "Here is new red fragment",
            "Here is new yellow fragment", "Here is new black fragment",
            "Here is new white fragment"};
    private String[] backColor = new String[]{"blue", "red", "yellow" ,"black", "white", "blue", "red", "yellow" ,"black", "white"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //color = (TextView) findViewById(R.layout.fragment_black.color_black);
        //color = ()
        TabLayout tl = findViewById(R.id.tab_layout);
        ViewPager2 viewPager2 = findViewById(R.id.pager);
        FragmentAdapter fa = new FragmentAdapter(this);
        fa.setValue(title.length, value, backColor);
        viewPager2.setAdapter(fa);

        viewPager2.setOffscreenPageLimit(1);
        TabLayoutMediator tab = new TabLayoutMediator(tl, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                /*switch (position) {

                    case 0:
                        tab.setText("Fragment1");
                        break;
                    case 1:
                        tab.setText("Fragment2");
                        break;
                    case 2:
                        tab.setText("Fragment3");
                        break;
                    case 3:
                        tab.setText("Fragment4");
                        break;
                }*/
                tab.setText(title[position]);
            }
        });
        tab.attach();
    }
        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //tabTester();
        //onViewCreated(this, );
        tb = findViewById(R.id.tab_layout);
        //vp = findViewById(R.id.pager);

        for(int i=0; i<title.length; i++)
        {
            tb.addTab(tb.newTab().setText(title[i]));
            fragments.add(TabFragment.newInstance(title[i]));
        }


        /*new TabLayoutMediator(tb, vp,
                (tab, position) -> tab.setText(title[position])
        ).attach();*/

        /*vp.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return FragmentAdapter.newInstance(title[position]);
            }

            @Override
            public int getItemCount() {
                return title.length;
            }
        });

        //vp.registerOnPageChangeCallback(changeCallBack);
    }
    /*public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        //ViewPager2 viewPager = view.findViewById(R.id.pager);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("OBJECT " + (position + 1))
        ).attach();
    }
    /*private void tabTester() {
        tb = (TabLayout) findViewById(R.id.tab_layout);
        vp = (ViewPager2) findViewById(R.id.pager);
        for(int i=0; i<title.length; i++)
        {
            fragments.add(new Fragment());
            tb.addTab(tb.newTab());
        }
        tb.setupWithViewPager(vp, false);
        pa = new PagerAdapter() {
            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return false;
            }
        };
        vp.setAdapter(pa);
        for(int i=0; i<title.length; i++)
        {
            tb.getTabAt(i).setText(title[i]);
        }
    }*/
}

