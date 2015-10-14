package com.app.appfragement;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.app.AppConstant;
import com.app.OnClick;
import com.app.OnCustomCategoryItemClick;
import com.app.Utility.Utility;
import com.app.adapter.SimpleAdapter;
import com.app.adapter.SimpleCustomAdapter;
import com.app.aggro.R;
import com.app.api.Category;
import com.app.api.GsonRequest;
import com.app.api.VolleyErrorHelper;
import com.app.getterAndSetter.MyToolBar;
import com.app.holder.GroupItem;
import com.app.local.database.AppTracker;
import com.app.local.database.UserInfo;
import com.app.modal.AppDetail;
import com.app.modal.AppList;
import com.app.response.CustomMsg;
import com.app.response.CustomResponse;
import com.app.response.RegisterResponse;
import com.app.thin.downloadmanager.ThinDownloadManager;
import com.library.storage.SimpleStorage;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomAppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomAppFragment extends Fragment implements OnCustomCategoryItemClick {
    // TODO: Rename parameter arguments, choose names that match

    // TODO: Rename and change types of parameters
    private GroupItem groupItem;
    public static String CATEGORY = "CATEGORY";
    public static String CATEGORYLEVEL = "CATEGORYLEVEL";
    private String data = null;

    UltimateRecyclerView ultimateRecyclerView;
    SimpleCustomAdapter simpleRecyclerViewAdapter = null;
    LinearLayoutManager linearLayoutManager;

    private int count = 1;
    private CustomMsg appList;

    private Toolbar toolbar;

    CoordinatorLayout rootLayout;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param imageUrl Parameter 1.
     * @return A new instance of fragment CustomAppFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomAppFragment newInstance(String imageUrl) {
        CustomAppFragment fragment = new CustomAppFragment();
        final Bundle args = new Bundle();
        args.putString(CATEGORY, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    public CustomAppFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments()!=null){
            data = getArguments().getString(CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_custom_app, container, false);
        initInstances(v);
        init(v);
        showCategorizedApp();

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("######## onDestroy ######## ");
        count = 0;
    }

    public void selectsearchAppFragement(String local, String level) {
        // update the main content by replacing fragments
        android.support.v4.app.Fragment fragment = SearchAppFragement.newInstance("", "");
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

    private void initInstances(View view) {

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(data);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update the main content by replacing fragments
                startActivity(new Intent(getActivity(), com.app.aggro.MainActivity.class));
                getActivity().finish();
            }
        });
        rootLayout = (CoordinatorLayout) view.findViewById(R.id.htab_maincontent);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init(View view) {
        appList = new CustomMsg();
        groupItem = new GroupItem();
//        listView = (ListView)view.findViewById(R.id.app_lib_list);
        ultimateRecyclerView = (UltimateRecyclerView)view. findViewById(R.id.ultimate_recycler_view);
        ultimateRecyclerView.setHasFixedSize(false);
//        final List<String> stringList = new ArrayList<>();
//        appList.setTitle("efefre");
//        groupItem.appLists.add(appList);
        simpleRecyclerViewAdapter = new SimpleCustomAdapter(groupItem.customMsgList, getActivity(),this);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);

        ultimateRecyclerView.enableLoadmore();
        simpleRecyclerViewAdapter.setCustomLoadMoreView(LayoutInflater.from(getActivity())
                .inflate(R.layout.custom_bottom_progressbar, null));

//        ultimateRecyclerView.setParallaxHeader(getActivity().getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, ultimateRecyclerView.mRecyclerView, false));
//        ultimateRecyclerView.setOnParallaxScroll(new UltimateRecyclerView.OnParallaxScroll() {
//            @Override
//            public void onParallaxScroll(float percentage, float offset, View parallax) {
////                Drawable c = toolbar.getBackground();
////                c.setAlpha(Math.round(127 + percentage * 128));
//                toolbar.setBackgroundDrawable(toolbar.getBackground());
//            }
//        });

        ultimateRecyclerView.setRecylerViewBackgroundColor(Color.parseColor("#ffffff"));
//        ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        simpleRecyclerViewAdapter.insert(appList, simpleRecyclerViewAdapter.getAdapterItemCount());
//                        ultimateRecyclerView.setRefreshing(false);
//                        //   ultimateRecyclerView.scrollBy(0, -50);
//                        linearLayoutManager.scrollToPosition(0);
////                        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
////                        simpleRecyclerViewAdapter.notifyDataSetChanged();
//                    }
//                }, 1000);
//            }
//        });

        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        count = count + 1;
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
//                    ultimateRecyclerView.showToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
                    ultimateRecyclerView.hideFloatingActionMenu();
                } else if (observableScrollState == ObservableScrollState.DOWN) {
//                    ultimateRecyclerView.showToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
                    ultimateRecyclerView.hideFloatingActionMenu();
                }
            }
        });

        ultimateRecyclerView.hideDefaultFloatingActionButton();

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
        hm.put("category_name", data);
        hm.put("email", UserInfo.getRandom().email);
        hm.put("limit", "" + 5);
        hm.put("page", "" + count);
        return hm;
    }

    private Response.Listener<CustomResponse> createMyReqSuccessListener() {
        return new Response.Listener<CustomResponse>() {
            @Override
            public void onResponse(CustomResponse response) {
                try {
//                    appList = new CustomMsg();
                    GroupItem groupItem = new GroupItem();
                    List<CustomMsg> lists= response.getMsg();
                    for (int i = 0; i< lists.size();i++){
                        CustomMsg appList = lists.get(i);
                        String appname = appList.getPackagename();
                        Log.e("APPNAMe", "" + appname);
                        if (createLocalTraceOfApp(appList))
                            appList.setIsInstalled(true);
                        else
                            appList.setIsInstalled(false);
                        simpleRecyclerViewAdapter.insert(appList, simpleRecyclerViewAdapter.getAdapterItemCount());
                        simpleRecyclerViewAdapter.notifyDataSetChanged();
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
                System.out.println(error.getMessage());
//                String errorMsg = VolleyErrorHelper.getMessage(error, getActivity());
//                if (error.getLocalizedMessage().toString()!=null || !(error.getLocalizedMessage().toString().equals("null")))
//                Log.e("EROOR MESSG","" + error.getLocalizedMessage().toString());
//                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG)
//                        .show();
            }
        };
    }

    public boolean createLocalTraceOfApp(CustomMsg appList){
        AppTracker appTracker = AppTracker.getSingleEntry(appList.getPackagename());
        if (appTracker!=null){
            AppTracker appLocalTracker = appTracker.load(AppTracker.class,appTracker.getId());
            appLocalTracker.appName = appList.getAppName();
            appLocalTracker.catName = appList.getCategory();
            appLocalTracker.packageName = appList.getPackagename();
            appLocalTracker.appIconUrl = appList.getIconLink();
            appLocalTracker.isInstalled = appTracker.isInstalled;
            appLocalTracker.rating = appTracker.rating;
            appLocalTracker.save();
            return true;
        }else{
            return false;
        }

    }

    public void getAppsByCategory(){
        getTopSellingApps(data);
    }

    private void getTopSellingApps(String category) {
        loadApiGetMethod(category);
    }


    /*--
    Interface listener for Adding app
     */
    @Override
    public void downloadApp(int downloadId,CustomMsg appList) {
        Utility.writePackageNameToPrefs(getActivity(), appList.getPackagename());
        Utility.writePrefs(getActivity(), appList.getAppName(), getResources().getString(R.string.aggro_downloaded_app_name));
        Utility.writePrefs(getActivity(),appList.getCategory(),getResources().getString(R.string.aggro_downloaded_app_category));
        Utility.writePrefs(getActivity(),appList.getPackagename(),getResources().getString(R.string.aggro_downloaded_app_market_url));
        Utility.writePrefs(getActivity(), appList.getIconLink(), getResources().getString(R.string.aggro_downloaded_app_icon_url));
        Utility.writeBooleaenPrefs(getActivity(), appList.isInstalled(), getResources().getString(R.string.aggro_is_app_downloaded));
        Utility.writeRatingToPrefs(getActivity(),appList.getAppRating().floatValue(),getResources().getString(R.string.aggro_app_rating));
//        Category category = new Category(getActivity());
//        category.setMyEnum(aggroCategory);
        launchPlayStore(appList.getPackagename());
    }

    @Override
    public boolean openApp(CustomMsg appList) {
        PackageManager manager = getActivity().getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(appList.getPackagename());
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

    @Override
    public void createCustomcategory(CustomMsg appList) {

    }

    private void launchPlayStore(final String appPackageName) { // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
