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
//        AVAnalytics.enableCrashReport(ctx, true);
        AVObject.registerSubclass(Picture.class);
    }

    public static ArrayList<String> getContentType(){
        AVQuery<AVObject> query = new AVQuery<AVObject>("ContentType");
        final ArrayList<String> result = null;
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> avObjects, AVException e) {
                if(e == null){
                    for(AVObject object:avObjects){
                        result.add(object.getString("name"));
                    }
                }else{
                    Log.v("failed","error:"+e.getMessage());
                }
            }
        });

        return result;

    }

    public static ArrayList<String> getEmotionType(){
        final ArrayList<String> result = null;
        return result;

    }
}
