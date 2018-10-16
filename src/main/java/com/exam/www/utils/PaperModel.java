package com.exam.www.utils;

import java.util.List;

/*
试卷数据
 */
public class PaperModel {
    //接收用户提交的问题信息
    List<PaperSubmit> list;
    //接收试卷Id
    private String paperId;

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public List<PaperSubmit> getList() {
        return list;
    }

    public void setList(List<PaperSubmit> list) {
        this.list = list;
    }
}
