package com.media.cms.service;

import com.media.cms.model.entity.Channel;
import java.util.List;

public interface ChannelService {
    List<Channel> getAll();
    Channel create(Channel channel);
    Channel update(Long id, Channel channel);
    void delete(Long id);
    String pushContent(Long contentId, Long channelId);
}
