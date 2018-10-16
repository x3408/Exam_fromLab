package com.exam.www.service.impl;


import com.exam.www.entity.DataDictionary;
import com.exam.www.repository.DataDictionaryRepository;
import com.exam.www.service.ISystemService;
import com.exam.www.utils.Constants;
import com.exam.www.utils.DataRequest;
import com.exam.www.utils.DataTableReturnObject;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/2/7.
 */
@Service
public class SystemServiceImpl extends BaseServiceImpl implements ISystemService {
    private  static  final org.slf4j.Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);

    @Autowired
    private DataDictionaryRepository dataDictionaryRepository;

   /* @Autowired
    private CheckCodeRepository checkCodeRepository;*/

    @PersistenceContext
    private EntityManager em;

    public List<DataDictionary> getDataDictionaryByType(String type) {
        //只查找还在使用的
        return dataDictionaryRepository.getDataDictionaryByType(type, Constants.DATA_STATE_ACTIVE);
    }

   /* public CheckCode getCheckCode(String mobilePhone) {
        return checkCodeRepository.findOne(mobilePhone);
    }*/

    /**
     * 验证码保存
     * @param checkCode
     */
  /*  public void saveCheckCode(CheckCode checkCode) {
        //先检查之前是否发送过验证码
        CheckCode updateCheckCode = checkCodeRepository.findOne(checkCode.getMoiblePhone());
        if (updateCheckCode != null) {
            updateCheckCode.setCode(checkCode.getCode());
            updateCheckCode.setSendTime(new Date());
        } else {
            updateCheckCode = new CheckCode();
            updateCheckCode.setMoiblePhone(checkCode.getMoiblePhone());
            updateCheckCode.setCode(checkCode.getCode());
            updateCheckCode.setSendTime(new Date());
        }
        //保存到数据库中
        checkCodeRepository.save(updateCheckCode);
    }*/

    /**
     * 数据字典树
     * @return
     */
    public List<DataDictionary> getDictionaryTree() {
        // select name from data_dictionary where state = '0' or state = '1' group by type
        //获得查询工厂对象
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        //得到安全查询主语句
        CriteriaQuery<Tuple> cq = criteriaBuilder.createQuery(Tuple.class);
        //Root定义from语句中可能出现的类型
        Root<DataDictionary> dataDictionary = cq.from(DataDictionary.class);

        cq.groupBy(dataDictionary.get("name"));
        cq.groupBy(dataDictionary.get("type"));
        //创建查询条件
        Predicate condition1 = criteriaBuilder.equal(dataDictionary.get("state").as(String.class), "0");
        Predicate condition2 = criteriaBuilder.equal(dataDictionary.get("state").as(String.class), "1");
        Predicate predicate = criteriaBuilder.or(condition1, condition2);
//      拼接where语句
        cq.where(predicate);
        //表示select name,type
        CompoundSelection sel = criteriaBuilder.tuple(dataDictionary.get("name"),dataDictionary.get("type") );
        cq.select(sel);
        TypedQuery<Tuple> q = em.createQuery(cq);
        List<Tuple> result = q.getResultList();
        List<DataDictionary> dataDictionaries = new ArrayList<DataDictionary>();
        for(Tuple tuple : result) {
            //对结果进行封装
            DataDictionary data = new DataDictionary();
            data.setName((String) tuple.get(0));
            data.setType((String) tuple.get(1));
            dataDictionaries.add(data);
        }
        return dataDictionaries;
    }

    /**
     * 数据字典根据类型分页查询
     * @param dr
     * @param type
     * @return
     */
    public DataTableReturnObject getDataDictionaryPageMode(DataRequest dr, final String type) {
        DataTableReturnObject j=new DataTableReturnObject();
        String fieldName = dr.getSidx();
        Sort sort = new Sort("asc".equalsIgnoreCase(dr.getSord())? Sort.Direction.ASC: Sort.Direction.DESC, fieldName);
        Pageable pageable = new PageRequest(dr.getPage()-1, dr.getRows(), sort);
        List<JSONObject> list=new ArrayList<JSONObject>();

        Page<DataDictionary> userPage = dataDictionaryRepository.findAll(new Specification<DataDictionary>() {
            @Override
            public Predicate toPredicate(Root<DataDictionary> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (!StringUtils.isEmpty(type)) {
                    Predicate p0 = cb.equal(root.get("type").as(String.class), type);
                    Predicate p = cb.and(p0);
                    list.add(p);
                } else {
                    Predicate p0 = cb.equal(root.get("type").as(String.class), "");
                    Predicate p = cb.and(p0);
                    list.add(p);
                }

                Predicate[] p7 = new Predicate[list.size()];
                query.where(cb.and(list.toArray(p7)));
                return null;
            }
        }, pageable);
        List<DataDictionary> list1=userPage.getContent();
        long counts=userPage.getTotalElements();
        for (DataDictionary dataDictionary : list1) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", dataDictionary.getId());
            jsonObject.put("name", dataDictionary.getName());
            jsonObject.put("code", dataDictionary.getCode());
            jsonObject.put("sort", String.valueOf(dataDictionary.getSort()));
            jsonObject.put("value", dataDictionary.getValue());
            if ("0".equals(dataDictionary.getState())) {
                jsonObject.put("state", "是");
            } else if ("1".equals(dataDictionary.getState())) {
                jsonObject.put("state", "否");
            } else {
                jsonObject.put("state", "");
            }
            list.add(jsonObject);
        }
        j.setAaData(list);
        j.setiTotalDisplayRecords(counts);
        j.setiTotalRecords(counts);
        return j;
    }

    @Override
    public void saveDataDictionary(DataDictionary dataDictionary) {
        dataDictionaryRepository.save(dataDictionary);
    }

    /**
     * 数据字典同一类型的code是否已存在
     * @param type
     * @param code
     * @param id
     * @return
     */
    @Override
    public boolean checkDataDictionaryTypeCodeExist(String type, String code, String id) {
        if (StringUtils.isEmpty(id)) {
            DataDictionary data = dataDictionaryRepository.getDataDictionaryByTypeCode(type, code);
            return data == null ? false : true;
        } else {
            DataDictionary data = dataDictionaryRepository.getDataDictionaryByTypeCode(type, code, Integer.valueOf(id));
            return data == null ? false : true;
        }
    }

    /**
     * 删除数据字典类型
     * @param ids
     */
    @Transactional
    public void deleteDataDictionary(String[] ids) {
        StringBuilder sb = new StringBuilder("delete from data_dictionary where id in (");
        for (String id: ids) {
            sb.append("'");
            sb.append(id);
            sb.append("'");
            sb.append(",");
        }
        if (ids != null && ids.length > 0) {
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append(")");
        Query query = em.createNativeQuery(sb.toString(), DataDictionary.class);
        query.executeUpdate();
    }

    public DataDictionary getDataDictionary(String id) {
        return dataDictionaryRepository.findOne(Integer.valueOf(id));
    }

    /**
     * 更新保存数据字典
     * @param dataDictionary
     */
    public void updateDataDictionary(DataDictionary dataDictionary) {
        DataDictionary updateDataDictionary = dataDictionaryRepository.findOne(dataDictionary.getId());
        if (updateDataDictionary != null) {
            updateDataDictionary.setValue(dataDictionary.getValue());
            updateDataDictionary.setCode(dataDictionary.getCode());
            updateDataDictionary.setDescription(dataDictionary.getDescription());
            updateDataDictionary.setSort(dataDictionary.getSort());
            updateDataDictionary.setState(dataDictionary.getState());
        }
        dataDictionaryRepository.save(updateDataDictionary);
    }
}
