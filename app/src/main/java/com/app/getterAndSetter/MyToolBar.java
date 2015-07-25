package com.app.getterAndSetter;

import android.support.v7.widget.Toolbar;

/**
 * Created by ericbasendra on 24/07/15.
 */
public class MyToolBar {
    static Toolbar toolbar;
    public static Toolbar getToolbar() {
        return toolbar;
    }

    public static void setToolbar(Toolbar toolbar) {
        MyToolBar.toolbar = toolbar;
    }


}
