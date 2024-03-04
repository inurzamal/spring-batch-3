package com.springbatch.repository;

import com.springbatch.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Modifying
    @Query(value = "DELETE FROM EMPLOYEE_DETAILS WHERE CITY = 'New York'", nativeQuery = true)
    void deleteEmployeesByCityNewYork();
}
