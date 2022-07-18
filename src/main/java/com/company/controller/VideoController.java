package com.company.controller;

import com.company.dto.profile.ResponseInfoDTO;
import com.company.dto.tag.TagVideoDTO;
import com.company.dto.video.*;
import com.company.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;


    @PostMapping("/public/create")
    public ResponseEntity<ResponseInfoDTO> create(@RequestBody @Valid VideoCreatedDTO dto) {
        ResponseInfoDTO response = videoService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody VideoUpdateDTO dto) {
        ResponseInfoDTO response = videoService.update(dto, id);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/change_status")
    public ResponseEntity<?> changeStatus(@RequestBody VideoVisibleDTO dto) {
        ResponseInfoDTO response = videoService.updateStatus(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/view_count")
    public ResponseEntity<?> increaseViewCount(@RequestBody VideoWatchedDTO dto) {
        videoService.increaseViewCountAndLike(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/public/get_by_category")
    public ResponseEntity<List<VideoShortInfoDTO>> getByCategory(@RequestParam(name = "category") Integer categoryId,
                                                                 @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "size", defaultValue = "5") Integer size) {
        List<VideoShortInfoDTO> response = videoService.getByCategory(categoryId, page, size);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/public/get_by_title")
    public ResponseEntity<List<VideoShortInfoDTO>> getByCategory(@RequestParam(name = "title") String title) {
        List<VideoShortInfoDTO> response = videoService.searchByTitle(title);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/public/get_by_tag_id/{id}")
    public ResponseEntity<List<VideoShortInfoDTO>> searchByTagId(@PathVariable("id") Integer tagId,
                                                                 @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "size", defaultValue = "5") Integer size) {
        List<VideoShortInfoDTO> response = videoService.searchByTagId(tagId, page, size);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/get_video_by_id/{id}")
    public ResponseEntity<VideoFullInfoDTO> getVideoById(@PathVariable("id") String id) {
        VideoFullInfoDTO response = videoService.getVideoById(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/adm/video_list")
    public ResponseEntity<List<VideoShortOwnerPlaylistDTO>> getVideoListPagination(@RequestParam(name = "page") Integer page,
                                                                                   @RequestParam(name = "size") Integer size) {
        List<VideoShortOwnerPlaylistDTO> response = videoService.getVideoListPagination(page, size);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/public/get_channel_videos/{id}")
    public ResponseEntity<List<VideoDTO>> channelVideos(@PathVariable("id") String channelId,
                                                  @RequestParam(name = "page") int page,
                                                  @RequestParam(name = "size") int size) {
        List<VideoDTO> list = videoService.getVideoListByChannelPagination(channelId, page, size);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/add_tag")
    public ResponseEntity<String> addTag(@RequestBody VideoTagDTO dto){
        videoService.addTag(dto);
        return ResponseEntity.ok("Added");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody VideoTagDTO dto){
        videoService.delete(dto);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/public/get_list/{id}")
    public ResponseEntity<List<TagVideoDTO>> getTagByVideoId(@PathVariable("id") String videoId){
        List<TagVideoDTO> list = videoService.getTagListByVideoId(videoId);
        return ResponseEntity.ok().body(list);
    }
}
