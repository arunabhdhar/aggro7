package com.app.gridcategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aggro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonal on 7/2/2015.
 */
public class TwoWayGrid extends BaseAdapter{

    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;

    public TwoWayGrid(Context context) {
        mInflater = LayoutInflater.from(context);

        mItems.add(new Item(context.getResources().getString(R.string.cat_games),R.mipmap.games));
        mItems.add(new Item(context.getResources().getString(R.string.cat_entertainment),R.mipmap.entertainment));
        mItems.add(new Item(context.getResources().getString(R.string.cat_sports), R.mipmap.sports));
        mItems.add(new Item(context.getResources().getString(R.string.cat_teaching),R.mipmap.teaching));
        mItems.add(new Item(context.getResources().getString(R.string.cat_news),   R.mipmap.news));
        mItems.add(new Item(context.getResources().getString(R.string.cat_health),  R.mipmap.health));
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
        View v = view;
        ImageView picture;
        TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView) v.getTag(R.id.picture);
        name = (TextView) v.getTag(R.id.text);

        Item item = getItem(i);

        picture.setImageResource(item.drawableId);
        name.setText(item.name);

        return v;
    }

    private static class Item {
        public final String name;
        public final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}

