package com.example.a8117finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Login2Activity extends Activity implements Validator.ValidationListener{

    //initial the elements needed
    Button back;
    TextView etEmail;
    @Order(1)
    @NotEmpty
    @Length(min=8,max = 16, message = "Length should be 8-16")
    EditText etPwd;
    Button login;
    //TextView etTest;
    String username;
    String pwd;

    //initial the shared preference
    public static final String FILE_NAME = "userSP";
    SharedPreferences userSP;
    SharedPreferences.Editor editor;

    /**
     * create the page
     * initial the validator, data needed
     * initial the SP
     * including the back and log in button logic
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                 StrictMode.setThreadPolicy(policy);
            }

        //initial validator
        Validator validator = new Validator(this);
        validator.setValidationListener(this);
        //data initial
        back = findViewById(R.id.back);
        etEmail = findViewById(R.id.email);
        etPwd = findViewById(R.id.password);
        login = findViewById(R.id.login);

        /**
         * fill the username from userSP
         */
        userSP = this.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE+MODE_APPEND);
        editor = userSP.edit();
        username = userSP.getString("username","");
        etEmail.setText(username);


        /**
         * the log in button logic
         * when clicked, validate the password
         */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
                //submitForm();

            }
        });


        /**
         * the back button logic
         * when clicked, jump back to the log in page 1
         */
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * @Yang Wang
                 * here should jump back to the log in page 1
                 */

                Intent go = new Intent(Login2Activity.this, Login1Activity.class);
                startActivity(go);
            }
        });

    }


    /**
     * submit the form to server and react according to the response
     * if status is 200, go to the homepage
     * if status is 401, password invalid
     * if status is others, toast error and ask for retry
     */
    private void submitForm() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        String requestBody = buildRequestBody();
        //RequestBody body = RequestBody.create(mediaType, "{\n    \"username\": \"vegesna00@gmail.com\",\n    \"password\":\"123\"\n}");
        RequestBody body = RequestBody.create(mediaType, buildRequestBody());

        Request request = new Request.Builder()
                .url("https://final-project-team-1-section-1.herokuapp.com/user/login")
                .post(body)
                .build();
        try {
            //Toast.makeText(this, body.toString(), Toast.LENGTH_LONG).show();
            Response response = client.newCall(request).execute();
            JSONObject responseData = new JSONObject(response.body().string());
           //etTest = findViewById(R.id.test);
           //etTest.setText(responseData.toString());

            String status = responseData.getString("status");
            if ("200".equals(status)) {
                //save the login status to userSP
                editor.putInt("is_logged_in",1);
                editor.commit();
                /**
                 * @Yang Wang
                 * Here should jump to the homepage
                 */

                Intent go = new Intent(Login2Activity.this, HomeActivity.class);
                startActivity(go);

            } else if ("401".equals(status)){
                Toast.makeText(this, "Invalid password, Please try again.", Toast.LENGTH_LONG).show();
                etPwd.setText("");
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
     * build request body content
     * @return the request body text
     */
    private String buildRequestBody() {
        String requestBody = "{\n    \"username\": \""+username+"\",\n    \"password\":\""+ pwd +"\"\n}";
        return requestBody;
    }


    /**
     * validation logic
     * if successful, get the contents from the form, and submit them to server
     * if not, give the tip of errors
     */
    @Override
    public void onValidationSucceeded() {
        pwd = etPwd.getText().toString().trim();
        username = etEmail.getText().toString().trim();
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

