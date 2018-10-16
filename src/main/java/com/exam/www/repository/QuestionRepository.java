package com.exam.www.repository;


import com.exam.www.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, String> {

    @Query("from Question where content=:QuestionName")
    public Question findByQuestionName(@Param("QuestionName") String QuestionName);

    public Page<Question> findAll(Specification<Question> spec, Pageable pageable);


    @Query("select  id from Question ")
    public String[] getQuestionIds();

    @Query("from Question  where id=:id")
    public Question findQuestionById(@Param("id") String id);

    @Query("select count(*) from Question ")
    public Integer getSumNum();

    @Query("from Question where id=:id and questionState!=:state")
    public Question findQuestionByIdAndState(@Param("id") String is, @Param("state") Integer state);

    @Query("select count(*) from Question where  questionState !=:state and questionType=:questionType")
    public Integer getQuestionSumByIdAndStateAndType(@Param("state") Integer state,@Param("questionType") Integer questionType);

    @Query(value = "select q.* from question q where q.questionType=:type order by  RAND() limit 25",nativeQuery=true)
    public  List<Question> selectRandomQuestionByType(@Param("type")Integer type);

    @Query(value = "select q.* from question q where q.questionType=:type order by  RAND() limit :limit",nativeQuery = true)
    public  List<Question> selectRandomQuestionByType(@Param("type")Integer type,@Param("limit")Integer limit);







}
