package com.company.repository;

import com.company.entity.ProfileEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ProfileRepository extends PagingAndSortingRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set password = ?2 where id = ?1")
    void changePassword(Integer id, String newPassword);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set name = ?2 where id = ?1")
    void updateProfile(Integer id, String name);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set photoId = ?1 where id = ?2")
    void updatePhoto(String photoId, Integer pId);
}
