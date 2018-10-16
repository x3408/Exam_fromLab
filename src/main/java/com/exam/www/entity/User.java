package com.exam.www.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2016/10/11.
 */
@Entity
@Table(name = "user")
public class User extends BaseEntity implements java.io.Serializable {

    private String state;//正常1    注销2  冻结3

    private String companyName;//单位

    private String userName;//登录名--身份证

    private String passWord;//密码

    private Integer isAdmin;//1是 0不是

    private String name;//昵称

    private Integer isLock;//是否锁定   0没有锁定  1锁定

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private Set<Paper> paperList
            = new HashSet<Paper>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private Set<Apply> applyList = new HashSet<>(0);
    @Column(length = 10)
    private String isDelete;
    @Transient
    private String userInfo;
    @Transient
    private String passwordConfirm;

    public Set<Apply> getApplyList() {
        return applyList;
    }

    public void setApplyList(Set<Apply> applyList) {
        this.applyList = applyList;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public Set<Paper> getPaperList() {
        return paperList;
    }

    public void setPaperList(Set<Paper> paperList) {
        this.paperList = paperList;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
