package com.example.a8117finalproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.a8117finalproject.adapter.BasicFragmentAdapter;
import com.example.a8117finalproject.bean.Room;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Pattern;

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

public class SettingsActivity extends AppCompatActivity implements Validator.ValidationListener {

    //initial the elements needed
    TextView etUsername;
    @Order(1)
    @NotEmpty
    @Length(min=3,max=15,message = "Length should be 3-15.")
    EditText etHomeName;
    @Order(2)
    @NotEmpty
    @Pattern(regex = ".+,.+", message = "Format should be \"city,country\".")
    EditText etCity;
    TextView etTest;
    TabLayout mRoomTab;
    ViewPager viewPager;
    Button addRoom;
    Button logOut;
    Button savehc;

    private FragmentManager manager;
    private FragmentTransaction transaction;

    static String username;
    String homeName;
    String city;

    //initial the shared preference
    public static final String FILE_NAME = "userSP";
    SharedPreferences userSP;
    SharedPreferences.Editor editor;




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


        //initial validator
        Validator validator = new Validator(this);
        validator.setValidationListener(this);



        //data initial
        mRoomTab = findViewById(R.id.roomTab);
        viewPager = findViewById(R.id.viewpager);
        etUsername = findViewById(R.id.username);
        etHomeName = findViewById(R.id.homename);
        etCity = findViewById(R.id.city);
        addRoom = findViewById(R.id.addroom);
        logOut = findViewById(R.id.logout);
        savehc = findViewById(R.id.savehomesettings);


        /**
         * fill the username from userSP
         */
        userSP = this.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = userSP.edit();
        username = userSP.getString("username","");
        etUsername.setText(username);



        //get user data from server
        getUserDetails();





        String[] titleArray = new String[roomCount];
        for (int i = 0; i < roomCount; i++) {
            titleArray[i] = roomsname.get(i);
            SettingFragment st = new SettingFragment();
            JSONObject roominfo = null;
            String roomtemp = null;
            String roomwda = null;
            String roomwea = null;
            try {
                roominfo = roomDetails.getJSONObject(i);
                roomtemp = roominfo.getString("preferred_temp");
                roomwda = roominfo.getString("alarm_time_weekday");
                roomwea = roominfo.getString("alarm_time_weekend");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            st.newInstance(roomtemp, roomwda, roomwea, roomsname.get(i));
            fragmentList.add(st);
        }

        BasicFragmentAdapter adapter = new BasicFragmentAdapter(getSupportFragmentManager(), fragmentList, titleArray);

        viewPager.setAdapter(adapter);
        mRoomTab.setupWithViewPager(viewPager);


        //add room tabs
        //addRoomTab(roomCount);



        /**
         * the add button logic
         * when clicked, jump to the add room page
         */
        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * @Yang Wang
                 * here should jump back to the add room page
                 */

            }
        });

        /**
         * the log out button logic
         * when clicked, jump back to the log in page 1
         */
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save the login status to userSP
                editor.putInt("is_logged_in",0);
                editor.commit();

                /**
                 * @Yang Wang
                 * here should jump back to the log in page 1
                 */

            }
        });

        /**
         * the save home button logic
         * when clicked, validate the form
         */
        savehc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validator.validate();

            }
        });

    }

    /*public void a8117finalproject(View view) {
        startActivity(new Intent(this, SettingFragment.class));
    }*/


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
            //etTest = findViewById(R.id.test);
            //etTest.setText(responseData.toString());

            String status = responseData.getString("status");
            if ("200".equals(status)) {

                JSONObject responseBody = new JSONObject(responseData.getString("body"));
                roomCount = responseBody.getInt("rooms_count");
                JSONArray rooms = responseBody.getJSONArray("room_names");

                getRoomName(rooms);
                setHomeDetails(responseBody);
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







    /**
     * validation logic
     * if successful, get the contents from the form, and submit them to server
     * if not, give the tip of errors
     */
    @Override
    public void onValidationSucceeded() {
        getContentFromForm();
        submitForm();
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
    private void submitForm() {
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
    private String buildRequestBodysavehc() {
        String requestBody = "{\n    \"username\": \""+username+"\",\n    \"name\": \""+homeName+"\",\n    \"location\": \""+city+"\"\n}";
        return requestBody;
    }

    /**
     * get the from content
     */
    private void getContentFromForm() {
        homeName = etHomeName.getText().toString().trim();
        city = etCity.getText().toString().trim();
    }

}