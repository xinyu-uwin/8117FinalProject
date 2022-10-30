package com.example.a8117finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends Activity implements Validator.ValidationListener{


    @BindView(R.id.password)
    @Length(min = 8, max = 18)
    EditText password;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);



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
                validator.validate();

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

