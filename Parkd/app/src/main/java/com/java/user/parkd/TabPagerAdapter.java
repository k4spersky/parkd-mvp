package com.java.user.parkd;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by kkuczkowski on 03/03/2017.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"parkd", "search", "settings"};

    public TabPagerAdapter (FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new Fragment1Activity();
        } else if(position == 1) {
            return new Fragment2Activity();
        } else if(position == 2) {
            return new Fragment3Activity();
        }
        return null;
    }
    @Override
    public CharSequence getPageTitle(int position){
        // Generate title based on item position
        return tabTitles[position];
    }
}