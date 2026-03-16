package com.media.cms.service;

import com.media.cms.model.dto.ContentCreateRequest;
import com.media.cms.model.dto.ContentUpdateRequest;
import com.media.cms.model.entity.Content;
import com.media.cms.model.entity.ContentStatus;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface ContentService {
    Content create(ContentCreateRequest request, Long creatorId);
    Content update(Long id, ContentUpdateRequest request);
    void delete(Long id);
    Content getById(Long id);
    List<Content> getAll();
    List<Content> getByStatus(ContentStatus status);
    List<Content> getByCreator(Long creatorId);
    Content assignChannels(Long contentId, List<Long> channelIds);
}
