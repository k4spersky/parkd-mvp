package com.java.user.parkd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;


/**
 * desc
 */
public class AddPrivateSpaceActivity extends AppCompatActivity {
    Toolbar tb1;
    Button addSpaceBtn;
    String payment_option;
    String email;
    android.support.design.widget.TextInputEditText pri;
    EditText add;
    EditText pos;
    EditText la;
    EditText ln;
    EditText loc;
    android.support.design.widget.TextInputEditText de;
    String price;
    String lat;
    String lng;
    String postco;
    String addres;
    String city;
    String desc;
    ProgressDialog ringProgressDialog;


    private CharSequence[] payments={};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_space);
        tb1 = (Toolbar) findViewById(R.id.toolbar_space);
        setSupportActionBar(tb1);
        getSupportActionBar().setTitle("Add New Private Space");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addSpaceBtn = (Button) findViewById(R.id.addSpaceBtn);

        pri = (android.support.design.widget.TextInputEditText) findViewById(R.id.space_charge);
        add = (EditText) findViewById(R.id.space_address);
        pos = (EditText) findViewById(R.id.space_postcode);
        la = (EditText) findViewById(R.id.space_latitude);
        ln = (EditText) findViewById(R.id.longitude);
        de = (android.support.design.widget.TextInputEditText) findViewById(R.id.space_descr);
        loc = (EditText) findViewById(R.id.space_city);

        // blue spinner (city picker)
        Spinner spaceSpinner = (Spinner) findViewById(R.id.space_type_spinner);
        String[] spaceTypes = new String[] {"Private", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, spaceTypes);

        spaceSpinner.setAdapter(adapter);
        spaceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        addSpaceBtn.setOnClickListener(view -> {
            //put data into variables
            getData();
            //check if any data is missing
            if (checkData())
            {return;
            }
            SharedPreferences sharedpref = AddPrivateSpaceActivity.this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            email = sharedpref.getString("email", "");
            //Check if the have payment options
            checkCard(email);


        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        return super.onOptionsItemSelected(item);
    }

    public boolean checkData()
    {
        if (price.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPrivateSpaceActivity.this);
            builder.setMessage("Please enter the price you wish to charge per hour.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }else
        {
        }
        if (addres.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPrivateSpaceActivity.this);
            builder.setMessage("Please enter the address of the space you wish to rent.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }else
        {
        }
        if (lat.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPrivateSpaceActivity.this);
            builder.setMessage("Please enter a latitude.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }else
        {
        }
        if (postco.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPrivateSpaceActivity.this);
            builder.setMessage("Please enter the postcode.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }else
        {
        }
        if (lng.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPrivateSpaceActivity.this);
            builder.setMessage("Please enter longitude.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }else
        {
        }
        if (desc.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPrivateSpaceActivity.this);
            builder.setMessage("Please enter a description.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }else
        {
        }
        if (city.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPrivateSpaceActivity.this);
            builder.setMessage("Please enter the city the space is located in.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }else
        {
        }

        return false;
    }
    public void getData()
    {
        price = pri.getText().toString();
        lat = la.getText().toString();
        lng = ln.getText().toString();
        addres = add.getText().toString();
        city = loc.getText().toString();
        postco = pos.getText().toString();
        desc = de.getText().toString();

    }

    public void checkCard(String id)
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    //Receives response from the php
                    JSONArray jsonResponse = new JSONArray(response);

                    if (jsonResponse.length() > 0) {
                        ArrayList<JSONObject> carddata = new ArrayList<>();

                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONObject object = jsonResponse.getJSONObject(i);
                            carddata.add(object);
                        }

                        if (carddata.get(0).getBoolean("success")) {
                            List<String> listItems = new ArrayList<String>();
                            for (int i = 0; i < carddata.size(); i++) {
                                listItems.add(carddata.get(i).getString("type") + " Ending in " + carddata.get(i).getString("digits"));
                            }
                            payments = listItems.toArray(new CharSequence[listItems.size()]);
                            onCreateDialog();
                        }


                    } else {
                        //Alerts the user of failure and asks for them retry
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddPrivateSpaceActivity.this);
                        builder.setMessage("No payment methods have been added to your account yet. Would you like to add one now?")
                                .setNegativeButton(android.R.string.cancel, null)
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do the acknowledged action, beware, this is run on UI thread
                                        Intent intent = new Intent(AddPrivateSpaceActivity.this, AddPaymentActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // Sends request to the php
        CardDetailsRequest Request = new CardDetailsRequest(id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AddPrivateSpaceActivity.this);
        queue.add(Request);

    }

    public void onCreateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AddPrivateSpaceActivity.this);
        builder.setTitle("Which payment method would you like to use for us to send transactions to?")
                .setSingleChoiceItems(payments, 0, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        payment_option = payments[which].toString().trim();
                        dialog.dismiss();
                        //launchRingDialog(view);
                        spaceRequest();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create()
                .show();
    }

    public void spaceRequest()
    {
        ringProgressDialog= ProgressDialog.show(AddPrivateSpaceActivity.this, "Please wait ...", "Processing ...", true);
        //ringProgressDialog.setCancelable(false);

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String rep = response;
                ringProgressDialog.dismiss();

                if (rep.equals("success")) {
                    //Opens up login form if successful
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddPrivateSpaceActivity.this);
                    builder.setMessage("Space has been added. You will shortly receive email confrimation. If you have any issues feel free to contact info@parkd.com.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // do the acknowledged action, beware, this is run on UI thread
                                    finish();
                                    clearData();
                                }
                            })
                            .create()
                            .show();

                } else {
                    //ringProgressDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddPrivateSpaceActivity.this);
                        builder.setMessage("Error occured")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();

                }

            }
        };
        NewSpaceRequest Request = new NewSpaceRequest(email, price, addres, postco, lat, lng, desc, city, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AddPrivateSpaceActivity.this);
        queue.add(Request);

    }
    private void clearData()
    {
        la.setText("");
        ln.setText("");
        loc.setText("");
        pos.setText("");
        pri.setText("");
        de.setText("");
        add.setText("");


    }
}
