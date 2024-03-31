package com.springbatch.batch.purgejob;


import com.springbatch.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurgeJobTasklet implements Tasklet {

    public static final Logger log = LoggerFactory.getLogger(PurgeJobTasklet.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        employeeRepository.deleteEmployeesByCityNewYork(); //Purge Job

        return RepeatStatus.FINISHED;
    }

}
