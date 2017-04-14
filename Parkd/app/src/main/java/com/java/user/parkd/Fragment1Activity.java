package com.java.user.parkd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Fragment1Activity extends Fragment {

    View view;
    TextView m_MessageView;
    private List<CarParks> m_carParks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_fragment1, container, false);
        m_MessageView = (TextView) view.findViewById(R.id.carpark_name);

        CardView cardView = (CardView) view.findViewById(R.id.cv);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

        // WTF
        FirebaseRecyclerAdapter mAdapter = new FirebaseRecyclerAdapter<CarParks, Fragment1Holder>(CarParks.class,
                R.layout.active_bookings_cardview, Fragment1Holder.class, databaseRef) {
            @Override
            protected void populateViewHolder(Fragment1Holder viewHolder, CarParks model, int position) {

            }
        };

        Spinner citySpinner = (Spinner) view.findViewById(R.id.spinner_frag1);
        String[] cities = new String[] {"Armagh", "Belfast", "Dublin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, cities);

        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        return view;
    }

    public void onStart() {
        super.onStart();

        //instantiate FirebasePost
        FirebasePost postFirebase = new FirebasePost();
        //call main method from FirebasePost class
        postFirebase.run(m_MessageView);
    }
}
