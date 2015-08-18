package com.app.appfragement.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.adapter.AggroRecyclerViewAdapter;
import com.app.aggro.R;
import com.app.getterAndSetter.MyToolBar;
import com.app.gridcategory.ImageItem;
import com.app.holder.ChildItem;
import com.app.holder.GroupItem;
import com.app.local.database.AppTracker;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private static final int ITEM_COUNT = 100;

    private GroupItem groupItem = new GroupItem();

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        groupItem.items = preareList();
        mAdapter = new RecyclerViewMaterialAdapter(new AggroRecyclerViewAdapter(preareList(),getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }

    private List<ChildItem> preareList() {
        if (groupItem != null)
            groupItem.items.clear();

        List<AppTracker> eventList = null;
            eventList = AppTracker.getAll();

        int size = eventList.size();

        for (int i = 0; i < eventList.size(); i++){
            ChildItem childItem = new ChildItem();
            AppTracker event = eventList.get(i);
            childItem.mAppIconUrl = event.appIconUrl;
            childItem.mAppCategory = event.catName;
            childItem.mAppname = event.appName;
            childItem.mRating = event.rating;
            groupItem.items.add(childItem);
        }

        return groupItem.items;
    }

}
