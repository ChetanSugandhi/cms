package com.media.cms.controller;

import com.media.cms.model.dto.WorkflowActionRequest;
import com.media.cms.model.entity.ApprovalWorkflow;
import java.security.Principal;
import org.springframework.http.ResponseEntity;

public interface WorkflowController {
    ResponseEntity<ApprovalWorkflow> start(Long contentId, WorkflowActionRequest request);
    ResponseEntity<ApprovalWorkflow> action(Long contentId, WorkflowActionRequest request, Principal principal) throws Exception;
    ResponseEntity<ApprovalWorkflow> status(Long contentId);
}
