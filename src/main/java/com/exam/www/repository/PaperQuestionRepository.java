package com.exam.www.repository;


import com.exam.www.entity.PaperQuestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaperQuestionRepository extends PagingAndSortingRepository<PaperQuestion, String> {
    @Query("from PaperQuestion  where questionId=:questionId and paperId=:paperId")
    public PaperQuestion findByQuestionIdAndPaperId(@Param("questionId") String questionId, @Param("paperId") String paperId);

    @Query("from PaperQuestion where paperId=:paperId")
    public List<PaperQuestion> findOneByPaperId(@Param("paperId") String paperId);

    @Query(value = "select * from paper_question where questionId=?1" ,nativeQuery=true)
    public List<PaperQuestion> findPaperQuestionById(String questionId);
}
