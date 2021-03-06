package com.ifading.mostimportant.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ifading.mostimportant.R;
import com.ifading.mostimportant.adpter.MindRvAdpter;
import com.ifading.mostimportant.bean.MindItemBean;
import com.ifading.mostimportant.db.DbDao;
import com.ifading.mostimportant.db.DbUtils;
import com.ifading.mostimportant.helper.DragCallback;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MindRvAdpter.ItemOnClickListener {
    @BindView(R.id.main_toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.main_add_button)
    protected Button mAddButton;
    @BindView(R.id.main_rv)
    protected RecyclerView mMindRv;

    private ArrayList<MindItemBean> mData = new ArrayList<>();
    private MindRvAdpter mMindRvAdpter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolbar);
        DbUtils.init(this.getApplicationContext());
        initView();
        initData();
    }

    private void initData() {
        /*for (int i = 0; i < 10; i++) {
            mData.add("Test " + i);
        }*/
        mData = DbUtils.getAllData();
        mMindRvAdpter = new MindRvAdpter(this.getApplicationContext(), mData);
        mMindRvAdpter.setItemOnClickListener(this);
        mMindRv.setAdapter(mMindRvAdpter);
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMindRv.setLayoutManager(linearLayoutManager);
        DragCallback dragCallback = new DragCallback();
        dragCallback.setDragCallbackListener(new DragCallback.DragCallbackListener() {
            @Override
            public void onMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
                int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position

                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mData, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mData, i, i - 1);
                    }
                }
                mMindRvAdpter.notifyItemMoved(fromPosition, toPosition);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(dragCallback);
        itemTouchHelper.attachToRecyclerView(mMindRv);
    }

    @OnClick({R.id.main_add_button})
    protected void onClick(View v) {
        if (v == mAddButton) {
            showNewItemDialog();

        }
    }

    /**
     * 新建思考的item的dialog
     */
    private void showNewItemDialog() {
        final EditText et = new EditText(this);

        new AlertDialog.Builder(this,AlertDialog.THEME_HOLO_LIGHT)
                .setTitle("新思考的名称")
                .setView(et)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "名称不能为空！" + input, Toast.LENGTH_LONG).show();
                        }
                        else {
                            inserNewItem(input);
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 向新数据库插入新数据
     * @param name
     */
    private void inserNewItem(String name) {
        DbUtils.insertItem(name);
        upgradeRv();
    }

    private void upgradeRv() {
        mData = DbUtils.getAllData();
        mMindRvAdpter.setData(mData);
        mMindRvAdpter.notifyDataSetChanged();
    }

    private void showToast(String content) {
        Toast.makeText(this.getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View itemView, int position) {
        if (position<0){
            return;
        }
        Intent intent = new Intent(MainActivity.this,MostImportantDetailActivity.class);
        intent.putExtra(MostImportantDetailActivity.ITEM_NUMBER,mData.get(position).getId());
        intent.putExtra(MostImportantDetailActivity.ITEN_NAME,mData.get(position).getName());
        startActivity(intent);
    }
}
