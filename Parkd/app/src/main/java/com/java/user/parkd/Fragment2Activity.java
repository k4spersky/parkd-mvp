package com.java.user.parkd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment2Activity extends Fragment implements View.OnClickListener {

    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment2, container, false);

        //This Listener listens for click on the login button
        //The login button is located here just for testing purposes
        //The following code is standard for running a new activity
        Button testButton = (Button) view.findViewById(R.id.button2);
        testButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent LoginIntent = new Intent(Fragment2Activity.this.getActivity(), LoginActivity.class);
        getActivity().finish();
        Fragment2Activity.this.startActivity(LoginIntent);
    }
}
