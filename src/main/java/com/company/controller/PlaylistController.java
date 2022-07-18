package com.company.controller;

import com.company.dto.playlist.PlaylistDTO;
import com.company.dto.playlist.PlaylistStatusDTO;
import com.company.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PlaylistDTO dto) {
        PlaylistDTO response = playlistService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PlaylistDTO> update(@PathVariable("id") String id, @RequestBody PlaylistDTO dto) {
        PlaylistDTO response = playlistService.update(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/change_status/{id}")
    public ResponseEntity<String> changeStatus(@PathVariable("id") String id, @RequestBody PlaylistStatusDTO dto) {
        playlistService.changeStatus(id, dto);
        return ResponseEntity.ok("Successfully changed");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        playlistService.delete(id);
        return ResponseEntity.ok("Successfully deleted");
    }

    //    @GetMapping("/adm/list")
//    public ResponseEntity<List<PlaylistInfoDTO>> list(@Param(value = "page") int page, @Param(value = "size") int size){
//        List<PlaylistInfoDTO> list = playlistService.listForAdminByPagination(page, size);
//        return ResponseEntity.ok().body(list);
//    }
    @GetMapping("adm/pagination")
    public ResponseEntity<PageImpl<PlaylistDTO>> pagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size) {
        PageImpl<PlaylistDTO> list = playlistService.pagination(page, size);
        return ResponseEntity.ok().body(list);
    }
}
