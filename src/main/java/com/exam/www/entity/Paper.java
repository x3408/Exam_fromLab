package com.exam.www.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="paper")
public class Paper extends  BaseEntity implements java.io.Serializable{
    private String paperTilte;  //试卷表题
    private String paperTheme;  //试卷主题
    private Integer paperState; //试卷状态：0：未提交；1：提交；
    private Integer paperScore;//试卷总分
    private Integer rightNum;  //正确答题数目
    private Integer paperSum; //满分多少
    private Integer choiceNum;//选择题个数
    private Integer multiNum;//多选题个数
    private Integer judgeNum;//判断题个数

    public Integer getChoiceNum() {
        return choiceNum;
    }

    public void setChoiceNum(Integer choiceNum) {
        this.choiceNum = choiceNum;
    }

    public Integer getJudgeNum() {
        return judgeNum;
    }

    public void setJudgeNum(Integer judgeNum) {
        this.judgeNum = judgeNum;
    }

    public Integer getPaperScore() {
        return paperScore;
    }

    public void setPaperScore(Integer paperScore) {
        this.paperScore = paperScore;
    }

    public Integer getPaperSum() {
        return paperSum;
    }

    public void setPaperSum(Integer paperSum) {
        this.paperSum = paperSum;
    }

    /*答卷人*/
    @ManyToOne(cascade =  CascadeType.MERGE)
    @JoinColumn(name = "userId")
    private User user;

     /*试题*/
/*    @ManyToMany(cascade = CascadeType.ALL)*/
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "paper_question",
            joinColumns = { @JoinColumn(name = "paperId") },
            inverseJoinColumns = { @JoinColumn(name = "questionId") })
    private List<Question> questions;
    public String getPaperTilte() {
        return paperTilte;
    }

    public void setPaperTilte(String paperTilte) {
        this.paperTilte = paperTilte;
    }

    public String getPaperTheme() {
        return paperTheme;
    }

    public void setPaperTheme(String paperTheme) {
        this.paperTheme = paperTheme;
    }

    public Integer getPaperState() {
        return paperState;
    }

    public void setPaperState(Integer paperState) {
        this.paperState = paperState;
    }



    public Integer getRightNum() {
        return rightNum;
    }

    public void setRightNum(Integer rightNum) {
        this.rightNum = rightNum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Integer getMultiNum() {
        return multiNum;
    }

    public void setMultiNum(Integer multiNum) {
        this.multiNum = multiNum;
    }
}
