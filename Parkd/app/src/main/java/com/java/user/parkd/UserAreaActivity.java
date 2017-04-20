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

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserAreaActivity extends AppCompatActivity

{
    Toolbar tb1;
    EditText userFirstName;
    EditText userPhoneNumber;
    EditText userEmail;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.java.user.parkd.R.layout.activity_register_ua);
        //The following code is used for assigning variables to the controls located on the login page

        userFirstName = (EditText) findViewById(R.id.editFN);
        userPhoneNumber = (EditText) findViewById(R.id.editpN);
        userEmail = (EditText) findViewById(R.id.editEmail);
        save = (Button) findViewById(R.id.saveData);

        tb1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb1);
        getSupportActionBar().setTitle("My Account Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadData();
        userFirstName.setSelection(userFirstName.getText().length());
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                postData();
            }
        });

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
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
                        String email = jsonResponse.getString("email");
                        userFirstName.setText(firstname + " " + lastname);
                        userEmail.setText(email);
                        userPhoneNumber.setText(number);

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

    private boolean emptyData(String first, String email)
    {
        if (first.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserAreaActivity.this);
            builder.setMessage("Please enter your first name.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }else
        {
        }

        if (email.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserAreaActivity.this);
            builder.setMessage("Please enter your email address.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }else
        {
        }

        return false;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private void postData()
    {
       String name = userFirstName.getText().toString();
        String[] splited = name.split("\\s+");
        String pn = userPhoneNumber.getText().toString();
        String em = userEmail.getText().toString();
        String ln = splited[1];
        String fn = splited[0];

        if (emptyData(fn, em))
        {return;}
        if(isEmailValid(em) == false)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserAreaActivity.this);
            builder.setMessage("Email Address is not valid.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return;
        }
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
                        SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpref.edit();
                        editor.putString("email", userEmail.getText().toString());
                        editor.putString("name", userFirstName.getText().toString());
                        editor.apply();
                        Toast.makeText(UserAreaActivity.this, "Changes saved", Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        //Alerts the user of failure and asks for them retry
                        Toast.makeText(UserAreaActivity.this, "Unable to update data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        useremail = sharedpref.getString("email", "");
        UserEditRequest ua = new UserEditRequest(useremail, fn, ln, pn, em, responseListener);
        RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
        queue.add(ua);
    }


}
