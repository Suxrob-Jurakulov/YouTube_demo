package com.company.dto.video;

import com.company.dto.CategoryDTO;
import com.company.dto.attach.AttachDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.dto.tag.TagDTO;
import com.company.enums.LikeStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoFullInfoDTO {

//    (id,key,title,description,
//    preview_attach(id,url),attach(id,url,duration),
//    category(id,name),published_date,channel(id,name,photo(url)),shared_count,duration
//    tagList(id,name), view_count,Like(like_count,dislike_count,
//    isUserLiked,IsUserDisliked),)

    private String id;
    private String name;
    private String description;
    private LocalDateTime createdDate;
    private Integer viewCount;
    private Integer shareCount;
    private Integer likeCount;
    private Integer dislikeCount;
    private LikeStatus status;

    private AttachDTO attach;
    private AttachDTO preview;
    private CategoryDTO category;
    private List<TagDTO> tags;

    private ChannelDTO channel;
}
