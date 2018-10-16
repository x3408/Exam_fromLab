package com.exam.www.entity;


import javax.persistence.*;

@Entity
@Table(name = "paper_question")
public class PaperQuestion implements java.io.Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "questionId")
    private Question question;
    @ManyToOne
    @JoinColumn(name = "paperId")
    private Paper paper;
    private Integer isRight;//判断是否正确---0错误  1正确  Null未填
    private String personAnswer;//用户存储上传的答案
    private Integer questionIsAnswer;//问题状态  1-已答题  0-未答题

    public Integer getQuestionIsAnswer() {
        return questionIsAnswer;
    }

    public void setQuestionIsAnswer(Integer questionIsAnswer) {
        this.questionIsAnswer = questionIsAnswer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonAnswer() {
        return personAnswer;
    }

    public void setPersonAnswer(String personAnswer) {
        this.personAnswer = personAnswer;
    }

    public Integer getIsRight() {
        return isRight;
    }

    public void setIsRight(Integer isRight) {
        this.isRight = isRight;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }
}
