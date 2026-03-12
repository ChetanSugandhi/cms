package com.media.cms.service.impl;

import com.media.cms.exception.ResourceNotFoundException;
import com.media.cms.model.dto.MetricsUpdateRequest;
import com.media.cms.model.entity.Channel;
import com.media.cms.model.entity.Content;
import com.media.cms.model.entity.EngagementMetrics;
import com.media.cms.repository.ChannelRepository;
import com.media.cms.repository.ContentRepository;
import com.media.cms.repository.EngagementMetricsRepository;
import com.media.cms.service.EngagementMetricsService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EngagementMetricsServiceImpl implements EngagementMetricsService {

    private final EngagementMetricsRepository metricsRepository;
    private final ContentRepository contentRepository;
    private final ChannelRepository channelRepository;

    public EngagementMetricsServiceImpl(EngagementMetricsRepository metricsRepository,
                                        ContentRepository contentRepository,
                                        ChannelRepository channelRepository) {
        this.metricsRepository = metricsRepository;
        this.contentRepository = contentRepository;
        this.channelRepository = channelRepository;
    }

    @Override
    public EngagementMetrics upsert(MetricsUpdateRequest request) {
        Content content = contentRepository.findById(request.getContentId())
                .orElseThrow(() -> new ResourceNotFoundException("Content not found: " + request.getContentId()));
        Channel channel = channelRepository.findById(request.getChannelId())
                .orElseThrow(() -> new ResourceNotFoundException("Channel not found: " + request.getChannelId()));

        EngagementMetrics metrics = metricsRepository
                .findByContentIdAndChannelId(request.getContentId(), request.getChannelId())
                .orElse(new EngagementMetrics());

        metrics.setContent(content);
        metrics.setChannel(channel);
        metrics.setViews(nonNull(metrics.getViews()) + nonNull(request.getViews()));
        metrics.setLikes(nonNull(metrics.getLikes()) + nonNull(request.getLikes()));
        metrics.setComments(nonNull(metrics.getComments()) + nonNull(request.getComments()));
        metrics.setShares(nonNull(metrics.getShares()) + nonNull(request.getShares()));

        return metricsRepository.save(metrics);
    }

    @Override
    public List<EngagementMetrics> byContent(Long contentId) {
        return metricsRepository.findByContentId(contentId);
    }

    @Override
    public List<EngagementMetrics> byChannel(Long channelId) {
        return metricsRepository.findByChannelId(channelId);
    }

    private long nonNull(Long value) {
        return value == null ? 0L : value;
    }
}
