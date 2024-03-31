package com.springbatch.batch.finalanalysisjob;

import com.springbatch.entity.Employee;
import com.springbatch.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinalAnalysisJobTasklet implements Tasklet {

    public static final Logger log = LoggerFactory.getLogger(FinalAnalysisJobTasklet.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        Employee newEmployee = new Employee();
        newEmployee.setName("Rahul");
        newEmployee.setCity("Guwahati");
        employeeRepository.save(newEmployee);

        List<Employee> employees = employeeRepository.findAll();
        employees.forEach(employee -> log.info("Employee: {}", employee));

        return RepeatStatus.FINISHED;
    }
}
