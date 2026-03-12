package com.media.cms.controller.impl;

import com.media.cms.model.entity.Channel;
import com.media.cms.service.ChannelService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channels")
public class ChannelControllerImpl implements com.media.cms.controller.ChannelController {

    private final ChannelService channelService;

    public ChannelControllerImpl(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CONTENT_CREATOR','EDITOR','MARKETING','MANAGER')")
    public ResponseEntity<List<Channel>> getAll() {
        return ResponseEntity.ok(channelService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MARKETING')")
    public ResponseEntity<Channel> create(@Valid @RequestBody Channel channel) {
        return ResponseEntity.ok(channelService.create(channel));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MARKETING')")
    public ResponseEntity<Channel> update(@PathVariable Long id, @Valid @RequestBody Channel channel) {
        return ResponseEntity.ok(channelService.update(id, channel));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MARKETING')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        channelService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{channelId}/push/{contentId}")
    @PreAuthorize("hasAnyRole('MARKETING')")
    public ResponseEntity<String> push(@PathVariable Long channelId, @PathVariable Long contentId) {
        return ResponseEntity.ok(channelService.pushContent(contentId, channelId));
    }
}
