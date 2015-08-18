package com.app.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.aggro.R;
import com.app.gridcategory.SquareImageView;
import com.app.holder.ChildHolder;
import com.app.holder.ChildItem;
import com.app.holder.GroupItem;


/**
 * Created by C-ShellWin on 12/28/2014.
 */
public class AppLibrary extends BaseAdapter {

	private Context mContext;
	private GroupItem groupitem;
	private LayoutInflater inflater;

	public AppLibrary(Context mContext, GroupItem groupitem) {
		this.mContext = mContext;
		this.groupitem = groupitem;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		if (groupitem != null)
			return groupitem.items.size();
		else
			return 0;
	}

	@Override
	public ChildItem getItem(int position) {
		return groupitem.items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChildHolder holder = null;
		ChildItem item = getItem(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.lib_item, parent,
					false);
			holder = new ChildHolder();
			convertView.setTag(holder);
		} else {
			holder = (ChildHolder) convertView.getTag();
		}

		holder.mAppImageView = (SquareImageView) convertView
				.findViewById(R.id.app_icon);
		holder.mAppName = (TextView)convertView.findViewById(R.id.app_name);
		holder.mAppCategory = (TextView)convertView.findViewById(R.id.app_cat);

//		holder.mAppImageView.setImageResource(item.mAppIconUrl);
		holder.mAppName.setText(item.mAppname);
		holder.mAppCategory.setText(item.mAppCategory);

		return convertView;
	}
}
