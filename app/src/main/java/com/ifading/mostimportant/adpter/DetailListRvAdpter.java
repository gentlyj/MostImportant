package com.ifading.mostimportant.adpter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifading.mostimportant.R;
import com.ifading.mostimportant.bean.ListDetailItemBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/10.
 */

public class DetailListRvAdpter extends RecyclerView.Adapter {

    private static final String TAG = "DetailListRvAdpter";
    private static final int ADD_ITEM_TYPE = 1;
    private Context mContext;
    private ArrayList<ListDetailItemBean> mDatas;
    private ItemOnClickListener mItemOnClickListener;

    public DetailListRvAdpter(Context context, ArrayList<ListDetailItemBean> data) {
        this.mDatas = data;
        this.mContext = context;
        Log.d(TAG, "新建DetailListRvAdpter,数据大小:"+data.size() );
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ADD_ITEM_TYPE){
            return new ItemAddViewHolder(View.inflate(mContext, R.layout.item_rv_detail_list_add, null));
        }else {
            return new NormalViewHolder(View.inflate(mContext, R.layout.item_rv_detail_list_normal, null));

        }
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, final int position) {
        Log.d(TAG, "设置内容了,position:" + position);
        if (holder instanceof NormalViewHolder) {
            ((NormalViewHolder) holder).mTvName.setText(mDatas.get(position).getTitle());
        }
    }

    public void setData(ArrayList<ListDetailItemBean> data) {
        this.mDatas = data;
    }


    public class NormalViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvName;
        private CardView mCard;
        public NormalViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.item_tv_detail_list_title);
            mCard = (CardView) itemView.findViewById(R.id.item_card_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "HOLDER点击了,position:" + getLayoutPosition());
                    if (mItemOnClickListener != null) {
                        mItemOnClickListener.onNormalItemClick(null, getLayoutPosition());
                    }
                }
            });
        }
    }

    public class ItemAddViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvName;
        private CardView mCard;
        public ItemAddViewHolder( View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.item_tv_main_mind_name);
            mCard = (CardView) itemView.findViewById(R.id.item_card_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "HOLDER点击了,position:" + getLayoutPosition());
                    if (mItemOnClickListener != null) {
                        mItemOnClickListener.onAddItemClick();
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = mDatas.get(position).isAddItemType()?1:0;
        Log.d(TAG, "ItemType:" + type);
        return type;
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount,size:" + mDatas.size());
        return mDatas.size();
    }

    public interface ItemOnClickListener {
        void onNormalItemClick(View itemView, int position);

        void onAddItemClick();
    }

    public void setItemOnClickListener(ItemOnClickListener listener) {
        this.mItemOnClickListener = listener;
    }
}
