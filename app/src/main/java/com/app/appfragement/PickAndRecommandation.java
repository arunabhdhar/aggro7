package com.app.appfragement;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aggro.MainActivity;
import com.app.aggro.R;
import com.app.api.Category;
import com.app.getterAndSetter.MyCategory;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardGridView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PickAndRecommandation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PickAndRecommandation extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    CoordinatorLayout rootLayout;
    Toolbar toolbar;

    private CardGridView mListView;
    private CardGridArrayAdapter mCardArrayAdapter;

    int[] myImageList = new int[]{R.drawable.airoplan, R.drawable.hospital,R.drawable.amusement_park, R.drawable.atm,R.drawable.artgallery, R.drawable.bakery,R.drawable.library, R.drawable.bank
    ,R.drawable.bar, R.drawable.saloon,R.drawable.loading, R.drawable.book_store,R.drawable.meal_take_away, R.drawable.movie,R.drawable.cardeal, R.drawable.night_club,R.drawable.park, R.drawable.food};

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PickAndRecommandation.
     */
    // TODO: Rename and change types and number of parameters
    public static PickAndRecommandation newInstance(String param1, String param2) {
        PickAndRecommandation fragment = new PickAndRecommandation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PickAndRecommandation() {
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
        return inflater.inflate(R.layout.fragment_pick_and_recommandation, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInstances(view);
    }

    private void initInstances(View view) {

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Pick&Recommendation");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCard();

    }

    /**
     * This method builds a simple list of cards
     */
    private void initCard() {

        String[] places = getResources().getStringArray(R.array.pick_recommendation_array);
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < places.length; i++) {

            GplayGridCard card = new GplayGridCard(getActivity());

            //Only for test, use different titles and ratings
            card.headerTitle =  places[i];
            card.secondaryTitle = "place type " + places[i];

            //Only for test, change some icons
            card.resourceIdThumbnail = myImageList[i];
//            if ((i % 3 == 0)) {
//                card.resourceIdThumbnail = R.drawable.ic_ic_dh_bat;
//            } else if ((i % 6 == 1)) {
//                card.resourceIdThumbnail = R.drawable.ic_ic_dh_net;
//            } else if ((i % 6 == 2)) {
//                card.resourceIdThumbnail = R.drawable.ic_tris;
//            } else if ((i % 6 == 3)) {
//                card.resourceIdThumbnail = R.drawable.ic_info;
//            } else if ((i % 6 == 4)) {
//                card.resourceIdThumbnail = R.drawable.ic_smile;
//            }

            card.init();
            cards.add(card);
        }

        //Set the adapter
        mCardArrayAdapter = new CardGridArrayAdapter(getActivity(), cards);

        mListView = (CardGridView) getActivity().findViewById(R.id.carddemo_extras_grid_base1);
        if (mListView != null) {
            setAlphaAdapter();
        }
    }

    private void setAlphaAdapter() {
        AnimationAdapter animCardArrayAdapter = new AlphaInAnimationAdapter(mCardArrayAdapter);
        animCardArrayAdapter.setAbsListView(mListView);
        mListView.setExternalAdapter(animCardArrayAdapter, mCardArrayAdapter);
    }

    /**
     * A card as Google Play
     */
    public class GplayGridCard extends Card {

        protected TextView mTitle;
        protected TextView mSecondaryTitle;
        protected RatingBar mRatingBar;
        protected int resourceIdThumbnail = -1;
        protected int count;

        protected String headerTitle;
        protected String secondaryTitle;
        protected float rating;

        public GplayGridCard(Context context) {
            super(context, R.layout.carddemo_extras_gplay_inner_content);
        }

        public GplayGridCard(Context context, int innerLayout) {
            super(context, innerLayout);
        }

        private void init() {

            //Add header with the overflow button
            CardHeader header = new CardHeader(getContext());
            header.setButtonOverflowVisible(true);
            header.setTitle(headerTitle);


            addCardHeader(header);

            //Add thumbnail
            GplayGridThumb thumbnail = new GplayGridThumb(getContext());
            if (resourceIdThumbnail > -1)
                thumbnail.setDrawableResource(resourceIdThumbnail);
            else
                thumbnail.setDrawableResource(R.drawable.ic_ic_launcher_web);
                addCardThumbnail(thumbnail);

            //Listeners
            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    String localvariable = null;
                    Category.AggroCategory catLevel = null;
                    if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[0].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[0].toString();
                        catLevel = Category.AggroCategory.PRODUCTIVITY;
                    } else if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[1].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[1].toString();
                        catLevel = Category.AggroCategory.MEDICAL;
                        Log.e("GETE", "" + card.getCardHeader().getTitle());
                    }
                    else if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[2].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[2].toString();
                        catLevel = Category.AggroCategory.LIFESTYLE;
                        Log.e("GETE", "" + card.getCardHeader().getTitle());
                    }
                    else if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[3].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[3].toString();
                        catLevel = Category.AggroCategory.PRODUCTIVITY;
                        Log.e("GETE", "" + card.getCardHeader().getTitle());
                    }
                    else if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[4].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[4].toString();
                        catLevel = Category.AggroCategory.LIFESTYLE;
                        Log.e("GETE", "" + card.getCardHeader().getTitle());
                    }
                    else if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[5].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[5].toString();
                        catLevel = Category.AggroCategory.SHOPPING;
                        Log.e("GETE", "" + card.getCardHeader().getTitle());
                    }
                    else if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[6].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[6].toString();
                        catLevel = Category.AggroCategory.LIBRARIES_AND_DEMO;
                        Log.e("GETE", "" + card.getCardHeader().getTitle());
                    }
                    else if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[7].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[7].toString();
                        catLevel = Category.AggroCategory.LIFESTYLE;
                        Log.e("GETE", "" + card.getCardHeader().getTitle());
                    }
                    else if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[8].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[8].toString();
                        catLevel = Category.AggroCategory.SHOPPING;
                        Log.e("GETE", "" + card.getCardHeader().getTitle());
                    }
                    else if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[9].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[9].toString();
                        catLevel = Category.AggroCategory.LIFESTYLE;
                        Log.e("GETE", "" + card.getCardHeader().getTitle());
                    }
                    else if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[10].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[10].toString();
                        catLevel = Category.AggroCategory.TRAVEL_AND_LOCAL;
                        Log.e("GETE", "" + card.getCardHeader().getTitle());
                    }
                    else if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[11].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[11].toString();
                        catLevel = Category.AggroCategory.NEWS_AND_MAGAZINES;
                        Log.e("GETE", "" + card.getCardHeader().getTitle());
                    }
                    else if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[12].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[12].toString();
                        catLevel = Category.AggroCategory.SHOPPING;
                        Log.e("GETE", "" + card.getCardHeader().getTitle());
                    }
                    else if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[13].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[13].toString();
                        catLevel = Category.AggroCategory.LIFESTYLE;
                        Log.e("GETE", "" + card.getCardHeader().getTitle());
                    }
                    else if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[14].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[14].toString();
                        catLevel = Category.AggroCategory.TRAVEL_AND_LOCAL;
                        Log.e("GETE", "" + card.getCardHeader().getTitle());
                    }
                    else if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[15].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[15].toString();
                        catLevel = Category.AggroCategory.TRAVEL_AND_LOCAL;
                        Log.e("GETE", "" + card.getCardHeader().getTitle());
                    }
                    else if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[16].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[16].toString();
                        catLevel = Category.AggroCategory.LIFESTYLE;
                        Log.e("GETE", "" + card.getCardHeader().getTitle());
                    }
                    else if (card.getCardHeader().getTitle().equals(getResources().getStringArray(R.array.pick_recommendation_array)[17].toString())) {
                        localvariable = getResources().getStringArray(R.array.pick_recommendation_array)[17].toString();
                        catLevel = Category.AggroCategory.SHOPPING;
                        Log.e("GETE", "" + card.getCardHeader().getTitle());
                    }

                    MyCategory.setIsCustomcategory(false);
                    selectshowCatApp(localvariable,catLevel);
                }
            });
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            //Populate the inner elements

            TextView title = (TextView) view.findViewById(R.id.carddemo_extras_gplay_main_inner_title);
            title.setText("FREE");

            TextView subtitle = (TextView) view.findViewById(R.id.carddemo_extras_gplay_main_inner_subtitle);
            subtitle.setText(secondaryTitle);

            RatingBar mRatingBar = (RatingBar) parent.findViewById(R.id.carddemo_extras_gplay_main_inner_ratingBar);

            mRatingBar.setNumStars(5);
            mRatingBar.setMax(5);
            mRatingBar.setStepSize(0.5f);
            mRatingBar.setRating(rating);
        }

        /**
         * CardThumbnail
         */
        class GplayGridThumb extends CardThumbnail {

            public GplayGridThumb(Context context) {
                super(context);
            }

            @Override
            public void setupInnerViewElements(ViewGroup parent, View viewImage) {

                viewImage.getLayoutParams().width = 100;
                viewImage.getLayoutParams().height = 100;


            }
        }
    }

    private void selectshowCatApp(String local, Category.AggroCategory level) {
        Fragment fragment = ShowCatAppFragement.newInstance(local, level);
        FragmentManager fragmentManager = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.main_content, fragment);
        MainActivity.fragmentStack.lastElement().onPause();
        ft.hide(MainActivity.fragmentStack.lastElement());
        MainActivity.fragmentStack.push(fragment);
        ft.commit();
    }
}
