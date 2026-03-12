package com.media.cms.controller;

import com.media.cms.model.dto.MetricsUpdateRequest;
import com.media.cms.model.entity.EngagementMetrics;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface MetricsController {
    ResponseEntity<EngagementMetrics> upsert(MetricsUpdateRequest request);
    ResponseEntity<List<EngagementMetrics>> byContent(Long contentId);
    ResponseEntity<List<EngagementMetrics>> byChannel(Long channelId);
}
