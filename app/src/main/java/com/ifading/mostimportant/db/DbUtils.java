package com.ifading.mostimportant.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ifading.mostimportant.bean.MindItemBean;

import java.util.ArrayList;

/**
 * Created by yangjingsheng on 17/6/29.
 */

public class DbUtils {

    private static DbDao mDao;

    public static void init(Context context){
        mDao = new DbDao(context);
    }

    public static void insertItem(String name){
        mDao.insertItem(name);
    }

    /**
     * 获取所有item
     * @return 数据
     */
    public static ArrayList<MindItemBean> getAllData() {
        return mDao.getAllData();
    }

    public static void updateItemContent(int id,String content){
        mDao.updateItemContent( id, content);
    }

    public static String getItemContent(int position) {
        return mDao.getItemContent(position);
    }

    public static void updateItemDescribe(int mCurrentItemDbPosition, String describtion) {
        mDao.updateItemDescribe(mCurrentItemDbPosition,describtion);
    }

    public static String getItemDescribe(int mCurrentItemDbPosition) {
        return mDao.getItemDescribe(mCurrentItemDbPosition);
    }
}
