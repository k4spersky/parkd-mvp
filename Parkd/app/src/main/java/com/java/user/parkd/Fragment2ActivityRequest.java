package com.java.user.parkd;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paul on 25/04/2017.
 */

public class Fragment2ActivityRequest extends StringRequest {

    private static final String map_REQUEST_URL = "http://pjohnston37.students.cs.qub.ac.uk/Android/getSpaces.php";
    private Map<String, String> params;

    //Constructor for the request
    protected Fragment2ActivityRequest(String location, Response.Listener<String> listener) {
        super(Request.Method.POST, map_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("loc", location);
    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
