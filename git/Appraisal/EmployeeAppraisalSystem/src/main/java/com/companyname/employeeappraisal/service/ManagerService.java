package com.companyname.employeeappraisal.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.companyname.employeeappraisal.dto.AppModuleDTO;
import com.companyname.employeeappraisal.dto.AppraisalApproverStatusDTO;
import com.companyname.employeeappraisal.dto.AppraisalTeamMemberDTO;
import com.companyname.employeeappraisal.dto.EducationDetails;
import com.companyname.employeeappraisal.dto.EmployeeAppraisalDTO;
import com.companyname.employeeappraisal.dto.EmployeeAppraisalDetailDTO;
import com.companyname.employeeappraisal.dto.EmployeeAppraisalWorkDTO;
import com.companyname.employeeappraisal.dto.EmployeePerformanceGoalsDTO;
import com.companyname.employeeappraisal.dto.FeedbackParticipantDTO;
import com.companyname.employeeappraisal.dto.FeedbackRequestDTO;
import com.companyname.employeeappraisal.dto.FeedbackSummaryDTO;
import com.companyname.employeeappraisal.dto.HodFeedbackDTO;
import com.companyname.employeeappraisal.dto.ManagerEmployeeAppraisalDTO;
import com.companyname.employeeappraisal.dto.ManagerFeedbackDTO;
import com.companyname.employeeappraisal.dto.ManagerModuleDTO;
import com.companyname.employeeappraisal.dto.PerformanceGoalDTO;
import com.companyname.employeeappraisal.dto.QuestionAnswerDTO;
import com.companyname.employeeappraisal.dto.ResponseDTO;
import com.companyname.employeeappraisal.dto.SummaryDTO;
import com.companyname.employeeappraisal.dto.TeamAppraisalDetailsDTO;
import com.companyname.employeeappraisal.exception.InvalidCredentialsException;
import com.companyname.employeeappraisal.model.AppraisalDeptModule;
import com.companyname.employeeappraisal.model.AppraisalDeptQuestion;
import com.companyname.employeeappraisal.model.AppraisalManagerDeptQuestion;
import com.companyname.employeeappraisal.model.AppraisalModule;
import com.companyname.employeeappraisal.model.Employee360FeedbackAnswer;
import com.companyname.employeeappraisal.model.Employee360FeedbackParticipant;
import com.companyname.employeeappraisal.model.EmployeeAppraisal;
import com.companyname.employeeappraisal.model.EmployeeAppraisalApprovers;
import com.companyname.employeeappraisal.model.EmployeeAppraisalDetails;
import com.companyname.employeeappraisal.model.EmployeeAppraisalWork;
import com.companyname.employeeappraisal.model.HodEmployeeAppraisal;
import com.companyname.employeeappraisal.model.ManagerEmployeeAppraisal;
import com.companyname.employeeappraisal.model.MasterFeedbackCategory;
import com.companyname.employeeappraisal.model.MasterFinancialYear;
import com.companyname.employeeappraisal.model.PerformanceGoal;
import com.companyname.employeeappraisal.repository.AppraisalDeptModuleRepository;
import com.companyname.employeeappraisal.repository.AppraisalDeptQuestionRepository;
import com.companyname.employeeappraisal.repository.AppraisalEligibleEmployeeRepository;
import com.companyname.employeeappraisal.repository.AppraisalManagerDeptQuestionRepository;
import com.companyname.employeeappraisal.repository.AppraisalModuleRepository;
import com.companyname.employeeappraisal.repository.Employee360FeedbackAnswerRepository;
import com.companyname.employeeappraisal.repository.Employee360FeedbackParticipantRepository;
import com.companyname.employeeappraisal.repository.EmployeeAppraisalApproversRepository;
import com.companyname.employeeappraisal.repository.EmployeeAppraisalDetailsRepository;
import com.companyname.employeeappraisal.repository.EmployeeAppraisalRepository;
import com.companyname.employeeappraisal.repository.EmployeeAppraisalWorkRepository;
import com.companyname.employeeappraisal.repository.HodAppraisalDeptQuestionRepository;
import com.companyname.employeeappraisal.repository.HodEmployeeAppraisalRepository;
import com.companyname.employeeappraisal.repository.ManagerEmployeeAppraisalRepository;
import com.companyname.employeeappraisal.repository.MasterFeedbackCategoryRepository;
import com.companyname.employeeappraisal.repository.MasterFinancialYearRepository;
import com.companyname.employeeappraisal.repository.MasterRepository;
import com.companyname.employeeappraisal.repository.PerformanceGoalRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class ManagerService {
	private final JavaMailSender mailSender;
	private MasterRepository masterRepository;
	private PerformanceGoalRepository performanceGoalRepository;
	private AppraisalManagerDeptQuestionRepository appraisalManagerDeptQuestionRepository;
	private ManagerEmployeeAppraisalRepository managerEmployeeAppraisalRepository;
	private EmployeeAppraisalRepository employeeAppraisalRepository;
	private EmployeeAppraisalApproversRepository employeeAppraisalApproversRepository;
	private AppraisalModuleRepository appraisalModuleRepository;
	private AppraisalDeptModuleRepository appraisalDeptModuleRepository;
	private AppraisalEligibleEmployeeRepository appraisalEligibleEmployeeRepository;
	private AppraisalDeptQuestionRepository appraisalDeptQuestionRepository;
	private EmployeeAppraisalDetailsRepository employeeAppraisalDetailsRepository;
	private HodAppraisalDeptQuestionRepository hodAppraisalDeptQuestionRepository;
	private HodEmployeeAppraisalRepository hodEmployeeAppraisalRepository;
	private EmployeeAppraisalWorkRepository employeeAppraisalWorkRepository;
	private MasterFeedbackCategoryRepository masterFeedbackCategoryRepository;
	private Employee360FeedbackAnswerRepository employee360FeedbackAnswerRepository;
	private Employee360FeedbackParticipantRepository employee360FeedbackParticipantRepository;
	private MasterFinancialYearRepository masterFinancialYearRepository;

	public ManagerService(MasterRepository masterRepository, PerformanceGoalRepository performanceGoalRepository,
			AppraisalManagerDeptQuestionRepository appraisalManagerDeptQuestionRepository,
			ManagerEmployeeAppraisalRepository managerEmployeeAppraisalRepository,
			EmployeeAppraisalRepository employeeAppraisalRepository,
			EmployeeAppraisalApproversRepository employeeAppraisalApproversRepository,
			AppraisalModuleRepository appraisalModuleRepository,
			AppraisalDeptModuleRepository appraisalDeptModuleRepository,
			AppraisalEligibleEmployeeRepository appraisalEligibleEmployeeRepository,
			AppraisalDeptQuestionRepository appraisalDeptQuestionRepository,
			EmployeeAppraisalDetailsRepository employeeAppraisalDetailsRepository,
			HodAppraisalDeptQuestionRepository hodAppraisalDeptQuestionRepository,
			HodEmployeeAppraisalRepository hodEmployeeAppraisalRepository,
			EmployeeAppraisalWorkRepository employeeAppraisalWorkRepository,
			MasterFeedbackCategoryRepository masterFeedbackCategoryRepository, JavaMailSender mailSender,
			Employee360FeedbackAnswerRepository employee360FeedbackAnswerRepository,
			Employee360FeedbackParticipantRepository employee360FeedbackParticipantRepository,
			MasterFinancialYearRepository masterFinancialYearRepository) {
		this.masterRepository = masterRepository;
		this.performanceGoalRepository = performanceGoalRepository;
		this.appraisalManagerDeptQuestionRepository = appraisalManagerDeptQuestionRepository;
		this.managerEmployeeAppraisalRepository = managerEmployeeAppraisalRepository;
		this.employeeAppraisalRepository = employeeAppraisalRepository;
		this.employeeAppraisalApproversRepository = employeeAppraisalApproversRepository;
		this.appraisalModuleRepository = appraisalModuleRepository;
		this.appraisalDeptModuleRepository = appraisalDeptModuleRepository;
		this.appraisalEligibleEmployeeRepository = appraisalEligibleEmployeeRepository;
		this.appraisalDeptQuestionRepository = appraisalDeptQuestionRepository;
		this.employeeAppraisalDetailsRepository = employeeAppraisalDetailsRepository;
		this.hodAppraisalDeptQuestionRepository = hodAppraisalDeptQuestionRepository;
		this.hodEmployeeAppraisalRepository = hodEmployeeAppraisalRepository;
		this.employeeAppraisalWorkRepository = employeeAppraisalWorkRepository;
		this.masterFeedbackCategoryRepository = masterFeedbackCategoryRepository;
		this.mailSender = mailSender;
		this.employee360FeedbackAnswerRepository = employee360FeedbackAnswerRepository;
		this.employee360FeedbackParticipantRepository = employee360FeedbackParticipantRepository;
		this.masterFinancialYearRepository = masterFinancialYearRepository;
	}

	private static final Logger log = LoggerFactory.getLogger(ManagerService.class);
	@Value("${feedback.request.title}")
	private String defaultTitle;

	@Value("${feedback.request.description}")
	private String defaultDescription;

	// @Cacheable(value = "teamGoals", key = "#employeeId")
	public List<ResponseDTO> getTeamGoals(String employeeId) {
		log.info("Fetching team goals for manager employeeId={}", employeeId);
		int managerSeqNo = Integer.parseInt(employeeId);
		return masterRepository.findEmployeesWithGoals(managerSeqNo).stream().map(row -> {
			ResponseDTO dto = new ResponseDTO();
			dto.setEmployeeId(row.get("employee_id") != null ? ((Number) row.get("employee_id")).intValue() : null);
			dto.setFullName((String) row.get("employee_name"));
			dto.setDepartment((String) row.get("department"));
			dto.setDesignation((String) row.get("designation"));
			dto.setTotalGoalCount(String.valueOf(row.get("total_goal_count")));
			dto.setStatus(row.get("last_goal_status") != null ? row.get("last_goal_status").toString() : "---");
			dto.setStatusName(row.get("status_name") != null ? row.get("status_name").toString() : "---");
			dto.setLastUpdated(row.get("last_updated_date") != null ? row.get("last_updated_date").toString() : "---");
			log.debug("Mapped ResponseDTO: {}", dto);
			return dto;

		}).collect(Collectors.toList());
	}

	// Service method to prepare goal summary response for a manager
	// @Cacheable(value = "teamGoalSummary", key = "#managerId")
	public List<SummaryDTO> getTeamGoalSummary(String managerId) {
		log.info("Team goals Summary managerId={}", managerId);
		int managerSeqNo = Integer.parseInt(managerId);
		Map<String, Object> result = masterRepository.getManagerGoalSummary(managerSeqNo);

		if (result == null || result.isEmpty()) {
			return Arrays.asList(new SummaryDTO("Goals Submitted", 0, 0), new SummaryDTO("Pending Approval", 0, 0),
					new SummaryDTO("Approved", 0, 0), new SummaryDTO("Rejected", 0, 0));
		}

		int total = getIntOrDefault(result, "total_goal_count");
		log.info("Team goals Summary total={}", total);
		return Arrays.asList(new SummaryDTO("Goals Submitted", getIntOrDefault(result, "submitted_goals"), total),
				new SummaryDTO("Pending Approval", getIntOrDefault(result, "pending_approval"), total),
				new SummaryDTO("Approved", getIntOrDefault(result, "approved_goals"), total),
				new SummaryDTO("Rejected", getIntOrDefault(result, "rejected_goals"), total));
	}

	private int getIntOrDefault(Map<String, Object> map, String key) {
		if (map == null || !map.containsKey(key) || map.get(key) == null) {
			return 0;
		}
		Object val = map.get(key);
		if (val instanceof Number) {
			return ((Number) val).intValue();
		}
		try {
			return Integer.parseInt(val.toString());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Manager will approve or reject an employee's performance goal by specifying
	 * the goalId. The approval status is updated based on the 'type' field: -
	 * "approve" sets status to approved (1003) - "reject" sets status to rejected
	 * (1004) Manager's comments and approval metadata are saved along with the
	 * status update.
	 */
	// @CacheEvict(value = "employeeGoals", key = "#goal.employeeId")
	public Map<String, Object> getManagerGoalApproval(PerformanceGoalDTO dto) {
		log.info("Manager approval request received for goalId={}, type={}", dto.getGoalId(), dto.getType());
		Optional<PerformanceGoal> optionalGoal = performanceGoalRepository.findById(dto.getGoalId());
		if (!optionalGoal.isPresent()) {
			log.error("Goal not found with id={}", dto.getGoalId());
			throw new InvalidCredentialsException("Goal not found with id: " + dto.getGoalId());
		}

		PerformanceGoal goal = optionalGoal.get();
		log.debug("Fetched PerformanceGoal: {}", goal);
		if (!goal.getStatus().equals(1002)) {
			log.warn("Goal id={} is not in a state for manager approval. Current status={}", goal.getGoalId(),
					goal.getStatus());
			throw new InvalidCredentialsException(
					"Goal is not in a state for manager approval. Current status: " + goal.getStatus());
		}
		goal.setApprovedManagerId(dto.getApprovedManagerId());
		goal.setManagerComment(dto.getManagerComment());
		goal.setApprovalDate(LocalDateTime.now());

		String type = dto.getType();
		if ("approve".equalsIgnoreCase(type)) {
			log.info("Goal id={} approved by managerId={}", goal.getGoalId(), dto.getApprovedManagerId());
			goal.setStatus(1003);
		} else if ("reject".equalsIgnoreCase(type)) {
			log.info("Goal id={} rejected by managerId={}", goal.getGoalId(), dto.getApprovedManagerId());
			goal.setStatus(1004);
		} else {
			log.error("Invalid approval type: {} for goalId={}", type, goal.getGoalId());
			throw new InvalidCredentialsException("Invalid approval type: " + type);
		}

		PerformanceGoal savedGoal = performanceGoalRepository.save(goal);
		log.debug("Saved PerformanceGoal: {}", savedGoal);
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("goalId", savedGoal.getGoalId());
		responseMap.put("title", savedGoal.getTitle());
		responseMap.put("status", savedGoal.getStatus());
		responseMap.put("approvalDate", savedGoal.getApprovalDate());
		responseMap.put("approvedManagerId", savedGoal.getApprovedManagerId());
		responseMap.put("managerComment", savedGoal.getManagerComment());

		log.info("Manager approval response prepared for goalId={}", savedGoal.getGoalId());
		return responseMap;
	}

	// Service method to fetch employee details along with performance goals
	// Group goals by financial year and maintain insertion order
	// @Cacheable(value = "employeeGoals", key = "#employeeId")
	public EmployeePerformanceGoalsDTO getEmployeeGoals(Integer employeeId) {
		log.info("Fetching goals for employeeId={}", employeeId);
		Map<String, Object> empMap = masterRepository.getEmployeeDetails(employeeId);
		if (empMap == null || empMap.isEmpty()) {
			log.error("Employee not found with id={}", employeeId);
			throw new RuntimeException("Employee not found: " + employeeId);
		}
		log.debug("Employee details: {}", empMap);
		EmployeePerformanceGoalsDTO employee = new EmployeePerformanceGoalsDTO();
		employee.setEmployeeId(String.valueOf(empMap.get("employeeId")));
		employee.setFullName(String.valueOf(empMap.get("fullName")));
		employee.setDepartment(String.valueOf(empMap.get("department")));
		employee.setDesignation(String.valueOf(empMap.get("designation")));
		List<Integer> excludedStatuses = Arrays.asList(1001);
		List<PerformanceGoal> goalEntities = performanceGoalRepository
				.findByEmployeeIdAndStatusNotInOrderByCreatedDateDesc(employeeId, excludedStatuses);
		log.info("Found {} goals for employeeId={}", goalEntities.size(), employeeId);
		List<PerformanceGoalDTO> goalDTOList = goalEntities.stream().map(goal -> {
			PerformanceGoalDTO dto = new PerformanceGoalDTO();
			dto.setEmployeeId(goal.getEmployeeId());
			dto.setGoalId(goal.getGoalId());
			dto.setTitle(goal.getTitle());
			dto.setDescription(goal.getDescription());
			dto.setGoalCategoryName(goal.getGoalCategory() != null ? goal.getGoalCategory().getCategoryName() : null);
			dto.setTimeline(goal.getTimeline());
			dto.setStatus(goal.getStatus());
			dto.setStatusName(goal.getMasterStatus() != null ? goal.getMasterStatus().getStatusName() : null);
//			dto.setDocumentName(goal.getDocumentName());
//			dto.setDocument(goal.getDocumentPath());
			dto.setQuarterName(goal.getQuarter().getQuarterCode());
			dto.setQuarterId(goal.getQuarter().getQuarterId());
			dto.setFinancialYearId(goal.getFinancialYear().getFinancialYearId());
			dto.setGoalCompletionPercentage(
					goal.getGoalCompletionPercentage() != null ? goal.getGoalCompletionPercentage() : BigDecimal.ZERO);

//			String filePath = goal.getDocumentPath();
//
//			if (filePath != null) {
//				try {
//					dto.setDocumentBytes(Files.readAllBytes(Paths.get(filePath)));
//					log.debug("Read document for goalId={} from path={}", goal.getGoalId(), filePath);
//				} catch (IOException e) {
//					dto.setDocumentBytes(null);
//					log.warn("Failed to read document for goalId={} from path={}: {}", goal.getGoalId(), filePath,
//							e.getMessage());
//				}
//			}
			dto.setFinancialYear(
					goal.getFinancialYear() != null ? goal.getFinancialYear().getFinancialYearCode() : "Unknown");

			return dto;
		}).collect(Collectors.toList());
		log.info("Mapped {} goals to DTOs for employeeId={}", goalDTOList.size(), employeeId);
		List<Map<String, Object>> groupedGoalList = goalDTOList.stream().collect(
				Collectors.groupingBy(PerformanceGoalDTO::getFinancialYear, LinkedHashMap::new, Collectors.toList()))
				.entrySet().stream().map(entry -> {
					Map<String, Object> yearBlock = new LinkedHashMap<>();
					yearBlock.put("year", entry.getKey());
					yearBlock.put("goals", entry.getValue());
					return yearBlock;
				}).collect(Collectors.toList());

		employee.setGoalsByYear(groupedGoalList);
		log.info("Returning goals grouped by year for employeeId={}", employeeId);
		return employee;
	}

	public List<TeamAppraisalDetailsDTO> getTeamAppraisalEmployees(String managerSeq) {

	    log.info("Fetching team appraisal employees for managerSeq={}", managerSeq);

	    Integer loginId = Integer.parseInt(managerSeq);
	    int currentManagerSeqNo = loginId;

	    List<Integer> subordinates = masterRepository.getAllSubordinates(loginId);

	    if (subordinates == null || subordinates.isEmpty()) {
	        return Collections.emptyList();
	    }

	    List<Map<String, Object>> rows =
	            masterRepository.findTeamAppraisalDetails(subordinates, loginId);

	    log.debug("Fetched {} team appraisal rows for managerSeq={}",
	            rows != null ? rows.size() : 0, managerSeq);

	    return rows.stream().map(row -> {

	        TeamAppraisalDetailsDTO dto = new TeamAppraisalDetailsDTO();

	        dto.setEmployeeId(row.get("empId") != null ? ((Number) row.get("empId")).intValue() : null);
	        dto.setEmployeeName(row.get("callname") != null ? row.get("callname").toString() : "--");
	        dto.setDepartmentId(row.get("departmentid") != null ? ((Number) row.get("departmentid")).intValue() : null);
	        dto.setDepartment(row.get("department") != null ? row.get("department").toString() : "--");
	        dto.setDesignation(row.get("designation") != null ? row.get("designation").toString() : "--");
	        dto.setGender(row.get("gender") != null ? row.get("gender").toString() : "--");
	        dto.setGoalStatus(row.get("goalStatus") != null ? row.get("goalStatus").toString() : "--");
	        dto.setAppraisalStatus(row.get("appraisalStatus") != null ? row.get("appraisalStatus").toString() : "--");
	        dto.setAppraisalId(row.get("appraisalId") != null ? ((Number) row.get("appraisalId")).intValue() : null);

	        dto.setMngReview("--");
	        dto.setHodReview("--");
	        dto.setOverallRating("--");
	        dto.setEnable("--");

	        if (dto.getAppraisalId() != null) {

	            Integer appraisalId = dto.getAppraisalId();

	            log.debug("Processing appraisalId={} for employeeId={}", appraisalId, dto.getEmployeeId());

	            // -------------------------------------------------
	            // ✅ RATINGS LOGIC ADDED
	            // -------------------------------------------------

	            List<EmployeeAppraisalDetails> selfDetails =
	                    employeeAppraisalDetailsRepository
	                            .findByAppraisal_AppraisalId(appraisalId);

	            List<ManagerEmployeeAppraisal> managerDetails =
	                    managerEmployeeAppraisalRepository
	                            .findByEmployeeAppraisal_AppraisalId(appraisalId);

	            List<HodEmployeeAppraisal> hodDetails =
	                    hodEmployeeAppraisalRepository
	                            .findByEmployeeAppraisal_AppraisalId(appraisalId);

	            Double selfAvg = selfDetails.stream()
	                    .filter(d -> d.getRatingId() != null)
	                    .mapToDouble(EmployeeAppraisalDetails::getRatingId)
	                    .average()
	                    .orElse(0.0);

	            Double managerAvg = managerDetails.stream()
	                    .filter(d -> d.getRatingId() != null)
	                    .mapToDouble(ManagerEmployeeAppraisal::getRatingId)
	                    .average()
	                    .orElse(0.0);

	            Double hodAvg = hodDetails.stream()
	                    .filter(d -> d.getRatingId() != null)
	                    .mapToDouble(HodEmployeeAppraisal::getRatingId)
	                    .average()
	                    .orElse(0.0);

	            dto.setSelfRating(selfAvg == 0.0 ? null : round2(selfAvg));
	            dto.setManagerRating(managerAvg == 0.0 ? null : round2(managerAvg));
	            dto.setHodRating(hodAvg == 0.0 ? null : round2(hodAvg));

	            List<Double> validRatings = new ArrayList<>();
	            if (selfAvg > 0) validRatings.add(selfAvg);
	            if (managerAvg > 0) validRatings.add(managerAvg);
	            if (hodAvg > 0) validRatings.add(hodAvg);

	            Double overallAvg = validRatings.stream()
	                    .mapToDouble(Double::doubleValue)
	                    .average()
	                    .orElse(0.0);

	            if (overallAvg > 0) {
	                dto.setOverallRating(String.valueOf(Math.round(overallAvg))); // ✅ 2.6 → 3
	            } else {
	                dto.setOverallRating("--");
	            }

	            log.debug("Ratings → Self={}, Manager={}, HOD={}, Overall={}",
	                    selfAvg, managerAvg, hodAvg, dto.getOverallRating());

	            // -------------------------------------------------
	            // EXISTING REVIEW LOGIC
	            // -------------------------------------------------

	            List<Map<String, Object>> approverRows =
	                    masterRepository.findApproverStatuses(appraisalId);

	            List<AppraisalApproverStatusDTO> managerReviewList =
	                    approverRows.stream().map(ar -> {

	                        AppraisalApproverStatusDTO m = new AppraisalApproverStatusDTO();

	                        m.setApproverStatus(ar.get("approverStatus") != null
	                                ? ((Number) ar.get("approverStatus")).intValue()
	                                : null);

	                        m.setManagerId(ar.get("managerId") != null
	                                ? ((Number) ar.get("managerId")).intValue()
	                                : null);

	                        m.setManagerName(ar.get("managerName") != null
	                                ? ar.get("managerName").toString()
	                                : "--");

	                        m.setStatus(ar.get("status") != null
	                                ? ar.get("status").toString()
	                                : "--");

	                        return m;

	                    }).collect(Collectors.toList());

	            dto.setManagerReview(managerReviewList);

	            boolean anyPending = managerReviewList.stream()
	                    .anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1001);

	            boolean anyInProgress = managerReviewList.stream()
	                    .anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1002);

	            boolean allCompleted = !managerReviewList.isEmpty() &&
	                    managerReviewList.stream()
	                            .allMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1003);

	            if (anyPending) dto.setMngReview("Pending");
	            else if (anyInProgress) dto.setMngReview("In Progress");
	            else if (allCompleted) dto.setMngReview("Completed");

	            List<Map<String, Object>> hodRows =
	                    masterRepository.findHodApproverStatuses(appraisalId);

	            if (hodRows != null && !hodRows.isEmpty()) {

	                boolean anyHodInProgress = hodRows.stream().anyMatch(hr -> {
	                    Integer status = hr.get("approverStatus") != null
	                            ? ((Number) hr.get("approverStatus")).intValue()
	                            : null;
	                    return status != null && status == 1002;
	                });

	                boolean allHodCompleted = hodRows.stream().allMatch(hr -> {
	                    Integer status = hr.get("approverStatus") != null
	                            ? ((Number) hr.get("approverStatus")).intValue()
	                            : null;
	                    return status != null && status == 1003;
	                });

	                if (anyHodInProgress) dto.setHodReview("Pending");
	                else if (allHodCompleted) dto.setHodReview("Completed");
	            }

	            boolean managerSubmitted = managerReviewList.stream()
	                    .filter(m -> m.getManagerId() != null && m.getManagerId() == currentManagerSeqNo)
	                    .anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1003);

	            boolean managerInProgress = managerReviewList.stream()
	                    .filter(m -> m.getManagerId() != null && m.getManagerId() == currentManagerSeqNo)
	                    .anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1002);

	            boolean hodSubmitted = hodRows != null && hodRows.stream()
	                    .anyMatch(hr -> ((Number) hr.get("approverStatus")).intValue() == 1003);

	            boolean hodInProgress = hodRows != null && hodRows.stream()
	                    .anyMatch(hr -> ((Number) hr.get("approverStatus")).intValue() == 1002);

	            if (managerSubmitted || hodSubmitted) dto.setEnable("Y");
	            else if (managerInProgress || hodInProgress) dto.setEnable("K");
	            else dto.setEnable("N");

	            log.debug("Set enable status={} for employeeId={} appraisalId={}",
	                    dto.getEnable(), dto.getEmployeeId(), appraisalId);
	        }

	        return dto;

	    }).collect(Collectors.toList());
	}


//	// @Cacheable(value = "employeeDetailsByAppraisal", key = "#appraisalId")
//	public List<TeamAppraisalDetailsDTO> getEmployeeDetailsById(Integer appraisalId) {
//		log.info("Fetching employee details for appraisalId={}", appraisalId);
//		EmployeeAppraisal appraisal = employeeAppraisalRepository.findById(appraisalId).orElseThrow(() -> {
//			log.error("Appraisal not found for id={}", appraisalId);
//			return new RuntimeException("Appraisal not found for id: " + appraisalId);
//		});
//		Integer employeeId = appraisal.getEmployeeId();
//		Long empSeqNo = employeeId.longValue();
//		LocalDate fromDate = appraisal.getFinancialYear().getStartDate();
//		LocalDate toDate   = appraisal.getFinancialYear().getEndDate();
//		log.debug("Found employeeId={} for appraisalId={}", employeeId, appraisalId);
//
//List<Map<String, Object>> leaveData =
//        masterRepository.findUsedAndLopBetweenDates(empSeqNo, fromDate, toDate);
//Integer used = 0;
//Integer lop  = 0;
//
//if (leaveData != null && !leaveData.isEmpty()) {
//    Map<String, Object> leaveRow = leaveData.get(0);
//
//    if (leaveRow.get("used") != null) {
//        used = ((Number) leaveRow.get("used")).intValue();
//    }
//
//    if (leaveRow.get("lop") != null) {
//        lop = ((Number) leaveRow.get("lop")).intValue();
//    }
//}
//		List<Map<String, Object>> empRows = masterRepository.findEmployeeDetailsByEmployeeSeqNo(empSeqNo);
//		log.info("Found {} employee detail rows for empSeqNo={}", empRows.size(), empSeqNo);
//		return empRows.stream().map(row -> {
//
//			Integer empId = ((Number) row.get("empid")).intValue();
//			log.debug("Processing employeeId={}", empId);
//			List<EducationDetails> eduList = masterRepository.findEducationDetailsOfEmployee(empId).stream()
//					.map(e -> new EducationDetails((String) e.get("qualificationid"),
//							String.valueOf(e.get("yearofpassing"))))
//					.toList();
//			log.debug("Found {} education records for employeeId={}", eduList.size(), empId);
//			TeamAppraisalDetailsDTO dto = new TeamAppraisalDetailsDTO();
//
//			dto.setEmployeeId(((Number) row.get("employeeId")).intValue());
//			dto.setEmployeeName((String) row.get("callName"));
//			dto.setDepartmentId((Integer) row.get("departmentid"));
//			dto.setDepartment((String) row.get("departmentName"));
//			dto.setDesignation((String) row.get("designationName"));
//			dto.setGender((String) row.get("gender"));
//			dto.setDoj(row.get("DATEOFJOIN") != null ? row.get("DATEOFJOIN").toString() : null);
//			dto.setLocation((String) row.get("HQ"));
//			dto.setWorkingDays(String.valueOf(row.get("EmployeeDaysCount")));
//
//			dto.setPrevExp(String.valueOf(row.get("previousExp")));
//			dto.setCurExp(String.valueOf(row.get("currentExp")));
//			dto.setTotalExp(String.valueOf(row.get("totalExp")));
//			dto.setAppraisalId(appraisalId);
//			dto.setEducationDetails(eduList);
//			dto.setUtilizedLeaves(String.valueOf(used));
//			dto.setLossOfPay(String.valueOf(lop));
//			return dto;
//
//		}).toList();
//	}
	public List<TeamAppraisalDetailsDTO> getEmployeeDetailsById(Integer appraisalId) {

	    log.info("Fetching employee details for appraisalId={}", appraisalId);

	    EmployeeAppraisal appraisal = employeeAppraisalRepository
	            .findById(appraisalId)
	            .orElseThrow(() -> {
	                log.error("Appraisal not found for id={}", appraisalId);
	                return new RuntimeException("Appraisal not found for id: " + appraisalId);
	            });

	    Integer employeeId = appraisal.getEmployeeId();
	    Long empSeqNo = employeeId.longValue();

	    LocalDate fromDate = appraisal.getFinancialYear().getStartDate();
	    LocalDate toDate   = appraisal.getFinancialYear().getEndDate();

	    log.debug("EmployeeId={}, FromDate={}, ToDate={}", employeeId, fromDate, toDate);

	    // =========================
	    // FETCH USED & LOP
	    // =========================
	    List<Map<String, Object>> leaveData =
	            masterRepository.findUsedAndLopBetweenDates(empSeqNo, fromDate, toDate);

	    int tempUsed = 0;
	    int tempLop  = 0;

	    if (leaveData != null && !leaveData.isEmpty()) {
	        Map<String, Object> leaveRow = leaveData.get(0);

	        if (leaveRow.get("used") != null) {
	            tempUsed = ((Number) leaveRow.get("used")).intValue();
	        }

	        if (leaveRow.get("lop") != null) {
	            tempLop = ((Number) leaveRow.get("lop")).intValue();
	        }
	    }

	    final int used = tempUsed;
	    final int lop  = tempLop;

	    // =========================
	    // FETCH EMPLOYEE DETAILS
	    // =========================
	    List<Map<String, Object>> empRows =
	            masterRepository.findEmployeeDetailsByEmployeeSeqNo(empSeqNo);

	    log.info("Found {} employee detail rows for empSeqNo={}", empRows.size(), empSeqNo);

	    return empRows.stream().map(row -> {

	        Integer empId = ((Number) row.get("empid")).intValue();

	        List<EducationDetails> eduList =
	                masterRepository.findEducationDetailsOfEmployee(empId)
	                        .stream()
	                        .map(e -> new EducationDetails(
	                                (String) e.get("qualificationid"),
	                                String.valueOf(e.get("yearofpassing"))
	                        ))
	                        .toList();

	        TeamAppraisalDetailsDTO dto = new TeamAppraisalDetailsDTO();

	        dto.setEmployeeId(((Number) row.get("employeeId")).intValue());
	        dto.setEmployeeName((String) row.get("callName"));
	        dto.setDepartmentId((Integer) row.get("departmentid"));
	        dto.setDepartment((String) row.get("departmentName"));
	        dto.setDesignation((String) row.get("designationName"));
	        dto.setGender((String) row.get("gender"));
	        dto.setDoj(row.get("DATEOFJOIN") != null ? row.get("DATEOFJOIN").toString() : null);
	        dto.setLocation((String) row.get("HQ"));
	        dto.setWorkingDays(String.valueOf(row.get("EmployeeDaysCount")));
	        dto.setPrevExp(String.valueOf(row.get("previousExp")));
	        dto.setCurExp(String.valueOf(row.get("currentExp")));
	        dto.setTotalExp(String.valueOf(row.get("totalExp")));
	        dto.setAppraisalId(appraisalId);
	        dto.setEducationDetails(eduList);

	        // ✅ Set Used & LOP
	        dto.setUtilizedLeaves(String.valueOf(used));
	        dto.setLossOfPay(String.valueOf(lop));

	        return dto;

	    }).toList();
	}

//
//	public List<AppraisalManagerDeptQuestion> getQuestionsByModuleId(Integer moduleId) {
//		if (moduleId == null) {
//			return Collections.emptyList();
//		}
//		
//		return appraisalManagerDeptQuestionRepository.findByDeptModule_DeptModuleIdAndDeptModule_Status(moduleId, 1001);
//	}
	public List<AppraisalManagerDeptQuestion> getQuestionsByModuleId(Integer moduleId) {
		log.info("Fetching questions for moduleId={}", moduleId);

		if (moduleId == null) {
			log.warn("moduleId is null, returning empty list");
			return Collections.emptyList();
		}

		List<AppraisalManagerDeptQuestion> questions = appraisalManagerDeptQuestionRepository
				.findByDeptModule_DeptModuleIdAndDeptModule_Status(moduleId, 1001);

		log.info("Found {} questions for moduleId={}", questions.size(), moduleId);
		return questions;
	}

	@Transactional
	public List<ManagerEmployeeAppraisal> saveManagerAppraisal(ManagerEmployeeAppraisalDTO dto) {
		log.info("Saving manager appraisal for appraisalId={}, managerId={}, type={}", dto.getAppraisalId(),
				dto.getManagerId(), dto.getType());
		// Fetch employee appraisal only if it is already submitted (status = 1002)
//		EmployeeAppraisal employeeAppraisal = employeeAppraisalRepository
//				.findByAppraisalIdAndStatus(dto.getAppraisalId(), 1002)
//				.orElseThrow(() -> new InvalidCredentialsException(
//						"Employee Appraisal not found or not submitted for appraisal id: " + dto.getAppraisalId()));
		EmployeeAppraisal employeeAppraisal = employeeAppraisalRepository
				.findByAppraisalIdAndStatus(dto.getAppraisalId(), 1002).orElseThrow(() -> {
					log.error("Employee Appraisal not found or not submitted for appraisalId={}", dto.getAppraisalId());
					return new InvalidCredentialsException(
							"Employee Appraisal not found or not submitted for appraisal id: " + dto.getAppraisalId());
				});
		List<ManagerEmployeeAppraisal> savedAppraisals = new ArrayList<>();

		Integer newStatus = "draft".equalsIgnoreCase(dto.getType()) ? 1001 : 1002;
		log.info("New status for manager appraisal: {}", newStatus);
//		EmployeeAppraisalApprovers currentApprover = employeeAppraisalApproversRepository
//				.findByAppraisal_AppraisalIdAndApproverEmpId(dto.getAppraisalId(), dto.getManagerId())
//				.orElseThrow(() -> new InvalidCredentialsException(
//						"Approver not found for manager id: " + dto.getManagerId()));
		EmployeeAppraisalApprovers currentApprover = employeeAppraisalApproversRepository
				.findByAppraisal_AppraisalIdAndApproverEmpId(dto.getAppraisalId(), dto.getManagerId())
				.orElseThrow(() -> {
					log.error("Approver not found for managerId={} and appraisalId={}", dto.getManagerId(),
							dto.getAppraisalId());
					return new InvalidCredentialsException("Approver not found for manager id: " + dto.getManagerId());
				});
		if (!"M".equals(currentApprover.getApproverRole())) {
			log.warn("Current approver role is not 'M', skipping save");
			return Collections.emptyList();
		}

		int currentLevel = currentApprover.getApproverLevel();
		log.info("Current approver level={}", currentLevel);
		if (newStatus.equals(1002)) {

			if (currentLevel > 1) {
				int prevLevel = currentLevel - 1;

				EmployeeAppraisalApprovers previousApprover = employeeAppraisalApproversRepository
						.findByAppraisal_AppraisalIdAndApproverLevel(dto.getAppraisalId(), prevLevel);

				if (!previousApprover.getApproverStatus().equals(1003)) {
					log.error("Cannot submit. Previous approver at level {} has not submitted yet", prevLevel);
					throw new InvalidCredentialsException("Cannot submit appraisal. Previous approver at level "
							+ prevLevel + " has not submitted yet.");
				}
			}
		}

		if (dto.getDetails() != null) {
			for (EmployeeAppraisalDetailDTO detailDto : dto.getDetails()) {
				  if (detailDto.getRatingId() == null) {
				        log.warn("Missing rating for questionId={}", detailDto.getQuestionId());
				        throw new InvalidCredentialsException(
				                "Please provide rating for all questions."
				        );
				    }

				    if (detailDto.getRatingComment() == null || detailDto.getRatingComment().trim().isEmpty()) {
				        log.warn("Missing comment for questionId={}", detailDto.getQuestionId());
				        throw new InvalidCredentialsException(
				                "Please provide comment for all questions."
				        );
				    }

				log.info("Processing questionId={} for appraisalId={}", detailDto.getQuestionId(),
						dto.getAppraisalId());
				Optional<ManagerEmployeeAppraisal> existingOpt = managerEmployeeAppraisalRepository
						.findByEmployeeAppraisal_AppraisalIdAndManagerIdAndQuestion_QuestionId(dto.getAppraisalId(),
								dto.getManagerId(), detailDto.getQuestionId());

				ManagerEmployeeAppraisal appraisalDetail;

				if (existingOpt.isPresent()) {
					appraisalDetail = existingOpt.get();

					if (appraisalDetail.getStatus() != null && appraisalDetail.getStatus().equals(1002)) {
						log.error("Appraisal already submitted for questionId={}, cannot update",
								detailDto.getQuestionId());
						throw new InvalidCredentialsException("Appraisal already submitted and cannot be updated.");
					}
				} else {
					appraisalDetail = new ManagerEmployeeAppraisal();
					appraisalDetail.setEmployeeAppraisal(employeeAppraisal);
					appraisalDetail.setManagerId(dto.getManagerId());
					appraisalDetail.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				}

				appraisalDetail.setStatus(newStatus);

//				AppraisalManagerDeptQuestion question = appraisalManagerDeptQuestionRepository
//						.findById(detailDto.getQuestionId()).orElseThrow(() -> new InvalidCredentialsException(
//								"Question not found with id: " + detailDto.getQuestionId()));
				AppraisalManagerDeptQuestion question = appraisalManagerDeptQuestionRepository
						.findById(detailDto.getQuestionId()).orElseThrow(() -> {
							log.error("Question not found with id={}", detailDto.getQuestionId());
							return new InvalidCredentialsException(
									"Question not found with id: " + detailDto.getQuestionId());
						});
				appraisalDetail.setQuestion(question);
				appraisalDetail.setRatingId(detailDto.getRatingId());
				appraisalDetail.setComment(detailDto.getRatingComment());
				appraisalDetail.setLastUpdate(new Timestamp(System.currentTimeMillis()));

				ManagerEmployeeAppraisal saved = managerEmployeeAppraisalRepository.save(appraisalDetail);
				log.info("Saved appraisal detail: id={}, questionId={}, managerId={}", saved.getId(),
						detailDto.getQuestionId(), dto.getManagerId());
				savedAppraisals.add(saved);
			}
		}

		if (newStatus.equals(1002)) {
			log.info("Updating current approver and next approver statuses for appraisalId={}", dto.getAppraisalId());

			currentApprover.setApproverStatus(1003);
			currentApprover.setSubmittedDate(LocalDateTime.now());
			currentApprover.setLupdate(LocalDateTime.now());
			employeeAppraisalApproversRepository.save(currentApprover);

			EmployeeAppraisalApprovers nextApprover = employeeAppraisalApproversRepository
					.findByAppraisal_AppraisalIdAndApproverLevel(dto.getAppraisalId(), currentLevel + 1);

			if (nextApprover != null) {
				nextApprover.setApproverStatus(1002);
				nextApprover.setLupdate(LocalDateTime.now());
				employeeAppraisalApproversRepository.save(nextApprover);
			}
		}
		log.info("Manager appraisal saved successfully for appraisalId={}, managerId={}, totalDetailsSaved={}",
				dto.getAppraisalId(), dto.getManagerId(), savedAppraisals.size());
		return savedAppraisals;
	}

	public List<SummaryDTO> getTeamAppraisalSummary(String managerId) {

		Integer managerEmpSeq = Integer.parseInt(managerId);

		List<Integer> subordinateEmpSeqNos = masterRepository.getAllSubordinates(managerEmpSeq);

		if (subordinateEmpSeqNos == null || subordinateEmpSeqNos.isEmpty()) {
			return List.of(new SummaryDTO("Goals", 0, 0), new SummaryDTO("Self Appraisals", 0, 0), new SummaryDTO("Completed Self Appraisals", 0, 0),
					new SummaryDTO("Manager Review Completed", 0, 0));
		}

		int totalEmployees = subordinateEmpSeqNos.size();

		int goalsCount = performanceGoalRepository.countByEmployeeIdInAndStatusIn(subordinateEmpSeqNos,
				Arrays.asList(1002, 1003, 1005));

		int selfAppraisalCount = employeeAppraisalRepository.countByEmployeeIdInAndStatusIn(subordinateEmpSeqNos,
				Arrays.asList(1002, 1003));
		long eligibleEmployeeCount =
			    appraisalEligibleEmployeeRepository.countByEmployeeIdIn(
			        subordinateEmpSeqNos.stream()
			            .map(Long::valueOf)
			            .toList()
			    );


		int managerReviewCompletedCount = employeeAppraisalApproversRepository
				.countManagerReviewCompleted(subordinateEmpSeqNos, managerEmpSeq);
		  int completedSelfAppraisalCount = employeeAppraisalRepository
		            .countByEmployeeIdInAndStatus(subordinateEmpSeqNos, 1003);
		return List.of(new SummaryDTO("Goals", goalsCount, totalEmployees),
				new SummaryDTO("Self Appraisals", selfAppraisalCount, (int) eligibleEmployeeCount),  new SummaryDTO("Completed Self Appraisals", completedSelfAppraisalCount, (int) eligibleEmployeeCount),

				new SummaryDTO("Manager Review Completed", managerReviewCompletedCount, (int) eligibleEmployeeCount));
	}

	public List<PerformanceGoalDTO> getEmployeeGoalsByFinancialYearId(Integer employeeId, Integer financialYearId) {
		log.info("Fetching employee goals for employeeId={}, financialYearId={}", employeeId, financialYearId);
		List<Integer> statuses = Arrays.asList(1002, 1003); // Approved, Completed

		List<PerformanceGoal> goals = performanceGoalRepository
				.findByEmployeeIdAndFinancialYear_FinancialYearIdAndStatusIn(employeeId, financialYearId, statuses);
		log.info("Found {} goals for employeeId={}, financialYearId={}", goals.size(), employeeId, financialYearId);

		return goals.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	private PerformanceGoalDTO convertToDto(PerformanceGoal goal) {
		PerformanceGoalDTO dto = new PerformanceGoalDTO();
		dto.setGoalId(goal.getGoalId());
		dto.setEmployeeId(goal.getEmployeeId());
		dto.setTitle(goal.getTitle());
		dto.setDescription(goal.getDescription());
		if (goal.getGoalCategory() != null) {
			dto.setGoalCategoryId(goal.getGoalCategory().getGoalCategoryId());
			dto.setGoalCategoryName(goal.getGoalCategory().getCategoryName());
		}
		dto.setTimeline(goal.getTimeline());
		dto.setStatus(goal.getStatus());
		dto.setStatusName(goal.getMasterStatus() != null ? goal.getMasterStatus().getStatusName() : null);
		if (goal.getFinancialYear() != null) {
			dto.setFinancialYearId(goal.getFinancialYear().getFinancialYearId());
			dto.setFinancialYear(String.valueOf(goal.getFinancialYear().getYear()));
		}
		if (goal.getQuarter() != null) {
			dto.setQuarterId(goal.getQuarter().getQuarterId());
			dto.setQuarterName(goal.getQuarter().getQuarterCode());
		}
		dto.setCreatedBy(goal.getCreatedBy());
		dto.setUpdateBy(goal.getUpdateBy());
		dto.setApprovedManagerId(goal.getApprovedManagerId());
		dto.setApprovalDate(goal.getApprovalDate() != null ? goal.getApprovalDate().toString() : null);
		dto.setManagerComment(goal.getManagerComment());
		dto.setFeedBackRating(goal.getFeedbackRating() != null ? goal.getFeedbackRating() : 0);
		dto.setFeedBackComment(goal.getFeedbackComment());
		dto.setImplementedGoal(goal.getImplementedGoal());
		dto.setFeedBackDate(goal.getFeedbackDate() != null ? goal.getFeedbackDate().toString() : null);
//		dto.setDocumentName(goal.getDocumentName());
		dto.setLupdate(goal.getLupdate() != null ? goal.getLupdate() : null);
		dto.setUpdatedDate(goal.getUpdatedDate());
		log.debug("Converted PerformanceGoal (id={}) to DTO", goal.getGoalId());
		return dto;
	}

	public AppModuleDTO getEmployeeAppraisalDataByIdModuleWise(Integer moduleId, Integer appraisalId) {
	    log.info("Fetching appraisal data for moduleId={} and appraisalId={}", moduleId, appraisalId);

	    AppraisalDeptModule module = appraisalDeptModuleRepository.findById(moduleId)
	            .orElseThrow(() -> new RuntimeException("Module not found"));

	    AppModuleDTO moduleDTO = new AppModuleDTO();
	    moduleDTO.setModuleId(module.getDeptModuleId());
	    moduleDTO.setModuleName(module.getAppraisalModule().getAppraisalModuleName());

	    List<AppraisalDeptQuestion> questions = appraisalDeptQuestionRepository
	            .findByDeptModule_DeptModuleIdAndStatus(moduleId, 1001);
	    List<EmployeeAppraisalDetails> appraisalDetails = employeeAppraisalDetailsRepository
	            .findByAppraisal_AppraisalId(appraisalId);

	    Map<Integer, EmployeeAppraisalDetails> detailsMap = appraisalDetails.stream()
	            .collect(Collectors.toMap(d -> d.getQuestion().getQuestionId(), d -> d));

	    List<EmployeeAppraisalDetailDTO> detailDTOList = new ArrayList<>();

	    double totalRating = 0;
	    int ratingCount = 0;

	    for (AppraisalDeptQuestion q : questions) {
	        EmployeeAppraisalDetailDTO dto = new EmployeeAppraisalDetailDTO();
	        dto.setQuestionId(q.getQuestionId());
	        dto.setQuestionName(q.getQuestionText());

	        EmployeeAppraisalDetails details = detailsMap.get(q.getQuestionId());
	        if (details != null) {
	            dto.setAppraisalDetailId(details.getAppraisalDetailId());
	            dto.setRatingId(details.getRatingId());
	            dto.setRatingComment(details.getRatingComment());
	            dto.setCreatedDate(details.getCreatedDate());

	            if (details.getRatingId() != null) {
	                totalRating += details.getRatingId();
	                ratingCount++;
	            }
	        }

	        detailDTOList.add(dto);
	    }

	    moduleDTO.setEmployeeAppraisal(detailDTOList);

	    // Calculate exact average
	    double avgRating = ratingCount > 0 ? totalRating / ratingCount : 0;
	    moduleDTO.setAverageRating(avgRating); // exact average

	    // Apply custom rounding rules
	    int roundedRating;
	    if (avgRating >= 3.5) {
	        roundedRating = 4;
	    } else if (avgRating <= 3.2) {
	        roundedRating = 3;
	    } else {
	        roundedRating = (int) Math.round(avgRating); // normal rounding in between
	    }
	    moduleDTO.setRoundedAverageRating(roundedRating); // add this field in AppModuleDTO

	    log.info("Module exact average rating={} and rounded rating={} for moduleId={} and appraisalId={}", 
	             avgRating, roundedRating, moduleId, appraisalId);

	    return moduleDTO;
	}

	public EmployeeAppraisalDTO getAppraisalDetails(Integer appraisalId, Integer managerId) {
		log.info("Fetching appraisal details for appraisalId={} and managerId={}", appraisalId, managerId);
		EmployeeAppraisal appraisal = employeeAppraisalRepository
				.findByAppraisalIdAndStatusInWithFinancialYear(appraisalId, Arrays.asList(1002, 1003))
				.orElseThrow(() -> new InvalidCredentialsException("Appraisal not found"));

		EmployeeAppraisalApprovers approver = employeeAppraisalApproversRepository
				.findByAppraisal_AppraisalIdAndApproverEmpId(appraisalId, managerId)
				.orElseThrow(() -> new InvalidCredentialsException("Approver not found"));

		String role = approver.getApproverRole();
		Integer level = approver.getApproverLevel();
		Integer employeeId = appraisal.getEmployeeId();

		Integer departmentId = masterRepository.findDepartmentIdByEmployeeCode(String.valueOf(employeeId));
		if (departmentId == null) {
			departmentId = 0;
		}

		EmployeeAppraisalDTO dto = new EmployeeAppraisalDTO();
		dto.setAppraisalId(appraisal.getAppraisalId());
		dto.setEmployeeId(employeeId);
		dto.setRole(role);

		// Assuming financialYear is fetched eagerly or session is open
		MasterFinancialYear financialYear = appraisal.getFinancialYear();
		if (financialYear == null) {
			throw new InvalidCredentialsException("Financial year details not found");
		}
		dto.setFinancialYearId(financialYear.getFinancialYearId());
		dto.setFinancialYear(financialYear.getFinancialYearCode());

		dto.setQuarterId(appraisal.getQuarter() != null ? appraisal.getQuarter().getQuarterId() : null);
		dto.setCreatedDate(appraisal.getCreatedDate());

		LocalDate fromDate = financialYear.getStartDate();
		LocalDate toDate = financialYear.getEndDate();

		System.err.println("fromDate: " + fromDate);
		System.err.println("toDate: " + toDate);

		LocalDateTime fromDateTime = fromDate.atStartOfDay();
		LocalDateTime toDateTime = toDate.atTime(LocalTime.MAX);

		System.err.println("fromDateTime: " + fromDateTime);
		System.err.println("toDateTime: " + toDateTime);

		// For role M or H, fetch goals and works
		if ("M".equalsIgnoreCase(role) || "H".equalsIgnoreCase(role)) {
//	        List<PerformanceGoalDTO> goals = getEmployeeGoalsByFinancialYearId(employeeId, financialYear.getFinancialYearId());
//	        dto.setPerformanceGoalDTO(goals);
			List<PerformanceGoalDTO> goals = getEmployeeGoalsByFinancialYearId(employeeId, fromDateTime, toDateTime);
			dto.setPerformanceGoalDTO(goals);
//			List<EmployeeAppraisalWorkDTO> works = getWorksByEmployeeIdAndFinanicalYearId(employeeId,
//					financialYear.getFinancialYearId());
			List<EmployeeAppraisalWorkDTO> works = getWorksByEmployeeIdAndFinanicalYearId(employeeId, fromDateTime,
					toDateTime);
			log.debug("Fetched {} goals and {} works for employeeId={}", goals.size(), works.size(), employeeId);
			dto.setEmployeeAppraisalWorkDTO(works);
		}

		if ("M".equalsIgnoreCase(role)) {
			List<EmployeeAppraisalApprovers> previousManagers = employeeAppraisalApproversRepository
					.findByAppraisal_AppraisalIdAndApproverRoleAndApproverLevelLessThanAndApproverStatus(appraisalId,
							"M", level, 1003);

			List<ManagerFeedbackDTO> previousFeedback = buildPreviousManagerFeedback(previousManagers, departmentId,
					appraisalId);
			ManagerModuleDTO managerModules = buildManagerModulesWithFlag(appraisalId, departmentId, managerId);

			dto.setPreviousManagerFeedback(previousFeedback);
			dto.setManagerModules(managerModules);
			log.info("Manager role: set previous feedback and modules for managerId={}", managerId);
			return dto;
		}

		if ("H".equalsIgnoreCase(role)) {
			List<EmployeeAppraisalApprovers> completedManagers = employeeAppraisalApproversRepository
					.findByAppraisal_AppraisalIdAndApproverRoleAndApproverStatus(appraisalId, "M", 1003);

			List<ManagerFeedbackDTO> previousFeedback = buildPreviousManagerFeedback(completedManagers, departmentId,
					appraisalId);

			List<HodEmployeeAppraisal> hodAnswers = hodEmployeeAppraisalRepository
					.findByHodIdAndEmployeeAppraisal_AppraisalId(managerId, appraisalId);

			HodFeedbackDTO hodFeedback = hodAnswers.isEmpty()
					? buildHodFeedbackPending(managerId, departmentId, appraisal, level)
					: buildHodFeedback(managerId, departmentId, appraisal, level);

			String hodFlag = resolveFlag(hodAnswers.isEmpty() ? null : 1002, approver.getApproverStatus());
			hodFeedback.setFlag(hodFlag);

			dto.setPreviousManagerFeedback(previousFeedback);
			dto.setHodFeedback(hodFeedback);

			Integer hodStatus = hodAnswers.isEmpty() ? 0 : hodAnswers.get(0).getStatus();
			hodFeedback.setStatus(hodStatus);
			log.info("HOD role: set feedback and status={} for managerId={}", hodStatus, managerId);
			return dto;
		}

		throw new InvalidCredentialsException("Invalid approver role");
	}

	private HodFeedbackDTO buildHodFeedbackPending(Integer hodId, Integer departmentId, EmployeeAppraisal appraisal,
			Integer level) {
		log.info("Building pending HOD feedback for hodId={}, departmentId={}, appraisalId={}, level={}", hodId,
				departmentId, appraisal.getAppraisalId(), level);
		List<AppraisalDeptModule> deptModules = appraisalDeptModuleRepository.findByDepartmentIdAndStatus(departmentId,
				1001);

		if (deptModules.isEmpty() && departmentId != 0) {
			log.info("Fallback to default department modules as no modules found for departmentId={}", departmentId);
			deptModules = appraisalDeptModuleRepository.findByDepartmentIdAndStatus(0, 1001);
		}

		List<AppModuleDTO> moduleDTOs = new ArrayList<>();

//		for (AppraisalDeptModule deptModule : deptModules) {
//
//			AppraisalModule module = deptModule.getAppraisalModule();
//			if (module == null)
//				continue;
//
//			List<EmployeeAppraisalDetailDTO> questionDTOs = hodAppraisalDeptQuestionRepository
//					.findByDeptModule_DeptModuleIdAndStatus(deptModule.getDeptModuleId(), 1001).stream().map(q -> {
//						EmployeeAppraisalDetailDTO dto = new EmployeeAppraisalDetailDTO();
//						dto.setQuestionId(q.getQuestionId());
//						dto.setQuestionName(q.getQuestionText());
//						dto.setAppraisalDetailId(null);
//						dto.setRatingId(null);
//						dto.setRatingComment(null);
//						log.debug("Prepared pending questionId={} for moduleId={}", q.getQuestionId(),
//								module.getAppraisalModuleId());
//						return dto;
//					}).collect(Collectors.toList());
//
//			moduleDTOs.add(new AppModuleDTO(module.getAppraisalModuleId(), module.getAppraisalModuleName(), null,
//					questionDTOs));
//			log.info("Added moduleId={} with {} pending questions", module.getAppraisalModuleId(), questionDTOs.size());
//		}
		for (AppraisalDeptModule deptModule : deptModules) {

		    AppraisalModule module = deptModule.getAppraisalModule();
		    if (module == null)
		        continue;

		    List<EmployeeAppraisalDetailDTO> questionDTOs = hodAppraisalDeptQuestionRepository
		            .findByDeptModule_DeptModuleIdAndStatus(deptModule.getDeptModuleId(), 1001)
		            .stream()
		            .map(q -> {
		                EmployeeAppraisalDetailDTO dto = new EmployeeAppraisalDetailDTO();
		                dto.setQuestionId(q.getQuestionId());
		                dto.setQuestionName(q.getQuestionText());
		                dto.setAppraisalDetailId(null);
		                dto.setRatingId(null);
		                dto.setRatingComment(null);
		                return dto;
		            })
		            .collect(Collectors.toList());

		    // ✅ If no questions → add "--"
		    if (questionDTOs.isEmpty()) {
		        EmployeeAppraisalDetailDTO emptyDto = new EmployeeAppraisalDetailDTO();
		        emptyDto.setQuestionId(null);
		        emptyDto.setQuestionName("--");
		        emptyDto.setAppraisalDetailId(null);
		        emptyDto.setRatingId(null);
		        emptyDto.setRatingComment(null);

		        questionDTOs.add(emptyDto);
		    }

		    moduleDTOs.add(new AppModuleDTO(
		            module.getAppraisalModuleId(),
		            module.getAppraisalModuleName(),
		            null,
		            questionDTOs
		    ));

		    log.info("Added moduleId={} with {} questions",
		            module.getAppraisalModuleId(),
		            questionDTOs.size());
		}
		Map<String, Object> empMap = masterRepository.getEmployeeDetails(hodId);
		HodFeedbackDTO hodDTO = new HodFeedbackDTO();
		hodDTO.setManagerId(hodId);
		hodDTO.setManagerName(empMap != null ? (String) empMap.get("fullName") : null);
		hodDTO.setApproverLevel(level);
		hodDTO.setFlag("Y");

		hodDTO.setModules(moduleDTOs);

		log.info("Built pending HOD feedback for hodId={}, totalModules={}", hodId, moduleDTOs.size());
		return hodDTO;
	}

	private List<ManagerFeedbackDTO> buildPreviousManagerFeedback(List<EmployeeAppraisalApprovers> previousManagers,
			Integer departmentId, Integer appraisalId) {
		System.err.print("i am coming here");
		log.info("Building previous manager feedback for appraisalId={}, departmentId={}, managersCount={}",
				appraisalId, departmentId, previousManagers.size());
		final List<AppraisalDeptModule> deptModules;
		List<AppraisalDeptModule> fetchedModules = appraisalDeptModuleRepository
				.findByDepartmentIdAndStatus(departmentId, 1001);

		if (fetchedModules.isEmpty() && departmentId != 0) {
			deptModules = appraisalDeptModuleRepository.findByDepartmentIdAndStatus(0, 1001);
			log.info("Fallback to default department modules as no modules found for departmentId={}", departmentId);
		} else {
			deptModules = fetchedModules;
		}
		log.info("Total deptModules used: {}", deptModules.size());
		return previousManagers.stream().map(manager -> {

			Integer managerId = manager.getApproverEmpId();
			Map<String, Object> empMap = masterRepository.getEmployeeDetails(managerId);
			String managerName = empMap != null ? (String) empMap.get("fullName") : null;
			log.info("Processing managerId={}, managerName={}", managerId, managerName);
			Map<Integer, ManagerEmployeeAppraisal> answerMap = managerEmployeeAppraisalRepository
					.findByManagerIdAndEmployeeAppraisal_AppraisalId(managerId, appraisalId).stream()
					.collect(Collectors.toMap(a -> a.getQuestion().getQuestionId(), Function.identity(), (o, n) -> n));
			log.info("Manager {} has {} answers", managerId, answerMap.size());
			List<AppModuleDTO> moduleDTOs = deptModules.stream().map(deptModule -> {

				AppraisalModule module = deptModule.getAppraisalModule();

				List<EmployeeAppraisalDetailDTO> questionDTOs = appraisalManagerDeptQuestionRepository
						.findByDeptModule_DeptModuleIdAndStatus(deptModule.getDeptModuleId(), 1001).stream().map(q -> {
							ManagerEmployeeAppraisal ans = answerMap.get(q.getQuestionId());

							EmployeeAppraisalDetailDTO dto = new EmployeeAppraisalDetailDTO();
							dto.setQuestionId(q.getQuestionId());
							dto.setQuestionName(q.getQuestionText());

							if (ans != null) {
								dto.setAppraisalDetailId(ans.getId());
								dto.setRatingId(ans.getRatingId());
								dto.setRatingComment(ans.getComment());
							}
							return dto;
						}).collect(Collectors.toList());
				log.info("ModuleId={} has {} questions", module.getAppraisalModuleId(), questionDTOs.size());
				return new AppModuleDTO(module.getAppraisalModuleId(), module.getAppraisalModuleName(), null,
						questionDTOs);

			}).collect(Collectors.toList());
			ManagerFeedbackDTO dto = new ManagerFeedbackDTO();
			dto.setManagerId(managerId);
			dto.setManagerName(managerName);
			dto.setApproverLevel(manager.getApproverLevel());
			dto.setFlag("Y");

			dto.setModules(moduleDTOs);
			log.info("Built ManagerFeedbackDTO for managerId={}, modulesCount={}", managerId, moduleDTOs.size());
			return dto;
		}).collect(Collectors.toList());
	}

	private HodFeedbackDTO buildHodFeedback(Integer hodId, Integer departmentId, EmployeeAppraisal appraisal,
			Integer level) {
		log.info("Building HOD feedback for hodId={}, departmentId={}, appraisalId={}, level={}", hodId, departmentId,
				appraisal.getAppraisalId(), level);

		List<AppraisalDeptModule> deptModules = appraisalDeptModuleRepository.findByDepartmentIdAndStatus(departmentId,
				1001);

		if (deptModules.isEmpty() && departmentId != 0) {
			deptModules = appraisalDeptModuleRepository.findByDepartmentIdAndStatus(0, 1001);
		}
		List<HodEmployeeAppraisal> hodAnswers = hodEmployeeAppraisalRepository
				.findByHodIdAndEmployeeAppraisal_AppraisalId(hodId, appraisal.getAppraisalId());

		final Map<Integer, HodEmployeeAppraisal> answerMap = new HashMap<>();
		for (HodEmployeeAppraisal ans : hodAnswers) {
			Integer qId = ans.getQuestion() != null ? ans.getQuestion().getQuestionId() : null;
			if (qId != null) {
				answerMap.put(qId, ans);
			}
		}
		log.info("Answer map populated with {} entries", answerMap.size());
		List<AppModuleDTO> moduleDTOs = new ArrayList<>();

		for (AppraisalDeptModule deptModule : deptModules) {

			AppraisalModule module = deptModule.getAppraisalModule();
			if (module == null) {
				System.out.println("DEBUG: DeptModule " + deptModule.getDeptModuleId() + " has null AppraisalModule");
				continue;
			}
			log.info("Processing moduleId={}, moduleName={}", module.getAppraisalModuleId(),
					module.getAppraisalModuleName());
			List<EmployeeAppraisalDetailDTO> questionDTOs = hodAppraisalDeptQuestionRepository
					.findByDeptModule_DeptModuleIdAndStatus(deptModule.getDeptModuleId(), 1001).stream().map(q -> {
						HodEmployeeAppraisal ans = answerMap.get(q.getQuestionId());

						EmployeeAppraisalDetailDTO dto = new EmployeeAppraisalDetailDTO();
						dto.setQuestionId(q.getQuestionId());
						dto.setQuestionName(q.getQuestionText());

						if (ans != null) {
							dto.setAppraisalDetailId(ans.getHodAppraisalDetailId());
							dto.setRatingId(ans.getRatingId());
							dto.setRatingComment(ans.getComment());
						} else {
							dto.setAppraisalDetailId(null);
							dto.setRatingId(null);
							dto.setRatingComment(null);
						}
						return dto;
					}).collect(Collectors.toList());
			log.info("ModuleId={} has {} questions", module.getAppraisalModuleId(), questionDTOs.size());

			moduleDTOs.add(new AppModuleDTO(module.getAppraisalModuleId(), module.getAppraisalModuleName(), null,
					questionDTOs));
		}

		Map<String, Object> empMap = masterRepository.getEmployeeDetails(hodId);

		HodFeedbackDTO hodDTO = new HodFeedbackDTO();
		hodDTO.setManagerId(hodId);
		hodDTO.setManagerName(empMap != null ? (String) empMap.get("fullName") : null);
		hodDTO.setApproverLevel(level);
		hodDTO.setModules(moduleDTOs);
		log.info("Returning HodFeedbackDTO with {} modules", moduleDTOs.size());
		return hodDTO;
	}

	private String resolveFlag(Integer appraisalStatus, Integer approverStatus) {

		if (appraisalStatus != null && approverStatus != null) {

//	        if (appraisalStatus == 1001 && approverStatus == 1002) {
//	            return "D";  
//	        }

			if (appraisalStatus == 1002 && approverStatus == 1003
					|| appraisalStatus == null && approverStatus != null && approverStatus == 1002) {
				return "Y";
			}
		}
		return "Y";
	}

	private ManagerModuleDTO buildManagerModulesWithFlag(Integer appraisalId, Integer departmentId, Integer managerId) {
		log.info("Building manager modules for appraisalId={}, departmentId={}, managerId={}", appraisalId,
				departmentId, managerId);
		List<ManagerEmployeeAppraisal> answers = managerEmployeeAppraisalRepository
				.findByEmployeeAppraisal_AppraisalIdAndManagerId(appraisalId, managerId);
		Integer managerStatus = answers.isEmpty() ? 0 : answers.get(0).getStatus();
		log.info("Manager status: {}", managerStatus);
		Map<Integer, ManagerEmployeeAppraisal> answerMap = answers.stream()
				.collect(Collectors.toMap(a -> a.getQuestion().getQuestionId(), Function.identity(), (o, n) -> n));
		List<AppraisalDeptModule> deptModules = appraisalDeptModuleRepository.findByDepartmentIdAndStatus(departmentId,
				1001);

		if (deptModules.isEmpty() && departmentId != 0) {
			deptModules = appraisalDeptModuleRepository.findByDepartmentIdAndStatus(0, 1001);
			log.info("Fallback to default department modules as no modules found for departmentId={}", departmentId);
		}

		Set<Integer> allowedModuleIds = deptModules.stream().map(AppraisalDeptModule::getDeptModuleId)
				.collect(Collectors.toSet());
		List<AppraisalModule> modules = appraisalModuleRepository.findAll().stream()
				.filter(m -> m.getStatus() != null && m.getStatus() == 1001)
				.filter(m -> allowedModuleIds.contains(m.getAppraisalModuleId())).collect(Collectors.toList());

		List<AppModuleDTO> appModuleDTOs = new ArrayList<>();

		long totalQuestions = 0;
		long answeredQuestions = 0;
		for (AppraisalModule module : modules) {

			Integer moduleId = module.getAppraisalModuleId();

			List<AppraisalManagerDeptQuestion> questions = appraisalManagerDeptQuestionRepository
					.findByDeptModule_DeptModuleIdAndDeptModule_Status(moduleId, 1001);
			log.info("ModuleId={} has {} questions", moduleId, questions.size());
			List<EmployeeAppraisalDetailDTO> details = new ArrayList<>();

			for (AppraisalManagerDeptQuestion q : questions) {

				ManagerEmployeeAppraisal ans = answerMap.get(q.getQuestionId());

				EmployeeAppraisalDetailDTO detail = new EmployeeAppraisalDetailDTO();
				detail.setQuestionId(q.getQuestionId());
				detail.setQuestionName(q.getQuestionText());

				if (ans != null) {
					detail.setAppraisalDetailId(ans.getId());
					detail.setRatingId(ans.getRatingId());
					detail.setRatingComment(ans.getComment());
				}

				details.add(detail);
			}

			totalQuestions += questions.size();
			answeredQuestions += details.stream().filter(d -> d.getRatingId() != null).count();

			appModuleDTOs.add(new AppModuleDTO(moduleId, module.getAppraisalModuleName(), null, details));
		}
		String flag = (totalQuestions > 0 && totalQuestions == answeredQuestions) ? "Y" : "N";
		log.info("Total questions: {}, Answered questions: {}, Flag set to: {}", totalQuestions, answeredQuestions,
				flag);
		ManagerModuleDTO managerModules = new ManagerModuleDTO(flag, appModuleDTOs);
		managerModules.setStatus(managerStatus);
		log.info("Returning ManagerModuleDTO with status={}", managerStatus);
		return managerModules;
	}

	private List<PerformanceGoalDTO> getEmployeeGoalsByFinancialYearId(Integer employeeId, LocalDateTime fromDate,
			LocalDateTime toDate) {
		List<Integer> statuses = Arrays.asList(1002, 1003, 1004,1005);
		log.info("Fetching goals for employeeId={}, fromDate={}, toDate={}, statuses={}", employeeId, fromDate, toDate,
				statuses);

		List<PerformanceGoal> goals = performanceGoalRepository
				.findByEmployeeIdAndStatusInAndCreatedDateBetween(employeeId, statuses, fromDate, toDate);
		log.info("Found {} goals for employeeId={}", goals.size(), employeeId);
		System.err.println("-----: " + goals.toString());
		return goals.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	public List<EmployeeAppraisalWorkDTO> getWorksByEmployeeIdAndFinanicalYearId(Integer employeeId,
			LocalDateTime fromDate, LocalDateTime toDate) {
		log.info("Fetching works for employeeId={} and financialYearId={}", employeeId, fromDate, toDate);
		List<Integer> statuses = Arrays.asList(1002); // approved works

//		List<EmployeeAppraisalWork> works = employeeAppraisalWorkRepository
//				.findByEmployeeIdAndFinancialYear_FinancialYearIdAndStatusIn(employeeId, financialYearId, statuses);
		List<EmployeeAppraisalWork> works = employeeAppraisalWorkRepository
				.findByEmployeeIdAndStatusInAndCreatedDateBetween(employeeId, statuses, fromDate, toDate);
		List<EmployeeAppraisalWorkDTO> dtos = works.stream().map(work -> {

			EmployeeAppraisalWorkDTO dto = new EmployeeAppraisalWorkDTO();

			dto.setWorkId(work.getWorkId());
			dto.setEmployeeId(work.getEmployeeId());

			if (work.getFinancialYear() != null) {
				dto.setFinancialYearId(work.getFinancialYear().getFinancialYearId());
				dto.setFinancialYear(work.getFinancialYear().getFinancialYearCode());
			}

			if (work.getQuarter() != null) {
				dto.setQuarterId(work.getQuarter().getQuarterId());
				dto.setType(work.getQuarter().getQuarterCode());
			}

			dto.setWorkTitle(work.getWorkTitle());
			dto.setWorkDescription(work.getWorkDescription());

			if (work.getWorkType() != null) {
				dto.setWorkTypeId(work.getWorkType().getWorkTypeId());
				dto.setWorkType(work.getWorkType().getWorkType());
			}

			dto.setCompletionPercentage(work.getCompletionPercentage());
			dto.setOutcome(work.getOutcome());
			dto.setStatus(work.getStatus());
			dto.setCreatedDate(work.getCreatedDate());
			dto.setUpdatedDate(work.getUpdatedDate());

			log.debug("Mapped work DTO: {}", dto);

			return dto;
		}).collect(Collectors.toList());

		log.info("Returning {} work DTOs for employeeId={}", dtos.size(), employeeId);
		return dtos;
	}

	public List<AppraisalTeamMemberDTO> getTeammembersAppraisalEmployees(Integer managerSeq) {
	    log.info("Fetching appraisal employees for managerSeq={}", managerSeq);

	    List<Integer> subordinates = masterRepository.getAllSubordinates(managerSeq);
	    if (subordinates == null || subordinates.isEmpty()) {
	        return Collections.emptyList();
	    }
	    List<Map<String, Object>> rows = masterRepository.findTeamAppraisalEmployees(subordinates, managerSeq);

//	    List<Map<String, Object>> rows = masterRepository.findTeamAppraisalEmployees(managerSeq,subordinates);
	    List<AppraisalTeamMemberDTO> result = new ArrayList<>();
	    List<Integer> feedbackStatuses = Arrays.asList(1002, 1003);

	    for (Map<String, Object> row : rows) {
	        AppraisalTeamMemberDTO dto = new AppraisalTeamMemberDTO();

	        // Safe conversion from Map
	        dto.setEmployeeId(row.get("employeeId") != null ? ((Number) row.get("employeeId")).intValue() : null);
	        dto.setEmployeeName(row.get("employeeName") != null ? row.get("employeeName").toString() : null);
	        dto.setDepartment(row.get("department") != null ? row.get("department").toString() : null);
	        dto.setDesignation(row.get("designation") != null ? row.get("designation").toString() : null);
	        dto.setAppraisalId(row.get("appraisalId") != null ? ((Number) row.get("appraisalId")).intValue() : null);

	        // Appraisal added flag
	        dto.setAddedToAppraisal(row.get("appraisalPresent") != null ? row.get("appraisalPresent").toString() : "NO");

	        // Peer feedback counts
	        dto.setPeerFeedbackRequested(row.get("peerRequested") != null ? ((Number) row.get("peerRequested")).intValue() : 0);
	        dto.setPeerFeedbackCompleted(row.get("peerSubmitted") != null ? ((Number) row.get("peerSubmitted")).intValue() : 0);
	        dto.setLastUpdated(row.get("lastUpdated") != null ? row.get("lastUpdated").toString() : "--");

	        // Last updated from latest feedback response
	        employee360FeedbackParticipantRepository
	            .findTopByManagerIdAndAppraisalIdAndParticipantStatusInOrderByRespondedOnDesc(managerSeq, dto.getAppraisalId(), feedbackStatuses)
	            .ifPresent(p -> dto.setLastUpdated(p.getRespondedOn() != null ? p.getRespondedOn().toString() : "--"));

	        result.add(dto);
	    }

	    log.info("Total appraisal team members fetched: {}", result.size());
	    return result;
	}

	public List<AppraisalTeamMemberDTO> getTeamMembersExceptSelf(Integer empSeq, Integer appraisalId,
			Integer managerId) {
		log.info("Fetching team members except self: empSeq={}, appraisalId={}, managerId={}", empSeq, appraisalId,
				managerId);
		Integer managerSeq = masterRepository.findDirectManager(empSeq);
		if (managerSeq == null) {
			return Collections.emptyList();
		}
		List<Integer> subordinates = masterRepository.getAllSubordinates(managerSeq);
		if (subordinates == null || subordinates.isEmpty()) {
			return Collections.emptyList();
		}
		List<Map<String, Object>> rows = masterRepository.findTeamMembersExceptSelfAndManagerMap(subordinates, empSeq,
				managerSeq, appraisalId, managerId);
		log.debug("Team members query returned {} rows", rows.size());
		List<AppraisalTeamMemberDTO> result = new ArrayList<>();

		for (Map<String, Object> row : rows) {

			AppraisalTeamMemberDTO dto = new AppraisalTeamMemberDTO();

			dto.setEmployeeId(row.get("employeeid") != null ? ((Number) row.get("employeeid")).intValue() : null);

			dto.setEmployeeName((String) row.get("employeename"));
			dto.setDepartment((String) row.get("department"));
			dto.setDesignation((String) row.get("designation"));
			dto.setEmailId((String) row.get("emailid"));
			dto.setRequestedDate((String) row.get("requestedDate"));
			dto.setActionStatus(row.get("actionStatus") != null ? ((Number) row.get("actionStatus")).intValue() : null);
			dto.setParticipantId(
					row.get("participantId") != null ? ((Number) row.get("participantId")).intValue() : null);
			log.debug("Mapped team member: {}", dto);
			result.add(dto);
		}
		log.info("Total team members (excluding self) fetched: {}", result.size());
		return result;
	}

	@Transactional
	public void saveFeedback(FeedbackRequestDTO dto) {
		log.info("Saving feedback for appraisalId={}, reviewerId={}", dto.getAppraisalId(),
				dto.getReviewerEmployeeId());
//		Employee360FeedbackParticipant participant = employee360FeedbackParticipantRepository
//				.findByAppraisalIdAndReviewerEmployeeId(dto.getAppraisalId(), dto.getReviewerEmployeeId())
//				.orElseGet(() -> createParticipant(dto));
		Employee360FeedbackParticipant participant = employee360FeedbackParticipantRepository
				.findByAppraisalIdAndReviewerEmployeeId(dto.getAppraisalId(), dto.getReviewerEmployeeId())
				.orElseGet(() -> {
					log.info("Participant not found, creating new participant for appraisalId={}, reviewerId={}",
							dto.getAppraisalId(), dto.getReviewerEmployeeId());
					return createParticipant(dto);
				});

		log.debug("Participant found/created: participantId={}, status={}", participant.getParticipantId(),
				participant.getParticipantStatus());
		if (participant.getParticipantStatus() == 1003) {
			throw new RuntimeException("Feedback already submitted for this participant");
		} else if (participant.getParticipantStatus() == 1002) {
			throw new RuntimeException("Feedback already requested but not submitted yet");
		}
		if ("submit".equalsIgnoreCase(dto.getType())) {
			participant.setParticipantStatus(1002);
			participant.setResponseStatus("S");
			log.info("Participant set to requested/submitted status: participantId={}", participant.getParticipantId());
		} else {
			participant.setParticipantStatus(1001);
			log.info("Participant set to draft/pending status: participantId={}", participant.getParticipantId());
		}
		employee360FeedbackParticipantRepository.save(participant);
		/*
		 * for (FeedbackParticipantDTO partDto : dto.getParticipants()) {
		 * MasterFeedbackCategory category =
		 * masterFeedbackCategoryRepository.findById(partDto.getCategoryId())
		 * .orElseThrow(() -> new InvalidCredentialsException("Category not found"));
		 */
		for (FeedbackParticipantDTO partDto : dto.getParticipants()) {
			MasterFeedbackCategory category = masterFeedbackCategoryRepository.findById(partDto.getCategoryId())
					.orElseThrow(() -> {
						log.error("Category not found: categoryId={}", partDto.getCategoryId());
						return new InvalidCredentialsException("Category not found");
					});
			log.debug("Processing category: categoryId={}, categoryName={}", category.getCategoryId(),
					category.getCategoryName());
			for (QuestionAnswerDTO qa : partDto.getAnswers()) {
//				if (qa.getAnswerId() != null) {
//					Employee360FeedbackAnswer answer = employee360FeedbackAnswerRepository.findById(qa.getAnswerId())
//							.orElseThrow(() -> new RuntimeException("Answer not found"));
//
//					answer.setAnswerText(qa.getAnswerText());
//					employee360FeedbackAnswerRepository.save(answer);
				if (qa.getAnswerId() != null) {
					Employee360FeedbackAnswer answer = employee360FeedbackAnswerRepository.findById(qa.getAnswerId())
							.orElseThrow(() -> {
								log.error("Answer not found: answerId={}", qa.getAnswerId());
								return new RuntimeException("Answer not found");
							});

					answer.setAnswerText(qa.getAnswerText());
					employee360FeedbackAnswerRepository.save(answer);
					log.debug("Updated existing answer: answerId={}, text='{}'", qa.getAnswerId(), qa.getAnswerText());

				} else {
					Employee360FeedbackAnswer answer = new Employee360FeedbackAnswer();
					answer.setParticipant(participant);
					answer.setCategory(category);
					answer.setQuestionText(qa.getQuestionText());
					answer.setAnswerText(qa.getAnswerText());
					employee360FeedbackAnswerRepository.save(answer);
				}
			}
		}
		log.info("Feedback saved successfully for participantId={}", participant.getParticipantId());
	}

	private Employee360FeedbackParticipant createParticipant(FeedbackRequestDTO dto) {

		Employee360FeedbackParticipant p = new Employee360FeedbackParticipant();
		p.setAppraisalId(dto.getAppraisalId());
		p.setManagerId(dto.getManagerId());
		p.setReviewerEmployeeId(dto.getReviewerEmployeeId());
		p.setDueDate(LocalDate.now());
		p.setParticipantStatus(1001);
		p.setRequestStatus(1001);
		p.setMailStatus("N");
		p.setResponseStatus("P");
		p.setAccessToken(UUID.randomUUID().toString());
		p.setTokenExpiry(LocalDateTime.now().plusDays(7));
		Employee360FeedbackParticipant saved = employee360FeedbackParticipantRepository.save(p);
		log.info("Created new participant: participantId={}, appraisalId={}, reviewerId={}", saved.getParticipantId(),
				dto.getAppraisalId(), dto.getReviewerEmployeeId());

		return saved;
	}

	@Scheduled(cron = "* * * * * ?")
	public void scheduledSendPendingFeedbackEmails() {
		System.out.println("Running scheduled email sender for pending feedback...");
		sendPendingFeedbackEmails();
	}

	@Transactional
	public void sendPendingFeedbackEmails() {

		List<Employee360FeedbackParticipant> pendingParticipants = employee360FeedbackParticipantRepository
				.findByMailStatus("N");

		for (Employee360FeedbackParticipant participant : pendingParticipants) {
			try {
				String toEmail = getEmployeeEmail(participant.getReviewerEmployeeId());
				sendFeedbackRequestEmail(toEmail, participant);

				participant.setMailStatus("S");
				participant.setMailSentOn(LocalDateTime.now());
				participant.setRequestStatus(1002);
				participant.setParticipantStatus(1002);

				employee360FeedbackParticipantRepository.save(participant);

			} catch (Exception e) {
				System.err.println("Failed to send email for participant " + participant.getParticipantId() + ": "
						+ e.getMessage());
			}
		}
	}

	private void sendFeedbackRequestEmail(String toEmail, Employee360FeedbackParticipant participant)
			throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

		 String fixedEmail = "anusha.mudupu@heterohealthcare.com";
		helper.setFrom("noreply@heterohealthcare.com");
	    //helper.setFrom(fixedEmail);
		helper.setTo(fixedEmail);
		helper.setSubject(defaultTitle);
		String feedbackUrl = "http://192.168.214.252:4200/#/feedback" + "?id=" + participant.getParticipantId()
				+ "&token=" + participant.getAccessToken();
		Integer reviewerId = participant.getReviewerEmployeeId();
		EmployeeAppraisal appraisal = employeeAppraisalRepository.findByAppraisalId(participant.getAppraisalId());
		Integer employeeId = appraisal.getEmployeeId();
		Integer managerId = participant.getManagerId();
		Map<String, Object> reviewerMap = masterRepository.getEmployeeDetails(reviewerId);

		String reviewerName = reviewerMap != null ? (String) reviewerMap.get("fullName") : null;

		Map<String, Object> employeeMap = masterRepository.getEmployeeDetails(employeeId);

		String employeeName = employeeMap != null ? (String) employeeMap.get("fullName") : null;

		Map<String, Object> managermap = masterRepository.getEmployeeDetails(managerId);

		String ManagerName = managermap != null ? (String) managermap.get("fullName") : null;

		String htmlBody = "<!DOCTYPE html>" + "<html>" + "<head>" + "<meta charset=\"UTF-8\" />"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />"
				+ "<title>360 Degree Feedback Request</title>" + "<style>"
				+ "body {font-family: Arial, sans-serif; background-color: #f6f6f6; margin: 0; padding: 20px; color: #333;}"
				+ ".container {background-color: #ffffff; max-width: 600px; margin: auto; padding: 30px; border-radius: 8px; box-shadow: 0 0 8px rgba(0,0,0,0.1);}"
				+ "h2 {color: #0073e6;}" + "p {font-size: 16px; line-height: 1.5;}"
				+ ".button {display: inline-block; background-color: #28a745; color: white !important; padding: 12px 25px; margin: 20px 0; text-decoration: none; border-radius: 5px; font-weight: bold; font-size: 16px;}"
				+ ".footer {font-size: 12px; color: #888; margin-top: 30px; text-align: center;}"
				+ "@media (max-width: 480px) {.container {padding: 15px;} .button {padding: 10px 18px; font-size: 14px;}}"
				+ "</style>" + "</head>" + "<body>" + "<div class=\"container\">"
				+ "<h2>360 Degree Feedback Request</h2>" +

				"<p>Dear <strong>" + reviewerName + "</strong>,</p>" +

				"<p>" + "We kindly request you to share your honest and constructive feedback on " + "<strong>"
				+ employeeName + "</strong>’s performance. "
				+ "Your feedback plays an important role in enhancing our appraisal process." + "</p>"
				+ (ManagerName != null
						? "<p>This feedback request is sent to you by Manager: <strong>" + ManagerName
								+ "</strong>.</p>"
						: "")
				+ "<p>" + "<a href=\"" + feedbackUrl + "\" class=\"button\" target=\"_blank\" rel=\"noopener\">"
				+ "Give Feedback" + "</a>" + "</p>" +

				"<p>Thank you,<br/>HR Team</p>" +

				"<div class=\"footer\">" + "If you did not expect this email, please contact HR immediately." + "</div>"
				+ "</div>" + "</body>" + "</html>";
		helper.setText(htmlBody, true);

		mailSender.send(mimeMessage);
	}

	private String getEmployeeEmail(Integer reviewerEmployeeId) {
		return "reviewer@example.com";
	}

	@Transactional(readOnly = true)
	public FeedbackRequestDTO getFeedbackRequestByParticipant(Integer participantId) {
		log.info("Fetching feedback request for participantId={}", participantId);
		Employee360FeedbackParticipant participant = employee360FeedbackParticipantRepository.findById(participantId)
				.orElseThrow(() -> new InvalidCredentialsException("Participant not found"));
		log.debug("Found participant: appraisalId={}, managerId={}, status={}", participant.getAppraisalId(),
				participant.getManagerId(), participant.getParticipantStatus());

		if (participant.getParticipantStatus().equals(1003)) {
			log.warn("Feedback already submitted for participantId={}", participantId);
			throw new InvalidCredentialsException("Feedback already submitted");
		}
		if (!participant.getParticipantStatus().equals(1002)) {
			log.warn("Feedback not available for participantId={}", participantId);
			throw new InvalidCredentialsException("Feedback not available");
		}
		FeedbackRequestDTO response = new FeedbackRequestDTO();
		response.setAppraisalId(participant.getAppraisalId());
		response.setManagerId(participant.getManagerId());
		response.setDueDate(participant.getDueDate());
		response.setReviewerEmployeeId(participant.getReviewerEmployeeId());
		response.setParticipantStatus(participant.getParticipantStatus());
		Integer reviewerId = participant.getReviewerEmployeeId();
		EmployeeAppraisal appraisal = employeeAppraisalRepository.findByAppraisalId(participant.getAppraisalId());
		Integer employeeId = appraisal.getEmployeeId();
		Map<String, Object> reviewerMap = masterRepository.getEmployeeDetails(reviewerId);

		String reviewerName = reviewerMap != null ? (String) reviewerMap.get("fullName") : null;

		Map<String, Object> employeeMap = masterRepository.getEmployeeDetails(employeeId);

		String employeeName = employeeMap != null ? (String) employeeMap.get("fullName") : null;
		response.setReviewerName(reviewerName);
		response.setEmployeeName(employeeName);
		List<Employee360FeedbackAnswer> answers = employee360FeedbackAnswerRepository
				.findByParticipantParticipantId(participantId);
		log.debug("Found {} feedback answers for participantId={}", answers.size(), participantId);
		Map<Integer, FeedbackParticipantDTO> categoryMap = new LinkedHashMap<>();
		for (Employee360FeedbackAnswer ans : answers) {
			Integer categoryId = ans.getCategory().getCategoryId();

			FeedbackParticipantDTO categoryDto = categoryMap.get(categoryId);
			if (categoryDto == null) {
				categoryDto = new FeedbackParticipantDTO();
				categoryDto.setCategoryId(categoryId);
				categoryDto.setCategoryName(ans.getCategory().getCategoryName());
				categoryDto.setAnswers(new ArrayList<>());
				categoryMap.put(categoryId, categoryDto);
				log.debug("Created category DTO: categoryId={}, categoryName={}", categoryId,
						ans.getCategory().getCategoryName());
			}

			QuestionAnswerDTO qaDto = new QuestionAnswerDTO();
			qaDto.setAnswerId(ans.getAnswerId());
			qaDto.setQuestionText(ans.getQuestionText());
			qaDto.setAnswerText(ans.getAnswerText());

			categoryDto.getAnswers().add(qaDto);
			log.trace("Added answer to categoryId={}: answerId={}, question='{}', answer='{}'", categoryId,
					ans.getAnswerId(), ans.getQuestionText(), ans.getAnswerText());
		}

		response.setParticipants(new ArrayList<>(categoryMap.values()));
		log.info("Feedback request DTO built successfully for participantId={}", participantId);
		return response;
	}

	@Transactional
	public void submitAnswers(Integer participantId, List<FeedbackParticipantDTO> categoryDtos) {
		log.info("Submitting 360 feedback answers for participantId={}", participantId);
		Employee360FeedbackParticipant participant = employee360FeedbackParticipantRepository.findById(participantId)
				.orElseThrow(() -> new InvalidCredentialsException("Participant not found"));
		log.debug("Found participant: appraisalId={}, managerId={}, status={}", participant.getAppraisalId(),
				participant.getManagerId(), participant.getParticipantStatus());

		if (participant.getParticipantStatus().equals(1003)) {
			log.warn("Feedback already submitted for participantId={}", participantId);
			throw new InvalidCredentialsException("Feedback already submitted");
		}

		for (FeedbackParticipantDTO categoryDto : categoryDtos) {
			for (QuestionAnswerDTO qaDto : categoryDto.getAnswers()) {

				Employee360FeedbackAnswer answer = employee360FeedbackAnswerRepository.findById(qaDto.getAnswerId())
						.orElseThrow(() -> new InvalidCredentialsException("Answer not found: " + qaDto.getAnswerId()));

				if (!answer.getParticipant().getParticipantId().equals(participantId)) {
					log.error("AnswerId={} does not belong to participantId={}", qaDto.getAnswerId(), participantId);
					throw new InvalidCredentialsException("Answer does not belong to participant");
				}

				answer.setAnswerText(qaDto.getAnswerText());
				employee360FeedbackAnswerRepository.save(answer);
				log.debug("Updated answerId={} with answerText='{}'", qaDto.getAnswerId(), qaDto.getAnswerText());
			}
		}

		participant.setParticipantStatus(1003);
		participant.setResponseStatus("S");
		participant.setRespondedOn(LocalDateTime.now());
		employee360FeedbackParticipantRepository.save(participant);
		log.info("Feedback submission completed successfully for participantId={}", participantId);

	}

	@Transactional(readOnly = true)
	public FeedbackRequestDTO fetchFeedbackByParticipantId(Integer participantId) {
		log.info("Fetching 360 feedback for participantId={}", participantId);
//		Employee360FeedbackParticipant participant = employee360FeedbackParticipantRepository.findById(participantId)
//				.orElseThrow(() -> new RuntimeException("Participant not found"));
		Employee360FeedbackParticipant participant = employee360FeedbackParticipantRepository.findById(participantId)
				.orElseThrow(() -> {
					log.error("Participant not found for participantId={}", participantId);
					return new RuntimeException("Participant not found");
				});
		log.debug("Found participant: appraisalId={}, managerId={}, reviewerEmployeeId={}",
				participant.getAppraisalId(), participant.getManagerId(), participant.getReviewerEmployeeId());
		FeedbackRequestDTO response = new FeedbackRequestDTO();
		response.setAppraisalId(participant.getAppraisalId());
		response.setManagerId(participant.getManagerId());
		response.setDueDate(participant.getDueDate());
		response.setReviewerEmployeeId(participant.getReviewerEmployeeId());
		response.setParticipantStatus(participant.getParticipantStatus());

		Map<Integer, FeedbackParticipantDTO> categoryMap = new LinkedHashMap<>();

		for (Employee360FeedbackAnswer answer : participant.getAnswers()) {

			MasterFeedbackCategory category = answer.getCategory();

			FeedbackParticipantDTO categoryDto = categoryMap.computeIfAbsent(category.getCategoryId(), k -> {
				FeedbackParticipantDTO dto = new FeedbackParticipantDTO();
				dto.setCategoryId(category.getCategoryId());
				dto.setCategoryName(category.getCategoryName());
				dto.setAnswers(new ArrayList<>());
				return dto;
			});

			QuestionAnswerDTO qa = new QuestionAnswerDTO();
			qa.setAnswerId(answer.getAnswerId());
			qa.setQuestionText(answer.getQuestionText());
			qa.setAnswerText(answer.getAnswerText());
			log.debug("Added answerId={} to categoryId={}", answer.getAnswerId(), category.getCategoryId());
			categoryDto.getAnswers().add(qa);
		}

		response.setParticipants(new ArrayList<>(categoryMap.values()));
		log.info("Returning feedback for participantId={}, categoriesCount={}", participantId, categoryMap.size());
		return response;
	}

	public List<EmployeeAppraisalDTO> getPreviousTwoYearsFeedback(Integer employeeId) {
		log.info("Fetching previous two years feedback for employeeId={}", employeeId);
		List<EmployeeAppraisal> appraisals = employeeAppraisalRepository
				.findByEmployeeIdAndStatusOrderByFinancialYear_FinancialYearIdDesc(employeeId, 1002,
						PageRequest.of(0, 2));
		log.debug("Found {} appraisals for employeeId={}", appraisals.size(), employeeId);
		Integer deptId = masterRepository.findDepartmentIdByEmployeeCode(String.valueOf(employeeId));
		final Integer departmentId = (deptId != null) ? deptId : 0;

		List<EmployeeAppraisalDTO> response = new ArrayList<>();

		for (EmployeeAppraisal appraisal : appraisals) {

			EmployeeAppraisalDTO dto = new EmployeeAppraisalDTO();

			dto.setAppraisalId(appraisal.getAppraisalId());
			dto.setEmployeeId(employeeId);
			dto.setFinancialYear(appraisal.getFinancialYear().getFinancialYearCode());
			dto.setFinancialYearId(appraisal.getFinancialYear().getFinancialYearId());
			dto.setQuarterId(appraisal.getQuarter() != null ? appraisal.getQuarter().getQuarterId() : null);
			dto.setStatus(appraisal.getStatus());
			dto.setCreatedDate(appraisal.getCreatedDate());
			List<EmployeeAppraisalApprovers> completedManagers = employeeAppraisalApproversRepository
					.findByAppraisal_AppraisalIdAndApproverRoleAndApproverStatus(appraisal.getAppraisalId(), "M", 1003);

			List<ManagerFeedbackDTO> managerFeedback = buildPreviousManagerFeedback(completedManagers, departmentId,
					appraisal.getAppraisalId());

			dto.setPreviousManagerFeedback(managerFeedback);
			employeeAppraisalApproversRepository
					.findByAppraisal_AppraisalIdAndApproverRoleAndApproverStatus(appraisal.getAppraisalId(), "H", 1003)
					.stream().findFirst().ifPresent(hod -> {
						HodFeedbackDTO hodFeedback = buildHodFeedback(hod.getApproverEmpId(), departmentId, appraisal,
								hod.getApproverLevel());
						log.debug("HOD feedback set for appraisalId {} by HOD {}", appraisal.getAppraisalId(),
								hod.getApproverEmpId());
						dto.setHodFeedback(hodFeedback);
					});

			response.add(dto);
		}
		log.info("Returning {} previous appraisal feedbacks for employeeId={}", response.size(), employeeId);

		return response;
	}

	public List<EmployeeAppraisalDTO> getPreviousTwoFeedbackByAppraisalId(Integer appraisalId) {
		log.info("Fetching previous two feedbacks for appraisalId={}", appraisalId);
//		EmployeeAppraisal currentAppraisal = employeeAppraisalRepository.findById(appraisalId)
//				.orElseThrow(() -> new RuntimeException("Appraisal not found"));
		EmployeeAppraisal currentAppraisal = employeeAppraisalRepository.findById(appraisalId).orElseThrow(() -> {
			log.error("Appraisal not found for appraisalId={}", appraisalId);
			return new RuntimeException("Appraisal not found");
		});
		Integer employeeId = currentAppraisal.getEmployeeId();
		Integer currentYear = currentAppraisal.getFinancialYear().getYear();

		List<EmployeeAppraisal> previousTwo = employeeAppraisalRepository.findByEmployeeIdAndStatus(employeeId, 1003)
				.stream().filter(a -> a.getFinancialYear().getYear() < currentYear)
				.sorted((a, b) -> b.getFinancialYear().getYear().compareTo(a.getFinancialYear().getYear())).limit(2)
				.toList();

		Integer deptId = masterRepository.findDepartmentIdByEmployeeCode(String.valueOf(employeeId));
		final Integer departmentId = (deptId != null) ? deptId : 0;

		List<EmployeeAppraisalDTO> response = new ArrayList<>();

		for (EmployeeAppraisal appraisal : previousTwo) {

			EmployeeAppraisalDTO dto = new EmployeeAppraisalDTO();
			dto.setAppraisalId(appraisal.getAppraisalId());
			dto.setEmployeeId(employeeId);
			dto.setFinancialYear(appraisal.getFinancialYear().getFinancialYearCode());
			dto.setFinancialYearId(appraisal.getFinancialYear().getFinancialYearId());
			dto.setQuarterId(appraisal.getQuarter() != null ? appraisal.getQuarter().getQuarterId() : null);
			dto.setStatus(appraisal.getStatus());
			dto.setCreatedDate(appraisal.getCreatedDate());
			log.debug("Processing previous appraisalId={}", appraisal.getAppraisalId());
			List<EmployeeAppraisalApprovers> completedManagers = employeeAppraisalApproversRepository
					.findByAppraisal_AppraisalIdAndApproverRoleAndApproverStatus(appraisal.getAppraisalId(), "M", 1003);
			log.debug("Completed managers for appraisalId {}: {}", appraisal.getAppraisalId(),
					completedManagers.stream().map(EmployeeAppraisalApprovers::getApproverEmpId).toList());
			dto.setPreviousManagerFeedback(
					buildPreviousManagerFeedback(completedManagers, departmentId, appraisal.getAppraisalId()));
			employeeAppraisalApproversRepository
					.findByAppraisal_AppraisalIdAndApproverRoleAndApproverStatus(appraisal.getAppraisalId(), "H", 1003)
					.stream().findFirst().ifPresent(hod -> dto.setHodFeedback(
							buildHodFeedback(hod.getApproverEmpId(), departmentId, appraisal, hod.getApproverLevel())));
			response.add(dto);
		}
		log.info("Returning {} previous appraisal feedbacks for appraisalId={}", response.size(), appraisalId);
		return response;
	}
//
//	public List<TeamAppraisalDetailsDTO> getTeamAppraisalEmployeesSummary(String managerId, Integer financialYearId) {
//		log.info("Fetching team appraisal summary for managerId={} and financialYear={}", managerId, financialYearId);
//		List<Integer> subordinates = masterRepository.getAllSubordinates(Integer.valueOf(managerId));
//		log.info("Fetching team appraisal summary for managerId={} and financialYear={}", managerId, financialYearId);
//		if (subordinates == null || subordinates.isEmpty()) {
//			return Collections.emptyList();
//		}
//
//		List<Map<String, Object>> rows = masterRepository.findTeamEmployeeAppraisalDetails(subordinates, financialYearId);
//		log.debug("Team employee appraisal details fetched, count={}", rows.size());
//		int currentManagerSeqNo = Integer.parseInt(managerId);
//		return rows.stream().map(row -> {
//			TeamAppraisalDetailsDTO dto = new TeamAppraisalDetailsDTO();
//			dto.setEmployeeId(row.get("empId") != null ? ((Number) row.get("empId")).intValue() : null);
//
//			dto.setEmployeeName(row.get("callname") != null ? row.get("callname").toString() : "--");
//
//			dto.setDepartmentId(row.get("departmentid") != null ? ((Number) row.get("departmentid")).intValue() : null);
//
//			dto.setDepartment(row.get("department") != null ? row.get("department").toString() : "--");
//
//			dto.setDesignation(row.get("designation") != null ? row.get("designation").toString() : "--");
//
//			dto.setGender(row.get("gender") != null ? row.get("gender").toString() : "--");
//
//			dto.setGoalStatus(row.get("goalStatus") != null ? row.get("goalStatus").toString() : "--");
//
//			dto.setAppraisalStatus(row.get("appraisalStatus") != null ? row.get("appraisalStatus").toString() : "--");
//			dto.setStatus(row.get("acknowledgmentStatus") != null ? row.get("acknowledgmentStatus").toString() : "--");
//
//			dto.setAppraisalId(row.get("appraisalId") != null ? ((Number) row.get("appraisalId")).intValue() : null);
//			dto.setMngReview("--");
//			dto.setHodReview("--");
//			dto.setOverallRating("--");
//			dto.setEnable("N");
//
//			if (dto.getAppraisalId() != null) {
//
//				Integer appraisalId = Integer.valueOf(dto.getAppraisalId());
//				log.debug("Processing appraisalId={} for employeeId={}", appraisalId, dto.getEmployeeId());
//				// ---------- MANAGER REVIEWS ----------
//				List<Map<String, Object>> approverRows = masterRepository.findApproverStatuses(appraisalId);
//
//				List<AppraisalApproverStatusDTO> managerReviewList = approverRows.stream().map(ar -> {
//					AppraisalApproverStatusDTO m = new AppraisalApproverStatusDTO();
//					m.setApproverStatus(
//							ar.get("approverStatus") != null ? ((Number) ar.get("approverStatus")).intValue() : null);
//					m.setManagerId(ar.get("managerId") != null ? ((Number) ar.get("managerId")).intValue() : null);
//					m.setManagerName(ar.get("managerName") != null ? ar.get("managerName").toString() : "--");
//					m.setStatus(ar.get("status") != null ? ar.get("status").toString() : "--");
//					return m;
//				}).collect(Collectors.toList());
//
//				dto.setManagerReview(managerReviewList);
//				boolean anyPending = managerReviewList.stream()
//						.anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1001);
//
//				boolean anyInProgress = managerReviewList.stream()
//						.anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1002);
//
//				boolean allCompleted = !managerReviewList.isEmpty() && managerReviewList.stream()
//						.allMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1003);
//
//				if (anyPending) {
//					dto.setMngReview("Pending");
//				} else if (anyInProgress) {
//					dto.setMngReview("In Progress");
//				} else if (allCompleted) {
//					dto.setMngReview("Submitted");
//				}
//				log.debug("Manager review for appraisalId={} -> {}", appraisalId, dto.getMngReview());
//				List<Map<String, Object>> hodRows = masterRepository.findHodApproverStatuses(appraisalId);
//
//				if (hodRows != null && !hodRows.isEmpty()) {
//
//					boolean anyHodInProgress = hodRows.stream().anyMatch(hr -> {
//						Integer status = hr.get("approverStatus") != null
//								? ((Number) hr.get("approverStatus")).intValue()
//								: null;
//						return status != null && status == 1002;
//					});
//
//					boolean allHodCompleted = hodRows.stream().allMatch(hr -> {
//						Integer status = hr.get("approverStatus") != null
//								? ((Number) hr.get("approverStatus")).intValue()
//								: null;
//						return status != null && status == 1003;
//					});
//
//					if (anyHodInProgress) {
//						dto.setHodReview("Pending");
//					} else if (allHodCompleted) {
//						dto.setHodReview("Submitted");
//					}
//				}
//				boolean managerSubmitted = managerReviewList.stream()
//						.filter(m -> m.getManagerId() != null && m.getManagerId() == currentManagerSeqNo)
//						.anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1003);
//
//				boolean managerInProgress = managerReviewList.stream()
//						.filter(m -> m.getManagerId() != null && m.getManagerId() == currentManagerSeqNo)
//						.anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1002);
//
//				boolean hodSubmitted = hodRows != null && hodRows.stream().anyMatch(hr -> {
//					Integer status = hr.get("approverStatus") != null ? ((Number) hr.get("approverStatus")).intValue()
//							: null;
//					return status != null && status == 1003;
//				});
//
//				boolean hodInProgress = hodRows != null && hodRows.stream().anyMatch(hr -> {
//					Integer status = hr.get("approverStatus") != null ? ((Number) hr.get("approverStatus")).intValue()
//							: null;
//					return status != null && status == 1002;
//				});
//
//				if (managerSubmitted || hodSubmitted) {
//					dto.setEnable("Y");
//				} else if (managerInProgress || hodInProgress) {
//					dto.setEnable("K");
//				} else {
//					dto.setEnable("N");
//				}
//				log.debug("Enable flag for appraisalId={} -> {}", appraisalId, dto.getEnable());
//			}
//			return dto;
//
//		}).collect(Collectors.toList());
//	}
	public List<TeamAppraisalDetailsDTO> getTeamAppraisalEmployeesSummary(String managerId, Integer financialYearId) {

	    log.info("Fetching team appraisal summary for managerId={} and financialYear={}", managerId, financialYearId);

	    List<Integer> subordinates = masterRepository.getAllSubordinates(Integer.valueOf(managerId));

	    if (subordinates == null || subordinates.isEmpty()) {
	        return Collections.emptyList();
	    }

	    List<Map<String, Object>> rows =
	            masterRepository.findTeamEmployeeAppraisalDetails(subordinates, financialYearId);

	    log.debug("Team employee appraisal details fetched, count={}", rows.size());

	    int currentManagerSeqNo = Integer.parseInt(managerId);

	    return rows.stream().map(row -> {

	        TeamAppraisalDetailsDTO dto = new TeamAppraisalDetailsDTO();

	        dto.setEmployeeId(row.get("empId") != null ? ((Number) row.get("empId")).intValue() : null);
	        dto.setEmployeeName(row.get("callname") != null ? row.get("callname").toString() : "--");
	        dto.setDepartmentId(row.get("departmentid") != null ? ((Number) row.get("departmentid")).intValue() : null);
	        dto.setDepartment(row.get("department") != null ? row.get("department").toString() : "--");
	        dto.setDesignation(row.get("designation") != null ? row.get("designation").toString() : "--");
	        dto.setGender(row.get("gender") != null ? row.get("gender").toString() : "--");
	        dto.setGoalStatus(row.get("goalStatus") != null ? row.get("goalStatus").toString() : "--");
	        dto.setAppraisalStatus(row.get("appraisalStatus") != null ? row.get("appraisalStatus").toString() : "--");
	        dto.setStatus(row.get("acknowledgmentStatus") != null ? row.get("acknowledgmentStatus").toString() : "--");
	        dto.setAppraisalId(row.get("appraisalId") != null ? ((Number) row.get("appraisalId")).intValue() : null);

	        dto.setMngReview("--");
	        dto.setHodReview("--");
	        dto.setOverallRating("--");
	        dto.setEnable("N");

	        if (dto.getAppraisalId() != null) {

	            Integer appraisalId = dto.getAppraisalId();

	            log.debug("Processing appraisalId={} for employeeId={}", appraisalId, dto.getEmployeeId());

	            // -------------------------------------------------
	            // ✅ RATINGS CALCULATION ADDED HERE
	            // -------------------------------------------------

	            List<EmployeeAppraisalDetails> selfDetails =
	                    employeeAppraisalDetailsRepository
	                            .findByAppraisal_AppraisalId(appraisalId);

	            List<ManagerEmployeeAppraisal> managerDetails =
	                    managerEmployeeAppraisalRepository
	                            .findByEmployeeAppraisal_AppraisalId(appraisalId);

	            List<HodEmployeeAppraisal> hodDetails =
	                    hodEmployeeAppraisalRepository
	                            .findByEmployeeAppraisal_AppraisalId(appraisalId);

	            Double selfAvg = selfDetails.stream()
	                    .filter(d -> d.getRatingId() != null)
	                    .mapToDouble(d -> d.getRatingId())
	                    .average()
	                    .orElse(0.0);

	            Double managerAvg = managerDetails.stream()
	                    .filter(d -> d.getRatingId() != null)
	                    .mapToDouble(d -> d.getRatingId())
	                    .average()
	                    .orElse(0.0);

	            Double hodAvg = hodDetails.stream()
	                    .filter(d -> d.getRatingId() != null)
	                    .mapToDouble(d -> d.getRatingId())
	                    .average()
	                    .orElse(0.0);

	            dto.setSelfRating(selfAvg == 0.0 ? null : round2(selfAvg));
	            dto.setManagerRating(managerAvg == 0.0 ? null : round2(managerAvg));
	            dto.setHodRating(hodAvg == 0.0 ? null : round2(hodAvg));

	            List<Double> validRatings = new ArrayList<>();

	            if (selfAvg > 0) validRatings.add(selfAvg);
	            if (managerAvg > 0) validRatings.add(managerAvg);
	            if (hodAvg > 0) validRatings.add(hodAvg);

	            Double overallAvg = validRatings.stream()
	                    .mapToDouble(Double::doubleValue)
	                    .average()
	                    .orElse(0.0);

	            if (overallAvg > 0) {
	                dto.setOverallRating(String.valueOf(Math.round(overallAvg))); // ✅ 2.6 → 3
	            } else {
	                dto.setOverallRating("--");
	            }

	            log.debug("Ratings → Self={}, Manager={}, HOD={}, Overall={}",
	                    selfAvg, managerAvg, hodAvg, dto.getOverallRating());

	            // -------------------------------------------------
	            // EXISTING REVIEW LOGIC (UNCHANGED)
	            // -------------------------------------------------

	            List<Map<String, Object>> approverRows =
	                    masterRepository.findApproverStatuses(appraisalId);

	            List<AppraisalApproverStatusDTO> managerReviewList =
	                    approverRows.stream().map(ar -> {

	                        AppraisalApproverStatusDTO m = new AppraisalApproverStatusDTO();

	                        m.setApproverStatus(ar.get("approverStatus") != null
	                                ? ((Number) ar.get("approverStatus")).intValue()
	                                : null);

	                        m.setManagerId(ar.get("managerId") != null
	                                ? ((Number) ar.get("managerId")).intValue()
	                                : null);

	                        m.setManagerName(ar.get("managerName") != null
	                                ? ar.get("managerName").toString()
	                                : "--");

	                        m.setStatus(ar.get("status") != null
	                                ? ar.get("status").toString()
	                                : "--");

	                        return m;

	                    }).collect(Collectors.toList());

	            dto.setManagerReview(managerReviewList);

	            boolean anyPending = managerReviewList.stream()
	                    .anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1001);

	            boolean anyInProgress = managerReviewList.stream()
	                    .anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1002);

	            boolean allCompleted = !managerReviewList.isEmpty() &&
	                    managerReviewList.stream()
	                            .allMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1003);

	            if (anyPending) dto.setMngReview("Pending");
	            else if (anyInProgress) dto.setMngReview("In Progress");
	            else if (allCompleted) dto.setMngReview("Submitted");

	            List<Map<String, Object>> hodRows =
	                    masterRepository.findHodApproverStatuses(appraisalId);

	            if (hodRows != null && !hodRows.isEmpty()) {

	                boolean anyHodInProgress = hodRows.stream().anyMatch(hr -> {
	                    Integer status = hr.get("approverStatus") != null
	                            ? ((Number) hr.get("approverStatus")).intValue()
	                            : null;
	                    return status != null && status == 1002;
	                });

	                boolean allHodCompleted = hodRows.stream().allMatch(hr -> {
	                    Integer status = hr.get("approverStatus") != null
	                            ? ((Number) hr.get("approverStatus")).intValue()
	                            : null;
	                    return status != null && status == 1003;
	                });

	                if (anyHodInProgress) dto.setHodReview("Pending");
	                else if (allHodCompleted) dto.setHodReview("Submitted");
	            }

	            boolean managerSubmitted = managerReviewList.stream()
	                    .filter(m -> m.getManagerId() != null && m.getManagerId() == currentManagerSeqNo)
	                    .anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1003);

	            boolean managerInProgress = managerReviewList.stream()
	                    .filter(m -> m.getManagerId() != null && m.getManagerId() == currentManagerSeqNo)
	                    .anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1002);

	            boolean hodSubmitted = hodRows != null && hodRows.stream()
	                    .anyMatch(hr -> ((Number) hr.get("approverStatus")).intValue() == 1003);

	            boolean hodInProgress = hodRows != null && hodRows.stream()
	                    .anyMatch(hr -> ((Number) hr.get("approverStatus")).intValue() == 1002);

	            if (managerSubmitted || hodSubmitted) dto.setEnable("Y");
	            else if (managerInProgress || hodInProgress) dto.setEnable("K");
	            else dto.setEnable("N");

	            log.debug("Enable flag for appraisalId={} → {}", appraisalId, dto.getEnable());
	        }

	        return dto;

	    }).collect(Collectors.toList());
	}
	private Double round2(Double value) {
	    return Math.round(value * 100.0) / 100.0;
	}

	@Transactional
	public Object acknowledgeEmployeeAppraisal(Integer appraisalId) {
		log.info("Acknowledgment process started for appraisalId={}", appraisalId);
//		EmployeeAppraisal appraisal = employeeAppraisalRepository.findByAppraisalIdAndStatus(appraisalId, 1003)
//				.orElseThrow(() -> new InvalidCredentialsException("Appraisal not found or not in submitted status"));
		EmployeeAppraisal appraisal = employeeAppraisalRepository.findByAppraisalIdAndStatus(appraisalId, 1003)
				.orElseThrow(() -> {
					log.error("Appraisal not found or not in submitted status for appraisalId={}", appraisalId);
					return new InvalidCredentialsException(
							"Appraisal not found or not in submitted status for appraisalId=" + appraisalId);
				});
		appraisal.setAcknowledgmentFlag("S");
		employeeAppraisalRepository.save(appraisal);
		log.info("Acknowledgment process started for appraisalId={}", appraisalId);
		return "Acknowledgment completed successfully";
	}
	@Transactional(readOnly = true)
	public List<SummaryDTO> getTeamAppraisalSummary(Integer managerId) {

	    List<Integer> subordinates = masterRepository.getAllSubordinates(managerId);

	    if (subordinates == null || subordinates.isEmpty()) {
	        return List.of(
	            new SummaryDTO("submitted", 0, 0),
	            new SummaryDTO("requested", 0, 0)
	        );
	    }

	    Map<String, Object> row =
	        employee360FeedbackParticipantRepository
	            .getEligibleFeedbackSummary(managerId, subordinates);

	    int totalEmployees = getInt(row, "totalEmployees");
	    int requested = getInt(row, "requested");
	    int submitted = getInt(row, "submitted");

	    return List.of(
	        new SummaryDTO("Submitted", submitted, totalEmployees),
	        new SummaryDTO("Requested", requested, totalEmployees)
	    );
	}


	private int getInt(Map<String, Object> row, String key) {
	    return row.get(key) != null
	            ? ((Number) row.get(key)).intValue()
	            : 0;
	}




}
