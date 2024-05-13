package com.springbatch.batch.purgejob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class PurgeJobConfig {


    private Step purgeJobStep(PurgeJobTasklet purgeJobTasklet, JobRepository jobRepository, PlatformTransactionManager jpaTransactionManager) {
        return new StepBuilder("purgeJobStep", jobRepository)
                .tasklet(purgeJobTasklet, jpaTransactionManager)
                .build();
    }

    private Step purgeEmailTaskletStep(PurgeEmailTasklet emailTasklet, JobRepository jobRepository, PlatformTransactionManager jpaTransactionManager) {
        return new StepBuilder("purgeEmailTaskletStep", jobRepository)
                .tasklet(emailTasklet, jpaTransactionManager)
                .build();
    }

    @Bean(name = "purgeJob")
    public Job purgeJob(JobRepository jobRepository, PurgeJobTasklet myTasklet, PurgeEmailTasklet emailTasklet, PlatformTransactionManager jpaTransactionManager) {
        return new JobBuilder("purgeJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(purgeJobStep(myTasklet,jobRepository,jpaTransactionManager))
                .next(purgeEmailTaskletStep(emailTasklet,jobRepository,jpaTransactionManager))
                .build();
    }
}
