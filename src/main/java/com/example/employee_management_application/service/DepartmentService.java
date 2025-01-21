package com.example.employee_management_application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employee_management_application.dao.DepartmentRepository;
import com.example.employee_management_application.dto.DepartmentDTO;
import com.example.employee_management_application.dto.EmployeeDTO;
import com.example.employee_management_application.exception.CustomCreateException;
import com.example.employee_management_application.model.Department;
import com.example.employee_management_application.model.Employee;

@Service
public class DepartmentService {

	private static final String DEPARTMENT_NOT_FOUND_MESSAGE = "Department not found with id: ";

	private final DepartmentRepository departmentRepository;

	public DepartmentService(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	public List<DepartmentDTO> getAllDepartments() {
		return departmentRepository.findAll().stream().map(DepartmentDTO::fromEntity).toList();
	}

	public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
		List<Employee> employees = departmentDTO.getEmployees().stream().map(this::convertEmployeeDTOToEntity).toList();

		Department department = new Department(departmentDTO.getDname(), employees);

		employees.forEach(employee -> employee.setDepartment(department));

		try {
			Department savedDepartment = departmentRepository.save(department);
			return DepartmentDTO.fromEntity(savedDepartment);
		} catch (Exception e) {
			throw new CustomCreateException("Failed to create department", e);
		}
	}

	public DepartmentDTO getDepartmentById(Integer did) {
		Department department = departmentRepository.findById(did)
				.orElseThrow(() -> new CustomCreateException(DEPARTMENT_NOT_FOUND_MESSAGE + did));
		return DepartmentDTO.fromEntity(department);
	}

	public DepartmentDTO updateDepartment(Integer did, DepartmentDTO departmentDTO) {
		Department existingDepartment = departmentRepository.findById(did)
				.orElseThrow(() -> new CustomCreateException(DEPARTMENT_NOT_FOUND_MESSAGE + did));

		existingDepartment.setDname(departmentDTO.getDname());

		List<Employee> employees = departmentDTO.getEmployees().stream().map(this::convertEmployeeDTOToEntity).toList();

		existingDepartment.setEmployees(employees);
		employees.forEach(employee -> employee.setDepartment(existingDepartment));

		Department updatedDepartment = departmentRepository.save(existingDepartment);
		return DepartmentDTO.fromEntity(updatedDepartment);
	}

	public void deleteDepartment(Integer did) {
		if (!departmentRepository.existsById(did)) {
			throw new CustomCreateException(DEPARTMENT_NOT_FOUND_MESSAGE + did);
		}
		departmentRepository.deleteById(did);
	}

	private Employee convertEmployeeDTOToEntity(EmployeeDTO employeeDTO) {

		return new Employee(employeeDTO.getEname(), employeeDTO.getEage(), employeeDTO.getEmail(),
				employeeDTO.getEsalary(), null);
	}

}
