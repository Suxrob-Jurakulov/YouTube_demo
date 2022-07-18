package com.company.service;

import com.company.dto.tag.TagDTO;
import com.company.entity.ProfileEntity;
import com.company.entity.TagEntity;
import com.company.exp.ItemNotFoundException;
import com.company.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private TagRepository tagRepository;

    public TagDTO create(TagDTO dto) {
        ProfileEntity currentUser = profileService.getCurrentUser();
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        entity.setProfileId(currentUser.getId());
        tagRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public void update(TagDTO dto) {
        TagEntity entity = get(dto.getId());
        entity.setName(dto.getName());
        tagRepository.save(entity);
    }

    public TagEntity get(Integer id){
        return tagRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Tag Not found");
        });
    }

    public void delete(Integer id) {
        TagEntity entity = get(id);
        tagRepository.delete(entity);
    }

    public List<TagDTO> list() {
        Iterable<TagEntity> all = tagRepository.findAll();
        List<TagDTO> list = new LinkedList<>();
        all.forEach(entity -> {
            TagDTO dto = new TagDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setCreatedDate(entity.getCreatedDate());
            list.add(dto);
        });
        return list;
    }
}
