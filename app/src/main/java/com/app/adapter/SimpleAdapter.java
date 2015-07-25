package com.app.adapter;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.app.Utility.ColoredRatingBar;
import com.app.Utility.CustomVolleyRequestQueue;
import com.app.Utility.GifWebView;
import com.app.aggro.R;
import com.app.gridcategory.SquareImageView;
import com.app.modal.AppList;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.animators.internal.ViewHelper;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.List;


public class SimpleAdapter extends UltimateViewAdapter<SimpleAdapter.SimpleAdapterViewHolder> {
    private List<AppList> stringList;

    private int mDuration = 500;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mLastPosition = 5;

    private boolean isFirstOnly = true;
    private Context mContext;
    public SimpleAdapter(List<AppList> stringList, Context mContext) {
        this.stringList = stringList;
        this.mContext = mContext;
    }


    @Override
    public void onBindViewHolder(final SimpleAdapterViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= stringList.size() : position < stringList.size()) && (customHeaderView != null ? position > 0 : true)) {

            ((SimpleAdapterViewHolder) holder).appName.setText(stringList.get(customHeaderView != null ? position - 1 : position).getTitle());
            ((SimpleAdapterViewHolder) holder).appCategory.setText(stringList.get(customHeaderView != null ? position - 1 : position).getCategory());
            ((SimpleAdapterViewHolder) holder).coloredRatingBar.setRating(((Double) stringList.get(customHeaderView != null ? position - 1 : position).getRating()).floatValue());
            String url = stringList.get(customHeaderView != null ? position - 1 : position).getIcon();
            ((SimpleAdapterViewHolder) holder).imageViewSample.setImageUrl(url, ((SimpleAdapterViewHolder) holder).mImageLoader);

            // ((ViewHolder) holder).itemView.setActivated(selectedItems.get(position, false));
            if (mDragStartListener != null) {
//                ((ViewHolder) holder).imageViewSample.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
//                            mDragStartListener.onStartDrag(holder);
//                        }
//                        return false;
//                    }
//                });

                ((SimpleAdapterViewHolder) holder).item_view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
            }
        }

        if (!isFirstOnly || position > mLastPosition) {
            for (Animator anim : getAdapterAnimations(holder.itemView, AdapterAnimationType.ScaleIn)) {
                anim.setDuration(mDuration).start();
                anim.setInterpolator(mInterpolator);
            }
            mLastPosition = position;
        } else {
            ViewHelper.clear(holder.itemView);
        }

    }

    @Override
    public int getAdapterItemCount() {
        return stringList.size();
    }

    @Override
    public SimpleAdapterViewHolder getViewHolder(View view) {
        return new SimpleAdapterViewHolder(view, false);
    }

    @Override
    public SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_adapter, parent, false);
        SimpleAdapterViewHolder vh = new SimpleAdapterViewHolder(v, true);
        return vh;
    }


    public void insert(AppList string, int position) {
        insert(stringList, string, position);
    }

    public void remove(int position) {
        remove(stringList, position);
    }

    public void clear() {
        clear(stringList);
    }

    @Override
    public void toggleSelection(int pos) {
        super.toggleSelection(pos);
    }

    @Override
    public void setSelected(int pos) {
        super.setSelected(pos);
    }

    @Override
    public void clearSelection(int pos) {
        super.clearSelection(pos);
    }


    public void swapPositions(int from, int to) {
        swapPositions(stringList, from, to);
    }


    @Override
    public long generateHeaderId(int position) {
        // URLogs.d("position--" + position + "   " + getItem(position));
        if (getItem(position).getSize() > 0)
            return getItem(position).getSize();
        else return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stick_header_item, viewGroup, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.stick_text);
//        textView.setText(String.valueOf(getItem(position).charAt(0)));
//        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AA70DB93"));
        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AAffffff"));
        ImageView imageView = (ImageView) viewHolder.itemView.findViewById(R.id.stick_img);

        SecureRandom imgGen = new SecureRandom();
        switch (imgGen.nextInt(3)) {
            case 0:
                imageView.setImageResource(R.mipmap.test_back2);
                break;
            case 1:
                imageView.setImageResource(R.mipmap.test_back3);
                break;
            case 2:
                imageView.setImageResource(R.mipmap.test_back2);
                break;
        }

    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        swapPositions(fromPosition, toPosition);
//        notifyItemMoved(fromPosition, toPosition);
        super.onItemMove(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        remove(position);
        // notifyItemRemoved(position);
//        notifyDataSetChanged();
        super.onItemDismiss(position);
    }
//
//    private int getRandomColor() {
//        SecureRandom rgen = new SecureRandom();
//        return Color.HSVToColor(150, new float[]{
//                rgen.nextInt(359), 1, 1
//        });
//    }

    public void setOnDragStartListener(OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;

    }


    public class SimpleAdapterViewHolder extends UltimateRecyclerviewViewHolder {

        TextView appName, appCategory;
        NetworkImageView imageViewSample;
        ColoredRatingBar coloredRatingBar;
        ProgressBar progressBarSample;
        View item_view;
         ImageLoader mImageLoader;

        public SimpleAdapterViewHolder(View itemView, boolean isItem) {
            super(itemView);
//            itemView.setOnTouchListener(new SwipeDismissTouchListener(itemView, null, new SwipeDismissTouchListener.DismissCallbacks() {
//                @Override
//                public boolean canDismiss(Object token) {
//                    Logs.d("can dismiss");
//                    return true;
//                }
//
//                @Override
//                public void onDismiss(View view, Object token) {
//                   // Logs.d("dismiss");
//                    remove(getPosition());
//
//                }
//            }));
            if (isItem) {
                appName = (TextView) itemView.findViewById(
                        R.id.app_name);
                appCategory = (TextView) itemView.findViewById(
                        R.id.app_cat);
                imageViewSample = (NetworkImageView) itemView.findViewById(R.id.app_icon);
                progressBarSample = (ProgressBar) itemView.findViewById(R.id.progressbar);
                coloredRatingBar = (ColoredRatingBar)itemView.findViewById(R.id.coloredRatingBar1);
                progressBarSample.setVisibility(View.GONE);
                item_view = itemView.findViewById(R.id.itemview);
                mImageLoader = CustomVolleyRequestQueue.getInstance(mContext.getApplicationContext())
                        .getImageLoader();

            }

        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public AppList getItem(int position) {
        if (customHeaderView != null)
            position--;
        if (position < stringList.size())
            return stringList.get(position);
        else return new AppList();
    }


}