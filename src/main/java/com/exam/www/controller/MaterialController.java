package com.exam.www.controller;


import com.exam.www.entity.Material;
import com.exam.www.service.IMaterialService;
import com.exam.www.utils.DataRequest;
import com.exam.www.utils.DataTableReturnObject;
import com.exam.www.utils.DataTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class MaterialController extends BaseController {
    @Autowired
    private IMaterialService materialService;

    @RequestMapping(value = "/materialManage", method = RequestMethod.GET)
    public String meterialManage() {
        return "fyxmt.material.mange";
    }

    @RequestMapping(value = "/saveMaterial", method = RequestMethod.POST)
    public String saveActivity(@ModelAttribute("material") Material material,
                               HttpSession session) {
        if (material != null && !material.getMaterialUrl().trim().equals("") && !material.getMaterialDescription().trim().equals("")
                && !material.getMaterialTheme().trim().equals("")) {
            materialService.saveMaterial(material,session);
        }
        return "redirect:/user/materialManage";
    }

    @RequestMapping(value = "/materialManage/paging", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String paperManagePaging(HttpServletRequest request) throws Exception {
        String sEcho = request.getParameter("sEcho");
        DataRequest dr = DataTableUtil.trans(request);
        String searchInfo = new String(request.getParameter("searchInfo").getBytes("ISO8859-1"), "UTF-8");
        String beginDate = request.getParameter("beginDate");
        String overDate = request.getParameter("overDate");
        DataTableReturnObject dro = materialService.getTestPageModePlus(dr, searchInfo, beginDate, overDate);
        return DataTableUtil.transToJsonStr(sEcho, dro);
    }


    @RequestMapping(value = "/deleteMaterialById")
    public String deleteQuestion(@RequestParam(value = "id") String id, HttpServletRequest request) {
        //先删除对应的文件
        Material material=materialService.findMaterailById(id);

        //再删除该记录
        materialService.deletMaterialById(id);
        return "fyxmt.material.mange";
    }
}
