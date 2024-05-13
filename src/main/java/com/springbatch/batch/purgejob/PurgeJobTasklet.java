package com.springbatch.batch.purgejob;

import com.springbatch.entity.Student;
import com.springbatch.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurgeJobTasklet implements Tasklet {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurgeJobTasklet.class);

    private final StudentRepository studentRepository;

    public PurgeJobTasklet(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        try {
            List<Student> studentList = studentRepository.findAllStudentsWithPercentageLessThan60();

            if (!studentList.isEmpty()) {
                studentRepository.deleteAll(studentList);
                LOGGER.info("Number of Records deleted: {}", studentList.size());
            } else {
                LOGGER.info("No records found with percentage less than 60.");
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while deleting records: " + e.getMessage(), e);
        }

        return RepeatStatus.FINISHED;
    }

}
