package com.java.user.parkd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


/**
 * desc
 */
public class AddPrivateSpaceActivity extends AppCompatActivity {
    Toolbar tb1;
    Button addSpaceBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_space);
        tb1 = (Toolbar) findViewById(R.id.toolbar_space);
        setSupportActionBar(tb1);
        getSupportActionBar().setTitle("Add new Private Space");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addSpaceBtn = (Button) findViewById(R.id.addSpaceBtn);

        // blue spinner (city picker)
        Spinner spaceSpinner = (Spinner) findViewById(R.id.space_type_spinner);
        String[] spaceTypes = new String[] {"Private", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, spaceTypes);

        spaceSpinner.setAdapter(adapter);
        spaceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
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
