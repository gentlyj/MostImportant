package com.ifading.mostimportant.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifading.mostimportant.R;
import com.ifading.mostimportant.adpter.DetailListRvAdpter;
import com.ifading.mostimportant.adpter.MindRvAdpter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 列表展示item 的fragment Created by Administrator on 2017/5/21.
 */

public class ListFragment extends Fragment implements DetailListRvAdpter.ItemOnClickListener {
    private static final String TAG = "ListFragment";
    //@BindView(R.id.rv_detali_list)
    protected RecyclerView mListRv;
    private DetailListRvAdpter mListRvAdpter;
    private ArrayList<String> mData = new ArrayList<>();

    public static Fragment newInstance(int index) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"lifecyckle,onCreateView");
        View view = inflater.inflate(R.layout.fragment_detail_list, container, false);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initView(View view) {
        mListRv = (RecyclerView) view.findViewById(R.id.rv_detali_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        mListRv.setLayoutManager(linearLayoutManager);
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            mData.add("条目:"+i);
        }
        mListRvAdpter = new DetailListRvAdpter(this.getActivity(), mData);
        mListRvAdpter.setItemOnClickListener(this);
        mListRv.setAdapter(mListRvAdpter);
    }


    @Override
    public void onItemClick(View itemView, int position) {

    }
}
