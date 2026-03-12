package com.media.cms.service.impl;

import com.media.cms.exception.BadRequestException;
import com.media.cms.exception.ResourceNotFoundException;
import com.media.cms.model.entity.ApprovalWorkflow;
import com.media.cms.model.entity.Content;
import com.media.cms.model.entity.ContentStatus;
import com.media.cms.model.entity.User;
import com.media.cms.model.entity.WorkflowStatus;
import com.media.cms.repository.ApprovalWorkflowRepository;
import com.media.cms.repository.ContentRepository;
import com.media.cms.repository.UserRepository;
import com.media.cms.service.ApprovalWorkflowService;
import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApprovalWorkflowServiceImpl implements ApprovalWorkflowService {

    private final ApprovalWorkflowRepository workflowRepository;
    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final RuntimeService runtimeService;
    private final TaskService taskService;

    public ApprovalWorkflowServiceImpl(ApprovalWorkflowRepository workflowRepository,
                                       ContentRepository contentRepository,
                                       UserRepository userRepository,
                                       RuntimeService runtimeService,
                                       TaskService taskService) {
        this.workflowRepository = workflowRepository;
        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    @Override
    @Transactional
    public ApprovalWorkflow startWorkflow(Long contentId, Long assignedEditorId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found: " + contentId));

        User editor = null;
        if (assignedEditorId != null) {
            editor = userRepository.findById(assignedEditorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Assigned user not found: " + assignedEditorId));
        }

        Map<String, Object> vars = new HashMap<>();
        vars.put("contentId", contentId);
        vars.put("approved", false);
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("contentApprovalProcess", vars);

        content.setStatus(ContentStatus.UNDER_REVIEW);
        contentRepository.save(content);

        ApprovalWorkflow workflow = workflowRepository.findByContentId(contentId).orElse(new ApprovalWorkflow());
        workflow.setContent(content);
        workflow.setAssignedUser(editor);
        workflow.setCurrentStage(" ");
        workflow.setStatus(WorkflowStatus.PENDING);
        workflow.setProcessInstanceId(instance.getProcessInstanceId());
        return workflowRepository.save(workflow);
    }

    @Override
    @Transactional
    public ApprovalWorkflow approve(Long contentId, Long approverId) throws Exception {
        return complete(contentId, approverId, true);
    }

    @Override
    @Transactional
    public ApprovalWorkflow reject(Long contentId, Long approverId) throws Exception {
        return complete(contentId, approverId, false);
    }

    @Override
    public ApprovalWorkflow getByContentId(Long contentId) {
        return workflowRepository.findByContentId(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow not found for content: " + contentId));
    }

    private ApprovalWorkflow complete(Long contentId, Long approverId, boolean approved) throws Exception {
        ApprovalWorkflow workflow = getByContentId(contentId);
        User approver = userRepository.findById(approverId)
                .orElseThrow(() -> new ResourceNotFoundException("Approver not found: " + approverId));

        Task task = taskService.createTaskQuery()
                .processInstanceId(workflow.getProcessInstanceId())
                .active()
                .singleResult();

        if (task == null) {
            throw new BadRequestException("No active task for workflow");
        }

        Map<String, Object> vars = new HashMap<>();
        vars.put("approved", approved);
        taskService.complete(task.getId(), vars);

        Content content = workflow.getContent();
        workflow.setAssignedUser(approver);

        if(workflow.getCurrentStage().equals("EDITOR_REVIEW")) {
          if (approved) {
            workflow.setStatus(WorkflowStatus.APPROVED);
            workflow.setCurrentStage("SCHEDULED_AND_MARKETING_NOTIFIED");
            content.setStatus(ContentStatus.APPROVED);
          } else {
            workflow.setStatus(WorkflowStatus.REJECTED);
            workflow.setCurrentStage("REJECTED");
            content.setStatus(ContentStatus.REJECTED);
          }
        }
        else if(workflow.getCurrentStage().equals("SCHEDULED_AND_MARKETING_NOTIFIED")) {
          throw new Exception("Content is already Scheduled and its status is Approved !");
        }

        contentRepository.save(content);
        return workflowRepository.save(workflow);
    }
}
