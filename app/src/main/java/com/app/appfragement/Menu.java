package com.app.appfragement;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.aggro.R;
import com.app.slideradapter.MyFragmentAdapter;
import com.app.slideradapter.TabsPagerAdapter;
import com.app.viewslider.SlidingTabLayout;

import java.util.List;
import java.util.Vector;

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


    private ViewPager mViewPager;
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
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
        setUpFragementPager(view);
        setUpTabColor();
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

        fragments.add(Fragment.instantiate(getActivity(), AppFragement.class.getName(), page));
        fragments.add(Fragment.instantiate(getActivity(), CatFragement.class.getName(), page));
        fragments.add(Fragment.instantiate(getActivity(),FavFragement.class.getName(),page));

//after adding all the fragments write the below lines
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mPagerAdapter  = new MyFragmentAdapter(getActivity(),super.getFragmentManager(), fragments);

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0);
        MyFragmentAdapter.setPos(mParam2);
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                if (mParam2 == MyFragmentAdapter.CENTERED_PAGE)
                    mViewPager.setCurrentItem(mParam2);
                else if (mParam2 == MyFragmentAdapter.RIGHT_PAGE)
                    mViewPager.setCurrentItem(mParam2);
                else if (mParam2 == MyFragmentAdapter.LEFT_PAGE)
                    mViewPager.setCurrentItem(mParam2);
            }
        });

        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);

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


}
