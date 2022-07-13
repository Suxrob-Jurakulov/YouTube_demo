package com.company.service;

import com.company.dto.AttachDTO;
import com.company.dto.PlaylistDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.dto.playlist.PlaylistInfoDTO;
import com.company.dto.playlist.PlaylistStatusDTO;
import com.company.entity.ChannelEntity;
import com.company.entity.PlaylistEntity;
import com.company.entity.ProfileEntity;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.mapper.PlaylistInfo;
import com.company.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ChannelService channelService;


    public PlaylistDTO create(PlaylistDTO dto) {
        checkProfile(dto.getChannel());

        PlaylistEntity entity = new PlaylistEntity();
        insertToEntity(dto, entity);
        playlistRepository.save(entity);

        return entityToDto(entity);
    }

    public PlaylistDTO update(String id, PlaylistDTO dto) {
        PlaylistEntity entity = get(id);
        insertToEntity(dto, entity);
        playlistRepository.save(entity);
        return entityToDto(entity);
    }

    public PlaylistEntity get(String id) {
        return playlistRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Playlist not found");
        });
    }

    private PlaylistDTO entityToDto(PlaylistEntity entity) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setOrderNum(entity.getOrderNum());
        dto.setChannel(entity.getChannelId());
        dto.setReview(entity.getReviewId());
        return dto;
    }

    private void insertToEntity(PlaylistDTO dto, PlaylistEntity entity) {
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
        entity.setChannelId(dto.getChannel());
        entity.setReviewId(dto.getReview());
        entity.setOrderNum(dto.getOrderNum());
    }

    public void changeStatus(String id, PlaylistStatusDTO dto) {
        PlaylistEntity entity = get(id);
        checkProfile(entity.getChannelId());
        playlistRepository.updateStatus(dto, id);
    }

    private void checkProfile(String id) {
        ChannelEntity channel = channelService.get(id);
        ProfileEntity profile = profileService.getCurrentUser();
        if (channel.getProfile() != profile) {
            throw new BadRequestException("Siz bu kanal egasi emassiz");
        }
    }

    public List<PlaylistInfoDTO> listForAdminByPagination(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<PlaylistInfo> all = playlistRepository.getAllListForAdmin(pageable);
//
//        List<PlaylistInfoDTO> list = new LinkedList<>();
//        all.forEach(entity -> list.add(entityToInfoDto(entity)));
//        return list;
        return null;
    }

    private PlaylistInfoDTO entityToInfoDto(PlaylistInfo entity) {
        PlaylistInfoDTO dto = new PlaylistInfoDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setOrderNum(entity.getOrderNum());

        ChannelDTO channel = new ChannelDTO();
        channel.setId(entity.getChannel().getId());
        channel.setName(entity.getChannel().getName());

        AttachDTO photo = new AttachDTO();
        photo.setId(entity.getChannel().getPhotoId());
        photo.setUrl(entity.getChannel().getPhoto().getPath());
        channel.setPhoto(photo);

        dto.setChannel(channel);

        ProfileDTO profile = new ProfileDTO();
        profile.setId(entity.getChannel().getProfileId());
        profile.setName(entity.getChannel().getProfile().getName());

        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setId(entity.getChannel().getProfile().getPhoto().getId());
        attachDTO.setUrl(entity.getChannel().getProfile().getPhoto().getPath());
        profile.setPhoto(attachDTO);
        dto.setProfile(profile);
        return dto;
    }
}
