package com.springbatch.scheduler;
import com.springbatch.entity.Employee;
import com.springbatch.repository.EmployeeRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
public class MyJobScheduler {

    private static final Logger log = LoggerFactory.getLogger(MyJobScheduler.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

//    @Autowired
//    EmployeeRepository employeeRepository;

    @Scheduled(cron = "0 0/1 * 1/1 * ?") // Cron expression for every 1 minute
    public void runJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(job, jobParameters);
            log.info("Job launched successfully at scheduled interval");
        } catch (JobExecutionException e) {
            log.error("Error launching scheduled job: {}", e.getMessage(), e);
        }
    }
}
