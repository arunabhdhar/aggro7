package com.app.getterAndSetter;

import android.support.v7.widget.Toolbar;

import com.app.slideradapter.MyFragmentAdapter;

/**
 * Created by ericbasendra on 24/07/15.
 */
public class MyToolBar {
    static Toolbar toolbar;


    static MyFragmentAdapter myFragmentAdapter;

    public static Toolbar getToolbar() {
        return toolbar;
    }

    public static void setToolbar(Toolbar toolbar) {
        MyToolBar.toolbar = toolbar;
    }


    public static MyFragmentAdapter getMyFragmentAdapter() {
        return myFragmentAdapter;
    }

    public static void setMyFragmentAdapter(MyFragmentAdapter myFragmentAdapter) {
        MyToolBar.myFragmentAdapter = myFragmentAdapter;
    }

}
