package com.exam.www.utils;
/*
连接Paper和中间表
 */
public class JoinPaperQuestion {
    private String id;//id
    private String questionTheme;//试题主题
    private String questionTitle;//试题题目
    private String alternativeOption1;//为了显示在页面上方便显示选择题A,B,C,D---亢余字段
    private String alternativeOption2;
    private String alternativeOption3;
    private String alternativeOption4;

    private String rightAnswer;//正确答案
    private Integer baseScope;//得分
    private Integer questionState;  //试题状态：0：未答题；1：正确；2：错误
    private Integer questionType; //试题类型：0：选择题；1填空题
    private Integer isRight;//是否正确
    private String personAnswer;//个人答案

    public String getPersonAnswer() {
        return personAnswer;
    }

    public void setPersonAnswer(String personAnswer) {
        this.personAnswer = personAnswer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionTheme() {
        return questionTheme;
    }

    public void setQuestionTheme(String questionTheme) {
        this.questionTheme = questionTheme;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getAlternativeOption1() {
        return alternativeOption1;
    }

    public void setAlternativeOption1(String alternativeOption1) {
        this.alternativeOption1 = alternativeOption1;
    }

    public String getAlternativeOption2() {
        return alternativeOption2;
    }

    public void setAlternativeOption2(String alternativeOption2) {
        this.alternativeOption2 = alternativeOption2;
    }

    public String getAlternativeOption3() {
        return alternativeOption3;
    }

    public void setAlternativeOption3(String alternativeOption3) {
        this.alternativeOption3 = alternativeOption3;
    }

    public String getAlternativeOption4() {
        return alternativeOption4;
    }

    public void setAlternativeOption4(String alternativeOption4) {
        this.alternativeOption4 = alternativeOption4;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Integer getBaseScope() {
        return baseScope;
    }

    public void setBaseScope(Integer baseScope) {
        this.baseScope = baseScope;
    }

    public Integer getQuestionState() {
        return questionState;
    }

    public void setQuestionState(Integer questionState) {
        this.questionState = questionState;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getIsRight() {
        return isRight;
    }

    public void setIsRight(Integer isRight) {
        this.isRight = isRight;
    }
}
