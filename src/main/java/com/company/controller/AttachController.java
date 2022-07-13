package com.company.controller;

import com.company.dto.AttachDTO;
import com.company.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/attach")
public class AttachController {

    @Autowired
    private AttachService attachService;

    @PostMapping("/public/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file){
        AttachDTO dto = attachService.saveToSystem(file);
        return ResponseEntity.ok().body(dto);
    }

    //  GET ATTACH - 1
    @GetMapping(value = "/public/open/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("id") String id) {
        try {
            return this.attachService.loadImage(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    //  GET ATTACH - 2
    @GetMapping(value = "/public/opens/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> show(@PathVariable("id") String id) {
        byte[] response = attachService.show(id);
        return ResponseEntity.ok().body(response);
    }

    //  GET ATTACH - 3 -> by all type
    @GetMapping(value = "/public/open_general/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@PathVariable("fileName") String fileName) {
        return attachService.open_general(fileName);
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id, HttpServletRequest request) {
        String response = attachService.delete(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/public/download/{id}")
    public ResponseEntity<?> download(@PathVariable("id") String id) {
        return attachService.download(id);
    }

    @GetMapping("/pagination")
    public ResponseEntity<?> pagination(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "size", defaultValue = "4") Integer size) {
        List<AttachDTO> response = attachService.pagination(page, size);
        return ResponseEntity.ok().body(response);
    }
}
