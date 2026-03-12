package com.media.cms.model.dto;

import com.media.cms.model.entity.ContentFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentCreateRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private ContentFormat format;
}
