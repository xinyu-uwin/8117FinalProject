package com.example.a8117finalproject.roomPage_home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.a8117finalproject.MainActivity;
import com.example.a8117finalproject.R;
import com.example.a8117finalproject.SettingsActivity;
import com.example.a8117finalproject.roomPage_curtain.AlarmRequest;
import com.example.a8117finalproject.roomPage_curtain.AlarmResponse;
import com.example.a8117finalproject.roomPage_curtain.ApiClient;
import com.example.a8117finalproject.roomPage_curtain.LightRequest;
import com.example.a8117finalproject.roomPage_curtain.LightResponse;
import com.example.a8117finalproject.roomPage_curtain.tempRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlackFragment<inflater> extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView color;
    Button change;
    EditText textChange;
    private String colorValue;
    private String backColor;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    private SeekBar lightSeekBar;
    private SeekBar curtainSeekbar;
    private TextView textView;
    private TextView timeDisplay;
    private TextView nextAlarm;
    private TextView lightView;
    private TextView curtainView;

    private TextView temp;
    private Button tempUp;
    private Button tempDown;
    private Button setTemp;


    int lightValue = 0;
    int curtainValue = 0;
    private static String name;
    private static String room;
    private String username = "qwe@qq.com";    //this need to be assigned depending on who is logged in
    private String room_name = "JOSH";             //this need to be assigned depending on which room
    private static JSONArray roomDetail;
    int roomNum = 0;
    /**
     */
    // TODO: Rename and change types and number of parameters
    public static BlackFragment newInstance(String userName, String roomName, JSONArray roomDetails) {
        BlackFragment fragment = new BlackFragment();
        Bundle args = new Bundle();
        name = userName;
        room = roomName;
        roomDetail = roomDetails;
        fragment.setArguments(args);
        //fragment.createBlack(param1);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getContext(), colorValue, Toast.LENGTH_LONG).show();
        //createBlack(mParam1);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_black, container, false);
        /*colorValue = ARG_PARAM1;
        backColor = ARG_PARAM2;
        color = view.findViewById(R.id.color_black);
        change = view.findViewById(R.id.button_black);
        textChange = view.findViewById(R.id.change_black);
        int background = BlackFragment.getId(backColor, R.color.class);
        view.setBackgroundColor(Color.parseColor(String.format("#%06X", (0xFFFFFF & background))));
        String info = colorValue;
        color.setText(info);//backColor+" is set to Background.");//+"\n"+Integer.toHexString(background));//BlackFragment.getName(backColor, R.color.class));
        textChange.setText(backColor);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String change = textChange.getText().toString();
                color.setText(change.toCharArray(),0, change.length());
            }
        });*/
        room_name = room;
        username = name;

        //textView = (TextView) view.findViewById(R.id.textView);

        lightSeekBar = (SeekBar) view.findViewById(R.id.lightBar);
        curtainSeekbar = (SeekBar) view.findViewById(R.id.curtainBar);
        timeDisplay = (TextView) view.findViewById(R.id.timeDisplay);
        nextAlarm = (TextView) view.findViewById(R.id.nextAlarm);
        lightView = (TextView) view.findViewById(R.id.lightView);
        curtainView = (TextView) view.findViewById(R.id.curtainView);

        lightView.setText(Integer.toString(lightValue));
        curtainView.setText(Integer.toString(curtainValue));

        temp = view.findViewById(R.id.temp);
        tempUp = view.findViewById(R.id.temp_up);
        tempDown = view.findViewById(R.id.temp_down);
        setTemp = view.findViewById(R.id.set_temp);
        try {
            roomNum = setTemperature();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tempUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempValue = Integer.parseInt(temp.getText().toString());
                tempValue++;
                temp.setText(String.valueOf(tempValue));
            }
        });
        tempDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempValue = Integer.parseInt(temp.getText().toString());
                tempValue--;
                temp.setText(String.valueOf(tempValue));
            }
        });
        setTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTemp();
            }
        });

        getSeekValue(roomNum);

        lightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                lightValue = i;
                lightView.setText(Integer.toString(lightValue));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateLight(createLightRequest());
            }
        });

        curtainSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                curtainValue = i;
                curtainView.setText(Integer.toString(curtainValue));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateLight(createLightRequest());
            }
        });
        //updateAlarm(createAlarmRequest());
        //curtainSeekbar.setProgress(10);
        /*Button change = view.findViewById(R.id.change_light);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //updateLight(createLightRequest());
                //updateAlarm(createAlarmRequest());
                //getSeekValue(roomNum);
            }
        });*/

        return view;
    }

    private int setTemperature() throws JSONException {

        //*Log.i("Room info", roomDetail.get(0).toString());
        int roomNum = 0;
        for(int i=0; i<roomDetail.length(); i++)
        {
            String[] value = roomDetail.get(i).toString().split("\"");
            if(value[3].equals(room_name))
            {
                temp.setText(value[16].toCharArray(), 1, 2);
                return i;
            }
        }

        /*String[] value = roomDetail.get(0).toString().split("\"");
        temp.setText(value[3]);*/
        return -1;
    }

    /*public static BlackFragment getInstance(Object object) {
        BlackFragment black = new BlackFragment();
        Bundle bundle = new Bundle();
    }*/
    public static int getId(String valueName, Class<?> c) {
        try{
            Field fe = c.getDeclaredField(valueName);
            return fe.getInt(fe);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getName(String valueName, Class<?> c) {
        try{
            Field fe = c.getDeclaredField(valueName);
            return fe.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public LightRequest createLightRequest(){        //request for light and curtains
        System.out.println("Light = "+ lightValue);
        //temp.setText(String.valueOf(lightValue));
        LightRequest lightRequest = new LightRequest();
        lightRequest.setLight_on(lightValue);
        lightRequest.setCurtain_on(curtainValue);
        lightRequest.setUsername(username);
        lightRequest.setRoom_name(room_name);
        //temp.setText(String.valueOf(room_name));

        return lightRequest;
    }

    public void updateLight(LightRequest lightRequest){
        Call<LightResponse> lightResponseCall = ApiClient.getLightService().saveUsers(lightRequest);
        lightResponseCall.enqueue(new Callback<LightResponse>() {
            @Override
            public void onResponse(Call<LightResponse> call, Response<LightResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_LONG).show();
                    assert response.body() != null;
                    Log.i("time ", response.isSuccessful()+" here");
                }else{
                    Toast.makeText(getActivity(), "Request Failed", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<LightResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Request Failed"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateTemp(){
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
            okhttp3.Response response = client.newCall(request).execute();
            JSONObject responseData = new JSONObject(response.body().string());


            //responseData.getJSONObject();
            String status = responseData.getString("status");
            if ("200".equals(status)) {
                Toast.makeText(getActivity(), "Update successfully.", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getActivity(), "Unknown error, Please try again.", Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String buildRequestBodysavehc() {
        String requestBody = "{" +
                "\n    \"username\": \""+username+
                "\",\n    \"room_name\": \""+room_name+
                "\",\n    \"preferred_temp\": \""+Integer.parseInt(temp.getText().toString())+
                "\"\n}";
        return requestBody;
    }

    public AlarmRequest createAlarmRequest(){        //request for light and curtains
        AlarmRequest alarmRequest = new AlarmRequest();
        alarmRequest.setUsername(username);
        alarmRequest.setRoom_name(room_name);

        return alarmRequest;
    }

    public void updateAlarm(AlarmRequest alarmRequest){

        Call<AlarmResponse> alarmResponseCall = ApiClient.getAlarmService().saveUsers(alarmRequest);
        //temp.setText(String.valueOf(alarmResponseCall.size()));
        alarmResponseCall.enqueue(new Callback<AlarmResponse>() {
            @Override
            public void onResponse(Call<AlarmResponse> call, Response<AlarmResponse> response) {
                if(response.isSuccessful()){
                    //Toast.makeText(getActivity(), "Updated Sucessfully", Toast.LENGTH_LONG).show();

                    Log.d("test", "yes");
                    String jsonStr = response.body().getBody().toString();
                    Map<String, String> jsonMap = new HashMap<>();
                    String key ="";
                    String value ="";
                    char flag = 'k';
                    for(int i=1; i<jsonStr.length()-1;i++){
                        char temp = jsonStr.charAt(i);
                        if(temp == '='){
                            flag = 'v';

                        }else if(temp == ',') {
                            flag = 'k';
                            jsonMap.put(key, value);
                            key = "";
                            value = "";
                        }else if(temp == ' ') {

                        }else {
                            if(flag=='k') {
                                key = key + jsonStr.charAt(i);
                            }
                            if(flag=='v') {
                                value = value + jsonStr.charAt(i);
                            }
                        }
                    }
                    Calendar Demo_Calendar = Calendar.getInstance();
                    Demo_Calendar.setTime(new Date());
                    int Day_Number = Demo_Calendar.get(Calendar.DAY_OF_WEEK);
                    String alarmTime="";
                    if(Day_Number==6||Day_Number==7) {
                        alarmTime = jsonMap.get("alarm_time_weekend");
                    }else {
                        alarmTime = jsonMap.get("alarm_time_weekday");
                    }
                    timeDisplay.setText(alarmTime);
                    String timeDiff = getTimeDiff(alarmTime);

                    //Toast.makeText(getContext(), "Here is "+timeDiff, Toast.LENGTH_LONG).show();
                    Log.i("Time diff", timeDiff);
                    nextAlarm.setText(timeDiff.split(":")[0]+"h"+timeDiff.split(":")[1]+"m");
                }else{
                    Toast.makeText(getActivity(), "Request Failed", Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<AlarmResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Alarm Request Failed"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    private static int getCurtain() {
        int curtainValue = 0;
        return curtainValue;
    }
    static String getTimeDiff(String alarmTime) {
        DateTimeFormatter dtf2 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dtf2 = DateTimeFormatter.ofPattern("HH:mm");
        }
        LocalDateTime now2 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            now2 = LocalDateTime.now();
        }
        String currTime2 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currTime2 = dtf2.format(now2);
        }
        String day = "";

        if(Integer.parseInt(alarmTime.split(":")[0])>Integer.parseInt(currTime2.split(":")[0])) {
            LocalDate today1 = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                today1 = LocalDate.now();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                day = today1.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            }

        }else if(Integer.parseInt(alarmTime.split(":")[0])==Integer.parseInt(currTime2.split(":")[0])) {
            if(Integer.parseInt(alarmTime.split(":")[1])>=Integer.parseInt(currTime2.split(":")[1])) {
                LocalDate today1 = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    today1 = LocalDate.now();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    day = today1.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                }
            }else {
                LocalDate today1 = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    today1 = LocalDate.now();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    day = (today1.plusDays(1)).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                }

            }
        }else {
            LocalDate today1 = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                today1 = LocalDate.now();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                day = (today1.plusDays(1)).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            }
        }

        DateTimeFormatter dtf = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        }
        LocalDateTime now = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
        }
        String currTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currTime = dtf.format(now);
        }


        alarmTime = day+" "+alarmTime+":00";
//		 System.out.println(currTime+ " \n" +alarmTime);

        System.out.println(alarmTime);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try {
            Date d1 = sdf.parse(currTime);
            Date d2 = sdf.parse(alarmTime);

            long difference_In_Time= d2.getTime() - d1.getTime();
            long difference_In_Minutes = (difference_In_Time/ (1000 * 60))% 60;
            long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
            return difference_In_Hours+":"+difference_In_Minutes;
        }
        catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    private void getSeekValue(int roomNum) {

        String light = null;
        String curtain = null;
        try {
            light = roomDetail.get(roomNum).toString().split("\"")[10];
            light = String.valueOf(light.toCharArray(), 1, light.toCharArray().length-2);
            curtain = roomDetail.get(roomNum).toString().split("\"")[12];
            //temp.setText(light);
            curtain = String.valueOf(curtain.toCharArray(), 1, curtain.toCharArray().length-2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        lightValue = Integer.parseInt(light);
        curtainValue = Integer.parseInt(curtain);
        lightSeekBar.setProgress(lightValue);
        curtainSeekbar.setProgress(curtainValue);
        lightView.setText(Integer.toString(lightValue));
        curtainView.setText(Integer.toString(curtainValue));
    }
}