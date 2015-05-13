package com.hustascii.goldpic.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by wei on 15/5/13.
 */
@DatabaseTable(tableName="collect")
public class CollectList {
    @DatabaseField(generatedId=true,useGetSet=true,columnName="collect_id")
    private int id;

    @DatabaseField(columnName = "pic_id")
    private String pic_id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPic_id() {
        return pic_id;
    }

    public void setPic_id(String pic_id) {
        this.pic_id = pic_id;
    }


    public CollectList(){}

    public CollectList(String pic_id){
        this.setPic_id(pic_id);
    }
}
