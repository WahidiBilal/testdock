package com.example.employee_management_application.dto;

import java.util.Collections;
import java.util.List;

import com.example.employee_management_application.model.Department;

public class DepartmentDTO {
	private Integer did;
	private String dname;
	private List<EmployeeDTO> employees = Collections.emptyList();

	public DepartmentDTO() {
	}

	public DepartmentDTO(String dname, List<EmployeeDTO> employees) {
		this.dname = dname;

	}

	// Getters and Setters

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public List<EmployeeDTO> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeDTO> employees) {
		this.employees = employees;
	}

	public static DepartmentDTO fromEntity(Department department) {
		DepartmentDTO departmentDTO = new DepartmentDTO();
		departmentDTO.setDid(department.getDid());
		departmentDTO.setDname(department.getDname());
		departmentDTO.setEmployees(department.getEmployees().stream().map(EmployeeDTO::fromEntity).toList());
		return departmentDTO;
	}
}
