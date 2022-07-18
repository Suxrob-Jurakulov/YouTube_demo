package com.company.service;

import com.company.dto.ReportDTO;
import com.company.entity.ProfileEntity;
import com.company.entity.ReportEntity;
import com.company.enums.ReportType;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private VideoService videoService;

    public void create(ReportDTO dto) {
        ReportType type = dto.getType();
        if (type.name().equals("CHANNEL")) {
            channelService.get(dto.getJoinId());
        } else {
            videoService.get(dto.getJoinId());
        }
        ProfileEntity profile = profileService.getCurrentUser();

        ReportEntity entity = new ReportEntity();
        entity.setContent(dto.getContent());
        entity.setJoinId(dto.getJoinId());
        entity.setProfileId(profile.getId());
        entity.setType(dto.getType());
        reportRepository.save(entity);
    }

    public PageImpl<ReportDTO> getListPagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created_date");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ReportEntity> all = reportRepository.findAll(pageable);
        List<ReportDTO> list = new LinkedList<>();
        all.forEach(entity -> list.add(getReportInfoDTO(entity)));
        return new PageImpl<>(list, pageable, all.getTotalElements());
    }

    public void delete(Integer id) {
        ReportEntity entity = get(id);
        reportRepository.delete(entity);
    }

    public ReportEntity get(Integer id) {
        return reportRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Report not found");
        });
    }

    public List<ReportDTO> getListByUserId(Integer userId) {
        ProfileEntity profile = profileService.get(userId);
        List<ReportEntity> all = reportRepository.findByProfileIdOrderByCreatedDateDesc(profile.getId());
        List<ReportDTO> list = new LinkedList<>();
        all.forEach(entity -> list.add(getReportInfoDTO(entity))
        );
        return list;
    }

    private ReportDTO getReportInfoDTO(ReportEntity entity) {
        ReportDTO dto = new ReportDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setType(entity.getType());
        dto.setJoinId(entity.getJoinId());
        dto.setProfile(profileService.getProfileDTO(entity.getProfileId()));
        return dto;
    }
}
