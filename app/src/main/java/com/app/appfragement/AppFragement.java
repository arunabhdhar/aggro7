package com.app.appfragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.app.adapter.AppLibrary;
import com.app.aggro.R;
import com.app.holder.ChildItem;
import com.app.holder.GroupItem;

import java.util.ArrayList;

/**
 * Created by sonal on 7/2/2015.
 */
public class AppFragement extends Fragment {


    private GroupItem groupItem;
    private AppLibrary adpapter;
    private ListView listView;
    public static AppFragement newInstance(String imageUrl) {

        final AppFragement mf = new AppFragement();

        final Bundle args = new Bundle();
        args.putString("somedata", "somedata");
        mf.setArguments(args);

        return mf;
    }

    public AppFragement() {
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
        final View v = inflater.inflate(R.layout.fragement_app, container, false);

        init(v);
        preareList();
        setListviewToAdapter();
        return v;
    }

    private void init(View view) {
        groupItem = new GroupItem();
        listView = (ListView)view.findViewById(R.id.app_lib_list);
    }

    private void preareList() {
         ArrayList<String> list = new ArrayList<String>();
        ArrayList<Integer> imageList = new ArrayList<Integer>();
        list.add(getActivity().getResources().getString(
                R.string.name_one));
        list.add(getActivity().getResources().getString(
                R.string.name_two));
        list.add(getActivity().getResources().getString(R.string.name_three));
        list.add(getActivity().getResources().getString(
                R.string.name_one));
        list.add(getActivity().getResources().getString(
                R.string.name_two));
        list.add(getActivity().getResources().getString(
                R.string.name_three));

        imageList.add(R.mipmap.sports);
        imageList.add(R.mipmap.health);
        imageList.add(R.mipmap.teaching);
        imageList.add(R.mipmap.sports);
        imageList.add(R.mipmap.health);
        imageList.add(R.mipmap.teaching);

            for (int i = 0; i < list.size(); i++) {
                ChildItem childItem = new ChildItem();
                childItem.mAppIcon = imageList.get(i);
                childItem.mAppname = list.get(i).toString();
                childItem.mAppCategory = "Games";
                groupItem.items.add(childItem);
            }
    }

    private void setListviewToAdapter() {
        if (groupItem != null) {
            adpapter = new AppLibrary(getActivity(), groupItem);
            listView.setAdapter(adpapter);
        } else {
            Toast.makeText(
                    getActivity(),
                    "No Games found",
                    Toast.LENGTH_LONG).show();
        }
    }
}