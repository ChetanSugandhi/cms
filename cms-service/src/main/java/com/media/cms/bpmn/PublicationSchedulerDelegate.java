package com.media.cms.bpmn;

import com.media.cms.exception.ResourceNotFoundException;
import com.media.cms.model.entity.Content;
import com.media.cms.model.entity.ContentStatus;
import com.media.cms.repository.ContentRepository;
import java.time.LocalDateTime;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("publicationSchedulerDelegate")
public class PublicationSchedulerDelegate implements JavaDelegate {

    private final ContentRepository contentRepository;

    public PublicationSchedulerDelegate(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public void execute(DelegateExecution execution) {
        Long contentId = ((Number) execution.getVariable("contentId")).longValue();
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found: " + contentId));

        if (content.getPublicationDate() == null) {
            content.setPublicationDate(LocalDateTime.now().plusDays(1));
        }
        content.setStatus(ContentStatus.SCHEDULED);
        contentRepository.save(content);
    }
}
