package com.media.cms.controller;

import com.media.cms.model.entity.Channel;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ChannelController {
    ResponseEntity<List<Channel>> getAll();
    ResponseEntity<Channel> create(Channel channel);
    ResponseEntity<Channel> update(Long id, Channel channel);
    ResponseEntity<Void> delete(Long id);
    ResponseEntity<String> push(Long channelId, Long contentId);
}
