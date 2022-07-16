package com.company.controller;

import com.company.dto.AttachDTO;
import com.company.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @PostMapping("/public/create")
    public ResponseEntity<AttachDTO> create(@RequestParam MultipartFile file){
        AttachDTO response = videoService.create(file);
        return ResponseEntity.ok().body(response);
    }

}
