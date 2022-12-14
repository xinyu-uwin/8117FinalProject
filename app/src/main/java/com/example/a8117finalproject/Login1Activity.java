package com.example.a8117finalproject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Order;

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


    //initial the elements needed
    @Order(1)
    @Email(message = "Please use a valid email")
    EditText etEmail;
    Button next;
    //TextView etTest;
    String username;
    TextView etAgreement;

    //initial the shared preference
    public static final String FILE_NAME = "userSP";
    SharedPreferences userSP;
    SharedPreferences.Editor editor;




    /**
     * create the page
     * initial the validator, data needed
     * initial the SP
     * including the next button logic
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                 StrictMode.setThreadPolicy(policy);
            }

        setSpan();

        //initial validator
        Validator validator = new Validator(this);
        validator.setValidationListener(this);
        //data initial
        etEmail = findViewById(R.id.email);
        next = findViewById(R.id.next);
        etAgreement = findViewById(R.id.agreement);

        //initial the SP
        userSP = this.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE+MODE_APPEND);
        editor = userSP.edit();
        //editor.putInt("is_logged_in", 0);
        //editor.putString("username", "");

        /**
         * the next button logic
         * when clicked, validate the e-mail
         */
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });



    }

    /**
     * submit the form to server and react according to the response
     * if status is 200, go to the log in process
     * if status is 404, go to the sign up process
     * if status is others, toast error and ask for retry
     */
    private void submitForm() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        String requestBody = buildRequestBody();
        //RequestBody body = RequestBody.create(mediaType, "{\n    \"username\": \"vegesna00@gmail.com\"\n}");
        RequestBody body = RequestBody.create(mediaType, buildRequestBody());

        Request request = new Request.Builder()
                .url("https://final-project-team-1-section-1.herokuapp.com/user/login")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject responseData = new JSONObject(response.body().string());
           //etTest = findViewById(R.id.test);
           //etTest.setText(responseData.toString());
            String status = responseData.getString("status");
            //Toast.makeText(this, status, Toast.LENGTH_LONG).show();
            if ("200".equals(status)) {
                //save the username to userSP
                editor.putString("username",etEmail.getText().toString());
                editor.commit();
                /**
                 * @Yang Wang
                 * Here should jump to the log in page 2
                 */

                Intent go = new Intent(Login1Activity.this, Login2Activity.class);
                startActivity(go);

            } else if ("404".equals(status)){
                //save the username to userSP
                editor.putString("username",etEmail.getText().toString());
                editor.commit();
                /**
                 * @Yang Wang
                 * Here should jump to the sign up page
                 */
                Intent go = new Intent(Login1Activity.this, SignUpActivity.class);
                startActivity(go);
            }
            else {
                Toast.makeText(this, "Unknown error, Please try again.", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {

            //Toast.makeText(this, "200", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /**
     * build request body content
     * @return the request body text
     */
    private String buildRequestBody() {
        String requestBody = "{\n    \"username\": \""+ username + "\"\n}";
        return requestBody;
    }


    /**
     * validation logic
     * if successful, get the contents from the form, and submit them to server
     * if not, give the tip of errors
     */
    @Override
    public void onValidationSucceeded() {
        //Toast.makeText(this, "Validate success???", Toast.LENGTH_LONG).show();
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

    private void setSpan() {


        SpannableString spannableString = new SpannableString("Continue means agree with the Terms&Conditions and Privacy Policy");

        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Uri uri = Uri.parse("https://final-project-team-1-section-1.herokuapp.com/privacynotice");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Taking you to the TC and Privacy Policy Page", Toast.LENGTH_LONG).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);// ????????????
                ds.setUnderlineText(true); // ??????????????????
            }
        }, 30, 65, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        /*ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#009ad6"));
        // 0 , 8 ???????????????????????????index ????????????
        spannableString.setSpan(colorSpan, 30, 65, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "User existed or invalid information, Please try again.",
                        Toast.LENGTH_LONG).show();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);//?????????true????????????????????????????????????
            }
        }, 30, 65,  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
*/
           /* SpannableString spannableString = new SpannableString("Agree with the Terms&Conditions and Privacy Policy");
            //?????????????????????
            //    spannableString.setSpan(new UnderlineSpan(), 17, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //???????????????????????????
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);//?????????true????????????????????????????????????
                }
            }, 15, 52,  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //????????????????????????
            spannableString.setSpan(new ForegroundColorSpan(1), 17, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


            spannableString.setSpan(new ForegroundColorSpan(1), 24, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/

        ((TextView)findViewById(R.id.agreement)).setText(spannableString);
            //etAgreement.setText(spannableString);
        ((TextView)findViewById(R.id.agreement)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView)findViewById(R.id.agreement)).setHighlightColor(Color.TRANSPARENT);

        }


}

