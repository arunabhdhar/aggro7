package com.app.gridcategory;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.aggro.R;
import com.app.api.Category;
import com.app.appfragement.ShowCatAppFragement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonal on 7/2/2015.
 */
public class TwoWayGrid extends BaseAdapter{

    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;
    private Context context;

    public TwoWayGrid(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);

        mItems.add(new Item(context.getResources().getString(R.string.cat_bussiness),R.mipmap.games));
        mItems.add(new Item(context.getResources().getString(R.string.cat_lifestyle),R.mipmap.games));
        mItems.add(new Item(context.getResources().getString(R.string.cat_news),   R.mipmap.news));
        mItems.add(new Item(context.getResources().getString(R.string.cat_education),R.mipmap.teaching));
        mItems.add(new Item(context.getResources().getString(R.string.cat_transporation),R.mipmap.games));
        mItems.add(new Item(context.getResources().getString(R.string.cat_productiity),R.mipmap.games));
        mItems.add(new Item(context.getResources().getString(R.string.cat_games),R.mipmap.games));
        mItems.add(new Item(context.getResources().getString(R.string.cat_travel),R.mipmap.games));
        mItems.add(new Item(context.getResources().getString(R.string.cat_sports), R.mipmap.sports));
        mItems.add(new Item(context.getResources().getString(R.string.cat_health),  R.mipmap.health));
        mItems.add(new Item(context.getResources().getString(R.string.cat_entertainment),R.mipmap.entertainment));
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemHolder holder = null;
        View v = view;
        ImageView picture;
        TextView name;
        FrameLayout frameLayout;

//        if (v == null) {
//            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
//            v.setTag(R.id.picture, v.findViewById(R.id.picture));
//            v.setTag(R.id.text, v.findViewById(R.id.text));
//        }

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            holder = new ItemHolder();
            holder.frameLayout = (FrameLayout)v.findViewById(R.id.frame);
            holder.picture = (ImageView)v.findViewById(R.id.picture);
            holder.name = (TextView)v.findViewById(R.id.text);
            v.setTag(holder);

        } else {
            holder = (ItemHolder) v.getTag();
        }

//        picture = (ImageView) v.getTag(R.id.picture);
//        name = (TextView) v.getTag(R.id.text);

        Item item = getItem(i);

//        picture.setImageResource(item.drawableId);
//        name.setText(item.name);


        holder.frameLayout.setTag(item.name);
        holder.picture.setImageResource(item.drawableId);
        holder.name.setText(item.name);

        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String category = v.getTag().toString();
                Log.e("CSt","" + category);
                selectItem(category);

            }
        });

        return v;
    }

    private void selectItem(String category) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        String localvariable= null;
        Category.AggroCategory catLevel = null;
        if (category.trim().equals(context.getResources().getString(R.string.cat_bussiness))){
            localvariable = context.getResources().getString(R.string.cat_bussiness);
             catLevel = Category.AggroCategory.BUSINESS;
        }
        else if (category.trim().equals(context.getResources().getString(R.string.cat_lifestyle))){
            localvariable = context.getResources().getString(R.string.cat_lifestyle);
            catLevel = Category.AggroCategory.LIFESTYLE;
        }
        else if (category.trim().equals(context.getResources().getString(R.string.cat_news))){
            localvariable = context.getResources().getString(R.string.cat_news);
            catLevel = Category.AggroCategory.NEWS_AND_MAGAZINES;
        }
        else if (category.trim().equals(context.getResources().getString(R.string.cat_education))){
            localvariable = context.getResources().getString(R.string.cat_education);
            catLevel = Category.AggroCategory.EDUCATION;
        }
        else if (category.trim().equals(context.getResources().getString(R.string.cat_transporation))){
            localvariable = context.getResources().getString(R.string.cat_transporation);
            catLevel = Category.AggroCategory.TRANSPORTATION;
        }
        else if (category.trim().equals(context.getResources().getString(R.string.cat_productiity))){
            localvariable = context.getResources().getString(R.string.cat_productiity);
            catLevel = Category.AggroCategory.PRODUCTIVITY;
        }
        else if (category.trim().equals(context.getResources().getString(R.string.cat_games))){
            localvariable = context.getResources().getString(R.string.cat_games);
            catLevel = Category.AggroCategory.GAMES;
        }
        else if (category.trim().equals(context.getResources().getString(R.string.cat_travel))){
            localvariable = context.getResources().getString(R.string.cat_travel);
            catLevel = Category.AggroCategory.TRAVEL_AND_LOCAL;
        }
        else if (category.trim().equals(context.getResources().getString(R.string.cat_sports))){
            localvariable = context.getResources().getString(R.string.cat_sports);
            catLevel = Category.AggroCategory.SPORTS;
        }
        else if (category.trim().equals(context.getResources().getString(R.string.cat_health))){
            localvariable = context.getResources().getString(R.string.cat_health);
            catLevel = Category.AggroCategory.HEALTH_AND_FITNESS;
        }
        else if (category.trim().equals(context.getResources().getString(R.string.cat_entertainment))){
            localvariable = context.getResources().getString(R.string.cat_entertainment);
            catLevel = Category.AggroCategory.MUSIC_AND_AUDIO;
        }
        else {
            //custom category
        }
//        Activity activity = (Activity) context;
//        fragment = ShowCatAppFragement.newInstance(localvariable,catLevel);
//        FragmentManager fragmentManager = activity.getFragmentManager();
//        FragmentTransaction transaction  = fragmentManager.beginTransaction();
////        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
//        // Replace whatever is in the fragment_container view with this fragment,
//        // and add the transaction to the back stack so the user can navigate back
//        transaction.replace(R.id.content_frame, fragment);
//        transaction.addToBackStack(null);
//
//        // Commit the transaction
//        transaction.commit();
    }

    private static class Item {
        public final String name;
        public final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }

    static class ItemHolder {
        ImageView picture;
        TextView name;
        FrameLayout frameLayout;
    }
}

