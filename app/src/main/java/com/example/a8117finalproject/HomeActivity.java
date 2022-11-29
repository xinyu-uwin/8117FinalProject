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
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a8117finalproject.adapter.FragmentAdapter;
import com.example.a8117finalproject.roomPage_home.roomPageHome;
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
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity implements Validator.ValidationListener {

    @Order(1)
    @NotEmpty
    @Length(min=3,max=15,message = "Length should be 3-15.")
    EditText etHomeName;
    @Order(2)
    @NotEmpty
    @Pattern(regex = ".+,.+", message = "Format should be \"city,country\".")
    private TextView color;
    private TabLayout tb;
    private ViewPager2 vp;
    private PagerAdapter pa;
    private Fragment testFragment;
    private roomPageHome bf;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public static final String FILE_NAME = "userSP";
    private static final String WEATHER_BODY = "9f3c930d48912bb3f53c6b6902a01953";
    SharedPreferences userSP;
    SharedPreferences.Editor editor;

    int roomCount;
    static JSONArray roomDetails = new JSONArray();
    Bundle[] bundles = new Bundle[roomCount];
    TextView city;
    ImageView weather;
    TextView weatherDesc;

    private String[] roomList;

    String cityLocate;
    String weatherValue;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setBottomButton();
        city = (TextView)findViewById(R.id.city_name);
        weather = (ImageView)findViewById(R.id.weather_photo);
        weatherDesc = (TextView)findViewById(R.id.weather_value);

        userSP = this.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = userSP.edit();
        username = userSP.getString("username","");
        getUserDetails();
        getWeatherDetail();
        //setWeather();
        Arrays.sort(roomList);
        TabLayout tl = findViewById(R.id.tab_layout);
        ViewPager2 viewPager2 = findViewById(R.id.pager);
        FragmentAdapter fa = new FragmentAdapter(this);
        fa.setValue(roomList.length, roomList, username, roomDetails);
        viewPager2.setAdapter(fa);

        viewPager2.setOffscreenPageLimit(1);
        TabLayoutMediator tab = new TabLayoutMediator(tl, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                tab.setText(roomList[position]);//roomsname.get(position));
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
    private void setWeather() {
        Log.i("Weather value: ", weatherValue);
        if(weatherValue.equals("overcast clouds"))
        {
            weather.setBackgroundResource(R.drawable.cloudy);
        }
        if(weatherValue.equals("sunny"))
        {
            weather.setBackgroundResource(R.drawable.weather_sunny);
        }
        if(weatherValue.equals("sunny"))
        {
            weather.setBackgroundResource(R.drawable.weather_sunny);
        }
    }*/

    protected void getUserDetails() {
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
            //Toast.makeText(this, responseData.toString(), Toast.LENGTH_LONG).show();
            //Log.i("responseData", responseData.toString());

            String status = responseData.getString("status");
            if ("200".equals(status)) {

                JSONObject responseBody = new JSONObject(responseData.getString("body"));
                roomCount = responseBody.getInt("rooms_count");
                JSONArray rooms = responseBody.getJSONArray("room_names");
                roomList = new String[rooms.length()];
                for(int i=0; i<rooms.length(); i++)
                {
                    roomList[i] = rooms.get(i).toString();
                }
                roomDetails = responseBody.getJSONArray("room_list");
                cityLocate = responseBody.getString("location");
                Log.i("location: ", cityLocate);
                Log.i("City info", cityLocate);
                city.setText(cityLocate.split(",")[0]);


        } else {
                Toast getUserDataToast = Toast.makeText(getApplicationContext(), "Fail to get user dara. Try again", Toast.LENGTH_SHORT);
                getUserDataToast.show();
            }

            //responseData.getJSONObject();*/


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


    protected void getWeatherDetail() {
        StringBuilder command = new StringBuilder();
        command.append("https://final-project-team-1-section-1.herokuapp.com/weatherdata/");
        command.append("?location=");
        command.append(cityLocate);
        //command.append("&appid=");
        //command.append(WEATHER_BODY);

        StringRequest sRequest = new StringRequest(com.android.volley.Request.Method.GET, command.toString(), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                //StringBuilder detail = new StringBuilder();
                try {
                    StringBuilder info = new StringBuilder();
                    //JSONObject jList = new JSONObject(response).getJSONObject("list");
                    //for(int i=0; i<jList.length(); i++)
                    {
                        JSONObject jData = new JSONObject(response);
                        //JSONArray jArray = jData.getJSONArray("weather");
                        JSONObject jWeather = jData.getJSONObject("body");
                        String description = jWeather.getString("climate");

                        weatherValue = description;
                        weatherDesc.setText(weatherValue);
                        Log.i("TAG_Weather_II", weatherValue);
                        if(weatherValue.equals("Clouds"))
                            weather.setBackgroundResource(R.drawable.cloudy);
                        if(weatherValue.equals("Rain"))
                            weather.setBackgroundResource(R.drawable.rain);
                        if(weatherValue.equals("Snow"))
                            weather.setBackgroundResource(R.drawable.snow);
                        if(weatherValue.equals("Drizzle"))
                            weather.setBackgroundResource(R.drawable.drizzle);
                        if(weatherValue.equals("Mist"))
                            weather.setBackgroundResource(R.drawable.mist);
                        if(weatherValue.equals("Drizzle"))
                            weather.setBackgroundResource(R.drawable.drizzle);
                        if(weatherValue.equals("Haze"))
                            weather.setBackgroundResource(R.drawable.haze);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(getApplicationContext());
        rQueue.add(sRequest);

        //Log.i("TAG_Weather_II", weatherValue);
    }

    private String buildRequestBody() {
        String requestBody = "{\n    \"username\": \""+ username +"\n}";
        return requestBody;
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }

    private void setBottomButton() {

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

    }
}

