package com.hustascii.goldpic.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.hustascii.goldpic.R;
import com.hustascii.goldpic.adapter.HomeAdapter;
import com.hustascii.goldpic.beans.Picture;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wei on 15-5-7.
 */
public abstract class PageFragment extends Fragment{
    private ListView mListView;
    private HomeAdapter homeAdapter;
    private ArrayList<Picture> mList;
    private ImageLoader mImageLoader;
    private ProgressDialog progressDialog;
    public FindCallback<Picture> getDataCallback;
    public AVQuery<Picture> query;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<Picture>();
        getDataCallback = new FindCallback<Picture>() {
            @Override
            public void done(List<Picture> pictures, AVException e) {
                progressDialog.dismiss();
                if (e == null) {
                    mList.addAll(pictures);
                } else {
                    Log.v("error", e.getMessage());
                }
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

        homeAdapter = new HomeAdapter(getActivity(), mList);

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

    public void getData(){}
}
