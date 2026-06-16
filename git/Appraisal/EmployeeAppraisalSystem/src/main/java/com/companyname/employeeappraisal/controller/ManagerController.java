package com.companyname.employeeappraisal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.companyname.employeeappraisal.dto.AppModuleDTO;
import com.companyname.employeeappraisal.dto.AppraisalTeamMemberDTO;
import com.companyname.employeeappraisal.dto.EmployeeAppraisalDTO;
import com.companyname.employeeappraisal.dto.EmployeePerformanceGoalsDTO;
import com.companyname.employeeappraisal.dto.FeedbackRequestDTO;
import com.companyname.employeeappraisal.dto.FeedbackSubmitRequestDTO;
import com.companyname.employeeappraisal.dto.ManagerEmployeeAppraisalDTO;
import com.companyname.employeeappraisal.dto.PerformanceGoalDTO;
import com.companyname.employeeappraisal.dto.ResponseDTO;
import com.companyname.employeeappraisal.dto.SummaryDTO;
import com.companyname.employeeappraisal.dto.TeamAppraisalDetailsDTO;
import com.companyname.employeeappraisal.model.ManagerEmployeeAppraisal;
import com.companyname.employeeappraisal.service.ManagerService;

@RestController
@RequestMapping("/manager")
public class ManagerController {
	private ManagerService managerService;

	public ManagerController(ManagerService managerService) {
		this.managerService = managerService;
	}

	// API to fetch team goals of employees reporting to a manager
	@GetMapping(value = "/teamgoals/{employeeId}", produces = "application/json")//tested
	public List<ResponseDTO> getTeamGoals(@PathVariable String employeeId) {
		return managerService.getTeamGoals(employeeId);
	}

	// REST API to fetch goal summary for a manager's team
	@GetMapping(value = "/team/goalsummary/{managerId}", produces = "application/json")
	public ResponseEntity<List<SummaryDTO>> getTeamGoalSummary(@PathVariable String managerId) {
		return ResponseEntity.ok(managerService.getTeamGoalSummary(managerId));
	}

	// API to fetch performance goals of a specific employee
	@GetMapping(value = "/goals/{employeeid}", produces = "application/json")//tested
	public EmployeePerformanceGoalsDTO getGoals(@PathVariable Integer employeeid) {
		return managerService.getEmployeeGoals(employeeid);
	}

	// API manager to approve or reject an employee's performance goal.
	@PostMapping(value = "/goals/approval", produces = "application/json")//tested
	public ResponseEntity<Map<String, Object>> approveGoal(@RequestBody PerformanceGoalDTO dto) {
		Map<String, Object> resultMessage = managerService.getManagerGoalApproval(dto);
		return ResponseEntity.ok(resultMessage);
	}

	/**
	 * API to get appraisal details of all employees reporting to a manager (i.e.,
	 * manager's under employees who are eligible for appraisal)
	 **/
	@PostMapping(value = "/team/appraisal", produces = MediaType.APPLICATION_JSON_VALUE)//tested
	public List<TeamAppraisalDetailsDTO> getTeamAppraisalEmployees(@RequestParam String employeeId) {
		return managerService.getTeamAppraisalEmployees(employeeId);
	}

	/**
	 * Controller API to fetch detailed employee information by employee ID
	 * including education details
	 **/
	@PostMapping(value = "/employeedetails/{appraisalId}", produces = "application/json")
	public List<TeamAppraisalDetailsDTO> getEmployeeDetailsById(@PathVariable Integer appraisalId) {
		return managerService.getEmployeeDetailsById(appraisalId);
	}

	/**
	 * API for manager to give feedback and ratings for an employee appraisal Saves
	 * manager appraisal responses (draft or submit) Validates appraisal submission
	 * status and approver level Moves appraisal to next approver on successful
	 * submission
	 **/
	@PostMapping(value = "/save", produces = "application/json")//tested
	public ResponseEntity<List<ManagerEmployeeAppraisal>> saveManagerAppraisal(
			@RequestBody ManagerEmployeeAppraisalDTO dto) {
		return ResponseEntity.ok(managerService.saveManagerAppraisal(dto));
	}

	// Manager dashboard summary of team appraisal progress
//	@GetMapping(value = "/team/appraisalsummary/{managerId}", produces = "application/json")
//	public ResponseEntity<List<SummaryDTO>> getTeamAppraisalSummary(@PathVariable String managerId) {
//		return ResponseEntity.ok(managerService.getTeamAppraisalSummary(managerId));
//	}
	@GetMapping(value ="/team/appraisalsummary/{managerId}", produces = "application/json")
	public List<SummaryDTO> getTeamSummary(@PathVariable String managerId) {
	    return managerService.getTeamAppraisalSummary(managerId);
	}
	// REST API to fetch employee performance goals for a specific financial year.
	@GetMapping(value = "/fetch/goals/{employeeId}/{year}", produces = "application/json")
	public ResponseEntity<List<PerformanceGoalDTO>> getEmployeeGoalsByYear(@PathVariable Integer employeeId,
			@PathVariable Integer year) {
		return ResponseEntity.ok(managerService.getEmployeeGoalsByFinancialYearId(employeeId, year));
	}

	/**
	 * REST API to fetch employee appraisal data module-wise for a specific
	 * appraisal ID.
	 **/
	@GetMapping(value = "/fetch/appraisaldata/{moduleId}/{appraisalId}", produces = "application/json")
	public ResponseEntity<AppModuleDTO> getEmployeeAppraisalDataByIdModuleWise(@PathVariable Integer moduleId,
			@PathVariable Integer appraisalId) {
		return ResponseEntity.ok(managerService.getEmployeeAppraisalDataByIdModuleWise(moduleId, appraisalId));
	}

	@GetMapping(value = "/fetch/{appraisalId}/{managerId}", produces = "application/json")
	public ResponseEntity<Object> getAppraisalDetailsForManager(@PathVariable Integer appraisalId,
			@PathVariable Integer managerId) {
		return ResponseEntity.ok(managerService.getAppraisalDetails(appraisalId, managerId));
	}

	@PostMapping(value = "/appraisal/employees", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AppraisalTeamMemberDTO> getTeammembersAppraisalEmployees(@RequestParam Integer managerId) {
		return managerService.getTeammembersAppraisalEmployees(managerId);
	}
	@GetMapping(value="/feedback/summary/{managerId}", produces = "application/json")
	public ResponseEntity<List<SummaryDTO>> getFeedbackSummary(
	        @PathVariable Integer managerId) {

	   List<SummaryDTO> summary = managerService.getTeamAppraisalSummary(managerId);
	    return ResponseEntity.ok(summary);
	}
	// fetching employee teammets
	@PostMapping(value = "/team/members", produces = "application/json")
	public List<AppraisalTeamMemberDTO> getTeamMembersExceptSelf(@RequestParam Integer employeeId,
			@RequestParam Integer appraisalId, @RequestParam Integer managerId) {
		return managerService.getTeamMembersExceptSelf(employeeId, appraisalId, managerId);
	}

	@PostMapping(value = "/360/save", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> saveFeedback(@RequestBody FeedbackRequestDTO feedbackRequestDTO) {
		try {
			managerService.saveFeedback(feedbackRequestDTO);
			return ResponseEntity.ok(Map.of("message", "Feedback saved successfully"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Failed to save feedback: " + e.getMessage()));
		}
	}

	@GetMapping(value = "/{participantId}", produces = "application/json")
	public ResponseEntity<?> getQuestionsByParticipant(@PathVariable Integer participantId) {
		Object questionsData = managerService.getFeedbackRequestByParticipant(participantId);
		return ResponseEntity.ok(questionsData);
	}

	@PostMapping(value = "/submit/feedback", produces = "application/json")
	public ResponseEntity<String> submitAnswers(@RequestBody FeedbackSubmitRequestDTO request) {
		managerService.submitAnswers(request.getParticipantId(), request.getCategoryDtos());
		return ResponseEntity.ok("Feedback submitted successfully");
	}

	@GetMapping(value = "/fetch/feedback/{participantId}", produces = "application/json")
	public ResponseEntity<Object> fetchFeedback(@PathVariable Integer participantId) {
		return ResponseEntity.ok(managerService.fetchFeedbackByParticipantId(participantId));
	}

	@GetMapping(value = "/feedback/previous/{appraisalId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EmployeeAppraisalDTO>> getPreviousTwoYearsFeedback(@PathVariable Integer appraisalId) {
		return ResponseEntity.ok(managerService.getPreviousTwoFeedbackByAppraisalId(appraisalId));
	}

	/**
	 * API to get appraisal details of all employees reporting to a manager (i.e.,
	 * manager's under employees who are completed the appraisal)
	 **/
	@PostMapping(value = "/team/appraisalsummary", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TeamAppraisalDetailsDTO> getTeamAppraisalEmployeesSummary(@RequestParam String managerId,
			@RequestParam Integer financialYearId) {
		return managerService.getTeamAppraisalEmployeesSummary(managerId, financialYearId);
	}

	@GetMapping(value = "/acknowledge/{appraisalId}", produces = "application/json")
	public ResponseEntity<Object> acknowledgeEmployeeAppraisal(@PathVariable Integer appraisalId) {
		return ResponseEntity.ok(managerService.acknowledgeEmployeeAppraisal(appraisalId));
	}

}
