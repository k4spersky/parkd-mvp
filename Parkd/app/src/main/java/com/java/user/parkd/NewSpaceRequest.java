package com.java.user.parkd;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paul on 03/05/2017.
 */

public class NewSpaceRequest extends StringRequest {
    private Map<String, String> params;
    public NewSpaceRequest(String user, String price, String address, String postcode, String lat, String lng, String desc, String loc, Response.Listener<String> listener) {
        super(Request.Method.POST, ADDSPACE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", user);
        params.put("price", price);
        params.put("address", address);
        params.put("post", postcode);
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("des", desc);
        params.put("loc", loc);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    //Url for the register page
    private static final String ADDSPACE_REQUEST_URL = "http://pjohnston37.students.cs.qub.ac.uk/Android/addSpace.php";
}
