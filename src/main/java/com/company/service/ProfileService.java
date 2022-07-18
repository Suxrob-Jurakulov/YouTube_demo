package com.company.service;

import com.company.dto.attach.AttachDTO;
import com.company.dto.profile.PasswordDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.PositionStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.util.CurrentUserUtil;
import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AttachService attachService;


    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Not found");
        });
    }

    public ProfileEntity getByEmail(String email) {
        return profileRepository.findByEmail(email).orElseThrow(() -> {
            throw new BadRequestException("This email not found");
        });
    }

    public boolean isExistEmail(String email) {
        return profileRepository.existsByEmail(email);
    }

    public String changePassword(PasswordDTO dto) {
        ProfileEntity profile = getCurrentUser();
        if (!profile.getPassword().equals(MD5Util.getMd5(dto.getOldPassword()))) {
            throw new BadRequestException("Old password is wrong");
        }

        String newPassword = MD5Util.getMd5(dto.getNewPassword());
        profileRepository.changePassword(profile.getId(), newPassword);
        return "Successfully updated";
    }

    public String update(ProfileDTO dto) {
        ProfileEntity entity = CurrentUserUtil.currentUser().getProfile();
        profileRepository.updateProfile(entity.getId(), dto.getName());
        return "Successfully updated";
    }

    public ProfileDTO create(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setStatus(PositionStatus.ACTIVE);
        entity.setRole(dto.getRole());
        entity.setEmail(dto.getEmail());
        if (attachService.isExistAttach(dto.getPhotoId())) {
            entity.setPhotoId(dto.getPhotoId());
        }
        entity.setVisible(true);
        return entityToDto(entity);
    }

    public ProfileDTO getProfileDetail() {
        ProfileEntity entity = getCurrentUser();
        return entityToDto(entity);
    }

    public ProfileEntity getCurrentUser() {
        return CurrentUserUtil.currentUser().getProfile();
    }

    private ProfileDTO entityToDto(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        if (entity.getPhoto() != null) {
            dto.setPhotoUrl(attachService.getFileFullPath(entity.getPhoto()));
        }
        return dto;
    }

    public String updatePhoto(String id) {
        ProfileEntity profile = getCurrentUser();
        if (profile.getPhoto() == null) {
            profile.setPhotoId(id);
        } else {
            String forDelete = profile.getPhotoId();
            profile.setPhotoId(id);
            profileRepository.updatePhoto(id, profile.getId());
            attachService.delete(forDelete);
        }
        return "Successfully changed photo";
    }

    public ProfileDTO getProfileDTO(Integer id){
        ProfileEntity entity = get(id);
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setId(entity.getPhotoId());
        attachDTO.setUrl(attachService.getFullUrl(entity.getPhotoId()));
        dto.setPhoto(attachDTO);
        return dto;
    }
}
