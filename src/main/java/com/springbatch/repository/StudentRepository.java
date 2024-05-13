package com.springbatch.repository;

import com.springbatch.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT * FROM students WHERE percentage_of_marks < 60", nativeQuery = true)
    List<Student> findAllStudentsWithPercentageLessThan60();

}

