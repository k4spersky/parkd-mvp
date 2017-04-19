package com.java.user.parkd;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class UserAreaActivity extends AppCompatActivity

{
    Toolbar tb1;
    TextView userFirstName;
    TextView userLastName;
    TextView userPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.java.user.parkd.R.layout.activity_register_ua);
        //The following code is used for assigning variables to the controls located on the login page
        final TextView logOut = (TextView) findViewById(R.id.logout);
        final TextView user = (TextView) findViewById(R.id.accName);
        userFirstName = (TextView) findViewById(R.id.fName);
        userLastName = (TextView) findViewById(R.id.lName);
        userPhoneNumber = (TextView) findViewById(R.id.phoneNumber);

        tb1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb1);
        getSupportActionBar().setTitle("My Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadData();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
                break;
            case R.id.action_edit:
                createEdit();
                break;



        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData()
    {
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
                        String number = jsonResponse.getString("phone");
                        userFirstName.setText(firstname);
                        userLastName.setText(lastname);
                        if(number.equals("")) {
                            userPhoneNumber.setText("Add Phone Number");
                        }else {
                         userPhoneNumber.setText(number);
                        }
                    }else{
                        //Alerts the user of failure and asks for them retry
                        Toast.makeText(UserAreaActivity.this, "Unable to load data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        useremail = sharedpref.getString("email", "");
        UserAreaRequest ua = new UserAreaRequest(useremail, responseListener);
        RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
        queue.add(ua);
    }
    private static String useremail;

    public void onRestart(){
              super.onRestart();
        loadData();
    }

     public void onResume(){
     super.onResume();
            loadData();
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_item, menu);
        return true;
    }



    private void createEdit()
    {
        Intent settings = new Intent(UserAreaActivity.this, UserEditActivity.class);
        settings.putExtra("firstname", userFirstName.getText());
        settings.putExtra("lastname", userLastName.getText());
        settings.putExtra("number", userPhoneNumber.getText());
        UserAreaActivity.this.startActivity(settings);

    }

}
