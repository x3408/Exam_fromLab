package com.exam.www.service.impl;


import com.exam.www.entity.*;
import com.exam.www.repository.*;
import com.exam.www.service.IPaperService;
import com.exam.www.utils.DataRequest;
import com.exam.www.utils.DataTableReturnObject;
import com.exam.www.utils.JoinPaperQuestion;
import com.exam.www.utils.PaperDetails;
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
import java.util.*;

/**
 * Created by admin on 2016/10/11.
 */
@Service("PaperService")
public class PaperServiceImpl extends BaseServiceImpl implements IPaperService {


    @PersistenceContext
    private EntityManager em;
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaperQuestionRepository paperQuestionRepository;
    @Autowired
    private DataDictionaryRepository dataDictionaryRepository;
    private static  final int CHOICENUM=20;
    private static  final int MULTINUM=5;
    private static  final int FILLNUM=20;




    @Override
    public DataTableReturnObject getTestPageModePlus(DataRequest dr, String searchPaperInfo, String state, String beginDate, String overDate) {
        try {
            DataTableReturnObject j = new DataTableReturnObject();
            String fieldName = dr.getSidx();
            Sort sort = new Sort("asc".equalsIgnoreCase(dr.getSord()) ? Sort.Direction.ASC : Sort.Direction.DESC, fieldName);
            Pageable pageable = new PageRequest(dr.getPage() - 1, dr.getRows(), sort);
            List<JSONObject> list = new ArrayList<JSONObject>();

            Page<Paper> PaperPage = paperRepository.findAll(new Specification<Paper>() {
                @Override
                public Predicate toPredicate(Root<Paper> root,
                                             CriteriaQuery<?> query, CriteriaBuilder cb) {

                    List<Predicate> list = new ArrayList<Predicate>();
                    // 用户信息查询
                    if (!StringUtils.isEmpty(searchPaperInfo)) {
                        Predicate p1 = cb.like(root.get("paperTilte").as(String.class), "%" + searchPaperInfo + "%");
                        Predicate p2 = cb.like(root.get("paperTheme").as(String.class), "%" + searchPaperInfo + "%");
                        Predicate p3 = cb.and(cb.or(p1, p2));
                        list.add(p3);
                    }
                    // 状态查询
                    if (!StringUtils.isEmpty(state)) {
                        Predicate p4 = cb.equal(root.get("paperState").as(String.class), state);
                        list.add(p4);
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
            List<Paper> list1 = PaperPage.getContent();
            long counts = PaperPage.getTotalElements();
            List<DataDictionary> PaperState = dataDictionaryRepository.getDataDictionaryByType("TESTPAPER_STATE");
            Map<String, String> map = new HashMap<String, String>();
            for (DataDictionary data : PaperState) {
                map.put(data.getCode(), data.getValue());
            }
            for (Paper paper : list1) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", paper.getId());
                jsonObject.put("createDate", paper.getCreateDate());
                jsonObject.put("userName", paper.getUser().getUserName());
                jsonObject.put("name",paper.getUser().getName());
                jsonObject.put("companyName",paper.getUser().getCompanyName());
                jsonObject.put("paperTheme", paper.getPaperTheme());
                jsonObject.put("rightNum", paper.getRightNum());
                Integer integer = paper.getPaperState();
                String stateString = integer.toString();
                if (0 == integer) {
                    jsonObject.put("paperScore", 0);
                    jsonObject.put("searchState", "<span style=\"color: limegreen\">" + map.get(stateString) + "</span>");
                } else if (1 == integer) {
                    jsonObject.put("paperScore",paper.getPaperScore());
                    jsonObject.put("searchState", "<span style=\"color: red\">" + map.get(stateString) + "</span>");
                } else {
                    jsonObject.put("paperScore",paper.getPaperScore());
                    jsonObject.put("searchState", map.get(stateString));
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
    public synchronized  Paper produceTestPaperPlus(String userName, Integer choiceNum, Integer fillNum) {
        //先生成一个试卷对象,补全相关信息
        Paper paper=null;
        //先查看此时数据库中各题型的数量,然后判断是否够生成具有指定数量的试卷
        Integer cNum=questionRepository.getQuestionSumByIdAndStateAndType(2,0);
        Integer jNum=questionRepository.getQuestionSumByIdAndStateAndType(2,1);
            if (cNum >= choiceNum && jNum >= fillNum) {
                paper = new Paper();
                paper.setId(UUID.randomUUID().toString());
                paper.setPaperTheme("管理员考试");
                paper.setPaperTilte("食品安全管理员考试");
                paper.setPaperState(0);//未提交
                paper.setRightNum(0);
                //获取考生信息
                User user = userRepository.findByUserName(userName);
                paper.setUser(user);
                paper.setPaperScore(0);
                paper.setPaperSum(100);
                paper.setCreateDate(new Date());
                paper.setVersion(0);
                // 遍历试题集合,通过type判断题型,从而选择questionId到数组中
                String ids[] = questionRepository.getQuestionIds();
                List<String> choiceList = new ArrayList<>(0);
                List<String> fillList = new ArrayList<>(0);
                long t = System.currentTimeMillis();//获得当前时间的毫秒数
                Random random = new Random(t);//作为种子数传入到Random的构造器中
                Integer sum = 0;
                boolean flag = true;
                while (flag) {
                    boolean b = false;
                    //先产生一个随机数,然后判断它所对应的类型
                    Integer randomIndex = random.nextInt(ids.length);
//                  Question question = questionRepository.findQuestionById(ids[randomIndex]);
                    //查询得到的问题不能为逻辑删除(2)的问题
                    Question question = questionRepository.findQuestionByIdAndState(ids[randomIndex], 2);
                    //对不同体型抽取不同数量到对应的数组内
                    if(question!=null) {
                        if (question.getQuestionType() == 0 && choiceList.size() < choiceNum) {
                            //判断是否有重复的
                            for (String string : choiceList) {
                                if (string.equals(question.getId())) {
                                    b = true;
                                }
                            }
                            //说明不重复,则放入列表中
                            if (!b) {
                                choiceList.add(question.getId());
                            }
                        } else if (question.getQuestionType() == 1 && fillList.size() < fillNum) {
                            //判断是否有重复的
                            for (String string : fillList) {
                                if (string.equals(question.getId())) {
                                    b = true;
                                }
                            }
                            //说明不重复,则放入列表中
                            if (!b) {
                                fillList.add(question.getId());
                            }
                        } else {
                            //其它类型
                        }
                    }
                    sum = choiceList.size() + fillList.size();
                    if (sum == 50) {
                        //控制循环结束
                        flag = false;
                    }
                }

                List<String> resultIdList = new ArrayList<>(0);
                List<Question> questionList = new ArrayList<>(0);
                for (int i = 0; i < sum; i++) {
                    if (i < choiceList.size()) {
//                System.out.println(choiceList.get(i));
                        resultIdList.add(choiceList.get(i));
                    } else {
//                System.out.println(fillList.get(i-choiceList.size()));
                        resultIdList.add(fillList.get(i - choiceList.size()));
                    }
                }
                //将结果对应的id转为question对象并放入集合中,通过保存paper(method1:可行---适用情况:question与paper中间表中只有questionId与paperId)
                if (resultIdList != null && resultIdList.size() > 0) {
                    for (String string : resultIdList) {
                        Question question = questionRepository.findQuestionById(string);
                        questionList.add(question);
                    }
                }
                paper.setQuestions(questionList);
                paper.setChoiceNum(choiceNum);
                paper.setJudgeNum(fillNum);
                paperRepository.save(paper);
            }
       /* for(int i=0;i<5;i++) {
            PaperQuestion paperQuestion = new PaperQuestion();
            paperQuestion.setPaper(paper);
            paperQuestion.setQuestion(questionList.get(i));
            paperQuestionRepository.save(paperQuestion);
        }*/
//        method2:借助中间表对象进行操做
     /*   paperRepository.insertInto(paper);*/
        return paper;
    }

    @Override
    public void deletPaperById(String id) {
        //1、删除中间表---失败
       /* Paper  paper=paperRepository.findOne(id);
        List<Question> list=paper.getQuestions();
        for(Question question:list){
            if(question!=null){
                question.getPapers().remove(paper);
                paper.getQuestions().remove(question);
            }
        }*/
       /* paperRepository.save(paper);*/
        //2、用sql删除--待定
        // 3、通过配置级联关系来删除
        paperRepository.delete(id);
    }


    @Override
    public Paper selectPaperById(String id) {
        return paperRepository.findOne(id);
    }

    /*@Override
    public List<JoinPaperQuestion> joinSelectPaperById(String id) {
        return paperRepository.joinSelectPaperById(id);
    }*/

    @Override
    public PaperDetails getPaperDetails(String id) {
        //拼接用于展示试卷详情的对象----PaperDetails
        Paper paper = paperRepository.findOne(id);
        List<PaperQuestion> list = paperQuestionRepository.findOneByPaperId(id);
        //物理删除试题的后果---解决方案---判断是否能够找到该问题---输出提示信息or不
        List<JoinPaperQuestion> joinList = new ArrayList<>(0);
        for (Question question : paper.getQuestions()) {
            for (PaperQuestion paperQuestion : list) {
                //判断Question与中间表的连接
                if (question == paperQuestion.getQuestion()&&question!=null) {
                    JoinPaperQuestion joinPaperQuestion = new JoinPaperQuestion();
                    joinPaperQuestion.setId(question.getId());
                    joinPaperQuestion.setAlternativeOption1(question.getAlternativeOption1());
                    joinPaperQuestion.setAlternativeOption2(question.getAlternativeOption2());
                    joinPaperQuestion.setAlternativeOption3(question.getAlternativeOption3());
                    joinPaperQuestion.setAlternativeOption4(question.getAlternativeOption4());
                    joinPaperQuestion.setBaseScope(question.getBaseScope());
                    joinPaperQuestion.setQuestionState(Integer.valueOf(question.getQuestionState()));
                    joinPaperQuestion.setQuestionTheme(question.getQuestionTheme());
                    joinPaperQuestion.setQuestionTitle(question.getQuestionTitle());
                    joinPaperQuestion.setQuestionType(question.getQuestionType());
                    joinPaperQuestion.setRightAnswer(question.getRightAnswer());
                    joinPaperQuestion.setIsRight(paperQuestion.getIsRight());//重点
                    joinPaperQuestion.setPersonAnswer(paperQuestion.getPersonAnswer());//重点
                    joinList.add(joinPaperQuestion);
                }
            }
        }
        PaperDetails paperDetails = new PaperDetails();
        paperDetails.setList(joinList);
        paperDetails.setPaper(paper);
        return paperDetails;
    }

    @Override
    public Paper getPaperByIdAndState(String id) {
        return paperRepository.getPaperByIdAndState(id);
    }

    @Override
    public void updatePaper(Paper paper) {
        paper.setUpdateBy(getUser(userRepository) == null ? "" : getUser(userRepository).getId());
        paper.setUpdateDate(new Date());
        paperRepository.updatePaperById(paper.getPaperScore(),paper.getId());
    }

    @Override
    public List<Paper> selectTodayPaperById(String id) {
        /*  java.util.Date date=new java.util.Date();
        String hql="select i from paper i where to_char(i.createDate,'yyyy-MM-dd') =  to_char(?,yyyy-MM-dd)";
        Query query = em.createNativeQuery(hql, Paper.class);
        query.setParameter(1,date, TemporalType.DATE);*/
        List<Paper> list=paperRepository.selectTodayPaperById(id);
        return list;
    }

    @Override
    public Paper produceTestPaperBySql(String userName) {
        Paper paper = null;
        List<Question> choiceList = new ArrayList<>(0);
        List<Question> fillList = new ArrayList<>(0);
        List<Question> multiList = new ArrayList<>(0);
        choiceList = questionRepository.selectRandomQuestionByType(0,20);
        fillList = questionRepository.selectRandomQuestionByType(1,20);
        multiList = questionRepository.selectRandomQuestionByType(2, 5);
        List<Question> resultList = new ArrayList<>(0);
        if (choiceList != null && choiceList.size() > 0) {
            for (Question question : choiceList) {
                resultList.add(question);
            }
        }
        if (multiList != null && multiList.size() > 0) {
            for (Question question : multiList) {
                resultList.add(question);
            }
        }
        if (fillList != null && fillList.size() > 0) {
            for (Question question : fillList) {
                resultList.add(question);
            }
        }
        if (resultList != null && resultList.size() > 0) {
            paper=new Paper();
            paper.setId(UUID.randomUUID().toString());
            paper.setPaperTheme("作业人员考试");
            paper.setPaperTilte("食品安全作业人员考试");
            paper.setPaperState(0);//未提交
            paper.setRightNum(0);
            //获取考生信息
            User user = userRepository.findByUserName(userName);
            paper.setUser(user);
            paper.setPaperScore(0);
            paper.setPaperSum(100);
            paper.setCreateDate(new Date());
            paper.setVersion(0);
            paper.setQuestions(resultList);
            paper.setChoiceNum(CHOICENUM);
            paper.setMultiNum(MULTINUM);
            paper.setJudgeNum(FILLNUM);
            paperRepository.save(paper);
        }
        return paper;
    }

    @Override
    public List<Paper> findList(Map<String, Object> map) {
        StringBuilder sql = new StringBuilder("select * from paper where 1=1");
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equals("startTime")) {
                    if (entry.getValue() != null) {
                        sql.append(" and createDate>= '" + entry.getValue().toString() + "'");
                    }
                } else if (entry.getKey().equals("endTime")) {
                    if (entry.getValue() != null) {
                        sql.append(" and createDate<= '" + entry.getValue().toString() + "'");
                    }
                }
            }
        }
        Query query = em.createNativeQuery(sql.toString(), Paper.class);
        List<Paper> list = query.getResultList();
        return list;
    }
}
