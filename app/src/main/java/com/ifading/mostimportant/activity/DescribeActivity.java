package com.ifading.mostimportant.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ifading.mostimportant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/8 0008.
 */

public class DescribeActivity extends AppCompatActivity{
    public static final String DESCRIBE_CONTENT = "describe_content";
    public static final int REQUEST_ID = 1;
    public static final String EDITED_INFO = "edited_info";
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

    @OnClick({R.id.describe_btn})
    public void onClick(View v){
        if (v == mBTn){
            String info = mEt.getText().toString();
            Intent intent = new Intent();
            intent.putExtra(EDITED_INFO,info);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

}
