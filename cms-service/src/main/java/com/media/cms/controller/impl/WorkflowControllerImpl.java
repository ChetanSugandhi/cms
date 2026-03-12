package com.media.cms.controller.impl;

import com.media.cms.exception.BadRequestException;
import com.media.cms.model.dto.WorkflowActionRequest;
import com.media.cms.model.entity.ApprovalWorkflow;
import com.media.cms.repository.UserRepository;
import com.media.cms.service.ApprovalWorkflowService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.Locale;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowControllerImpl implements com.media.cms.controller.WorkflowController {

    private final ApprovalWorkflowService workflowService;
    private final UserRepository userRepository;

    public WorkflowControllerImpl(ApprovalWorkflowService workflowService, UserRepository userRepository) {
        this.workflowService = workflowService;
        this.userRepository = userRepository;
    }

    @PostMapping("/{contentId}/start")
    @PreAuthorize("hasAnyRole('CONTENT_CREATOR','EDITOR','MANAGER')")
    public ResponseEntity<ApprovalWorkflow> start (@PathVariable Long contentId,
                                                  @RequestBody(required = false) WorkflowActionRequest request) {
        Long assigned = request == null ? null : request.getAssignedUserId();
        return ResponseEntity.ok(workflowService.startWorkflow(contentId, assigned));
    }

    @PostMapping("/{contentId}/action")
    @PreAuthorize("hasAnyRole('EDITOR','MANAGER')")
    public ResponseEntity<ApprovalWorkflow> action(@PathVariable Long contentId,
                                                   @Valid @RequestBody WorkflowActionRequest request,
                                                   Principal principal) throws Exception {
        Long userId = userRepository.findByEmail(principal.getName()).orElseThrow().getId();
        String action = request.getAction().toLowerCase(Locale.ROOT);
        if ("approve".equals(action)) {
            return ResponseEntity.ok(workflowService.approve(contentId, userId));
        }
        if ("reject".equals(action)) {
            return ResponseEntity.ok(workflowService.reject(contentId, userId));
        }
        throw new BadRequestException("Supported actions: approve, reject");
    }

    @GetMapping("/{contentId}")
    @PreAuthorize("hasAnyRole('CONTENT_CREATOR','EDITOR','MARKETING','MANAGER')")
    public ResponseEntity<ApprovalWorkflow> status(@PathVariable Long contentId) {
        return ResponseEntity.ok(workflowService.getByContentId(contentId));
    }
}
