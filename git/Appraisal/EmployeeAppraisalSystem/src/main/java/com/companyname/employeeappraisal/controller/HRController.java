package com.companyname.employeeappraisal.controller;

import java.util.List;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.companyname.employeeappraisal.dto.ApiResponse;
import com.companyname.employeeappraisal.dto.TeamAppraisalDetailsDTO;
import com.companyname.employeeappraisal.service.HRService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/hr")
public class HRController {
	private HRService hrService;

	public HRController(HRService hrService) {
		this.hrService = hrService;
	}

	@PostMapping(value = "/appraisalsheet/format", produces = "application/octet-stream")
	public ResponseEntity<byte[]> downloadFormat(@RequestParam("financialYearId") Integer financialYearId,
			@RequestParam("quarterId") Integer quarterId) {

		byte[] fileBytes = hrService.downloadFormat(financialYearId, quarterId);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Appraisal_Format.xlsx")
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(fileBytes);
	}

	@PostMapping(value = "/appraisalsheet/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Upload appraisal sheet")
	public ResponseEntity<ApiResponse> uploadAppraisalSheet(
			@Parameter(required = true) @RequestPart("file") MultipartFile file,
			@RequestParam("loginid") Integer loginid, @RequestParam("financialYearId") Integer financialYearId,
			@RequestParam("quarterId") Integer quarterId) {
		ApiResponse response = hrService.uploadAppraisalSheet(file, loginid, financialYearId, quarterId);
		if ("error".equalsIgnoreCase(response.getStatus())) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/team/appraisal", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TeamAppraisalDetailsDTO> getTeamAppraisalEmployees(@RequestParam Integer financialYearId, @RequestParam(required = false) Integer quarterId) {
		return hrService.getTeamAppraisalEmployees(financialYearId,quarterId);
	}

	@GetMapping(value = "/fetch/{appraisalId}", produces = "application/json")
	public ResponseEntity<Object> getFeedbackByAppraisalId(@PathVariable Integer appraisalId) {
		return ResponseEntity.ok(hrService.getFeedBackByAppraisalId(appraisalId));
	}

	@PostMapping(value = "/appraisalstatus/download", produces = "application/json")
	public ResponseEntity<byte[]> downloadExcel(@RequestParam String financialYearId,
			@RequestParam(required = false) String quarterId) {
		byte[] excelData = hrService.generateExcel(financialYearId, quarterId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDisposition(ContentDisposition.attachment().filename("Appraisal_Status.xlsx").build());
		return new ResponseEntity<>(excelData, headers, HttpStatus.OK);

	}
}
