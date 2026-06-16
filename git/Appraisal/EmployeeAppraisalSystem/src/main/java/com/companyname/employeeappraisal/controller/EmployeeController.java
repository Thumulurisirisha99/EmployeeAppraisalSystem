package com.companyname.employeeappraisal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.companyname.employeeappraisal.dto.ApiResponse;
import com.companyname.employeeappraisal.dto.AppModuleDTO;
import com.companyname.employeeappraisal.dto.EmployeeAppraisalDTO;
import com.companyname.employeeappraisal.dto.EmployeeAppraisalDetailDTO;
import com.companyname.employeeappraisal.dto.EmployeeAppraisalWorkDTO;
import com.companyname.employeeappraisal.dto.EmployeeAppraisalWorkResponseDTO;
import com.companyname.employeeappraisal.dto.PerformanceGoalDTO;
import com.companyname.employeeappraisal.model.EmployeeAppraisalWork;
import com.companyname.employeeappraisal.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	// API to save employee performance goals.
	@PostMapping(value = "/goal/save", consumes = "application/json", produces = "application/json") // tested
	public ResponseEntity<PerformanceGoalDTO> savegoals(@RequestBody PerformanceGoalDTO performanceGoalDTO) {
		PerformanceGoalDTO savedGoal = employeeService.saveGoal(performanceGoalDTO);
		return ResponseEntity.ok(savedGoal);
	}

	/**
	 * Manager approves or rejects an employee goal. Once approved (status 1003),
	 * the employee can submit feedback with comments and documents by goalId.
	 * 
	 * @throws Exception
	 */
	@PostMapping(value = "/selffeedback", consumes = "multipart/form-data")
	public ResponseEntity<?> saveSelfFeedback(@RequestPart("feedbackData") String feedbackData,
			@RequestPart(value = "files", required = false) MultipartFile[] files) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		PerformanceGoalDTO dto = mapper.readValue(feedbackData, PerformanceGoalDTO.class);
		String message = employeeService.saveSelfFeedback(dto, files);
		return ResponseEntity.ok(Map.of("status", "success", "message", message));
	}

	// Service method to update an existing PerformanceGoal entity identified by
	// goalId
	// ---// extrraaa
	@PostMapping(value = "/goal/update", produces = "application/json")
	public ResponseEntity<PerformanceGoalDTO> updateGoal(@RequestBody PerformanceGoalDTO dto) {
		PerformanceGoalDTO updated = employeeService.updateGoal(dto.getGoalId(), dto);
		return ResponseEntity.ok(updated);
	}
//	@PostMapping("/goals/{goalId}/submit")
//	public ResponseEntity<PerformanceGoalDTO> submitGoal(
//	        @PathVariable Integer goalId,
//	        @RequestParam Integer userId) {
//
//	    PerformanceGoalDTO response = performanceGoalService.submitGoal(goalId, userId);
//	    return ResponseEntity.ok(response);
//	}

	// API to fetch performance goals of a specific employeeid
	@GetMapping(value = "/goals/{employeeid}", produces = "application/json") // tested
	public ResponseEntity<?> getGoals(@PathVariable Integer employeeid) {
		return ResponseEntity.ok(employeeService.getEmployeeGoals(employeeid));
	}

	@GetMapping(value = "/goaldocument/{goalId}", produces = "application/json")
	public ResponseEntity<?> getGoalDocuments(@PathVariable Integer goalId) {
		List<Map<String, String>> docs = employeeService.getGoalDocumentsBase64(goalId);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
				.body(Map.of("status", "success", "documents", docs));
	}

	/**
	 * Fetch appraisal modules mapped to a specific department.
	 **/
	@GetMapping(value = "/appraisal/modules/{departmentId}", produces = "application/json")
	public ResponseEntity<List<AppModuleDTO>> getAppraisalModulesByDepartment(@PathVariable Integer departmentId) {
		return ResponseEntity.ok(employeeService.getAppraisalModulesByDepartment(departmentId));
	}

	/**
	 * Fetch self-appraisal questions for a given appraisal module.
	 **/
	@GetMapping(value = "/appraisal/questions/{moduleId}", produces = "application/json")
	public ResponseEntity<List<EmployeeAppraisalDetailDTO>> getQuestionByModuleId(@PathVariable Integer moduleId) {
		return ResponseEntity.ok(employeeService.getQuestionByModuleId(moduleId));
	}

	/**
	 * API to save or submit employee appraisal - Draft save → status 1001 - Submit
	 * → status 1002
	 */
	@PostMapping(value = "/appraisal/save", produces = "application/json") // tested
	public ResponseEntity<Object> saveAppraisal(@RequestBody EmployeeAppraisalDTO employeeAppraisalDTO) {
		return ResponseEntity.ok(employeeService.insertOrUpdateAppraisal(employeeAppraisalDTO));
	}

	/**
	 * API to fetch manager / approver approval status Used to identify which
	 * manager's approval is pending
	 */
	@PostMapping(value = "/managerStatus", produces = "application/json") // tested
	public ResponseEntity<?> getManagerStatus(@RequestParam Integer eligibleId) {
		return ResponseEntity.ok(employeeService.getManagerStatus(eligibleId));
	}

	/**
	 * Fetch employee appraisal data module-wise along with already filled answers
	 * (if any)
	 */
	@GetMapping(value = "/fetch/{eligibleId}/{departmentId}", produces = "application/json")
	public ResponseEntity<?> getApprasalData(@PathVariable Integer eligibleId, @PathVariable Integer departmentId) {
		return ResponseEntity.ok(employeeService.getApprasalData(eligibleId, departmentId));
	}

	// work save
	@PostMapping(value = "/work/save", produces = "application/json") // tested
	public ResponseEntity<EmployeeAppraisalWork> saveOrUpdateWork(@RequestBody EmployeeAppraisalWorkDTO dto) {
		EmployeeAppraisalWork savedWork = employeeService.saveOrUpdate(dto);
		return ResponseEntity.ok(savedWork);
	}

	@GetMapping(value = "/work/{employeeId}", produces = "application/json") // tested
	public ResponseEntity<List<EmployeeAppraisalWorkResponseDTO>> getWorksByEmployeeId(
			@PathVariable Integer employeeId) {
		List<EmployeeAppraisalWorkResponseDTO> response = employeeService.getWorksByEmployeeId(employeeId);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/work-details/{workId}", produces = "application/json") // tested
	public ResponseEntity<EmployeeAppraisalWorkDTO> getWorkById(@PathVariable Integer workId) {
		EmployeeAppraisalWorkDTO response = employeeService.getWorkById(workId);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/work/format", produces = "application/octet-stream") // tested
	public ResponseEntity<byte[]> downloadFormat() {
		byte[] fileBytes = employeeService.downloadFormat();
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=work_Format.xlsx")
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(fileBytes);
	}

	@PostMapping(value = "/uploadWorkFile", produces = "application/json") // tested
	public ResponseEntity<ApiResponse> uploadWorkFile(@RequestParam("file") MultipartFile file,
			@RequestParam("employeeId") Integer employeeId, @RequestParam("financialYearId") Integer financialYearId,
			@RequestParam("quarterId") Integer quarterId, @RequestParam("type") String type) {
		ApiResponse response = employeeService.uploadWorkFile(file, employeeId, financialYearId, quarterId, type);
		return ResponseEntity.ok(response);
	}

	@PostMapping(value="/goalstatusMessage/{employeeId}", produces = "application/json")
    public ResponseEntity<?> getGoalsAndWorksByEmployee(
            @PathVariable Integer employeeId,
            @RequestParam String activeFromDate,
            @RequestParam String activeToDate) {

        ResponseEntity<?> response =
        		employeeService.getGoalsAndWorksByEmployeeAndDate(
                        employeeId, activeFromDate, activeToDate);

        return response;
    }

}
