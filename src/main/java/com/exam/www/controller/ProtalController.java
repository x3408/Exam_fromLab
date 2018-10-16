package com.exam.www.controller;

import com.alibaba.fastjson.JSON;
import com.exam.www.entity.User;
import com.exam.www.service.IMaterialService;
import com.exam.www.service.IUserService;
import com.exam.www.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/protal")
public class ProtalController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IMaterialService materialService;

    /*@RequestMapping("")
  public String protalLogin(User user,RedirectAttributes attr){
      String userName=user.getUserName();
      *//*if(userName!=null) {
            attr.addFlashAttribute("userName", userName);
            attr.addFlashAttribute("choiceNum", 2);
            attr.addFlashAttribute("judgeNum", 3);*//*
       *//* return "redirect:/exam/testPaper/producePlus?choiceNum=2&judgeNum=3&id="+uid;*//*//不安全--
           *//* return "redirect:/exam/testPaper/producePlus";*//*
        }//
        return "";
    }*/
    @RequestMapping("/protalLogin")
    public String protalLoginRedirect() {
        return "/protal/protalLogin";
    }

    @RequestMapping("/protalPaperDetails")
    public String protalPaperDetails() {
        return "/protal/protalPaperDetails";
    }

    @RequestMapping({"/protalMaterialDetails"})
    public String protalMaterialDetails() {
        return "/protal/protalMaterialDetails";
    }

    /*
    测试试卷提交测试
     */
   /* @RequestMapping("/paperSumitTest")
    public String protalSubmit() {
        return "/protal/test";
    }
*/
    @RequestMapping(value = "/checkProtalLogin", method = RequestMethod.POST)
    @ResponseBody
    public Json protalLogin(String userName,
                            String passWord, HttpSession httpSession) {
        Json result = new Json();
        result.setCode("-1");
        result.setMsg("登录失败");
        result.setSuccess(false);
        if (userName == null || userName.isEmpty()) {
            result.setMsg("用户名不能为空");
            return result;
        }
        if (passWord == null || passWord.isEmpty()) {
            result.setMsg("密码不能为空");
            return result;
        }
        boolean flag = userService.checkUserByUserName(userName);
        if (!flag) {
            result.setMsg("不存在该用户");
            return result;
        }
        User user = userService.checkProtalLogin(userName, passWord);
        if (user == null) {
            result.setMsg("用户或密码错误");
            result.setCode("-1");
            return result;
        } else {
            //检验用户的状态,正常才可以登录---//正常1    注销2  冻结3
            if (user.getState().equals("1")) {
                //将数据存储到session中
                /*//获取session的Id
                String sessionId = httpSession.getId();
                //判断session是不是新创建的
                if (httpSession.isNew()) {
                } else {
                }*/
                httpSession.setAttribute("exitUser", user);
                httpSession.setMaxInactiveInterval(30 * 60);//30分钟失效
                System.out.println("将user放入session中");
                result.setSuccess(true);
                result.setMsg("成功登录");
                result.setCode("0");
            } else if (user.getState().equals("2")) {
                result.setMsg("用户已注销");
            } else if (user.getState().equals("3")) {
                result.setMsg("用户已被冻结");
            }
            return result;
        }
    }
    @RequestMapping(value = "/exitProtalLogin", method = RequestMethod.POST)
    @ResponseBody
    public Json exitProtalLogin(HttpSession session) {
           session.invalidate();
           Json json=new Json();
           json.setMsg("退出成功");
           json.setCode("0");
           json.setSuccess(true);
           return  json;
    }
    @RequestMapping(value={"/selectMaterialList"}, produces={"text/html;charset=UTF-8"})
    @ResponseBody
    public String selectMaterialList(String goPage, HttpSession session)
    {
        int page;
        if (goPage == null) {
            page = 0;
        }
        else if (goPage.equals(""))
            page = 0;
        else
            page = Integer.parseInt(goPage);
        Sort sort = new Sort(Sort.Direction.DESC, new String[] { "createDate" });
        Pageable pageable = new PageRequest(page, 10, sort);
        Page materialList = materialService.findAll(pageable, session);
       return JSON.toJSONString(materialList,true);
    }
}
