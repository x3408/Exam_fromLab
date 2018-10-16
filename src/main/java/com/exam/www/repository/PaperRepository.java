package com.exam.www.repository;


import com.exam.www.entity.Paper;
import com.exam.www.utils.JoinPaperQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

@Repository
public interface PaperRepository extends PagingAndSortingRepository<Paper, String> {


    public Page<Paper> findAll(Specification<Paper> spec, Pageable pageable);

    @Query("from Paper where tel=:mobilePhone")
    public Paper findPaperByMobilePhone(@Param("mobilePhone") String mobilePhone);

    /**
     * 查询手机号是否已被其他用户注册
     * */
    @Query("from Paper where tel=:mobilePhone and id != :id")
    public Paper findPaperByMobilePhone(@Param("mobilePhone") String mobilePhone, @Param("id") String id);

    @Transactional
    @Modifying
    @Query("update Paper set passWord = :passWord where id=:id")
    public void updatePasswordById(@Param("passWord") String passWord, @Param("id") String id);

    /*@Query("from Paper p1,PaperQuestion p2 where p1.id=p2.paperId and p1.paperId=:paperId")
    public List<JoinPaperQuestion> joinSelectPaperById(@Param("paperId")String paperId);*/

    @Query("from Paper where id=:id and paperState=1")
    public Paper getPaperByIdAndState(@Param("id")String id);

    @Transactional
    @Modifying
    @Query("update Paper set paperScore = :paperScore where id=:id")
    public void updatePaperById(@Param("paperScore") Integer paperScore, @Param("id") String id);


    @Query("from Paper where date(createDate) = curdate() and userId=:id")
    public List<Paper> selectTodayPaperById(@Param("id")String id);

}
