package com.example.a8117finalproject;

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
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Pattern;

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
    @Order(1)
    @NotEmpty
    @Min(value = 16,message = "Should between 16-30")
    @Max(value = 30,message = "Should between 16-30")
    EditText etTemp;
    @Order(2)
    @NotEmpty
    @Length(min=5,max=5,message ="Format should be \"HH:MM\"")
    @Pattern(regex = "\\d\\d:\\d\\d",message ="Format should be \"HH:MM\"")
    EditText etWDA;
    @Order(3)
    @NotEmpty
    @Length(min=5,max=5,message ="Format should be \"HH:MM\"")
    @Pattern(regex = "\\d\\d:\\d\\d",message ="Format should be \"HH:MM\"")
    EditText etWEA;
    String roomName;
    String username;
    String temp;
    String wda;
    String wea;

    public  String ARG_PARAM1;
    public  String ARG_PARAM2;
    public  String ARG_PARAM3;
    public  String ARG_PARAM4;




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
         * when clicked, send request
         * if successful, jump to settings page
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
                    //Toast.makeText(getContext(),responseData.toString(), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getContext(), "Unknown error, Please try again.", Toast.LENGTH_LONG).show();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


/**
 * the save room change button logic
 * when clicked, validate the form
 * if success, jump to settings page
 */

        saveRC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validator.validate();



            }
        });


        return view;
    }


    public  SettingFragment newInstance(String temp, String wd_alarm, String we_alarm, String roomName) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        ARG_PARAM1 = temp;
        ARG_PARAM2 = wd_alarm;
        ARG_PARAM3 = we_alarm;
        ARG_PARAM4 = roomName;;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onValidationSucceeded() {

        temp = etTemp.getText().toString().trim();
        wda = etWDA.getText().toString().trim();
        wea = etWEA.getText().toString().trim();


        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        //String requestBody = buildRequestBody();
        RequestBody body = RequestBody.create(mediaType, "{\n    \"username\": \""+username+"\",\n    \"room_name\": \""+roomName+"\",\n    \"alarm_time_weekday\": \""+wda+"\",\n    \"alarm_time_weekend\": \""+wea+"\",\n    \"preferred_temp\": \""+temp+"\"\n}");
        //RequestBody body = RequestBody.create(mediaType, buildRequestBody());

        Request request = new Request.Builder()
                .url("https://final-project-team-1-section-1.herokuapp.com/user/settings")
                .post(body)
                .build();
        try {
            //Toast.makeText(this, body.toString(), Toast.LENGTH_LONG).show();
            Response response = client.newCall(request).execute();
            JSONObject responseData = new JSONObject(response.body().string());
            //Toast.makeText(getContext(),responseData.toString(), Toast.LENGTH_LONG).show();
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
                Toast.makeText(getContext(), "Unknown error, Please try again.", Toast.LENGTH_LONG).show();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());
            //show the error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                // show the other error messages
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }



}
