package com.app;

import com.app.modal.AppList;
import com.app.response.CustomMsg;

/**
 * Created by ericbasendra on 25/09/15.
 */
public interface OnCustomCategoryItemClick {

    public void downloadApp(int downloadId,CustomMsg appList);
    public boolean openApp(CustomMsg appList);
    public void createCustomcategory(CustomMsg appList);

}
