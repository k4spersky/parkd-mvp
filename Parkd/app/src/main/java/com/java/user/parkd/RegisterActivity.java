package com.java.user.parkd;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;



public class RegisterActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //The following code is used for assigning variables to the controls located on the register page


        final EditText etEmail = (EditText) findViewById(R.id.etEmaily);
        final EditText etFirstName = (EditText) findViewById(R.id.etFirstName);
        final EditText etLastName = (EditText) findViewById(R.id.etLastName);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bRegister = (Button) findViewById(R.id.bRegister);
        final TextView termsLink = (TextView) findViewById(com.java.user.parkd.R.id.informtextView);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Collects values from text boxes and put them into variables
                final String firstName = etFirstName.getText().toString();
                final String password = etPassword.getText().toString();
                final String email = etEmail.getText().toString();
                final String lastName = etLastName.getText().toString();

                //Check if passwords match
                //check if email is valid
                if (emptyData(firstName, lastName, email, password ))
                {
                    return;
                }
                if (isEmailValid(email)) {

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Email is not valid. Please enter a valid email address.")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                    return;
                }
                //check if passwords are right format
                if (validate(password))
                {}
                else{AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Password is not valid. Make sure it contains 6 to 20 characters with at least one digit, one upper case letter, one lower case letter. ")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                    return;}

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            //Receives response from the php
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success = true) {
                                //Opens up login form if successful
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Account Created")
                                        .create()
                                        .show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }else
                            {
                                String resp = jsonResponse.getString("text");
                                if(resp.equals("Email Exists"))
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Email has already been registered. Please choose another.")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                                else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Registration Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();}
                        } }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                // Sends request to the php
                RegisterRequest registerRequest = new RegisterRequest(firstName, lastName, email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);


            }
        });
        //Terms and Condtions popup
        termsLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
               startActivity(new Intent(RegisterActivity.this, PopTermsCon.class));



            }});
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
    //Checks if there are any empty strings
    public boolean emptyData(String firstName, String lastName, String email, String Pass)
    {
        if (firstName.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Please enter your first name.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }else
        {
        }
        if (lastName.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Please enter your last name.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }else
        {
        }
        if (email.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Please enter an email address.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }else
        {
        }

        if (Pass.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Please enter a password.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }else
        {
        }
        return false;
    }

    /**
     * Validate password with regular expression
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public boolean validate(final String password){
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        return super.onOptionsItemSelected(item);
    }

    private Pattern pattern;
    private Matcher matcher;
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$";

}
