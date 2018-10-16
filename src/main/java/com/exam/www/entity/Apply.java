package com.exam.www.entity;

import javax.persistence.*;

@Entity
@Table(name="apply")
public class Apply extends  BaseEntity implements java.io.Serializable{
    private Integer state;//状态    0待处理   1已处理  2已过期

    private String applyContent;//申请内容
    @ManyToOne(cascade =  CascadeType.MERGE)
    @JoinColumn(name = "userId")
    private User user;

    public String getApplyContent() {
        return applyContent;
    }

    public void setApplyContent(String applyContent) {
        this.applyContent = applyContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
