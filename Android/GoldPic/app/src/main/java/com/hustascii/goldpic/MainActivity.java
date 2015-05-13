package com.hustascii.goldpic;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.AvoidXfermode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.hustascii.goldpic.adapter.TypeAdapter;
import com.hustascii.goldpic.fragments.CollectPageFragment;
import com.hustascii.goldpic.fragments.HotPageFragment;
import com.hustascii.goldpic.fragments.NewPageFragment;
import com.hustascii.goldpic.fragments.PageFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ViewPager pager;
    private Button contentTypeBtn;
    private Button emotionTypeBtn;
    private ListView typeList;
    private TypeAdapter typeAdapter;
    private ProgressDialog progressDialog;
    private FragmentPagerItemAdapter adapter;

    private ArrayList<AVObject> mList;
    private ArrayList<AVObject> contentType;
    private ArrayList<AVObject> emotionType;
    private MyApp app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog
                = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("初始化中...");
        progressDialog.show();

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("神配圖");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		/* findView */
        mList = new ArrayList<AVObject>();
        contentType = new ArrayList<AVObject>();
        emotionType = new ArrayList<AVObject>();

        app = (MyApp)getApplication();

        typeAdapter = new TypeAdapter(this,mList);
        initDrawer();

        initPages();



    }

    private void initPages(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_open);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.hot_page, HotPageFragment.class,new Bundle())
                .add(R.string.new_page, NewPageFragment.class)
                .add(R.string.collet_page, CollectPageFragment.class)
                .create());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }

    private void initDrawer(){
        contentTypeBtn = (Button)findViewById(R.id.content_type_btn);
        emotionTypeBtn = (Button)findViewById(R.id.emotion_type_btn);
        contentTypeBtn.setOnClickListener(this);
        emotionTypeBtn.setOnClickListener(this);
        typeList = (ListView)findViewById(R.id.list_type);
        typeList.setAdapter(typeAdapter);

        typeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               app.setType(mList.get((int)id));
               app.setType_id(0);
//              send();

            }
        });

        getContentType();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getContentType(){
        AVQuery<AVObject> query = new AVQuery<AVObject>("ContentType");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> avObjects, AVException e) {
                app.setType_id(0);
                progressDialog.dismiss();
                mList.clear();
                contentType.clear();
                if(e == null){
                    contentType.addAll(avObjects);
                    mList.addAll(contentType);
                }else{
                    Log.v("failed", "error:" + e.getMessage());
                }
                typeAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getEmotionType(){
        AVQuery<AVObject> query = new AVQuery<AVObject>("EmotionType");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> avObjects, AVException e) {
                app.setType_id(1);
                progressDialog.dismiss();
                mList.clear();
                emotionType.clear();
                if(e == null){
                    emotionType.addAll(avObjects);
                    mList.addAll(emotionType);
                }else{
                    Log.v("failed", "error:" + e.getMessage());
                }
                typeAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.content_type_btn:
                app.setType_id(0);
                if(contentType.isEmpty()){
                   getContentType();
                }else{
                    mList.clear();
                    mList.addAll(contentType);

                    typeAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.emotion_type_btn:
                app.setType_id(1);
                if(emotionType.isEmpty()){
                    getEmotionType();
                }else{
                    mList.clear();
                    mList.addAll(emotionType);
                    typeAdapter.notifyDataSetChanged();
                }
                break;
        }
    }


    public void send() {
        Intent intent = new Intent("android.intent.action.ACTION_TYPE");
//        intent.putExtra("msg", "hello receiver.");
        sendBroadcast(intent);
    }
}
