package com.exam.www.dto;

/**
 * Created by Administrator on 2017/7/8.
 */
public class SpecialistDTO implements java.io.Serializable{
    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 单位
     */
    private String companyName;

    /**
     * 微信id
     */
    private String openId;

    /**
     * 职务
     */
    private String job;

    /**
     * 身份证号
     */
    private String idcard;

    /**
     * 手机号码
     */
    private String mobilePhone;

    /**
     * 专家微信登录密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 最高学历
     */
    private String degree;

    /**
     * 职称
     */
    private String professionalTitle;

    /**
     * 专业
     */
    private String specialty;

    /**
     * 工作履历
     */
    private String jobExperience;

    /**
     * 科研成果
     */
    private String achievements;

    /**
     * 状态：1：启用；2：停用
     */
    private String state;

    /**
     * 逻辑删除标识
     */
    private String isDelete;

    /**
     * 微信头像url
     */
    private String headImage;

    /**
     * 评分状态：
     * 1:该专家正在打分活动中,2:结束活动
     */
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getProfessionalTitle() {
        return professionalTitle;
    }

    public void setProfessionalTitle(String professionalTitle) {
        this.professionalTitle = professionalTitle;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getJobExperience() {
        return jobExperience;
    }

    public void setJobExperience(String jobExperience) {
        this.jobExperience = jobExperience;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
