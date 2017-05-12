package com.ifading.mostimport.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
    public ArrayList<String> getAllData() {
        ArrayList<String> data = new ArrayList<>();
        String sql = "select * from "+ DataBase.MAINPAGE.TABLE_NAME ;
        SQLiteDatabase database = mHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, new String[]{});
        if (cursor!=null){
            while (cursor.moveToNext()){
                Log.d(TAG,"检索到数据:"+cursor.getString(cursor.getColumnIndex(DataBase.MAINPAGE.ITEM_NAME)));
                data.add(cursor.getString(cursor.getColumnIndex(DataBase.MAINPAGE.ITEM_NAME)));
            }
            cursor.close();
        }
        database.close();

        return data;
    }
}
