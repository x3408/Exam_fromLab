package com.exam.www.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

//直接继承Spring Security中默认提供的实现类User，
// 但是User为了避免用户信息被外部程序篡改，被设计为只能通过构造方法来为内部数据赋值，没有提供setter方法对其中数据进行修改
//后台UserDetail
public class BackendUserDtail extends User {

    private static final long serialVersionUID = 4374424667663347893L;
    /**
     *
     */
    private String id;
    private String nickName;
    private String tel;
    private String address;
    private String logo;

    public BackendUserDtail(String username, String password,
                            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public BackendUserDtail(String username, String password, boolean enabled,
                            boolean accountNonExpired, boolean credentialsNonExpired,
                            boolean accountNonLocked,
                            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
