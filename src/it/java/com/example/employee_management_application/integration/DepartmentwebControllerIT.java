package com.example.employee_management_application.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.employee_management_application.conn.TestContainerCon;
import com.example.employee_management_application.dto.DepartmentDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DepartmentwebControllerIT extends TestContainerCon {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String getBaseUrl() {
		return "http://localhost:" + port + "/departments";
	}

	@Test
	void testCreateDepartment() {
		// Given
		DepartmentDTO departmentDTO = new DepartmentDTO();
		departmentDTO.setDname("IT");
		departmentDTO.setEmployees(Collections.emptyList());

		// When
		ResponseEntity<Void> response = restTemplate.postForEntity(getBaseUrl() + "/create", departmentDTO, Void.class);

		// Then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
		assertThat(response.getHeaders().getLocation().toString()).endsWith("/departments");
	}

}
