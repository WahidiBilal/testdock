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
import com.example.employee_management_application.service.DepartmentService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DepartmentControllerIT extends TestContainerCon {

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@BeforeEach
	public void setUp() {
		departmentService.getAllDepartments()
				.forEach(department -> departmentService.deleteDepartment(department.getDid()));
	}

	@AfterEach
	public void cleanUp() {

		departmentService.getAllDepartments()
				.forEach(department -> departmentService.deleteDepartment(department.getDid()));
	}

	@Test
	void testGetAllDepartments() {
		// Given
		DepartmentDTO department1 = new DepartmentDTO();
		department1.setDname("IT");
		departmentService.createDepartment(department1);

		DepartmentDTO department2 = new DepartmentDTO();
		department2.setDname("Sales");
		departmentService.createDepartment(department2);

		// When
		ResponseEntity<DepartmentDTO[]> response = restTemplate
				.getForEntity("http://localhost:" + port + "/api/departments/getAll", DepartmentDTO[].class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DepartmentDTO[] departments = response.getBody();
		assertThat(departments).hasSize(2);
		assertThat(departments[0].getDname()).isEqualTo("IT");
		assertThat(departments[1].getDname()).isEqualTo("Sales");
	}

	@Test
	void testCreateDepartment() {
		// Given
		DepartmentDTO departmentDTO = new DepartmentDTO();
		departmentDTO.setDname("IT");

		// When
		ResponseEntity<DepartmentDTO> response = restTemplate.postForEntity(
				"http://localhost:" + port + "/api/departments/create", departmentDTO, DepartmentDTO.class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		DepartmentDTO createdDepartment = response.getBody();
		assertThat(createdDepartment).isNotNull();
		assertThat(createdDepartment.getDname()).isEqualTo("IT");

		// Assert
		List<DepartmentDTO> departments = departmentService.getAllDepartments();
		assertThat(departments).hasSize(1);
		assertThat(departments.get(0).getDname()).isEqualTo("IT");
	}

	@Test
	void testGetDepartmentById_Success() {
		// Given
		DepartmentDTO departmentDTO = new DepartmentDTO();
		departmentDTO.setDname("IT");
		DepartmentDTO createdDepartment = departmentService.createDepartment(departmentDTO);

		// When
		ResponseEntity<DepartmentDTO> response = restTemplate.getForEntity(
				"http://localhost:" + port + "/api/departments/" + createdDepartment.getDid(), DepartmentDTO.class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DepartmentDTO retrievedDepartment = response.getBody();
		assertThat(retrievedDepartment).isNotNull();
		assertThat(retrievedDepartment.getDid()).isEqualTo(createdDepartment.getDid());
		assertThat(retrievedDepartment.getDname()).isEqualTo("IT");
	}

	@Test
	void testDeleteDepartmentById_Success() {
		// Given
		DepartmentDTO departmentDTO = new DepartmentDTO();
		departmentDTO.setDname("IT");
		DepartmentDTO createdDepartment = departmentService.createDepartment(departmentDTO);

		// When
		restTemplate.delete("http://localhost:" + port + "/api/departments/" + createdDepartment.getDid());

		// Then
		List<DepartmentDTO> departments = departmentService.getAllDepartments();
		assertThat(departments).isEmpty();
	}
}