package com.app.aggro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.app.Utility.Utility;
import com.app.Validator.EmptyTextListener;
import com.app.Validator.InputValidator;
import com.app.address.User;
import com.app.gps.GPSTracker;
import com.app.spinneradapter.NothingSelectedSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class Registration extends Activity {

    private ImageView signIn;
    private Spinner spinnerMF;
    private GPSTracker gpsTracker ;
    private EditText name_ed, user_name_ed,age_ed, gender_ed,location_ed, email_ed;
    public static Typeface mpRegular, mpBold, mpSnap;
    private Activity mContext = Registration.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Utility.readUserInfoFromPrefs(mContext,getResources().getString(R.string.username)).equals("")){
            startActivity(new Intent(Registration.this, com.app.aggro.Menu.class));
            finish();
        }

        setContentView(R.layout.activity_registration);
        new Utility().setupUI(findViewById(R.id.reg), Registration.this);
        init();
        trackLocation();
        addItemToSpinner();
        addListenerToSpinner();
    }


    @Override
    protected void onPause() {
        super.onPause();
        gpsTracker.stopUsingGPS();
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

    private void init() {
        mpSnap = Typeface.createFromAsset(getAssets(),
                "fonts/snap-itc.ttf");
        mpRegular = Typeface.createFromAsset(getAssets(),
                "fonts/Helvetica Neue CE 55 Roman.ttf");
        mpBold = Typeface.createFromAsset(getAssets(),
                "fonts/Helvetica Neue CE 75 Bold.ttf");
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
                feildValidator();
            }
        });


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
                    break;
                case 1:
                    //Female Selection
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
            if (gpsTracker.canGetLocation())
            {
                User user = gpsTracker.getAddress(Registration.this,gpsTracker.getLatitude(), gpsTracker.getLongitude());
                location_ed.setText(user.city);
            }
            else
            {
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings

                gpsTracker.showSettingsAlert();
            }


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
            //submit
            Utility.writeUserInfoToPrefs(mContext,name_ed.getText().toString(),user_name_ed.getText().toString(),email_ed.getText().toString().trim());
            startActivity(new Intent(Registration.this, com.app.aggro.Menu.class));
            finish();
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


}
