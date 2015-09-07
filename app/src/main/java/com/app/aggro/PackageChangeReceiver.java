package com.app.aggro;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.app.BroadcastListener;
import com.app.Utility.Utility;
import com.app.api.Category;
import com.app.appfragement.ShowCatAppFragement;
import com.app.local.database.AppTracker;
import com.app.thin.downloadmanager.DownloadManager;

/**
 * Created by ericbasendra on 16/08/15.
 */
public class PackageChangeReceiver extends BroadcastReceiver {
    static final String TAG = "DownloadReceiver";
    BroadcastListener broadcastListener;
    public PackageChangeReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, intent.getAction());
        if (intent == null || intent.getData() == null) {
            return;
        }

        final String packageName = intent.getData().getSchemeSpecificPart();

        if (Utility.readUserInfoFromPrefs(context,context.getString(R.string.aggro_prefs_package_name).trim()).equals(packageName)){
            AppTracker localApptracker = AppTracker.getSingleEntry(packageName);
            if (localApptracker!=null){
                  AppTracker appTracker = localApptracker.load(AppTracker.class,localApptracker.getId());
                 if (appTracker.isInstalled)
                     appTracker.isInstalled = false;
                else
                     appTracker.isInstalled = true;

                appTracker.save();
            }
            else{
                AppTracker appTracker = new AppTracker();
                appTracker.isInstalled = true;
                appTracker.packageName = packageName;
                appTracker.appName = Utility.readUserInfoFromPrefs(context,context.getResources().getString(R.string.aggro_downloaded_app_name));
                appTracker.catName = Utility.readUserInfoFromPrefs(context,context.getResources().getString(R.string.aggro_downloaded_app_category));
                appTracker.marketUrl = Utility.readUserInfoFromPrefs(context,context.getResources().getString(R.string.aggro_downloaded_app_market_url));
                appTracker.appIconUrl = Utility.readUserInfoFromPrefs(context,context.getResources().getString(R.string.aggro_downloaded_app_icon_url));
                appTracker.save();
            }
        }else{
          // do some work if you want
        }
        Intent i = new Intent(context,Menu.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.getApplicationContext().startActivity(i);
    }


}
