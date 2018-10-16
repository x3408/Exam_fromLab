package com.exam.www.repository;

import com.exam.www.entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends PagingAndSortingRepository<Material, String> {
    public Page<Material> findAll(Specification<Material> spec, Pageable pageable);
}
