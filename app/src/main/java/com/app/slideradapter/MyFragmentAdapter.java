package com.app.slideradapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

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
    MaterialViewPager materialViewPager;
    public MyFragmentAdapter(Context context, FragmentManager fm, List<Fragment> myFrags, MaterialViewPager materialViewPager) {
        super(fm);
        this.myFragments = myFrags;
        this.context = context;
        this.materialViewPager = materialViewPager;
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
        if(position == oldPosition)
            return;
        oldPosition = position;

        int color = 0;
        String imageUrl = "";
        switch (oldPosition){
            case 0:
                imageUrl = "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg";
                color = context.getResources().getColor(R.color.blue);
                break;
            case 1:
                imageUrl = "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg";
                color = context.getResources().getColor(R.color.green);
                break;
            case 2:
                imageUrl = "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg";
                color = context.getResources().getColor(R.color.cyan);
                break;
            case 3:
                imageUrl = "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg";
                color = context.getResources().getColor(R.color.red);
                break;
        }

        final int fadeDuration = 400;

        //change header's color and image
//        materialViewPager.setImageUrl(imageUrl,fadeDuration);
        materialViewPager.setImageDrawable(context.getResources().getDrawable(R.mipmap.test_back2), fadeDuration);
//        materialViewPager.setColor(color,fadeDuration);

    }




    public static int getPos() {
        return pos;
    }

    public static void setPos(int pos) {
        MyFragmentAdapter.pos = pos;
    }


}
