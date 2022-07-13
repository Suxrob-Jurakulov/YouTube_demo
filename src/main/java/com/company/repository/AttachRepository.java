package com.company.repository;

import com.company.entity.AttachEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AttachRepository extends PagingAndSortingRepository<AttachEntity, String> {


    @Query("from AttachEntity")
    Page<AttachEntity> getAttachByPage(Pageable pageable);

    boolean existsById(String id);

}
