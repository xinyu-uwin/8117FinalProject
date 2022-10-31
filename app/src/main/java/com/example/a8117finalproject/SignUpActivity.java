package com.example.a8117finalproject;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SignUpActivity extends Activity implements Validator.ValidationListener{







    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);




        //initial validator
        Validator validator = new Validator(this);
        validator.setValidationListener(this);

        //data initial
        String email = findViewById(R.id.email).toString();
        String password = findViewById(R.id.password).toString();
        String username = findViewById(R.id.username).toString();
        String city = findViewById(R.id.city).toString();
        String roomName = findViewById(R.id.roomname).toString();
        String temp = findViewById(R.id.temp).toString();
        String weekdayAlarm = findViewById(R.id.weekday_alarm).toString();
        String weekendAlarm = findViewById(R.id.weekend_alarm).toString();



        //Complete button
        //第一步：关联控件
        Button complete= findViewById(R.id.sign_up_complete);
        //第二步：实现接口
        View.OnClickListener add = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //响应事件
                //validator.validate();
               OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("text/plain");
                RequestBody body = RequestBody.create(mediaType, "{\n    \"username\": \"sam@gmail.com\",\n    \"password\": \"123\",\n    \"location\": \"windsor,canada\",\n    \"alarm_time_weekday\": \"07:00\",\n    \"alarm_time_weekend\": \"09:00\",\n    \"preferred_temp\": 22,\n    \"name\": \"nav\"\n}");
                Request request = new Request.Builder()
                        .url("https://final-project-team-1-section-1.herokuapp.com/user/register")
                        .method("POST", body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        //第三步：接口绑定控件
        complete.setOnClickListener(add);



    }


    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "成功了！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        Toast.makeText(this, "失败了！", Toast.LENGTH_LONG).show();
    }
}

