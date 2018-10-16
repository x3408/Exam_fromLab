package com.exam.www.repository;


import com.exam.www.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    @Query("from User where userName=:userName and isDelete=0 and state=1")
    public User findByUserName(@Param("userName") String userName);


    @Query("from User where userName=:userName and isDelete=0 and state=1  and isAdmin!=0")
    public User findAdminUserByUserName(@Param("userName") String userName);

    public Page<User> findAll(Specification<User> spec, Pageable pageable);

    @Query("from User where tel=:mobilePhone")
    public User findUserByMobilePhone(@Param("mobilePhone") String mobilePhone);

    /**
     * 查询手机号是否已被其他用户注册
     * */
    @Query("from User where tel=:mobilePhone and id != :id")
    public User findUserByMobilePhone(@Param("mobilePhone") String mobilePhone, @Param("id") String id);

    @Transactional
    @Modifying
    @Query("update User set passWord = :passWord where id=:id")
    public void updatePasswordById(@Param("passWord") String passWord, @Param("id") String id);
}
