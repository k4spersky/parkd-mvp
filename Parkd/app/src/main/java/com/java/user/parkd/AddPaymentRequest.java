package com.java.user.parkd;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paul on 16/04/2017.
 */

public class AddPaymentRequest extends StringRequest {
    private Map<String, String> params;

    //Constructor for the request
    public AddPaymentRequest(String card_number, String expire_date, String cvv, String email, String card_type, String digits, String manual, Response.Listener<String> listener) {
        super(Method.POST, ADDPAYMENT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("card_number", card_number);
        params.put("expire_date", expire_date);
        params.put("cvv", cvv);
        params.put("email", email);
        params.put("type", card_type);
        params.put("digits", digits);
        params.put("manual", manual);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    //Url for the register page
    private static final String ADDPAYMENT_REQUEST_URL = "http://pjohnston37.students.cs.qub.ac.uk/Android/cardDetails.php";
}
