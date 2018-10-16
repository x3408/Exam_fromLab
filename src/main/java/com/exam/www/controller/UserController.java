package com.exam.www.controller;


import com.exam.www.dto.UserDTO;
import com.exam.www.entity.User;
import com.exam.www.service.IUserService;
import com.exam.www.utils.*;
import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.jxls.common.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/2/14.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;


    @RequestMapping(value = "/userManage/paging", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String userManagePaging(HttpServletRequest request,
                                   HttpSession session) throws Exception {
        String sEcho = request.getParameter("sEcho");
        DataRequest dr = DataTableUtil.trans(request);
        String searchUserInfo = new String(request.getParameter("searchUserInfo").getBytes("ISO8859-1"), "UTF-8");
        String searchState = request.getParameter("searchState");
        String beginDate = request.getParameter("beginDate");
        String overDate = request.getParameter("overDate");
        DataTableReturnObject dro = userService.getUserPageMode(dr, searchUserInfo, searchState, session, beginDate, overDate);
        return DataTableUtil.transToJsonStr(sEcho, dro);
    }

    @RequestMapping(value = "/userManage", method = RequestMethod.GET)
    public String userManage(HttpServletRequest request,
                             HttpServletResponse response) {
        return "fyxmt.user.mange";
    }

    @RequestMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        user.setState("1");
        return "fyxmt.addUser.mange";
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user,
                           HttpServletRequest request) {
//        保存的均为普通用户
//        userService.saveUser(user);
        User existUser = userService.findUserByUsername(user.getUserName());
        if (existUser != null) {
            return "redirect:/user/userManage";
        }
        userService.save(user);
        addMessage(request, "保存成功");
        return "redirect:/user/userManage";
    }

    @RequestMapping(value = "/selectDuplicateUser", method = RequestMethod.GET)
    @ResponseBody
    public Json selectDuplicateUser(@RequestParam(value = "userName") String userName, HttpServletRequest request) {
        Json json = new Json();
        User user = userService.findUserByUsername(userName);
        if (user == null) {
            json.setSuccess(true);
        } else {
            json.setSuccess(false);
            json.setMsg("该用户已存在！");
        }
        return json;
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    @ResponseBody
    public Json deleteUser(@RequestParam(value = "ids") String[] ids, HttpServletRequest request) throws IOException {
        userService.deleteUser(ids);
        Json j = new Json();
        j.setSuccess(true);
        j.setMsg("删除成功！");
        return j;
    }



    /*@RequestMapping(value = "/viewUser", method = RequestMethod.GET)
    public String viewUser(@RequestParam(value = "id") String id, HttpServletRequest request) {
        User user = userService.getUser(id);
        request.setAttribute("user", user);
        return "fyxmt.updateUser.mange";
    }*/

    @RequestMapping(value = "/viewUser", method = RequestMethod.GET)
    @ResponseBody
    public Json viewUser(@RequestParam(value = "id") String id, HttpServletRequest request) {
        User user = userService.getUser(id);
        Json json = new Json();
        json.setSuccess(false);
        if (user != null) {
            json.setSuccess(true);
            json.setObj(user);
        }
        json.setCode("0");
        return json;
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") User user, HttpServletRequest request) {
        userService.updateUser(user);
        //return "redirect:"+request.getContextPath()+"/user/userManage";
        return "redirect:/user/userManage";
    }

    @RequestMapping(value = "/updateUserState", method = RequestMethod.GET)
    @ResponseBody
    public Json updateUserState(@RequestParam(value = "ids") String[] ids,
                                @RequestParam(value = "state") String state,
                                HttpServletRequest request) throws IOException {
        userService.updateUserState(ids, state);
        Json j = new Json();
        j.setSuccess(true);
        j.setMsg("更新成功！");
        return j;
    }

    @RequestMapping(value = "/resetUserPassword", method = RequestMethod.GET)
    @ResponseBody
    public Json resetUserPassword(@ModelAttribute("id") String id, HttpServletRequest request) {
        userService.resetUserPassword(id);
        Json j = new Json();
        j.setSuccess(true);
        j.setMsg("重置成功！");
        return j;
    }

    @RequestMapping(value = "/selectExistUser", method = RequestMethod.GET)
    @ResponseBody
    public Json selectExistUser(HttpSession session) throws IOException {
        User user = userService.selectExistUser(session);
        Json j = new Json();
        j.setSuccess(true);
        j.setObj(user);
        j.setMsg("");
        return j;
    }

    @RequestMapping(value = "/exportUserExcel", method = RequestMethod.GET)
    @ResponseBody
    public Json exportUserExcel(HttpServletRequest request, HttpServletResponse response) {
        logger.info("exportUserExcel->start");
        Json json = new Json();
        Map<String, Object> map = new HashMap<String, Object>();

        if (request.getSession().getAttribute(Constants.SESSION_USER_CREATEUSER_STARTTIME) != null) {
            String createOrder_startTime
                    = request.getSession().getAttribute(Constants.SESSION_USER_CREATEUSER_STARTTIME).toString();
            if (createOrder_startTime != null && !createOrder_startTime.equals("")) {
                createOrder_startTime = createOrder_startTime + " 00:00:00";
                map.put("createOrder_startTime", createOrder_startTime);
            }
        }

        if (request.getSession().getAttribute(Constants.SESSION_USER_CREATEUSER_ENDTIME) != null) {

            String createOrder_endTime
                    = request.getSession().getAttribute(Constants.SESSION_USER_CREATEUSER_ENDTIME).toString();
            if (createOrder_endTime != null && !createOrder_endTime.equals("")) {
                createOrder_endTime = createOrder_endTime + " 24:00:00";
                map.put("createOrder_endTime", createOrder_endTime);
            }
        }
        String userDirection = null;
        String userProperty = null;
         /*if (request.getSession().getAttribute(Constants.SESSION_USER_SORTDIRECTION) != null) {
            userDirection = request.getSession().getAttribute(Constants.SESSION_USER_SORTDIRECTION).toString();
        }
        if (request.getSession().getAttribute(Constants.SESSION_USER_SORTPROPERTY) != null) {
            userProperty = request.getSession().getAttribute(Constants.SESSION_USER_SORTPROPERTY).toString();
        }*/
        List<User> list = null;
        try {
            //查找全部用户数据
            list = userService.findUserList(-100, -100, userProperty, userDirection, map);
            //List<Order> list = iOrderService.findAllOrderList();
            if (list == null || list.size() == 0) {
                json.setSuccess(false);
                json.setMsg("导出内容为空！");
                return json;
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("findOrderList->e:" + e.toString());
            json.setSuccess(false);
            json.setCode("101");
            json.setMsg("导出失败，获取全部用户列表错误！");
            return json;
        }
        try {
            List<UserDTO> listDTO = Lists.transform(list, UserDTO.couseEnrollUsers);
            Context context = new Context();
            context.putVar("users", listDTO);
           /* List<Context> objects = new ArrayList<Context>();
            objects.add(context);*/
            String webRoot = request.getSession().getServletContext().getRealPath("/");
            String srcFilePath = webRoot + "resources\\templates\\export\\user_template.xls";
            String excelUrl = ExportUtil.exportExcel(context, srcFilePath, request, response);
            json.setSuccess(true);
            json.setMsg("导出成功！");
            json.setCode("0");
            json.setObj(excelUrl);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
          /*  String fullError = getFullStackTrace(e);
            logger.error("exportOrderExcel->e:" + fullError);*/
            json.setSuccess(false);
            json.setCode("102");
            json.setMsg("导出失败，导出错误！");
            return json;
        }


    }

    @RequestMapping(value = "/exportUserExcelPlus")
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
        String path = webRoot + "resources/templates/export/user.xls";
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
        nCell.setCellValue("用户列表");
        /*nCell.setCellValue(inputDate.replace("-0", "-").replace("-", "年") + "月份出货表");//2015-01   2015-12*/
        //=======================================小标题=================================
        rowNo++;
        //=======================================数据输出=================================================
        nRow = sheet.getRow(rowNo++);//读取第三行
        CellStyle customCellStyle = nRow.getCell(cellNo).getCellStyle();
        CellStyle orderNoCellStyle = nRow.getCell(cellNo++).getCellStyle();
        CellStyle productNoCellStyle = nRow.getCell(cellNo++).getCellStyle();
        CellStyle cNumberCellStyle = nRow.getCell(cellNo++).getCellStyle();
        List<User> list = userService.findList(map);//查询出符合指定船期的货物列表
        rowNo = 2;
        for (User cp : list) {
            nRow = sheet.createRow(rowNo++);//产生数据行
            nRow.setHeightInPoints(24);//设置行高

            cellNo = 0;
            nCell = nRow.createCell(cellNo++);//产生单元格对象
            if(cp.getCreateDate()!=null) {
                nCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cp.getCreateDate()));//创建时间
            }else{
                nCell.setCellValue("");
            }
            nCell.setCellStyle(customCellStyle);//设置文本样式

            nCell = nRow.createCell(cellNo++);//产生单元格对象
            nCell.setCellValue(cp.getUserName());//身份证号
            nCell.setCellStyle(orderNoCellStyle);//设置文本样式

            nCell = nRow.createCell(cellNo++);//产生单元格对象
            nCell.setCellValue(cp.getName());     // 姓名
            nCell.setCellStyle(productNoCellStyle);//设置文本样式


            nCell = nRow.createCell(cellNo++);//产生单元格对象
            nCell.setCellValue(cp.getCompanyName());// 公司名
            nCell.setCellStyle(cNumberCellStyle);//设置文本样式

        }
        //======================================输出到客户端（下载）========================================
        DownloadUtil downUtil = new DownloadUtil();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//流  内存中的缓存区
        wb.write(baos);//将excel表格中的内容输出到缓存
        baos.close();//刷新缓存
        String format = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss.ss").format(new Date());
        downUtil.download(baos, response, format+"用户表.xls");//如果是中文，下载时可能会产生乱码，如何解决？
        return null;
    }





/*
    @RequestMapping(value = "/checkMobilePhoneExist", method = RequestMethod.GET)
    @ResponseBody
    public Json checkMobilePhoneExist(@ModelAttribute("tel") String tel, @ModelAttribute("id") String id) {
        Json json=new Json();
        User user = userService.findUserByMobilePhone(tel, id);
        if (user == null) {
            json.setSuccess(true);
        } else {
            json.setSuccess(false);
            json.setMsg("该手机号码已存在！");
        }
        return json;
    }

    @RequestMapping(value = "/dataDictionary", method = RequestMethod.GET)
    public String dataDictionary() {
        return "fyxmt.dataDictionary.mange";
    }



    @RequestMapping(value = "/dataDictionary/paging", produces="text/html;charset=UTF-8")
    @ResponseBody
    public String pageSelect(HttpServletRequest request) throws Exception {
        String sEcho = request.getParameter("sEcho");
        DataRequest dr = DataTableUtil.trans(request);
        String type = request.getParameter("type");
        DataTableReturnObject dro = systemService.getDataDictionaryPageMode(dr, type);
        return DataTableUtil.transToJsonStr(sEcho, dro);
    }

    @RequestMapping("/addDataDictionary")
    public String addDataDictionary(@ModelAttribute("dataDictionary") DataDictionary dataDictionary, HttpServletRequest request) {
        String type = request.getParameter("type");
        dataDictionary.setType(type);
        return "fyxmt.addDataDictionary.mange";
    }



    @RequestMapping(value = "/checkCodeExist", method = RequestMethod.GET)
    @ResponseBody
    public Json checkCodeExist(@RequestParam(value = "type") String type,
                                @RequestParam(value = "code") String code,
                               @RequestParam(value = "id", required = false) String id) {
        Json j = new Json();
        boolean exist = systemService.checkDataDictionaryTypeCodeExist(type, code, id);
        j.setSuccess(exist);
        return j;
    }

    @RequestMapping(value = "/deleteDataDictionary", method = RequestMethod.GET)
    @ResponseBody
    public Json dataDictionary(@RequestParam(value = "ids") String[] ids, HttpServletRequest request) throws IOException {
        systemService.deleteDataDictionary(ids);
        Json j=new Json();
        j.setSuccess(true);
        j.setMsg("删除成功！");
        return j;
    }

    @RequestMapping(value = "/viewDataDictionary", method = RequestMethod.GET)
    public String viewDataDictionary(@RequestParam(value = "id") String id, HttpServletRequest request) {
        DataDictionary dataDictionary = systemService.getDataDictionary(id);
        request.setAttribute("dataDictionary", dataDictionary);
        return "fyxmt.updateDataDictionary.mange";
    }

    @RequestMapping(value = "/updateDataDictionary", method = RequestMethod.POST)
    public String updateDataDictionary(@ModelAttribute("dataDictionary") DataDictionary dataDictionary, HttpServletRequest request) {
        systemService.updateDataDictionary(dataDictionary);
        //return "redirect:"+request.getContextPath()+"/user/dataDictionary";
        request.setAttribute("dataType", dataDictionary.getType());
        return "redirect:/user/dataDictionary";
    }*/
}
