package com.teamD.RevTaskManagement.dao;

import com.teamD.RevTaskManagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee,Long> {
    Employee findByEmployeeName(String name);
    Employee findByEmail(String email);
}
