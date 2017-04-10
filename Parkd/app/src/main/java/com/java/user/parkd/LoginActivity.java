package com.java.user.parkd;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.content.Intent;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.java.user.parkd.R.id.etEmail;
import static com.java.user.parkd.R.id.switch1;

public class LoginActivity extends AppCompatActivity {
        Switch emailSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.java.user.parkd.R.layout.activity_login);
        //code to stop auto keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //The following code is used for assigning variables to the controls located on the login page
        final EditText Email = (EditText) findViewById(etEmail);
        final EditText etPassword = (EditText) findViewById(com.java.user.parkd.R.id.etPassword);
        final Button bLogin = (Button) findViewById(com.java.user.parkd.R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(com.java.user.parkd.R.id.tvRegisterHere);
        final TextView user = (TextView) findViewById(R.id.accName);
         emailSwitch = (Switch) findViewById(R.id.switch1);
        getData();
        if (name.length()==0)
        {
            if(switchStatus.toString().matches("Yes"))
            {
                //Set controls
                Email.setText(useremail);
                emailSwitch.setChecked(true);

            }
            else {//DO nothing
                 }


        }else{
            //send to login page
            Intent LoginIntent = new Intent(LoginActivity.this, MainActivity.class);
            LoginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            //LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(LoginIntent);
            finish();
        }

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This Listener listens for click on the register text link
                //The following code is standard for running a new activity, in this case it opens the register form
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Collects values from text boxes and put them into variables
                final String email = Email.getText().toString();
                useremail = email;
                final String password = etPassword.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>()
                {

                    @Override
                    public void onResponse(String response) {

                        try {
                            //Receives response from the php
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success)
                            {

                                //Opens up userActivity form if successful
                                String firstname = jsonResponse.getString("firstname");
                                String lastname = jsonResponse.getString("lastname");
                                String email = jsonResponse.getString("email");
                                //int age = jsonResponse.getInt("age");

                                name = firstname + " " + lastname;
                                saveinfo();
                                //user.setText(name);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                //login = true;
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                                finish();


                            }else{
                                //Alerts the user of failure and asks for them retry
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // Sends request to the php
                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
    public void saveinfo()
    {
        //saves users name and email
            SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpref.edit();
            editor.putString("name", name);
        if(emailSwitch.isChecked())
        {
            editor.putString("Saveemail", "Yes");
        }else
            {
                editor.putString("Saveemail", "No");
            }
                editor.putString("email", useremail);
                editor.apply();



    }
    private static String name;
    private static String useremail;
    private static String switchStatus;
    public void getData()

    {
        SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        name = sharedpref.getString("name", "");
        useremail = sharedpref.getString("email", "");
        switchStatus = sharedpref.getString("Saveemail", "");


    }


    //public static boolean login = false;
}
