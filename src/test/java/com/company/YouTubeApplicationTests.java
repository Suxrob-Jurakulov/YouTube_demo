package com.company;

import com.company.mapper.video.VideoShortInfo;
import com.company.repository.PlaylistRepository;
import com.company.repository.VideoRepository;
import com.company.service.ChannelService;
import com.company.service.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SpringBootTest
class YouTubeApplicationTests {
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private VideoRepository videoRepository;

    @Test
    void contextLoads() {
        Pageable pageable = PageRequest.of(0, 5);
        List<VideoShortInfo> byCategory = videoRepository.getByCategory(1, pageable);
        System.out.println(byCategory);
    }

}
