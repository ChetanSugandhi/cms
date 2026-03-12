package com.media.cms.model.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelAssignRequest {

    @NotEmpty
    private Set<Long> channelIds;
}
