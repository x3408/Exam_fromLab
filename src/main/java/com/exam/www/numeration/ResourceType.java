package com.exam.www.numeration;

/**
 * Created by Xu Qingxin on 2016-10-15.
 */
public enum ResourceType {

    PAGE("页面"),
    BUTTON("按钮");

    String name;

    ResourceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
