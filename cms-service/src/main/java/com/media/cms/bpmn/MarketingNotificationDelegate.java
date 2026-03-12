package com.media.cms.bpmn;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("marketingNotificationDelegate")
public class MarketingNotificationDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(MarketingNotificationDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        Long contentId = ((Number) execution.getVariable("contentId")).longValue();
        log.info("Marketing notification triggered for contentId={}", contentId);
    }
}
