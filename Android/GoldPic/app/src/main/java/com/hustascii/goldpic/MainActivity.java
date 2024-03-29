package com.hustascii.goldpic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Button contentTypeBtn;
    private Button emotionTypeBtn;
    private ListView typeList;
    private TypeAdapter typeAdapter;
    private ProgressDialog progressDialog;
    private FragmentPagerItemAdapter adapter;
    private SmartTabLayout viewPagerTab;
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

        viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        changePage(0,"电影院");

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

                mDrawerLayout.closeDrawers();
//                PageFragment fragment = (HotPageFragment)fra

            }
        });

        getContentType();
    }


    private void changePage(int type_id,String type){
        Bundler bundler = new Bundler().putString("type", type).putInt("type_id",type_id);
        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.hot_page, HotPageFragment.class, bundler.get())
                .add(R.string.new_page, NewPageFragment.class,bundler.get())
                .add(R.string.collet_page, CollectPageFragment.class,bundler.get())
                .create());
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
//            Intent i = new Intent(MainActivity.this,SettingsActivity.class);
//            startActivity(i);
            ImageLoader loader = ImageLoader.getInstance();
            loader.clearMemoryCache();
            loader.clearDiskCache();
            Toast.makeText(this,"缓存清理成功",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getContentType(){
        AVQuery<AVObject> query = new AVQuery<AVObject>("ContentType");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> avObjects, AVException e) {
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
                if(contentType.isEmpty()){
                   getContentType();
                }else{
                    mList.clear();
                    mList.addAll(contentType);
                    typeAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.emotion_type_btn:
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

}
