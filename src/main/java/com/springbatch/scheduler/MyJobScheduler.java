package com.springbatch.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class MyJobScheduler {

    private static final Logger log = LoggerFactory.getLogger(MyJobScheduler.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("jobName")
    private Job job;


    @Scheduled(cron = "0 0/1 * 1/1 * ?") // Cron expression for every 1 minute
    public void runJob() {

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
            log.info("Job launched successfully at scheduled interval");
        } catch (JobExecutionException e) {
            log.error("Error launching scheduled job: {}", e.getMessage(), e);
        }
    }
}
