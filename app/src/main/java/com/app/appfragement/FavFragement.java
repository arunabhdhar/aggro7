package com.app.appfragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.app.aggro.R;
import com.app.gridcategory.ThreeWayGrid;
import com.app.gridcategory.TwoWayGrid;

/**
 * Created by sonal on 7/2/2015.
 */
public class FavFragement extends Fragment {

    public static FavFragement newInstance(String imageUrl) {

        final FavFragement mf = new FavFragement();

        final Bundle args = new Bundle();
        args.putString("somedata", "somedata");
        mf.setArguments(args);

        return mf;
    }

    public FavFragement() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String data = getArguments().getString("somedata");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate and locate the main ImageView
        final View v = inflater.inflate(R.layout.fragement_fav, container, false);
        init(v);
        return v;
    }

    private void init(View view){
        GridView gridView = (GridView)view.findViewById(R.id.gridview);
        gridView.setAdapter(new ThreeWayGrid(getActivity()));
    }
}
