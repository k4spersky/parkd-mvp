package com.java.user.parkd;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paul on 26/02/2017.
 */

public class LoginRequest extends StringRequest {
    private static final String Login_REQUEST_URL = "http://pjohnston37.students.cs.qub.ac.uk/Android/login.php";
    private Map<String, String> params;

    //Constructor for the request
    public LoginRequest(String email, String password, Response.Listener<String> listener) {
        super(Method.POST, Login_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}

