package com.app.response;

/**
 * Created by shivram on 17/09/15.
 */
import java.util.HashMap;
import java.util.Map;
public class CustomMsg {

    private String categoryName;
    private String appName;
    private String iconLink;
    private String category;
    private String packagename;
    private Double appRating;
    private boolean isInstalled = false;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     *
     * @param categoryName
     * The category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     *
     * @return
     * The appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     *
     * @param appName
     * The appName
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     *
     * @return
     * The iconLink
     */
    public String getIconLink() {
        return iconLink;
    }

    /**
     *
     * @param iconLink
     * The iconLink
     */
    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }

    /**
     *
     * @return
     * The category
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     * The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @return
     * The appRating
     */
    public Double getAppRating() {
        return appRating;
    }


    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public boolean isInstalled() {
        return isInstalled;
    }

    public void setIsInstalled(boolean isInstalled) {
        this.isInstalled = isInstalled;
    }

    /**
     *
     * @param appRating
     * The appRating
     */
    public void setAppRating(Double appRating) {
        this.appRating = appRating;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
