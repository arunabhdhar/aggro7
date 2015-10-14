package com.app.aggro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.view.Menu;
import android.widget.TextView;

import com.app.Utility.Utility;
import com.app.appfragement.HelpFragement;
import com.app.appfragement.MyDetailsFragement;
import com.app.appfragement.SearchAppFragement;
import com.app.appfragement.SettingsFragment;
import com.app.appfragement.fragment.MenuMain;
import com.app.local.database.UserInfo;
import com.app.slideradapter.MyFragmentAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42Log;

import java.util.Stack;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements MyDetailsFragement.OnFragmentInteractionListener,App42GCMController.App42GCMListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    FloatingActionButton fabBtn;
    //    FrameLayout rootLayout;
    CoordinatorLayout rootLayout;
    Toolbar toolbar;
    //    TabLayout tabLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    NavigationView navigation;
    TextView username, email;
    public static Stack<Fragment> fragmentStack;
    private FragmentManager fragmentManager;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentStack = new Stack<Fragment>();
        initInstances();
        loadAds();
    }

    private void loadAds() {
        mAdView = (AdView) findViewById(R.id.adView);
        // Request for Ads
        AdRequest adRequest = new AdRequest.Builder()

                // Add a test device to show Test Ads
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        // Load ads into Banner Ads
        mAdView.loadAd(adRequest);
    }

    private void initInstances() {
        App42API.initialize(
                this,
                getResources().getString(R.string.app_hq_app_id),
                getResources().getString(R.string.app_hq_client_key));
        App42Log.setDebug(true);
        App42API.setLoggedInUser(UserInfo.getRandom().userName) ;
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.hello_world, R.string.hello_world){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }


            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        navigation = (NavigationView) findViewById(R.id.navigation);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navItem1:
                        Utility.writeFragementStatsToPrefs(MainActivity.this, MyFragmentAdapter.CENTERED_PAGE, getString(R.string.prefs_fragement_stats));
                        refreshFragement(Utility.readFragementStatsFromPrefs(MainActivity.this, getString(R.string.prefs_fragement_stats)));
                        break;
                    case R.id.navItem2:
                        selectItemMyDetails();
                        break;
                    case R.id.navItem3:
                        Utility.writeFragementStatsToPrefs(MainActivity.this, MyFragmentAdapter.RIGHT_PAGE, getString(R.string.prefs_fragement_stats));
                        refreshFragement(Utility.readFragementStatsFromPrefs(MainActivity.this, getString(R.string.prefs_fragement_stats)));
                        break;
                    case R.id.navItem4:
                        helpFragement();
                        break;
                    case R.id.navItem5:
                        settingsAppFragement("","");
                        break;
                }
                return false;
            }
        });

        username = (TextView)navigation.findViewById(R.id.username);
        email = (TextView)navigation.findViewById(R.id.email);
        UserInfo userInfo = UserInfo.getRandom();
        if (userInfo !=null){
            username.setText(userInfo.userName);
            email.setText(userInfo.email);
        }


        refreshFragement(0);
    }

    public void onStart() {
        super.onStart();
        if (App42GCMController.isPlayServiceAvailable(this)) {
            App42GCMController.getRegistrationId(MainActivity.this,
                    getResources().getString(R.string.google_project_number), this);
        } else {
            Log.i("App42PushNotification",
                    "No valid Google Play Services APK found.");
        }
    }
    /*
         * * This method is called when a Activty is stop disable all the events if
         * occuring (non-Javadoc)
         *
         * @see android.app.Activity#onStop()
         */
    public void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAdView != null) {
            mAdView.pause();
        }
        unregisterReceiver(mBroadcastReceiver);
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        String message = getIntent().getStringExtra(
                App42GCMService.ExtraMessage);
        if (message != null)
            Log.d("MainActivity-onResume", "Message Recieved :" + message);
        IntentFilter filter = new IntentFilter(
                App42GCMService.DisplayMessageAction);
        filter.setPriority(2);
        registerReceiver(mBroadcastReceiver, filter);
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        if (fragmentStack.size() == 2) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            fragmentStack.lastElement().onPause();
            ft.remove(fragmentStack.pop());
            fragmentStack.lastElement().onResume();
            ft.show(fragmentStack.lastElement());
            ft.commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_search) {
            selectsearchAppFragement("","");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void selectsearchAppFragement(String local, String level) {
        Fragment fragment = SearchAppFragement.newInstance("", "");
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.main_content, fragment);
        fragmentStack.lastElement().onPause();
        ft.hide(fragmentStack.lastElement());
        fragmentStack.push(fragment);
        ft.commit();
    }

    public void settingsAppFragement(String local, String level) {
        Fragment fragment = SettingsFragment.newInstance("", "");
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.main_content, fragment);
        fragmentStack.lastElement().onPause();
        ft.hide(fragmentStack.lastElement());
        fragmentStack.push(fragment);
        ft.commit();
    }


    private void selectItemMyDetails() {
        Fragment fragment = MyDetailsFragement.newInstance("", "");
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.main_content, fragment);
        fragmentStack.lastElement().onPause();
        ft.hide(fragmentStack.lastElement());
        fragmentStack.push(fragment);
        ft.commit();
    }

    private void refreshFragement(int item){
        Fragment homeListFragment= MenuMain.newInstance("", item);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.main_content, homeListFragment);
        fragmentStack.push(homeListFragment);
        ft.commit();
    }

    private void helpFragement(){
        Fragment homeListFragment= HelpFragement.newInstance("", "");
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.main_content, homeListFragment);
        fragmentStack.push(homeListFragment);
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public void onGCMRegistrationId(String gcmRegId) {

        App42GCMController.storeRegistrationId(MainActivity.this, gcmRegId);
        if(!App42GCMController.isApp42Registerd(MainActivity.this))
            App42GCMController.registerOnApp42(App42API.getLoggedInUser(), gcmRegId, MainActivity.this);
    }

    @Override
    public void onApp42Response(String responseMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void onRegisterApp42(String responseMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                App42GCMController.storeApp42Success(MainActivity.this);
            }
        });
    }

    final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent
                    .getStringExtra(App42GCMService.ExtraMessage);
            Log.e("MASSAGE RECEIVED","" + message);


        }
    };
}
