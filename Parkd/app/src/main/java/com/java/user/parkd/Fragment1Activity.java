package com.java.user.parkd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by kkuczkowski on 03/03/2017.
 */

public class Fragment1Activity extends Fragment implements View.OnClickListener {

    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_fragment1, container, false);

        Button testButton = (Button) view.findViewById(R.id.button2);
        testButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent LoginIntent = new Intent(Fragment1Activity.this.getActivity(), LoginActivity.class);
        Fragment1Activity.this.startActivity(LoginIntent);

    }
}
