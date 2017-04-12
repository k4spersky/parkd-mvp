package com.java.user.parkd;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import java.util.List;

/**
 * Created by Paul on 09/04/2017.
 */

public class BookingsActivity extends AppCompatActivity {
<<<<<<< HEAD:Parkd/app/src/main/java/com/java/user/parkd/bookingsActivity.java

=======
>>>>>>> origin/master:Parkd/app/src/main/java/com/java/user/parkd/BookingsActivity.java
    Toolbar tb1;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.bookings);
        tb1 = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(tb1);
        getSupportActionBar().setTitle("My Bookings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.bookings_viewpager);
        viewPager.setAdapter(new BookingsTabPagerAdapter(getSupportFragmentManager(),
                BookingsActivity.this));
        viewPager.setCurrentItem(0);

        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.booking_sliding_tabs);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.eucalyptus));
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

        return super.onOptionsItemSelected(item);
    }



}
