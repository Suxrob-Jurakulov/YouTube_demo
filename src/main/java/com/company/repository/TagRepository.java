package com.company.repository;

import com.company.entity.TagEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagRepository extends PagingAndSortingRepository<TagEntity, Integer> {

}
