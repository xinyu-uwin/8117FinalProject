package com.example.a8117finalproject;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SignUpActivity extends Activity implements Validator.ValidationListener{
    TextView etEmail;
    @Length(min=8,max=18)
    @NotEmpty
    EditText etPwd;
    @NotEmpty
    @Length(min=3,max=15)
    EditText etUsername;
    @NotEmpty
    EditText etCity;
    @NotEmpty
    @Length(min=3,max=15)
    EditText etRoomname;
    @NotEmpty
    EditText etTemp;
    @NotEmpty
    EditText etWeekdayAlarm;
    @NotEmpty
    EditText etWeekendAlarm;
    Button complete;
    TextView etTest;

    String pwd;
    String username;
    String city;
    String roomName;
    String temp;
    String weekdayAlarm;
    String weekendAlarm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                 StrictMode.setThreadPolicy(policy);
            }


        //initial validator
        Validator validator = new Validator(this);
        validator.setValidationListener(this);

        //data initial
        etEmail = findViewById(R.id.email);
        etPwd = findViewById(R.id.password);
        etUsername = findViewById(R.id.username);
        etCity = findViewById(R.id.city);
        etRoomname = findViewById(R.id.roomname);
        etTemp = findViewById(R.id.temp);
        etWeekdayAlarm = findViewById(R.id.weekday_alarm);
        etWeekendAlarm = findViewById(R.id.weekend_alarm);
        complete = findViewById(R.id.sign_up_complete);


        //Complete button
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validator.validate();
                submitForm();
            }
        });

    }
    /**
     * submit the form to server
     */
    private void submitForm() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        String requestBody = buildRequestBody();
        RequestBody body = RequestBody.create(mediaType, "{\n    \"username\": \"vegesna00@gmail.com\",\n    \"password\": \"123\",\n    \"name\": \"nav\",\n    \"location\": \"windsor,canada\",\n    \"room_name\": \"bedroom-1\",\n    \"alarm_time_weekday\": \"07:00\",\n    \"alarm_time_weekend\": \"19:40\",\n    \"preferred_temp\": 22\n}");
        //RequestBody body = RequestBody.create(mediaType, buildRequestBody());

        Request request = new Request.Builder()
                .url("https://final-project-team-1-section-1.herokuapp.com/user/register")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject responseData = new JSONObject(response.body().string());
           etTest = findViewById(R.id.test);
           etTest.setText(responseData.toString());

            //responseData.getJSONObject();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * initial the request body
     */
    private String buildRequestBody() {
        String requestBody = "{\n    \"username\": \""+ username + "\",\n    \"password\": \""+ pwd +"\",\n    \"location\": \"windsor,canada\",\n    \"alarm_time_weekday\": \"07:00\",\n    \"alarm_time_weekend\": \"09:00\",\n    \"preferred_temp\": 22,\n    \"name\": \"nav\"\n}";
        return requestBody;
    }

    /**
     * get the from content
     */
    private void getContentFromForm() {
        pwd = etPwd.getText().toString().trim();
        username = etUsername.getText().toString().trim();
        city = etCity.getText().toString().trim();
        roomName = etRoomname.getText().toString().trim();
        temp = etTemp.getText().toString().trim();
        weekdayAlarm = etWeekdayAlarm.getText().toString().trim();
        weekendAlarm = etWeekendAlarm.getText().toString().trim();
    }


    @Override
    public void onValidationSucceeded() {
        //Toast.makeText(this, "Validate success！", Toast.LENGTH_LONG).show();
        getContentFromForm();
        submitForm();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        Toast.makeText(this, "Please check the form！", Toast.LENGTH_LONG).show();
    }
}

