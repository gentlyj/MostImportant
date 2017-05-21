package com.ifading.mostimportant.activity;


import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;

import com.ifading.mostimportant.R;
import com.ifading.mostimportant.adpter.DetailViewPager;
import com.ifading.mostimportant.db.DataBase;
import com.ifading.mostimportant.fragment.ChartFragment;
import com.ifading.mostimportant.fragment.ListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MostImportantDetailActivity extends AppCompatActivity {

    public static final String ITEM_NUMBER = "item_number";
    public static final String ITEN_NAME = "iten_name";
    private FloatingActionButton mFab;
    @BindView(R.id.tablayout)
    protected TabLayout mTabLayout;
    private Toolbar mToolbar;
    @BindView(R.id.viewpage)
    protected ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_important_detail);
        ButterKnife.bind(this);
        initView();

        initData(getIntent().getStringExtra(ITEN_NAME));

        int position = getIntent().getIntExtra(ITEM_NUMBER, 0);


    }

    private void initData(String name) {
        mToolbar.setTitle(name);
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题
        DetailViewPager viewPagerAdapter = new DetailViewPager(getSupportFragmentManager());
        viewPagerAdapter.addFragment(ListFragment.newInstance(0), "列表");//添加Fragment
        viewPagerAdapter.addFragment(ChartFragment.newInstance(1), "图表");
        mViewPager.setAdapter(viewPagerAdapter);//设置适配器
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mFab = (FloatingActionButton) findViewById(R.id.fab);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //点击back键finish当前activity
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
