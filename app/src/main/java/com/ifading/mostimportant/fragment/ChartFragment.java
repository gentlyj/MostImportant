package com.ifading.mostimportant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifading.mostimportant.R;

/**
 * 图表展示item 的fragment Created by Administrator on 2017/5/21.
 */

public class ChartFragment extends Fragment {
    public static Fragment newInstance(int index) {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_detail_chart,container, false);
        return inflate;
    }
}
