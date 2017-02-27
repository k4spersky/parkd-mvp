package com.java.user.parkd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;


public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.java.user.parkd.R.layout.activity_register_ua);
        //The following code is used for assigning variables to the controls located on the login page
        final EditText etUsername = (EditText) findViewById(com.java.user.parkd.R.id.etUsername);
        final EditText etAge = (EditText) findViewById(com.java.user.parkd.R.id.etAge);
        final TextView welcomeMessage = (TextView) findViewById(com.java.user.parkd.R.id.tvWelcomeMessage);
        //Get data sent from login page
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        int age = intent.getIntExtra("age", -1);
        //Sets controls
        String message = name + WELCOME_MSG;
        welcomeMessage.setText(message);
        etUsername.setText(username);
        etAge.setText(age + "");
    }

    // constants declared below
    protected static final String WELCOME_MSG = " welcome to your user area." ;
}
