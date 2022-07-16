package com.company.controller;

import com.company.dto.channel.ChannelAttachDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.dto.channel.ChannelStatusDTO;
import com.company.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @PostMapping("/create")
    public ResponseEntity<ChannelDTO> create(@RequestBody ChannelDTO dto) {
        ChannelDTO response = channelService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ChannelDTO> update(@PathVariable("id") String id, @RequestBody ChannelDTO dto) {
        ChannelDTO response = channelService.update(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update_channel_photo/{id}")
    public ResponseEntity<String> updatePhoto(@PathVariable("id") String id, @RequestBody ChannelAttachDTO dto) {
        channelService.updatePhoto(id, dto);
        return ResponseEntity.ok("Successfully updated");
    }

    @PutMapping("/update_channel_banner/{id}")
    public ResponseEntity<String> updateBanner(@PathVariable("id") String id, @RequestBody ChannelAttachDTO dto) {
        channelService.updateBanner(id, dto);
        return ResponseEntity.ok("Successfully updated");
    }

    @DeleteMapping("/remove_photo/{id}")
    public ResponseEntity<String> removePhoto(@PathVariable("id") String id) {
        String response = channelService.deletePhoto(id);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/remove_banner/{id}")
    public ResponseEntity<String> removeBanner(@PathVariable("id") String id) {
        String response = channelService.deleteBanner(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/adm/list")
    public ResponseEntity<List<ChannelDTO>> list(@Param(value = "page") int page, @Param(value = "size") int size) {
        List<ChannelDTO> list = channelService.list(page, size);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/public/get/{id}")
    public ResponseEntity<ChannelDTO> get(@PathVariable("id") String id) {
        ChannelDTO dto = channelService.getChannel(id);
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("change_status/{id}")
    public ResponseEntity<String> changeStatus(@PathVariable("id") String id, @RequestBody ChannelStatusDTO dto){
        channelService.changeStatus(id, dto);
        return ResponseEntity.ok("Changed");
    }

    @GetMapping("/user/channelList")
    public ResponseEntity<List<ChannelDTO>> list() {
        List<ChannelDTO> list = channelService.channelList();
        return ResponseEntity.ok().body(list);
    }
}
