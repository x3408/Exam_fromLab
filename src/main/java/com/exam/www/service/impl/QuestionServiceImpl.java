package com.exam.www.service.impl;


import com.exam.www.entity.PaperQuestion;
import com.exam.www.entity.Question;
import com.exam.www.repository.DataDictionaryRepository;
import com.exam.www.repository.PaperQuestionRepository;
import com.exam.www.repository.QuestionRepository;
import com.exam.www.repository.UserRepository;
import com.exam.www.service.IQuestionService;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
@Transactional
public class  QuestionServiceImpl extends  BaseServiceImpl implements IQuestionService{
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private PaperQuestionRepository paperQuestionRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;


    @Autowired
    private DataDictionaryRepository dataDictionaryRepository;

    @Override
    public DataTableReturnObject getQuestionPageMode(DataRequest dr, String key, String state,String type) {
        try {
            DataTableReturnObject j=new DataTableReturnObject();
            String fieldName = dr.getSidx();
            Sort sort = new Sort("asc".equalsIgnoreCase(dr.getSord())? Sort.Direction.ASC: Sort.Direction.DESC, fieldName);
            Pageable pageable = new PageRequest(dr.getPage()-1, dr.getRows(), sort);
            List<JSONObject> list=new ArrayList<JSONObject>();
            Page<Question> questionPage = questionRepository.findAll(new Specification<Question>() {
                @Override
                public Predicate toPredicate(Root<Question> root,
                                             CriteriaQuery<?> query, CriteriaBuilder cb) {
                    // 逻辑删除标示
                    List<Predicate> list = new ArrayList<Predicate>();
                    /*System.out.println("关键字为:"+key);
                    System.out.println("类型为:"+type);
                    System.out.println("状态为:"+state);*/
                    // 关键字查询
                    if (!StringUtils.isEmpty(key)) {
                        Predicate p1 = cb.like(root.get("questionTitle").as(String.class), "%" + key + "%");
                        Predicate p2 = cb.like(root.get("questionTheme").as(String.class), "%" + key + "%");
                        Predicate p3 = cb.or(p1,p2);
                        list.add(p3);
                    }
                    // 类型查询
                    if (!StringUtils.isEmpty(type)) {
                       Predicate p4=cb.equal(root.get("questionType").as(Integer.class),Integer.parseInt(type));
                        list.add(p4);
                    }
                    //状态查询
                    if (!StringUtils.isEmpty(state)) {
                        Predicate p5 = cb.equal(root.get("questionState").as(Integer.class), state);
                        list.add(p5);
                    }
                    //不加载逻辑删除的试题
                    Predicate p6=cb.notEqual(root.get("questionState").as(Integer.class),2);
                    list.add(p6);
                    Predicate[] p7 = new Predicate[list.size()];
                    query.where(cb.and(list.toArray(p7)));
                    return null;
                }
            }, pageable);
            List<Question> list1=questionPage.getContent();
            long counts=questionPage.getTotalElements();
            for (Question question : list1) {
                JSONObject jsonObject = new JSONObject();
                if(question.getId()!=null){
                    jsonObject.put("id", question.getId());
                }
                if(question.getCreateDate()!=null){
                    jsonObject.put("createDate",question.getCreateDate());
                }
                if(question.getQuestionTitle()!=null){
                    jsonObject.put("questionTitle",question.getQuestionTitle() );
                }
                if(question.getQuestionState()!=null){
                    if (0==question.getQuestionState()) {
                        jsonObject.put("questionState", "<span style=\"color: limegreen\">正常</span>");
                    } else if (1==question.getQuestionState()) {
                        jsonObject.put("questionState", "<span style=\"color: red\">弃用</span>");
                    }
                }
                if(question.getQuestionType()!=null){
                    if(question.getQuestionType()==0){
                        jsonObject.put("questionType","选择题");
                    }else if(question.getQuestionType()==1){
                        jsonObject.put("questionType","判断题");
                    }else if(question.getQuestionType()==2){
                        jsonObject.put("questionType","多选题");
                    }else {
                        jsonObject.put("questionType","其它题型");
                    }
                }else{
                    jsonObject.put("questionType","题型");
                }
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
    /*public void saveQuestion(Question question,String alternativeOption) {
        question.setId(UUID.randomUUID().toString());
        question.setCreateBy(getUser(userRepository) == null ? "" :getUser(userRepository).getId());
        question.setCreateDate(new Date());
        question.setVersion(0);
        question.setAlternativeOption(alternativeOption);//将可选答案以#拼接
        question.setQuestionState(0);
        question.setBaseScope(0);//
        questionRepository.save(question);
    }*/
    public void saveQuestion(Question question){
        question.setId(UUID.randomUUID().toString());
        question.setCreateBy(getUser(userRepository) == null ? "" :getUser(userRepository).getId());
        question.setCreateDate(new Date());
        question.setVersion(0);
        question.setQuestionState(0);
        question.setBaseScope(0);//
        questionRepository.save(question);
    }

    @Override
    public void deleteQuestionById(String id) {
        //先删除中间表对应的记录
        List<PaperQuestion> list=paperQuestionRepository.findPaperQuestionById(id);
        for(PaperQuestion paperQuestion:list){

            paperQuestionRepository.delete(paperQuestion);
        }
        //再删除试题
        questionRepository.delete(id);
    }

    @Override
    public Question getQuestionById(String id) {
        return questionRepository.findOne(id);
    }

    @Override
    public void updateQuestionState(String[] ids, String state) {
        StringBuilder sb = new StringBuilder("update question set questionState=:state where id in (");
        for (String id: ids) {
            sb.append("'");
            sb.append(id);
            sb.append("'");
            sb.append(",");
        }
        if (ids != null && ids.length > 0) {
            sb.deleteCharAt(sb.length()-1);
            System.out.println(ids.length);
        }
        sb.append(")");
        Query query = em.createNativeQuery(sb.toString(), Question.class);
        query.setParameter("state", state);
        query.executeUpdate();

    }

    public void logicDeleteQuestionById(String id){
        Question question=questionRepository.findOne(id);
        question.setQuestionState(2);
        question.setUpdateBy(getUser(userRepository) == null ? "" : getUser(userRepository).getId());
        question.setUpdateDate(new Date());
        questionRepository.save(question);
    }


}