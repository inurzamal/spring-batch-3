package com.springbatch.batch.studentemployeejob;

import com.springbatch.entity.Employee;
import com.springbatch.entity.Student;
import com.springbatch.repository.EmployeeRepository;
import com.springbatch.repository.StudentRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;

@Configuration
public class StudentEmployeeJobConfiguration {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    public ItemProcessor<Student, Employee> studentToEmployeeProcessor;

    @Bean
    public ItemReader<Student> studentItemReader() throws Exception {
        RepositoryItemReader<Student> itemReader = new RepositoryItemReader<>();
        itemReader.setRepository(studentRepository);
        itemReader.setMethodName("findAll");
        itemReader.setPageSize(100);
        itemReader.setSort(Collections.singletonMap("studentName", Sort.Direction.ASC));
        itemReader.afterPropertiesSet();
        return itemReader;
    }

    @Bean
    public ItemWriter<Employee> employeeJpaItemWriter() {
        return items -> employeeRepository.saveAll(items);
    }

    @Bean
    public Step studentEmployeeStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) throws Exception {
        return new StepBuilder("studentEmployeeStep", jobRepository)
                .<Student,Employee>chunk(10, platformTransactionManager)
                .reader(studentItemReader())
                .processor(studentToEmployeeProcessor)
                .writer(employeeJpaItemWriter())
                .build();
    }

    @Bean(name = "studentEmployeeJob")
    public Job studentEmployeeJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new JobBuilder("studentEmployeeJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(studentEmployeeStep(jobRepository, transactionManager))
                .build();
    }

}
