package com.exam.www.service;

import com.exam.www.entity.Material;
import com.exam.www.utils.DataRequest;
import com.exam.www.utils.DataTableReturnObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;

public interface IMaterialService {
    //保存资料
    public void saveMaterial(Material material, HttpSession session);

    //dataTable分页显示
    public DataTableReturnObject getTestPageModePlus(DataRequest dr, final String searchInfo, final String beginDate, final String overDate);

    //根据id删除资料
    public void deletMaterialById(String id);

    //根据id找到资料
    public Material findMaterailById(String id);

    //查询所有资料
    public  Page<Material> findAll(Pageable paramPageable, HttpSession paramHttpSession);
}
