package com.companyname.employeeappraisal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.companyname.employeeappraisal.dto.ManagerEmployeeAppraisalDTO;
import com.companyname.employeeappraisal.service.HodService;

@RestController
@RequestMapping("/hod")
public class HodController {

	private HodService hodService;

	public HodController(HodService hodService) {
		this.hodService = hodService;
	}

	@PostMapping(value = "/save", produces = "application/json")
	public ResponseEntity<String> saveAppraisal(@RequestBody ManagerEmployeeAppraisalDTO dto) {
		return ResponseEntity.ok(hodService.saveAppraisal(dto));
	}
}
