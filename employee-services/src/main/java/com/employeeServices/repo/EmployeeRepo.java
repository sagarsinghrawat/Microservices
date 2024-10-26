package com.employeeServices.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeeServices.entities.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
}
