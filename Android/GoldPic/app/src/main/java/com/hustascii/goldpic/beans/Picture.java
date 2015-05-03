package com.hustascii.goldpic.beans;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.io.Serializable;

/**
 * Created by wei on 15-4-28.
 */
@AVClassName(Picture.PICTURE_CLASS)
public class Picture extends AVObject {

    static final String PICTURE_CLASS = "Picture";

    private static final String PIC_KEY = "picObj";

    private static final String LIKE_KEY = "likeCount";

    private static final String COLLECT_KEY = "";


    public String getPic_url() {
        return this.getString(PIC_KEY);
    }

    public void setPic_url(String pic_url) {
        this.put(PIC_KEY,pic_url);
    }

    public int getLikeCount() {
        return this.getInt(LIKE_KEY);
    }

    public void setLikeCount(int likeCount) {
        this.put(LIKE_KEY,likeCount);
    }

    public Picture() {
    }
}
