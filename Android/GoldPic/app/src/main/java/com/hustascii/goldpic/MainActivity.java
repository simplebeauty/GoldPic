package com.hustascii.goldpic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hustascii.goldpic.fragments.CollectPageFragment;
import com.hustascii.goldpic.fragments.HotPageFragment;
import com.hustascii.goldpic.fragments.NewPageFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {


    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ViewPager pager;

    private Button contentTypeBtn;
    private Button emotionTypeBtn;

    private ListView typeList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);

        mToolbar.setTitle("神配圖");

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		/* findView */

        initPages();

        initDrawer();

    }


    private void initPages(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_open);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.hot_page, HotPageFragment.class)
                .add(R.string.new_page, HotPageFragment.class)
                .add(R.string.collet_page, HotPageFragment.class)
                .create());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }


    private void initDrawer(){
        contentTypeBtn = (Button)findViewById(R.id.content_type_btn);
        emotionTypeBtn = (Button)findViewById(R.id.emotion_type_btn);
        typeList = (ListView)findViewById(R.id.list_type);
        String[] contentType = {"全部","電影","電視劇","電影","電視劇","電影","電視劇","電影","電視劇","電影","電視劇"};
        String[] emotionType = {"全部","高興","悲傷","高興","悲傷","高興","悲傷","高興","悲傷","高興","悲傷"};

        typeList.setAdapter(new ArrayAdapter<String>(this,R.layout.type_item,R.id.type_text,contentType));


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



}
