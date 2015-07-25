package com.app.appfragement;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.app.adapter.AppLibrary;
import com.app.adapter.SimpleAdapter;
import com.app.aggro.*;
import com.app.api.Category;
import com.app.api.GsonRequest;
import com.app.api.VolleyErrorHelper;
import com.app.getterAndSetter.MyToolBar;
import com.app.holder.GroupItem;
import com.app.modal.AppDetail;
import com.app.modal.AppList;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonal on 7/2/2015.
 */
public class ShowCatAppFragement extends Fragment{


    private GroupItem groupItem;
    private AppLibrary adpapter;
    private ListView listView;
    private static String CATEGORY = "CATEGORY";
    private static String CATEGORYLEVEL = "CATEGORYLEVEL";
    private String data = null;
    Category.AggroCategory aggroCategory;

    UltimateRecyclerView ultimateRecyclerView;
    SimpleAdapter simpleRecyclerViewAdapter = null;
    LinearLayoutManager linearLayoutManager;
    int moreNum = 2;
    private ActionMode actionMode;
    private ItemTouchHelper mItemTouchHelper;

    private int count = 1;
    private AppList appList;

    private Toolbar toolbar;

    public static ShowCatAppFragement newInstance(String imageUrl, Category.AggroCategory aggroCategory) {

        final ShowCatAppFragement mf = new ShowCatAppFragement();

        final Bundle args = new Bundle();
        args.putString(CATEGORY, imageUrl);
        args.putSerializable(CATEGORYLEVEL,aggroCategory);
        mf.setArguments(args);

        return mf;
    }

    public ShowCatAppFragement() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            data = getArguments().getString(CATEGORY);
            aggroCategory = (Category.AggroCategory)getArguments().getSerializable(CATEGORYLEVEL);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate and locate the main ImageView
        final View v = inflater.inflate(R.layout.fragement_show_cat_app, container, false);

        init(v);
        showCategorizedApp();
        return v;
    }

    private void init(View view) {
        toolbar = MyToolBar.getToolbar();
        appList = new AppList();
        groupItem = new GroupItem();
        listView = (ListView)view.findViewById(R.id.app_lib_list);
        ultimateRecyclerView = (UltimateRecyclerView)view. findViewById(R.id.ultimate_recycler_view);
        ultimateRecyclerView.setHasFixedSize(false);
//        final List<String> stringList = new ArrayList<>();
//        appList.setTitle("efefre");
//        groupItem.appLists.add(appList);
        simpleRecyclerViewAdapter = new SimpleAdapter(groupItem.appLists, getActivity());

        linearLayoutManager = new LinearLayoutManager(getActivity());
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);

        ultimateRecyclerView.enableLoadmore();
        simpleRecyclerViewAdapter.setCustomLoadMoreView(LayoutInflater.from(getActivity())
                .inflate(R.layout.custom_bottom_progressbar, null));

        ultimateRecyclerView.setParallaxHeader(getActivity().getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, ultimateRecyclerView.mRecyclerView, false));
        ultimateRecyclerView.setOnParallaxScroll(new UltimateRecyclerView.OnParallaxScroll() {
            @Override
            public void onParallaxScroll(float percentage, float offset, View parallax) {
//                Drawable c = toolbar.getBackground();
//                c.setAlpha(Math.round(127 + percentage * 128));
                toolbar.setBackgroundDrawable(toolbar.getBackground());
            }
        });

        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.parseColor("#ffffff"));
        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        simpleRecyclerViewAdapter.insert(appList, simpleRecyclerViewAdapter.getAdapterItemCount());
                        ultimateRecyclerView.setRefreshing(false);
                        //   ultimateRecyclerView.scrollBy(0, -50);
                        linearLayoutManager.scrollToPosition(0);
//                        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
//                        simpleRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });

        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        count = count + 1;
//                        simpleRecyclerViewAdapter.insert(appList, simpleRecyclerViewAdapter.getAdapterItemCount());
//                        simpleRecyclerViewAdapter.insert(appList, simpleRecyclerViewAdapter.getAdapterItemCount());
//                        simpleRecyclerViewAdapter.insert(appList, simpleRecyclerViewAdapter.getAdapterItemCount());
                        getAppsByCategory();
                        // linearLayoutManager.scrollToPositionWithOffset(maxLastVisiblePosition,-1);
                        //   linearLayoutManager.scrollToPosition(maxLastVisiblePosition);

                    }
                }, 1000);
            }
        });


        ultimateRecyclerView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

            }

            @Override
            public void onDownMotionEvent() {

            }

            @Override
            public void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState) {
//                if (observableScrollState == ObservableScrollState.DOWN) {
//                    ultimateRecyclerView.showToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
//                    ultimateRecyclerView.showFloatingActionMenu();
//                } else if (observableScrollState == ObservableScrollState.UP) {
//                    ultimateRecyclerView.hideToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
//                    ultimateRecyclerView.hideFloatingActionMenu();
//                } else if (observableScrollState == ObservableScrollState.STOP) {
//                }
                URLogs.d("onUpOrCancelMotionEvent");
                if (observableScrollState == ObservableScrollState.UP) {
                    ultimateRecyclerView.showToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
                    ultimateRecyclerView.hideFloatingActionMenu();
                } else if (observableScrollState == ObservableScrollState.DOWN) {
                    ultimateRecyclerView.showToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
                    ultimateRecyclerView.showFloatingActionMenu();
                }
            }
        });

        ultimateRecyclerView.showFloatingButtonView();

    }

    public int getScreenHeight() {
        return getActivity().findViewById(android.R.id.content).getHeight();
    }


  private void showCategorizedApp(){
     getAppsByCategory();
      }



    private void loadApiGetMethod(String category){
        int limit = 5;
        String country = "IN";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        String url = "https://42matters.com/api/1/apps/top_google_charts.json?" + "list_name=topselling_free"+ "&cat_key=" + category + "&country=" + country + "&limit=" + limit + "&page=" + count + "&access_token=" + getActivity().getResources().getString(R.string.aggro_access_token);
        Log.e("URL","" + url);
        GsonRequest<AppDetail> myReq = new GsonRequest<AppDetail>(
                Request.Method.GET,
                url,
                AppDetail.class,
                createMyReqSuccessListener(),
                createMyReqErrorListener());

        mRequestQueue.add(myReq);
    }

    private void loadApiPostMethod(){

    }

    private Response.Listener<AppDetail> createMyReqSuccessListener() {
        return new Response.Listener<AppDetail>() {
            @Override
            public void onResponse(AppDetail response) {
                try {
                    Log.e("APP NAME", response.getCategoryName());
                    appList = new AppList();
                    GroupItem groupItem = new GroupItem();
                    List<AppList> lists= response.getAppList();
                    for (int i = 0; i< lists.size();i++){
                        AppList appList = lists.get(i);
                        String appname = appList.getPackageName();
                        Log.e("APPNAMe","" + appname);
                        simpleRecyclerViewAdapter.insert(appList, simpleRecyclerViewAdapter.getAdapterItemCount());
                    }

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
                String errorMsg = VolleyErrorHelper.getMessage(error, getActivity());
//                Log.e("EROOR MESSG","" + error.getMessage().toString());
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG)
                        .show();
            }
        };
    }

    public void getAppsByCategory(){
        switch (aggroCategory){
            case APPS:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_all_apps));
                break;
            case BUSINESS:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_bussiness));
                break;
            case LIFESTYLE:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_lifestyle));
                break;
            case NEWS_AND_MAGAZINES:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_news));
                break;
            case EDUCATION:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_education));
                break;
            case TRANSPORTATION:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_transporation));
                break;
            case PRODUCTIVITY:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_productiity));
                break;
            case GAMES:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_games));
                break;
            case TRAVEL_AND_LOCAL:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_travel));
                break;
            case SPORTS:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_sports));
                break;
            case HEALTH_AND_FITNESS:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_health));
                break;
            case MUSIC_AND_AUDIO:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_entertainment));
                break;

            default:
                System.out.println("Not comes under the predefined category");
                break;

        }
    }

    private void getTopSellingApps(String string) {
        loadApiGetMethod(string);
    }


}