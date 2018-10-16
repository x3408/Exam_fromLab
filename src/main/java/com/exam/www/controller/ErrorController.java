package com.exam.www.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by admin on 2016/10/11.
 */
@Controller
public class ErrorController {

    @RequestMapping("/error")
    public String error() {
    return "error";
    }

    @RequestMapping("/403")
    public String error403(Model model) {
        model.addAttribute("status","403");
        model.addAttribute("msg","没有权限访问该页面！");
        return "error";
    }
}
