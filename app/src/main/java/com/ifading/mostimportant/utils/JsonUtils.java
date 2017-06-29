package com.ifading.mostimportant.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ifading.mostimportant.bean.ListDetailItemBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


/**
 * Json 工具类 Created on 2016/6/21.
 */
public class JsonUtils {


    /**
     * map to json
     *
     * @param map
     * @return json String
     */
    public static String mapToJson(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }

        StringBuilder paras = new StringBuilder();
        paras.append("{");
        Iterator<Map.Entry<String, String>> ite = map.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>)ite.next();
            paras.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\"");
            if (ite.hasNext()) {
                paras.append(",");
            }
        }
        paras.append("}");
        return paras.toString();
    }

    public static Map<String,String> jsonToMap(String mapString) {
        Gson gson = new Gson();

        return gson.fromJson(mapString,new TypeToken<Map<String, String>>() {
        }.getType());

    }

    /**
     * json to list
     * @param listString 数据库取出的字符串
     * @return list
     */
    public static ArrayList<ListDetailItemBean> jsonToList(String listString){
        Gson gson = new Gson();

        return gson.fromJson(listString,new TypeToken<ArrayList<ListDetailItemBean>>() {
        }.getType());
    }


    public static String listToJson(ArrayList<ListDetailItemBean> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
