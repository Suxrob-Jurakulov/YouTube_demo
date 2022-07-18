package com.company.repository;

import com.company.entity.VideoEntity;
import com.company.entity.VideoTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface VideoTagRepository extends JpaRepository<VideoTagEntity, Integer> {

    List<VideoTagEntity> findAllByVideo(VideoEntity video);

    @Query(value = "from VideoTagEntity v where v.videoId =:videoId")
    List<VideoTagEntity> getAllTagNameByVideoId(@Param("videoId") String videoId);

    @Transactional
    @Modifying
    @Query(value = "update VideoTagEntity set visible = false where videoId = ?1 and tagId = ?2")
    void deleteTag(String videoId, Integer tagId);




    List<VideoTagEntity> findByVideoIdAndVisibleTrue(String videoId);
}
