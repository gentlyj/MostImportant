package com.ifading.mostimportant.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ifading.mostimportant.bean.MindItemBean;
import java.util.ArrayList;


/**
 * 数据库操作类 Created by Administrator on 2017/5/12.
 */

public class DbDao {

    private static final String TAG = "DbDao";
    private final SqliteHelper mHelper;


    public DbDao(Context context){
        mHelper = new SqliteHelper(context, DataBase.DATABASE_NAME,null,DataBase.dbVersion);
    }

    public void insertItem(String name){
        SQLiteDatabase database = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBase.MAINPAGE.ITEM_NAME,name);
        database.insert(DataBase.MAINPAGE.TABLE_NAME,null,contentValues);

        database.close();
    }

    /**
     * 获取所有item

     * @return 数据
     */
    public ArrayList<MindItemBean> getAllData() {
        ArrayList<MindItemBean> data = new ArrayList<>();
        String sql = "select * from " + DataBase.MAINPAGE.TABLE_NAME;
        SQLiteDatabase database = mHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, new String[]{});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                MindItemBean bean = new MindItemBean();
                Log.d(TAG, "检索到数据:" + cursor.getString(cursor.getColumnIndex(DataBase.MAINPAGE.ITEM_NAME)));
                bean.setName(cursor.getString(cursor.getColumnIndex(DataBase.MAINPAGE.ITEM_NAME)));
                bean.setDescribe(cursor.getString(cursor.getColumnIndex(DataBase.MAINPAGE.ITEM_DESCRIBE)));
                bean.setId(cursor.getInt(cursor.getColumnIndex(DataBase.MAINPAGE.ITEM_ID)));
                bean.setContent(cursor.getString(cursor.getColumnIndex(DataBase.MAINPAGE.CONTENT)));
                data.add(bean);
            }
            cursor.close();

        }
        database.close();
        return data;
    }

    /**
     * 更新某一思考的内部条目的改变
     *
     * @param id      大条目的id
     * @param content 所有小条目数据转换成的json字符串
     */
    public void updateItemContent(int id, String content) {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBase.MAINPAGE.CONTENT, content);

        int update = database.update(DataBase.MAINPAGE.TABLE_NAME, contentValues, DataBase.MAINPAGE.ITEM_ID + " =? ", new String[]{String.valueOf(id)});

        Log.d("ListFragment","更新结果是:"+update);

        database.close();
    }

    /**
     * 通过id获取具体的内容
     * @param position id
     * @return 内容
     */
    public String getItemContent(int position) {
        SQLiteDatabase database = mHelper.getWritableDatabase();

        String sql = "select * from " + DataBase.MAINPAGE.TABLE_NAME + " where " + DataBase.MAINPAGE.ITEM_ID + " =? ";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(position)});
        String content = null;
        if (cursor != null ) {
            if (cursor.moveToNext()) {
                content = cursor.getString(cursor.getColumnIndex(DataBase.MAINPAGE.CONTENT));
            }
            cursor.close();
        }
        database.close();
        return content;
    }
}
