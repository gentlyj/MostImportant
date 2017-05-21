package com.ifading.mostimportant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifading.mostimportant.R;

/**
 * 列表展示item 的fragment Created by Administrator on 2017/5/21.
 */

public class ListFragment extends Fragment {
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
        return inflater.inflate(R.layout.fragment_detail_list,container, false);
    }
}
