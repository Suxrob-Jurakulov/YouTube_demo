package com.company.repository;

import com.company.entity.VideoEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VideoRepository extends PagingAndSortingRepository<VideoEntity, String> {

}
