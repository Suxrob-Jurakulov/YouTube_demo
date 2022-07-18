package com.company.controller;

import com.company.dto.tag.TagDTO;
import com.company.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("/create")
    public ResponseEntity<TagDTO> create(@RequestBody TagDTO dto){
        TagDTO response = tagService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/adm/update")
    public ResponseEntity<String> update(@RequestBody TagDTO dto){
        tagService.update(dto);
        return ResponseEntity.ok("Updated");
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        tagService.delete(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/public/list")
    public ResponseEntity<List<TagDTO>> list(){
        List<TagDTO> list = tagService.list();
        return ResponseEntity.ok().body(list);
    }
}
