package com.app.appfragement.fragment;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.aggro.MyApplication;
import com.app.aggro.R;
import com.app.appfragement.AppFragement;
import com.app.appfragement.FavFragement;
import com.app.slideradapter.MyFragmentAdapter;

import java.util.List;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuMain extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //int representing our 0th tab corresponding to the Fragment where App results are dispalyed
    public static final int TAB_APP_RESULTS = 0;
    //int corresponding to our 1st tab corresponding to the Fragment where CATEGORY are dispalyed
    public static final int TAB_CATEGORY = 1;
    //int corresponding to our 2nd tab corresponding to the Fragment where FAVOURITES are displayed
    public static final int TAB_FAVOURITES = 2;
    //int corresponding to the number of tabs in our Fragement
    public static final int TAB_COUNT = 3;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private int mParam2;

    ActionBarDrawerToggle drawerToggle;
    FloatingActionButton fabBtn;
    CoordinatorLayout rootLayout;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
//    private MaterialTabHost mTabHost;
    private ViewPager mPager;
    private TabLayout mTabHost;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuMain.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuMain newInstance(String param1, int param2) {
        MenuMain fragment = new MenuMain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MenuMain() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.menu_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupTabs(view);
        initInstances(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("MenuMain Fragemnent");
    }

    private void initInstances(View view) {

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Aggro");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        rootLayout = (CoordinatorLayout)view.findViewById(R.id.htab_maincontent);
        fabBtn = (FloatingActionButton) view.findViewById(R.id.fabBtn);

        collapsingToolbarLayout = (CollapsingToolbarLayout)view.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle("");
        if(((AppCompatActivity)getActivity()).getSupportActionBar()!=null)
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_menu);
        }
    }


    private void setupTabs(View view) {
        ImageView header = (ImageView)view.findViewById(R.id.header);
        mTabHost = (TabLayout)view. findViewById(R.id.materialTabHost);
        List<Fragment> fragments = new Vector<Fragment>();
        MyFragmentAdapter mPagerAdapter;
//for each fragment you want to add to the pager
        Bundle page = new Bundle();
        page.putString("url", "d");

        fragments.add(Fragment.instantiate(getActivity(), AppFragement.class.getName(), page));
        fragments.add(Fragment.instantiate(getActivity(), RecyclerViewGridFragment.class.getName(), page));
        fragments.add(Fragment.instantiate(getActivity(), FavFragement.class.getName(), page));

//after adding all the fragments write the below lines
        mPager = (ViewPager)view. findViewById(R.id.htab_viewpager);

        mPagerAdapter  = new MyFragmentAdapter(getActivity(),super.getChildFragmentManager(), fragments,mPager,header);
        mPager.setAdapter(mPagerAdapter);

        mPagerAdapter.notifyDataSetChanged();
        mTabHost.setupWithViewPager(mPager);
        if (mParam2 == MyFragmentAdapter.CENTERED_PAGE){
            mPager.setCurrentItem(1);
        }

        else if (mParam2 == MyFragmentAdapter.RIGHT_PAGE){
            mPager.setCurrentItem(2);
        }

        else if (mParam2 == MyFragmentAdapter.LEFT_PAGE){
            mPager.setCurrentItem(0);
        }

    }

}
