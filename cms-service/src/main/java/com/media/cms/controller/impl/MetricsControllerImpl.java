package com.media.cms.controller.impl;

import com.media.cms.controller.MetricsController;
import com.media.cms.model.dto.MetricsUpdateRequest;
import com.media.cms.model.entity.EngagementMetrics;
import com.media.cms.service.EngagementMetricsService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/metrics")
public class MetricsControllerImpl implements MetricsController {

    private final EngagementMetricsService metricsService;

    public MetricsControllerImpl(EngagementMetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MARKETING','MANAGER')")
    public ResponseEntity<EngagementMetrics> upsert(@Valid @RequestBody MetricsUpdateRequest request) {
        return ResponseEntity.ok(metricsService.upsert(request));
    }

    @GetMapping("/content/{contentId}")
    @PreAuthorize("hasAnyRole('MARKETING','MANAGER','EDITOR')")
    public ResponseEntity<List<EngagementMetrics>> byContent(@PathVariable Long contentId) {
        return ResponseEntity.ok(metricsService.byContent(contentId));
    }

    @GetMapping("/channel/{channelId}")
    @PreAuthorize("hasAnyRole('MARKETING','MANAGER','EDITOR')")
    public ResponseEntity<List<EngagementMetrics>> byChannel(@PathVariable Long channelId) {
        return ResponseEntity.ok(metricsService.byChannel(channelId));
    }
}
