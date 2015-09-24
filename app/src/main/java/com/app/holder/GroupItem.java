package com.app.holder;

import com.app.gridcategory.ImageItem;
import com.app.modal.AppList;
import com.app.response.CustomMsg;

import java.util.ArrayList;
import java.util.List;

public class GroupItem {
	public String title;
	public List<ChildItem> items = new ArrayList<ChildItem>();
	public List<AppList> appLists = new ArrayList<AppList>();
	public List<ImageItem> gridList = new ArrayList<ImageItem>();
	public List<CustomMsg> customMsgList = new ArrayList<CustomMsg>();
}
