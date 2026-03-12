package com.media.cms.service;

import com.media.cms.model.dto.MetricsUpdateRequest;
import com.media.cms.model.entity.EngagementMetrics;
import java.util.List;

public interface EngagementMetricsService {
    EngagementMetrics upsert(MetricsUpdateRequest request);
    List<EngagementMetrics> byContent(Long contentId);
    List<EngagementMetrics> byChannel(Long channelId);
}
