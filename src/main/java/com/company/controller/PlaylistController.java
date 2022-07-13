package com.company.controller;

import com.company.dto.PlaylistDTO;
import com.company.dto.playlist.PlaylistInfoDTO;
import com.company.dto.playlist.PlaylistStatusDTO;
import com.company.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PlaylistDTO dto){
        PlaylistDTO response = playlistService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PlaylistDTO> update(@PathVariable("id") String id, @RequestBody PlaylistDTO dto){
        PlaylistDTO response = playlistService.update(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/change_status/{id}")
    public ResponseEntity<String> changeStatus(@PathVariable("id") String id, @RequestBody PlaylistStatusDTO dto){
        playlistService.changeStatus(id, dto);
        return ResponseEntity.ok("Successfully changed");
    }

    @GetMapping("/adm/list")
    public ResponseEntity<List<PlaylistInfoDTO>> list(@Param(value = "page") int page, @Param(value = "size") int size){
        List<PlaylistInfoDTO> list = playlistService.listForAdminByPagination(page, size);
        return ResponseEntity.ok().body(list);
    }
}
