package com.app.getterAndSetter;

/**
 * Created by ericbasendra on 07/09/15.
 */
public class MyCategory {

    static boolean isCustomcategory= false;
     static String categoryName = "";

    public static String getCategoryName() {
        return categoryName;
    }

    public static void setCategoryName(String categoryName) {
        MyCategory.categoryName = categoryName;
    }

    public static boolean isCustomcategory() {
        return isCustomcategory;
    }

    public static void setIsCustomcategory(boolean isCustomcategory) {
        MyCategory.isCustomcategory = isCustomcategory;
    }


}
