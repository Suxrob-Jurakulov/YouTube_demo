package com.company.repository;

import com.company.dto.playlist.PlaylistStatusDTO;
import com.company.entity.PlaylistEntity;
import com.company.mapper.PlaylistInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

public interface PlaylistRepository extends PagingAndSortingRepository<PlaylistEntity, String> {

    @Transactional
    @Modifying
    @Query(value = "update PlaylistEntity set status = ?1 where id = ?2")
    void updateStatus(PlaylistStatusDTO dto, String id);


    @Query(value = "select p.id from ", nativeQuery = true)
    Page<PlaylistInfo> getAllListForAdmin(Pageable pageable);
}
