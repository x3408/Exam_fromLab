package com.exam.www.entity;

import javax.persistence.Entity;

@Entity
public class Material extends BaseEntity{
    private String materialDescription;//备注
    private String materialUrl;//路径
    private String materialTheme;//主题
    private Integer materialState;//状态

    public Integer getMaterialState() {
        return materialState;
    }

    public void setMaterialState(Integer materialState) {
        this.materialState = materialState;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl;
    }

    public String getMaterialTheme() {
        return materialTheme;
    }

    public void setMaterialTheme(String materialTheme) {
        this.materialTheme = materialTheme;
    }
}
