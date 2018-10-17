package com.exam.www.controller;

import com.exam.www.entity.Apply;
import com.exam.www.entity.Paper;
import com.exam.www.entity.User;
import com.exam.www.service.IApplyService;
import com.exam.www.service.IPaperService;
import com.exam.www.service.IQuestionPaperService;
import com.exam.www.utils.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/exam")
public class PaperController {
    @Autowired
    private IPaperService iPaperService;
    @Autowired
    private IQuestionPaperService iQuestionPaperService;
    @Autowired
    private IApplyService iApplyService;

    @RequestMapping(value = "/testPaperManage", method = RequestMethod.GET)
    public String userManage(HttpServletRequest request,
                             HttpServletResponse response) {
        return "fyxmt.testPaper.mange";
    }

    @RequestMapping(value = "/testPaperManage/paging", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String paperManagePaging(HttpServletRequest request) throws Exception {
        String sEcho = request.getParameter("sEcho");
        DataRequest dr = DataTableUtil.trans(request);
        String searchPaperInfo = new String(request.getParameter("searchPaperInfo").getBytes("ISO8859-1"), "UTF-8");
        /*String userInfoKey = new String(request.getParameter("userInfoKey").getBytes("ISO8859-1"), "UTF-8");*/
        String searchState = request.getParameter("searchState");
        String beginDate = request.getParameter("beginDate");
        String overDate = request.getParameter("overDate");
        DataTableReturnObject dro = iPaperService.getTestPageModePlus(dr, searchPaperInfo, searchState, beginDate, overDate);
        return DataTableUtil.transToJsonStr(sEcho, dro);
    }

    /**
     * 查看试卷详情
     *
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/viewPaperDetail", produces = "text/html;charset=UTF-8")
    public String viewPaperDetail(@RequestParam(value = "id") String id, HttpServletRequest request) throws Exception {
        //1：尝试1---通过对象关系,用<c:forech>嵌套展示---false
       /* Paper paper = iPaperService.selectPaperById(id);
        List<PaperQuestion> list = iQuestionPaperService.selectQuestionPaperById(id);*/
        /*PaperDetail paperDetail = new PaperDetail();
        paperDetail.setPaper(paper);
        paperDetail.setPaperQuestion(list);
        request.setAttribute("paper", paper);
        request.setAttribute("paperDetail", paperDetail);*/
        //2:尝试2--@Query表连接----false---理论上可以啊
       /* List<JoinPaperQuestion> list=iPaperService.joinSelectPaperById(id);*/
        //3:尝试3----可以---泪牛满面---true
        //通过PaperDetails手动封装---重点为 List<JoinPaperQuestion>---可以提取到isRight
        PaperDetails paperDetails = iPaperService.getPaperDetails(id);
        request.setAttribute("paperDetails", paperDetails);
        return "fyxmt.testPaper.paperDetail";
    }

    @RequestMapping(value = "/viewPaper", method = RequestMethod.GET)
    @ResponseBody
    public Json viewUser(@RequestParam(value = "id") String id, HttpServletRequest request) {
        Paper paper = iPaperService.getPaperByIdAndState(id);
        Json json = new Json();
        json.setSuccess(false);
        if (paper != null) {
            json.setSuccess(true);
            json.setObj(paper);
        }
        json.setMsg("不能修改未完成的试卷");
        json.setCode("0");
        return json;
    }

    @RequestMapping(value = "/updatePaper")
    public String updatePaper(@ModelAttribute("paper") Paper paper, HttpServletRequest request) {
        iPaperService.updatePaper(paper);
        //return "redirect:"+request.getContextPath()+"/user/userManage";
        return "redirect:/exam/testPaperManage";
    }

    @RequestMapping(value = "/testPaper/producePlus")
    @ResponseBody
    public Json testPaperProduce(HttpSession session) {
        System.out.println("正在生成试卷");
        /*Integer chiceNum = Integer.parseInt(choiceNumString);
        Integer judgeNum = Integer.parseInt(judegNumString);*/
        Integer chiceNum = 25;
        Integer judgeNum = 25;
        User user = (User) session.getAttribute("exitUser");
        System.out.println("该试卷所属用户名为:" + user.getUserName());
        String userName = user.getUserName();
        Json result = new Json();
        result.setSuccess(false);
        result.setMsg("生成试卷出错,请联系监考老师，重新考试");
        result.setCode("-1");
        boolean flag = true;
//            1、没有生成试卷
//            2、生成了试卷但审批通过
//            3、生成了试卷且无审批通过
//       查询当天是否已经生成过试卷
        List<Paper> list = iPaperService.selectTodayPaperById(user.getId());
        if (list.size() > 0 && list != null) {
            //判断是否审批通过
            List<Apply> applyList = iApplyService.selectTodayApplyByUId(user.getId(), 1);
            //不通过
            if (applyList == null || applyList.size() == 0) {
                flag = false;
                result.setMsg("你今天已参与过考试,请改天再考或联系管理员,管理员同意后即可再次考试");
                iApplyService.produceApply(user);//生成申请
            }
        }
        if (flag) {
            Paper paper = iPaperService.produceTestPaperPlus(userName, chiceNum, judgeNum);
            if (paper != null) {
                System.out.println("生成试卷成功了");
                System.out.println("该试卷id为" + paper.getId());
                result.setMsg("生成试卷成功！");
                result.setObj(paper);
                result.setSuccess(true);
                result.setCode("0");
            } else {
                System.out.println("试题不够,生成试卷失败");
                result.setMsg("试题不够,生成试卷失败,请联系管理员");
                result.setSuccess(false);
                result.setCode("-1");
            }
        }
        //说明已申请过并被同意,这是第二次答题
        List<Apply> applyList = iApplyService.selectTodayApplyByUId(user.getId(), 1);
        if (applyList.size() > 0 && applyList != null) {
            for (Apply apply : applyList) {
                if (apply != null) {
                    //删除表中已有的申请
                       /*iApplyService.deleteUserApplyById(paper.getUser().getId());*/
                    iApplyService.deleteUserApplyById(apply.getId());
                }
            }
        }
        return result;
    }

    @RequestMapping(value = "/testPaper/produceBySql")
    @ResponseBody
    public Json testPaperProduceBySql(HttpSession session) {
        System.out.println("正在生成试卷");
        User user = (User) session.getAttribute("exitUser");
        System.out.println("该试卷所属用户名为:" + user.getUserName());
        String userName = user.getUserName();
        Json result = new Json();
        result.setSuccess(false);
        result.setMsg("生成试卷出错,请联系监考老师，重新考试");
        result.setCode("-1");
        boolean flag = true;
        List<Paper> list = iPaperService.selectTodayPaperById(user.getId());
        if (list.size() > 0 && list != null) {
            //判断是否审批通过
            List<Apply> applyList = iApplyService.selectTodayApplyByUId(user.getId(), 1);
            //不通过
            if (applyList == null || applyList.size() == 0) {
                flag = false;
                result.setMsg("你今天已参与过考试,请改天再考或联系管理员,管理员同意后即可再次考试");
                iApplyService.produceApply(user);//生成申请
            }
        }
        if (flag) {
            Paper paper = iPaperService.produceTestPaperBySql(userName);
            if (paper != null) {
                System.out.println("生成试卷成功了");
                System.out.println("该试卷id为" + paper.getId());
                result.setMsg("生成试卷成功！");
                result.setObj(paper);
                result.setSuccess(true);
                result.setCode("0");
            } else {
                System.out.println("试题不够,生成试卷失败");
                result.setMsg("试题不够,生成试卷失败,请联系管理员");
                result.setSuccess(false);
                result.setCode("-1");
            }
        }

//        需求改变,第二次答题不需要重新申请
        //说明已申请过并被同意,这是第二次答题
        /*List<Apply> applyList = iApplyService.selectTodayApplyByUId(user.getId(), 1);
        if (applyList.size() > 0 && applyList != null) {
            for (Apply apply : applyList) {
                if (apply != null) {
                    iApplyService.deleteUserApplyById(apply.getId());
                }
            }
        }*/
        return result;
    }


    @RequestMapping(value = "/deletePaperById")
    public String deleteQuestion(@RequestParam(value = "id") String id, HttpServletRequest request) {
        iPaperService.deletPaperById(id);
        return "fyxmt.testPaper.mange";
    }

    @RequestMapping(value = "/testPaper/submit")
    @ResponseBody
    public Json testPaperSubmit(PaperModel paperModel, HttpSession session) {
        PaperResult paperResult = null;
        if (paperModel != null) {
            paperResult = iQuestionPaperService.testPaperSubmit(paperModel);
        }
        Json json = new Json();
        if (paperResult != null) {
            json.setObj(paperResult);
        }

        if (paperResult != null) {
            json.setSuccess(true);
            json.setMsg("分析试卷成功");
            //session.invalidate();
        } else {
            json.setSuccess(false);
            json.setMsg("分析试卷失败");
        }
        json.setCode("0");
        return json;
    }

    @RequestMapping(value = "/exportPaperExcelPlus")
    public String print(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (request.getParameter(Constants.EXAM_STARTTIME) != null) {
            String startTime = request.getParameter(Constants.EXAM_STARTTIME);
            if (startTime != null && !startTime.equals("")) {
                map.put("startTime", startTime);
            }
        }

        if (request.getParameter(Constants.EXAM_ENDTIME) != null) {
            String endTime = request.getParameter(Constants.EXAM_ENDTIME);
            if (endTime != null && !endTime.equals("")) {
                map.put("endTime", endTime);
            }
        }
        //通用变量
        int rowNo = 0, cellNo = 1;
        Row nRow = null;
        Cell nCell = null;


        //1.读取工作簿
        String webRoot = request.getSession().getServletContext().getRealPath("/");
        String path = webRoot + "resources/templates/export/paper.xls";
        System.out.println(path);
        InputStream is = new FileInputStream(path);
        Workbook wb = new HSSFWorkbook(is);
        //2.读取工作表
        Sheet sheet = wb.getSheetAt(0);
        cellNo = 1;//重置
        //3.创建行对象
        //=========================================大标题=============================
        nRow = sheet.getRow(rowNo++);//读取行对象
        nCell = nRow.getCell(cellNo);
        //设置单元格的内容
        nCell.setCellValue("试卷列表");
        /*nCell.setCellValue(inputDate.replace("-0", "-").replace("-", "年") + "月份出货表");//2015-01   2015-12*/
        //=======================================小标题=================================
        rowNo++;
        //=======================================数据输出=================================================
        nRow = sheet.getRow(rowNo++);//读取第三行
        CellStyle customCellStyle = nRow.getCell(cellNo).getCellStyle();
        CellStyle orderNoCellStyle = nRow.getCell(cellNo++).getCellStyle();
        CellStyle productNoCellStyle = nRow.getCell(cellNo++).getCellStyle();
        CellStyle cNumberCellStyle = nRow.getCell(cellNo++).getCellStyle();
        List<Paper> list = iPaperService.findList(map);//查询出符合指定船期的货物列表
        rowNo = 2;
        for (Paper cp : list) {
            nRow = sheet.createRow(rowNo++);//产生数据行
            nRow.setHeightInPoints(24);//设置行高

            cellNo = 0;
            nCell = nRow.createCell(cellNo++);//产生单元格对象
            nCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cp.getCreateDate()));//创建时间
            nCell.setCellStyle(customCellStyle);//设置文本样式

            nCell = nRow.createCell(cellNo++);//产生单元格对象
            nCell.setCellValue(cp.getUser().getUserName());//身份证号
            nCell.setCellStyle(orderNoCellStyle);//设置文本样式

            nCell = nRow.createCell(cellNo++);//产生单元格对象
            nCell.setCellValue(cp.getUser().getName());     // 姓名
            nCell.setCellStyle(productNoCellStyle);//设置文本样式

            nCell = nRow.createCell(cellNo++);//产生单元格对象
            nCell.setCellValue(cp.getUser().getCompanyName());     // 单位名称
            nCell.setCellStyle(productNoCellStyle);//设置文本样式

            nCell = nRow.createCell(cellNo++);//产生单元格对象
            nCell.setCellValue(cp.getPaperTheme());// 考试主题
            nCell.setCellStyle(cNumberCellStyle);//设置文本样式


            nCell = nRow.createCell(cellNo++);//产生单元格对象
            nCell.setCellValue(cp.getPaperScore());// 试卷分数
            nCell.setCellStyle(cNumberCellStyle);//设置文本样式

            nCell = nRow.createCell(cellNo++);//产生单元格对象
            nCell.setCellValue((cp.getPaperState()==0)?"未提交":"已完成");// 状态
            nCell.setCellStyle(cNumberCellStyle);//设置文本样式

        }
        //======================================输出到客户端（下载）========================================
        DownloadUtil downUtil = new DownloadUtil();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//流  内存中的缓存区
        wb.write(baos);//将excel表格中的内容输出到缓存
        baos.close();//刷新缓存
        String format = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss.ss").format(new Date());
        downUtil.download(baos, response, format+"试卷表.xls");//如果是中文，下载时可能会产生乱码，如何解决？
        return null;
    }



}
