package com.app.aggro;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;

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
public class Menu extends AppCompatActivity implements com.app.appfragement.Menu.OnFragmentInteractionListener{

    private static final int PROFILE_SETTING = 1;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Remove line to test RTL support
        //getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
                .withToolbar(toolbar)//set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_compact_header).withIcon(R.mipmap.library).withIdentifier(1).withCheckable(false),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_action_bar).withIcon(R.mipmap.my_detail).withIdentifier(2).withCheckable(false),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_multi_drawer).withIcon(R.mipmap.fav).withIdentifier(3).withCheckable(false),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIdentifier(4).withCheckable(false),
                        new SecondaryDrawerItem().withName("Help & Feedback").withIdentifier(5).withCheckable(false)
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem

//                        if (drawerItem != null) {
//                            Intent intent = null;
//                            if (drawerItem.getIdentifier() == 1) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, SimpleCompactHeaderDrawerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 2) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, ActionBarDrawerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 3) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, MultiDrawerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 4) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, SimpleNonTranslucentDrawerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 5) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, ComplexHeaderDrawerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 6) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, SimpleFragmentDrawerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 7) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, EmbeddedDrawerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 8) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, FullscreenDrawerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 9) {
//                                intent = new Intent(SimpleHeaderDrawerActivity.this, CustomContainerActivity.class);
//                            } else if (drawerItem.getIdentifier() == 20) {
//                                intent = new LibsBuilder()
//                                        .withFields(R.string.class.getFields())
//                                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
//                                        .intent(SimpleHeaderDrawerActivity.this);
//                            }
//                            if (intent != null) {
//                                Menu.this.startActivity(intent);
//                            }
//                        }


                        selectItem();
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

        selectItem();
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

    private void selectItem() {
        // update the main content by replacing fragments
        Fragment fragment = com.app.appfragement.Menu.newInstance("", "");
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
