package com.app.appfragement;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.adapter.SimpleAnimationAdapter;
import com.app.aggro.R;
import com.app.appfragement.fragment.RecyclerViewFragment;
import com.app.appfragement.fragment.RecyclerViewGridFragment;
import com.app.appfragement.fragment.RecyclerViewThreeWayGridFragment;
import com.app.getterAndSetter.MyToolBar;
import com.app.gridcategory.ImageItem;
import com.app.holder.GroupItem;
import com.app.slideradapter.MyFragmentAdapter;
import com.app.slideradapter.TabsPagerAdapter;
import com.app.viewslider.SlidingTabLayout;
import com.crashlytics.android.Crashlytics;
import com.github.florent37.materialviewpager.BuildConfig;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;



import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import io.fabric.sdk.android.Fabric;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Menu.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Menu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private int mParam2;

    private OnFragmentInteractionListener mListener;

    private SlidingTabLayout mSlidingTabLayout;


    private MaterialViewPager mViewPager;

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;

    UltimateRecyclerView ultimateRecyclerView;
    SimpleAnimationAdapter simpleRecyclerViewAdapter = null;
    LinearLayoutManager linearLayoutManager ;

    private Toolbar toolbar;
    private GroupItem groupItem;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Menu.
     */
    // TODO: Rename and change types and number of parameters
    public static Menu newInstance(String param1, int param2) {
        Menu fragment = new Menu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Menu() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam2 = getArguments().getInt(ARG_PARAM2);
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        init(view);

        MyToolBar.getToolbar().setTitle("");
        MyToolBar.getToolbar().setTitle(getActivity().getResources().getString(R.string.app_menu));
        setUpFragementPager(view);
//        setUpTabColor();
    }

    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onPrepareOptionsMenu(android.view.Menu menu) {
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
                selectsearchAppFragement("","");
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mViewPager = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    void setUpFragementPager(View view) {
        List<Fragment> fragments = new Vector<Fragment>();
        MyFragmentAdapter mPagerAdapter;
//for each fragment you want to add to the pager
        Bundle page = new Bundle();
        page.putString("url", "d");

        fragments.add(Fragment.instantiate(getActivity(), RecyclerViewFragment.class.getName(), page));
        fragments.add(Fragment.instantiate(getActivity(), RecyclerViewGridFragment.class.getName(), page));
        fragments.add(Fragment.instantiate(getActivity(), RecyclerViewThreeWayGridFragment.class.getName(), page));

//after adding all the fragments write the below lines
        mViewPager = (MaterialViewPager) view.findViewById(R.id.materialViewPager);
        mPagerAdapter  = new MyFragmentAdapter(getActivity(),super.getChildFragmentManager(), fragments);
        mViewPager.getToolbar().setVisibility(View.GONE);
        mViewPager.getViewPager().setAdapter(mPagerAdapter);
        if (mParam2 == MyFragmentAdapter.CENTERED_PAGE){
            mViewPager.getViewPager().setCurrentItem(mParam2);
        }

        else if (mParam2 == MyFragmentAdapter.RIGHT_PAGE){
            mViewPager.getViewPager().setCurrentItem(mParam2);
        }

        else if (mParam2 == MyFragmentAdapter.LEFT_PAGE){
            mViewPager.getViewPager().setCurrentItem(mParam2);
        }

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

    }


    void setUpTabColor(){
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                // TODO Auto-generated method stub
                return Menu.this.getResources().getColor(android.R.color.white);
            }
            @Override
            public int getDividerColor(int position) {
                // TODO Auto-generated method stub
                return Menu.this.getResources().getColor(R.color.slider_bar);
            }

        });
    }


    /**
     * Prepare some dummy data for gridview
     */
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> mItems = new ArrayList<>();
//        mItems.add(new ImageItem(getActivity().getResources().getString(R.string.cat_bussiness),R.mipmap.games));
        return mItems;
    }

    private void init(){
        if (!BuildConfig.DEBUG)
            Fabric.with(getActivity(), new Crashlytics());


    }

}
