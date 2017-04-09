package com.java.user.parkd;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;



public class UserAreaActivity extends AppCompatActivity

{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.java.user.parkd.R.layout.activity_register_ua);
        //The following code is used for assigning variables to the controls located on the login page
        final TextView logOut = (TextView) findViewById(R.id.logout);
        final TextView user = (TextView) findViewById(R.id.accName);


        logOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //This Listener listens for click on the register text link
                //The following code is standard for running a new activity, in this case it opens the register form
                removeData();
                //user.setText("");
                Intent intent = new Intent(UserAreaActivity.this, LoginActivity.class);
                //login = true;
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                finishAffinity();
                startActivity(intent);
                finish();

            }
        });


    }

    public void removeData()
    {
        SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.remove("name");
        switchStatus = sharedpref.getString("Saveemail", "");
        if(switchStatus.matches("No"))
        {
            editor.remove("email");
        }
        editor.apply();

        Toast.makeText(this, "Logged Out", Toast.LENGTH_LONG).show();

    }



   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.actionbar1, menu);
        return super.onCreateOptionsMenu(menu);
    }*/


    private String name;
    private String switchStatus;

    private class Toolbar {
    }
}
