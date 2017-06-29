package com.ifading.mostimportant.bean;

/**
 * 详细列表里条目对应的数据封装类 Created by yangjingsheng on 17/6/29.
 */

public class ListDetailItemBean {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAddItemType() {
        return addItemType;
    }

    public void setAddItemType(boolean addItemType) {
        this.addItemType = addItemType;
    }

    private boolean addItemType;

    @Override
    public String toString() {
        return "ListDetailItemBean{" +
                "title='" + title + '\'' +
                ", addItemType=" + addItemType +
                '}';
    }
}
