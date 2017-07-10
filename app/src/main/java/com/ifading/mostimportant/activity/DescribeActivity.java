package com.ifading.mostimportant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;

import com.ifading.mostimportant.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/8 0008.
 */

public class DescribeActivity extends AppCompatActivity{
    public static final String DESCRIBE_CONTENT = "describe_content";
    public static final int REQUEST_ID = 1;
    @BindView(R.id.describe_et)
    protected EditText mEt;
    @BindView(R.id.describe_btn)
    protected Button mBTn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe);
        ButterKnife.bind(this);
        String content = getIntent().getStringExtra(DESCRIBE_CONTENT);
        initData(content);
    }

    private void initData(String content) {
        mEt.setText(content);
    }
}
