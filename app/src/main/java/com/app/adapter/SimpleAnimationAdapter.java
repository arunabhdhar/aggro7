package com.app.adapter;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aggro.R;
import com.app.gridcategory.ImageItem;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.animators.internal.ViewHelper;

import java.security.SecureRandom;
import java.util.List;


public class SimpleAnimationAdapter extends UltimateViewAdapter<RecyclerView.ViewHolder> {
    private List<ImageItem> stringList;
    Context mContext;
    public SimpleAnimationAdapter(List<ImageItem> stringList,Context mContext) {
        this.stringList = stringList;
        mContext = mContext;
    }
    private int mDuration = 300;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mLastPosition = 5;

    private boolean isFirstOnly = true;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= stringList.size() : position < stringList.size()) && (customHeaderView != null ? position > 0 : true)) {

            ((ViewHolder) holder).textViewSample.setText(stringList.get(customHeaderView != null ? position - 1 : position).getTitle());
            ((ViewHolder) holder).imageViewSample.setImageResource(stringList.get(customHeaderView != null ? position - 1 : position).getImage());
            // ((ViewHolder) holder).itemView.setActivated(selectedItems.get(position, false));
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
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new UltimateRecyclerviewViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public void insert(ImageItem string, int position) {
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
        URLogs.d("position--" + position + "   " + getItem(position));
        if (getItem(position).getTitle().length() > 0)
            return getItem(position).getTitle().length();
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
//        textView.setText(String.valueOf(getItem(position).getTitle()));
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
//
//    private int getRandomColor() {
//        SecureRandom rgen = new SecureRandom();
//        return Color.HSVToColor(150, new float[]{
//                rgen.nextInt(359), 1, 1
//        });
//    }


    class ViewHolder extends UltimateRecyclerviewViewHolder {

        TextView textViewSample;
        ImageView imageViewSample;

        public ViewHolder(View itemView) {
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
            textViewSample = (TextView) itemView.findViewById(
                    R.id.text);
            imageViewSample = (ImageView) itemView.findViewById(R.id.picture);

        }
    }

    public ImageItem getItem(int position) {
        if (customHeaderView != null)
            position--;
        if (position < stringList.size())
            return stringList.get(position);
        else return new ImageItem(mContext.getResources().getString(R.string.cat_all_apps),R.mipmap.games);
    }

}
