package com.app.appfragement;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.Utility.AnimCheckBox;
import com.app.aggro.MainActivity;
import com.app.aggro.R;
import com.app.local.database.UserInfo;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements AnimCheckBox.OnCheckedChangeListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CoordinatorLayout rootLayout;
    Toolbar toolbar;

    AnimCheckBox checkbox_notification,dwn_wifi_check,dwn_charging_check;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInstances(view);
        initCheckBox(view);
    }

    private void initCheckBox(View view) {
        checkbox_notification = (AnimCheckBox)view.findViewById(R.id.checkbox_notification);
        dwn_wifi_check = (AnimCheckBox)view.findViewById(R.id.dwn_wifi_check);
        dwn_charging_check = (AnimCheckBox)view.findViewById(R.id.dwn_charging_check);

        checkbox_notification.setOnCheckedChangeListener(this);
        dwn_wifi_check.setOnCheckedChangeListener(this);
        dwn_charging_check.setOnCheckedChangeListener(this);

        UserInfo userInfo = UserInfo.getRandom();
        if (userInfo == null)
            return;
        checkbox_notification.setChecked(userInfo.isNotificationEnabled);
        dwn_wifi_check.setChecked(userInfo.wifiEnabledDownload);
        dwn_charging_check.setChecked(userInfo.downloadWhileCharging);

    }


    private void initInstances(View view) {

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Settings");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                MainActivity.fragmentStack.lastElement().onPause();
                ft.remove(MainActivity.fragmentStack.pop());
                MainActivity.fragmentStack.lastElement().onResume();
                ft.show(MainActivity.fragmentStack.lastElement());
                ft.commit();
            }
        });
        rootLayout = (CoordinatorLayout) view.findViewById(R.id.htab_maincontent);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onChange(boolean checked) {
        UserInfo userInfo = UserInfo.getRandom();
        if (userInfo == null)
            return;

        if (checkbox_notification.isChecked()){
            userInfo.isNotificationEnabled = true;
        }else{
            userInfo.isNotificationEnabled = false;
        }

        if (dwn_wifi_check.isChecked()){
            userInfo.wifiEnabledDownload = true;
        }else {
            userInfo.wifiEnabledDownload = false;
        }

        if (dwn_charging_check.isChecked()){
            userInfo.downloadWhileCharging = true;
        }else {
            userInfo.downloadWhileCharging = false;
        }

        userInfo.save();
    }
}
