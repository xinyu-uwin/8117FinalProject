package com.example.a8117finalproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.a8117finalproject.adapter.BasicFragmentAdapter;
import com.example.a8117finalproject.bean.Room;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SettingsActivity extends AppCompatActivity {

    TextView etUsername;
    EditText etHomeName;
    EditText etCity;
    TextView etTest;
    TabLayout mRoomTab;
    ViewPager viewPager;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    String username;
    String homeName;
    String city;


    static int roomCount;
    ArrayList<String> roomsname = new ArrayList<String>();
    List<Fragment> fragmentList = new ArrayList<>();
    static JSONArray roomDetails = new JSONArray();
    Bundle[] bundles = new Bundle[roomCount];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



        //data initial
        mRoomTab = findViewById(R.id.roomTab);
        viewPager = findViewById(R.id.viewpager);
        etUsername = findViewById(R.id.username);
        etHomeName = findViewById(R.id.homename);
        etCity = findViewById(R.id.city);



        //get user data from server

            getUserDetails();



        String[] titleArray = new String[roomCount];
        for (int i = 0; i < roomCount; i++) {
            titleArray[i] = roomsname.get(i);
            fragmentList.add(new SettingFragment());




        }
        BasicFragmentAdapter adapter = new BasicFragmentAdapter(getSupportFragmentManager(), fragmentList, titleArray);
        viewPager.setAdapter(adapter);
        mRoomTab.setupWithViewPager(viewPager);




        //add room tabs
        //addRoomTab(roomCount);

    }



    public void a8117finalproject(View view) { startActivity(new Intent(this, SettingFragment.class)); }


    private void getContent() {

        username = etUsername.getText().toString().trim();
        city = etCity.getText().toString().trim();


    }

    private void getUserDetails() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        String requestBody = buildRequestBody();
        RequestBody body = RequestBody.create(mediaType, "{\n    \"username\":\"vegesna00@gmail.com\"\n}");
        //RequestBody body = RequestBody.create(mediaType, buildRequestBody());

        Request request = new Request.Builder()
                .url("https://final-project-team-1-section-1.herokuapp.com/user/room-details")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject responseData = new JSONObject(response.body().string());
            //etTest = findViewById(R.id.test_tip);
            //etTest.setText(responseData.toString());

            String status = responseData.getString("status");
            if ("200".equals(status)) {

                JSONObject responseBody = new JSONObject(responseData.getString("body"));
                roomCount = responseBody.getInt("rooms_count");
                JSONArray rooms = responseBody.getJSONArray("room_names");

                getRoomName(rooms);

                setHomeDetails(responseBody);


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

    //Add the room tabs according to the room count
    private void addRoomTab(int roomCount) {
        for (int i = 0; i < roomCount; i++) {
            mRoomTab.addTab(mRoomTab.newTab().setText(roomsname.get(i)));

        }

    }

    private void setHomeDetails(JSONObject a) throws JSONException {

        homeName = a.getString("name");
        city= a.getString("location");

        etHomeName = findViewById(R.id.homename);
        etHomeName.setText(homeName);

        etCity = findViewById(R.id.city);
        etCity.setText(city);


    }

    private void getRoomName(JSONArray a) throws JSONException {

        for(int i = 0; i< a.length();i++) {
            roomsname.add(i, a.getString(i));
        }

    }

    private void getRoomsDetails(JSONObject a) throws JSONException {
        roomDetails = a.getJSONArray("room_list");
        for(int i =0; i< roomDetails.length();i++){
            JSONObject roomdata = roomDetails.getJSONObject(i);
            bundles[i].putString("room_name",roomdata.getString("room_name"));
            bundles[i].putString("preferred_temp",roomdata.getString("preferred_temp"));
            bundles[i].putString("alarm_time_weekday",roomdata.getString("alarm_time_weekday"));
            bundles[i].putString("alarm_time_weekend",roomdata.getString("alarm_time_weekend"));
            fragmentList.get(i).setArguments(bundles[i]);
        }


    }

    private void setRoomsDetails(JSONObject a) {
        for(int i = 0; i<fragmentList.size();i++){

        }
    }
}
