package com.app.appfragement;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.adapter.SimpleAdapter;
import com.app.adapter.SimpleAnimationAdapter;
import com.app.aggro.R;
import com.app.api.Category;
import com.app.getterAndSetter.MyToolBar;
import com.app.gridcategory.GridViewAdapter;
import com.app.gridcategory.ImageItem;
import com.app.gridcategory.TwoWayGrid;
import com.app.holder.GroupItem;
import com.app.modal.AppList;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;

/**
 * Created by sonal on 7/2/2015.
 */
public class CatFragement extends Fragment{

    private GridViewAdapter gridAdapter;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;

    UltimateRecyclerView ultimateRecyclerView;
    SimpleAnimationAdapter simpleRecyclerViewAdapter = null;
    GridLayoutManager gridLayoutManager;

    private Toolbar toolbar;
    private GroupItem groupItem;

    private int count = 1;

    public static CatFragement newInstance(String imageUrl) {

        final CatFragement mf = new CatFragement();

        final Bundle args = new Bundle();
        args.putString("somedata", "somedata");
        mf.setArguments(args);

        return mf;
    }

    public CatFragement() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        String data = getArguments().getString("somedata");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate and locate the main ImageView
        final View v = inflater.inflate(R.layout.fragement_cat, container, false);
        init1(v);
        return v;
    }



    private void init1(View view){
        GridView gridView = (GridView)view.findViewById(R.id.gridview);
//        gridView.setAdapter(new TwoWayGrid(getActivity()));

        gridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item, getData());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                //Start details Fragement
                selectItem(item.getTitle());
            }
        });
    }


   private void init(View view){
       toolbar = MyToolBar.getToolbar();
       toolbar.setTitle(getString(R.string.app_cat));
       groupItem = new GroupItem();
       groupItem.gridList = getData();

       ultimateRecyclerView = (UltimateRecyclerView)view. findViewById(R.id.ultimate_recycler_view);
       ultimateRecyclerView.setHasFixedSize(false);

       simpleRecyclerViewAdapter = new SimpleAnimationAdapter(groupItem.gridList, getActivity());
       gridLayoutManager=new GridLayoutManager(getActivity(),2);
       ultimateRecyclerView.setLayoutManager(gridLayoutManager);
       ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);

       ultimateRecyclerView.disableLoadmore();
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
//                       simpleRecyclerViewAdapter.insert(new ImageItem(getString(R.string.cat_all_apps),R.mipmap.games), simpleRecyclerViewAdapter.getAdapterItemCount());
                       ultimateRecyclerView.setRefreshing(false);
                       //   ultimateRecyclerView.scrollBy(0, -50);
                       gridLayoutManager.scrollToPosition(0);
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
//                       count = count + 1;
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

        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_comics), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_communication), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_finance), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_media_video), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_medical), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_personilazation), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_photography), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_shopping), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_social), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_tool), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_wheather), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_lib_demo), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_arcade), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_puzzle), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_card), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_casual), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_racing), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_sport), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_action), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_adventure), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_board), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_casino), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_educational), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_family), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_music), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_role_playing), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_simulation), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_strategy), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_trivia), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_game_word), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_app_wallpaper), R.mipmap.entertainment));
        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_app_widget), R.mipmap.entertainment));

        return mItems;
    }

    private void selectshowCatApp(String local, Category.AggroCategory level) {
        // update the main content by replacing fragments
        Fragment fragment = ShowCatAppFragement.newInstance(local, level);
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction  = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        getActivity().invalidateOptionsMenu();
    }

    private void selectItem(String category) {
        // update the main content by replacing fragments
        String localvariable = null;
        Category.AggroCategory catLevel = null;
        if (category.trim().equals(getActivity().getResources().getString(R.string.cat_bussiness))) {
            localvariable = getActivity().getResources().getString(R.string.cat_bussiness);
            catLevel = Category.AggroCategory.BUSINESS;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_lifestyle))) {
            localvariable = getActivity().getResources().getString(R.string.cat_lifestyle);
            catLevel = Category.AggroCategory.LIFESTYLE;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_news))) {
            localvariable = getActivity().getResources().getString(R.string.cat_news);
            catLevel = Category.AggroCategory.NEWS_AND_MAGAZINES;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_education))) {
            localvariable = getActivity().getResources().getString(R.string.cat_education);
            catLevel = Category.AggroCategory.EDUCATION;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_transporation))) {
            localvariable = getActivity().getResources().getString(R.string.cat_transporation);
            catLevel = Category.AggroCategory.TRANSPORTATION;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_productiity))) {
            localvariable = getActivity().getResources().getString(R.string.cat_productiity);
            catLevel = Category.AggroCategory.PRODUCTIVITY;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_games))) {
            localvariable = getActivity().getResources().getString(R.string.cat_games);
            catLevel = Category.AggroCategory.GAMES;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_travel))) {
            localvariable = getActivity().getResources().getString(R.string.cat_travel);
            catLevel = Category.AggroCategory.TRAVEL_AND_LOCAL;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_sports))) {
            localvariable = getActivity().getResources().getString(R.string.cat_sports);
            catLevel = Category.AggroCategory.SPORTS;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_health))) {
            localvariable = getActivity().getResources().getString(R.string.cat_health);
            catLevel = Category.AggroCategory.HEALTH_AND_FITNESS;
        } else if (category.trim().equals(getActivity().getResources().getString(R.string.cat_entertainment))) {
            localvariable = getActivity().getResources().getString(R.string.cat_entertainment);
            catLevel = Category.AggroCategory.MUSIC_AND_AUDIO;
        } else {
            //custom category
        }

        selectshowCatApp(localvariable,catLevel);
    }

}
