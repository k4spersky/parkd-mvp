package com.example.user.parkd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //The following code is used for assigning variables to the controls located on the register page
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        final Button bRegister = (Button) findViewById(R.id.bRegister);
    }
}
