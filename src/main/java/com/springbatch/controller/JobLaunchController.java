package com.springbatch.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
public class JobLaunchController {

	private static final Logger log = LoggerFactory.getLogger(JobLaunchController.class);

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("firstJob")
	private Job firstJob;

	@GetMapping("/launchJob/{jobName}")
	public ResponseEntity<String> launchJob(@PathVariable("jobName") String jobName) {

		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("startAt", System.currentTimeMillis()).toJobParameters();

		if (jobName.equalsIgnoreCase("firstJob")) {
			try {
				log.info("Before Job run..");
				jobLauncher.run(firstJob, jobParameters);
				log.info("Job launched successfully with parameters: {}", jobParameters);
				return ResponseEntity.ok("Job launched successfully");
			} catch (JobExecutionException e) {
				log.error("Error launching job: {}", e.getMessage(), e);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error launching job: " + e.getMessage());
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide correct jobName");
	}
}

