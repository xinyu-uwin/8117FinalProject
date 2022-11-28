package com.example.a8117finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.*;

import com.mobsandgeeks.saripaar.*;
import com.mobsandgeeks.saripaar.annotation.*;
import org.json.*;
import java.io.IOException;
import java.util.List;
import okhttp3.*;

public class AddroomActivity extends Activity implements Validator.ValidationListener {

    //initial the elements needed
    Button back;
    @Order(1)
    @NotEmpty
    @Length(min = 3, max = 15, message = "Length should be 3-15.")
    EditText etRoomname;
    @Order(2)
    @NotEmpty
    @Min(value = 16, message = "Should between 16-30")
    @Max(value = 30, message = "Should between 16-30")
    EditText etTemp;
    @Order(3)
    @NotEmpty
    @Length(min = 5, max = 5, message = "Format should be \"HH:MM\"")
    @Pattern(regex = "\\d\\d:\\d\\d", message = "Format should be \"HH:MM\"")
    EditText etWeekdayAlarm;
    @Order(4)
    @NotEmpty
    @Length(min = 5, max = 5, message = "Format should be \"HH:MM\"")
    @Pattern(regex = "\\d\\d:\\d\\d", message = "Format should be \"HH:MM\"")
    EditText etWeekendAlarm;
    Button addRoom;
    Button cancel;
    //TextView etTest;

    public String username;
    String roomName;
    String temp;
    String weekdayAlarm;
    String weekendAlarm;

    //initial the shared preference
    public static final String FILE_NAME = "userSP";
    SharedPreferences userSP;
    SharedPreferences.Editor editor;

    /**
     * create the page
     * initial the validator, data needed
     * initial the SP
     * including the back, cancel, and add room button logic
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addroom);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        //initial validator
        Validator validator = new Validator(this);
        validator.setValidationListener(this);

        //data initial
        back = findViewById(R.id.back);
        etRoomname = findViewById(R.id.roomname);
        etTemp = findViewById(R.id.temp);
        etWeekdayAlarm = findViewById(R.id.weekday_alarm);
        etWeekendAlarm = findViewById(R.id.weekend_alarm);
        addRoom = findViewById(R.id.addroom);
        cancel = findViewById(R.id.cancel);

        /**
         * fill the username from userSP
         */
        userSP = this.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE + MODE_APPEND);
        editor = userSP.edit();
        username = userSP.getString("username", "");

        //addroom button
        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validator.validate();
                //submitForm();
            }
        });

        /**
         * the back button logic
         * when clicked, jump back to settings page
         */
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * @Yang Wang
                 * here should jump back to settings page
                 */
                Intent go = new Intent(AddroomActivity.this, SettingsActivity.class);
                startActivity(go);
            }
        });

        /**
         * the cancel button logic
         * when clicked, jump back to settings page
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * @Yang Wang
                 * here should jump back to settings page
                 */
                Intent go = new Intent(AddroomActivity.this, SettingsActivity.class);
                startActivity(go);
            }
        });
    }

    /**
     * submit the form to server and react according to the response
     * if status is 200, go to the settings page
     * if status is 400, ask for checking
     * if status is others, toast error and ask for retry
     */
    private void submitForm() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        String requestBody = buildRequestBody();
        //RequestBody body = RequestBody.create(mediaType, "{\n    \"username\": \"sam@gmail.com\",\n    \"password\": \"123\",\n    \"location\": \"windsor,canada\",\n    \"alarm_time_weekday\": \"07:00\",\n    \"alarm_time_weekend\": \"09:00\",\n    \"preferred_temp\": 22,\n    \"name\": \"nav\"\n}");
        RequestBody body = RequestBody.create(mediaType, buildRequestBody());

        Request request = new Request.Builder()
                .url("https://final-project-team-1-section-1.herokuapp.com/user/room/add")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject responseData = new JSONObject(response.body().string());
            //etTest = findViewById(R.id.test);
            //etTest.setText(responseData.toString());

            //responseData.getJSONObject();
            //responseData.getJSONObject();
            String status = responseData.getString("status");
            if ("200".equals(status)) {
                //save the login status to userSP
                editor.putInt("is_logged_in",1);
                editor.commit();
                Toast.makeText(this, "Add room successful.", Toast.LENGTH_LONG).show();
                /**
                 * @Yang Wang
                 * Here should jump to the settings page
                 */
                Intent go = new Intent(AddroomActivity.this, SettingsActivity.class);
                startActivity(go);

            } else if ("400".equals(status)){
                Toast.makeText(this, "User existed or invalid information, Please try again.", Toast.LENGTH_LONG).show();
            }
            else {
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
    private String buildRequestBody() {
        String requestBody = "{\n    \"username\": \""+username+"\",\n    \"room_name\": \""+roomName+"\",\n    \"alarm_time_weekday\": \""+weekdayAlarm+"\",\n    \"alarm_time_weekend\": \""+weekendAlarm+"\",\n    \"preferred_temp\": "+temp+"\n}";
        return requestBody;
    }

    /**
     * get the from content
     */
    private void getContentFromForm() {
        roomName = etRoomname.getText().toString().trim();
        temp = etTemp.getText().toString().trim();
        weekdayAlarm = etWeekdayAlarm.getText().toString().trim();
        weekendAlarm = etWeekendAlarm.getText().toString().trim();
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

}



