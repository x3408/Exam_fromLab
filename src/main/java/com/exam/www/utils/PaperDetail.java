package com.exam.www.utils;

import com.exam.www.entity.Paper;
import com.exam.www.entity.PaperQuestion;

import java.util.List;

public class PaperDetail {
    private Paper paper;//试卷
    private List<PaperQuestion> paperQuestion;//试卷问题(包含isRight)



    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public List<PaperQuestion> getPaperQuestion() {
        return paperQuestion;
    }

    public void setPaperQuestion(List<PaperQuestion> paperQuestion) {
        this.paperQuestion = paperQuestion;
    }
}
