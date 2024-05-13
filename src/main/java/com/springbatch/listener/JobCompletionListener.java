package com.springbatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionListener implements JobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        LOGGER.info("---Before Job Listener---");
        LOGGER.info("Job {} is starting at {}", jobExecution.getJobInstance().getJobName(), jobExecution.getStartTime());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        LOGGER.info("---After Job Listener---");
        LOGGER.info("Job {} completed with status {} at {}",
                jobExecution.getJobInstance().getJobName(), jobExecution.getStatus(), jobExecution.getEndTime());

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOGGER.info("Job completed successfully---: {}", jobExecution.getJobInstance().getJobName());
            System.exit(0);
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            LOGGER.error("Job failed: {}", jobExecution.getJobInstance().getJobName());
            System.exit(1);
        }

    }
}
