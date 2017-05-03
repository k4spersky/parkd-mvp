package com.java.user.parkd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 14/04/2017.
 */

public class PaymentActivity extends AppCompatActivity {

    Toolbar tb1;

    FloatingActionButton fb;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private PaymentsAdapter adapter;
    private List<CardDetailsData> datalist;
    private String jsonString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);
        fb = (FloatingActionButton) findViewById(R.id.addNewCard);
        tb1 = (Toolbar) findViewById(R.id.toolbar_pm);
        setSupportActionBar(tb1);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerPaymentView);
        getSupportActionBar().setTitle("Payment Methods");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        datalist = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(PaymentActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PaymentsAdapter(PaymentActivity.this, datalist);
        recyclerView.setAdapter(adapter);

        //Downloading data from below url (Universal Resource Locator) to obtain data from the Admin database
        SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String email = sharedpref.getString("email", "");
        final String url = "http://pjohnston37.students.cs.qub.ac.uk/Android/getCardDetails.php?id=" + email;
        new AsyncHTTPTask().execute(url);

        fb.setOnClickListener(view -> {
            Intent settings = new Intent(PaymentActivity.this, AddPaymentActivity.class);
            PaymentActivity.this.startActivity(settings);

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        return super.onOptionsItemSelected(item);
    }

    public class AsyncHTTPTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;

            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    jsonString = response.toString();
                    parseResult();
                    result = 1; // Successful
                } else {

                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d("Exception Caught", e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPreExecute() {
            // SHOW THE SPINNER WHILE LOADING FEEDS
           // linlaHeaderProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            //adapter.getItemCount();
            if (result == 1) {
                //Initialising the adapter - Passing in the activity and the parsed Admin Team List
                adapter = new PaymentsAdapter(PaymentActivity.this, datalist);
                //Setting the adapter
                recyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(PaymentActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //This method will parse the RAW data downloaded from the server
    private void parseResult() {

        try {
            JSONArray AdminArrays = new JSONArray(jsonString);
            datalist = new ArrayList<>();

            for (int i = 0; i < AdminArrays.length(); i++) {
                JSONObject object = AdminArrays.getJSONObject(i);
                CardDetailsData data = new CardDetailsData(object.getString("type"),
                        object.getString("digits"), object.getString("url"));
                this.datalist.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onRestart(){
        super.onRestart();
    }

    public void onResume() {
        super.onResume();

        SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String email = sharedpref.getString("email", "");
        final String url = "http://pjohnston37.students.cs.qub.ac.uk/Android/getCardDetails.php?id=" +email;

        new AsyncHTTPTask().execute(url);
    }
}
