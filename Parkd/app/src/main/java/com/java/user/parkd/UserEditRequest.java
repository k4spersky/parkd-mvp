package com.java.user.parkd;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paul on 19/04/2017.
 */

public class UserEditRequest extends StringRequest {
    private static final String UAEdit_REQUEST_URL = "http://pjohnston37.students.cs.qub.ac.uk/Android/userEdit.php";
    private Map<String, String> params;

    //Constructor for the request
    public UserEditRequest(String email, String firstName, String lastName, String phoneNumber,
                           String new_email, Response.Listener<String> listener) {
        super(Request.Method.POST, UAEdit_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("user", email);
        params.put("fn", firstName);
        params.put("ln", lastName);
        params.put("pn", phoneNumber);
        params.put("em", new_email);
    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
