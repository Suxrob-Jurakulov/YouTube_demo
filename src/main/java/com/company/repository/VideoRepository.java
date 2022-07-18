package com.company.repository;

import com.company.entity.VideoEntity;
import com.company.enums.VisibleStatus;
import com.company.mapper.video.VideoFullInfo;
import com.company.mapper.video.VideoShortInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface VideoRepository extends PagingAndSortingRepository<VideoEntity, String> {


    @Transactional
    @Modifying
    @Query("update VideoEntity set status = ?2 where id = ?1")
    void changeStatus(String id, VisibleStatus visibleStatus);

    @Transactional
    @Modifying
    @Query("update VideoEntity set viewCount = viewCount + 1 where id = ?1")
    void increaseCount(String id);


    @Query(value = "select v.id as id, v.name as name, v.preview_id as previewId," +
            " v.created_date as createdDate, v.view_count as viewCount, " +
            " v.time as time, c.id as channelId, c.name as channelName, c.photo_id as channelPhotoId  " +
            " from video v join channel c on v.channel_id = c.id " +
            " where c.status = 'ACTIVE' and c.visible = true and " +
            " v.status = 'PUBLIC' and v.visible = true and v.category_id = ?1", nativeQuery = true)
    List<VideoShortInfo> getByCategory(Integer categoryId, Pageable pageable);


    @Query(value = "select v.id as id, v.name as name, v.preview_id as previewId," +
            " v.created_date as createdDate, v.view_count as viewCount, " +
            " v.time as time, c.id as channelId, c.name as channelName, c.photo_id as channelPhotoId  " +
            " from video v join channel c on v.channel_id = c.id " +
            " join video_tag vt on v.id = vt.video_id " +
            " where c.status = 'ACTIVE' and c.visible = true and " +
            " v.status = 'PUBLIC' and v.visible = true and vt.tag_id = ?1", nativeQuery = true)
    List<VideoShortInfo> searchByTagId(Integer tagId, Pageable pageable);

    @Query(value = "select v.id as id, v.name as name, v.preview_id as previewId," +
            " v.created_date as createdDate, v.view_count as viewCount, " +
            " v.time as time, c.id as channelId, c.name as channelName, c.photo_id as channelPhotoId  " +
            " from video v join channel c on v.channel_id = c.id " +
            " where c.status = 'ACTIVE' and c.visible = true and " +
            " v.status = 'PUBLIC' and v.visible = true and v.name = ?1", nativeQuery = true)
    List<VideoShortInfo> searchByTitle(String title);


    @Query("select v.status as visibleStatus, v.id as id, v.name as name, v.description as description, v.previewId as previewId, " +
            " v.attachId as attachId, v.channel.id as channelId, v.channel.name as channelName," +
            " v.categoryId as categoryId, v.category.name as categoryName, v.createdDate as createdDate, " +
            " v.viewCount as viewCount, v.sharedCount as sharedCount, v.attach.duration as time, " +
            " v.channel.photoId as channelPhotoId, v.channel.profileId as channelProfileId, " +
            " (select count(vl) from VideoLikeEntity vl  where vl.videoId = ?1 and vl.status = 'LIKE') as likeCount, " +
            " (select count(vl) from VideoLikeEntity vl  where vl.videoId = ?1 and vl.status = 'DISLIKE') as disLikeCount, " +
            " (select status from VideoLikeEntity  where videoId = ?1 and profileId = ?2 ) as likeStatus " +
            " from VideoEntity v where v.visible = true and v.id = ?1")
    VideoFullInfo getVideoById(String id, Integer profileId);



    @Query(value = "select v.id as id, v.name as name, v.preview_id as previewId," +
            " v.created_date as createdDate, v.view_count as viewCount, " +
            " v.time as time, c.id as channelId, c.name as channelName, c.photo_id as channelPhotoId  " +
            " from video v join channel c on v.channel_id = c.id " +
            " where c.status = 'ACTIVE' and c.visible = true and " +
            " v.status = 'PUBLIC' and v.visible = true ", nativeQuery = true)
    List<VideoShortInfo> getVideoPagination(Pageable page);


    @Query(value = "from VideoEntity where channelId = ?1")
    List<VideoEntity> getList(String channelId, Pageable pageable);
}
