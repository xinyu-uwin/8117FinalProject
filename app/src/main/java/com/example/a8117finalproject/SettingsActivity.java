package com.example.a8117finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class SettingsActivity extends Activity implements Validator.ValidationListener {
    TextView etEmail;
    @Length(min = 8, max = 18)
    @NotEmpty
    EditText etPwd;
    @NotEmpty
    @Length(min = 3, max = 15)
    EditText etUsername;
    @NotEmpty
    EditText etCity;
    @NotEmpty
    @Length(min = 3, max = 15)
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
        setContentView(R.layout.activity_settings);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }
}
