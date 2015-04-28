package com.hustascii.goldpic.beans;

import java.io.Serializable;

/**
 * Created by wei on 15-4-28.
 */
public class Picture implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;


    private String pic_url;

    private int likeCount;

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
