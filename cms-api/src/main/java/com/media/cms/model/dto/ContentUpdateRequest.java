package com.media.cms.model.dto;

import com.media.cms.model.entity.ContentFormat;
import com.media.cms.model.entity.ContentStatus;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentUpdateRequest {

    private String title;
    private String description;
    private ContentFormat format;
    private ContentStatus status;
    private LocalDateTime publicationDate;
}
