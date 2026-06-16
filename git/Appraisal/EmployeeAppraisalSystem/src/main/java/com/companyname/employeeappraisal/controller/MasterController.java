package com.companyname.employeeappraisal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.companyname.employeeappraisal.dto.ApiResponse;
import com.companyname.employeeappraisal.dto.PerformanceGoalDTO;
import com.companyname.employeeappraisal.dto.ResponseDTO;
import com.companyname.employeeappraisal.exception.InvalidCredentialsException;
import com.companyname.employeeappraisal.model.GoalCategory;
import com.companyname.employeeappraisal.model.MasterFeedbackCategory;
import com.companyname.employeeappraisal.model.MasterFinancialYear;
import com.companyname.employeeappraisal.model.MasterQuarter;
import com.companyname.employeeappraisal.model.MasterWorkType;
import com.companyname.employeeappraisal.service.MasterService;

@RestController
@RequestMapping("/master")
public class MasterController {
	private MasterService masterService;

	public MasterController(MasterService masterService) {
		this.masterService = masterService;
	}

	// Login API Validates employee credentials and returns login details along with
	// role, modules, financial year, quarter, and JWT token.
	@PostMapping(value = "/login", produces = "application/json")//tested
	public ResponseEntity<ApiResponse> login(@RequestParam String employeeId, @RequestParam String password) {
		if (employeeId == null || employeeId.trim().isEmpty()) {
			throw new InvalidCredentialsException("Employee code cannot be empty");
		}

		if (password == null || password.trim().isEmpty()) {
			throw new InvalidCredentialsException("Password cannot be empty");
		}

		ResponseDTO response = masterService.login(employeeId, password);
		return ResponseEntity.ok(ApiResponse.success("Login successful", response));
	}

	// REST API to fetch all active goal categories.
	@GetMapping(value = "/goalCategory", produces = "application/json")//tested
	public List<GoalCategory> getGoalCategory() {
		return masterService.getGoalCategory();
	}

	// get details of a specific performance goal by goalId
	@GetMapping(value = "/performancegoals/{goalId}", produces = "application/json")//tested
	public PerformanceGoalDTO getGoalById(@PathVariable int goalId) {
		return masterService.getGoalById(goalId);
	}

	@GetMapping(value = "/worktype", produces = "application/json")//tested
	public List<MasterWorkType> fetchAllWorkTypes() {
		return masterService.getAllWorkTypes();
	}

	@GetMapping(value ="/categories", produces = "application/json")//tested
	public List<MasterFeedbackCategory> getCategories() {
		return masterService.getActiveCategories();
	}
	@GetMapping(value ="/financialYears", produces = "application/json")//tested
	public List<MasterFinancialYear> getFinancialYears() {
		return masterService.getFinancialYears();
	}
	@GetMapping(value ="/quarters", produces = "application/json")//tested
	public List<MasterQuarter> getQuarters() {
		return masterService.getQuarters();
	}
	 
}
