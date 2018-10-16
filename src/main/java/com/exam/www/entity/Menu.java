package com.exam.www.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu Qingxin on 2016-10-15.
 */
@Entity
@Table(name = "menu")
public class Menu extends BaseEntity implements java.io.Serializable {

    @Column(length = 50)
    private String name;
    @Column(length = 50, unique = true)
    private String menuKey;//这个权限KEY是唯一的，新增时要注意，
    @Column(length = 200)
    private String menuUrl;//URL地址．例如：/videoType/query　　不需要项目名和http://xxx:8080

    private int sequence;//级别 菜单的顺序

    @Column(columnDefinition = "varchar(500)")
    private String description;
    @Column(columnDefinition = "varchar(200)")
    private String logo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "parent_id")
    private Menu parent;
    @Transient
    private List<Menu> menusList = new ArrayList<Menu>();

    public Menu(String name, String menuUrl, String logo) {
        super();
        this.name = name;
        this.menuUrl = menuUrl;
        this.logo = logo;
    }

    public Menu() {
        super();
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(String menuKey) {
        this.menuKey = menuKey;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Menu> getMenusList() {
        return menusList;
    }

    public void setMenusList(List<Menu> menusList) {
        this.menusList = menusList;
    }
}
