package com.java.user.parkd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.AsyncTask;
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
 * kkuczkowski
 */

public class RentActivity extends AppCompatActivity {
    private TextView rentText;
    Toolbar tb1;
    FloatingActionButton fb;
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RentAdapter adapter;
    private List<RentData> datalist;
    private String jsonString = "";
    private LinearLayout linlaHeaderProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_space);
        fb = (FloatingActionButton) findViewById(R.id.addNewSpace);
        tb1 = (Toolbar) findViewById(R.id.toolbar);
        linlaHeaderProgress = (LinearLayout) findViewById(R.id.rentHeaderProgress);
        setSupportActionBar(tb1);
        getSupportActionBar().setTitle("Your Parking Spaces");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerRentView);
        linearLayoutManager = new LinearLayoutManager(RentActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        datalist = new ArrayList<>();

        SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String email = sharedpref.getString("email", "");
        final String url = "http://pjohnston37.students.cs.qub.ac.uk/Android/mySpaces.php?id=" + email;
        new AsyncHTTPTask().execute(url);
        adapter = new RentAdapter(RentActivity.this, datalist);
        recyclerView.setAdapter(adapter);

        fb.setOnClickListener(view -> {
            Intent settings = new Intent(RentActivity.this, AddPrivateSpaceActivity.class);
            RentActivity.this.startActivity(settings);

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
             linlaHeaderProgress.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            //adapter.getItemCount();
            if (result == 1) {
                //Initialising the adapter - Passing in the activity and the parsed Admin Team List
                adapter = new RentAdapter(RentActivity.this, datalist);
                linlaHeaderProgress.setVisibility(View.GONE);
                //Setting the adapter
                recyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(RentActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
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
                RentData data = new RentData(object.getString("image"),
                        object.getString("address"), object.getString("postcode"), object.getDouble("price"), object.getString("desc"), object.getString("show"), object.getString("type"));
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
        final String url = "http://pjohnston37.students.cs.qub.ac.uk/Android/mySpaces.php?id=" +email;

        new AsyncHTTPTask().execute(url);
    }

}
