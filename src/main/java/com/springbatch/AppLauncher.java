package com.springbatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppLauncher implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppLauncher.class);

	@Value("${spring.batch.job.names:none}")
	private String jobName;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("finalAnalysisJob")
	private Job individualFinalAnalysisJob;

	@Autowired
	@Qualifier("purgeJob")
	private Job purgeEscrowAnalysisJob;

	@Autowired
	@Qualifier("studentEmployeeJob")
	private Job studentEmployeeJob;

	public static void main(String[] args) {
		SpringApplication.run(AppLauncher.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Job jobToRun;

		if ("individualFinalAnalysisJob".equalsIgnoreCase(jobName)) {
			jobToRun = individualFinalAnalysisJob;
		} else if ("purgeEscrowAnalysisJob".equalsIgnoreCase(jobName)) {
			jobToRun = purgeEscrowAnalysisJob;
		} else if ("studentEmployeeJob".equalsIgnoreCase(jobName)) {
			jobToRun = studentEmployeeJob;
		} else {
			throw new IllegalArgumentException("Invalid job specified: " + jobName);
		}

		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("startAt", System.currentTimeMillis())
				.toJobParameters();

		try {
			jobLauncher.run(jobToRun, jobParameters);
			System.exit(0);
		} catch (Exception e) {
			LOGGER.error(String.format("Error Launching Job: %s Exception: %s", jobName, e.getMessage()), e);
			System.exit(1);
		}
	}
}
