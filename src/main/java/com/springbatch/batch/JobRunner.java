package com.springbatch.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobRunner implements CommandLineRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("finalAnalysisJob")
    private Job finalAnalysisJob;

    @Autowired
    @Qualifier("purgeJob")
    private Job purgeJob;

    @Override
    public void run(String... args) throws Exception {
        // Launch finalAnalysisJob
        JobParameters jobParameters1 = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(finalAnalysisJob, jobParameters1);

        // Launch purgeJob
        JobParameters jobParameters2 = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(purgeJob, jobParameters2);
    }
}

