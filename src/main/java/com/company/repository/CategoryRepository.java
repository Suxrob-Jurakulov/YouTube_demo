package com.company.repository;

import com.company.entity.CategoryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Integer> {

    Boolean existsByName(String name);


}
