package com.app.getterAndSetter;

/**
 * Created by ericbasendra on 07/09/15.
 */
public class MyCategory {

    static boolean isCustomcategory= false;
    private String categoryName = "";

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public static boolean isCustomcategory() {
        return isCustomcategory;
    }

    public static void setIsCustomcategory(boolean isCustomcategory) {
        MyCategory.isCustomcategory = isCustomcategory;
    }


}
