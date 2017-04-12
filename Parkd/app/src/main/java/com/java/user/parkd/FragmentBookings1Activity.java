package com.java.user.parkd;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
 * Created by Paul on 09/04/2017.
 */

public class FragmentBookings1Activity extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ActiveBookingsCustomAdapter adapter;
    private List<ActiveBookingsData> datalist;
    private String jsonString = "";
    private LinearLayout linlaHeaderProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_bookings_fragment1, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        linlaHeaderProgress = (LinearLayout) view.findViewById(R.id.linlaHeaderProgress);
        datalist = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ActiveBookingsCustomAdapter(getActivity(), datalist);
        recyclerView.setAdapter(adapter);
        
        //Downloading data from below url (Universal Resource Locator) to obtain data from the Admin database
        SharedPreferences sharedpref = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String email = sharedpref.getString("email", "");
        final String url = "http://pjohnston37.students.cs.qub.ac.uk/Android/bookings.php?id=" +email;
        new AsyncHTTPTask().execute(url);

        return view;
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
                adapter = new ActiveBookingsCustomAdapter(getActivity(), datalist);
                //Setting the adapter
                recyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
            linlaHeaderProgress.setVisibility(View.GONE);
        }
    }

    //This method will parse the RAW data downloaded from the server
    private void parseResult() {

        try {
            JSONArray AdminArrays = new JSONArray(jsonString);
            datalist = new ArrayList<>();
            for (int i = 0; i < AdminArrays.length(); i++) {
                JSONObject object = AdminArrays.getJSONObject(i);
                ActiveBookingsData data = new ActiveBookingsData(object.getString("bookingdate"), object.getString("image"), object.getString("address"), object.getString("postcode"));
                this.datalist.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
