package com.app.modal;

/**
 * Created by ericbasendra on 11/08/15.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("promo_video")
    @Expose
    private String promoVideo;
    @Expose
    private Boolean iap;
    @Expose
    private String lang;
    @Expose
    private String downloads;
    @Expose
    private String version;
    @Expose
    private String title;
    @Expose
    private String created;
    @Expose
    private List<String> screenshots = new ArrayList<String>();
    @Expose
    private String description;
    @SerializedName("cat_type")
    @Expose
    private Integer catType;
    @SerializedName("content_rating")
    @Expose
    private String contentRating;
    @SerializedName("i18n_lang")
    @Expose
    private List<String> i18nLang = new ArrayList<String>();
    @SerializedName("cat_key")
    @Expose
    private String catKey;
    @Expose
    private String website;
    @SerializedName("package_name")
    @Expose
    private String packageName;
    @SerializedName("market_update")
    @Expose
    private String marketUpdate;
    @Expose
    private Integer size;
    @SerializedName("cat_int")
    @Expose
    private Integer catInt;
    @Expose
    private String category;
    @Expose
    private String price;
    @Expose
    private Double rating;
    @SerializedName("price_numeric")
    @Expose
    private Integer priceNumeric;
    @SerializedName("version_code")
    @Expose
    private Integer versionCode;
    @Expose
    private String developer;
    @SerializedName("number_ratings")
    @Expose
    private Integer numberRatings;
    @Expose
    private String icon;
    @SerializedName("icon_72")
    @Expose
    private String icon72;
    @SerializedName("market_url")
    @Expose
    private String marketUrl;
    @SerializedName("deep_link")
    @Expose
    private String deepLink;
    @SerializedName("iap_max")
    @Expose
    private Double iapMax;
    @SerializedName("iap_min")
    @Expose
    private Double iapMin;

    /**
     *
     * @return
     * The promoVideo
     */
    public String getPromoVideo() {
        return promoVideo;
    }

    /**
     *
     * @param promoVideo
     * The promo_video
     */
    public void setPromoVideo(String promoVideo) {
        this.promoVideo = promoVideo;
    }

    /**
     *
     * @return
     * The iap
     */
    public Boolean getIap() {
        return iap;
    }

    /**
     *
     * @param iap
     * The iap
     */
    public void setIap(Boolean iap) {
        this.iap = iap;
    }

    /**
     *
     * @return
     * The lang
     */
    public String getLang() {
        return lang;
    }

    /**
     *
     * @param lang
     * The lang
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     *
     * @return
     * The downloads
     */
    public String getDownloads() {
        return downloads;
    }

    /**
     *
     * @param downloads
     * The downloads
     */
    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

    /**
     *
     * @return
     * The version
     */
    public String getVersion() {
        return version;
    }

    /**
     *
     * @param version
     * The version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The created
     */
    public String getCreated() {
        return created;
    }

    /**
     *
     * @param created
     * The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     *
     * @return
     * The screenshots
     */
    public List<String> getScreenshots() {
        return screenshots;
    }

    /**
     *
     * @param screenshots
     * The screenshots
     */
    public void setScreenshots(List<String> screenshots) {
        this.screenshots = screenshots;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * The contentRating
     */
    public String getContentRating() {
        return contentRating;
    }

    /**
     *
     * @param contentRating
     * The content_rating
     */
    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    /**
     *
     * @return
     * The i18nLang
     */
    public List<String> getI18nLang() {
        return i18nLang;
    }

    /**
     *
     * @param i18nLang
     * The i18n_lang
     */
    public void setI18nLang(List<String> i18nLang) {
        this.i18nLang = i18nLang;
    }

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
     * The website
     */
    public String getWebsite() {
        return website;
    }

    /**
     *
     * @param website
     * The website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     *
     * @return
     * The packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     *
     * @param packageName
     * The package_name
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     *
     * @return
     * The marketUpdate
     */
    public String getMarketUpdate() {
        return marketUpdate;
    }

    /**
     *
     * @param marketUpdate
     * The market_update
     */
    public void setMarketUpdate(String marketUpdate) {
        this.marketUpdate = marketUpdate;
    }

    /**
     *
     * @return
     * The size
     */
    public Integer getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     *
     * @return
     * The catInt
     */
    public Integer getCatInt() {
        return catInt;
    }

    /**
     *
     * @param catInt
     * The cat_int
     */
    public void setCatInt(Integer catInt) {
        this.catInt = catInt;
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
     * The price
     */
    public String getPrice() {
        return price;
    }

    /**
     *
     * @param price
     * The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The rating
     */
    public Double getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     * The rating
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     *
     * @return
     * The priceNumeric
     */
    public Integer getPriceNumeric() {
        return priceNumeric;
    }

    /**
     *
     * @param priceNumeric
     * The price_numeric
     */
    public void setPriceNumeric(Integer priceNumeric) {
        this.priceNumeric = priceNumeric;
    }

    /**
     *
     * @return
     * The versionCode
     */
    public Integer getVersionCode() {
        return versionCode;
    }

    /**
     *
     * @param versionCode
     * The version_code
     */
    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    /**
     *
     * @return
     * The developer
     */
    public String getDeveloper() {
        return developer;
    }

    /**
     *
     * @param developer
     * The developer
     */
    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    /**
     *
     * @return
     * The numberRatings
     */
    public Integer getNumberRatings() {
        return numberRatings;
    }

    /**
     *
     * @param numberRatings
     * The number_ratings
     */
    public void setNumberRatings(Integer numberRatings) {
        this.numberRatings = numberRatings;
    }

    /**
     *
     * @return
     * The icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     *
     * @param icon
     * The icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     *
     * @return
     * The icon72
     */
    public String getIcon72() {
        return icon72;
    }

    /**
     *
     * @param icon72
     * The icon_72
     */
    public void setIcon72(String icon72) {
        this.icon72 = icon72;
    }

    /**
     *
     * @return
     * The marketUrl
     */
    public String getMarketUrl() {
        return marketUrl;
    }

    /**
     *
     * @param marketUrl
     * The market_url
     */
    public void setMarketUrl(String marketUrl) {
        this.marketUrl = marketUrl;
    }

    /**
     *
     * @return
     * The deepLink
     */
    public String getDeepLink() {
        return deepLink;
    }

    /**
     *
     * @param deepLink
     * The deep_link
     */
    public void setDeepLink(String deepLink) {
        this.deepLink = deepLink;
    }

    /**
     *
     * @return
     * The iapMax
     */
    public Double getIapMax() {
        return iapMax;
    }

    /**
     *
     * @param iapMax
     * The iap_max
     */
    public void setIapMax(Double iapMax) {
        this.iapMax = iapMax;
    }

    /**
     *
     * @return
     * The iapMin
     */
    public Double getIapMin() {
        return iapMin;
    }

    /**
     *
     * @param iapMin
     * The iap_min
     */
    public void setIapMin(Double iapMin) {
        this.iapMin = iapMin;
    }

}
