package com.hustascii.goldpic.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by wei on 15/5/13.
 */
@DatabaseTable(tableName="like")
public class LikeList {

    @DatabaseField(generatedId=true,useGetSet=true,columnName="like_id")
    private int like_id;

    @DatabaseField(columnName = "pic_id")
    private String pic_id;

    public int getLike_id() {
        return like_id;
    }

    public void setLike_id(int like_id) {
        this.like_id = like_id;
    }

    public String getPic_id() {
        return pic_id;
    }

    public void setPic_id(String pic_id) {
        this.pic_id = pic_id;
    }
}
