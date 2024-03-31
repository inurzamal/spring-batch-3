package com.springbatch.batch.finalanalysisjob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@Service
public class FinalAnalysisEmailTasklet implements Tasklet {

    public static final Logger log = LoggerFactory.getLogger(FinalAnalysisEmailTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        sendMail();
        return RepeatStatus.FINISHED;
    }

    private void sendMail() {
        log.info("sending mail..TODO..finalAnalysisJob");
    }
}
