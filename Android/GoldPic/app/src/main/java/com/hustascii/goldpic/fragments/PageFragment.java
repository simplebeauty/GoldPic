package com.hustascii.goldpic.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.FindCallback;
import com.hustascii.goldpic.MyApp;
import com.hustascii.goldpic.R;
import com.hustascii.goldpic.adapter.HomeAdapter;
import com.hustascii.goldpic.beans.Picture;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by wei on 15-5-7.
 */
public abstract class PageFragment extends Fragment{
    protected ListView mListView;
    protected HomeAdapter homeAdapter;
    protected ArrayList<Picture> mList;
    private ImageLoader mImageLoader;
    protected ProgressDialog progressDialog;
    public FindCallback<Picture> getDataCallback;
    public AVQuery<Picture> query;
    public AVObject type;


    public PageFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<Picture>();
        homeAdapter = new HomeAdapter(getActivity(),mList);
        getDataCallback = new FindCallback<Picture>() {
            @Override
            public void done(List<Picture> results, AVException e) {
                progressDialog.dismiss();

                mList.clear();
                if (e == null) {
                    mList.addAll(results);
                } else {
                    Log.v("error", e.getMessage());
                }

                if(0 == mList.size()){
                    Toast.makeText(getActivity(),"并没有啊",Toast.LENGTH_SHORT);
                }
                Log.v("size",String.valueOf(mList.size()));
                homeAdapter.notifyDataSetChanged();
            }
        };


    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_hot_page, null, false);
        mListView = (ListView) view.findViewById(R.id.piclist);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("获取图片中...");
        progressDialog.show();

        mImageLoader = ImageLoader.getInstance();
        mListView.setOnScrollListener(new PauseOnScrollListener(mImageLoader, false, false));

        mListView.setAdapter(homeAdapter);





        query = AVQuery.getQuery(Picture.class);
        getData();
        query.findInBackground(getDataCallback);


        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public abstract void getData();



    public static class TypeBroadcastReceiver extends BroadcastReceiver{
        private static final String TAG = "TypeBroadCast";

        @Override
        public void onReceive(Context context, Intent intent) {
            MyApp app = (MyApp)context.getApplicationContext();

            AVObject type = app.getType();
            int type_id = app.getType_id();
            AVQuery<Picture> query = AVQuery.getQuery(Picture.class);

            if(type_id == 0){
                query.whereEqualTo("ContentType",type);
            }else{
                query.whereEqualTo("EmotionType",type);
            }
//            getData();
//            query.findInBackground(getDataCallback);
        }
    }

}
