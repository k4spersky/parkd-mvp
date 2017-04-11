package com.java.user.parkd;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 09/04/2017.
 */

public class FragmentBookings1Activity extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapter adapter;
    private List<MyData> datalist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_bookings_fragment1, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        datalist = new ArrayList<>();
        Toast.makeText(getActivity(), "Here", Toast.LENGTH_LONG).show();
        load_data_from_server();
        Toast.makeText(getActivity(), "Here2", Toast.LENGTH_LONG).show();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CustomAdapter(getActivity(), datalist);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void load_data_from_server(){
        SharedPreferences sharedpref = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String email = sharedpref.getString("email", "");
        Toast.makeText(getActivity(), email, Toast.LENGTH_LONG).show();
        Response.Listener<String> responseListener = new Response.Listener<String>()
        {

            @Override
            public void onResponse(String response) {
        try {
            //Receives response from the php
            JSONArray array = new JSONArray(response);
            //Toast.makeText(getActivity(), array.length(), Toast.LENGTH_LONG).show();
            for (int j=0; j<array.length(); j++){
                Toast.makeText(getActivity(), "In array", Toast.LENGTH_LONG).show();
                JSONObject object = array.getJSONObject(j);
                MyData data = new MyData(object.getString("bookingdate"), object.getString("image_address"), object.getString("address"), object.getString("postcode"));
                datalist.add(data);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    };
        ActiveBookingsRequest activeRequest = new ActiveBookingsRequest(email, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(activeRequest);


        }
}
    // Sends request to the php

