package com.app.aggro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.app.BroadcastListener;
import com.app.Utility.Utility;
import com.app.local.database.AppTracker;

/**
 * Created by ericbasendra on 16/08/15.
 */
public class UninstallApkReceiver extends BroadcastReceiver {
    static final String TAG = "DownloadReceiver";
    BroadcastListener broadcastListener;
    public UninstallApkReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, intent.getAction());
        if (intent == null || intent.getData() == null) {
            return;
        }

        final String packageName = intent.getData().getSchemeSpecificPart();


            AppTracker localApptracker = AppTracker.getSingleEntry(packageName);
            if (localApptracker!=null){
                  AppTracker appTracker = localApptracker.load(AppTracker.class,localApptracker.getId());

                 localApptracker.deleteSingleEntry(packageName);
                 Intent i = new Intent(context,MainActivity.class);
                 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                 context.getApplicationContext().startActivity(i);

            }
            else{

            }

//
    }


}
