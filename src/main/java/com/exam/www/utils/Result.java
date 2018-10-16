package com.exam.www.utils;
/*
封装试卷得分信息
 */
public class Result {
    private String baseSum;//试卷总分值
    private String paperScore;//试卷得分
    private String choiceScore;//选择题总得分
    private String packScore;//填空题总得分

    public String getBaseSum() {
        return baseSum;
    }

    public void setBaseSum(String baseSum) {
        this.baseSum = baseSum;
    }

    public String getPaperScore() {
        return paperScore;
    }

    public void setPaperScore(String paperScore) {
        this.paperScore = paperScore;
    }

    public String getChoiceScore() {
        return choiceScore;
    }

    public void setChoiceScore(String choiceScore) {
        this.choiceScore = choiceScore;
    }

    public String getPackScore() {
        return packScore;
    }

    public void setPackScore(String packScore) {
        this.packScore = packScore;
    }
}
