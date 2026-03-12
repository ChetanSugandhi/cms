package com.media.cms.repository;

import com.media.cms.model.entity.ApprovalWorkflow;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalWorkflowRepository extends JpaRepository<ApprovalWorkflow, Long> {
    Optional<ApprovalWorkflow> findByContentId(Long contentId);
    Optional<ApprovalWorkflow> findByProcessInstanceId(String processInstanceId);
}
