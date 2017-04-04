package com.java.user.parkd;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paul on 26/02/2017.
 */

public class RegisterRequest extends StringRequest {

    private Map<String, String> params;

    //Constructor for the request
    public RegisterRequest(String firstname, String lastname, String email,  String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    //Url for the register page
    private static final String REGISTER_REQUEST_URL = "http://pjohnston37.students.cs.qub.ac.uk/Android/register.php";
}
