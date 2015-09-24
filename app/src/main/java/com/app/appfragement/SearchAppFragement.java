package com.app.appfragement;


import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.app.AppConstant;
import com.app.OnClick;
import com.app.Utility.Utility;
import com.app.adapter.SimpleAdapter;
import com.app.aggro.R;
import com.app.api.Category;
import com.app.api.GsonRequest;
import com.app.api.VolleyErrorHelper;
import com.app.getterAndSetter.MyCategory;
import com.app.getterAndSetter.MyToolBar;
import com.app.holder.GroupItem;
import com.app.local.database.AppTracker;
import com.app.modal.AppList;
import com.app.modal.SearchDetail;
import com.app.thin.downloadmanager.DownloadManager;
import com.app.thin.downloadmanager.DownloadRequest;
import com.app.thin.downloadmanager.DownloadStatusListener;
import com.app.thin.downloadmanager.ThinDownloadManager;
import com.balysv.materialmenu.MaterialMenu;
import com.balysv.materialmenu.MaterialMenuDrawable;
import com.library.storage.SimpleStorage;
import com.library.storage.Storage;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchAppFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchAppFragement extends Fragment implements OnClick {
    // TODO: Rename parameter arguments, choose names that match

    final static String DIRECTORY_NAME = "aggro";
    final static String SUBDIRECTORY_NAME = "apk";
    final static String FILE_CONTENT = "some file content";

    private String data = null;
    String aggroCategory = null;

    UltimateRecyclerView ultimateRecyclerView;
    SimpleAdapter simpleRecyclerViewAdapter = null;
    LinearLayoutManager linearLayoutManager;

    private int count = 1;
    private AppList appList;

    private Toolbar toolbar;

    private boolean isSearchOpened = false;
    private EditText edtSeach;

    GroupItem groupItem;

    Storage storage = null;

    int downloadId1;
    private ThinDownloadManager downloadManager;
    private static final int DOWNLOAD_THREAD_POOL_SIZE = 4;
    MyDownloadStatusListener myDownloadStatusListener = new MyDownloadStatusListener();


    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchAppFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchAppFragement newInstance(String imageUrl, String aggroCategory) {
        SearchAppFragement fragment = new SearchAppFragement();
        Bundle args = new Bundle();
        args.putString(ShowCatAppFragement.CATEGORY, imageUrl);
        args.putSerializable(ShowCatAppFragement.CATEGORYLEVEL, aggroCategory);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchAppFragement() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments()!=null){
            data = getArguments().getString(ShowCatAppFragement.CATEGORY);
            aggroCategory = getArguments().getString(ShowCatAppFragement.CATEGORYLEVEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_search_app_fragement, container, false);

        init(v);
        search(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    // handle back button
                    MyToolBar.getToolbar().setVisibility(View.VISIBLE);
                    getActivity().getSupportFragmentManager().popBackStack();
                    return true;
                }

                return false;
            }
        });
    }

    private void init(View view) {
        if (SimpleStorage.isExternalStorageWritable()) {
            storage = SimpleStorage.getExternalStorage();
        }
        else {
            storage = SimpleStorage.getInternalStorage(getActivity());
        }
        boolean dirExists = storage.isDirectoryExists(DIRECTORY_NAME + File.pathSeparator + SUBDIRECTORY_NAME);
        if (!dirExists)
            storage.createDirectory(DIRECTORY_NAME + File.pathSeparator + SUBDIRECTORY_NAME);

        edtSeach = (EditText)view.findViewById(R.id.search);
        toolbar = MyToolBar.getToolbar();
        toolbar.setVisibility(View.GONE);
        appList = new AppList();
        groupItem = new GroupItem();

        ultimateRecyclerView = (UltimateRecyclerView)view. findViewById(R.id.ultimate_recycler_view);
        ultimateRecyclerView.setHasFixedSize(false);
//        final List<String> stringList = new ArrayList<>();
//        appList.setTitle("efefre");
//        groupItem.appLists.add(appList);
        simpleRecyclerViewAdapter = new SimpleAdapter(groupItem.appLists, getActivity(),this);

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
                        if (isSearchOpened){
                            count = count + 1;
                            searchForApp();
                        }

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

    private void searchForApp(){
        int limit = 6;
        String country = "IN";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        String url = "http://jarvisme.com/api/search.php?q=" + edtSeach.getText().toString().trim() + "&limit=" + limit + "&page=" + count + "&access_token=" + getActivity().getResources().getString(R.string.aggro_access_token);

//        String url = "https://42matters.com/api/1/apps/search.json?q=" + edtSeach.getText().toString().trim() + "&limit=" + limit + "&page=" + count + "&access_token=" + getActivity().getResources().getString(R.string.aggro_access_token);
        Log.e("URL", "" + count);
        GsonRequest<SearchDetail> myReq = new GsonRequest<SearchDetail>(
                Request.Method.GET,
                url,
                SearchDetail.class,
                searchSuccessListener(),
                searchErrorListener());

        myReq.setRetryPolicy(new DefaultRetryPolicy(
                AppConstant.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(myReq);
    }

    private Response.Listener<SearchDetail> searchSuccessListener() {
        return new Response.Listener<SearchDetail>() {
            @Override
            public void onResponse(SearchDetail response) {
                try {
                    appList = new AppList();
                    List<AppList> lists= response.getResults();
                    if (lists.size() == 0)
                        ultimateRecyclerView.disableLoadmore();
                    for (int i = 0; i< lists.size();i++){
                        AppList appList = lists.get(i);
                        String appname = appList.getTitle();
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

    private Response.ErrorListener searchErrorListener() {
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

    private void search(View view){
        RelativeLayout relativeLayout = (RelativeLayout)view.findViewById(R.id.rel_arrow_back);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyToolBar.getToolbar().setVisibility(View.VISIBLE);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        //this is a listener to do a search when the user clicks on search button
        edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    isSearchOpened = true;
                    simpleRecyclerViewAdapter.clear();
                    doSearch();
                    return true;
                }
                return false;
            }
        });

        edtSeach.requestFocus();

        //open the keyboard focused in the edtSearch
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);
    }

    private void doSearch() {
        searchForApp();
    }

    public boolean createLocalTraceOfApp(AppList appList){
        AppTracker appTracker = AppTracker.getSingleEntry(appList.getPackageName());
        if (appTracker!=null){
            AppTracker appLocalTracker = appTracker.load(AppTracker.class,appTracker.getId());
            appLocalTracker.appName = appList.getTitle();
            appLocalTracker.catName = appList.getCategory();
            appLocalTracker.packageName = appList.getPackageName();
            appLocalTracker.appIconUrl = appList.getIcon();
            appLocalTracker.isInstalled = appTracker.isInstalled;
            appLocalTracker.rating = appTracker.rating;
            appLocalTracker.save();
            return true;
        }else{
            return false;
        }

    }
    /*--
    Interface listener for Adding app
     */
    @Override
    public void downloadApp(int downloadId,AppList appList) {
        Utility.writePackageNameToPrefs(getActivity(), appList.getPackageName());
        Utility.writePrefs(getActivity(), appList.getTitle(), getResources().getString(R.string.aggro_downloaded_app_name));
        Utility.writePrefs(getActivity(),appList.getCategory(),getResources().getString(R.string.aggro_downloaded_app_category));
        Utility.writePrefs(getActivity(),appList.getMarketUrl(),getResources().getString(R.string.aggro_downloaded_app_market_url));
        Utility.writePrefs(getActivity(), appList.getIcon(), getResources().getString(R.string.aggro_downloaded_app_icon_url));
        Utility.writeBooleaenPrefs(getActivity(), appList.isInstalled(), getResources().getString(R.string.aggro_is_app_downloaded));
        Utility.writeRatingToPrefs(getActivity(), appList.getRating().floatValue(), getResources().getString(R.string.aggro_app_rating));
        launchPlayStore(appList.getPackageName());
    }

    @Override
    public boolean openApp(AppList appList) {
        PackageManager manager = getActivity().getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(appList.getPackageName());
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
    public void createCustomcategory(AppList appList) {
        String url = "http://jarvisme.com/customapp/add.php?";
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        GsonRequest<com.app.response.Response> myReq = new GsonRequest<com.app.response.Response>(
                Request.Method.POST,
                url,
                com.app.response.Response.class,
                prepareHasMap(appList),
                createMyReqSuccessListener(),
                createMyReqErrorListener());

        myReq.setRetryPolicy(new DefaultRetryPolicy(
                AppConstant.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(myReq);
    }

    private HashMap prepareHasMap(AppList appList){
        HashMap<String,String> hm = new HashMap<String,String>();
        hm.put("email", Utility.readUserInfoFromPrefs(getActivity(),getString(R.string.email)));
        hm.put("customCategory", MyCategory.getCategoryName());
        hm.put("appName",appList.getTitle());
        hm.put("iconurlkey",appList.getIcon());
        hm.put("category",appList.getCategory());
        hm.put("appRating","" + appList.getRating());
        hm.put("packagename",appList.getPackageName());
        return hm;
    }

    private com.android.volley.Response.Listener<com.app.response.Response> createMyReqSuccessListener() {
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

    private com.android.volley.Response.ErrorListener createMyReqErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMsg = VolleyErrorHelper.getMessage(error, getActivity());
//                if (error.getLocalizedMessage().toString()!=null || !(error.getLocalizedMessage().toString().equals("null")))
//                Log.e("EROOR MESSG","" + error.getLocalizedMessage().toString());
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG)
                        .show();
            }
        };
    }

    /*--
    Inner class to monitor download status
     */

    class MyDownloadStatusListener implements DownloadStatusListener {

        @Override
        public void onDownloadComplete(int id) {
            System.out.println("###### onDownloadComplete ######## " + id);

            if (id == downloadId1) {
                mBuilder.setContentText("Download complete");
                // Removes the progress bar
                mBuilder.setProgress(0, 0, false);
                mNotifyManager.notify(id, mBuilder.build());
            }


        }

        @Override
        public void onDownloadFailed(int id, int errorCode, String errorMessage) {
            System.out.println("###### onDownloadFailed ######## "+id+" : "+errorCode+" : "+errorMessage);
            if (id == downloadId1) {
                mBuilder.setContentText(getResources().getString(R.string.download_failed));
                mBuilder.setProgress(0, 0, false);
                mNotifyManager.notify(id, mBuilder.build());
            }
        }

        @Override
        public void onProgress(int id, long totalBytes, long downloadedBytes, int progress) {
            mBuilder.setProgress(100, progress, false);
            mNotifyManager.notify(id, mBuilder.build());
        }

    }

    private String getBytesDownloaded(int progress, long totalBytes) {
        //Greater than 1 MB
        long bytesCompleted = (progress * totalBytes)/100;
        if (totalBytes >= 1000000) {
            return (""+(String.format("%.1f", (float)bytesCompleted/1000000))+ "/"+ ( String.format("%.1f", (float)totalBytes/1000000)) + "MB");
        } if (totalBytes >= 1000) {
            return (""+(String.format("%.1f", (float)bytesCompleted/1000))+ "/"+ ( String.format("%.1f", (float)totalBytes/1000)) + "Kb");

        } else {
            return ( ""+bytesCompleted+"/"+totalBytes );
        }
    }

    private void showDownloadInNotification(String appName){
        mNotifyManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(getActivity());
        mBuilder.setContentTitle("Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.mipmap.logo);

        File fileSource = storage.getFile(DIRECTORY_NAME + File.separator + SUBDIRECTORY_NAME, appName);
        Intent toLaunch = new Intent();
        toLaunch.setAction(android.content.Intent.ACTION_VIEW);
        toLaunch.setDataAndType(Uri.fromFile(fileSource), getMimeType(Uri.fromFile(fileSource).toString()));  // you can also change jpeg to other types
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, toLaunch , 0);
        mBuilder.setContentIntent(contentIntent);
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);

        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }

        return type;
    }

    private void launchPlayStore(final String appPackageName) { // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

}
