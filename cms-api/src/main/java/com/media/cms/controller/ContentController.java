package com.media.cms.controller;

import com.media.cms.model.dto.ChannelAssignRequest;
import com.media.cms.model.dto.ContentCreateRequest;
import com.media.cms.model.dto.ContentUpdateRequest;
import com.media.cms.model.entity.Content;
import com.media.cms.model.entity.ContentStatus;
import java.security.Principal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

public interface ContentController {

    @PostMapping
    ResponseEntity<Content> create(ContentCreateRequest request, Principal principal);
    ResponseEntity<Content> update(Long id, ContentUpdateRequest request);
    ResponseEntity<Void> delete(Long id);
    ResponseEntity<List<Content>> all();
    ResponseEntity<Content> byId(Long id);
    ResponseEntity<List<Content>> byStatus(ContentStatus status);
    ResponseEntity<List<Content>> byCreator(Long creatorId);
    ResponseEntity<Content> assignChannels(Long id, ChannelAssignRequest request);
}
