package com.company.service;

import com.company.dto.AttachDTO;
import com.company.dto.VideoDTO;
import com.company.entity.AttachEntity;
import com.company.entity.VideoEntity;
import com.company.repository.AttachRepository;
import com.company.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.UUID;

@Service
public class VideoService {
    @Value("${attach.folder}")
    private String attachFolder;
    @Value("${server.url}")
    private String serverUrl;
    @Autowired
    private AttachRepository attachRepository;
    @Autowired
    private AttachService attachService;

    public AttachDTO create(MultipartFile file) {
       return attachService.saveToSystem(file);
    }
//    public String getYmDString() {
//        int year = Calendar.getInstance().get(Calendar.YEAR);
//        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
//        int day = Calendar.getInstance().get(Calendar.DATE);
//        return year + "/" + month + "/" + day; // 2022/04/23
//    }
//    private String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
//        int lastIndex = fileName.lastIndexOf(".");
//        return fileName.substring(lastIndex + 1);
//    }

}
