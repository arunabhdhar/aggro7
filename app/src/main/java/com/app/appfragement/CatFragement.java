package com.app.appfragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.app.aggro.R;
import com.app.api.Category;
import com.app.gridcategory.GridViewAdapter;
import com.app.gridcategory.ImageItem;
import com.app.gridcategory.TwoWayGrid;

import java.util.ArrayList;

/**
 * Created by sonal on 7/2/2015.
 */
public class CatFragement extends Fragment{

    private GridViewAdapter gridAdapter;

    public static CatFragement newInstance(String imageUrl) {

        final CatFragement mf = new CatFragement();

        final Bundle args = new Bundle();
        args.putString("somedata", "somedata");
        mf.setArguments(args);

        return mf;
    }

    public CatFragement() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String data = getArguments().getString("somedata");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate and locate the main ImageView
        final View v = inflater.inflate(R.layout.fragement_cat, container, false);
        init(v);
        return v;
    }

    private void init(View view){
        GridView gridView = (GridView)view.findViewById(R.id.gridview);
//        gridView.setAdapter(new TwoWayGrid(getActivity()));

        gridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item, getData());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                //Start details Fragement
                selectItem(item.getTitle());
            }
        });
    }

    /**
     * Prepare some dummy data for gridview
     */
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> mItems = new ArrayList<>();
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_bussiness),R.mipmap.games));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_lifestyle),R.mipmap.games));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_news),   R.mipmap.news));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_education),R.mipmap.teaching));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_transporation),R.mipmap.games));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_productiity),R.mipmap.games));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_games),R.mipmap.games));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_travel),R.mipmap.games));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_sports), R.mipmap.sports));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_health), R.mipmap.health));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_entertainment), R.mipmap.entertainment));
        return mItems;
    }

    private void selectshowCatApp(String local, Category.AggroCategory level) {
        // update the main content by replacing fragments
        Fragment fragment = ShowCatAppFragement.newInstance(local, level);
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction  = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void selectItem(String category) {
        // update the main content by replacing fragments
        String localvariable = null;
        Category.AggroCategory catLevel = null;
        if (category.trim().equals(getActivity().getResources().getString(R.string.cat_bussiness))) {
            localvariable = getActivity().getResources().getString(R.string.cat_bussiness);
            catLevel = Category.AggroCategory.BUSINESS;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_lifestyle))) {
            localvariable = getActivity().getResources().getString(R.string.cat_lifestyle);
            catLevel = Category.AggroCategory.LIFESTYLE;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_news))) {
            localvariable = getActivity().getResources().getString(R.string.cat_news);
            catLevel = Category.AggroCategory.NEWS_AND_MAGAZINES;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_education))) {
            localvariable = getActivity().getResources().getString(R.string.cat_education);
            catLevel = Category.AggroCategory.EDUCATION;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_transporation))) {
            localvariable = getActivity().getResources().getString(R.string.cat_transporation);
            catLevel = Category.AggroCategory.TRANSPORTATION;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_productiity))) {
            localvariable = getActivity().getResources().getString(R.string.cat_productiity);
            catLevel = Category.AggroCategory.PRODUCTIVITY;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_games))) {
            localvariable = getActivity().getResources().getString(R.string.cat_games);
            catLevel = Category.AggroCategory.GAMES;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_travel))) {
            localvariable = getActivity().getResources().getString(R.string.cat_travel);
            catLevel = Category.AggroCategory.TRAVEL_AND_LOCAL;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_sports))) {
            localvariable = getActivity().getResources().getString(R.string.cat_sports);
            catLevel = Category.AggroCategory.SPORTS;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_health))) {
            localvariable = getActivity().getResources().getString(R.string.cat_health);
            catLevel = Category.AggroCategory.HEALTH_AND_FITNESS;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_entertainment))) {
            localvariable = getActivity().getResources().getString(R.string.cat_entertainment);
            catLevel = Category.AggroCategory.MUSIC_AND_AUDIO;
        } else {
            //custom category
        }

        selectshowCatApp(localvariable,catLevel);
    }

}
