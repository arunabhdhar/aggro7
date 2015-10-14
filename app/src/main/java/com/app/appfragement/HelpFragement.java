package com.app.appfragement;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.app.aggro.MainActivity;
import com.app.aggro.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HelpFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HelpFragement extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CoordinatorLayout rootLayout;
    Toolbar toolbar;
    WebView mWebview;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HelpFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static HelpFragement newInstance(String param1, String param2) {
        HelpFragement fragment = new HelpFragement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HelpFragement() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help_fragement, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initInstances(view);
    }

    private void initInstances(View view) {

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Aggro");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                MainActivity.fragmentStack.lastElement().onPause();
                ft.remove(MainActivity.fragmentStack.pop());
                MainActivity.fragmentStack.lastElement().onResume();
                ft.show(MainActivity.fragmentStack.lastElement());
                ft.commit();
            }
        });
        rootLayout = (CoordinatorLayout) view.findViewById(R.id.htab_maincontent);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView textView1 = (TextView)view.findViewById(R.id.text1);
        TextView textView2 = (TextView)view.findViewById(R.id.text2);
        TextView textView3 = (TextView)view.findViewById(R.id.text3);
        TextView textView4 = (TextView)view.findViewById(R.id.text4);
        TextView textView5 = (TextView)view.findViewById(R.id.text5);

        textView1.setOnClickListener(l);
        textView2.setOnClickListener(l);
        textView3.setOnClickListener(l);
        textView4.setOnClickListener(l);
        textView5.setOnClickListener(l);


    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.text1:
                    loadDetailFragemnt("http://kumawatkhetesh.esy.es/aggro/help/remov_downloaded_app.html");
                    break;
                case R.id.text2:
                    loadDetailFragemnt("http://kumawatkhetesh.esy.es/aggro/help/new%20to%20aggro%20learn%20the%20basics.html");
                    break;
                case R.id.text3:
                    loadDetailFragemnt("http://kumawatkhetesh.esy.es/aggro/help/Read%20book%20on%20aggro.html");
                    break;
                case R.id.text4:
                    loadDetailFragemnt("http://kumawatkhetesh.esy.es/aggro/help/Disable%20enable%20aggro%20system%20apps.html");
                    break;
                case R.id.text5:
                    loadDetailFragemnt("http://kumawatkhetesh.esy.es/aggro/help/subscription%20on%20google%20play.html");
                    break;
            }
        }
    };


    private void loadDetailFragemnt(String url){
        Fragment fragment = HelpDetailFragment.newInstance("", url);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.main_content, fragment);
        MainActivity.fragmentStack.lastElement().onPause();
        ft.hide(MainActivity.fragmentStack.lastElement());
        MainActivity.fragmentStack.push(fragment);
        ft.commit();
    }

}
