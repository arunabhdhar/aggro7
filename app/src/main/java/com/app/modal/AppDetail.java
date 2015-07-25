package com.app.modal;

/**
 * Created by ericbasendra on 21/07/15.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppDetail {

    @SerializedName("cat_key")
    @Expose
    private String catKey;
    @Expose
    private Integer category;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("cat_type")
    @Expose
    private Integer catType;
    @Expose
    private String country;
    @Expose
    private String date;
    @Expose
    private List<AppList> appList = new ArrayList<AppList>();
    @Expose
    private String listName;
    @Expose
    private Integer limit;
    @SerializedName("number_results")
    @Expose
    private Integer numberResults;
    @SerializedName("has_next")
    @Expose
    private Boolean hasNext;
    @Expose
    private Integer page;
    @SerializedName("num_pages")
    @Expose
    private Integer numPages;

    /**
     *
     * @return
     * The catKey
     */
    public String getCatKey() {
        return catKey;
    }

    /**
     *
     * @param catKey
     * The cat_key
     */
    public void setCatKey(String catKey) {
        this.catKey = catKey;
    }

    /**
     *
     * @return
     * The category
     */
    public Integer getCategory() {
        return category;
    }

    /**
     *
     * @param category
     * The category
     */
    public void setCategory(Integer category) {
        this.category = category;
    }

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
     * The catType
     */
    public Integer getCatType() {
        return catType;
    }

    /**
     *
     * @param catType
     * The cat_type
     */
    public void setCatType(Integer catType) {
        this.catType = catType;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The appList
     */
    public List<AppList> getAppList() {
        return appList;
    }

    /**
     *
     * @param appList
     * The appList
     */
    public void setAppList(List<AppList> appList) {
        this.appList = appList;
    }

    /**
     *
     * @return
     * The listName
     */
    public String getListName() {
        return listName;
    }

    /**
     *
     * @param listName
     * The listName
     */
    public void setListName(String listName) {
        this.listName = listName;
    }

    /**
     *
     * @return
     * The limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     *
     * @param limit
     * The limit
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     *
     * @return
     * The numberResults
     */
    public Integer getNumberResults() {
        return numberResults;
    }

    /**
     *
     * @param numberResults
     * The number_results
     */
    public void setNumberResults(Integer numberResults) {
        this.numberResults = numberResults;
    }

    /**
     *
     * @return
     * The hasNext
     */
    public Boolean getHasNext() {
        return hasNext;
    }

    /**
     *
     * @param hasNext
     * The has_next
     */
    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    /**
     *
     * @return
     * The page
     */
    public Integer getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     *
     * @return
     * The numPages
     */
    public Integer getNumPages() {
        return numPages;
    }

    /**
     *
     * @param numPages
     * The num_pages
     */
    public void setNumPages(Integer numPages) {
        this.numPages = numPages;
    }

}

