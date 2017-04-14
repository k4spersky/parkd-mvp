package com.java.user.parkd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Fragment1Activity extends Fragment {

    View view;
    TextView m_MessageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_fragment1, container, false);
        m_MessageView = (TextView) view.findViewById(R.id.carpark_name);

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
