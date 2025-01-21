package com.example.employee_management_application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.employee_management_application.dao.DepartmentRepository;
import com.example.employee_management_application.dao.EmployeeRepository;
import com.example.employee_management_application.dto.EmployeeDTO;
import com.example.employee_management_application.exception.CustomCreateException;
import com.example.employee_management_application.model.Department;
import com.example.employee_management_application.model.Employee;

class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private DepartmentRepository departmentRepository;

	@InjectMocks
	private EmployeeService employeeService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllEmployees() {
		// Given
		Department department = new Department("IT");
		department.setDid(1);

		Employee employee1 = new Employee("employee1", 25, "employee1@example.com", 50000.0, department);
		employee1.setEid(1);

		Employee employee2 = new Employee("employee2", 30, "employee2@example.com", 60000.0, department);
		employee2.setEid(2);

		// When
		when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

		// Then
		List<EmployeeDTO> result = employeeService.getAllEmployees();

		assertEquals(2, result.size());
		assertEquals("employee1", result.get(0).getEname());
		assertEquals("employee2", result.get(1).getEname());

		// Verify
		verify(employeeRepository, times(1)).findAll();
	}

	@Test
	void testGetAllEmployees_EmptyList() {
		// Given
		when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

		// When
		List<EmployeeDTO> result = employeeService.getAllEmployees();

		// Then
		assertEquals(0, result.size());

		// Verify
		verify(employeeRepository, times(1)).findAll();
	}

	@Test
	void testCreateEmployee_Success() {
		// Given
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEname("employee1");
		employeeDTO.setEage(30);
		employeeDTO.setEmail("employee1@example.com");
		employeeDTO.setEsalary(50000.0);
		employeeDTO.setDepartmentId(1);

		Department department = new Department("IT");
		department.setDid(1);

		Employee employee = new Employee("employee1", 30, "employee1@example.com", 50000.0, department);
		employee.setEid(1);

		Employee savedEmployee = new Employee("employee1", 30, "employee1@example.com", 50000.0, department);
		savedEmployee.setEid(1);

		when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
		when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

		// When
		EmployeeDTO result = employeeService.createEmployee(employeeDTO);

		// Then
		assertEquals(1, result.getEid());
		assertEquals("employee1", result.getEname());
		assertEquals(30, result.getEage());
		assertEquals("employee1@example.com", result.getEmail());
		assertEquals(50000.0, result.getEsalary());
		assertEquals(1, result.getDepartmentId());

		// Verify
		verify(departmentRepository, times(1)).findById(1);
		verify(employeeRepository, times(1)).save(any(Employee.class));
	}

	@Test
	void testCreateEmployee_DepartmentNotFound() {
		// Given
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEname("employee1");
		employeeDTO.setEage(30);
		employeeDTO.setEmail("employee1@example.com");
		employeeDTO.setEsalary(50000.0);
		employeeDTO.setDepartmentId(1);

		when(departmentRepository.findById(1)).thenReturn(Optional.empty());

		// When
		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> employeeService.createEmployee(employeeDTO));
		assertEquals("Department not found", exception.getMessage());

		// Verify
		verify(departmentRepository, times(1)).findById(1);
		verify(employeeRepository, never()).save(any(Employee.class));
	}

	@Test
	void testCreateEmployee_FailedToCreate() {
		// Given
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEname("employee2");
		employeeDTO.setEage(28);
		employeeDTO.setEmail("employee2@example.com");
		employeeDTO.setEsalary(60000.0);
		employeeDTO.setDepartmentId(1);

		Department department = new Department("IT");

		when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
		when(employeeRepository.save(any(Employee.class))).thenReturn(null);

		// When
		RuntimeException exception = assertThrows(CustomCreateException.class,
				() -> employeeService.createEmployee(employeeDTO));
		assertEquals("Failed to create employee", exception.getMessage());

		// Verify
		verify(departmentRepository, times(1)).findById(1);
		verify(employeeRepository, times(1)).save(any(Employee.class));
	}

	@Test
	void testCreateEmployee_HandleNull() {
		// Given
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEname(null);
		employeeDTO.setEage(null);
		employeeDTO.setEmail(null);
		employeeDTO.setEsalary(null);
		employeeDTO.setDepartmentId(1);

		Department department = new Department("IT");
		department.setDid(1);

		Employee savedEmployee = new Employee();
		savedEmployee.setEid(1);
		savedEmployee.setEname(null);
		savedEmployee.setEage(null);
		savedEmployee.setEmail(null);
		savedEmployee.setEsalary(null);
		savedEmployee.setDepartment(department);

		when(departmentRepository.findById(1)).thenReturn(Optional.of(department));
		when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

		// When
		EmployeeDTO result = employeeService.createEmployee(employeeDTO);

		// Then
		assertEquals(null, result.getEname());
		assertEquals(null, result.getEage());
		assertEquals(null, result.getEmail());
		assertEquals(null, result.getEsalary());
		assertEquals(1, result.getDepartmentId());

		// Verify
		verify(departmentRepository, times(1)).findById(1);
		verify(employeeRepository, times(1)).save(any(Employee.class));
	}

	@Test
	void testGetEmployeeById() {
		// Given
		Department department = new Department("IT");
		department.setDid(1);

		Employee employee = new Employee("employee1", 30, "employee1@example.com", 50000.0, department);
		employee.setEid(1);

		// When
		when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

		// Then
		EmployeeDTO result = employeeService.getEmployeeById(1);

		assertEquals(1, result.getEid());
		assertEquals("employee1", result.getEname());
		assertEquals(30, result.getEage());
		assertEquals("employee1@example.com", result.getEmail());
		assertEquals(50000.0, result.getEsalary());
		assertEquals(1, result.getDepartmentId());

		// Verify
		verify(employeeRepository, times(1)).findById(1);
	}

	@Test
	void testGetEmployeeById_EmployeeNotFound() {
		// Given
		when(employeeRepository.findById(1)).thenReturn(Optional.empty());

		// When
		CustomCreateException exception = assertThrows(CustomCreateException.class,
				() -> employeeService.getEmployeeById(1));
		assertEquals("Employee not found with id: 1", exception.getMessage());

		// Verify
		verify(employeeRepository, times(1)).findById(1);
	}

	@Test
	void testDeleteEmployee_Success() {
		// Given
		when(employeeRepository.existsById(1)).thenReturn(true);

		// When
		employeeService.deleteEmployee(1);

		// Then
		verify(employeeRepository, times(1)).deleteById(1);
	}

	@Test
	void testDeleteEmployee_NotFound() {
		// Given
		when(employeeRepository.existsById(1)).thenReturn(false);

		// When
		RuntimeException exception = assertThrows(RuntimeException.class, () -> employeeService.deleteEmployee(1));
		assertEquals("Employee not found with id: 1", exception.getMessage());

		// Verify
		verify(employeeRepository, never()).deleteById(1);
	}

}
