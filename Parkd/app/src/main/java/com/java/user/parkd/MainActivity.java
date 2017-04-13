package com.java.user.parkd;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    // declare button
    TextView m_MessageView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.java.user.parkd.R.layout.activity_main);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.eucalyptus));
        viewPager.setCurrentItem(1);

        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            // Method designed to change colours depending on selected fragment position
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.dodger_blue));
                }
                if (tab.getPosition() == 1) {
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.eucalyptus));
                }
                if (tab.getPosition() == 2) {
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.cadet_grey));
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
        // Button connect
        m_MessageView = (TextView)findViewById(R.id.carpark_name);
        m_MessageView.setText("STRING");
    }

//    protected void onStart() {
//        super.onStart();
//
//        //instantiate FirebasePost
//        FirebasePost postFirebase = new FirebasePost();
//        //call main method
//        postFirebase.run(m_MessageView);
//    }
}
