package com.exam.www.service.impl;

import com.exam.www.entity.Material;
import com.exam.www.repository.MaterialRepository;
import com.exam.www.repository.UserRepository;
import com.exam.www.service.IMaterialService;
import com.exam.www.utils.DataRequest;
import com.exam.www.utils.DataTableReturnObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MaterialServiceImpl extends BaseServiceImpl implements IMaterialService {
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveMaterial(Material material, HttpSession session) {
        //补全信息
        material.setId(UUID.randomUUID().toString());
        material.setVersion(0);
        material.setCreateDate(new Date());
        material.setCreateBy(getUser(userRepository) == null ? "" : getUser(userRepository).getId());
        materialRepository.save(material);
    }

    @Override
    public DataTableReturnObject getTestPageModePlus(DataRequest dr, String searchInfo, String beginDate, String overDate) {
        try {
            DataTableReturnObject j = new DataTableReturnObject();
            String fieldName = dr.getSidx();
            Sort sort = new Sort("asc".equalsIgnoreCase(dr.getSord()) ? Sort.Direction.ASC : Sort.Direction.DESC, fieldName);
            Pageable pageable = new PageRequest(dr.getPage() - 1, dr.getRows(), sort);
            List<JSONObject> list = new ArrayList<JSONObject>();
            Page<Material> PaperPage = materialRepository.findAll(new Specification<Material>() {
                @Override
                public Predicate toPredicate(Root<Material> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    // 用户信息查询
                    if (!StringUtils.isEmpty(searchInfo)) {
                        Predicate p1 = cb.like(root.get("materialDescription").as(String.class), "%" + searchInfo + "%");
                        Predicate p2 = cb.like(root.get("materialTheme").as(String.class), "%" + searchInfo + "%");
                        Predicate p3 = cb.and(cb.or(p1, p2));
                        list.add(p3);
                    }

                    if (!StringUtils.isEmpty(beginDate)) {
                        Predicate p5 = cb.greaterThanOrEqualTo(root.get("createDate").as(String.class),beginDate);
                        list.add(p5);
                    }
                    if (!StringUtils.isEmpty(overDate)) {
                        Predicate p6 = cb.lessThanOrEqualTo(root.get("createDate").as(String.class),overDate);
                        list.add(p6);
                    }

                    Predicate[] p7 = new Predicate[list.size()];
                    query.where(cb.and(list.toArray(p7)));
                    return null;
                }
            }, pageable);
            List<Material> list1 = PaperPage.getContent();
            long counts = PaperPage.getTotalElements();
            for (Material material : list1) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", material.getId());
                jsonObject.put("createDate", material.getCreateDate());
                jsonObject.put("materialTheme", material.getMaterialTheme());
                jsonObject.put("materialDescription", material.getMaterialDescription());
                jsonObject.put("materialUrl", material.getMaterialUrl());
                list.add(jsonObject);
            }
            j.setAaData(list);
            j.setiTotalDisplayRecords(counts);
            j.setiTotalRecords(counts);
            return j;
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    @Override
    public void deletMaterialById(String id) {
        materialRepository.delete(id);
    }

    @Override
    public Material findMaterailById(String id) {
        return null;
    }

    @Override
    public Page<Material> findAll(Pageable pageable, HttpSession session) {
        Page pageList = materialRepository.findAll(pageable);
        return pageList;
    }


}
