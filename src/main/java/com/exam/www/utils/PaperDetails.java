package com.exam.www.utils;

import com.exam.www.entity.Paper;

import java.util.List;

/**
 * 用于显示试卷详情的对象
 */
public class PaperDetails {
    private Paper paper;//试卷对象
    private List<JoinPaperQuestion> list;//用于存放JoinPaperQuestion对象(含有isRight字段和Question信息)

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public List<JoinPaperQuestion> getList() {
        return list;
    }

    public void setList(List<JoinPaperQuestion> list) {
        this.list = list;
    }
}
