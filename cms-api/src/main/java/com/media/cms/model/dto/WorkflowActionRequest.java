package com.media.cms.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkflowActionRequest {

    @NotBlank
    private String action;

    private Long assignedUserId;
}
