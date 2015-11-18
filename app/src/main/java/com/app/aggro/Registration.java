package com.app.aggro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.app.AppConstant;
import com.app.Utility.Utility;
import com.app.Validator.EmptyTextListener;
import com.app.address.User;
import com.app.api.GsonRequest;
import com.app.gps.GPSTracker;
import com.app.gridcategory.ImageItem;
import com.app.local.database.AggroCategory;
import com.app.local.database.AppTracker;
import com.app.local.database.UserInfo;
import com.app.response.Msg;
import com.app.response.RegisterResponse;
import com.app.spinneradapter.NothingSelectedSpinnerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import fr.quentinklein.slt.LocationTracker;
import fr.quentinklein.slt.LocationUtils;
import fr.quentinklein.slt.TrackerSettings;

public class Registration extends Activity {
    LocationTracker myTracker;
    private AdView mAdView;
    private ImageView signIn;
    private Spinner spinnerMF;
    private GPSTracker gpsTracker ;
    private EditText name_ed, user_name_ed,age_ed, gender_ed,location_ed, email_ed;
    public static Typeface mpRegular, mpBold, mpSnap;
    public static int count = 0;
    private Activity mContext = Registration.this;
    private String gender;
    private static String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (UserInfo.getRandom() != null){
            count = count + 1;
            startActivity(new Intent(Registration.this, com.app.aggro.MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_registration);
        init();
        saveCategoryInDatabaseFirstTime();
//        initLocalApptracer();
        getInstalledApps();
        trackLocation();
        addItemToSpinner();
        addListenerToSpinner();
    }



    @Override
    protected void onPause() {
        super.onPause();

        if(myTracker != null) {
            myTracker.stopListen();
        }

        if (mAdView != null) {
            mAdView.pause();
        }
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        GooglePlayServicesUtil.isGooglePlayServicesAvailable(Registration.this);


        if (!LocationUtils.isGpsProviderEnabled(Registration.this)){
            Utility.showSettingsAlert(Registration.this);
        }
        if(myTracker != null) {
            myTracker.startListen();
        }
        if (mAdView != null) {
            mAdView.resume();
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_UP) {
            final View view = getCurrentFocus();

            if(view != null) {
                final boolean consumed = super.dispatchTouchEvent(ev);

                final View viewTmp = getCurrentFocus();
                final View viewNew = viewTmp != null ? viewTmp : view;

                if(viewNew.equals(view)) {
                    final Rect rect = new Rect();
                    final int[] coordinates = new int[2];

                    view.getLocationOnScreen(coordinates);

                    rect.set(coordinates[0], coordinates[1], coordinates[0] + view.getWidth(), coordinates[1] + view.getHeight());

                    final int x = (int) ev.getX();
                    final int y = (int) ev.getY();

                    if(rect.contains(x, y)) {
                        return consumed;
                    }
                }
                else if(viewNew instanceof EditText) {
                    return consumed;
                }

                final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                inputMethodManager.hideSoftInputFromWindow(viewNew.getWindowToken(), 0);

                viewNew.clearFocus();

                return consumed;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void init() {
        mpSnap = Typeface.createFromAsset(getAssets(),
                "fonts/snap-itc.ttf");
        mpRegular = Typeface.createFromAsset(getAssets(),
                "fonts/Helvetica Neue CE 55 Roman.ttf");
        mpBold = Typeface.createFromAsset(getAssets(),
                "fonts/Helvetica Neue CE 75 Bold.ttf");
        mAdView = (AdView) findViewById(R.id.adView);
        gpsTracker = new GPSTracker(Registration.this);
        signIn = (ImageView)findViewById(R.id.signin);
        spinnerMF = (Spinner)findViewById(R.id.gender);
        name_ed = (EditText)findViewById(R.id.name);
        user_name_ed = (EditText)findViewById(R.id.username);
        age_ed = (EditText)findViewById(R.id.age);
        location_ed = (EditText)findViewById(R.id.loacation);
        email_ed = (EditText)findViewById(R.id.email);

        email_ed.setOnEditorActionListener(new EmptyTextListener(email_ed, user_name_ed));
        
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MyApplication.getInstance().trackEvent("Register", "Sigin", "Save detail to server");
                    feildValidator();
                }catch (Exception e){
                    MyApplication.getInstance().trackException(e);
                    Log.e(TAG, "Exception: " + e.getMessage());
                }

            }
        });

        // Request for Ads
        AdRequest adRequest = new AdRequest.Builder()

                // Add a test device to show Test Ads
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        // Load ads into Banner Ads
        mAdView.loadAd(adRequest);

    }

    private ArrayList<ImageItem> defaultCategoryLoad(){
        final ArrayList<ImageItem> mItems = new ArrayList<>();
        mItems.add(new ImageItem(getString(R.string.cat_bussiness),R.drawable.business));
        mItems.add(new ImageItem(getString(R.string.cat_lifestyle),R.drawable.icon_lifestyle));
        mItems.add(new ImageItem(getString(R.string.cat_news),   R.mipmap.news));
        mItems.add(new ImageItem(getString(R.string.cat_education),R.mipmap.teaching));
        mItems.add(new ImageItem(getString(R.string.cat_transporation),R.drawable.transportaion));
        mItems.add(new ImageItem(getString(R.string.cat_productiity),R.mipmap.games));
        mItems.add(new ImageItem(getString(R.string.cat_games),R.mipmap.games));
        mItems.add(new ImageItem(getString(R.string.cat_travel),R.mipmap.games));
        mItems.add(new ImageItem(getString(R.string.cat_sports), R.mipmap.sports));
        mItems.add(new ImageItem(getString(R.string.cat_health), R.mipmap.health));
        mItems.add(new ImageItem(getString(R.string.cat_entertainment), R.mipmap.entertainment));

        mItems.add(new ImageItem(getString(R.string.cat_comics), R.drawable.comics));
        mItems.add(new ImageItem(getString(R.string.cat_communication), R.drawable.communication));
        mItems.add(new ImageItem(getString(R.string.cat_finance), R.drawable.finance));
        mItems.add(new ImageItem(getString(R.string.cat_media_video), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_medical), R.drawable.medical));
        mItems.add(new ImageItem(getString(R.string.cat_personilazation), R.drawable.personalization));
        mItems.add(new ImageItem(getString(R.string.cat_photography), R.drawable.photography));
        mItems.add(new ImageItem(getString(R.string.cat_shopping), R.drawable.shopping));
        mItems.add(new ImageItem(getString(R.string.cat_social), R.drawable.social));
        mItems.add(new ImageItem(getString(R.string.cat_tool), R.drawable.tool));
        mItems.add(new ImageItem(getString(R.string.cat_wheather), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_lib_demo), R.drawable.library_demo));
        mItems.add(new ImageItem(getString(R.string.cat_game_arcade), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_game_puzzle), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_game_card), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_game_casual), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_game_racing), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_game_sport), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_game_action), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_game_adventure), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_game_board), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_game_casino), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_game_educational), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_game_family), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_game_music), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_game_role_playing), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_game_simulation), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_game_strategy), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_game_trivia), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_game_word), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_app_wallpaper), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_app_widget), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_pick_recomendation), R.mipmap.entertainment));
        mItems.add(new ImageItem(getString(R.string.cat_recomendation), R.mipmap.entertainment));

        return mItems;
    }

    private void saveCategoryInDatabaseFirstTime(){

        for (int i = 0; i < defaultCategoryLoad().size(); i++){
            AggroCategory aggroCategory = new AggroCategory();
            ImageItem imageItem = defaultCategoryLoad().get(i);
            aggroCategory.categoryName = imageItem.getTitle();
            aggroCategory.categoryImage = imageItem.getImage();
            aggroCategory.reccomendation = 0;
            aggroCategory.save();
        }
    }

    /**
     * An inner class that will that handle
     * Spinner Selection event
     *
     */

    public class SystemSpinner implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            spinnerMF.setSelection(position);
            switch (position) {
                case 0:
                    //Male Selection
                    gender = "M";
                    break;
                case 1:
                    //Female Selection
                    gender = "F";
                    break;
                default:
                    break;
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

   private void addItemToSpinner(){
       ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_aray, R.layout.spinner_item);
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinnerMF.setPrompt(getResources().getString(R.string.spinner_prompt));

       spinnerMF.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.contact_spinner_row_nothing_selected, this));
   }

    private void addListenerToSpinner(){
        spinnerMF.setOnItemSelectedListener(new SystemSpinner());
    }

    private void trackLocation(){
            final TrackerSettings settings =
                    new TrackerSettings()
                            .setUseGPS(true)
                            .setUseNetwork(true)
                            .setUsePassive(true)
                            .setTimeBetweenUpdates(30 * 60 * 1000)
                            .setMetersBetweenUpdates(100);
            myTracker =  new LocationTracker(Registration.this, settings) {

                @Override
                public void onLocationFound(Location location) {
                    // Do some stuff when a new location has been found.
                    User user = getAddress(Registration.this, location.getLatitude(), location.getLongitude());
                    location_ed.setText(user.city);
                    stopListen();
                }

                @Override
                public void onTimeout() {

                }

            };
    }

    public User getAddress(Context context ,double latitude, double longitude){
        User user = null;
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            user = new User(addresses.get(0).getAddressLine(0),addresses.get(0).getLocality(),addresses.get(0).getAdminArea(),addresses.get(0).getCountryName(),addresses.get(0).getPostalCode());
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Check all the Editfield and show error
     * response if empty or
     * Invalid
     */
    
    private void feildValidator(){

        if (validator( name_ed)){
            name_ed.setError(getResources().getString(R.string.name_err_msg));
            return;
        }
        if (validator( user_name_ed)){
            user_name_ed.setError(getResources().getString(R.string.uname_err_msg));
            return;
        }

        if (validator(age_ed)){
            age_ed.setError(getResources().getString(R.string.age_err_msg));
            return;
        }
        if (genderValidator(spinnerMF)){
            TextView errorText = (TextView) spinnerMF.getSelectedView();
            errorText.setError(getResources().getString(R.string.gender_err_msg));
            return;
        }

        if (validator(email_ed)){
            email_ed.setError(getResources().getString(R.string.email_err_msg));
            return;
        }
        if (checkEmailPattern(email_ed.getText().toString().trim())){
            email_ed.setError(getResources().getString(R.string.email_err_val_msg));
            return;
        }
        else {
            String url = "http://oxiloindia.com/aggro/register.php";
            RequestQueue mRequestQueue = Volley.newRequestQueue(mContext);
            GsonRequest<RegisterResponse> myReq = new GsonRequest<RegisterResponse>(
                    Request.Method.POST,
                    url,
                    RegisterResponse.class,
                    prepareHasMap(),
                    createMyReqSuccessListener(),
                    createMyReqErrorListener());

            myReq.setRetryPolicy(new DefaultRetryPolicy(
                    AppConstant.MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            mRequestQueue.add(myReq);
        }

    }

    private boolean validator(EditText ed){
        if (ed.getText().toString().equals(""))
            return true;
        else
            return false;
    }

    private boolean genderValidator(Spinner sp){
        if (sp.getSelectedItemPosition() == 0)
            return true;
        else
            return false;

    }

    public boolean checkEmailPattern(String email) {
        Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9+._%-+]{1,100}"
                + "@" + "[a-zA-Z0-9][a-zA-Z0-9-]{0,10}" + "(" + "."
                + "[a-zA-Z0-9][a-zA-Z0-9-]{0,20}" + ")+");
        if (!EMAIL_PATTERN.matcher(email.toString())
                .matches())
            return true;

        return false;
    }

    private void getInstalledApps(){
        if (count == 0){
            List<PackageInfo> PackList = getPackageManager().getInstalledPackages(0);

            for (int i=0; i < PackList.size(); i++)
            {
                try {
                    PackageInfo PackInfo = PackList.get(i);
                    if (  (PackInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
                    {
                        AppTracker appTracker = new AppTracker();
                        appTracker.isInstalled = true;
                        appTracker.isSystenApp = true;
                        appTracker.packageName = PackInfo.packageName;
                        appTracker.appName = PackInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                        appTracker.catName = "Phone app";
                        appTracker.marketUrl = "xxx";
                        appTracker.appIconUrl = "xxx";
                        appTracker.save();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        }else{
          count = count + 1;
        }
    }


    private HashMap prepareHasMap(){
        HashMap<String,String> hm = new HashMap<String,String>();
        hm.put("firstName", name_ed.getText().toString());
        hm.put("lastName", name_ed.getText().toString());
        hm.put("userName", user_name_ed.getText().toString());
        hm.put("email", email_ed.getText().toString());
        hm.put("age", age_ed.getText().toString().trim());
        hm.put("location", location_ed.getText().toString());
        hm.put("gender", gender);
        return hm;
    }

    private Response.Listener<RegisterResponse> createMyReqSuccessListener(){
        return new Response.Listener<RegisterResponse>(){
            @Override
            public void onResponse(RegisterResponse registerResponse) {

                Log.e("HXSXS","" + registerResponse.getStatus());
                int status = registerResponse.getStatus();
                if (registerResponse.getStatus() == 1){
                    List<Msg> msgList = registerResponse.getMsg();
                    saveUserInfo(msgList);
//                    Utility.writeUserInfoToPrefs(mContext, msgList.get(0).getFirstName(), msgList.get(0).getUserName().toString(), msgList.get(0).getEmail().toString().trim(), gender, msgList.get(0).getLocation().toString(), age_ed.getText().toString());
                    startActivity(new Intent(Registration.this, com.app.aggro.MainActivity.class));
                    finish();

                }

            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener(){
        return new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                String errorMsg = VolleyErrorHelper.getMessage(volleyError, mContext);
//                if (error.getLocalizedMessage().toString()!=null || !(error.getLocalizedMessage().toString().equals("null")))
//                Log.e("EROOR MESSG","" + error.getLocalizedMessage().toString());
                Toast.makeText(mContext, volleyError.getMessage(), Toast.LENGTH_LONG)
                        .show();

            }
        };
    }

    private void saveUserInfo(List<Msg> msgList){

        UserInfo userInfo = new UserInfo();
        userInfo.name = msgList.get(0).getFirstName();
        userInfo.userName = msgList.get(0).getUserName().toString();
        userInfo.email = msgList.get(0).getEmail().toString();
        userInfo.age = age_ed.getText().toString().trim();
        userInfo.location = msgList.get(0).getLocation().toString();
        userInfo.geneder = gender;
        userInfo.isNotificationEnabled = true;
        userInfo.wifiEnabledDownload = false;
        userInfo.downloadWhileCharging = false;
        userInfo.save();
    }
}
