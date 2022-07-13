package com.company.service;

import com.company.dto.CommentDTO;
import com.company.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

//    public CommentDTO create (CommentDTO dto){
//
//    }
}
