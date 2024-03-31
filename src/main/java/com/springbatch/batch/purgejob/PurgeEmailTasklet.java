package com.springbatch.batch.purgejob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@Service
public class PurgeEmailTasklet implements Tasklet {

    public static final Logger log = LoggerFactory.getLogger(PurgeEmailTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        sendMail();
        return RepeatStatus.FINISHED;
    }

    private void sendMail() {
        log.info("sending mail..TODO..purgeJob");
    }
}
