package com.exam.www.controller;

import com.exam.www.entity.Apply;
import com.exam.www.service.IApplyService;
import com.exam.www.utils.DataRequest;
import com.exam.www.utils.DataTableReturnObject;
import com.exam.www.utils.DataTableUtil;
import com.exam.www.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class ApplyController {
    @Autowired
    private IApplyService applyService;

    @RequestMapping(value = "/applyManage", method = RequestMethod.GET)
    public String applyManage(HttpServletRequest request,
                              HttpServletResponse response) {
        return "fyxmt.userApply.mange";
    }

    @RequestMapping(value = "/applyManage/paging", produces="text/html;charset=UTF-8")
    @ResponseBody
    public String applyManagePaging(HttpServletRequest request,
                                    HttpSession session) throws Exception {
        String sEcho = request.getParameter("sEcho");
        DataRequest dr = DataTableUtil.trans(request);
        String searchUserInfo = new String(request.getParameter("searchUserInfo").getBytes("ISO8859-1"), "UTF-8");
        String searchState = request.getParameter("searchState");
        DataTableReturnObject dro = applyService.getUserApplyPageMode(dr, searchUserInfo, searchState,session);
        return DataTableUtil.transToJsonStr(sEcho, dro);
    }

    @RequestMapping(value = "/deleteUserApply", method = RequestMethod.GET)
    @ResponseBody
    public Json deleteUser(@RequestParam(value = "ids") String[] ids, HttpServletRequest request) throws IOException {
        applyService.deleteUserApply(ids);
        Json j=new Json();
        j.setSuccess(true);
        j.setMsg("删除成功！");
        return j;
    }

    @RequestMapping(value = "/updateApply", method = RequestMethod.POST)
    public String updateApply(@ModelAttribute("apply")Apply apply, HttpServletRequest request) {
        applyService.updateApply(apply);
        //return "redirect:"+request.getContextPath()+"/user/userManage";
        return "redirect:/user/applyManage";
    }

    @RequestMapping(value = "/viewApply", method = RequestMethod.GET)
    @ResponseBody
    public Json viewUser(@RequestParam(value = "id") String id, HttpServletRequest request) {
        Apply apply = applyService.getApply(id);
        Json json=new Json();
        json.setSuccess(false);
        if(apply!=null) {
            json.setSuccess(true);
            json.setObj(apply);
        }
        json.setCode("0");
        return json;
    }
}
