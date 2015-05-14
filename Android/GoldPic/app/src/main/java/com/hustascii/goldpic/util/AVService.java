package com.hustascii.goldpic.util;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVAnonymousUtils;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.hustascii.goldpic.beans.Picture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wei on 15-5-3.
 */
public class AVService {

    public static void AVinit(Context ctx){
        AVOSCloud.initialize(ctx,"b0v6ysdzmynqnbxxuytbdydjqp9uvxq2yfvug2h43tc612h0","utq5f4knl9oac8xrudd632a9fpv7644j2rjf77uf27iyjy8c");
        AVOSCloud.setDebugLogEnabled(true);
        AVObject.registerSubclass(Picture.class);
    }

}
