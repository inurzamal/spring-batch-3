package com.springbatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final ConfigurableApplicationContext context;

    @Autowired
    public JobCompletionNotificationListener(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @EventListener
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOGGER.info("Job completed successfully---: {}", jobExecution.getJobInstance().getJobName());
            // Perform cleanup or additional tasks
            SpringApplication.exit(context, () -> 0); // Exit with success status
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            LOGGER.error("Job failed: {}", jobExecution.getJobInstance().getJobName());
            // Handle job failure
            SpringApplication.exit(context, () -> 1); // Exit with failure status
        }
    }
}

