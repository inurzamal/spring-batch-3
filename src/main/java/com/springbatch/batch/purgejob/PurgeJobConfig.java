package com.springbatch.batch.purgejob;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PurgeJobConfig {


    private Step purgeJobStep(StepBuilderFactory stepBuilderFactory, PurgeJobTasklet purgeJobTasklet) {
        return stepBuilderFactory.get("purgeJobStep")
                .tasklet(purgeJobTasklet)
                .build();
    }

    private Step purgeEmailTaskletStep(StepBuilderFactory stepBuilderFactory, PurgeEmailTasklet emailTasklet) {
        return stepBuilderFactory.get("purgeEmailTaskletStep")
                .tasklet(emailTasklet)
                .build();
    }

    @Bean(name = "purgeJob")
    public Job purgeJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, PurgeJobTasklet myTasklet, PurgeEmailTasklet emailTasklet) {
        return jobBuilderFactory.get("purgeJob")
                .incrementer(new RunIdIncrementer())
                .start(purgeJobStep(stepBuilderFactory, myTasklet))
                .next(purgeEmailTaskletStep(stepBuilderFactory,emailTasklet))
                .build();
    }
}
