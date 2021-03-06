package com.company.controller;

import com.company.entity.EmailHistoryEntity;
import com.company.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;


    @GetMapping("/adm/list")
    public ResponseEntity<?> getEmail(@RequestParam(value = "page", defaultValue = "0") Integer size,
                                      @RequestParam(value = "size", defaultValue = "5") Integer page) {
        PageImpl<EmailHistoryEntity> list = emailService.pagination(page, size);
        return ResponseEntity.ok().body(list);
    }
}
