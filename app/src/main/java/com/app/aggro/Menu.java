package com.app.aggro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.app.appfragement.MyDetailsFragement;
import com.app.getterAndSetter.MyToolBar;
import com.app.slideradapter.MyFragmentAdapter;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.model.interfaces.OnCheckedChangeListener;

/**
 * Created by ericbasendra on 01/07/15.
 */
public class Menu extends AppCompatActivity implements com.app.appfragement.Menu.OnFragmentInteractionListener, MyDetailsFragement.OnFragmentInteractionListener{

    private static final int PROFILE_SETTING = 1;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;


    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Remove line to test RTL support
        //getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        // Handle Toolbar
//        MyToolBar myToolBar = new MyToolBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        MyToolBar.setToolbar(toolbar);
        setSupportActionBar(MyToolBar.getToolbar());


        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(android.R.color.transparent)
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder().
                 withFullscreen(false)
                .withActivity(this)
                .withSliderBackgroundDrawable(getResources().getDrawable(R.mipmap.drawer_bg))
                .withDisplayBelowToolbar(true)
                .withSelectedItem(-1)
                .withToolbar(toolbar)//set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_compact_header).withIcon(R.mipmap.library).withIdentifier(1).withCheckable(false).withTextColor(getResources().getColor(R.color.drawer_item_text_color)).withTypeface(Registration.mpRegular),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_action_bar).withIcon(R.mipmap.my_detail).withIdentifier(2).withCheckable(false).withTextColor(getResources().getColor(R.color.drawer_item_text_color)).withTypeface(Registration.mpRegular),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_multi_drawer).withIcon(R.mipmap.fav).withIdentifier(3).withCheckable(false).withTextColor(getResources().getColor(R.color.drawer_item_text_color)).withTypeface(Registration.mpRegular),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIdentifier(4).withCheckable(false).withTextColor(getResources().getColor(R.color.drawer_item_text_color)).withTypeface(Registration.mpRegular).withTypeface(Registration.mpRegular),
                        new SecondaryDrawerItem().withName("Help & Feedback").withIdentifier(5).withCheckable(false).withTextColor(getResources().getColor(R.color.drawer_item_text_color)).withTypeface(Registration.mpRegular).withTypeface(Registration.mpRegular)
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem

                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                getSupportActionBar().setTitle(getResources().getString(R.string.drawer_item_compact_header));
                                selectItem(MyFragmentAdapter.CENTERED_PAGE);
                            } else if (drawerItem.getIdentifier() == 2) {
                                getSupportActionBar().setTitle(getResources().getString(R.string.drawer_item_action_bar));
                                selectItemMyDetails(2);
                            } else if (drawerItem.getIdentifier() == 3) {
                                getSupportActionBar().setTitle(getResources().getString(R.string.drawer_item_multi_drawer));
                                selectItem(MyFragmentAdapter.RIGHT_PAGE);
                            } else if (drawerItem.getIdentifier() == 4) {

                            } else if (drawerItem.getIdentifier() == 5) {

                            }
                            if (intent != null) {
                                Menu.this.startActivity(intent);
                            }
                        }
//
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            // set the selection to the item with the identifier 10
            result.setSelectionByIdentifier(10, false);

        }

        selectItem(MyFragmentAdapter.LEFT_PAGE);
    }

    @Override
    public void onBackPressed() {
        if(isSearchOpened) {
            handleMenuSearch();
            return;
        }
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            Log.e("GET FRAGEMENT MANAGER", "" + getFragmentManager().getBackStackEntryCount());
            Menu.this.finish();
        } else {
            Log.e("ELSE CONDITION", "" + getFragmentManager().getBackStackEntryCount());
            Fragment f = Menu.this.getSupportFragmentManager().findFragmentById(R.id.content_frame);
                getFragmentManager().popBackStack();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(android.view.Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_open_search));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });

            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);

            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.ic_action));

            isSearchOpened = true;
        }
    }

    private void doSearch() {
//
    }

    private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
            if (drawerItem instanceof Nameable) {
                Log.i("material-drawer", "DrawerItem: " + ((Nameable) drawerItem).getName() + " - toggleChecked: " + isChecked);
            } else {
                Log.i("material-drawer", "toggleChecked: " + isChecked);
            }
        }
    };

    private void selectItem(int item) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        if (item == 1){
            fragment = com.app.appfragement.Menu.newInstance("", item);
        }else if (item == 2){
            fragment = com.app.appfragement.Menu.newInstance("", item);
        }
        else {
            fragment = com.app.appfragement.Menu.newInstance("", item);
        }
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction  = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void selectItemMyDetails(int item) {
        // update the main content by replacing fragments
        Fragment fragment = MyDetailsFragement.newInstance("", "");
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction  = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
