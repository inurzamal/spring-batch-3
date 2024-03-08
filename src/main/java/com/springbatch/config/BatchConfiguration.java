package com.springbatch.config;

import com.springbatch.service.EmailTasklet;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@EnableTransactionManagement
public class BatchConfiguration {

	private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private MyTasklet myTasklet;
	@Autowired
	private EmailTasklet emailTasklet;


	@Bean
	public Step myTaskletStep(StepBuilderFactory stepBuilderFactory, MyTasklet myTasklet) {
		return stepBuilderFactory.get("myTaskletStep")
				.tasklet(myTasklet)
				.build();
	}

	@Bean
	public Step emailTaskletStep(StepBuilderFactory stepBuilderFactory, EmailTasklet emailTasklet) {
		return stepBuilderFactory.get("emailTaskletStep")
				.tasklet(emailTasklet)
				.build();
	}

	@Bean(name = "jobRepository")
	public JobRepository getJobRepository(PlatformTransactionManager transactionManager) throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setDataSource(dataSource);  // Use the common DataSource
		factory.setTransactionManager(transactionManager);  // Use the common TransactionManager
		factory.setTablePrefix("MCCS_BATCH_");
		factory.setIsolationLevelForCreate("ISOLATION_DEFAULT");
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	@Bean
	public JobBuilderFactory jobBuilderFactory(JobRepository jobRepository) {
		return new JobBuilderFactory(jobRepository);
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	@Bean
	public StepBuilderFactory stepBuilderFactory(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilderFactory(jobRepository,transactionManager);
	}

	@Bean(name = "firstJob")
	public Job firstJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, MyTasklet myTasklet, EmailTasklet emailTasklet) {
		return jobBuilderFactory.get("firstJob")
				.incrementer(new RunIdIncrementer())
				.start(myTaskletStep(stepBuilderFactory, myTasklet))
				.next(emailTaskletStep(stepBuilderFactory,emailTasklet))
				.build();
	}

	@Bean
	public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
		SimpleJobLauncher launcher = new SimpleJobLauncher();
		launcher.setJobRepository(jobRepository);
		launcher.setTaskExecutor(new SimpleAsyncTaskExecutor());
//		launcher.setTaskExecutor(new SyncTaskExecutor());
		launcher.afterPropertiesSet();
		return launcher;
	}

}
