package com.company.controller;

import com.company.dto.CategoryDTO;
import com.company.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/adm/create")
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO dto) {
        CategoryDTO response = categoryService.create(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/adm/update/{id}")
    public ResponseEntity<CategoryDTO> update(@RequestParam("id") Integer id,
                                              @RequestBody CategoryDTO dto){
        CategoryDTO response = categoryService.update(id, dto);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<String> update(@RequestParam("id") Integer id){
        categoryService.delete(id);
        return ResponseEntity.ok("Successfully deleted");
    }

    @GetMapping("/public/list")
    public ResponseEntity<List<CategoryDTO>> list(){
        List<CategoryDTO> list = categoryService.list();
        return ResponseEntity.ok().body(list);
    }
}
