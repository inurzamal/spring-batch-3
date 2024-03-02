package com.springbatch.config;

import com.springbatch.service.MyTasklet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = "firstJob")
public class BatchConfiguration {

	private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final DataSource dataSource;
	private final MyTasklet myTasklet;

	@Autowired
	public BatchConfiguration(
			JobBuilderFactory jobBuilderFactory,
			StepBuilderFactory stepBuilderFactory,
			DataSource dataSource,
			MyTasklet myTasklet
	) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.dataSource = dataSource;
		this.myTasklet = myTasklet;
	}


	@Bean
	public Step myTaskletStep(StepBuilderFactory stepBuilderFactory, MyTasklet myTasklet) {
		return stepBuilderFactory.get("myTaskletStep")
				.tasklet(myTasklet)
				.build();
	}

	@Bean(name = "jobRepository")
	public JobRepository getJobRepository() throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setDataSource(dataSource);
		factory.setTransactionManager(transactionManager(dataSource));
		factory.setTablePrefix("PCCS_BATCH_");
		factory.setIsolationLevelForCreate("ISOLATION_DEFAULT");
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	@Bean
	public JobBuilderFactory jobBuilderFactory(JobRepository jobRepository) {
		return new JobBuilderFactory(jobRepository);
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public StepBuilderFactory stepBuilderFactory(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilderFactory(jobRepository,transactionManager);
	}

	@Bean
	public Job firstJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, MyTasklet myTasklet) {
		return jobBuilderFactory.get("firstJob")
				.incrementer(new RunIdIncrementer())
				.start(myTaskletStep(stepBuilderFactory, myTasklet))
				.build();
	}

	@Bean
	public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
		SimpleJobLauncher launcher = new SimpleJobLauncher();
		launcher.setJobRepository(jobRepository);
		launcher.setTaskExecutor(new SimpleAsyncTaskExecutor());
		launcher.afterPropertiesSet();
		return launcher;
	}

}
