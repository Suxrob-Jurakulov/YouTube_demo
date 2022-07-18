package com.company.repository;

import com.company.entity.WatchedVideoEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WatchedVideoRepository extends PagingAndSortingRepository<WatchedVideoEntity, Integer> {

}
