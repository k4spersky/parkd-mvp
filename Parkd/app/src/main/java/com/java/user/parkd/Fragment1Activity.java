package com.java.user.parkd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Fragment1Activity extends Fragment {
    FirebaseRecyclerAdapter mAdapter;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_fragment1, container, false);

        // blue spinner (city picker)
        Spinner citySpinner = (Spinner) view.findViewById(R.id.spinner_frag1);
        String[] cities = new String[] {"Belfast", "Armagh", "Dublin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, cities);

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

        // fragment 1 recyclerView (car park data list)
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.recyclerView);
        recycler.setHasFixedSize(false);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        mAdapter = new FirebaseRecyclerAdapter<CarParkData, Fragment1Holder>(CarParkData.class,
                R.layout.fragment1_cardview, Fragment1Holder.class, ref) {

            @Override
            public void populateViewHolder(Fragment1Holder viewHolder, CarParkData model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setAddress1(model.getAddress1());
                viewHolder.setCity(model.getCity() + ",");
                viewHolder.setPostCode(model.getPostCode());

                // make android great again feature
                View maga = viewHolder.magaView();
                maga.setBackgroundResource(R.color.youtube_red);

                if (model.getAvailableSpaces() <= 10)
                {
                    maga.setBackgroundResource(R.color.pastel_red);
                } else if (model.getAvailableSpaces() > 10 && model.getAvailableSpaces() <= 20) {
                    maga.setBackgroundResource(R.color.pastel_orange);
                } else {
                    maga.setBackgroundResource(R.color.eucalyptus);
                }

                viewHolder.setAvailableSpaces(Integer.toString(model.getAvailableSpaces()));

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Click", Toast.LENGTH_LONG).show();

                    }
                });
            }
        };

        recycler.setAdapter(mAdapter);
        return view;
    }

    /**
     *     only enable to use with {@link FirebasePost
     */
    public void onStart() {
        super.onStart();

        //instantiate FirebasePost
        // FirebasePost postFirebase = new FirebasePost();

        //call main method from FirebasePost class
        // postFirebase.run(m_MessageView);
    }

    //destroying adapter on parkd shutdown
    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }
}
