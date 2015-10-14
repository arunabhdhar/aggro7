package com.app.slideradapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.app.Updateable;
import com.app.aggro.R;
import com.app.appfragement.fragment.RecyclerViewFragment;
import com.github.florent37.materialviewpager.MaterialViewPager;

import java.util.List;

/**
 * Created by sonal on 7/2/2015.
 */
public class MyFragmentAdapter extends FragmentStatePagerAdapter {

    public static int pos = 0;
    public static int LEFT_PAGE = 0;
    public static int CENTERED_PAGE = 1;
    public static int RIGHT_PAGE =2;

    private List<Fragment> myFragments;
    private Context context;
    int oldPosition = -1;
    ViewPager materialViewPager;
    ImageView header;
    public MyFragmentAdapter(Context context, FragmentManager fm, List<Fragment> myFrags, ViewPager materialViewPager,ImageView header) {
        super(fm);
        this.myFragments = myFrags;
        this.context = context;
        this.materialViewPager = materialViewPager;
        this.header = header;
    }

    public void updateData(){
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {

        return myFragments.get(position);

    }

    @Override
    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }

    @Override
    public int getCount() {

        return myFragments.size();
    }



    @Override
    public CharSequence getPageTitle(int position) {

        setPos(position);

        String PageTitle = "";

        switch(pos)
        {
            case 0:
                PageTitle = context.getResources().getString(R.string.view_pager_apps);
                break;
            case 1:
                PageTitle = context.getResources().getString(R.string.view_pager_category);
                break;
            case 2:
                PageTitle = context.getResources().getString(R.string.view_pager_fav);
                break;
        }
        return PageTitle;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        //only if position changed
        Animation fadeIn = null;
        if(position == oldPosition)
            return;
        oldPosition = position;

        switch (oldPosition){
            case 0:
                header.setImageResource(R.drawable.header);
                fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                header.startAnimation(fadeIn);
                break;
            case 1:
                header.setImageResource(R.drawable.header_2);
                fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                header.startAnimation(fadeIn);
                break;
            case 2:
                header.setImageResource(R.drawable.nav_header_bg);
                fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                header.startAnimation(fadeIn);
                break;
            case 3:
                header.setImageResource(R.drawable.header_2);
                fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                header.startAnimation(fadeIn);
                break;
        }

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Animation fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
                header.startAnimation(fadeOut);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });


    }




    public static int getPos() {
        return pos;
    }

    public static void setPos(int pos) {
        MyFragmentAdapter.pos = pos;
    }


}
