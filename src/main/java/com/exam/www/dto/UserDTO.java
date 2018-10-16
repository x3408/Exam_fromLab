package com.exam.www.dto;

import com.exam.www.entity.User;
import com.exam.www.utils.DateUtil;
import com.google.common.base.Function;

public class UserDTO implements java.io.Serializable {
    //将user------>>userdto
    public static Function<User, UserDTO> couseEnrollUsers = new Function<User, UserDTO>(){
        @Override
        public UserDTO apply(User user) {
            UserDTO dto = convertUser2UserDTO(user);
            return dto;
        }
    };
    public static UserDTO convertUser2UserDTO(User user) {
        UserDTO userDTO=new UserDTO();
        userDTO.setUserName(user.getUserName());
        try {
            userDTO.setCreateDate(DateUtil.formatDate2String(user.getCreateDate(), "yyyy-mm-dd hh:mm:ss"));
        }catch (Exception e){
            System.out.println(e.getCause());
        }
        userDTO.setName(user.getName());
        userDTO.setCompanyName(user.getCompanyName());
        return userDTO;
    }
    //创建时间
    private String createDate;
    //    登录名
    private String userName;
    //    真实姓名
    private String name;
    //     公司名称
    private String companyName;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
