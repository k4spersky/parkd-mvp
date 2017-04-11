package com.java.user.parkd;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paul on 11/04/2017.
 */

public class ActiveBookingsRequest extends StringRequest {
    private static final String ActiveBooking_REQUEST_URL = "http://pjohnston37.students.cs.qub.ac.uk/Android/bookings.php";
    private Map<String, String> params;

    //Constructor for the request
    private String date;

    public ActiveBookingsRequest(String email, Response.Listener<String> listener) {
        super(Request.Method.POST, ActiveBooking_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);

    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
