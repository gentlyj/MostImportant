package com.ifading.mostimport.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifading.mostimport.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/10.
 */

public class MindRvAdpter extends RecyclerView.Adapter {

    private  Context mContext;
    private  ArrayList<String> mDatas;

    public MindRvAdpter(Context context, ArrayList<String> data){
        this.mDatas = data;
        this.mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(View.inflate(mContext,R.layout.item_rv_main_mind,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MainViewHolder)holder).mTvName.setText(mDatas.get(position));
    }

    public void setData(ArrayList<String> data) {
        this.mDatas = data;
    }


    public class MainViewHolder extends RecyclerView.ViewHolder{
        public TextView mTvName;
        public MainViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.item_tv_main_mind_name);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
