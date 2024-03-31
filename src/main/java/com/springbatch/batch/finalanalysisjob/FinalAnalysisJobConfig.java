package com.springbatch.batch.finalanalysisjob;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FinalAnalysisJobConfig {


    private Step finalAnalysisTaskletStep(StepBuilderFactory stepBuilderFactory, FinalAnalysisJobTasklet finalAnalysisJobTasklet) {
        return stepBuilderFactory.get("finalAnalysisTaskletStep")
                .tasklet(finalAnalysisJobTasklet)
                .build();
    }

    private Step finalAnalysisEmailTaskletStep(StepBuilderFactory stepBuilderFactory, FinalAnalysisEmailTasklet finalAnalysisEmailTasklet) {
        return stepBuilderFactory.get("finalAnalysisEmailTaskletStep")
                .tasklet(finalAnalysisEmailTasklet)
                .build();
    }

    @Bean(name = "finalAnalysisJob")
    public Job finalAnalysisJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, FinalAnalysisJobTasklet finalAnalysisJobTasklet, FinalAnalysisEmailTasklet finalAnalysisEmailTasklet) {
        return jobBuilderFactory.get("finalAnalysisJob")
                .incrementer(new RunIdIncrementer())
                .start(finalAnalysisTaskletStep(stepBuilderFactory, finalAnalysisJobTasklet))
                .next(finalAnalysisEmailTaskletStep(stepBuilderFactory,finalAnalysisEmailTasklet))
                .build();
    }

}
