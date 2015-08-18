package com.app.aggro;

import com.activeandroid.ActiveAndroid;

/**
 * Created by ericbasendra on 28/06/15.
 */
public class MyApplication extends com.activeandroid.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}


