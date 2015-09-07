package com.app.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.aggro.R;
import com.app.gridcategory.ImageItem;

import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class AggroRecyclerThreeWayGridViewAdapter extends RecyclerView.Adapter<AggroRecyclerThreeWayGridViewAdapter.ItemViewHolder> {

    List<ImageItem> contents;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public AggroRecyclerThreeWayGridViewAdapter(List<ImageItem> contents) {
        this.contents = contents;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_CELL;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        if (contents != null)
            return contents.size();
        else
            return 1;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                return new ItemViewHolder(view);
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_grid_small, parent, false);
                return new ItemViewHolder(view);
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                break;
            case TYPE_CELL:
                bindItemCardCell(position,holder);
                break;
        }
    }

    private void bindItemCardCell(int position,ItemViewHolder holder){
        if (position < getItemCount()) {
            holder.image.setImageResource(contents.get(position).getImage());
            holder.imageTitle.setText(contents.get(position).getTitle());
        }

    }



    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView imageTitle;
        ImageView image;;
        CardView card_view;
        public ItemViewHolder(View convertView){
            super(convertView);
            card_view = (CardView)convertView.findViewById(R.id.card_view);
            imageTitle = (TextView)convertView.findViewById(R.id.text);
            image = (ImageView)convertView.findViewById(R.id.picture);
        }


    }
}