package com.example.employee_management_application.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "department")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer did;

	private String dname;
	@OneToMany(mappedBy = "department", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Employee> employees = new ArrayList<>();

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

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Department(String dname, List<Employee> employees) {
		this.dname = dname;
		this.employees = employees;
	}

	public Department(String dname) {
		this.dname = dname;
	}

	public Department() {

	}

	public Department(Integer did, String dname, List<Employee> employees) {
		super();
		this.did = did;
		this.dname = dname;
		this.employees = employees;
	}

}
