package com.exam.www.numeration;

/**
 * Created by Xu Qingxin on 2016-10-15.
 */
public enum FileType {

    OVERALL("0"),
    MEETING("1"),
    SINGLE("2");

    String name;

    FileType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
