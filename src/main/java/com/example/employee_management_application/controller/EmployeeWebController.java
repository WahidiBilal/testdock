package com.example.employee_management_application.controller;

import com.example.employee_management_application.dto.EmployeeDTO;
import com.example.employee_management_application.service.EmployeeService;
import com.example.employee_management_application.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeWebController {

	private final EmployeeService employeeService;
	private final DepartmentService departmentService;

	private static final String REDIRECT_TO_EMPLOYEES = "redirect:/employees";

	public EmployeeWebController(EmployeeService employeeService, DepartmentService departmentService) {
		this.employeeService = employeeService;
		this.departmentService = departmentService;
	}

	@GetMapping
	public String listEmployees(Model model) {
		model.addAttribute("employees", employeeService.getAllEmployees());
		return "employees/list";
	}

	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("employee", new EmployeeDTO());
		model.addAttribute("departments", departmentService.getAllDepartments());
		return "employees/create";
	}

	@PostMapping("/create")
	public String createEmployee(@ModelAttribute EmployeeDTO employeeDTO) {
		employeeService.createEmployee(employeeDTO);
		return REDIRECT_TO_EMPLOYEES;
	}

	@PostMapping("/{id}/delete")
	public String deleteEmployee(@PathVariable Integer id) {
		employeeService.deleteEmployee(id);
		return REDIRECT_TO_EMPLOYEES;
	}

}
