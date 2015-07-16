package com.app.slideradapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.aggro.R;

import java.util.List;

/**
 * Created by sonal on 7/2/2015.
 */
public class MyFragmentAdapter extends FragmentStatePagerAdapter {

    public static int pos = 0;
    public static int LEFT_PAGE = 0;
    public static int CENTERED_PAGE = 1;
    public static int RIGHT_PAGE =2;

    private List<Fragment> myFragments;
    private Context context;

    public MyFragmentAdapter(Context context, FragmentManager fm, List<Fragment> myFrags) {
        super(fm);
        this.myFragments = myFrags;
        this.context = context;
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
                PageTitle = context.getResources().getString(R.string.view_pager_apps);
                break;
            case 1:
                PageTitle = context.getResources().getString(R.string.view_pager_category);
                break;
            case 2:
                PageTitle = context.getResources().getString(R.string.view_pager_fav);
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
