package com.app.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.app.aggro.MyApplication;
import com.app.aggro.R;
import com.app.cards.CustomCards;
import com.app.holder.ChildItem;
import com.library.storage.SimpleStorage;
import com.library.storage.Storage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by ericbasendra on 09/08/15.
 */
public class Utility {

    public static final String AGGRO_DRAWABLE_DIRECTORY =  "AggroImage";

    public void setupUI(View view,final Context mContext) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(mContext);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView,mContext);
            }
        }
    }

    private static void hideSoftKeyboard(Context activity) {
        if(activity != null){
            InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(((Activity)activity).getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void writeUserInfoToPrefs(Context mContext,String name, String userName,String email,String gender,String location,String age){
        SharedPreferences.Editor prefs = mContext
                .getSharedPreferences(mContext.getResources().getString(R.string.aggro_prefs),
                        Context.MODE_PRIVATE).edit();
        prefs.clear();
        prefs.putString(mContext.getResources().getString(R.string.name), name);
        prefs.putString(mContext.getResources().getString(R.string.username), userName);
        prefs.putString(mContext.getResources().getString(R.string.email), email);
        prefs.putString(mContext.getResources().getString(R.string.gender), gender);
        prefs.putString(mContext.getResources().getString(R.string.location), location);
        prefs.putString(mContext.getResources().getString(R.string.age), age);
        prefs.commit();
    }

    public static void writePackageNameToPrefs(Context mContext, String package_name){
        SharedPreferences.Editor prefs = mContext
                .getSharedPreferences(mContext.getResources().getString(R.string.aggro_prefs),
                        Context.MODE_PRIVATE).edit();
        prefs.putString(mContext.getResources().getString(R.string.aggro_prefs_package_name), package_name);
        prefs.commit();
    }

    public static String readUserInfoFromPrefs(Context mContext,String key_name){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                mContext.getResources().getString(R.string.aggro_prefs),
                mContext.MODE_PRIVATE);
        return sharedPreferences.getString(key_name, "");
    }

    public static void writeCategoryNameToPrefs(Context mContext, String catName){
        SharedPreferences.Editor prefs = mContext
                .getSharedPreferences(mContext.getResources().getString(R.string.aggro_prefs),
                        Context.MODE_PRIVATE).edit();
        prefs.putString(mContext.getResources().getString(R.string.cat_all_apps), catName);
        prefs.commit();
    }

    public static void writePrefs(Context mContext, String prevValue, String prefsKey){
        SharedPreferences.Editor prefs = mContext
                .getSharedPreferences(mContext.getResources().getString(R.string.aggro_prefs),
                        Context.MODE_PRIVATE).edit();
        prefs.putString(prefsKey, prevValue);
        prefs.commit();
    }

    public static void writeBooleaenPrefs(Context mContext, boolean prevValue, String prefsKey){
        SharedPreferences.Editor prefs = mContext
                .getSharedPreferences(mContext.getResources().getString(R.string.aggro_prefs),
                        Context.MODE_PRIVATE).edit();
        prefs.putBoolean(prefsKey, prevValue);
        prefs.commit();
    }

    public static void writeRatingToPrefs(Context mContext, float prevValue, String prefsKey){
        SharedPreferences.Editor prefs = mContext
                .getSharedPreferences(mContext.getResources().getString(R.string.aggro_prefs),
                        Context.MODE_PRIVATE).edit();
        prefs.putFloat(prefsKey, prevValue);
        prefs.commit();
    }

    public static void writeFragementStatsToPrefs(Context mContext, int prevValue, String prefsKey){
        SharedPreferences.Editor prefs = mContext
                .getSharedPreferences(mContext.getResources().getString(R.string.aggro_prefs),
                        Context.MODE_PRIVATE).edit();
        prefs.putInt(prefsKey, prevValue);
        prefs.commit();
    }

    public static int readFragementStatsFromPrefs(Context mContext,String key_name){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                mContext.getResources().getString(R.string.aggro_prefs),
                mContext.MODE_PRIVATE);
        return sharedPreferences.getInt(key_name, 0);
    }

    public static void showSettingsAlert(final Context mContext){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public static void setMobileDataState(boolean mobileDataEnabled,Context context)
    {
        try
        {
            TelephonyManager telephonyService = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            Method setMobileDataEnabledMethod = telephonyService.getClass().getDeclaredMethod("setDataEnabled", boolean.class);

            if (null != setMobileDataEnabledMethod)
            {
                setMobileDataEnabledMethod.invoke(telephonyService, mobileDataEnabled);
            }
        }
        catch (Exception ex)
        {
            Log.e("ERROR", "Error setting mobile data state", ex);
        }
    }

    public static boolean getMobileDataState(Context context)
    {
        try
        {
            TelephonyManager telephonyService = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            Method getMobileDataEnabledMethod = telephonyService.getClass().getDeclaredMethod("getDataEnabled");

            if (null != getMobileDataEnabledMethod)
            {
                boolean mobileDataEnabled = (Boolean) getMobileDataEnabledMethod.invoke(telephonyService);

                return mobileDataEnabled;
            }
        }
        catch (Exception ex)
        {
            Log.e("TAG", "Error getting mobile data state", ex);
        }

        return false;
    }

    /**
     * Return whether the given PackgeInfo represents a system package or not.
     * User-installed packages (Market or otherwise) should not be denoted as
     * system packages.
     *
     * @param pkgInfo
     * @return
     */
    public static boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }

    public static boolean openDownloadedApp(Context mContext ,String packageName) {
        PackageManager manager = mContext.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {
//                return false;
                throw new PackageManager.NameNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            MyApplication.getInstance().trackException(e);
            e.printStackTrace();
            return false;
        }
    }
}
