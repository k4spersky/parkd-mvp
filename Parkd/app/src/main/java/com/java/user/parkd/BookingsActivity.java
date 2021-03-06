package com.java.user.parkd;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;


/**
 * Created by Paul on 09/04/2017.
 */

public class BookingsActivity extends AppCompatActivity {
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
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            // Method designed to change colours depending on selected fragment position
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.eucalyptus));
                }
                if (tab.getPosition() == 1) {
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.youtube_red));
                }

            }

            // left empty as not needed
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
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
