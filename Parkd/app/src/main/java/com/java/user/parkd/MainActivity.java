package com.java.user.parkd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.java.user.parkd.R.layout.activity_main);

        final Button bLogin = (Button) findViewById(com.java.user.parkd.R.id.bLoginButton);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This Listener listens for click on the login button
                //The login button is located here just for testng purposes
                //The following code is standard for running a new activity
                Intent LoginIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(LoginIntent);
            }
        });
    }
}
