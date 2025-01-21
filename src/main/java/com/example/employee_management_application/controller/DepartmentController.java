package com.example.employee_management_application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee_management_application.dto.DepartmentDTO;
import com.example.employee_management_application.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

	private final DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
		List<DepartmentDTO> departments = departmentService.getAllDepartments();
		return new ResponseEntity<>(departments, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
		DepartmentDTO createdDepartment = departmentService.createDepartment(departmentDTO);
		return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
	}

	@GetMapping("/{did}")
	public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Integer did) {
		DepartmentDTO department = departmentService.getDepartmentById(did);
		if (department != null) {
			return new ResponseEntity<>(department, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{did}")
	public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Integer did,
			@RequestBody DepartmentDTO departmentDTO) {
		DepartmentDTO updatedDepartment = departmentService.updateDepartment(did, departmentDTO);
		return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
	}

	@DeleteMapping("/{did}")
	public ResponseEntity<Void> deleteDepartment(@PathVariable Integer did) {
		departmentService.deleteDepartment(did);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
