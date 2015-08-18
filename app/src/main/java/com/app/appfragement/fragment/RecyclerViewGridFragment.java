package com.app.appfragement.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.adapter.AggroRecyclerGridViewAdapter;
import com.app.adapter.AggroRecyclerViewAdapter;
import com.app.aggro.R;
import com.app.getterAndSetter.MyToolBar;
import com.app.gridcategory.ImageItem;
import com.app.holder.ChildItem;
import com.app.holder.GroupItem;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewGridFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private static final int ITEM_COUNT = 100;

    private GroupItem groupItem = new GroupItem();

    public static RecyclerViewGridFragment newInstance() {
        return new RecyclerViewGridFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new AggroRecyclerGridViewAdapter(getData(),getActivity()));

        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
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
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_bussiness),R.mipmap.games));
        return mItems;
    }
}
