package com.springbatch.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

	private final DataSource dataSource;

	public BatchConfiguration(DataSource dataSource) {
		this.dataSource = dataSource;
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

	@Bean
	public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
		SimpleJobLauncher launcher = new SimpleJobLauncher();
		launcher.setJobRepository(jobRepository);
		launcher.setTaskExecutor(new SyncTaskExecutor());
		launcher.afterPropertiesSet();
		return launcher;
	}

}
