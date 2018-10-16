package com.exam.www.repository;

import com.exam.www.entity.DataDictionary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataDictionaryRepository extends CrudRepository<DataDictionary, Integer>{
    @Query("from DataDictionary where type = :type")
    public List<DataDictionary> getDataDictionaryByType(@Param("type") String type);

    @Query("from DataDictionary where type = :type and (state = :state or state = '2')")
    public List<DataDictionary> getDataDictionaryByType(@Param("type") String type, @Param("state") String state);

    public Page<DataDictionary> findAll(Specification<DataDictionary> spec, Pageable pageable);

    @Query("from DataDictionary where type = :type and code = :code")
    public DataDictionary getDataDictionaryByTypeCode(@Param("type") String type, @Param("code") String code);

    @Query("from DataDictionary where type = :type and code = :code and id != :id")
    public DataDictionary getDataDictionaryByTypeCode(@Param("type") String type, @Param("code") String code, @Param("id") Integer id);
}
