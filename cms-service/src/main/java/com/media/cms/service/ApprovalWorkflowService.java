package com.media.cms.service;

import com.media.cms.model.entity.ApprovalWorkflow;

public interface ApprovalWorkflowService {
    ApprovalWorkflow startWorkflow(Long contentId, Long assignedEditorId);
    ApprovalWorkflow approve(Long contentId, Long approverId) throws Exception;
    ApprovalWorkflow reject(Long contentId, Long approverId) throws Exception;
    ApprovalWorkflow getByContentId(Long contentId);
}
