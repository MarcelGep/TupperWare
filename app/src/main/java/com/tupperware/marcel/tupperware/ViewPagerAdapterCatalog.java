package com.tupperware.marcel.tupperware;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Marcel on 14.12.2016.
 */

public class ViewPagerAdapterCatalog extends FragmentPagerAdapter {

    String[] tabTitleArray = {"Info", "Details"};

    public ViewPagerAdapterCatalog (FragmentManager manager){
        super(manager);
    }


    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return new Fragment1();
            case 1: return new Fragment2();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){
        return tabTitleArray[position];
    }
}
