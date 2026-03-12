package com.media.cms.repository;

import com.media.cms.model.entity.EngagementMetrics;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EngagementMetricsRepository extends JpaRepository<EngagementMetrics, Long> {
    List<EngagementMetrics> findByContentId(Long contentId);
    List<EngagementMetrics> findByChannelId(Long channelId);
    Optional<EngagementMetrics> findByContentIdAndChannelId(Long contentId, Long channelId);
}
