package com.hustascii.goldpic.adapter;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.hustascii.goldpic.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by wei on 15/5/12.
 */
public class TypeAdapter extends BaseAdapter{

    private ArrayList<AVObject> mList;
    private Context mContext;
    private LayoutInflater mInflater;


    public TypeAdapter(Context context , ArrayList<AVObject> mList){
        this.mContext = context;
        this.mList = mList;
        this.mInflater = LayoutInflater.from(this.mContext);

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final TextView type_text;
        AVObject object = mList.get(position);

        if(view == null){
            view = mInflater.inflate(R.layout.type_item,null);
            type_text = (TextView)view.findViewById(R.id.type_text);
            view.setTag(type_text);
        }else{
            type_text = (TextView)view.getTag();
        }
        type_text.setText((String)object.get("name"));
        return view;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

}
