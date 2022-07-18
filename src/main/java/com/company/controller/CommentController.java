package com.company.controller;

import com.company.dto.CommentDTO;
import com.company.dto.CommentUpdateDTO;
import com.company.dto.comment.CommentProfileDTO;
import com.company.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentDTO> create(@RequestBody @Valid CommentDTO dto){
        CommentDTO response = commentService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody CommentUpdateDTO dto){
        commentService.update(dto);
        return ResponseEntity.ok("Updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        commentService.delete(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/adm/list")
    public ResponseEntity<PageImpl<CommentDTO>> list(@RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "5") int size){
        PageImpl<CommentDTO> list = commentService.list(page, size);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/adm/list_by_profileId/{id}")
    public ResponseEntity<List<CommentProfileDTO>> listByProfileId(@PathVariable("id") Integer id){
        List<CommentProfileDTO> response = commentService.listByProfileId(id);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/list_by_profile")
    public ResponseEntity<List<CommentProfileDTO>> listByProfileId(){
        List<CommentProfileDTO> response = commentService.listByProfile();
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/list_by_videoId/{id}")
    public ResponseEntity<List<CommentProfileDTO>> listByProfileId(@PathVariable("id") String videoId){
        List<CommentProfileDTO> response = commentService.listByVideoId(videoId);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/list_by_reply_comment/{id}")
    public ResponseEntity<List<CommentProfileDTO>> listByReplyCommentId(@PathVariable("id") Integer commentId){
        List<CommentProfileDTO> response = commentService.listByReplyCommentId(commentId);
        return ResponseEntity.ok().body(response);
    }
}
