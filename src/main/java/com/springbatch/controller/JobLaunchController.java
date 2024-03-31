package com.springbatch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JobLaunchController {

	private static final Logger log = LoggerFactory.getLogger(JobLaunchController.class);

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("finalAnalysisJob")
	private Job finalAnalysisJob;

	@Autowired
	@Qualifier("purgeJob")
	private Job purgeJob;


	@GetMapping("/launchJob/finalAnalysisJob")
	public ResponseEntity<String> finalAnalysis() {

		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("startAt", System.currentTimeMillis()).toJobParameters();

		try {
			log.info("Before Job run..");
			jobLauncher.run(finalAnalysisJob, jobParameters);
			log.info("Job launched successfully with parameters: {}", jobParameters);
			return ResponseEntity.ok("finalAnalysisJob launched successfully");
		} catch (JobExecutionException e) {
			log.error("Error launching job: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error launching finalAnalysisJob: " + e.getMessage());
		}
	}


	@GetMapping("/launchJob/purgeJob")
	public ResponseEntity<String> launchJob() {

		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("startAt", System.currentTimeMillis()).toJobParameters();

		try {
			log.info("Before Job run..");
			jobLauncher.run(purgeJob, jobParameters);
			log.info("Job launched successfully with parameters: {}", jobParameters);
			return ResponseEntity.ok("purgeJob launched successfully");
		} catch (JobExecutionException e) {
			log.error("Error launching job: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error launching purgeJob: " + e.getMessage());
		}
	}
}

