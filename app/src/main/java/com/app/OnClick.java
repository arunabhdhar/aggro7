package com.app;

import com.app.modal.AppList;

/**
 * Created by ericbasendra on 14/08/15.
 */
public interface OnClick {

    public void downloadApp(int downloadId,AppList appList);
    public boolean openApp(AppList appList);
    public void createCustomcategory(AppList appList);
}
