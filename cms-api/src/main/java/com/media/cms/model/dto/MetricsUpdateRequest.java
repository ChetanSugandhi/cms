package com.media.cms.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricsUpdateRequest {

    @NotNull
    private Long contentId;

    @NotNull
    private Long channelId;

    private Long views = 0L;
    private Long likes = 0L;
    private Long comments = 0L;
    private Long shares = 0L;
}
