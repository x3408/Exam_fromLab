package com.exam.www.service;

import com.exam.www.entity.PaperQuestion;
import com.exam.www.utils.JoinPaperQuestion;
import com.exam.www.utils.PaperModel;
import com.exam.www.utils.PaperResult;

import java.util.List;

/*
中间表接口
 */
public interface IQuestionPaperService {
    /*
   生成成绩信息
    */
    public PaperResult testPaperSubmit(PaperModel model);
    /*
    根据试卷d查询中间表
     */
    public List<PaperQuestion> selectQuestionPaperById(String id);

}
