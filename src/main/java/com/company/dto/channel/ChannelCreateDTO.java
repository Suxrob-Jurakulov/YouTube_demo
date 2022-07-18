package com.company.dto.channel;

import com.company.enums.VisibleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelCreateDTO {

    private String name;
    private String description;
    private String photo;
    private String banner;
    private VisibleStatus status;
    private String websiteUrl;
    private String telegramUrl;
    private String instagramUrl;
}
