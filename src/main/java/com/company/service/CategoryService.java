package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.entity.CategoryEntity;
import com.company.enums.PositionStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto) {
        Boolean isExist = categoryRepository.existsByName(dto.getName());
        if (isExist){
            throw new BadRequestException("This category already exist");
        }
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        entity.setPositionStatus(PositionStatus.ACTIVE);
        categoryRepository.save(entity);

        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());
        return dto;
    }

    public CategoryDTO update(Integer id, CategoryDTO dto) {
        CategoryEntity entity = get(id);
        entity.setName(dto.getName());
        categoryRepository.save(entity);

        dto.setId(entity.getId());
        return dto;
    }

    public void delete(Integer id) {
        get(id);
        categoryRepository.deleteById(id);
    }

    public CategoryEntity get(Integer id){
        return categoryRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Category not found");
        });
    }

    public List<CategoryDTO> list() {
        Iterable<CategoryEntity> all = categoryRepository.findAll();
        List<CategoryDTO> list = new LinkedList<>();
        all.forEach(entity -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setName(entity.getName());
            list.add(dto);
        });
        return list;
    }
}
