package com.springbatch.batch.finalanalysisjob;

import com.springbatch.entity.Address;
import com.springbatch.entity.Student;
import com.springbatch.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FinalAnalysisJobTasklet implements Tasklet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinalAnalysisJobTasklet.class);

    private final StudentRepository studentRepository;

    public FinalAnalysisJobTasklet(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        Address address1 = new Address("Hyderabad", "123 Main St", "10001");
        Student student1 = new Student("Simran", "Bachelor of Science", "Computer Science", 95.5, address1);

        Address address2 = new Address("Hyderabad", "456 Elm St", "90001");
        Student student2 = new Student("Karan", "B.Tech", "Civil", 98.2, address2);

        List<Student> students = studentRepository.saveAll(Arrays.asList(student1, student2));

        students.forEach(student -> LOGGER.info("Saved Student: {}", student));
        LOGGER.info("Number of students saved: {}", students.size());

        return RepeatStatus.FINISHED;
    }
}
