package com.media.cms.controller.impl;

import com.media.cms.controller.ContentController;
import com.media.cms.exception.BadRequestException;
import com.media.cms.model.dto.ChannelAssignRequest;
import com.media.cms.model.dto.ContentCreateRequest;
import com.media.cms.model.dto.ContentUpdateRequest;
import com.media.cms.model.entity.Content;
import com.media.cms.model.entity.ContentStatus;
import com.media.cms.model.entity.User;
import com.media.cms.repository.UserRepository;
import com.media.cms.service.ContentService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/content")
public class ContentControllerImpl implements ContentController {

    private final ContentService contentService;
    private final UserRepository userRepository;

    public ContentControllerImpl(ContentService contentService, UserRepository userRepository) {
        this.contentService = contentService;
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('CONTENT_CREATOR','MANAGER')")
    public ResponseEntity<Content> create(@Valid @RequestBody ContentCreateRequest request, Principal principal) {
//        Long creatorId = userRepository.findByEmail(principal.getName())
//                .orElseThrow(() -> new BadRequestException("Authenticated user not found"))
//                .getId();
//      User user = (User) principal;
      String email = principal.getName();

      User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new BadRequestException("Authenticated user not found"));

      return ResponseEntity.ok(contentService.create(request, user.getId()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CONTENT_CREATOR','EDITOR','MANAGER')")
    public ResponseEntity<Content> update(@PathVariable Long id, @RequestBody ContentUpdateRequest request) {
        return ResponseEntity.ok(contentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CONTENT_CREATOR, 'EDITOR','MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CONTENT_CREATOR','EDITOR','MARKETING','MANAGER')")
    public ResponseEntity<List<Content>> all() {
        return ResponseEntity.ok(contentService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CONTENT_CREATOR','EDITOR','MARKETING','MANAGER')")
    public ResponseEntity<Content> byId(@PathVariable Long id) {
        return ResponseEntity.ok(contentService.getById(id));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('CONTENT_CREATOR','EDITOR','MARKETING','MANAGER')")
    public ResponseEntity<List<Content>> byStatus(@PathVariable ContentStatus status) {
        return ResponseEntity.ok(contentService.getByStatus(status));
    }

    @GetMapping("/creator/{creatorId}")
    @PreAuthorize("hasAnyRole('CONTENT_CREATOR','EDITOR','MANAGER')")
    public ResponseEntity<List<Content>> byCreator(@PathVariable Long creatorId) {
        return ResponseEntity.ok(contentService.getByCreator(creatorId));
    }

    @PostMapping("/{id}/channels")
    @PreAuthorize("hasAnyRole('EDITOR','MANAGER')")
    public ResponseEntity<Content> assignChannels(@PathVariable Long id,
                                                  @Valid @RequestBody ChannelAssignRequest request) {
        return ResponseEntity.ok(contentService.assignChannels(id, request.getChannelIds().stream().toList()));
    }
}
