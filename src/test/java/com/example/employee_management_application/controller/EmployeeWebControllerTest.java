package com.example.employee_management_application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.example.employee_management_application.dto.EmployeeDTO;
import com.example.employee_management_application.service.DepartmentService;
import com.example.employee_management_application.service.EmployeeService;

class EmployeeWebControllerTest {

	@Mock
	private EmployeeService employeeService;

	@Mock
	private DepartmentService departmentService;

	@Mock
	private Model model;

	@InjectMocks
	private EmployeeWebController employeeWebController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testListEmployees() {
		// Given
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEid(1);
		employeeDTO.setEname("employee1");
		when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList(employeeDTO));

		// When
		String viewName = employeeWebController.listEmployees(model);

		// Then
		assertEquals("employees/list", viewName);

		// Verify
		verify(employeeService, times(1)).getAllEmployees();
		verify(model, times(1)).addAttribute("employees", Collections.singletonList(employeeDTO));
	}

	@Test
	void testShowCreateForm() {
		// Given
		when(departmentService.getAllDepartments()).thenReturn(Collections.emptyList());

		// When
		String viewName = employeeWebController.showCreateForm(model);

		// Then
		assertEquals("employees/create", viewName);

		// Verify
		verify(model, times(1)).addAttribute("departments", Collections.emptyList());
	}

	@Test
	void testCreateEmployee() {
		// Given
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEname("employee1");
		employeeDTO.setEage(30);
		employeeDTO.setEmail("employee1@example.com");
		employeeDTO.setEsalary(50000.0);
		employeeDTO.setDepartmentId(1);

		EmployeeDTO savedEmployeeDTO = new EmployeeDTO();
		savedEmployeeDTO.setEid(1);
		savedEmployeeDTO.setEname("employee2");
		savedEmployeeDTO.setEage(30);
		savedEmployeeDTO.setEmail("employee2@example.com");
		savedEmployeeDTO.setEsalary(50000.0);
		savedEmployeeDTO.setDepartmentId(1);

		when(employeeService.createEmployee(employeeDTO)).thenReturn(savedEmployeeDTO);

		// When
		String viewName = employeeWebController.createEmployee(employeeDTO);

		// Then
		assertEquals("redirect:/employees", viewName);

		// Verify
		verify(employeeService, times(1)).createEmployee(employeeDTO);
	}

	@Test
	void testDeleteEmployee() {
		// Given
		int employeeId = 1;

		// When
		String viewName = employeeWebController.deleteEmployee(employeeId);

		// Then
		assertEquals("redirect:/employees", viewName);

		// Verify
		verify(employeeService, times(1)).deleteEmployee(employeeId);
	}

}
