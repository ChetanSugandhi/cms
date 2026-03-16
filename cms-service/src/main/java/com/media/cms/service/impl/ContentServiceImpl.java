package com.media.cms.service.impl;

import com.media.cms.exception.ResourceNotFoundException;
import com.media.cms.model.dto.ContentCreateRequest;
import com.media.cms.model.dto.ContentUpdateRequest;
import com.media.cms.model.entity.Channel;
import com.media.cms.model.entity.Content;
import com.media.cms.model.entity.ContentStatus;
import com.media.cms.model.entity.User;
import com.media.cms.repository.ChannelRepository;
import com.media.cms.repository.ContentRepository;
import com.media.cms.repository.UserRepository;
import com.media.cms.service.ContentService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    public ContentServiceImpl(ContentRepository contentRepository,
                              UserRepository userRepository,
                              ChannelRepository channelRepository) {
        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
    }

    @Override
    public Content create(ContentCreateRequest request, Long creatorId) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Creator not found: " + creatorId));

        Content content = new Content();
        content.setTitle(request.getTitle());
        content.setDescription(request.getDescription());
        content.setFormat(request.getFormat());
        content.setStatus(ContentStatus.DRAFT);
        content.setCreator(creator);
        return contentRepository.save(content);
    }

    @Override
    public Content update(Long id, ContentUpdateRequest request) {
        Content content = getById(id);

        if (request.getTitle() != null) {
            content.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            content.setDescription(request.getDescription());
        }
        if (request.getFormat() != null) {
            content.setFormat(request.getFormat());
        }
        if (request.getStatus() != null) {
            content.setStatus(request.getStatus());
        }
        if (request.getPublicationDate() != null) {
            content.setPublicationDate(request.getPublicationDate());
        }

        return contentRepository.save(content);
    }

    @Override
    public void delete(Long id) {
        contentRepository.delete(getById(id));
    }

    @Override
    public Content getById(Long id) {
        return contentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found: " + id));
    }

    @Override
    public List<Content> getAll() {
        return contentRepository.findAll();
    }

    @Override
    public List<Content> getByStatus(ContentStatus status) {
        return contentRepository.findByStatus(status);
    }

    @Override
    public List<Content> getByCreator(Long creatorId) {
        return contentRepository.findByCreatorId(creatorId);
    }

    @Override
    @Transactional
    public Content assignChannels(Long contentId, List<Long> channelIds) {
        Content content = getById(contentId);
        List<Channel> channels = new ArrayList<>();
        for(Long id : channelIds) {
          Channel channel = channelRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Channel not found with id : " + id));
          if(channel != null) {
            channels.add(channel);
          }
        }
//        List<Channel> channels = channelRepository.findAllById(channelIds);
        if (channels.size() != channelIds.size()) {
            throw new ResourceNotFoundException("One or more channels not found");
        }
        content.setChannels(new HashSet<>(channels));
        return contentRepository.save(content);
    }
}
