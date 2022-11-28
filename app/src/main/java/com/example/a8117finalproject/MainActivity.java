package com.example.a8117finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
//main activity
    public static final String FILE_NAME = "userSP";
    SharedPreferences userSP;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userSP = this.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = userSP.edit();
        int isLogged = userSP.getInt("is_logged_in", 0);
        if(isLogged == 1)
        {
            Intent it = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(it);
        }
        else
        {
            Intent it = new Intent(MainActivity.this, Login1Activity.class);
            startActivity(it);
        }
    }
}