package com.example.employee_management_application.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.example.employee_management_application.conn.TestContainerCon;
import com.example.employee_management_application.dao.EmployeeRepository;
import com.example.employee_management_application.dto.DepartmentDTO;
import com.example.employee_management_application.dto.EmployeeDTO;
import com.example.employee_management_application.service.DepartmentService;
import com.example.employee_management_application.service.EmployeeService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeWebControllerIT extends TestContainerCon {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private EmployeeRepository employeeRepository;

	@AfterEach
	public void tearDown() {

		employeeRepository.deleteAll();

		// Log database state before checking emptiness
		System.out.println("Remaining Employees: " + employeeRepository.findAll());
	}

	private String getBaseUrl() {
		return "http://localhost:" + port + "/employees";
	}

	@Test
	void testListEmployees() {
		// Given
		DepartmentDTO department = new DepartmentDTO();
		department.setDname("HR");
		DepartmentDTO savedDepartment = departmentService.createDepartment(department);

		// Create an employee
		EmployeeDTO employee = new EmployeeDTO();
		employee.setEname("employee1");
		employee.setEage(30);
		employee.setEmail("employee1@example.com");
		employee.setEsalary(60000.00);
		employee.setDepartmentId(savedDepartment.getDid());
		employeeService.createEmployee(employee);

		// When
		ResponseEntity<String> response = restTemplate.getForEntity(getBaseUrl(), String.class);
		// Then
		assertThat(response.getBody()).contains("employee1");
	}

}
