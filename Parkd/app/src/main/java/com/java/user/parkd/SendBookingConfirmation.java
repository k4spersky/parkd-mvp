package com.java.user.parkd;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paul on 02/05/2017.
 */

public class SendBookingConfirmation extends StringRequest{
    private static final String map_REQUEST_URL = "http://pjohnston37.students.cs.qub.ac.uk/Android/addBooking.php";
    private Map<String, String> params;

    //Constructor for the request
    protected SendBookingConfirmation(String id, String from, String to, String space, String price, String digits, String date, Response.Listener<String> listener) {
        super(Request.Method.POST, map_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);
        params.put("from", from);
        params.put("to", to);
        params.put("space", space);
        params.put("price", price);
        params.put("dig", digits);
        params.put("date", date);
    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
