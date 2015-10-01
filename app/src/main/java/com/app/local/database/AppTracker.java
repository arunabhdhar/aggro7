package com.app.local.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by ericbasendra on 16/08/15.
 */
@Table(name = "Installer")
public class AppTracker extends Model{
    @Column(name = "AppName")
    public String appName;
    @Column(name = "CatName")
    public String catName;
    @Column(name = "MarketUrl")
    public String marketUrl;
    @Column(name = "PackageName")
    public String packageName;
    @Column(name = "AppIconUrl")
    public String appIconUrl;
    @Column(name = "IsInstalled")
    public boolean isInstalled;
    @Column(name = "Rating")
    public float rating;
    @Column(name = "IsFavourite")
    public int isFavourite = 0;

    public AppTracker(){
        super();
    }
    public static AppTracker getRandom() {
        return new Select().from(AppTracker.class).orderBy("RANDOM()").executeSingle();
    }

    public static AppTracker getSingleEntry(String packageName){
       return new Select()
                .from(AppTracker.class)
                .where("PackageName = ?", packageName)
                .orderBy("RANDOM()")
                .executeSingle();
    }

    public static List<AppTracker> getAll() {
        return new Select().from(AppTracker.class).orderBy("PackageName DESC").execute();
    }

    public static List<AppTracker> getAllByLast(AppTracker event) {
        return new Select().from(AppTracker.class).orderBy("Id DESC").execute();
    }

    public static List<AppTracker> getAllById(String reportid) {
        return new Select()
                .from(AppTracker.class)
                .where("PackageName = ?", reportid)
                .orderBy("Name ASC")
                .execute();
    }

    public List<AppTracker> getAllByFav(int fav) {
        return new Select()
                .from(AppTracker.class)
                .where("IsFavourite = ?", fav)
                .execute();
    }

    public void deleteSingleEntry(String packageName){
        new Delete().from(AppTracker.class).where("PackageName = ?",packageName).execute();
    }
}
