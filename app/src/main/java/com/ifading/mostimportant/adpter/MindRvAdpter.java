package com.ifading.mostimportant.adpter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifading.mostimportant.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/10.
 */

public class MindRvAdpter extends RecyclerView.Adapter {

    private static final String TAG = "MindRvAdpter";
    private Context mContext;
    private ArrayList<String> mDatas;
    private ItemOnClickListener mItemOnClickListener;

    public MindRvAdpter(Context context, ArrayList<String> data) {
        this.mDatas = data;
        this.mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(View.inflate(mContext, R.layout.item_rv_main_mind, null));
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, final int position) {
        ((MainViewHolder) holder).mTvName.setText(mDatas.get(position));
        //判断是否设置了监听器
        /*if(mItemOnClickListener != null){
            //为ItemView设置监听器
            ((MainViewHolder)holder).mCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "1111HOLDER点击了,position:" + position);
                    if (mItemOnClickListener != null) {
                        mItemOnClickListener.onItemClick(null, position);
                    }
                }
            });
        }*/
    }

    public void setData(ArrayList<String> data) {
        this.mDatas = data;
    }


    public class MainViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvName;
        private CardView mCard;
        public MainViewHolder( View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.item_tv_main_mind_name);
            mCard = (CardView) itemView.findViewById(R.id.item_card_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "HOLDER点击了,position:" + getLayoutPosition());
                    if (mItemOnClickListener != null) {
                        mItemOnClickListener.onItemClick(null, getLayoutPosition());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public interface ItemOnClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setItemOnClickListener(ItemOnClickListener listener) {
        this.mItemOnClickListener = listener;
    }
}
