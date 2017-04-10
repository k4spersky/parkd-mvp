package com.java.user.parkd;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Paul on 10/04/2017.
 */

public class BookingsTabPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"Active", "Inactive"};

    public BookingsTabPagerAdapter (FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new FragmentBookings1Activity();
        } else if(position == 1) {
            return new FragmentBookings2Activity();
        }

        return null;
    }
    @Override
    public CharSequence getPageTitle(int position){
        // Generate title based on item position
        return tabTitles[position];
    }
}
