package com.company.repository;

import com.company.entity.ChannelEntity;
import com.company.enums.Status;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ChannelRepository extends PagingAndSortingRepository<ChannelEntity, String> {

    @Transactional
    @Modifying
    @Query(value = "update ChannelEntity set status = ?1 where id = ?2")
    void updateStatus(Status status, String id);

    List<ChannelEntity> findByProfileIdAndVisible(Integer id, boolean b);


}
