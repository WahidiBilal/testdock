package com.example.employee_management_application.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.employee_management_application.conn.TestContainerCon;
import com.example.employee_management_application.dto.DepartmentDTO;
import com.example.employee_management_application.dto.EmployeeDTO;
import com.example.employee_management_application.service.DepartmentService;
import com.example.employee_management_application.service.EmployeeService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerIT extends TestContainerCon {
	@LocalServerPort
	private int port;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private TestRestTemplate restTemplate;

	@BeforeEach
	void setUp() {
		deleteAllEmployeesAndDepartments();
	}

	@AfterEach
	void tearDown() {
		deleteAllEmployeesAndDepartments();
	}

	private void deleteAllEmployeesAndDepartments() {
		employeeService.getAllEmployees().forEach(employee -> employeeService.deleteEmployee(employee.getEid()));
		departmentService.getAllDepartments()
				.forEach(department -> departmentService.deleteDepartment(department.getDid()));
	}

	@Test
	void testGetAllEmployees() {
		// Given
		DepartmentDTO department = new DepartmentDTO();
		department.setDname("IT");
		DepartmentDTO savedDepartment = departmentService.createDepartment(department);

		EmployeeDTO employee1 = new EmployeeDTO();
		employee1.setEname("employee1");
		employee1.setEage(30);
		employee1.setEmail("employee1@example.com");
		employee1.setEsalary(50000.00);
		employee1.setDepartmentId(savedDepartment.getDid());
		employeeService.createEmployee(employee1);

		EmployeeDTO employee2 = new EmployeeDTO();
		employee2.setEname("employee2");
		employee2.setEage(25);
		employee2.setEmail("employee2@example.com");
		employee2.setEsalary(45000.00);
		employee2.setDepartmentId(savedDepartment.getDid());
		employeeService.createEmployee(employee2);

		// When
		ResponseEntity<EmployeeDTO[]> response = restTemplate
				.getForEntity("http://localhost:" + port + "/api/employees/getAll", EmployeeDTO[].class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		EmployeeDTO[] employees = response.getBody();
		assertThat(employees).hasSize(2);
		assertThat(employees[0].getEname()).isEqualTo("employee1");
		assertThat(employees[1].getEname()).isEqualTo("employee2");
	}

	@Test
	void testCreateEmployee() {
		// Given
		DepartmentDTO department = new DepartmentDTO();
		department.setDname("IT");
		DepartmentDTO savedDepartment = departmentService.createDepartment(department);

		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEname("employee1");
		employeeDTO.setEage(30);
		employeeDTO.setEmail("employee1@example.com");
		employeeDTO.setEsalary(50000.00);
		employeeDTO.setDepartmentId(savedDepartment.getDid());

		// When
		ResponseEntity<EmployeeDTO> response = restTemplate
				.postForEntity("http://localhost:" + port + "/api/employees/create", employeeDTO, EmployeeDTO.class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		EmployeeDTO createdEmployee = response.getBody();
		assertThat(createdEmployee).isNotNull();
		assertThat(createdEmployee.getEname()).isEqualTo("employee1");

		// Assert
		List<EmployeeDTO> employees = employeeService.getAllEmployees();
		assertThat(employees).hasSize(1);
		assertThat(employees.get(0).getEname()).isEqualTo("employee1");
	}

	@Test
	void testGetEmployeeById_Success() {
		// Given
		DepartmentDTO department = new DepartmentDTO();
		department.setDname("IT");
		DepartmentDTO savedDepartment = departmentService.createDepartment(department);

		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEname("employee1");
		employeeDTO.setEage(30);
		employeeDTO.setEmail("employee1@example.com");
		employeeDTO.setEsalary(50000.00);
		employeeDTO.setDepartmentId(savedDepartment.getDid());
		EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);

		// When
		ResponseEntity<EmployeeDTO> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/api/employees/" + createdEmployee.getEid(), EmployeeDTO.class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		EmployeeDTO retrievedEmployee = response.getBody();
		assertThat(retrievedEmployee).isNotNull();
		assertThat(retrievedEmployee.getEid()).isEqualTo(createdEmployee.getEid());
		assertThat(retrievedEmployee.getEname()).isEqualTo("employee1");
	}

}
