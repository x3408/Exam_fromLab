package com.exam.www.service.impl;


import com.exam.www.entity.Menu;
import com.exam.www.entity.User;
import com.exam.www.repository.MenuRepository;
import com.exam.www.repository.UserRepository;
import com.exam.www.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by clyde on 2016/11/2.
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl implements IMenuService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Value("#{configProperties['ysb']}")
    private String ysb;

    @Value("#{configProperties['yssh']}")
    private String yssh;

    public List<Menu> getMenu(HttpServletRequest request) {
        User user = getUser(userRepository);
        //根据返回的user对象类型来判断是为管理员还是普通用户
        String menuKey = "admin";
        if (user.getIsAdmin()==0) {
            menuKey = "user";
        }
        //根据menuKey查看用户对应的模块,返回list列表
        return menuRepository.getByMenuKey(menuKey);
}
}
