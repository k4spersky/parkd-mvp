package com.java.user.parkd;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.java.user.parkd.R.layout.activity_login);
        //code to stop auto keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //The following code is used for assigning variables to the controls located on the login page
        final EditText etEmail = (EditText) findViewById(com.java.user.parkd.R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(com.java.user.parkd.R.id.etPassword);
        final Button bLogin = (Button) findViewById(com.java.user.parkd.R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(com.java.user.parkd.R.id.tvRegisterHere);

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
                final String email = etEmail.getText().toString();
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

                                String name = firstname + " " + lastname;
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("username", name); // Storing string
                                editor.commit();
                                //SaveSharedPreference.setUserName(LoginActivity.this, name);

                                Intent intent = new Intent(LoginActivity.this, Fragment3Activity.class);
                                //Allows for data to be sent from one form to another
                                //intent.putExtra("firstname", firstname);
                                //intent.putExtra("lastname", lastname);
                                //intent.putExtra("email", email);
                                //intent.putExtra("password", password);
                                //intent.putExtra("age", age);
                                LoginActivity.this.startActivity(intent);
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
}
