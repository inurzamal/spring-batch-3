package com.springbatch.service;

import com.springbatch.entity.Employee;
import com.springbatch.repository.EmployeeRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class MyTasklet implements Tasklet {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Long id = generateId(); // Generate an ID (you may use your preferred method for this)
        String name = "John"; // Replace with your actual name
        String city = "New York"; // Replace with your actual city

//        jdbcTemplate.update("INSERT INTO EMPLOYEE_DETAILS (id, name, city) VALUES (?, ?, ?)", id, name, city);

        List<Employee> employees = jdbcTemplate.query("SELECT * FROM EMPLOYEE_DETAILS",
                (rs, rowNum) -> {
                    Employee employee = new Employee();
                    employee.setId(rs.getLong("id"));
                    employee.setName(rs.getString("name"));
                    employee.setCity(rs.getString("city"));
                    return employee;
                });

        System.out.println("Fetched Employees:");
        employees.forEach(System.out::println);

        return RepeatStatus.FINISHED;
    }

    private Long generateId() {
        // Implement your logic to generate a unique ID, e.g., using UUID.randomUUID().getMostSignificantBits()
        return UUID.randomUUID().getMostSignificantBits();
    }
}
