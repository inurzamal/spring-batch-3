package com.springbatch.batch;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

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
    public void run(String... args) {
        // Define job parameters for finalAnalysisJob
        JobParameters finalAnalysisJobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        try {
            // Launch finalAnalysisJob
            jobLauncher.run(finalAnalysisJob, finalAnalysisJobParameters);
            System.out.println("finalAnalysisJob launched successfully.");
        } catch (JobExecutionException e) {
            System.err.println("Error launching finalAnalysisJob: " + e.getMessage());
            e.printStackTrace();
        }

        // Define job parameters for purgeJob
        JobParameters purgeJobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        try {
            // Launch purgeJob
            jobLauncher.run(purgeJob, purgeJobParameters);
            System.out.println("purgeJob launched successfully.");
        } catch (JobExecutionException e) {
            System.err.println("Error launching purgeJob: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
