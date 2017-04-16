package com.java.user.parkd;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Paul on 14/04/2017.
 */

public class AddPaymentActivity extends AppCompatActivity{
    Button add;
    ImageButton cam;
    Toolbar tb1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        add = (Button) findViewById(R.id.addCardBT);
        cam= (ImageButton) findViewById(R.id.cameraImage);
        tb1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb1);
        getSupportActionBar().setTitle("Add Payment Card");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText etCard = (EditText) findViewById(R.id.editCardNumber);
        final EditText etExpire = (EditText) findViewById(R.id.editExpireDate);
        final EditText etCvv = (EditText) findViewById(R.id.editCVV);

        cam.setColorFilter(this.getResources().getColor(R.color.google_blue));

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String card_number = etCard.getText().toString();
                final String expire_date = etExpire.getText().toString();
                final String cvv = etCvv.getText().toString();
                final String email = getEmail();
                if (emptyData(card_number, expire_date, cvv))
                {
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            //Receives response from the php
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success = true) {
                                //Opens up login form if successful
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddPaymentActivity.this);
                                builder.setMessage("Card Added")
                                        .create()
                                        .show();

                            }else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddPaymentActivity.this);
                                builder.setMessage("Unable to add card at this time. Please try again.")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();

                            } }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                // Sends request to the php
                AddPaymentRequest addPaymentRequest = new AddPaymentRequest(card_number, expire_date, cvv, email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddPaymentActivity.this);
                queue.add(addPaymentRequest);


            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        return super.onOptionsItemSelected(item);
    }

    private String getEmail()
    {
        SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        return sharedpref.getString("name", "").toString();

    }
    //Checks if there are any empty strings
    public boolean emptyData(String cardnumber, String expire, String cvv) {
        if (cardnumber.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPaymentActivity.this);
            builder.setMessage("Please enter your card number")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        } else {
        }
        if (expire.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPaymentActivity.this);
            builder.setMessage("Please the expire date for the card.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        } else {
        }
        if (cvv.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPaymentActivity.this);
            builder.setMessage("Please the cvv number for the card.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        } else {
        }
        return false;
    }
}