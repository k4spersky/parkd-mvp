package com.java.user.parkd;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Paul on 14/04/2017.
 */

public class AddPaymentActivity extends AppCompatActivity{
    Button add;
    ImageButton cam;
    Toolbar tb1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);
        add = (Button) findViewById(R.id.addCardBT);
        cam= (ImageButton) findViewById(R.id.cameraImage);
        tb1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb1);
        getSupportActionBar().setTitle("Add Payment Card");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       cam.setColorFilter(this.getResources().getColor(R.color.google_blue));

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


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
