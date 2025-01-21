package com.example.employee_management_application.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.employee_management_application.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
