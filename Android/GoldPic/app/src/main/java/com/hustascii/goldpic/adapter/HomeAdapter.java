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
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.SaveCallback;
import com.hustascii.goldpic.R;
import com.hustascii.goldpic.beans.CollectList;
import com.hustascii.goldpic.beans.CollectListDB;
import com.hustascii.goldpic.beans.Picture;
import com.hustascii.goldpic.util.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

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

        final AVObject picture = mList.get(i);

        final CollectListDB db = new CollectListDB(mContext);
        if(view == null){
            view = mInflater.inflate(R.layout.pic_item,null);
            viewHolder = new ViewHolder();
            viewHolder.mImg = (ImageView)view.findViewById(R.id.list_img);
            viewHolder.likeImg = (ImageView)view.findViewById(R.id.like_img);
            viewHolder.collectImg = (ImageView)view.findViewById(R.id.collect_img);
            viewHolder.likeBtn = (RelativeLayout)view.findViewById(R.id.like_btn);
            viewHolder.collectBtn = (RelativeLayout)view.findViewById(R.id.collect_btn);
            viewHolder.shareBtn = (RelativeLayout)view.findViewById(R.id.share_btn);
            viewHolder.likeCountText = (TextView)view.findViewById(R.id.like_num);
            viewHolder.likeCountText.setText(String.valueOf(picture.getInt("likeCount")));

            Log.v("ObjectId:",picture.getObjectId());
            if(db.is_exist(picture.getObjectId())){
                viewHolder.collectImg.setImageResource(R.drawable.icon_favorite_selected);
            }


            viewHolder.likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    viewHolder.likeImg.setImageResource(R.drawable.icon_like_selected);
                    picture.setFetchWhenSave(true);
                    picture.increment("likeCount");
                    picture.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if(e == null){
                                viewHolder.likeCountText.setText(String.valueOf(picture.getInt("likeCount")));
                            }else{
                                Toast.makeText(mContext,"点赞失败",Toast.LENGTH_LONG);
                            }
                        }
                    });
                }
            });

            viewHolder.collectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(db.is_exist(picture.getObjectId())) {
                        viewHolder.collectImg.setImageResource(R.drawable.icon_favorite_normal);
                        db.delete(picture.getObjectId());
                        Toast.makeText(mContext,"取消成功",Toast.LENGTH_SHORT).show();
                    }else{
                        viewHolder.collectImg.setImageResource(R.drawable.icon_favorite_selected);
                        CollectList pic = new CollectList();
                        pic.setPic_id(picture.getObjectId());
                        db.add(pic);
                        Toast.makeText(mContext,"收藏成功",Toast.LENGTH_SHORT).show();

                    }
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

        AVFile pic = picture.getAVFile("picFile");
        String imgUrl = pic.getUrl();
        if(!mBusy){
            if(viewHolder.mImg!=null) {

                mImageLoader
                        .displayImage(imgUrl, viewHolder.mImg, options, new AnimateFirstDisplayListener());
            }
        }else{
            mImageLoader.displayImage(imgUrl,viewHolder.mImg,options);
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
