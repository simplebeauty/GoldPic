package com.hustascii.goldpic.beans;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

import java.io.Serializable;

/**
 * Created by wei on 15-4-28.
 */
@AVClassName(Picture.PICTURE_CLASS)
public class Picture extends AVObject {

    static final String PICTURE_CLASS = "Picture";


    private static final String PIC_KEY = "picFile";

    private static final String LIKE_KEY = "likeCount";

    private static final String COLLECT_KEY = "";


    private static final String EMOTIONTYPE_KEY = "EmotionType";

    private static final String CONTENTTYPE_KEY = "ContentType";

    public AVFile getPic() {
        return this.getAVFile(PIC_KEY);
    }

    public void setPic_url(AVFile file) {
        this.put(PIC_KEY,file);
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
