package com.app.modal;

/**
 * Created by ericbasendra on 11/08/15.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchDetail {

    @Expose
    private List<AppList> results = new ArrayList<AppList>();
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
    @Expose
    private Integer limit;

    /**
     *
     * @return
     * The results
     */
    public List<AppList> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<AppList> results) {
        this.results = results;
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

}


