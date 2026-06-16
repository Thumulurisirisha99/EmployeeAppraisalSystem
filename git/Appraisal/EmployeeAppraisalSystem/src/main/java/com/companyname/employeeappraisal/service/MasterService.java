package com.companyname.employeeappraisal.service;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.companyname.employeeappraisal.dto.AppModuleDTO;
import com.companyname.employeeappraisal.dto.PerformanceGoalDTO;
import com.companyname.employeeappraisal.dto.ResponseDTO;
import com.companyname.employeeappraisal.exception.InvalidCredentialsException;
import com.companyname.employeeappraisal.model.AppModule;
import com.companyname.employeeappraisal.model.AppraisalEligibleEmployee;
import com.companyname.employeeappraisal.model.EmployeeRole;
import com.companyname.employeeappraisal.model.GoalCategory;
import com.companyname.employeeappraisal.model.MasterFeedbackCategory;
import com.companyname.employeeappraisal.model.MasterFinancialYear;
import com.companyname.employeeappraisal.model.MasterQuarter;
import com.companyname.employeeappraisal.model.MasterWorkType;
import com.companyname.employeeappraisal.model.PerformanceGoal;
import com.companyname.employeeappraisal.repository.AppModuleRepository;
import com.companyname.employeeappraisal.repository.AppraisalEligibleEmployeeRepository;
import com.companyname.employeeappraisal.repository.EmployeeAppraisalRepository;
import com.companyname.employeeappraisal.repository.EmployeeLoginDTO;
import com.companyname.employeeappraisal.repository.EmployeeRoleRepository;
import com.companyname.employeeappraisal.repository.GoalCategoryRepository;
import com.companyname.employeeappraisal.repository.LoginRightRepository;
import com.companyname.employeeappraisal.repository.MasterFeedbackCategoryRepository;
import com.companyname.employeeappraisal.repository.MasterFinancialYearRepository;
import com.companyname.employeeappraisal.repository.MasterQuarterRepository;
import com.companyname.employeeappraisal.repository.MasterRepository;
import com.companyname.employeeappraisal.repository.MasterWorkTypeRepository;
import com.companyname.employeeappraisal.repository.PerformanceGoalRepository;
import com.companyname.employeeappraisal.util.JwtUtil;

@Service
public class MasterService {
	private MasterRepository masterRepository;
	private EmployeeRoleRepository employeeRoleRepository;
	private LoginRightRepository loginRightRepository;
	private AppModuleRepository appModuleRepository;
	private final JwtUtil jwtUtil;
	private GoalCategoryRepository goalCategoryRepository;
	private PerformanceGoalRepository performanceGoalRepository;
	private MasterQuarterRepository masterQuarterRepository;
	private MasterFinancialYearRepository masterFinancialYearRepository;
	private AppraisalEligibleEmployeeRepository appraisalEligibleEmployeeRepository;
	private MasterWorkTypeRepository masterWorkTypeRepository;
	private MasterFeedbackCategoryRepository masterFeedbackCategoryRepository;
	private EmployeeAppraisalRepository employeeAppraisalRepository;

	public MasterService(MasterRepository masterRepository, EmployeeRoleRepository employeeRoleRepository,
			LoginRightRepository loginRightRepository, AppModuleRepository appModuleRepository, JwtUtil jwtUtil,
			GoalCategoryRepository goalCategoryRepository, PerformanceGoalRepository performanceGoalRepository,
			MasterQuarterRepository masterQuarterRepository,
			MasterFinancialYearRepository masterFinancialYearRepository,
			AppraisalEligibleEmployeeRepository appraisalEligibleEmployeeRepository,
			MasterWorkTypeRepository masterWorkTypeRepository,
			MasterFeedbackCategoryRepository masterFeedbackCategoryRepository,
			EmployeeAppraisalRepository employeeAppraisalRepository) {
		this.masterRepository = masterRepository;
		this.employeeRoleRepository = employeeRoleRepository;
		this.loginRightRepository = loginRightRepository;
		this.appModuleRepository = appModuleRepository;
		this.jwtUtil = jwtUtil;
		this.goalCategoryRepository = goalCategoryRepository;
		this.performanceGoalRepository = performanceGoalRepository;
		this.masterQuarterRepository = masterQuarterRepository;
		this.masterFinancialYearRepository = masterFinancialYearRepository;
		this.appraisalEligibleEmployeeRepository = appraisalEligibleEmployeeRepository;
		this.masterWorkTypeRepository = masterWorkTypeRepository;
		this.masterFeedbackCategoryRepository = masterFeedbackCategoryRepository;
		this.employeeAppraisalRepository = employeeAppraisalRepository;

	}

	private static final Logger log = LoggerFactory.getLogger(MasterService.class);

	/**
	 * Login API
	 *
	 * Flow: 1. Validate employee code and password 2. Fetch active roles assigned
	 * to the employee - If no role is found, default role is EMP (roleId = 1) 3.
	 * Fetch modules mapped to the employee role(s) 4. Enable / disable modules
	 * based on role and appraisal eligibility 5. Identify current financial year
	 * and active quarter 6. Generate JWT token with employeeId and role 7. Prepare
	 * and return login response with user details and module access
	 */

	@Transactional(rollbackFor = Throwable.class)
	public ResponseDTO login(String employeeCode, String password) {
		log.info("Login request received for employeeCode: {}", employeeCode);
		EmployeeLoginDTO loginDTO = masterRepository.findByEmployeeCodeAndPassword(employeeCode, password);

		if (loginDTO == null) {
			log.warn("Invalid login attempt for employeeCode: {}", employeeCode);
			throw new InvalidCredentialsException("Invalid employeeId or password");
		}

		Integer employeeId = loginDTO.getEmployeeCode();
		log.debug("Employee ID fetched: {}", employeeId);
		if (employeeId == null) {
			log.error("Employee ID is null after successful login lookup for employeeCode: {}", employeeCode);
			throw new InvalidCredentialsException("Employee ID not found for login user");
		}
		log.info("Fetching roles for employeeId: {}", employeeId);
		List<EmployeeRole> roles = employeeRoleRepository.findByEmployeeIdAndStatus(employeeId, 1001);

		boolean isEMP = false;
		boolean isMGR = false;
		boolean isHR = false;
		boolean isHod = false;
		List<Integer> roleIds;

		if (roles == null || roles.isEmpty()) {
			log.info("No roles found. Assigning default EMP role.");
			isEMP = true;
			roleIds = List.of(1);
		} else {
			roleIds = roles.stream().map(r -> r.getRole().getRoleId()).distinct().toList();
			log.debug("Role IDs found: {}", roleIds);
			isMGR = roleIds.contains(2);
			isHod = roleIds.contains(4);
			isHR = roleIds.contains(3);
			isEMP = !isMGR && !isHR;
		}
		log.info("Fetching allowed modules for roles: {}", roleIds);
		List<Integer> moduleIds = loginRightRepository.findActiveModuleIdsByRoleIds(roleIds);
		List<AppModule> allowedModules = appModuleRepository.findByModuleIdInAndStatus(moduleIds, 1001);

		Optional<AppraisalEligibleEmployee> eligibleOpt = appraisalEligibleEmployeeRepository
				.findTopByEmployeeIdAndStatusInOrderByEligibleIdDesc(employeeId, Arrays.asList(1001, 1003));

		boolean isEligible = eligibleOpt.isPresent();
		log.info("Checking active financial year and quarter");
		MasterFinancialYear activeFY = masterFinancialYearRepository.findFinancialYear(1001)
				.orElseThrow(() -> new InvalidCredentialsException("Active Financial Year not found"));

		MasterQuarter activeQuarter = masterQuarterRepository.findCurrentQuarter();

		if (activeQuarter == null) {
			log.error("Active Quarter not found");
			throw new InvalidCredentialsException("Active Quarter not found");
		}
		Integer activeFinancialYearId = activeFY.getFinancialYearId();
		String activeFinancialYearCode = activeFY.getFinancialYearCode();
		LocalDate activeFromdate = activeFY.getStartDate();
		LocalDate activeTodate = activeFY.getEndDate();
		Integer activeYear = activeFY.getYear();
		Integer activeQuarterId = activeQuarter.getQuarterId();
		String activeQuarterCode = activeQuarter.getQuarterCode();
		Integer financialYearId;
		String financialYearCode;
		Integer year;
		Integer quarterId;
		String quarterCode;
		if (isEligible) {
			AppraisalEligibleEmployee eligible = eligibleOpt.get();
			MasterFinancialYear fy = eligible.getFinancialYear();
			MasterQuarter q = eligible.getQuarter();
			financialYearId = fy.getFinancialYearId();
			financialYearCode = fy.getFinancialYearCode();
			year = fy.getYear();
			quarterId = q.getQuarterId();
			quarterCode = q.getQuarterCode();

		} else {
			financialYearId = activeFinancialYearId;
			financialYearCode = activeFinancialYearCode;
			year = activeYear;

			quarterId = activeQuarterId;
			quarterCode = activeQuarterCode;
		}
//		Map<Integer, String> enabledMap = new HashMap<>();
//
//		for (AppModule m : allowedModules) {
//			enabledMap.put(m.getModuleId(), "Y");
//		}
		Map<Integer, String> enabledMap = allowedModules.stream()
				.collect(Collectors.toMap(AppModule::getModuleId, m -> "Y"));

		List<Integer> statuses = Arrays.asList(1001, 1002, 1003);
		Optional<AppraisalEligibleEmployee> appraisalOpt = appraisalEligibleEmployeeRepository
				.findByEmployeeIdAndStatusInAndFinancialYear_FinancialYearIdAndQuarter_QuarterId(employeeId, statuses,
						financialYearId, quarterId);

		log.info("Preparing response for employeeId: {}", employeeId);

		ResponseDTO response = new ResponseDTO();
		response.setEmployeeId(employeeId);
		response.setFullName(loginDTO.getFullName());
		response.setDepartment(loginDTO.getDepartment());
		response.setDesignation(loginDTO.getDesignation());
		response.setDepartmentId(loginDTO.getDepartmentId() != null ? loginDTO.getDepartmentId() : 0);
		response.setFinancialYearId(financialYearId);
		response.setFinancialYear(financialYearCode);
		response.setYear(year);
		response.setQuarterId(quarterId);
		response.setQuarterName(quarterCode);
		response.setActiveFinancialYear(activeFinancialYearCode);
		response.setActiveFinancialYearId(activeFinancialYearId);
		response.setActiveQuarterId(activeQuarterId);
		response.setActiveQuarterName(activeQuarterCode);
		response.setActiveyear(activeYear);
		response.setActiveFromDate(activeFromdate);
		response.setActiveToDate(activeTodate);
		response.setRole(isHR ? "HR" : isMGR ? "MGR" : isHod ? "HOD" : "EMP");
		log.info("Role assigned: {}", response.getRole());
		response.setEligibleId(appraisalOpt.map(AppraisalEligibleEmployee::getEligibleId).orElse(0));

		String token = jwtUtil.generateToken(employeeId, response.getRole());
		response.setToken(token);
		log.info("JWT token generated successfully for employeeId: {}", employeeId);
		List<AppModuleDTO> moduleDTOs = allowedModules.stream().map(m -> new AppModuleDTO(m.getModuleId(),
				m.getModuleName(), enabledMap.getOrDefault(m.getModuleId(), "N"))).toList();
		response.setModules(moduleDTOs);
		log.info("Login successful for employeeId: {}", employeeId);
		return response;
	}

	@Cacheable(value = "goalCategory")
	public List<GoalCategory> getGoalCategory() {
		log.info("Fetching active goal categories from database");
		List<GoalCategory> categories = goalCategoryRepository.findByStatus(1001);
		log.debug("Number of goal categories fetched: {}", categories.size());
		return categories;
	}

	// @Cacheable(value = "performanceGoal", key = "#goalId")
	public PerformanceGoalDTO getGoalById(int goalId) {
		log.info("Fetching Performance Goal for goalId={}", goalId);
		PerformanceGoal entity = performanceGoalRepository.findByGoalId(goalId);
		if (entity == null) {
			log.warn("Performance Goal not found for goalId={}", goalId);
			throw new InvalidCredentialsException("Goal not found");
		}
		log.debug("Performance Goal found: id={}, title={}", entity.getGoalId(), entity.getTitle());
		PerformanceGoalDTO dto = new PerformanceGoalDTO();
		dto.setGoalId(entity.getGoalId());
		dto.setTitle(entity.getTitle());
		dto.setDescription(entity.getDescription());
		dto.setGoalCategoryId(entity.getGoalCategory().getGoalCategoryId());
		dto.setGoalCategoryName(entity.getGoalCategory().getCategoryName());
		dto.setTimeline(entity.getTimeline());
		dto.setStatus(entity.getStatus());
		dto.setFinancialYear(entity.getFinancialYear().getFinancialYearCode());
		dto.setFinancialYearId(entity.getFinancialYear().getFinancialYearId());
		dto.setQuarterId(entity.getQuarter().getQuarterId());
		dto.setQuarterName(entity.getQuarter() != null ? entity.getQuarter().getQuarterCode() : null);
		dto.setStatusName(entity.getMasterStatus().getStatusName());
		dto.setEmployeeId(entity.getEmployeeId());
		dto.setFeedBackRating(entity.getFeedbackRating() != null ? entity.getFeedbackRating() : 0);
		dto.setGoalCompletionPercentage(
				entity.getGoalCompletionPercentage() != null ? entity.getGoalCompletionPercentage() : BigDecimal.ZERO);
		dto.setFeedBackComment(entity.getFeedbackComment());
		dto.setImplementedGoal(entity.getImplementedGoal());
		dto.setFeedBackDate(entity.getFeedbackDate() != null ? entity.getFeedbackDate().toString() : null);
		dto.setApprovedManagerId(entity.getApprovedManagerId());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		if (entity.getApprovalDate() != null) {
			dto.setApprovalDate(entity.getApprovalDate().format(formatter));
		} else {
			dto.setApprovalDate(null);
		}
		dto.setManagerComment(entity.getManagerComment());
		dto.setDocument(entity.getDocumentPath());
		dto.setDocumentName(entity.getDocumentName());
		dto.setCreatedBy(entity.getCreatedBy());
		dto.setLupdate(entity.getLupdate());
		try {
			if (entity.getDocumentPath() != null) {
				Path filePath = Path.of(entity.getDocumentPath());
				log.debug("Reading document from path={}", filePath + "---", goalId);
				if (Files.exists(filePath)) {
					byte[] fileBytes = Files.readAllBytes(filePath);
					dto.setDocumentBytes(fileBytes);
					log.info("Document loaded successfully for goalId={}", goalId);
				} else {
					log.warn("Document not found at path={} for goalId={}", filePath, goalId);
					dto.setDocumentBytes(null);
				}
			}
		} catch (Exception e) {
			log.error("Error while reading document for goalId={}", goalId, e);
			dto.setDocumentBytes(null);
		}
		log.info("Returning PerformanceGoalDTO for goalId={}", goalId);
		return dto;
	}

	@Cacheable(value = "worktype")
	public List<MasterWorkType> getAllWorkTypes() {
		log.info("Fetching all work types (cacheable: worktype)");
		List<MasterWorkType> workTypes = masterWorkTypeRepository.findAll();
		log.debug("Total work types fetched: {}", workTypes.size());
		return workTypes;
	}

	@Cacheable(value = "categories")
	public List<MasterFeedbackCategory> getActiveCategories() {
		log.info("Fetching active feedback categories (status = 1001)");
		List<MasterFeedbackCategory> categories = masterFeedbackCategoryRepository.findByStatus(1001);
		log.debug("Active feedback categories count: {}", categories.size());
		return categories;
	}

	public List<MasterFinancialYear> getFinancialYears() {
		log.info("Fetching active financial years (status = 1001)");
		List<MasterFinancialYear> financialYears = masterFinancialYearRepository.findAll();
		log.debug("Number of financial years fetched: {}", financialYears.size());
		return financialYears;
	}

	public List<MasterQuarter> getQuarters() {
		log.info("Fetching all quarters");
		List<MasterQuarter> quarters = masterQuarterRepository.findAll();
		log.debug("Total quarters fetched: {}", quarters.size());
		return quarters;
	}

}