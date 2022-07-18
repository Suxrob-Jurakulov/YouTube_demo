package com.company.controller;

import com.company.dto.ReportDTO;
import com.company.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody @Valid ReportDTO dto) {
        reportService.create(dto);
        return ResponseEntity.ok("Thanks for information, admins will be check soon");
    }

    @GetMapping("/adm/list")
    public ResponseEntity<PageImpl<ReportDTO>> list(@RequestParam(name = "page", defaultValue = "0") int page,
                                                    @RequestParam(name = "size", defaultValue = "5") int size) {
        PageImpl<ReportDTO> respone = reportService.getListPagination(page, size);
        return ResponseEntity.ok().body(respone);
    }

    @GetMapping("/adm/list_by_userId/{id}")
    public ResponseEntity<List<ReportDTO>> list(@PathVariable("id") Integer userId) {
        List<ReportDTO> respone = reportService.getListByUserId(userId);
        return ResponseEntity.ok().body(respone);
    }

    @DeleteMapping("/adm/remove/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        reportService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
