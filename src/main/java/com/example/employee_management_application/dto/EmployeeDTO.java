package com.example.employee_management_application.dto;

import com.example.employee_management_application.model.Employee;

public class EmployeeDTO {
	private Integer eid;
	private String ename;
	private Integer eage;
	private String email;
	private Double esalary;
	private Integer departmentId;

	public EmployeeDTO() {
	}

	public EmployeeDTO(String ename, Integer eage, String email, Double esalary) {
		this.ename = ename;
		this.eage = eage;
		this.email = email;
		this.esalary = esalary;
	}

	// Getters and Setters

	public Integer getEid() {
		return eid;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public Integer getEage() {
		return eage;
	}

	public void setEage(Integer eage) {
		this.eage = eage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getEsalary() {
		return esalary;
	}

	public void setEsalary(Double esalary) {
		this.esalary = esalary;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public static EmployeeDTO fromEntity(Employee employee) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEid(employee.getEid());
		employeeDTO.setEname(employee.getEname());
		employeeDTO.setEage(employee.getEage());
		employeeDTO.setEmail(employee.getEmail());
		employeeDTO.setEsalary(employee.getEsalary());
		employeeDTO.setDepartmentId(employee.getDepartment().getDid());
		return employeeDTO;
	}

}
