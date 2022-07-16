package com.company.service;

import com.company.dto.AttachDTO;
import com.company.dto.channel.ChannelAttachDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.dto.channel.ChannelStatusDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ChannelEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.Status;
import com.company.exp.ItemNotFoundException;
import com.company.exp.NotPermissionException;
import com.company.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AttachService attachService;

    public ChannelDTO create(ChannelDTO dto) {
        ChannelEntity entity = new ChannelEntity();
        insertAttach(dto, entity);
        channelRepository.save(dtoToEntity(dto, entity));
        return entityToDto(entity);
    }

    private ChannelDTO entityToDto(ChannelEntity entity) {
        ChannelDTO dto = new ChannelDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        if (entity.getPhoto() != null) {
            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(entity.getPhotoId());
            attachDTO.setOriginalName(entity.getPhoto().getOriginalName());
            attachDTO.setUrl(entity.getPhoto().getPath());
            attachDTO.setExtension(entity.getPhoto().getExtension());
            dto.setPhoto(attachDTO);
        }
        return dto;
    }

    public ChannelDTO update(String id, ChannelDTO dto) {
        ChannelEntity entity = get(id);
        ProfileEntity profile = profileService.getCurrentUser();
        if (!Objects.equals(entity.getProfileId(), profile.getId())) {
            throw new NotPermissionException("This channel is not yours");
        }
        channelRepository.save(dtoToEntity(dto, entity));
        return entityToDto(entity);
    }

    private ChannelEntity dtoToEntity(ChannelDTO dto, ChannelEntity entity) {
        Integer pId = profileService.getCurrentUser().getId();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setProfileId(pId);
        entity.setStatus(Status.ACTIVE);

        if (dto.getInstagramUrl() != null) {
            entity.setInstagramUrl(dto.getInstagramUrl());
        }
        if (dto.getTelegramUrl() != null) {
            entity.setTelegramUrl(dto.getTelegramUrl());
        }
        if (dto.getWebsiteUrl() != null) {
            entity.setWebsiteUrl(dto.getWebsiteUrl());
        }
        return entity;
    }

    public ChannelEntity get(String id) {
        return channelRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("This channel not found");
        });
    }

    private void insertAttach(ChannelDTO dto, ChannelEntity entity) {
        if (dto.getPhoto() != null) {
            entity.setPhotoId(dto.getPhoto().getId());
        }
        if (dto.getBanner() != null) {
            entity.setPhotoId(dto.getBanner().getId());
        }
    }

    public void updatePhoto(String id, ChannelAttachDTO dto) {
        ChannelEntity entity = get(id);
        if (entity.getPhoto() == null) {
            entity.setPhotoId(dto.getPhotoId());
        } else {
            AttachEntity tempPhoto = entity.getPhoto();
            entity.setPhotoId(dto.getPhotoId());
            attachService.delete(tempPhoto.getId());
        }
    }

    public void updateBanner(String id, ChannelAttachDTO dto) {
        ChannelEntity entity = get(id);
        if (entity.getBanner() == null) {
            entity.setBannerId(dto.getBannerId());
        } else {
            AttachEntity tempBanner = entity.getBanner();
            entity.setBannerId(dto.getBannerId());
            attachService.delete(tempBanner.getId());
        }
    }

    public String deletePhoto(String id) {
        ChannelEntity entity = get(id);
        if (entity.getPhoto() == null) {
            return "Sizning kanalingizda photo yo'q!";
        }
        String tempPhoto = entity.getPhotoId();
        entity.setPhoto(null);
        entity.setPhotoId(null);
        attachService.delete(tempPhoto);
        return "Successfully deleted";
    }

    public String deleteBanner(String id) {
        ChannelEntity entity = get(id);
        if (entity.getBanner() == null) {
            return "Sizning kanalingizda banner yo'q!";
        }
        String tempBanner = entity.getBannerId();
        entity.setBanner(null);
        entity.setBannerId(null);
        attachService.delete(tempBanner);
        return "Successfully deleted";
    }

    public List<ChannelDTO> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ChannelEntity> all = channelRepository.findAll(pageable);

        List<ChannelDTO> list = new LinkedList<>();
        all.forEach(entity -> {
            ChannelDTO dto = new ChannelDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());
            list.add(dto);
        });
        return list;
    }

    public ChannelDTO getChannel(String id) {
        ChannelEntity entity = get(id);
        return entityToDto(entity);
    }

    public void changeStatus(String id, ChannelStatusDTO dto) {
        ChannelEntity entity = get(id);
        ProfileEntity profile = profileService.getCurrentUser();
        if (!Objects.equals(profile.getId(), entity.getProfileId()) || !profile.getRole().equals(ProfileRole.ROLE_ADMIN)){
            throw new NotPermissionException("This action not access for you! Mazgi");
        }
        channelRepository.updateStatus(dto.getStatus(), id);
    }

    public List<ChannelDTO> channelList() {
        ProfileEntity profileEntity = profileService.getCurrentUser();
        List<ChannelEntity> entityList = channelRepository.findByProfileIdAndVisible(profileEntity.getId(), true);
        List<ChannelDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            dtoList.add(entityToDto(entity));
        });
        return dtoList;
    }
}
