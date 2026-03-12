package com.media.cms.service.impl;

import com.media.cms.exception.ResourceNotFoundException;
import com.media.cms.model.entity.Channel;
import com.media.cms.model.entity.Content;
import com.media.cms.repository.ChannelRepository;
import com.media.cms.repository.ContentRepository;
import com.media.cms.service.ChannelService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ContentRepository contentRepository;

    public ChannelServiceImpl(ChannelRepository channelRepository,
                              ContentRepository contentRepository) {
        this.channelRepository = channelRepository;
        this.contentRepository = contentRepository;
    }

    @Override
    public List<Channel> getAll() {
        return channelRepository.findAll();
    }

    @Override
    public Channel create(Channel channel) {
        return channelRepository.save(channel);
    }

    @Override
    public Channel update(Long id, Channel request) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Channel not found: " + id));

        channel.setName(request.getName());
        channel.setType(request.getType());
        channel.setDescription(request.getDescription());
        return channelRepository.save(channel);
    }

    @Override
    public void delete(Long id) {
        channelRepository.deleteById(id);
    }

    @Override
    public String pushContent(Long contentId, Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ResourceNotFoundException("Channel not found: " + channelId));

        Content content = contentRepository.findById(contentId)
          .orElseThrow(() -> new ResourceNotFoundException("Content not found with id : " + contentId));

        if(content.getChannels().contains(channel)) {
          return "Content" + contentId + " has been already pushed to channel : " + channel.getName();
        }

        content.getChannels().add(channel);
        contentRepository.save(content);

        return "Placeholder: content " + contentId + " pushed to channel " + channel.getName();
    }
}
