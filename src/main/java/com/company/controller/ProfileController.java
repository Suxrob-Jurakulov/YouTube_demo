package com.company.controller;

import com.company.dto.PasswordDTO;
import com.company.dto.ProfileDTO;
import com.company.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PutMapping("/change_password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordDTO dto){
        String response = profileService.changePassword(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody ProfileDTO dto){
        String response = profileService.update(dto);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/adm/create")
    public ResponseEntity<ProfileDTO> createByAdmin(ProfileDTO dto){
        ProfileDTO response = profileService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/get_detail")
    public ResponseEntity<ProfileDTO> getProfileDetail(){
        ProfileDTO response = profileService.getProfileDetail();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/photo/{id}")
    public ResponseEntity<String> updatePhoto(@PathVariable("id") String id){
        String response = profileService.updatePhoto(id);
        return ResponseEntity.ok("Successfully changed!");
    }


}
