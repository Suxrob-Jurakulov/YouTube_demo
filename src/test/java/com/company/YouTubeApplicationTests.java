package com.company;

import com.company.repository.PlaylistRepository;
import com.company.service.ChannelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YouTubeApplicationTests {
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private ChannelService channelService;

    @Test
    void contextLoads() {

    }
}
