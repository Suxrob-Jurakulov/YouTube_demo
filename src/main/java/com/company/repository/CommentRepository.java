package com.company.repository;

import com.company.dto.comment.CommentProfileDTO;
import com.company.entity.CommentEntity;
import com.company.mapper.comment.CommentInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CommentRepository extends PagingAndSortingRepository<CommentEntity, Integer> {

    @Query(value = "select c.id as id, c.content as content, c.createdDate as createdDate, c.videoId as videoId, c.video.name as videoName, " +
            " c.video.previewId as videoPreviewId, c.video.time as videoDuration," +
            " (select count(cl) from CommentLikeEntity cl where cl.status = 'LIKE' and cl.profileId = ?1) as likeCount," +
            " (select count(cl) from CommentLikeEntity cl where cl.status = 'DISLIKE' and cl.profileId = ?1) as disLikeCount " +
            "from CommentEntity c ")
    List<CommentInfo> getCommentListByProfileId(Integer profileId);

    @Query(value = "select c.id as id, c.content as content, c.createdDate as createdDate, c.profileId as profileId, " +
            "c.profile.name as profileName, c.profile.photoId as profilePhotoId, " +
            " (select count(cl) from CommentLikeEntity cl where cl.status = 'LIKE' and cl.comment.videoId = ?1) as likeCount," +
            " (select count(cl) from CommentLikeEntity cl where cl.status = 'DISLIKE' and cl.comment.videoId = ?1) as disLikeCount " +
            "from CommentEntity c where c.videoId = ?1")
    List<CommentInfo> getListByVideoId(String videoId);

    @Query(value = "select c.id as id, c.content as content, c.createdDate as createdDate, c.profileId as profileId, " +
            "c.profile.name as profileName, c.profile.photoId as profilePhotoId, " +
            " (select count(cl) from CommentLikeEntity cl where cl.status = 'LIKE' and cl.profileId = ?1) as likeCount," +
            " (select count(cl) from CommentLikeEntity cl where cl.status = 'DISLIKE' and cl.profileId = ?1) as disLikeCount " +
            "from CommentEntity c where c.commentId = ?1")
    List<CommentInfo> getListByCommentId(Integer commentId);
}
