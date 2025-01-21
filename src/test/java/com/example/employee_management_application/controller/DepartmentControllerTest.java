package com.example.employee_management_application.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.example.employee_management_application.dto.DepartmentDTO;
import com.example.employee_management_application.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private DepartmentService departmentService;

	@InjectMocks
	private DepartmentController departmentController;

	@Test
	void testGetAllDepartment() throws Exception {
		// Given
		DepartmentDTO department1 = new DepartmentDTO();
		department1.setDid(1);
		department1.setDname("IT");

		DepartmentDTO department2 = new DepartmentDTO();
		department2.setDid(2);
		department2.setDname("Sales");

		List<DepartmentDTO> departments = Arrays.asList(department1, department2);

		// When
		when(departmentService.getAllDepartments()).thenReturn(departments);

		// Then
		mockMvc.perform(get("/api/departments/getAll")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].did", is(1))).andExpect(jsonPath("$[0].dname", is("IT")))
				.andExpect(jsonPath("$[1].did", is(2))).andExpect(jsonPath("$[1].dname", is("Sales")));
		// Verify
		verify(departmentService, times(1)).getAllDepartments();

	}

	@Test
	void testCreateDepartment() throws Exception {
		// Given
		DepartmentDTO departmentDTO = new DepartmentDTO();
		departmentDTO.setDname("IT");

		DepartmentDTO createdDepartment = new DepartmentDTO();
		createdDepartment.setDid(1);
		createdDepartment.setDname("IT");
		// When
		when(departmentService.createDepartment(any(DepartmentDTO.class))).thenReturn(createdDepartment);

		// Then
		mockMvc.perform(post("/api/departments/create").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(departmentDTO))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.did", is(1)))
				.andExpect(jsonPath("$.dname", is("IT")));
		// Verify
		verify(departmentService, times(1)).createDepartment(any(DepartmentDTO.class));
	}

	@Test
	void testGetDepartmentById_Success() throws Exception {
		// Given
		DepartmentDTO departmentDTO = new DepartmentDTO();
		departmentDTO.setDid(1);
		departmentDTO.setDname("IT");

		// When
		when(departmentService.getDepartmentById(1)).thenReturn(departmentDTO);

		// Then
		mockMvc.perform(get("/api/departments/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.did", is(1)))
				.andExpect(jsonPath("$.dname", is("IT")));

		// Verify
		verify(departmentService, times(1)).getDepartmentById(1);
	}

	@Test
	void testGetDepartmentById_NotFound() throws Exception {
		// When
		when(departmentService.getDepartmentById(anyInt())).thenReturn(null);

		// Then
		mockMvc.perform(get("/api/departments/1")).andExpect(status().isNotFound());

		// Verify
		verify(departmentService, times(1)).getDepartmentById(1);
	}

	@Test
	void testUpdateDepartment() throws Exception {
		// Given
		DepartmentDTO departmentDTO = new DepartmentDTO();
		departmentDTO.setDname("IT");

		DepartmentDTO updatedDepartment = new DepartmentDTO();
		updatedDepartment.setDid(1);
		updatedDepartment.setDname("HR");

		// When
		when(departmentService.updateDepartment(anyInt(), any(DepartmentDTO.class))).thenReturn(updatedDepartment);

		// Then
		mockMvc.perform(put("/api/departments/1").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(departmentDTO))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.did", is(1)))
				.andExpect(jsonPath("$.dname", is("HR")));

		// Verify
		verify(departmentService, times(1)).updateDepartment(anyInt(), any(DepartmentDTO.class));
	}

	@Test
	void testDeleteDepartment() throws Exception {
		// When
		mockMvc.perform(delete("/api/departments/1")).andExpect(status().isNoContent());

		// Verify
		verify(departmentService, times(1)).deleteDepartment(1);
	}

}
