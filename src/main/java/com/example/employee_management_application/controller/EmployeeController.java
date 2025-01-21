package com.example.employee_management_application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee_management_application.dto.EmployeeDTO;
import com.example.employee_management_application.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
		List<EmployeeDTO> employees = employeeService.getAllEmployees();
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
		EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
		return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
	}

	@GetMapping("/{eid}")
	public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Integer eid) {
		EmployeeDTO employee = employeeService.getEmployeeById(eid);
		if (employee != null) {
			return new ResponseEntity<>(employee, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{eid}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Integer eid) {
		employeeService.deleteEmployee(eid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}