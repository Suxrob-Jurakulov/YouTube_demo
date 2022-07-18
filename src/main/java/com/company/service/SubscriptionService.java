package com.company.service;

import com.company.dto.SubscriptionDTO;
import com.company.dto.attach.AttachDTO;
import com.company.dto.channel.ChannelDTO;
import com.company.entity.ChannelEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.SubscriptionEntity;
import com.company.enums.PositionStatus;
import com.company.exp.BadRequestException;
import com.company.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private AttachService attachService;

    public void create(SubscriptionDTO dto) {
        ChannelEntity channel = channelService.get(dto.getChannelId());
        ProfileEntity currentUser = profileService.getCurrentUser();

        SubscriptionEntity entity = new SubscriptionEntity();
        entity.setChannel(channel);
        entity.setProfile(currentUser);
        entity.setType(dto.getType());
        subscriptionRepository.save(entity);
    }

    public void updateStatus(SubscriptionDTO dto) {
        SubscriptionEntity entity = check(dto);
        entity.setStatus(dto.getStatus());
        subscriptionRepository.save(entity);
    }

    public void updateType(SubscriptionDTO dto) {
        SubscriptionEntity entity = check(dto);
        entity.setType(dto.getType());
        subscriptionRepository.save(entity);
    }

    private SubscriptionEntity check(SubscriptionDTO dto) {
        ProfileEntity profile = profileService.getCurrentUser();
        Optional<SubscriptionEntity> optional = subscriptionRepository.findByProfileIdAndChannelId(profile.getId(), dto.getChannelId());
        if (optional.isEmpty()) {
            throw new BadRequestException("Something went wrong");
        }
        return optional.get();
    }

    public List<SubscriptionDTO> getList() {
        ProfileEntity profile = profileService.getCurrentUser();
        List<SubscriptionDTO> list = new LinkedList<>();
        subscriptionRepository.findByProfileIdAndStatus(profile.getId(), PositionStatus.ACTIVE).forEach(s -> list.add(getDto(s)));
        return list;
    }

    public List<SubscriptionDTO> getListByUserId(Integer profileId) {
        List<SubscriptionEntity> entityList = subscriptionRepository.findByProfileIdAndStatus(profileId, PositionStatus.ACTIVE);
        List<SubscriptionDTO> list = new LinkedList<>();
        entityList.forEach(entity -> {
            SubscriptionDTO dto = getDto(entity);
            dto.setCreatedDate(entity.getCreatedDate());
            list.add(dto);
        });
        return list;
    }

    private SubscriptionDTO getDto(SubscriptionEntity entity) {
        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setId(entity.getId());
        dto.setType(entity.getType());

        ChannelEntity channel = channelService.get(entity.getChannelId());
        ChannelDTO channelDTO = new ChannelDTO();
        channelDTO.setId(channel.getId());
        channelDTO.setName(channel.getName());

        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setId(channel.getPhotoId());
        attachDTO.setUrl(attachService.getFullUrl(channel.getPhotoId()));
        channelDTO.setPhoto(attachDTO);
        dto.setChannel(channelDTO);
        return dto;
    }
}
