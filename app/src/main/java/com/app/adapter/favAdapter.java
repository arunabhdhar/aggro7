package com.app.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.app.Utility.ColoredRatingBar;
import com.app.Utility.CustomVolleyRequestQueue;
import com.app.aggro.R;
import com.app.holder.ChildItem;

import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class favAdapter extends RecyclerView.Adapter<favAdapter.ItemViewHolder> {

    List<ChildItem> contents;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    private Context context;
    CreateFav createFav;
    public favAdapter(List<ChildItem> contents, Context context,final CreateFav createFav) {
        this.contents = contents;
        this.context = context;
        this.createFav = createFav;
        Log.e("Content size","" + contents.size());
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        if (contents != null)
            return contents.size();
        else
            return -1;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
//                view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.list_item_card_big, parent, false);
//                return new ItemViewHolder(view);
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                ItemViewHolder vh = new ItemViewHolder(view);
                vh.rel_add_app.setTag(vh);
                vh.rel_add_app.setOnClickListener(clickItemListener());
                return vh;
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                ItemViewHolder vh = new ItemViewHolder(view);
                vh.rel_add_app.setTag(vh);
                vh.rel_add_app.setOnClickListener(clickItemListener());
                return vh;
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        int s =position;
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                bindItemCardCell(position,holder);
                break;
            case TYPE_CELL:
                bindItemCardCell(position,holder);
                break;
        }
    }

    private void bindItemCardCell(int position,ItemViewHolder holder) {

            Log.e("fedf", "" + "d ");
             String appname = contents.get(position).mAppname;
            holder.mAppImageView.setImageUrl(contents.get(position).mAppIconUrl, holder.mImageLoader);
            holder.mAppName.setText(contents.get(position).mAppname);
            holder.mAppCategory.setText(contents.get(position).mAppCategory);
            holder.coloredRatingBar.setRating(contents.get(position).mRating);

        if (contents.get(position).isFavourite == 0){
            holder.fav_star.setImageResource(R.mipmap.star_green);
        }else{
            holder.fav_star.setImageResource(R.mipmap.star_orange);
        }
    }


    private View.OnClickListener clickItemListener(){
        View.OnClickListener l = null;
        l = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemViewHolder holder = (ItemViewHolder) view.getTag();
                int id = holder.getPosition();
                if (view.getId() == holder.rel_add_app.getId()){
                    id = id- 1;
//                    Toast.makeText(mContext, "imageIV onClick at" + id, Toast.LENGTH_SHORT).show();
                     createFav.openApp(contents.get(id));

                } else {
                    Toast.makeText(context, "RecyclerView Item onClick at " + id, Toast.LENGTH_SHORT).show();
                }
            }
        };

        return l;
    }



    class ItemViewHolder extends RecyclerView.ViewHolder{
        NetworkImageView mAppImageView;
        TextView mAppName,mAppCategory;
        CardView card_view;
        ImageLoader mImageLoader;
        ColoredRatingBar coloredRatingBar;
        RelativeLayout rel_fav,rel_add_app;
        ImageView fav_star;
        public ItemViewHolder(View convertView){
            super(convertView);
            card_view = (CardView)convertView.findViewById(R.id.card_view);
            mAppImageView = (NetworkImageView) convertView
                    .findViewById(R.id.app_icon);
            mAppName = (TextView) convertView.findViewById(R.id.app_name);
            mAppCategory = (TextView)convertView.findViewById(R.id.app_cat);
            coloredRatingBar = (ColoredRatingBar)itemView.findViewById(R.id.coloredRatingBar1);
            rel_fav = (RelativeLayout)itemView.findViewById(R.id.rel_fav_app);
            rel_add_app = (RelativeLayout)itemView.findViewById(R.id.rel_add_app);
            fav_star = (ImageView)itemView.findViewById(R.id.fav_star);
            mImageLoader = CustomVolleyRequestQueue.getInstance(context.getApplicationContext())
                    .getImageLoader();
        }

    }

    public interface CreateFav {
        public void openApp(ChildItem childItem);
    }

}