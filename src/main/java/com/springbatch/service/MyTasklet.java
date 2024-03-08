package com.springbatch.service;

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
public class MyTasklet implements Tasklet {

    public static final Logger log = LoggerFactory.getLogger(MyTasklet.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

//        Employee newEmployee = new Employee();
//        newEmployee.setName("Rahul");
//        newEmployee.setCity("Guwahati");
//        employeeRepository.save(newEmployee);
//        List<Employee> employees = employeeRepository.findAll();
//        employees.forEach(employee -> log.info("Employee: {}", employee));

        employeeRepository.deleteEmployeesByCityNewYork(); //Purge Job

        return RepeatStatus.FINISHED;
    }

}
