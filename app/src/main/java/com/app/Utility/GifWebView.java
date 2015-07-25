package com.app.Utility;

import android.content.Context;
import android.webkit.WebView;

/**
 * Created by ericbasendra on 25/07/15.
 */
public class GifWebView extends WebView {

    public GifWebView(Context context, String path) {
        super(context);

        loadUrl(path);
    }
}
