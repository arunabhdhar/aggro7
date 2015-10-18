package com.app.appfragement.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.app.Utility.CustomRecyclerView;
import com.app.Utility.EndlessRecyclerOnScrollListener;
import com.app.Utility.Utility;
import com.app.adapter.AggroRecyclerThreeWayGridViewAdapter;
import com.app.adapter.AggroRecyclerViewAdapter;
import com.app.adapter.favAdapter;
import com.app.aggro.MyApplication;
import com.app.aggro.R;
import com.app.api.GsonRequest;
import com.app.api.VolleyErrorHelper;
import com.app.getterAndSetter.MyToolBar;
import com.app.gridcategory.ImageItem;
import com.app.holder.ChildItem;
import com.app.holder.GroupItem;
import com.app.local.database.AppTracker;
import com.app.response.CustomMsg;
import com.app.response.CustomResponse;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.google.android.gms.analytics.HitBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewThreeWayGridFragment extends Fragment {

    private CustomRecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private static final int ITEM_COUNT = 100;

    private GroupItem groupItem = new GroupItem();
    private int count = 1;


    public static RecyclerViewThreeWayGridFragment newInstance() {
        return new RecyclerViewThreeWayGridFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mRecyclerView = (CustomRecyclerView) view.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

//        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int current_page) {
//                // do something...
//                count = current_page;
//                loadApiGetMethod("fav");
//            }
//        });

        mAdapter = new RecyclerViewMaterialAdapter(new favAdapter(preareList(), getActivity(), new favAdapter.CreateFav() {
            @Override
            public void openApp(ChildItem childItem) {
                openDownloadedApp(childItem);
            }
        }));
        mRecyclerView.setAdapter(mAdapter);

//         loadApiGetMethod("fav");

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }



    private void loadApiGetMethod(String category){
        int limit = 5;
        String country = "IN";
        Log.e("Data", "" + category);
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        String url = "http://jarvisme.com/customapp/get.php?";
//        String url = "https://42matters.com/api/1/apps/top_google_charts.json?" + "list_name=topselling_free"+ "&cat_key=" + category + "&country=" + country + "&limit=" + limit + "&page=" + count + "&access_token=" + getActivity().getResources().getString(R.string.aggro_access_token);
        Log.e("URL", "" + url);
        GsonRequest<CustomResponse> myReq = new GsonRequest<CustomResponse>(
                Request.Method.POST,
                url,
                CustomResponse.class,
                prepareHasMap(),
                createMyReqSuccessListener(),
                createMyReqErrorListener());

        myReq.setRetryPolicy(new DefaultRetryPolicy(
                AppConstant.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(myReq);
    }

    private HashMap prepareHasMap(){
        HashMap<String,String> hm = new HashMap<String,String>();
        hm.put("category_name", "fav");
        hm.put("email", Utility.readUserInfoFromPrefs(getActivity(), getString(R.string.email)));
        hm.put("limit", "" + 5);
        hm.put("page", "" + count);
        return hm;
    }

    private Response.Listener<CustomResponse> createMyReqSuccessListener() {
        return new Response.Listener<CustomResponse>() {
            @Override
            public void onResponse(CustomResponse response) {
                try {
//                    GroupItem groupItem = new GroupItem();
                    List<CustomMsg> lists= response.getMsg();
                    for (int i = 0; i< lists.size();i++){
                        CustomMsg appList = lists.get(i);
                        ChildItem childItem = new ChildItem();
                        childItem.mPackageName = appList.getPackagename();
                        childItem.mAppname = appList.getAppName();
                        childItem.mRating = appList.getAppRating().floatValue();
                        childItem.mAppCategory = appList.getCategory();
                        childItem.mAppIconUrl = appList.getIconLink();
                        Log.e("APPNAMe", "" + childItem.mAppname);
                        groupItem.items.add(childItem);

                        mAdapter.notifyItemInserted(i);

                    }

//                    if (groupItem.items.size()>1) {
//                        mAdapter = new RecyclerViewMaterialAdapter(new favAdapter(groupItem.items, getActivity()));
//                        mRecyclerView.setAdapter(mAdapter);
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
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

    private List<ChildItem> preareList() {
        if (groupItem != null)
            groupItem.items.clear();

        List<AppTracker> eventList = null;
        eventList = new AppTracker().getAllByFav(1);
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

            Log.e("SSSS","" + childItem.isFavourite);
            groupItem.items.add(childItem);
        }

        return groupItem.items;
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
