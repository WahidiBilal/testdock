package com.example.employee_management_application.controller;

import com.example.employee_management_application.dto.DepartmentDTO;
import com.example.employee_management_application.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departments")
public class DepartmentwebController {

	private final DepartmentService departmentService;

	public DepartmentwebController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	private static final String REDIRECT_TO_DEPARTMENTS = "redirect:/departments";

	@GetMapping
	public String listDepartments(Model model) {
		model.addAttribute("departments", departmentService.getAllDepartments());
		return "departments/list";
	}

	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("department", new DepartmentDTO());
		return "departments/create";
	}

	@PostMapping("/create")
	public String createDepartment(@ModelAttribute DepartmentDTO departmentDTO) {
		departmentService.createDepartment(departmentDTO);
		return REDIRECT_TO_DEPARTMENTS;
	}

	@PostMapping("/{id}/delete")
	public String deleteDepartment(@PathVariable Integer id) {
		departmentService.deleteDepartment(id);
		return REDIRECT_TO_DEPARTMENTS;
	}

}
