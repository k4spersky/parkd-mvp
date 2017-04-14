package com.java.user.parkd;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Paul on 14/04/2017.
 */

public class PaymentActivity extends AppCompatActivity {

    Toolbar tb1;
    FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);
        fb = (FloatingActionButton) findViewById(R.id.addNewCard);
        tb1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb1);
        getSupportActionBar().setTitle("Payment Methods");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent settings = new Intent(PaymentActivity.this, AddPaymentActivity.class);
                PaymentActivity.this.startActivity(settings);

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        return super.onOptionsItemSelected(item);
    }

}
