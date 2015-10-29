package com.app.appfragement;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.*;
import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.adapter.AppLibrary;
import com.app.adapter.FavAdapter;
import com.app.aggro.R;
import com.app.cards.CustomCards;
import com.app.cards.FavCards;
import com.app.holder.ChildItem;
import com.app.holder.GroupItem;
import com.app.local.database.AppTracker;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * Created by sonal on 7/2/2015.
 */
public class FavFragement extends Fragment {


    List<Card> cards;
    CardRecyclerView mRecyclerView;
    FavAdapter myAdapter;

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
        setHasOptionsMenu(true);
        String data = getArguments().getString("somedata");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate and locate the main ImageView
        final View v = inflater.inflate(R.layout.fragement_app, container, false);
        init(v);
        createcard(v);
        return v;
    }
    private void init(View view) {

    }



    private void createcard(View v) {
        cards = new ArrayList<>();
        //Create a Card
        myAdapter = new FavAdapter(getActivity(),cards);

        List<AppTracker> eventList = null;
        eventList = AppTracker.getAllByFav(1);

        for (int i = 0; i < eventList.size(); i++){
            try {
                AppTracker event =  eventList.get(i);
                FavCards customCards = new FavCards(getActivity(),myAdapter);
                customCards.setIsSystemApp(event.isSystenApp);
                customCards.setmTitle(event.appName);
                customCards.setmCategory(event.catName);
                customCards.setIsFavourite(event.isFavourite);
                if (event.isFavourite == 0)
                    customCards.setFavResource(R.mipmap.star_green);
                else
                    customCards.setFavResource(R.mipmap.star_orange);
                customCards.setmPackageName(event.packageName);
                customCards.setPosition(i);
                Drawable icon = getActivity().getPackageManager().getApplicationIcon(event.packageName);
                customCards.setImageResources(icon);
                customCards.setmRating(4);
                cards.add(customCards);
            }catch (PackageManager.NameNotFoundException e){
                e.printStackTrace();
            }

        }

        myAdapter.notifyDataSetChanged();

        //Staggered grid view
        mRecyclerView = (CardRecyclerView) v.findViewById(R.id.carddemo_recyclerview);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Set the empty view
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(myAdapter);
        }

    }
}