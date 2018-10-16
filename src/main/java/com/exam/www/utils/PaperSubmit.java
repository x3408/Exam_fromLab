package com.exam.www.utils;
/*
用于接收用户提交的问题id及答案
 */
public class PaperSubmit {
    private String questionId;
    private String questionAnswer;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }



    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }
}
