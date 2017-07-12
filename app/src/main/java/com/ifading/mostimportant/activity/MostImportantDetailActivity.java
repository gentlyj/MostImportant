package com.ifading.mostimportant.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ifading.mostimportant.R;
import com.ifading.mostimportant.adpter.DetailViewPager;
import com.ifading.mostimportant.callback.AppBarStateChangeListener;
import com.ifading.mostimportant.db.DbUtils;
import com.ifading.mostimportant.fragment.ChartFragment;
import com.ifading.mostimportant.fragment.ListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MostImportantDetailActivity extends AppCompatActivity {

    public static final String ITEM_NUMBER = "item_number";
    public static final String ITEN_NAME = "iten_name";
    private static final String TAG = "DetailActivity";
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
    private int mCurrentItemDbPosition;
    private String mCurrentItemName;
    @BindView(R.id.detail_tv_describe)
    protected TextView mTvDescrivbe;
    //@BindView(R.id.detail_btn_test)
    protected Button nBtnTest;
    @BindView(R.id.detail_test_gesture_capture)
    protected View mGetEditInfoTouchEventView;
    @BindView(R.id.app_bar)
    protected AppBarLayout mAppBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_important_detail);
        ButterKnife.bind(this);
        mCurrentItemDbPosition = getIntent().getIntExtra(ITEM_NUMBER, 0);
        mCurrentItemName = getIntent().getStringExtra(ITEN_NAME);
        String describe = DbUtils.getItemDescribe(mCurrentItemDbPosition);
        initView();
        initData(getIntent().getStringExtra(ITEN_NAME),describe);
        initEvent();
    }

    private void initEvent() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                //因为占位view在折叠时会遮挡住toolbar,导致不能返回,所以在折叠时,隐藏占位view,也就是说只有展开时,才能点击修改
                if( state == State.EXPANDED ) {
                    //展开状态
                    mGetEditInfoTouchEventView.setVisibility(View.VISIBLE);
                }else if(state == State.COLLAPSED){
                    //折叠状态
                    mGetEditInfoTouchEventView.setVisibility(View.GONE);
                }else {
                    //中间状态
                    mGetEditInfoTouchEventView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initData(String name, String describe) {
        Log.d("ListFragment", "获取的name:" + name);
        getSupportActionBar().setTitle(name);
        //mToolbar.setTitle(name);
        //mToolbar.setSubtitle(name);
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题
        DetailViewPager viewPagerAdapter = new DetailViewPager(getSupportFragmentManager());
        mListFragment = ListFragment.newInstance(0);
        mListFragment.setCurrentItemDbPositon(mCurrentItemDbPosition);
        viewPagerAdapter.addFragment(mListFragment, "列表");//添加Fragment
        viewPagerAdapter.addFragment(ChartFragment.newInstance(1), "图表");
        mViewPager.setAdapter(viewPagerAdapter);//设置适配器
        mTvDescrivbe.setText(describe);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(mToolbar);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mFab = (FloatingActionButton) findViewById(R.id.fab);

    }

    @OnClick({R.id.fab, R.id.detail_ll_content_container,R.id.detail_test_gesture_capture})//,R.id.detail_ll_content_container，R.id.detail_btn_test,R.id.detail_tv_describe,
    public void onClick(View v) {
        if (v == mFab) {
            mListFragment.setAddState(!mListInAddState);
            enterEditState(!mListInAddState);
            mListInAddState = !mListInAddState;
        } else if (v == mTvDescrivbe) {
            //Log.d(TAG,"mTvDescrivbe-1NCLICK!");
        }else if (v == mGetEditInfoTouchEventView){
            Log.d(TAG,"mGetEditInfoTouchEventView-1NCLICK!");
            if (mListInAddState) {
                startDescribeActivity(mTvDescrivbe.getText().toString());
            } else {
                // TODO: 2017/7/8 0008 可以弹个popwindow?
            }
        }
    }

    private void startDescribeActivity(String content) {
        Intent intent = new Intent(MostImportantDetailActivity.this, DescribeActivity.class);
        intent.putExtra(DescribeActivity.DESCRIBE_CONTENT, content);
        startActivityForResult(intent, DescribeActivity.REQUEST_ID);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case DescribeActivity.REQUEST_ID:
                if (resultCode == Activity.RESULT_OK){
                    String describetion = data.getStringExtra(DescribeActivity.EDITED_INFO);
                    mTvDescrivbe.setText(describetion);
                    DbUtils.updateItemDescribe(mCurrentItemDbPosition,describetion);
                }
                break;

            default:
                break;
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
            mFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.add_item_fab_bg)));
            //mFab.setColorFilter(getResources().getColor(R.color.add_item_fab_bg));
            //mTvDescrivbe.setBackgroundColor(Color.parseColor("#aaffffff"));
            //mTvDescrivbe.setClickable(false);
        } else {
            mFab.setImageResource(R.drawable.ic_create_white);
            mFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
            //mTvDescrivbe.setBackgroundColor(Color.TRANSPARENT);
            //mTvDescrivbe.setClickable(false);
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
