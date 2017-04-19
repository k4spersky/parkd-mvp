package com.java.user.parkd;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Paul on 19/04/2017.
 */

public class UserEditActivity extends AppCompatActivity {
    Toolbar tb1;
    EditText fName;
    EditText lName;
    EditText pNumber;
    Button save;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        //The following code is used for assigning variables to the controls located on the login page
         fName = (EditText) findViewById(R.id.editFN);
         lName = (EditText) findViewById(R.id.editLN);
         pNumber = (EditText) findViewById(R.id.editPN);
        save = (Button) findViewById(R.id.saveData);

        tb1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb1);
        getSupportActionBar().setTitle("Edit Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String s = getIntent().getStringExtra("firstname");
        String t = getIntent().getStringExtra("lastname");
        String p = getIntent().getStringExtra("number");
        fName.setText(s);
        lName.setText(t);
        if(p.equals("Add Phone Number")) {
            pNumber.setText("");
        }else{
            pNumber.setText(p);
        }
        fName.setSelection(fName.getText().length());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        return super.onOptionsItemSelected(item);
    }

    private void postData()
    {
        String fn = fName.getText().toString();
        String ln = lName.getText().toString();
        String pn = pNumber.getText().toString();
        if (emptyData(fn, ln, pn))
        {return;}
        if(checkNumber(pn))
        {return;}
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
                        Toast.makeText(UserEditActivity.this, "Changes saved", Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        //Alerts the user of failure and asks for them retry
                        Toast.makeText(UserEditActivity.this, "Unable to update data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        useremail = sharedpref.getString("email", "");
        UserEditRequest ua = new UserEditRequest(useremail, fn, ln, pn, responseListener);
        RequestQueue queue = Volley.newRequestQueue(UserEditActivity.this);
        queue.add(ua);
    }
    private static String useremail;

    private boolean emptyData(String first, String last, String number)
    {
        if (first.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserEditActivity.this);
            builder.setMessage("Please enter your first name.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }else
        {
        }

        if (last.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserEditActivity.this);
            builder.setMessage("Please enter your last name.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }else
        {
        }

        return false;
    }

    private boolean checkNumber(String num)
    {
        if(num.length() != 11)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserEditActivity.this);
            builder.setMessage("The contact number you entered is not the correct length. Please enter an 11 digit contact number")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }
        return false;
    }
}
