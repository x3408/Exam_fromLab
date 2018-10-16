package com.exam.www.repository;


import com.exam.www.entity.Apply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ApplyRepository extends PagingAndSortingRepository<Apply, String> {
    @Query("from Apply where date(createDate) = curdate() and userId=:id and state=:state")
    public List<Apply> selectTodayApplyByUId(@Param("id") String id, @Param("state") Integer state);

    public Page<Apply> findAll(Specification<Apply> spec, Pageable pageable);
    @Transactional
    @Modifying
    @Query("delete  from Apply  where userId=:uid")
    public void deleteUserApplyById(@Param("uid") String uid);


}
