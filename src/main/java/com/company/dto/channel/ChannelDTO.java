package com.company.dto.channel;

import com.company.dto.AttachDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelDTO {
    private String id;
    private String name;
    private String description;
    private String websiteUrl;
    private String telegramUrl;
    private String instagramUrl;
    private AttachDTO photo;
    private AttachDTO banner;
}
