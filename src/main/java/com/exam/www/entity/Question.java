package com.exam.www.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question extends BaseEntity implements java.io.Serializable {

    private String questionTheme;//试题主题
    private String questionTitle;//试题题目
    private String alternativeOption1;//为了显示在页面上方便显示选择题A,B,C,D---亢余字段
    private String alternativeOption2;
    private String alternativeOption3;
    private String alternativeOption4;

    private String rightAnswer;//正确答案
    private Integer baseScope;//分值
    private Integer questionType; //试题类型：0：选择题；1填空题
    private Integer questionState;//试题状态: 0-正常 1-弃用  2-逻辑删除

    public Integer getQuestionState() {
        return questionState;
    }

    public void setQuestionState(Integer questionState) {
        this.questionState = questionState;
    }

    /*答卷*/
    /*@ManyToMany(cascade = CascadeType.ALL,mappedBy = "questions")
    @JsonIgnore*/
   /* @ManyToMany(cascade = CascadeType.MERGE,mappedBy = "questions")
    @JsonIgnore
    private List<Paper> papers;*/

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


    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }


    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getAlternativeOption1() {
        return alternativeOption1;
    }

    public void setAlternativeOption1(String alternativeOption1) {
        this.alternativeOption1 = alternativeOption1;
    }

    public Integer getBaseScope() {
        return baseScope;
    }

    public void setBaseScope(Integer baseScope) {
        this.baseScope = baseScope;
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

   /* public List<Paper> getPapers() {
        return papers;
    }

    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }*/
}
