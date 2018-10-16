package com.exam.www.controller;


import com.exam.www.entity.User;
import com.exam.www.service.IUserService;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class LoginController {
    private static final Logger logger = Logger.getLogger(LoginController.class);

//    @Value("#{configProperties['ldapHost']}")
//    private String ldapHost;
//
//    @Value("#{configProperties['ldapPort']}")
//    private String ldapPort;

    @Value("#{configProperties['domain']}")
    private String domain;

    @Value("#{configProperties['base']}")
    private String base;

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/ldap", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String ldap(HttpServletRequest request,HttpSession session) {
        JSONObject j = new JSONObject();
        j.put("success", false);
        j.put("code", -1);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.findAdminUserByUserName(username);
        boolean flag = true;
        String code = "";
        if (user == null) {
            System.out.println("用户不存在");
            flag=false;
            code="-1";
            j.put("msg", "用户不存在");
            return j.toString();
        }
        session.setAttribute("adminUser",user);
        j.put("success", flag);
        j.put("code", code);
        logger.warn("正在校验登录");
        return j.toString();
    }

    @RequestMapping("/login")
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "invalid", required = false) String invalid,
            @RequestParam(value = "notLogin", required = false) String notLogin,
            @RequestParam(value = "message", required = false) String message,
            HttpSession session) {
        System.out.println(session.getAttributeNames());
        ModelAndView model = new ModelAndView();
        logger.warn("校验登录");
        if (error != null) {
            model.addObject("msg", "用户名或密码不正确！！");
        }

        if (logout != null) {
            model.addObject("msg", "您已成功登出！！");
        }

        if (invalid != null) {
            model.addObject("msg", "当前账号已被他人登陆或者登陆超时！！");
        }

        if (notLogin != null) {
            model.addObject("msg", "您当前未登陆系统！！");
        }
        if (!StringUtils.isEmpty(message)) {
            model.addObject(message);
        }
        model.setViewName("login");
        return model;
    }



    @RequestMapping("/mainMenu")
    public String mainMenu(HttpSession session) {
        logger.warn("主页面");
        //tiles视图插件---tiles.xml
        return "default.main";
    }


    public static void main(String args[]) {
        String md5Password = new Md5PasswordEncoder().encodePassword(
                "123456", "");
        System.out.println(md5Password);
    }
}
