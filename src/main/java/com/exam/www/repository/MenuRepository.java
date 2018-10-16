package com.exam.www.repository;

import com.exam.www.entity.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends CrudRepository<Menu, String> {
    @Query("from Menu ")
    public List<Menu> getAll();

    @Query("from Menu where menuKey=:menuKey order by sequence")
    public List<Menu> getByMenuKey(@Param("menuKey") String menuKey);
}
