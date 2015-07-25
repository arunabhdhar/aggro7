package com.app.gridcategory;

/**
 * Created by ericbasendra on 22/07/15.
 */
import android.graphics.Bitmap;

public class ImageItem {
    private int image;
    private String title;

    public ImageItem(String title,int image) {
        super();
        this.image = image;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
