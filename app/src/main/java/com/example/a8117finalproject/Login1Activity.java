package com.example.a8117finalproject;

import android.app.Activity;
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


public class Login1Activity extends Activity implements Validator.ValidationListener{
    EditText etEmail;

    Button next;
    TextView etTest;

    String username;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                 StrictMode.setThreadPolicy(policy);
            }


        //initial validator
        Validator validator = new Validator(this);
        validator.setValidationListener(this);

        //data initial
        etEmail = findViewById(R.id.email);
        next = findViewById(R.id.next);


        //Complete button
        next.setOnClickListener(new View.OnClickListener() {
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
        RequestBody body = RequestBody.create(mediaType, "{\n    \"username\": \"vegesna00@gmail.com\"\n}");
        //RequestBody body = RequestBody.create(mediaType, buildRequestBody());

        Request request = new Request.Builder()
                .url("https://final-project-team-1-section-1.herokuapp.com/user/login")
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
        String requestBody = "{\n    \"username\": \""+ username + "\"\n}";
        return requestBody;
    }

    /**
     * get the from content
     */
    private void getContentFromForm() {
        username = etEmail.getText().toString().trim();

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

