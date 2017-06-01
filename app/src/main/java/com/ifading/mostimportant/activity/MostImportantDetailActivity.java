package com.ifading.mostimportant.activity;


import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ifading.mostimportant.R;
import com.ifading.mostimportant.adpter.DetailViewPager;
import com.ifading.mostimportant.fragment.ChartFragment;
import com.ifading.mostimportant.fragment.ListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MostImportantDetailActivity extends AppCompatActivity {

    public static final String ITEM_NUMBER = "item_number";
    public static final String ITEN_NAME = "iten_name";
    private FloatingActionButton mFab;
    @BindView(R.id.tablayout)
    protected TabLayout mTabLayout;
    private Toolbar mToolbar;
    @BindView(R.id.viewpage)
    protected ViewPager mViewPager;
    private ListFragment mListFragment;
    //当前list是否在编辑状态
    private boolean mListInAddState;
    @BindView(R.id.nestedScrollView)
    protected NestedScrollView mNsv;

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
        mListFragment = ListFragment.newInstance(0);
        viewPagerAdapter.addFragment(mListFragment, "列表");//添加Fragment
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

    @OnClick({R.id.fab})
    public void onClick(View v) {
        if (v == mFab) {
            mListFragment.setAddState(!mListInAddState);
            enterEditState(!mListInAddState);
            mListInAddState = !mListInAddState;
        }
    }

    /**
     * 进入编辑状态
     *
     * @param edit 是否编辑
     */
    private void enterEditState(boolean edit) {
        if (edit) {
            mFab.setImageResource(R.drawable.ic_done_white);
            //mFab.setBackgroundColor(getResources().getColor(R.color.add_item_fab_bg));
            mFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor( R.color.add_item_fab_bg)));
            //// TODO: 2017/5/28 显示增加的按钮,并且改变rv的高度 ,留出button的高度,要不就添加一个透明的item到地步,item要改为可以编辑的状态,可以改比重,改颜色\
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mNsv.getLayoutParams();
            layoutParams.setMargins(0,0,0,200);
            mNsv.setLayoutParams(layoutParams);
            //mFab.setColorFilter(getResources().getColor(R.color.add_item_fab_bg));
        } else {
            mFab.setImageResource(R.drawable.ic_create_white);
            mFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor( R.color.colorAccent)));
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mNsv.getLayoutParams();
            layoutParams.setMargins(0,0,0,0);
            mNsv.setLayoutParams(layoutParams);
        }
    }

    private void showToast(String content) {
        Toast.makeText(this.getApplicationContext(), content, Toast.LENGTH_SHORT).show();
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
