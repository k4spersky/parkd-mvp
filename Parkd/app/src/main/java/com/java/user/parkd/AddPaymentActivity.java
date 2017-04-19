package com.java.user.parkd;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import io.card.payment.CardIOActivity;
import io.card.payment.CardType;
import io.card.payment.CreditCard;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Integer.parseInt;

/**
 * Created by Paul on 14/04/2017.
 */

public class AddPaymentActivity extends AppCompatActivity {
    Button add;
    ImageButton cam;
    Toolbar tb1;
    private static final int REQUEST_SCAN = 100;
    private static final int REQUEST_AUTOTEST = 200;
    private EditText etCard;
    private String card_type = "";
    private String digits = "";
    private String manual = "Yes";
    private String current;
    private int len = 0;
    private boolean del = false;
    //Visa Cards Start with 4, Master Cards start with 51-55, American Express 34 or 37

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        add = (Button) findViewById(R.id.addCardBT);
        cam = (ImageButton) findViewById(R.id.cameraImage);
        tb1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb1);
        getSupportActionBar().setTitle("Add Payment Card");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etCard = (EditText) findViewById(R.id.editCardNumber);
        final EditText etExpire = (EditText) findViewById(R.id.editExpireDate);
        final EditText etCvv = (EditText) findViewById(R.id.editCVV);

        cam.setColorFilter(this.getResources().getColor(R.color.google_blue));

        etExpire.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable mEdit) {
                int len = 0;
                current = mEdit.toString();
                if (del == true) {
                } else {
                    if (current.length() == 2 && len < current.length()) {
                        mEdit.append("/");
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String str = etExpire.getText().toString();
                if (str.contains("/")) {
                    del = true;
                } else {
                    del = false;
                }
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        etCard.addTextChangedListener(new TextWatcher() {
            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = ' ';


            @Override
            public void afterTextChanged(Editable mEdit) {
                current = mEdit.toString();
                camImg();


                if (current.equals("")) {
                    card_type = "";
                    cam.setImageResource(R.drawable.camera);
                    cam.setColorFilter(AddPaymentActivity.this.getResources().getColor(R.color.google_blue));

                }
                if (!isInputCorrect(mEdit, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    mEdit.replace(0, mEdit.length(), buildCorrecntString(getDigitArray(mEdit, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }


            }


            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String str = etCard.getText().toString();
                len = str.length();
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        cam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent scanIntent = new Intent(AddPaymentActivity.this, CardIOActivity.class);

                // customize these values to suit your needs.


                // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
                startActivityForResult(scanIntent, REQUEST_SCAN);

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String card_number = etCard.getText().toString();
                final String expire_date = etExpire.getText().toString();
                final String cvv = etCvv.getText().toString();
                final String email = getEmail();
                if (emptyData(card_number, expire_date, cvv)) {
                    return;
                }
                if (checkExpire(expire_date)) {
                    return;
                }
                if (checkCvv(cvv)) {
                    return;
                }
                if (checkCardFormat(card_number)) {
                    return;
                }
                try {
                    if (dateValid(expire_date))
                    {
                        return;
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            //Receives response from the php
                            JSONObject jsonResponse = new JSONObject(response);
                            String success = jsonResponse.getString("success");

                            if (success.equals("true")) {
                                Toast.makeText(AddPaymentActivity.this, "Card Added", Toast.LENGTH_LONG).show();
                                finish();
                                //Opens up login form if successful


                            } else if (success.equals("false")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddPaymentActivity.this);
                                builder.setMessage("This card has already been added to this account. Please choose another.")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                                etExpire.setText("");
                                etCvv.setText("");
                                etCard.setText("");

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddPaymentActivity.this);
                                builder.setMessage("Unable to add card at this time. Please try again.")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                // Sends request to the php
                AddPaymentRequest addPaymentRequest = new AddPaymentRequest(card_number, expire_date, cvv, email, card_type, digits, manual, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddPaymentActivity.this);
                queue.add(addPaymentRequest);


            }
        });
    }

    private boolean checkCardFormat(String card_number) {
        if (card_number.length() == 19) {

            return false;

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPaymentActivity.this);
            builder.setMessage("Make sure the card number is the correct length")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }
    }

    private boolean checkExpire(String expire) {
        if (expire.length() == 5) {

            return false;

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPaymentActivity.this);
            builder.setMessage("Make sure the expire date is the correct length")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }
    }

    private boolean checkCvv(String cvv) {
        if (cvv.length() == 3) {

            return false;

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPaymentActivity.this);
            builder.setMessage("Make sure the cvv number is the correct length")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }

    }

    private boolean checkCardNumber(String card) {
        if (card.length() == 19) {

            return false;

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPaymentActivity.this);
            builder.setMessage("Make sure your card number is the correct length")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        return super.onOptionsItemSelected(item);
    }

    private String getEmail() {
        SharedPreferences sharedpref = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        return sharedpref.getString("email", "").toString();

    }

    //Checks if there are any empty strings
    public boolean emptyData(String cardnumber, String expire, String cvv) {
        if (cardnumber.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPaymentActivity.this);
            builder.setMessage("Please enter your card number")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        } else {
        }
        if (expire.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPaymentActivity.this);
            builder.setMessage("Please the expire date for the card.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        } else {
        }
        if (cvv.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPaymentActivity.this);
            builder.setMessage("Please the cvv number for the card.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
            return true;
        } else {
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SCAN) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                etCard.setText(scanResult.getFormattedCardNumber());
                digits = scanResult.getLastFourDigitsOfCardNumber();
                CardType cardType = scanResult.getCardType();
                card_type = cardType.name();
                manual = "No";
            } else {
                resultDisplayStr = "Scan was canceled.";
            }
            // do something with resultDisplayStr, maybe display it in a textView
            // resultTextView.setText(resultDisplayStr);
        }
        // else handle other activity results
    }

    @Override
    public void onStop() {
        super.onStop();
        ;
    }

    private void camImg() {
        String substring = "";
        int number = 0;
        if (current.length() == 1) {
            substring = current.substring(0, 1);
            number = parseInt(substring);

            if (number == 4) {
                card_type = "VISA";
                cam.setImageResource(R.drawable.visa);
                cam.setColorFilter(null);
            }
        }
        if (current.length() == 2) {
            substring = current.substring(0, 2);
            number = parseInt(substring);
            if (number == 34) {
                card_type = "American Express";
                cam.setImageResource(R.drawable.americanexpress);
                cam.setColorFilter(null);
            } else if (number == 37) {
                card_type = "American Express";
                cam.setImageResource(R.drawable.americanexpress);
                cam.setColorFilter(null);
            } else if (number == 51) {
                card_type = "MASTERCARD";
                cam.setImageResource(R.drawable.mastercard);
                cam.setColorFilter(null);
            } else if (number == 52) {
                card_type = "MASTERCARD";
                cam.setImageResource(R.drawable.mastercard);
                cam.setColorFilter(null);
            } else if (number == 53) {
                card_type = "MASTERCARD";
                cam.setImageResource(R.drawable.mastercard);
                cam.setColorFilter(null);
            } else if (number == 54) {
                card_type = "MASTERCARD";
                cam.setImageResource(R.drawable.mastercard);
                cam.setColorFilter(null);
            } else if (number == 55) {
                card_type = "MASTERCARD";
                cam.setImageResource(R.drawable.mastercard);
                cam.setColorFilter(null);
            }
        } else {
        }


    }

    private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
        boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
        for (int i = 0; i < s.length(); i++) { // chech that every element is right
            if (i > 0 && (i + 1) % dividerModulo == 0) {
                isCorrect &= divider == s.charAt(i);
            } else {
                isCorrect &= Character.isDigit(s.charAt(i));
            }
        }
        return isCorrect;

    }

    private String buildCorrecntString(char[] digits, int dividerPosition, char divider) {
        final StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != 0) {
                formatted.append(digits[i]);
                if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                    formatted.append(divider);
                }
            }
        }

        return formatted.toString();
    }

    private char[] getDigitArray(final Editable s, final int size) {
        char[] digits = new char[size];
        int index = 0;
        for (int i = 0; i < s.length() && index < size; i++) {
            char current = s.charAt(i);
            if (Character.isDigit(current)) {
                digits[index] = current;
                index++;
            }
        }
        return digits;
    }

    private boolean dateValid(String dateEntered) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
        simpleDateFormat.setLenient(false);
        Date expiry = simpleDateFormat.parse(dateEntered);
        boolean expired = expiry.before(new Date());
        if (expired == true){
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPaymentActivity.this);
            builder.setMessage("Expiry date entered is not valid.")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
        return true;}
        {
            return false;
            }
    }
}