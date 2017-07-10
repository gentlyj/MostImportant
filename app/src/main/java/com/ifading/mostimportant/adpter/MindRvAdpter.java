package com.ifading.mostimportant.adpter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifading.mostimportant.R;
import com.ifading.mostimportant.bean.MindItemBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/10.
 */

public class MindRvAdpter extends RecyclerView.Adapter {

    private static final String TAG = "MindRvAdpter";
    private Context mContext;
    private ArrayList<MindItemBean> mDatas;
    private ItemOnClickListener mItemOnClickListener;

    public MindRvAdpter(Context context, ArrayList<MindItemBean> data) {
        this.mDatas = data;
        this.mContext = context;
        Log.d(TAG, "新建MindRvAdpter了" );
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "设置内容了,onCreateViewHolder:");
        return new MainViewHolder(View.inflate(mContext, R.layout.item_rv_main_mind, null));
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, final int position) {
        Log.d(TAG, "设置内容了,position:" + position);
        ((MainViewHolder) holder).mTvName.setText(mDatas.get(position).getName());
        ((MainViewHolder) holder).mCard.setCardBackgroundColor(Color.BLACK);
    }

    public void setData(ArrayList<MindItemBean> data) {
        this.mDatas = data;
    }


    public class MainViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvName;
        private CardView mCard;
        public MainViewHolder( View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.item_tv_main_mind_name);
            mCard = (CardView) itemView.findViewById(R.id.item_card_view);
            //mCard.setCardBackgroundColor();
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
