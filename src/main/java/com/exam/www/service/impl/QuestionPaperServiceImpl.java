package com.exam.www.service.impl;

import com.exam.www.entity.Paper;
import com.exam.www.entity.PaperQuestion;
import com.exam.www.entity.Question;
import com.exam.www.repository.PaperQuestionRepository;
import com.exam.www.repository.PaperRepository;
import com.exam.www.repository.QuestionRepository;
import com.exam.www.service.IQuestionPaperService;
import com.exam.www.utils.PaperModel;
import com.exam.www.utils.PaperResult;
import com.exam.www.utils.PaperSubmit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class QuestionPaperServiceImpl implements IQuestionPaperService {
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private PaperQuestionRepository paperQuestionRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public PaperResult testPaperSubmit(PaperModel model) {
        //先比对答案,将每题得分放入中间表中,通过计算得到总分放入Paper表中,并计算出每类题型的得分
        Paper paper = paperRepository.findOne(model.getPaperId());
        Integer choiceSocreSum = 0;
        Integer packScoreSum = 0;
        for (PaperSubmit paperSubmit : model.getList()) {
            Question question = questionRepository.findOne(paperSubmit.getQuestionId());
            //通过paperId与questionId来找到该记录并修改isRight
//            PaperQuestion paperQuestion=paperQuestionRepository.findByQuestionIdAndPaperId('','');
            PaperQuestion paperQuestion = paperQuestionRepository.findByQuestionIdAndPaperId(question.getId(),model.getPaperId());
            //设置该题的答题状态
            //1、未答题
            if (paperSubmit.getQuestionAnswer() == null||paperSubmit.getQuestionAnswer().equals("")) {
                paperQuestion.setQuestionIsAnswer(0);
                paperQuestion.setIsRight(0);
            }
            if(paperQuestion!=null&&paperSubmit.getQuestionAnswer()!=null&&!paperSubmit.getQuestionAnswer().equals("")) {
                //2、已答题但答题错误
                if (!paperSubmit.getQuestionAnswer().equals(question.getRightAnswer())) {
                    paperQuestion.setQuestionIsAnswer(1);
                    paperQuestion.setIsRight(0);
                    paperQuestion.setPersonAnswer(paperSubmit.getQuestionAnswer());
                }
                //3、已答题且答题正确
                if (question.getRightAnswer().equals(paperSubmit.getQuestionAnswer())) {
                    paperQuestion.setQuestionIsAnswer(1);
                    paperQuestion.setIsRight(1);
                    paperQuestion.setPersonAnswer(paperSubmit.getQuestionAnswer());
                    //计算各题型分数
                    if (question.getQuestionType() == 0) {
                        choiceSocreSum += 2;
                    } else if (question.getQuestionType() == 1) {
                        packScoreSum += 2;
                    } else {
                        //其它题型
                    }
                }
            }

        }
        paper.setPaperScore(choiceSocreSum + packScoreSum);
        paper.setRightNum((choiceSocreSum+packScoreSum)/2);
        paper.setPaperState(1);
        paper.setUpdateDate(new Date());
        paperRepository.save(paper);
        PaperResult paperResult = new PaperResult();
        paperResult.setBaseSum(paper.getPaperSum());
        paperResult.setPaperScore(paper.getPaperScore());
        paperResult.setChoiceScore(choiceSocreSum);
        paperResult.setPackScore(packScoreSum);
        return paperResult;
    }

    @Override
    public List<PaperQuestion> selectQuestionPaperById(String id) {
        List<PaperQuestion> list = paperQuestionRepository.findOneByPaperId(id);
        return list;
    }


}
