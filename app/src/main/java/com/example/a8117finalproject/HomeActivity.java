package com.example.a8117finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a8117finalproject.R;
import com.example.a8117finalproject.adapter.FragmentAdapter;
import com.example.a8117finalproject.roomPage_home.BlackFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity implements Validator.ValidationListener {
    private TextView color;
    private TabLayout tb;
    private ViewPager2 vp;
    private PagerAdapter pa;
    private Fragment testFragment;
    private BlackFragment bf;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public static final String FILE_NAME = "userSP";
    SharedPreferences userSP;
    SharedPreferences.Editor editor;

    int roomCount;
    ArrayList<String> roomsname = new ArrayList<String>();
    List<Fragment> fragmentList = new ArrayList<>();
    static JSONArray roomDetails = new JSONArray();
    Bundle[] bundles = new Bundle[roomCount];

    private String[] title = new String[]{"room1", "room2", "room3", "room4", "room5"};
    private String[] value = new String[]{
            "Here is new blue fragment", "Here is new red fragment",
            "Here is new yellow fragment", "Here is new black fragment",
            "Here is new white fragment",
            "Here is new blue fragment", "Here is new red fragment",
            "Here is new yellow fragment", "Here is new black fragment",
            "Here is new white fragment"};
    private String[] backColor = new String[]{"blue", "red", "yellow" ,"black", "white", "blue", "red", "yellow" ,"black", "white"};


    static String username;
    String homeName;
    String city;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //color = (TextView) findViewById(R.layout.fragment_black.color_black);
        //color = ()

        Button home = (Button) findViewById(R.id.go_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(it);
            }
        });
        Button setting = (Button) findViewById(R.id.go_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(it);
            }
        });
        userSP = this.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = userSP.edit();
        username = userSP.getString("username","");
        //getUserDetails();

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
                tab.setText(title[position]);//roomsname.get(position));
            }
        });
        tab.attach();
        //Toast.makeText(this, roomsname.get(0), Toast.LENGTH_LONG).show();
        //Button home = (Button) findViewById(R.id.setting_Home);
        /*home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(it);
            }
        });*/
    }

/*
    private void getUserDetails() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        String requestBody = buildRequestBody();
        RequestBody body = RequestBody.create(mediaType, "{\n    \"username\": \""+username+"\"\n}");
        //RequestBody body = RequestBody.create(mediaType, buildRequestBody());

        Request request = new Request.Builder()
                .url("https://final-project-team-1-section-1.herokuapp.com/user/room-details")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject responseData = new JSONObject(response.body().string());
            //etTest = findViewById(R.id.test);
            //etTest.setText(responseData.toString());

            String status = responseData.getString("status");
            if ("200".equals(status)) {

                JSONObject responseBody = new JSONObject(responseData.getString("body"));
                roomCount = responseBody.getInt("rooms_count");
                JSONArray rooms = responseBody.getJSONArray("room_names");

                getRoomName(rooms);
                //setHomeDetails(responseBody);
                roomDetails = responseBody.getJSONArray("room_list");


            } else {
                Toast getUserDataToast = Toast.makeText(getApplicationContext(), "Fail to get user dara. Try again", Toast.LENGTH_SHORT);
                getUserDataToast.show();
            }

            //responseData.getJSONObject();


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private String buildRequestBody() {
        String requestBody = "{\n    \"username\": \""+ username +"\n}";
        return requestBody;
    }


    /*private void setHomeDetails(JSONObject a) throws JSONException {

        homeName = a.getString("name");
        city= a.getString("location");

        etHomeName = findViewById(R.id.homename);
        etHomeName.setText(homeName);

        etCity = findViewById(R.id.city);
        etCity.setText(city);


    }*/

    /*private void getRoomName(JSONArray a) throws JSONException {

        for(int i = 0; i< a.length();i++) {
            roomsname.add(i, a.getString(i));
        }

    }







    /**
     * validation logic
     * if successful, get the contents from the form, and submit them to server
     * if not, give the tip of errors
     */
    @Override
    public void onValidationSucceeded() {
        //getContentFromForm();
        //submitForm();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            //show the error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                // show the other error messages
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * submit the form to server and react according to the response
     * if status is 200, go to the settings page and refresh
     * if status is others, toast error and ask for retry
     */
    /*private void submitForm() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        String requestBody = buildRequestBodysavehc();
        //RequestBody body = RequestBody.create(mediaType, "{\n    \"username\": \"vegesna00@gmail.com\",\n    \"password\": \"123\",\n    \"name\": \"nav\",\n    \"location\": \"windsor,canada\",\n    \"room_name\": \"bedroom-1\",\n    \"alarm_time_weekday\": \"07:00\",\n    \"alarm_time_weekend\": \"19:40\",\n    \"preferred_temp\": 22\n}");
        RequestBody body = RequestBody.create(mediaType, buildRequestBodysavehc());

        Request request = new Request.Builder()
                .url("https://final-project-team-1-section-1.herokuapp.com/user/settings")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject responseData = new JSONObject(response.body().string());


            //responseData.getJSONObject();
            String status = responseData.getString("status");
            if ("200".equals(status)) {

                Toast.makeText(this, "Update successfully.", Toast.LENGTH_LONG).show();
                /**
                 * @Yang Wang
                 * Here should jump to settings page and refresh
                 */
                /*Intent go = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(go);

            } else {
                Toast.makeText(this, "Unknown error, Please try again.", Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * initial the request body
     */
    /*private String buildRequestBodysavehc() {
        String requestBody = "{\n    \"username\": \""+username+"\",\n    \"name\": \""+homeName+"\",\n    \"location\": \""+city+"\"\n}";
        return requestBody;
    }

    /**
     * get the from content
     */
    /*private void getContentFromForm() {
        homeName = etHomeName.getText().toString().trim();
        city = etCity.getText().toString().trim();
    }*/
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

