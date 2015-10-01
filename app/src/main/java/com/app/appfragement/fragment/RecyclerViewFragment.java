package com.app.appfragement.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.app.AppConstant;
import com.app.Updateable;
import com.app.Utility.Utility;
import com.app.adapter.AggroRecyclerViewAdapter;
import com.app.aggro.R;
import com.app.aggro.Registration;
import com.app.api.GsonRequest;
import com.app.getterAndSetter.MyToolBar;
import com.app.holder.ChildItem;
import com.app.holder.GroupItem;
import com.app.local.database.AppTracker;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private static final int ITEM_COUNT = 100;

    private GroupItem groupItem = new GroupItem();

    Updateable updateable;
    Context mContext;

    public RecyclerViewFragment(){

    }
    public RecyclerViewFragment(Updateable updateable, Context mContext){
        this.updateable = updateable;
        this.mContext = mContext;
    }
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
        if (groupItem.items.size()>1) {
            mAdapter = new RecyclerViewMaterialAdapter(new AggroRecyclerViewAdapter(preareList(), getActivity(), new AggroRecyclerViewAdapter.CreateFav() {
                @Override
                public void addToFav(ChildItem childItem) {
//                    addingAppToFav(childItem);
                    AppTracker appTracker = AppTracker.getSingleEntry(childItem.mPackageName);
                    Log.e("PACJA","" + appTracker.packageName);
                    if (childItem.isFavourite == 0){
                        childItem.isFavourite = 1;
                    }else{
                        childItem.isFavourite = 0;
                    }
                    if (appTracker != null){
                        appTracker.isFavourite = childItem.isFavourite;
                        Log.e("APP TRACKER FRAGEMENT","" + appTracker.isFavourite);
                        appTracker.save();
                    }
//                    MyToolBar.getMaterialViewPager().getViewPager().getAdapter().notifyDataSetChanged();
                    MyToolBar.getMyFragmentAdapter().updateData();
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void openApp(ChildItem childItem) {
                     openDownloadedApp(childItem);
                }
            }));
            mRecyclerView.setAdapter(mAdapter);
        }
//        createFavCat();
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
            int k = i;
            AppTracker event;
            event =  eventList.get(i);

            childItem.mAppIconUrl = event.appIconUrl;
            childItem.mAppCategory = event.catName;
            childItem.mAppname = event.appName;
            childItem.mRating = event.rating;
            childItem.mPackageName = event.packageName;
            childItem.isFavourite = event.isFavourite;
            groupItem.items.add(childItem);
        }

        return groupItem.items;
    }

    private void createFavCat(){
        if (Registration.count == 1){
            String url = "http://jarvisme.com/customapp/sendcat.php";
            RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
            GsonRequest<com.app.response.Response> myReq = new GsonRequest<com.app.response.Response>(
                    Request.Method.POST,
                    url,
                    com.app.response.Response.class,
                    prepareHasMap(Utility.readUserInfoFromPrefs(getActivity(), getString(R.string.email)),"fav"),
                    createMyReqSuccessListener("fav"),
                    createMyReqErrorListener());

            myReq.setRetryPolicy(new DefaultRetryPolicy(
                    AppConstant.MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            mRequestQueue.add(myReq);
        }
    }
    private HashMap prepareHasMap(String email, String category){
        HashMap<String,String> hm = new HashMap<String,String>();
        hm.put("email", email);
        hm.put("category", category);
        return hm;
    }


    private Response.Listener<com.app.response.Response> createMyReqSuccessListener(final String nameOfCategory){
        return new Response.Listener<com.app.response.Response>() {
            @Override
            public void onResponse(com.app.response.Response respose) {

                Log.e("HXSXS", "" + respose.getStatus());
                int status = respose.getStatus();
                if (respose.getStatus() == 1){


                }

            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener(){
        return new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError.getMessage());
//                String errorMsg = VolleyErrorHelper.getMessage(volleyError, getActivity());
//                if (error.getLocalizedMessage().toString()!=null || !(error.getLocalizedMessage().toString().equals("null")))
//                Log.e("EROOR MESSG","" + error.getLocalizedMessage().toString());
//                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG)
//                        .show();

            }
        };
    }


    public void addingAppToFav(ChildItem appList) {
        String url = "http://jarvisme.com/customapp/add.php?";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        GsonRequest<com.app.response.Response> myReq = new GsonRequest<com.app.response.Response>(
                Request.Method.POST,
                url,
                com.app.response.Response.class,
                prepareHasMap(appList),
                createMyFavSuccessListener(),
                createMyFavErrorListener());

        myReq.setRetryPolicy(new DefaultRetryPolicy(
                AppConstant.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(myReq);
    }

    private HashMap prepareHasMap(ChildItem appList){
        HashMap<String,String> hm = new HashMap<String,String>();
        hm.put("email", Utility.readUserInfoFromPrefs(getActivity(),getString(R.string.email)));
        hm.put("customCategory", "fav");
        hm.put("appName",appList.mAppname);
        hm.put("iconurlkey",appList.mAppIconUrl);
        hm.put("category",appList.mAppCategory);
        hm.put("appRating", "" + appList.mRating);
        hm.put("packagename", appList.mPackageName);
        return hm;
    }

    private com.android.volley.Response.Listener<com.app.response.Response> createMyFavSuccessListener() {
        return new com.android.volley.Response.Listener<com.app.response.Response>() {
            @Override
            public void onResponse(com.app.response.Response response) {
                try {
                    if (response.getStatus() == 1){
                        Toast.makeText(getActivity(), "App added successfully", Toast.LENGTH_LONG).show();
                        return;
                    }

                    else{
                        Toast.makeText(getActivity(), "OOPS something wrong happen please try again!!!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        };
    }

    private com.android.volley.Response.ErrorListener createMyFavErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
//                String errorMsg = VolleyErrorHelper.getMessage(error, getActivity());
//                if (error.getLocalizedMessage().toString()!=null || !(error.getLocalizedMessage().toString().equals("null")))
//                Log.e("EROOR MESSG","" + error.getLocalizedMessage().toString());
//                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG)
//                        .show();
            }
        };
    }

    public boolean openDownloadedApp(ChildItem appList) {
        PackageManager manager = getActivity().getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(appList.mPackageName);
            if (i == null) {
//                return false;
                throw new PackageManager.NameNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(i);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}
