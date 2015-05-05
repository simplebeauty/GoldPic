package com.hustascii.goldpic.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hustascii.goldpic.R;
import com.hustascii.goldpic.beans.Picture;
import com.hustascii.goldpic.util.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

/**
 * Created by wei on 15-4-28.
 */
public class HomeAdapter extends BaseAdapter{


    private LayoutInflater mInflater;
    private ArrayList<Picture> mList;
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;
    private Context mContext;


    private boolean mBusy = false;
    public void setFlagBusy(boolean busy){
        this.mBusy = busy;
    }


    public HomeAdapter(Context context,ArrayList<Picture> list){
        this.mInflater = LayoutInflater.from(context);
        this.mList = list;
        this.mContext = context;
        this.mImageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.example_1) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.example_1)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.example_1)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                        //.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
                        //设置图片加入缓存前，对bitmap进行设置
                        //.preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ViewHolder viewHolder;
        if(view == null){
            view = mInflater.inflate(R.layout.pic_item,null);
            viewHolder = new ViewHolder();
            viewHolder.mImg = (ImageView)view.findViewById(R.id.list_img);
            viewHolder.likeImg = (ImageView)view.findViewById(R.id.like_img);
            viewHolder.collectImg = (ImageView)view.findViewById(R.id.collect_img);

            viewHolder.likeBtn = (RelativeLayout)view.findViewById(R.id.like_btn);
            viewHolder.collectBtn = (RelativeLayout)view.findViewById(R.id.collect_btn);
            viewHolder.shareBtn = (RelativeLayout)view.findViewById(R.id.share_btn);



            viewHolder.likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewHolder.likeImg.setImageResource(R.drawable.icon_like_selected);
                }
            });

            viewHolder.collectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewHolder.collectImg.setImageResource(R.drawable.icon_favorite_selected);
                }
            });

            viewHolder.shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();

        }

        final Picture picture = mList.get(i);
        if(!mBusy){
            if(viewHolder.mImg!=null) {

                mImageLoader
                        .displayImage("http://edu.china.unity3d.com/uploads/assets/image/20141114/20141114173828_74628.jpg", viewHolder.mImg, options, new AnimateFirstDisplayListener());
            }
        }else{
            mImageLoader.displayImage("http://yuedu.fm/static/file/large/4c2e43a1db5fd89a2563eba7249ebc54",viewHolder.mImg,options);
        }
        return view;
    }

    public final class ViewHolder{
        public ImageView mImg;
        public RelativeLayout likeBtn;
        public TextView likeCountText;
        public RelativeLayout shareBtn;
        public RelativeLayout collectBtn;
        public ImageView likeImg;
        public ImageView collectImg;
    }

}
