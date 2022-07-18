package com.company.repository;

import com.company.entity.SubscriptionEntity;
import com.company.enums.PositionStatus;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends PagingAndSortingRepository<SubscriptionEntity, Integer> {


    Optional<SubscriptionEntity> findByProfileIdAndChannelId(Integer profile_id, String channel_id);

    List<SubscriptionEntity> findByProfileIdAndStatus(Integer profile_id, PositionStatus status);
}
