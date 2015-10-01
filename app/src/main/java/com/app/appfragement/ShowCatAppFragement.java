package com.app.appfragement;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.*;
import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.app.AppConstant;
import com.app.BroadcastListener;
import com.app.OnClick;
import com.app.Utility.Utility;
import com.app.adapter.AppLibrary;
import com.app.adapter.SimpleAdapter;
import com.app.aggro.*;
import com.app.api.Category;
import com.app.api.GsonRequest;
import com.app.api.VolleyErrorHelper;
import com.app.getterAndSetter.MyToolBar;
import com.app.holder.GroupItem;
import com.app.local.database.AppTracker;
import com.app.modal.AppDetail;
import com.app.modal.AppList;
import com.app.modal.Result;
import com.app.modal.SearchDetail;
import com.app.test.RevealActivity;
import com.app.thin.downloadmanager.DownloadManager;
import com.app.thin.downloadmanager.DownloadRequest;
import com.app.thin.downloadmanager.DownloadStatusListener;
import com.app.thin.downloadmanager.ThinDownloadManager;
import com.library.storage.SimpleStorage;
import com.library.storage.Storage;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.RecyclerItemClickListener;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonal on 7/2/2015.
 */
public class ShowCatAppFragement extends Fragment implements OnClick{

    final static String DIRECTORY_NAME = "aggro";
    final static String SUBDIRECTORY_NAME = "apk";
    final static String FILE_CONTENT = "some file content";

    private GroupItem groupItem;
    public static String CATEGORY = "CATEGORY";
    public static String CATEGORYLEVEL = "CATEGORYLEVEL";
    private String data = null;
    Category.AggroCategory aggroCategory;

    UltimateRecyclerView ultimateRecyclerView;
    SimpleAdapter simpleRecyclerViewAdapter = null;
    LinearLayoutManager linearLayoutManager;

    private int count = 1;
    private AppList appList;

    private Toolbar toolbar;

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;

    Storage storage = null;

    int downloadId1;
    private ThinDownloadManager downloadManager;
    private static final int DOWNLOAD_THREAD_POOL_SIZE = 4;
    MyDownloadStatusListener myDownloadStatusListener = new MyDownloadStatusListener();


    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;


    public static ShowCatAppFragement newInstance(String imageUrl, Category.AggroCategory aggroCategory) {

        final ShowCatAppFragement mf = new ShowCatAppFragement();

        final Bundle args = new Bundle();
        args.putString(CATEGORY, imageUrl);
        args.putSerializable(CATEGORYLEVEL, aggroCategory);
        mf.setArguments(args);

        return mf;
    }

    public ShowCatAppFragement() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("######## onDestroy ######## ");
        downloadManager.release();
        count = 0;
    }



    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        mSearchAction = menu.findItem(R.id.action_search);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
//                handleMenuSearch();
                selectsearchAppFragement(data,"");
                return true;
        }

        return false;
    }



    public void selectsearchAppFragement(String local, String level) {
        // update the main content by replacing fragments
        Fragment fragment = SearchAppFragement.newInstance("", "");
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

    protected void handleMenuSearch(){
        ActionBar action = ((AppCompatActivity)getActivity()).getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(((Activity)getActivity()).getCurrentFocus().getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_open_search));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
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

            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.ic_action));

            isSearchOpened = true;
        }
    }

    private void doSearch() {
//        searchForApp();
    }

    private void init(View view) {
        if (SimpleStorage.isExternalStorageWritable()) {
            storage = SimpleStorage.getExternalStorage();
        }
        else {
            storage = SimpleStorage.getInternalStorage(getActivity());
        }
        boolean dirExists = storage.isDirectoryExists(DIRECTORY_NAME + "/" + SUBDIRECTORY_NAME);
        if (!dirExists)
        storage.createDirectory(DIRECTORY_NAME + "/" + SUBDIRECTORY_NAME);

        downloadManager = new ThinDownloadManager(DOWNLOAD_THREAD_POOL_SIZE);
        toolbar = MyToolBar.getToolbar();
        toolbar.setTitle(data);
        appList = new AppList();
        groupItem = new GroupItem();
//        listView = (ListView)view.findViewById(R.id.app_lib_list);
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
        TelephonyManager tm = (TelephonyManager)getActivity().getSystemService(getActivity().TELEPHONY_SERVICE);
        String countryCodeValue = tm.getNetworkCountryIso();
        int limit = 5;
        String country = countryCodeValue;
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        String url = "http://jarvisme.com/api/json1.php?" + "list_name=topselling_free"+ "&cat_key=" + category + "&country=" + country + "&limit=" + limit + "&page=" + count + "&access_token=" + getActivity().getResources().getString(R.string.aggro_access_token);
//        String url = "https://42matters.com/api/1/apps/top_google_charts.json?" + "list_name=topselling_free"+ "&cat_key=" + category + "&country=" + country + "&limit=" + limit + "&page=" + count + "&access_token=" + getActivity().getResources().getString(R.string.aggro_access_token);
        Log.e("URL","" + url);
        GsonRequest<AppDetail> myReq = new GsonRequest<AppDetail>(
                Request.Method.GET,
                url,
                AppDetail.class,
                createMyReqSuccessListener(),
                createMyReqErrorListener());

        myReq.setRetryPolicy(new DefaultRetryPolicy(
                AppConstant.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(myReq);
    }

    private void loadApiPostMethod(){

    }

    private Response.Listener<AppDetail> createMyReqSuccessListener() {
        return new Response.Listener<AppDetail>() {
            @Override
            public void onResponse(AppDetail response) {
                try {
                    appList = new AppList();
                    GroupItem groupItem = new GroupItem();
                    List<AppList> lists= response.getAppList();
                    for (int i = 0; i< lists.size();i++){
                        AppList appList = lists.get(i);
                        String appname = appList.getPackageName();
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
            case COMICS:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_comics));
                break;
            case COMMUNICATION:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_communication));
                break;
            case FINANCE:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_finance));
                break;
            case MEDIA_AND_VIDEO:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_media_video));
                break;
            case MEDICAL:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_medical));
                break;
            case PERSONALIZATION:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_personilazation));
                break;
            case PHOTOGRAPHY:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_photography));
                break;
            case SHOPPING:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_shopping));
                break;
            case SOCIAL:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_social));
                break;
            case TOOLS:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_tool));
                break;
            case WEATHER:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_wheather));
                break;
            case LIBRARIES_AND_DEMO:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_lib_demo));
                break;
            case GAME_ARCADE:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_arcade));
                break;
            case GAME_PUZZLE:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_puzzle));
                break;
            case GAME_CARD:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_card));
                break;
            case GAME_CASUAL:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_casual));
                break;
            case GAME_RACING:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_racing));
                break;
            case GAME_SPORTS:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_sport));
                break;
            case GAME_ACTION:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_action));
                break;
            case GAME_ADVENTURE:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_adventure));
                break;
            case GAME_BOARD:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_board));
                break;
            case GAME_CASINO:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_casino));
                break;
            case GAME_EDUCATIONAL:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_educational));
                break;
            case GAME_FAMILY:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_family));
                break;
            case GAME_MUSIC:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_music));
                break;
            case GAME_ROLE_PLAYING:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_role_playing));
                break;
            case GAME_SIMULATION:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_simulation));
                break;
            case GAME_STRATEGY:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_strategy));
                break;
            case GAME_TRIVIA:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_trivia));
                break;
            case GAME_WORD:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_game_word));
                break;
            case APP_WALLPAPER:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_app_wallpaper));
                break;
            case APP_WIDGETS:
                getTopSellingApps(getActivity().getResources().getString(R.string.cat_app_widget));
                break;

            default:
                System.out.println("Not comes under the predefined category");
                break;

        }
    }

    private void getTopSellingApps(String category) {
       loadApiGetMethod(category);
    }


    /*--
    Interface listener for Adding app
     */
    @Override
    public void downloadApp(int downloadId,AppList appList) {
        Utility.writePackageNameToPrefs(getActivity(), appList.getPackageName());
        String appPackageName = appList.getPackageName();
        Utility.writePrefs(getActivity(), appList.getTitle(), getResources().getString(R.string.aggro_downloaded_app_name));
        Utility.writePrefs(getActivity(),appList.getCategory(),getResources().getString(R.string.aggro_downloaded_app_category));
        Utility.writePrefs(getActivity(),appList.getMarketUrl(),getResources().getString(R.string.aggro_downloaded_app_market_url));
        Utility.writePrefs(getActivity(), appList.getIcon(), getResources().getString(R.string.aggro_downloaded_app_icon_url));
        Utility.writeBooleaenPrefs(getActivity(), appList.isInstalled(), getResources().getString(R.string.aggro_is_app_downloaded));
        Utility.writeRatingToPrefs(getActivity(),appList.getRating().floatValue(),getResources().getString(R.string.aggro_app_rating));
        Category category = new Category(getActivity());
        category.setMyEnum(aggroCategory);
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

    }


    private void selectshowCatApp(String local, Category.AggroCategory level) {
        // update the main content by replacing fragments
        Fragment fragment = ShowCatAppFragement.newInstance(local, level);
        android.support.v4.app.FragmentManager fragmentManager = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction  = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        ((Activity)getActivity()).invalidateOptionsMenu();
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