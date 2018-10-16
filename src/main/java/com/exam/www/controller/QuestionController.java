package com.exam.www.controller;

import com.exam.www.entity.Question;
import com.exam.www.service.IQuestionService;
import com.exam.www.utils.DataRequest;
import com.exam.www.utils.DataTableReturnObject;
import com.exam.www.utils.DataTableUtil;
import com.exam.www.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/exam")
public class QuestionController {
    @Autowired
    private IQuestionService questionService;

    @RequestMapping(value = "/questionManage", method = RequestMethod.GET)
    public String userManage(HttpServletRequest request,
                             HttpServletResponse response) {
        return "fyxmt.question.mange";
    }

    @RequestMapping(value = "/questionManage/paging", produces="text/html;charset=UTF-8")
    @ResponseBody
    public String questionManagePaging(HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        String sEcho = request.getParameter("sEcho");
        DataRequest dr = DataTableUtil.trans(request);
//        解决乱码问题---多种方式的选择
        String searchKey = new String(request.getParameter("searchKey").getBytes("ISO8859-1"), "UTF-8");
//        String searchKey = request.getParameter("searchKey");
        String searchState = request.getParameter("searchState");
        String searchType = request.getParameter("searchType");
        DataTableReturnObject dro = questionService.getQuestionPageMode(dr, searchKey, searchState,searchType);
        return DataTableUtil.transToJsonStr(sEcho, dro);
    }

    @RequestMapping(value="/question/saveQuestion", method = RequestMethod.POST)
    public String saveQuestion(@ModelAttribute("question")Question question,HttpServletRequest request,
                               HttpServletResponse response){
        questionService.saveQuestion(question);
        return "fyxmt.question.mange";
    }

    @RequestMapping(value="/questionView")
    @ResponseBody
    public Json questionView(@RequestParam(value = "id") String id, HttpServletRequest request){

        Question question=questionService.getQuestionById(id);
        request.setAttribute("question", question);
        Json j=new Json();
        j.setSuccess(true);
        j.setMsg("查询成功！");
        j.setObj(question);
        return j;
    }
    @RequestMapping(value="/deteleQuestionById")
    @ResponseBody
    public Json deleteQuestion(@RequestParam(value = "id") String id, HttpServletRequest request){
//        物理删除---删除该题和它相关联的试卷,否则在查看试卷时找不到该题目
//      默认采用物理删除---即在查看试卷时没有该题目了,输出提示信息或者不提示
//        questionService.deleteQuestionById(id);
//        逻辑删除---只改变它的状态,数据还存放在题库中---再生成试卷搜索题库时,根据试题状态来组成试卷
        questionService.logicDeleteQuestionById(id);
        Json j=new Json();
        j.setSuccess(true);
        j.setMsg("删除成功！");
        return j;
    }
    @RequestMapping(value = "/updateQuestionState", method = RequestMethod.GET)
    @ResponseBody
    public Json updateQuetsionState(@RequestParam(value = "ids") String[] ids,
                           @RequestParam(value = "state") String state) throws IOException {
        questionService.updateQuestionState(ids, state);
        Json j=new Json();
        j.setSuccess(true);
        j.setMsg("更新成功！");
        return j;
    }






}
