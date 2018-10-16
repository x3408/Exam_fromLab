package com.exam.www.service;


import com.exam.www.entity.User;
import com.exam.www.utils.DataRequest;
import com.exam.www.utils.DataTableReturnObject;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/10/11.
 */
public interface IUserService {

    public User findUserByUsername(String username);

    public ModelAndView setModel(HttpSession session);

    public void save(User user);

    public String checkRole(String url, Map<String, Collection<ConfigAttribute>> resourceMap);

     public DataTableReturnObject getUserPageMode(DataRequest dr, final String searchUserInfo, final String state,HttpSession session);

    public DataTableReturnObject getUserPageMode(DataRequest dr, final String searchUserInfo, final String state,HttpSession session,final  String beginDate,final String overDate);

    public void saveUser(User user);

    public void deleteUser(String[] ids);

    public void updateUserState(String[] ids, String state);

    public User getUser(String id);

    public void updateUser(User user);

    public void resetUserPassword(String id);

    public User findUserByMobilePhone(String mobilePhone, String id);

    public void update(User user);
    public User selectExistUser(HttpSession session);
    public boolean checkUserByUserName(String userName);
    public User checkProtalLogin(String userName, String passWord);
    public User findAdminUserByUserName(String userName);

    public List<User> findUserList(int page, int pageSize,String userProperty,String userDirection,Map<String, Object> map) throws Exception;

    public List<User> findList();

    /*
    查询出符合要求的列表
     */
    public List<User> findList(Map<String,Object> map);
}
