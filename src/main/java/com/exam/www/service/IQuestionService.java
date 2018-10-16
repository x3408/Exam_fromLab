package com.exam.www.service;


import com.exam.www.entity.Question;
import com.exam.www.utils.DataRequest;
import com.exam.www.utils.DataTableReturnObject;

/**
 * Created by admin on 2016/10/11.
 */
public interface IQuestionService {
    /*获取试题列表*/
    public DataTableReturnObject getQuestionPageMode(DataRequest dr, final String key, final String state, final String type);
    /*添加试题*/
    /*public void saveQuestion(Question question,String alternativeOption);*/
    public void saveQuestion(Question question);
    /*根据id查询试题*/
    public Question getQuestionById(String id);
    /*根据id删除试题*/
    public void deleteQuestionById(String id);
    /*更新状态*/
    public void updateQuestionState(String ids[], String state);
    /*
    逻辑删除试题
     */
    public void logicDeleteQuestionById(String id);

}
