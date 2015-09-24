package com.app.local.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by shivram on 13/09/15.
 */
@Table(name = "AggroCategory")
public class AggroCategory extends Model{
    @Column(name = "CategoryName")
    public String categoryName;
    @Column(name = "CategoryImage")
    public int categoryImage;


    public AggroCategory(){
        super();
    }
    public static AggroCategory getRandom() {
        return new Select().from(AggroCategory.class).orderBy("RANDOM()").executeSingle();
    }

    public static AggroCategory getSingleEntry(String categoryName){
        return new Select()
                .from(AggroCategory.class)
                .where("CategoryName = ?", categoryName)
                .orderBy("RANDOM()")
                .executeSingle();
    }

    public static List<AggroCategory> getAll() {
        return new Select().from(AggroCategory.class).orderBy("CategoryName ASC").execute();
    }
}
