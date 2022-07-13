package com.company.repository;

import com.company.entity.CommentEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<CommentEntity, Integer> {

}
