package com.java.user.parkd;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment3Activity extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment3, container, false);
        final TextView loggedinName = (TextView) view.findViewById(R.id.accName);
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String name = "";
        pref.getString("username", name); // getting String
        if (name.length()==0)
        {

        }else{
            loggedinName.setText(name);

        }
        return view;

    }
}
