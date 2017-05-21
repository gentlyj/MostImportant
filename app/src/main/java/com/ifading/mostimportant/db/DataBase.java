package com.ifading.mostimportant.db;

/**
 * Created by Administrator on 2017/5/12.
 */

public interface DataBase {
     String DATABASE_NAME = "most_important";
    int dbVersion = 1;

    interface MAINPAGE{
        String TABLE_NAME = "mainpage";
        String ITEM_ID = "item_id";
        String ITEM_NAME = "item_name";
        String ITEM_DESCRIBE = "item_describe";
        String CONTENT = "content";

        String CREATE_SQL = "create table "+ TABLE_NAME +"( "
                            + ITEM_ID +"id integer primary key autoincrement, "
                            + ITEM_NAME + " text ,"
                            + ITEM_DESCRIBE + " text ,"
                            + CONTENT + " text )";
    }
}
