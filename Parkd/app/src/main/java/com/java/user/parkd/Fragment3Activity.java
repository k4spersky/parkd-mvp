package com.java.user.parkd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment3Activity extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment3, container, false);
         TextView loggedInName = (TextView) view.findViewById(R.id.accName);
        final TextView rent = (TextView) view.findViewById(R.id.rentSpace);
        final TextView mybook = (TextView) view.findViewById(R.id.accBooking);
        final TextView payments = (TextView) view.findViewById(R.id.payDetails);
        final TextView account = (TextView) view.findViewById(R.id.accnt);
        final TextView help = (TextView) view.findViewById(R.id.accAbout);
        final TextView logOut = (TextView) view.findViewById(R.id.signOut);
        name = "";
        getData();

        if (name.length()==0) {
        } else {
            loggedInName.setText(name);
        }

        rent.setOnClickListener(view16 -> {

            //This Listener listens for click on the register text link
            //The following code is standard for running a new activity, in this case it opens the register form
            Intent settings = new Intent(Fragment3Activity.this.getActivity(), RentActivity.class);
            Fragment3Activity.this.startActivity(settings);
        });

        //Listener for rent a space

        //Listener for myBookings
        mybook.setOnClickListener(view1 -> {

            //This Listener listens for click on the register text link
            //The following code is standard for running a new activity, in this case it opens the register form
            Intent settings = new Intent(Fragment3Activity.this.getActivity(), BookingsActivity.class);
            Fragment3Activity.this.startActivity(settings);
        });

        //Listener for payments
        payments.setOnClickListener(view12 -> {

            //This Listener listens for click on the register text link
            //The following code is standard for running a new activity, in this case it opens the register form
            Intent settings = new Intent(Fragment3Activity.this.getActivity(), PaymentActivity.class);
            Fragment3Activity.this.startActivity(settings);
        });

        //Listener for account
        account.setOnClickListener(view13 -> {

            //This Listener listens for click on the register text link
            //The following code is standard for running a new activity, in this case it opens the register form
            Intent settings = new Intent(Fragment3Activity.this.getActivity(), UserAreaActivity.class);
            Fragment3Activity.this.startActivity(settings);
        });

        //Listener for help
        help.setOnClickListener(view14 -> {

            //This Listener listens for click on the register text link
            //The following code is standard for running a new activity, in this case it opens the register form
        });

        logOut.setOnClickListener(view15 -> {

            //This Listener listens for click on the register text link
            //The following code is standard for running a new activity, in this case it opens the register form
            removeData();
            //user.setText("");
            Intent intent = new Intent(Fragment3Activity.this.getActivity(), LoginActivity.class);
            //login = true;
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            getActivity().finishAffinity();
            Fragment3Activity.this.startActivity(intent);
            getActivity().finish();

        });
        return view;
    }

    public void getData() {

        SharedPreferences sharedpref = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        name = sharedpref.getString("name", "");
    }

    public void removeData() {
        String switchStatus;
        SharedPreferences sharedPref = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("name");
        switchStatus = sharedPref.getString("Saveemail", "");
        if(switchStatus.matches("No"))
        {
            editor.remove("email");
        }
        editor.apply();

        Toast.makeText(getActivity(), "Logged Out", Toast.LENGTH_LONG).show();

    }

    private String name;
}
