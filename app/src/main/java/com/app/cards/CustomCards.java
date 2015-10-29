package com.app.cards;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.app.Utility.ColoredRatingBar;
import com.app.Utility.Utility;
import com.app.adapter.AppLibrary;
import com.app.adapter.FavAdapter;
import com.app.aggro.MyApplication;
import com.app.aggro.R;
import com.app.local.database.AppTracker;

import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by ericbasendra on 20/10/15.
 */
public class CustomCards extends Card {

    protected NetworkImageView appIcon;
    protected TextView title;
    protected TextView category;
    protected ColoredRatingBar ratingBar;
    protected ImageView fav_star;

    private Drawable imageResources;
    private int favResource;
    private String mTitle;
    private String mCategory;
    private String mPackageName;
    private float mRating;
    private boolean isSystemApp = false;
    private int position;

    private int isFavourite = 0;
    private List<Card> cardList;
    private AppLibrary appLibrary;

    private Context mContext;
    /**
     * Constructor with a custom inner layout
     * @param context
     */
    public CustomCards(Context context,AppLibrary appLibrary) {
        this(context, R.layout.carddemo_mycard_inner_content);
        this.appLibrary = appLibrary;
        this.mContext = context;
    }


    /**
     *
     * @param context
     * @param innerLayout
     */
    public CustomCards(Context context, int innerLayout){
        super(context,innerLayout);
        init(context);

    }

    /**
     * Init
     */
    private void init(final Context context){

        //No Header

        //Set a OnClickListener listener
//        setOnClickListener(new OnCardClickListener() {
//            @Override
//            public void onClick(Card card, View view) {
//                Toast.makeText(getContext(), "Click Listener card=", Toast.LENGTH_LONG).show();
//            }
//        });

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
//        super.setupInnerViewElements(parent, view);

        title = (TextView)parent.findViewById(R.id.app_name);
        category = (TextView)parent.findViewById(R.id.app_cat);
        appIcon = (NetworkImageView)parent.findViewById(R.id.app_icon);
        ratingBar = (ColoredRatingBar)parent.findViewById(R.id.coloredRatingBar1);
        fav_star = (ImageView)parent.findViewById(R.id.fav_star);

        if (title != null){
            title.setText(getmTitle());
        }
        if (category !=null){
            category.setText(getmCategory());
        }
        if (appIcon != null){
            appIcon.setBackground(getImageResources());
        }
        if (ratingBar != null){
            ratingBar.setRating(getmRating());
        }

        if (fav_star != null){
            fav_star.setImageResource(getFavResource());
        }

        RelativeLayout relativeLayout = (RelativeLayout)parent.findViewById(R.id.rel_add_app);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomCards customCards = (CustomCards) getCardView().getCard();
                Utility.openDownloadedApp(mContext,customCards.getmPackageName());
            }
        });

        RelativeLayout relativeFavApp = (RelativeLayout)parent.findViewById(R.id.rel_fav_app);
        relativeFavApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomCards customCards = (CustomCards) getCardView().getCard();
                MyApplication.getInstance().trackEvent("RecyleViewFragement", "Adding", "Add app to favourite");
                AppTracker appTracker = AppTracker.getSingleEntry(customCards.getmPackageName());
                if (customCards.getIsFavourite() == 0){
                    customCards.setIsFavourite(1);
                    customCards.setFavResource(R.mipmap.star_orange);
                    fav_star.setImageResource(getFavResource());
                }else{
                    customCards.setIsFavourite(0);
                    customCards.setFavResource(R.mipmap.star_green);
                    fav_star.setImageResource(getFavResource());
                }
                if (appTracker != null){
                    appTracker.isFavourite = customCards.getIsFavourite();
                    appTracker.save();
                }

                appLibrary.notifyItemChanged(customCards.getPosition());
            }
        });

    }




    public Drawable getImageResources() {
        return imageResources;
    }

    public void setImageResources(Drawable imageResources) {
        this.imageResources = imageResources;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public float getmRating() {
        return mRating;
    }

    public void setmRating(float mRating) {
        this.mRating = mRating;
    }

    public boolean isSystemApp() {
        return isSystemApp;
    }

    public void setIsSystemApp(boolean isSystemApp) {
        this.isSystemApp = isSystemApp;
    }


    public String getmPackageName() {
        return mPackageName;
    }

    public void setmPackageName(String mPackageName) {
        this.mPackageName = mPackageName;
    }

    public int getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getFavResource() {
        return favResource;
    }

    public void setFavResource(int favResource) {
        this.favResource = favResource;
    }
}
