package com.app.local.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * Created by ericbasendra on 12/10/15.
 */
@Table(name = "UserDetail")
public class UserInfo extends Model {
    @Column(name = "Name")
    public String name;
    @Column(name = "UserName")
    public String userName;
    @Column(name = "Age")
    public String age;
    @Column(name = "Gender")
    public String geneder;
    @Column(name = "Email")
    public String email;
    @Column(name = "Location")
    public String location;
    @Column(name = "IsNotificationEnabledd")
    public boolean isNotificationEnabled;
    @Column(name = "WifiEnabledDownload")
    public boolean wifiEnabledDownload;
    @Column(name = "DownloadWhileCharging")
    public boolean downloadWhileCharging;

    public UserInfo(){
        super();
    }
    public static UserInfo getRandom() {
        return new Select().from(UserInfo.class).orderBy("RANDOM()").executeSingle();
    }

    public static UserInfo getSingleEntry(String name){
        return new Select()
                .from(UserInfo.class)
                .where("Name = ?", name)
                .orderBy("RANDOM()")
                .executeSingle();
    }
}
