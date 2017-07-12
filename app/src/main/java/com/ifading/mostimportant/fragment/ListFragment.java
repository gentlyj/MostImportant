package com.ifading.mostimportant.fragment;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ifading.mostimportant.R;
import com.ifading.mostimportant.adpter.DetailListRvAdpter;
import com.ifading.mostimportant.bean.ListDetailItemBean;
import com.ifading.mostimportant.db.DbUtils;
import com.ifading.mostimportant.utils.JsonUtils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 列表展示item 的fragment Created by Administrator on 2017/5/21.
 */

public class ListFragment extends Fragment implements DetailListRvAdpter.ItemOnClickListener, View.OnClickListener {
    private static final String TAG = "ListFragment";
    //@BindView(R.id.rv_detali_list)
    protected RecyclerView mListRv;
    private DetailListRvAdpter mListRvAdpter;
    private ArrayList<ListDetailItemBean> mData = new ArrayList<>();
    private int mCurrentItemDbPosition;
    //新建条目时,seekbar百分比是否调整过
    private boolean mSeekBarSettled;
    //seekbar百分比
    private int mSbProgress;



    public static ListFragment newInstance(int index) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "lifecyckle,onCreateView");
        View view = inflater.inflate(R.layout.fragment_detail_list, container, false);
        initView(view);
        initEvent();
        initData();
        return view;
    }

    private void initEvent() {
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
        /*for (int i = 0; i <3; i++) {
            mData.add("条目:"+i);
        }*/
        mListRvAdpter = new DetailListRvAdpter(this.getActivity(), mData);
        mListRvAdpter.setItemOnClickListener(this);
        mListRv.setAdapter(mListRvAdpter);
    }


    @Override
    public void onNormalItemClick(View itemView, int position) {

    }

    @Override
    public void onAddItemClick() {
        showNewItemDialog();
    }

    /**
     * 新建思考的item的dialog
     */
    private void showNewItemDialog() {
        mSeekBarSettled = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        View dilogView = View.inflate(this.getActivity(), R.layout.dialog_new_item, null);
        builder.setView(dilogView);
        final EditText et = (EditText) dilogView.findViewById(R.id.dialog_new_item_ed_name);
        SeekBar sb = (SeekBar) dilogView.findViewById(R.id.dialog_new_item_sb_precent);
        Button btnOk = (Button) dilogView.findViewById(R.id.dialog_new_item_btn_ok);
        final TextView mTvImporantPercent = (TextView) dilogView.findViewById(R.id.dialog_new_item_tv_precent);
        Button btnCancel = (Button) dilogView.findViewById(R.id.dialog_new_item_btn_cancel);
        final AlertDialog dialog = builder.show();
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSeekBarSettled = true;
                mSbProgress = progress;
                Log.d(TAG,"状态改变时,mTvImporantPercent为空:"+(mTvImporantPercent == null));
                mTvImporantPercent.setText("重要程度: " + mSbProgress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = et.getText().toString();
                if (!mSeekBarSettled) {
                    Toast.makeText(getActivity().getApplicationContext(), "百分比未设置" + input, Toast.LENGTH_LONG).show();
                    return;
                }


                if (input.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "名称不能为空！" + input, Toast.LENGTH_LONG).show();
                } else {
                    //inserNewItem(input);
                    ListDetailItemBean bean = new ListDetailItemBean();
                    bean.setAddItemType(false);
                    bean.setTitle(input);
                    bean.setPrecent((byte) mSbProgress);
                    mData.add(bean);
                    swapData();

                    //ArrayList<ListDetailItemBean> desList = (ArrayList<ListDetailItemBean>) mData.clone();
                    ListDetailItemBean lastItem = mData.remove(mData.size() - 1);
                    //desList.remove(desList.size() - 1);
                    String content = JsonUtils.listToJson(mData);
                    mData.add(lastItem);
                    Log.d(TAG, "转换后的json字符串为:" + content + " mCurrentItemDbPosition:" + mCurrentItemDbPosition);
                    DbUtils.updateItemContent(mCurrentItemDbPosition, content);
                    dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

       /*final EditText et = new EditText(getActivity());

        new AlertDialog.Builder(this.getActivity(), AlertDialog.THEME_HOLO_LIGHT)
                .setTitle("新思考的名称")
//      .setIcon(android.R.drawable.ic_dialog_info)
                .setView(et)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getActivity().getApplicationContext(), "名称不能为空！" + input, Toast.LENGTH_LONG).show();
                        } else {
                            //inserNewItem(input);
                            ListDetailItemBean bean = new ListDetailItemBean();
                            bean.setAddItemType(false);
                            bean.setTitle(input);
                            mData.add(bean);
                            swapData();
                            //ArrayList<ListDetailItemBean> desList = new ArrayList<>();
//
                            //Collections.copy(desList,mData);

                            ArrayList<ListDetailItemBean> desList = (ArrayList<ListDetailItemBean>) mData.clone();
                            desList.remove(desList.size() - 1);
                            String content = JsonUtils.listToJson(desList);
                            Log.d(TAG, "转换后的json字符串为:" + content + " mCurrentItemDbPosition:" + mCurrentItemDbPosition);
                            DbUtils.updateItemContent(mCurrentItemDbPosition, content);
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();*/
    }

    /**
     * 添加数据后,要把交换一下刚添加的数据和添加item的条目交换位置,保证添加item的条目永远在下面
     */
    private void swapData() {
        if (mData.size() == 2) {
            Collections.swap(mData, 0, 1);
        } else {
            Collections.swap(mData, mData.size() - 2, mData.size() - 1);
        }
        mListRvAdpter.notifyDataSetChanged();
    }


    public void setAddState(boolean addState) {
        Log.d(TAG, "改变编辑状态了:" + addState);
        if (addState) {
            enterAddState();
        } else {
            exitAddState();
        }
    }

    private void exitAddState() {
        mData.remove(mData.size() - 1);
        mListRvAdpter.setData(mData);
        mListRvAdpter.notifyDataSetChanged();
    }

    private void enterAddState() {
        ListDetailItemBean bean = new ListDetailItemBean();
        bean.setAddItemType(true);
        mData.add(bean);
        mListRvAdpter.setData(mData);
        mListRvAdpter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * 添加条目,要增加名称,比重,
     */
    private void addItem() {

    }

    public void setCurrentItemDbPositon(int position) {
        this.mCurrentItemDbPosition = position;
        String itemContent = DbUtils.getItemContent(position);
        mData = JsonUtils.jsonToList(itemContent);
        if (mData == null) {
            mData = new ArrayList<>();
        }
        Log.d(TAG, "数据库中读取的字符串是:" + itemContent + " 转换为数据是:" + mData.toString());
        //mListRvAdpter.setData(mData);
        //mListRvAdpter.notifyDataSetChanged();
    }
}
