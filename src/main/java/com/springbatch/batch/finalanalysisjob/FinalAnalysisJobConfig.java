package com.springbatch.batch.finalanalysisjob;

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
public class FinalAnalysisJobConfig {


    private Step finalAnalysisTaskletStep(FinalAnalysisJobTasklet finalAnalysisJobTasklet, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("finalAnalysisTaskletStep", jobRepository)
                .tasklet(finalAnalysisJobTasklet, transactionManager)
                .build();
    }

    private Step finalAnalysisEmailTaskletStep(FinalAnalysisEmailTasklet finalAnalysisEmailTasklet, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("finalAnalysisEmailTaskletStep", jobRepository)
                .tasklet(finalAnalysisEmailTasklet, transactionManager)
                .build();
    }

    @Bean(name = "finalAnalysisJob")
    public Job finalAnalysisJob(JobRepository jobRepository,FinalAnalysisJobTasklet finalAnalysisJobTasklet, FinalAnalysisEmailTasklet finalAnalysisEmailTasklet, PlatformTransactionManager transactionManager) throws Exception {
        return new JobBuilder("finalAnalysisJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(finalAnalysisTaskletStep(finalAnalysisJobTasklet, jobRepository, transactionManager))
                .next(finalAnalysisEmailTaskletStep(finalAnalysisEmailTasklet, jobRepository, transactionManager))
                .build();
    }

}
