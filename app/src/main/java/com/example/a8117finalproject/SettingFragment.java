package com.example.a8117finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SettingFragment extends Fragment implements Validator.ValidationListener {

    Button saveRC;
    Button deleteRoom;
    EditText etTemp;
    EditText etWDA;
    EditText etWEA;
    String roomName;
    String username;
    public static String ARG_PARAM1;
    public static String ARG_PARAM2;
    public static String ARG_PARAM3;
    public static String ARG_PARAM4;




    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        etTemp = view.findViewById(R.id.temp);
        etWDA = view.findViewById(R.id.weekday_alarm);
        etWEA = view.findViewById(R.id.weekend_alarm);
        saveRC = view.findViewById(R.id.saveroomsettings);
        deleteRoom = view.findViewById(R.id.deleteroom);
        etTemp.setText(ARG_PARAM1);
        etWDA.setText(ARG_PARAM2);
        etWEA.setText(ARG_PARAM3);
        roomName = ARG_PARAM4;


        username = SettingsActivity.username;



        //initial validator
        Validator validator = new Validator(this);
        validator.setValidationListener(this);


        /**
         * the delete room button logic
         * when clicked, validate the form
         */
        deleteRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
                //String requestBody = buildRequestBody();
                RequestBody body = RequestBody.create(mediaType, "{\n    \"username\": \""+username+"\",\n    \"room_name\": \""+roomName+"\"\n}");
                //RequestBody body = RequestBody.create(mediaType, buildRequestBody());

                Request request = new Request.Builder()
                        .url("https://final-project-team-1-section-1.herokuapp.com/user/room/remove")
                        .post(body)
                        .build();
                try {
                    //Toast.makeText(this, body.toString(), Toast.LENGTH_LONG).show();
                    Response response = client.newCall(request).execute();
                    JSONObject responseData = new JSONObject(response.body().string());
                    Toast.makeText(getContext(),responseData.toString(), Toast.LENGTH_LONG).show();
                    //etTest = findViewById(R.id.test);
                    //etTest.setText(responseData.toString());

                    String status = responseData.getString("status");
                    if ("200".equals(status)) {


                        Toast.makeText(getContext(),"Update successfully.", Toast.LENGTH_LONG).show();
                        /**
                         * @Yang Wang
                         * Here should jump to settings page and refresh
                         */


                    } else {
                        //Toast.makeText(this, "Unknown error, Please try again.", Toast.LENGTH_LONG).show();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }


    public static SettingFragment newInstance(String temp, String wd_alarm, String we_alarm, String roomName) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        ARG_PARAM1 = temp;
        ARG_PARAM2 = wd_alarm;
        ARG_PARAM3 = we_alarm;
        ARG_PARAM4 = roomName;;
        fragment.setArguments(args);
        //fragment.createBlack(param1);
        return fragment;
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }
}
