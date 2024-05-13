package com.springbatch.processor;

import com.springbatch.entity.Employee;
import com.springbatch.entity.Student;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class StudentToEmployeeProcessor implements ItemProcessor<Student, Employee> {

    @Override
    public Employee process(Student student) throws Exception {
        if (student.getPercentageOfMarks() > 90) {
            Employee employee = new Employee();
            employee.setEmpName(student.getStudentName());
            employee.setEmpCity(student.getAddress().getCity());
            employee.setDegree(student.getDegree());
            employee.setSpecialization(student.getSpecialization());
            return employee;
        } else {
            return null;
        }
    }
}
