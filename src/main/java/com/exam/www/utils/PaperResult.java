package com.exam.www.utils;
/*
封装试卷得分信息
 */
public class PaperResult {
    private Integer baseSum;//试卷总分值
    private Integer paperScore;//试卷得分
    private Integer choiceScore;//选择题总得分
    private Integer packScore;//判断题总得分

    public Integer getBaseSum() {
        return baseSum;
    }

    public void setBaseSum(Integer baseSum) {
        this.baseSum = baseSum;
    }

    public Integer getPaperScore() {
        return paperScore;
    }

    public void setPaperScore(Integer paperScore) {
        this.paperScore = paperScore;
    }

    public Integer getChoiceScore() {
        return choiceScore;
    }

    public void setChoiceScore(Integer choiceScore) {
        this.choiceScore = choiceScore;
    }

    public Integer getPackScore() {
        return packScore;
    }

    public void setPackScore(Integer packScore) {
        this.packScore = packScore;
    }
}
