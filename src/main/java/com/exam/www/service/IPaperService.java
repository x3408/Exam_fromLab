package com.exam.www.service;


import com.exam.www.entity.Paper;
import com.exam.www.utils.DataRequest;
import com.exam.www.utils.DataTableReturnObject;
import com.exam.www.utils.PaperDetails;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/10/11.
 */
public interface IPaperService {


    /*
    得到试卷条件分页列表
     */
    public DataTableReturnObject getTestPageModePlus(DataRequest dr, final String searchUserInfo, final String state, final String beginDate, final String overDate);
    /*
    生成试卷
     */
//    public Paper produceTestPaper(String id);

    /*
     生成试卷PLUS
     */
    public Paper produceTestPaperPlus(String userName, Integer choiceNum, Integer fillNum);

    /*
    根据id删除试卷
     */
    public void deletPaperById(String id);

    /*
    根据试卷Id查找试卷详细信息
     */
    public Paper selectPaperById(String id);

    /*
   连接查询（根据paperId将paper表和中间表连接起来)
    */
    /*public List<JoinPaperQuestion> joinSelectPaperById(String id);*/
    public PaperDetails getPaperDetails(String id);

    /*
    根据id查询已完成的试卷
     */
    public Paper getPaperByIdAndState(String id);

    /*
    根据id更新试卷成绩
     */
    public void updatePaper(Paper paper);

    /*
    根据id来查询当天是否已有生成的试卷
     */
    public List<Paper> selectTodayPaperById(String id);

    /*
    通过sql来随机生成试卷
     */
    public Paper produceTestPaperBySql(String userName);

    /*
查询出符合要求的试卷列表
     */
    public List<Paper> findList(Map<String,Object> map);
}
