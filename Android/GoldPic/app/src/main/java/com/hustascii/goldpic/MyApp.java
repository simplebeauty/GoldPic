package com.hustascii.goldpic;

import android.app.Application;

import com.avos.avoscloud.AVObject;
import com.hustascii.goldpic.util.AVService;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by wei on 15-4-28.
 */
public class MyApp extends Application{




    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)//设置线程的优先级
                .denyCacheImageMultipleSizesInMemory()//当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//设置缓存文件的名字
                .discCacheFileCount(60)//缓存文件的最大个数
                .tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
//              .enableLogging() //是否打印日志用于检查错误
                .build();
        //Initialize ImageLoader with configuration
        ImageLoader.getInstance().init(config);


        AVService.AVinit(this);

    }
}
