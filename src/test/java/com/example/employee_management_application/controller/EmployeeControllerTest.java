package com.example.employee_management_application.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.employee_management_application.dto.EmployeeDTO;
import com.example.employee_management_application.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private EmployeeService employeeService;

	@InjectMocks
	private EmployeeController employeeController;

	@Test
	void testGetAllEmployees() throws Exception {
		// Given
		EmployeeDTO employee1 = new EmployeeDTO();
		employee1.setEid(1);
		employee1.setEname("employee1");

		EmployeeDTO employee2 = new EmployeeDTO();
		employee2.setEid(2);
		employee2.setEname("employee2");

		List<EmployeeDTO> employees = Arrays.asList(employee1, employee2);
		// When
		when(employeeService.getAllEmployees()).thenReturn(employees);

		// Then
		mockMvc.perform(get("/api/employees/getAll")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].eid", is(1))).andExpect(jsonPath("$[0].ename", is("employee1")))
				.andExpect(jsonPath("$[1].eid", is(2))).andExpect(jsonPath("$[1].ename", is("employee2")));
		// Verify
		verify(employeeService, times(1)).getAllEmployees();
	}

	@Test
	void testCreateEmployee() throws Exception {
		// Given
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEname("employee1");
		employeeDTO.setEage(30);
		employeeDTO.setEmail("employee1@example.com");
		employeeDTO.setEsalary(50000.0);
		employeeDTO.setDepartmentId(1);

		EmployeeDTO createdEmployee = new EmployeeDTO();
		createdEmployee.setEid(1);
		createdEmployee.setEname("employee1");
		// When
		when(employeeService.createEmployee(any(EmployeeDTO.class))).thenReturn(createdEmployee);

		// Then
		mockMvc.perform(post("/api/employees/create").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(employeeDTO))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.eid", is(1)))
				.andExpect(jsonPath("$.ename", is("employee1")));
		// Verify
		verify(employeeService, times(1)).createEmployee(any(EmployeeDTO.class));
	}

	@Test
	void testGetEmployeeById() throws Exception {
		// Given
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEid(1);
		employeeDTO.setEname("employee1");

		// When
		when(employeeService.getEmployeeById(1)).thenReturn(employeeDTO);

		// Then
		mockMvc.perform(get("/api/employees/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.eid", is(1)))
				.andExpect(jsonPath("$.ename", is("employee1")));
		// Verify
		verify(employeeService, times(1)).getEmployeeById(1);
	}

	@Test
	void testGetEmployeeById_NotFound() throws Exception {
		// Given
		// When
		when(employeeService.getEmployeeById(1)).thenReturn(null);

		// Then
		mockMvc.perform(get("/api/employees/1")).andExpect(status().isNotFound());
		// Verify
		verify(employeeService, times(1)).getEmployeeById(1);
	}

	@Test
	void testDeleteEmployee() throws Exception {
		// When
		mockMvc.perform(delete("/api/employees/1")).andExpect(status().isNoContent());
		// Verify
		verify(employeeService, times(1)).deleteEmployee(1);
	}
}
