package com.hustascii.goldpic.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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
import com.hustascii.goldpic.beans.CollectList;
import com.hustascii.goldpic.beans.CollectListDB;
import com.hustascii.goldpic.beans.Picture;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;


public class CollectPageFragment extends PageFragment {



    @Override
    public void getData() {
        CollectListDB db = new CollectListDB(getActivity());
        List<String> list = db.queryall();
        query.whereContainedIn("objectId",list);
    }
}
