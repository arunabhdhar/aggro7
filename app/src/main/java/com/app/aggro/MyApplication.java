package com.app.aggro;


import android.content.Context;
import android.support.multidex.MultiDex;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;
import com.app.parsepush.AppConfig;
import com.app.parsepush.ParseUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Custom implementation of android.app.Application.&nbsp;The android:name attribute in the
 * AndroidManifest.xml application element should be the name of your class (".MyApp"). Android will
 * always create an instance of the application class and call onCreate before creating any other
 * Activity, Service or BroadcastReceiver.
 */
public class MyApplication extends Application {


    public static final String TAG = MyApplication.class
            .getSimpleName();

    private static MyApplication mInstance;
    /**
     * The Analytics singleton. The field is set in onCreate method override when the application
     * class is initially created.
     */
//    private static GoogleAnalytics analytics;
//
//    /**
//     * The default app tracker. The field is from onCreate callback when the application is
//     * initially created.
//     */
//    private static Tracker tracker;
//
//    /**
//     * Access to the global Analytics singleton. If this method returns null you forgot to either
//     * set android:name="&lt;this.class.name&gt;" attribute on your application element in
//     * AndroidManifest.xml or you are not setting this.analytics field in onCreate method override.
//     */
//    public static GoogleAnalytics analytics() {
//        return analytics;
//    }
//
//    /**
//     * The default app tracker. If this method returns null you forgot to either set
//     * android:name="&lt;this.class.name&gt;" attribute on your application element in
//     * AndroidManifest.xml or you are not setting this.tracker field in onCreate method override.
//     */
//    public static Tracker tracker() {
//        return tracker;
//    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        //Init Font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Helvetica Neue CE 55 Roman.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());

                // register with Active Android
        ActiveAndroid.initialize(this);

        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }

    /***
     * Tracking screen view
     *
     * @param screenName screen name to be displayed on GA dashboard
     */
    public void trackScreenView(String screenName) {
        Tracker t = getGoogleAnalyticsTracker();

        // Set screen name.
        t.setScreenName(screenName);

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());

        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }

    /***
     * Tracking exception
     *
     * @param e exception to be tracked
     */
    public void trackException(Exception e) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();

            t.send(new HitBuilders.ExceptionBuilder()
                            .setDescription(
                                    new StandardExceptionParser(this, null)
                                            .getDescription(Thread.currentThread().getName(), e))
                            .setFatal(false)
                            .build()
            );
        }
    }

    /***
     * Tracking event
     *
     * @param category event category
     * @param action   action of the event
     * @param label    label
     */
    public void trackEvent(String category, String action, String label) {
        Tracker t = getGoogleAnalyticsTracker();

        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }


}
