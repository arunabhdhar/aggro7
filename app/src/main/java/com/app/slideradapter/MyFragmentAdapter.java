package com.app.slideradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by sonal on 7/2/2015.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {

    public static int pos = 0;

    private List<Fragment> myFragments;

    public MyFragmentAdapter(FragmentManager fm, List<Fragment> myFrags) {
        super(fm);
        myFragments = myFrags;
    }

    @Override
    public Fragment getItem(int position) {

        return myFragments.get(position);

    }

    @Override
    public int getCount() {

        return myFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        setPos(position);

        String PageTitle = "";

        switch(pos)
        {
            case 0:
                PageTitle = "APP";
                break;
            case 1:
                PageTitle = "CATEGORY";
                break;
            case 2:
                PageTitle = "FAVOURITES";
                break;
        }
        return PageTitle;
    }

    public static int getPos() {
        return pos;
    }

    public static void setPos(int pos) {
        MyFragmentAdapter.pos = pos;
    }
}
