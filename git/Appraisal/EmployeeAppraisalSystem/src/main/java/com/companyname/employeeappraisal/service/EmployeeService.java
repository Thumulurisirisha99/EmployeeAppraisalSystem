package com.companyname.employeeappraisal.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.companyname.employeeappraisal.dto.ApiResponse;
import com.companyname.employeeappraisal.dto.AppModuleDTO;
import com.companyname.employeeappraisal.dto.EmployeeAppraisalDTO;
import com.companyname.employeeappraisal.dto.EmployeeAppraisalDetailDTO;
import com.companyname.employeeappraisal.dto.EmployeeAppraisalWorkDTO;
import com.companyname.employeeappraisal.dto.EmployeeAppraisalWorkResponseDTO;
import com.companyname.employeeappraisal.dto.EmployeePerformanceGoalsDTO;
import com.companyname.employeeappraisal.dto.FileUploadInfo;
import com.companyname.employeeappraisal.dto.ModuleQuestionAnswerCountDTO;
import com.companyname.employeeappraisal.dto.PerformanceGoalDTO;
import com.companyname.employeeappraisal.exception.InvalidCredentialsException;
import com.companyname.employeeappraisal.model.AppraisalDeptModule;
import com.companyname.employeeappraisal.model.AppraisalDeptQuestion;
import com.companyname.employeeappraisal.model.AppraisalEligibleEmployee;
import com.companyname.employeeappraisal.model.EmployeeAppraisal;
import com.companyname.employeeappraisal.model.EmployeeAppraisalApprovers;
import com.companyname.employeeappraisal.model.EmployeeAppraisalDetails;
import com.companyname.employeeappraisal.model.EmployeeAppraisalWork;
import com.companyname.employeeappraisal.model.GoalCategory;
import com.companyname.employeeappraisal.model.MasterFinancialYear;
import com.companyname.employeeappraisal.model.MasterQuarter;
import com.companyname.employeeappraisal.model.MasterWorkType;
import com.companyname.employeeappraisal.model.PerformanceGoal;
import com.companyname.employeeappraisal.repository.AppraisalDeptModuleRepository;
import com.companyname.employeeappraisal.repository.AppraisalDeptQuestionRepository;
import com.companyname.employeeappraisal.repository.AppraisalEligibleEmployeeRepository;
import com.companyname.employeeappraisal.repository.AppraisalModuleRepository;
import com.companyname.employeeappraisal.repository.EmployeeAppraisalApproversRepository;
import com.companyname.employeeappraisal.repository.EmployeeAppraisalDetailsRepository;
import com.companyname.employeeappraisal.repository.EmployeeAppraisalRepository;
import com.companyname.employeeappraisal.repository.EmployeeAppraisalWorkRepository;
import com.companyname.employeeappraisal.repository.GoalCategoryRepository;
import com.companyname.employeeappraisal.repository.MasterFinancialYearRepository;
import com.companyname.employeeappraisal.repository.MasterQuarterRepository;
import com.companyname.employeeappraisal.repository.MasterRepository;
import com.companyname.employeeappraisal.repository.MasterWorkTypeRepository;
import com.companyname.employeeappraisal.repository.PerformanceGoalRepository;

@Service
public class EmployeeService {
	private PerformanceGoalRepository performanceGoalRepository;
	private GoalCategoryRepository goalCategoryRepository;
	private MasterRepository masterRepository;
	private AppraisalDeptModuleRepository appraisalDeptModuleRepository;
	private AppraisalDeptQuestionRepository appraisalDeptQuestionRepository;
	private EmployeeAppraisalRepository employeeAppraisalRepository;
	private EmployeeAppraisalDetailsRepository employeeAppraisalDetailsRepository;
	private EmployeeAppraisalApproversRepository employeeAppraisalApproversRepository;
	private MasterQuarterRepository masterQuarterRepository;
	private MasterFinancialYearRepository masterFinancialYearRepository;
	private EmployeeAppraisalWorkRepository employeeAppraisalWorkRepository;
	private MasterWorkTypeRepository masterWorkTypeRepository;
	private AppraisalEligibleEmployeeRepository appraisalEligibleEmployeeRepository;

	public EmployeeService(PerformanceGoalRepository performanceGoalRepository,
			GoalCategoryRepository goalCategoryRepository, MasterRepository masterRepository,
			AppraisalModuleRepository appraisalModuleRepository,
			AppraisalDeptModuleRepository appraisalDeptModuleRepository,
			AppraisalDeptQuestionRepository appraisalDeptQuestionRepository,
			EmployeeAppraisalRepository employeeAppraisalRepository,
			EmployeeAppraisalDetailsRepository employeeAppraisalDetailsRepository,
			EmployeeAppraisalApproversRepository employeeAppraisalApproversRepository,
			MasterQuarterRepository masterQuarterRepository,
			MasterFinancialYearRepository masterFinancialYearRepository,
			EmployeeAppraisalWorkRepository employeeAppraisalWorkRepository,
			MasterWorkTypeRepository masterWorkTypeRepository,
			AppraisalEligibleEmployeeRepository appraisalEligibleEmployeeRepository) {
		this.performanceGoalRepository = performanceGoalRepository;
		this.goalCategoryRepository = goalCategoryRepository;
		this.masterRepository = masterRepository;
		this.appraisalDeptModuleRepository = appraisalDeptModuleRepository;
		this.appraisalDeptQuestionRepository = appraisalDeptQuestionRepository;
		this.employeeAppraisalRepository = employeeAppraisalRepository;
		this.employeeAppraisalDetailsRepository = employeeAppraisalDetailsRepository;
		this.employeeAppraisalApproversRepository = employeeAppraisalApproversRepository;
		this.masterQuarterRepository = masterQuarterRepository;
		this.masterFinancialYearRepository = masterFinancialYearRepository;
		this.employeeAppraisalWorkRepository = employeeAppraisalWorkRepository;
		this.masterWorkTypeRepository = masterWorkTypeRepository;
		this.appraisalEligibleEmployeeRepository = appraisalEligibleEmployeeRepository;
	}

	private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
	@Value("${appraisal.goal.document.path}")
	private String documentupload;
	@Value("${hr.employee.id}")
	private Integer hrEmployeeId;
	@Value("${appraisal.works.path}")
	private String uploadWorks;

	/**
	 * API to save employee performance goals.
	 *
	 * Based on request type: - "submit" → goal status will be set to SUBMITTED
	 * (1002) - Otherwise → goal status will be saved as DRAFT (1001)
	 **/
//	@Caching(evict = {
//		    @CacheEvict(value = "employeeGoals", key = "#performanceGoalDTO.employeeId"),
//		    @CacheEvict(value = "performanceGoal", allEntries = true)
//		})
	@Transactional(rollbackFor = Throwable.class)
	public PerformanceGoalDTO saveGoal(PerformanceGoalDTO performanceGoalDTO) {
		log.info("Saving goal for employeeId={}, type={}", performanceGoalDTO.getEmployeeId(),
				performanceGoalDTO.getType());
		PerformanceGoal goal = new PerformanceGoal();
		goal.setEmployeeId(performanceGoalDTO.getEmployeeId());
		goal.setTitle(performanceGoalDTO.getTitle());
		goal.setDescription(performanceGoalDTO.getDescription());
		goal.setTimeline(performanceGoalDTO.getTimeline());
		goal.setCreatedDate(LocalDateTime.now());
		goal.setCreatedBy(performanceGoalDTO.getCreatedBy());
		if ("submit".equalsIgnoreCase(performanceGoalDTO.getType())) {
			goal.setStatus(1002);
			log.debug("Goal marked as SUBMITTED" + performanceGoalDTO.getEmployeeId());
		} else {
			goal.setStatus(1001);
			log.debug("Goal marked as DRAFT" + performanceGoalDTO.getEmployeeId());
		}
		MasterFinancialYear financialYear = masterFinancialYearRepository
				.findById(performanceGoalDTO.getFinancialYearId())
				.orElseThrow(() -> new InvalidCredentialsException("Financial Year not found"));

		goal.setFinancialYear(financialYear);

		MasterQuarter quarter = masterQuarterRepository.findById(performanceGoalDTO.getQuarterId())
				.orElseThrow(() -> new InvalidCredentialsException("Quarter not found"));
		goal.setQuarter(quarter);
		GoalCategory category = goalCategoryRepository.findById(performanceGoalDTO.getGoalCategoryId())
				.orElseThrow(() -> new InvalidCredentialsException("Goal Category not found"));
		goal.setGoalCategory(category);
		PerformanceGoal savedGoal = performanceGoalRepository.save(goal);
		PerformanceGoalDTO savedDto = new PerformanceGoalDTO();
		savedDto.setGoalId(savedGoal.getGoalId());
		savedDto.setEmployeeId(savedGoal.getEmployeeId());
		savedDto.setTitle(savedGoal.getTitle());
		savedDto.setDescription(savedGoal.getDescription());
		savedDto.setTimeline(savedGoal.getTimeline());
		savedDto.setStatus(savedGoal.getStatus());
		savedDto.setCreatedBy(savedGoal.getCreatedBy());
		savedDto.setGoalCategoryId(savedGoal.getGoalCategory().getGoalCategoryId());
		savedDto.setFinancialYearId(savedGoal.getFinancialYear().getFinancialYearId());
		savedDto.setFinancialYear(savedGoal.getFinancialYear().getFinancialYearCode());
		savedDto.setQuarterName(savedGoal.getQuarter().getQuarterCode());
		savedDto.setQuarterId(savedGoal.getQuarter().getQuarterId());
		log.info("Goal saved successfully. goalId={}", savedGoal.getGoalId());
		return savedDto;
	}

	/**
	 * Allows employee to submit self-feedback on an approved goal (status 1003).
	 * Employee can upload feedback rating, comments, implemented goal info, and
	 * optionally upload related document files. Feedback submission is only
	 * accepted if goal is in approved state.
	 */
	@Transactional(rollbackFor = Throwable.class)
	public String saveSelfFeedback(PerformanceGoalDTO dto, MultipartFile[] files) throws Exception {
		log.info("Saving self-feedback for goalId={}, employeeId={}", dto.getGoalId(), dto.getEmployeeId());
		PerformanceGoal goal = performanceGoalRepository.findById(dto.getGoalId())
				.orElseThrow(() -> new InvalidCredentialsException("Goal not found"));

		if (goal.getStatus() != 1003) {
			log.warn("Feedback rejected. goalId={} status={}", goal.getGoalId(), goal.getStatus());
			throw new InvalidCredentialsException("Feedback can only be submitted when goal status is 1003");
		}

		if (files != null && files.length > 0) {
			log.info("Uploading {} feedback files for goalId={}", files.length, goal.getGoalId());
			File uploadDir = new File(documentupload);
			if (!uploadDir.exists() && !uploadDir.mkdirs()) {
				throw new InvalidCredentialsException("Could not create upload directory");
			}

			File goalFolder = new File(uploadDir, "goal_" + goal.getGoalId());
			if (!goalFolder.exists() && !goalFolder.mkdirs()) {
				throw new InvalidCredentialsException("Could not create goal folder");
			}
			List<FileUploadInfo> uploadedFiles = Arrays.stream(files).filter(f -> f != null && !f.isEmpty())
					.map(file -> {

						try {
							String originalFileName = file.getOriginalFilename();

							String fileName = "goal_" + goal.getGoalId() + "_" + originalFileName;

							File destFile = new File(goalFolder, fileName);
							file.transferTo(destFile);

							return new FileUploadInfo(fileName, destFile.getAbsolutePath());

						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}).collect(Collectors.toList());
			if (!uploadedFiles.isEmpty()) {
				goal.setDocumentName(
						uploadedFiles.stream().map(FileUploadInfo::getFileName).collect(Collectors.joining(",")));

				goal.setDocumentPath(
						uploadedFiles.stream().map(FileUploadInfo::getFilePath).collect(Collectors.joining(",")));
			}
		}
		goal.setFeedbackRating(dto.getFeedBackRating());
		goal.setGoalCompletionPercentage(dto.getGoalCompletionPercentage());
		goal.setFeedbackComment(dto.getFeedBackComment());
		goal.setImplementedGoal(dto.getImplementedGoal());
		goal.setFeedbackDate(LocalDateTime.now());
		goal.setUpdateBy(dto.getUpdateBy());
		goal.setStatus(1005);
		performanceGoalRepository.save(goal);
		log.info("Feedback saved successfully for goalId={}", goal.getGoalId());
		return "Feedback saved successfully";
	}

//	@Caching(evict = {
//		    @CacheEvict(value = "performanceGoal", key = "#goalId"),
//		    @CacheEvict(value = "employeeGoals", key = "#performanceGoalDTO.employeeId")
//		})
	@Transactional(rollbackFor = Throwable.class)
	public PerformanceGoalDTO updateGoal(Integer goalId, PerformanceGoalDTO performanceGoalDTO) {
		log.info("Updating goalId={} by user={}", goalId, performanceGoalDTO.getUpdateBy());
		PerformanceGoal existingGoal = performanceGoalRepository.findById(goalId)
				.orElseThrow(() -> new InvalidCredentialsException("Goal not found with id: " + goalId));
		if (existingGoal.getStatus() != 1001) {
			log.warn("Goal update blocked. goalId={} status={}", goalId, existingGoal.getStatus());
			throw new InvalidCredentialsException("Goal cannot be edited because its status is not editable.");
		}

		existingGoal.setTitle(performanceGoalDTO.getTitle());
		existingGoal.setDescription(performanceGoalDTO.getDescription());
		existingGoal.setTimeline(performanceGoalDTO.getTimeline());
		existingGoal.setUpdateBy(performanceGoalDTO.getUpdateBy());

		if ("submit".equalsIgnoreCase(performanceGoalDTO.getType())) {
			existingGoal.setStatus(1002);
		} else {
			existingGoal.setStatus(1001);
		}
		MasterQuarter quarter = masterQuarterRepository.findById(performanceGoalDTO.getQuarterId())
				.orElseThrow(() -> new RuntimeException("Quarter not found"));
		existingGoal.setQuarter(quarter);
		GoalCategory category = goalCategoryRepository.findById(performanceGoalDTO.getGoalCategoryId())
				.orElseThrow(() -> new InvalidCredentialsException("Goal Category not found"));
		existingGoal.setGoalCategory(category);
		MasterFinancialYear financialYear = masterFinancialYearRepository
				.findById(performanceGoalDTO.getFinancialYearId())
				.orElseThrow(() -> new InvalidCredentialsException("Financial Year not found"));

		existingGoal.setFinancialYear(financialYear);
		PerformanceGoal updatedGoal = performanceGoalRepository.save(existingGoal);
		PerformanceGoalDTO updatedDto = new PerformanceGoalDTO();
		updatedDto.setGoalId(updatedGoal.getGoalId());
		updatedDto.setEmployeeId(updatedGoal.getEmployeeId());
		updatedDto.setTitle(updatedGoal.getTitle());
		updatedDto.setDescription(updatedGoal.getDescription());
		updatedDto.setTimeline(updatedGoal.getTimeline());
		updatedDto.setStatus(updatedGoal.getStatus());
		updatedDto.setUpdateBy(updatedGoal.getUpdateBy());
		updatedDto.setUpdatedDate(LocalDateTime.now());
		updatedDto.setGoalCategoryId(updatedGoal.getGoalCategory().getGoalCategoryId());
		updatedDto.setFinancialYear(updatedGoal.getFinancialYear().getFinancialYearCode());
		updatedDto.setFinancialYearId(updatedGoal.getFinancialYear().getFinancialYearId());
		updatedDto.setQuarterName(updatedGoal.getQuarter().getQuarterCode());
		updatedDto.setQuarterId(updatedGoal.getQuarter().getQuarterId());
		log.info("Goal updated successfully. goalId={}", updatedGoal.getGoalId());
		return updatedDto;
	}
//	@Transactional(rollbackFor = Throwable.class)
//	public PerformanceGoalDTO saveOrUpdateGoal(PerformanceGoalDTO dto) {
//
//	    log.info("SaveOrUpdate Goal → goalId={}, employeeId={}, type={}",
//	            dto.getGoalId(), dto.getEmployeeId(), dto.getType());
//
//	    PerformanceGoal goal;
//
//	    // ✅ UPDATE FLOW
//	    if (dto.getGoalId() != null) {
//
//	        goal = performanceGoalRepository.findById(dto.getGoalId())
//	                .orElseThrow(() -> new InvalidCredentialsException("Goal not found"));
//
//	        if (goal.getStatus() != 1001) {
//	            log.warn("Update blocked → goalId={}, status={}", dto.getGoalId(), goal.getStatus());
//	            throw new InvalidCredentialsException("Only draft goals can be edited.");
//	        }
//
//	        goal.setUpdatedDate(LocalDateTime.now());
//	        goal.setUpdateBy(dto.getUpdateBy());
//
//	        log.debug("Updating existing goal → goalId={}", dto.getGoalId());
//	    }
//
//	    // ✅ CREATE FLOW
//	    else {
//	        goal = new PerformanceGoal();
//	        goal.setCreatedDate(LocalDateTime.now());
//	        goal.setCreatedBy(dto.getCreatedBy());
//
//	        log.debug("Creating new goal for employeeId={}", dto.getEmployeeId());
//	    }
//
//	    // ✅ COMMON FIELD MAPPING
//	    goal.setEmployeeId(dto.getEmployeeId());
//	    goal.setTitle(dto.getTitle());
//	    goal.setDescription(dto.getDescription());
//	    goal.setTimeline(dto.getTimeline());
//
//	    // ✅ STATUS LOGIC
//	    if ("submit".equalsIgnoreCase(dto.getType())) {
//	        goal.setStatus(1002);
//	        log.debug("Goal marked as SUBMITTED");
//	    } else {
//	        goal.setStatus(1001);
//	        log.debug("Goal marked as DRAFT");
//	    }
//
//	    // ✅ RELATIONS
//	    MasterFinancialYear fy = masterFinancialYearRepository.findById(dto.getFinancialYearId())
//	            .orElseThrow(() -> new InvalidCredentialsException("Financial Year not found"));
//	    goal.setFinancialYear(fy);
//
//	    MasterQuarter quarter = masterQuarterRepository.findById(dto.getQuarterId())
//	            .orElseThrow(() -> new InvalidCredentialsException("Quarter not found"));
//	    goal.setQuarter(quarter);
//
//	    GoalCategory category = goalCategoryRepository.findById(dto.getGoalCategoryId())
//	            .orElseThrow(() -> new InvalidCredentialsException("Goal Category not found"));
//	    goal.setGoalCategory(category);
//
//	    // ✅ SAVE
//	    PerformanceGoal savedGoal = performanceGoalRepository.save(goal);
//
//	    log.info("Goal saved successfully → goalId={}, status={}",
//	            savedGoal.getGoalId(), savedGoal.getStatus());
//
//	    return convertToDto(savedGoal);
//	}
//	private PerformanceGoalDTO convertToDto(PerformanceGoal goal) {
//
//	    if (goal == null) {
//	        return null;
//	    }
//
//	    PerformanceGoalDTO dto = new PerformanceGoalDTO();
//
//	    dto.setGoalId(goal.getGoalId());
//	    dto.setEmployeeId(goal.getEmployeeId());
//	    dto.setTitle(goal.getTitle());
//	    dto.setDescription(goal.getDescription());
//	    dto.setTimeline(goal.getTimeline());
//	    dto.setStatus(goal.getStatus());
//
//	    dto.setCreatedBy(goal.getCreatedBy());
//	    dto.setUpdateBy(goal.getUpdateBy());
//	    dto.setUpdatedDate(goal.getUpdatedDate());
//
//	    // ✅ Safe relation mapping (avoid NullPointerException)
//
//	    if (goal.getGoalCategory() != null) {
//	        dto.setGoalCategoryId(goal.getGoalCategory().getGoalCategoryId());
//	    }
//
//	    if (goal.getFinancialYear() != null) {
//	        dto.setFinancialYearId(goal.getFinancialYear().getFinancialYearId());
//	        dto.setFinancialYear(goal.getFinancialYear().getFinancialYearCode());
//	    }
//
//	    if (goal.getQuarter() != null) {
//	        dto.setQuarterId(goal.getQuarter().getQuarterId());
//	        dto.setQuarterName(goal.getQuarter().getQuarterCode());
//	    }
//
//	    return dto;
//	}


	// @Cacheable(value = "employeeGoals", key = "#employeeId")
	public EmployeePerformanceGoalsDTO getEmployeeGoals(Integer employeeId) {
		log.info("Fetching goals for employeeId={}", employeeId);
		Map<String, Object> empMap = masterRepository.getEmployeeDetails(employeeId);
		if (empMap == null || empMap.isEmpty()) {
			log.error("Employee not found: {}", employeeId);
			throw new RuntimeException("Employee not found: " + employeeId);
		}

		EmployeePerformanceGoalsDTO employee = new EmployeePerformanceGoalsDTO();
		employee.setEmployeeId(String.valueOf(empMap.get("employeeId")));
		employee.setFullName(String.valueOf(empMap.get("fullName")));
		employee.setDepartment(String.valueOf(empMap.get("department")));
		employee.setDesignation(String.valueOf(empMap.get("designation")));
		List<PerformanceGoal> goalEntities = performanceGoalRepository
				.findByEmployeeIdOrderByCreatedDateDesc(employeeId);

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
			/*
			 * dto.setDocumentName(goal.getDocumentName());
			 * dto.setDocument(goal.getDocumentPath()); if (goal.getDocumentPath() != null)
			 * { try {
			 * dto.setDocumentBytes(Files.readAllBytes(Paths.get(goal.getDocumentPath())));
			 * } catch (IOException e) { dto.setDocumentBytes(null); } }
			 */
			dto.setQuarterName(goal.getQuarter() != null ? goal.getQuarter().getQuarterCode() : null);
			dto.setFinancialYear(goal.getFinancialYear().getFinancialYearCode());
			dto.setFinancialYearId(goal.getFinancialYear().getFinancialYearId());
			dto.setQuarterId(goal.getQuarter().getQuarterId());
			dto.setQuarterName(goal.getQuarter().getQuarterCode());

			return dto;
		}).collect(Collectors.toList());
		List<Map<String, Object>> groupedGoalList = goalDTOList.stream().collect(
				Collectors.groupingBy(PerformanceGoalDTO::getFinancialYear, LinkedHashMap::new, Collectors.toList()))
				.entrySet().stream().map(entry -> {
					Map<String, Object> yearBlock = new LinkedHashMap<>();
					yearBlock.put("year", entry.getKey());
					yearBlock.put("goals", entry.getValue());
					return yearBlock;
				}).collect(Collectors.toList());

		employee.setGoalsByYear(groupedGoalList);
		log.info("Found {} goals for employeeId={}", goalEntities.size(), employeeId);
		return employee;
	}

	/**
	 * Retrieves appraisal modules configured for a department.
	 *
	 * Flow: 1. Validate departmentId 2. Fetch active department–module mappings 3.
	 * Extract distinct appraisal modules 4. Convert modules to AppModuleDTO list
	 **/
	// @Cacheable(value = "appraisalModules", key = "#departmentId")
	public List<AppModuleDTO> getAppraisalModulesByDepartment(Integer departmentId) {
		if (departmentId == null) {
			departmentId = 0;
		}

		List<AppraisalDeptModule> deptModules = appraisalDeptModuleRepository.findByDepartmentIdAndStatus(departmentId,
				1001);
		if (deptModules.isEmpty() && departmentId != 0) {
			deptModules = appraisalDeptModuleRepository.findByDepartmentIdAndStatus(0, 1001);
		}

		if (deptModules.isEmpty()) {
			return Collections.emptyList();
		}

		return deptModules.stream().map(AppraisalDeptModule::getAppraisalModule).filter(Objects::nonNull).distinct()
				.map(module -> new AppModuleDTO(module.getAppraisalModuleId(), module.getAppraisalModuleName()))
				.collect(Collectors.toList());
	}

	/**
	 * Retrieves self-appraisal questions for a specific appraisal module.
	 *
	 * Flow: 1. Validate moduleId 2. Fetch active department-module questions 3. Map
	 * questions to EmployeeAppraisalDetailDTO 4. Initialize rating and comment
	 * fields as null (self-appraisal entry)
	 **/
//	@Cacheable(value = "appraisalQuestions", key = "#moduleId")
	public List<EmployeeAppraisalDetailDTO> getQuestionByModuleId(Integer moduleId) {
		if (moduleId == null) {
			return Collections.emptyList();
		}
		List<AppraisalDeptQuestion> questions = appraisalDeptQuestionRepository
				.findBydeptModuleDeptModuleIdAndDeptModuleStatus(moduleId, 1001);

		if (questions.isEmpty()) {
			return Collections.emptyList();
		}
		return questions.stream().map(q -> {
			EmployeeAppraisalDetailDTO dto = new EmployeeAppraisalDetailDTO();
			dto.setQuestionId(q.getQuestionId());
			dto.setQuestionName(q.getQuestionText());
			dto.setAppraisalDetailId(null);
			dto.setRatingId(null);
			dto.setRatingComment(null);
			dto.setCreatedDate(null);
			return dto;
		}).collect(Collectors.toList());
	}

	@Transactional
	public EmployeeAppraisal insertOrUpdateAppraisal(EmployeeAppraisalDTO dto) {
		log.info("Insert/Update appraisal for employeeId={}, FY={}, Quarter={}, type={}", dto.getEmployeeId(),
				dto.getFinancialYearId(), dto.getQuarterId(), dto.getType());
		EmployeeAppraisal appraisal = employeeAppraisalRepository
				.findByEmployeeIdAndFinancialYear_FinancialYearIdAndQuarter_QuarterId(dto.getEmployeeId(),
						dto.getFinancialYearId(), dto.getQuarterId());

		boolean isSubmit = "submit".equalsIgnoreCase(dto.getType());

		if (appraisal != null && appraisal.getStatus() == 1002) {
			log.warn("Appraisal already submitted. appraisalId={}", appraisal.getAppraisalId());
			throw new InvalidCredentialsException("Appraisal already submitted. No further changes allowed.");
		}

		if (appraisal == null) {
			appraisal = new EmployeeAppraisal();
			appraisal.setEmployeeId(dto.getEmployeeId());

			MasterFinancialYear financialYear = masterFinancialYearRepository.findById(dto.getFinancialYearId())
					.orElseThrow(() -> new InvalidCredentialsException("Financial Year not found"));
			appraisal.setFinancialYear(financialYear);

			MasterQuarter quarter = masterQuarterRepository.findById(dto.getQuarterId())
					.orElseThrow(() -> new RuntimeException("Quarter not found"));
			appraisal.setQuarter(quarter);

			appraisal.setCreatedDate(LocalDateTime.now());

			AppraisalEligibleEmployee eligible = appraisalEligibleEmployeeRepository
					.findByEmployeeIdAndFinancialYear_FinancialYearIdAndQuarter_QuarterIdAndStatus(dto.getEmployeeId(),
							dto.getFinancialYearId(), dto.getQuarterId(), 1001)
					.orElseThrow(() -> new RuntimeException("Eligible employee record not found for appraisal"));
			appraisal.setEligibleEmployee(eligible);
		}

		appraisal.setStatus(isSubmit ? 1002 : 1001);
		appraisal = employeeAppraisalRepository.save(appraisal);

		if (dto.getDetails() != null && !dto.getDetails().isEmpty()) {

			for (EmployeeAppraisalDetailDTO detailDTO : dto.getDetails()) {
				  if (detailDTO.getRatingId() == null) {
				        log.warn("Missing rating for questionId={}", detailDTO.getQuestionId());
				        throw new InvalidCredentialsException(
				                "Please provide rating for all questions."
				        );
				    }

				    if (detailDTO.getRatingComment() == null || detailDTO.getRatingComment().trim().isEmpty()) {
				        log.warn("Missing comment for questionId={}", detailDTO.getQuestionId());
				        throw new InvalidCredentialsException(
				                "Please provide comment for all questions."
				        );
				    }
				EmployeeAppraisalDetails detail = employeeAppraisalDetailsRepository
						.findByAppraisal_AppraisalIdAndQuestion_QuestionId(appraisal.getAppraisalId(),
								detailDTO.getQuestionId());

				if (detail == null) {
					detail = new EmployeeAppraisalDetails();
					detail.setAppraisal(appraisal);

					// IMPORTANT: Set question entity immediately to avoid duplicate inserts
					AppraisalDeptQuestion question = appraisalDeptQuestionRepository.findById(detailDTO.getQuestionId())
							.orElseThrow(() -> new RuntimeException("Question not found"));
					detail.setQuestion(question);
				}

				detail.setRatingId(detailDTO.getRatingId());
				detail.setRatingComment(detailDTO.getRatingComment());
				detail.setCreatedDate(LocalDateTime.now());

				employeeAppraisalDetailsRepository.save(detail);
			}
		}

		if (isSubmit
				&& !employeeAppraisalApproversRepository.existsByAppraisal_AppraisalId(appraisal.getAppraisalId())) {
			saveApprovers(appraisal, dto.getEmployeeId());
		}
		log.info("Appraisal saved. appraisalId={}, status={}", appraisal.getAppraisalId(), appraisal.getStatus());
		return appraisal;
	}

	@Transactional
	public void saveApprovers(EmployeeAppraisal appraisal, Integer employeeSeqNo) {

		List<Integer> under = masterRepository.findEmployeesUnder();
		Set<Integer> managerChain = new LinkedHashSet<>();
		Integer currentSeq = employeeSeqNo;

		while (true) {
			Integer managerSeq = masterRepository.findDirectManager(currentSeq);

			if (managerSeq == null)
				break;

			if (managerChain.contains(managerSeq))
				break;

			managerChain.add(managerSeq);

			if (under.contains(managerSeq)) {
				break;
			}

			currentSeq = managerSeq;
		}

		AtomicInteger level = new AtomicInteger(1);
		int totalLevels = managerChain.size();

		List<EmployeeAppraisalApprovers> approvers = managerChain.stream().map(mgr -> {

			int approverLevel = level.getAndIncrement();

			EmployeeAppraisalApprovers ap = new EmployeeAppraisalApprovers();
			ap.setAppraisal(appraisal);
			ap.setApproverEmpId(mgr);
			ap.setApproverLevel(approverLevel);
			if (approverLevel == totalLevels) {
				ap.setApproverRole("H");
			} else {
				ap.setApproverRole("M");
			}

			if (approverLevel == 1) {
				ap.setApproverStatus(1002);
			} else {
				ap.setApproverStatus(1001);
			}

			ap.setLupdate(LocalDateTime.now());
			return ap;

		}).collect(Collectors.toList());

		employeeAppraisalApproversRepository.saveAll(approvers);
	}

	// @Cacheable(value = "managerStatus", key = "#eligibleId")
	public List<Map<String, Object>> getManagerStatus(Integer eligibleId) {

		List<Object[]> rows = masterRepository.getApproverStatus(eligibleId);

		return rows.stream().map(r -> {
			Map<String, Object> map = new HashMap<>();
			map.put("managerName", r[0]);
			map.put("approverStatus", r[1]);
			map.put("status", r[2]);
			return map;
		}).collect(Collectors.toList());
	}

	/**
	 * Service method to fetch appraisal details for an employee grouped by
	 * department modules and questions
	 */
	// @Cacheable(value = "appraisalData", key = "#eligibleId + '-' +
	// #departmentId")
	public EmployeeAppraisalDTO getApprasalData(Integer eligibleId, Integer departmentId) {
		EmployeeAppraisal appraisal = employeeAppraisalRepository.findByEligibleEmployee_EligibleId(eligibleId)
				.orElse(null);

		if (appraisal == null) {
			EmployeeAppraisalDTO emptyDto = new EmployeeAppraisalDTO();
			emptyDto.setAppModule(Collections.emptyList());
			emptyDto.setModuleSummary(Collections.emptyList());
			return emptyDto;
		}

		Integer appraisalId = appraisal.getAppraisalId();
		List<EmployeeAppraisalDetails> detailEntities = employeeAppraisalDetailsRepository
				.findByAppraisal_AppraisalId(appraisalId);
//		List<AppraisalDeptModule> deptModules = appraisalDeptModuleRepository.findByDepartmentIdAndStatus(departmentId,
//				1001);
		List<AppraisalDeptModule> fetchedModules = appraisalDeptModuleRepository
				.findByDepartmentIdAndStatus(departmentId, 1001);

		final List<AppraisalDeptModule> deptModules;
		if (fetchedModules.isEmpty() && departmentId != 0) {
			deptModules = appraisalDeptModuleRepository.findByDepartmentIdAndStatus(0, 1001);
		} else {
			deptModules = fetchedModules;
		}

		EmployeeAppraisalDTO dto = new EmployeeAppraisalDTO();
		dto.setAppraisalId(appraisal.getAppraisalId());
		dto.setEmployeeId(appraisal.getEmployeeId());
		dto.setFinancialYearId(appraisal.getFinancialYear().getFinancialYearId());
		dto.setFinancialYear(appraisal.getFinancialYear().getFinancialYearCode());
		dto.setStatus(appraisal.getStatus());
		dto.setCreatedDate(appraisal.getCreatedDate());

		List<AppModuleDTO> modules = deptModules.stream().map(module -> {

			Integer moduleId = module.getAppraisalModule().getAppraisalModuleId();
			String moduleName = module.getAppraisalModule().getAppraisalModuleName();

			AppModuleDTO mod = new AppModuleDTO(moduleId, moduleName);

			List<EmployeeAppraisalDetailDTO> qList = module.getQuestions().stream().map(q -> {
				EmployeeAppraisalDetails saved = detailEntities.stream()
						.filter(ans -> ans.getQuestion().getQuestionId().equals(q.getQuestionId())).findFirst()
						.orElse(null);

				EmployeeAppraisalDetailDTO qdto = new EmployeeAppraisalDetailDTO();
				qdto.setAppraisalDetailId(saved != null ? saved.getAppraisalDetailId() : null);
				qdto.setQuestionId(q.getQuestionId());
				qdto.setQuestionName(q.getQuestionText());
				qdto.setRatingId(saved != null ? saved.getRatingId() : null);
				qdto.setRatingComment(saved != null ? saved.getRatingComment() : null);
				qdto.setCreatedDate(saved != null ? saved.getCreatedDate() : null);

				return qdto;

			}).collect(Collectors.toList());

			mod.setEmployeeAppraisal(qList);

			return mod;

		}).collect(Collectors.toList());

		dto.setAppModule(modules);
		List<ModuleQuestionAnswerCountDTO> moduleSummaryList = deptModules.stream().map(module -> {
			Integer moduleId = module.getAppraisalModule().getAppraisalModuleId();
			long questionCount = module.getQuestions().size();
			long submittedCount = detailEntities.stream().filter(ans -> ans.getQuestion().getDeptModule()
					.getAppraisalModule().getAppraisalModuleId().equals(moduleId)).count();

			ModuleQuestionAnswerCountDTO summary = new ModuleQuestionAnswerCountDTO();
			summary.setModuleId(moduleId);
			summary.setQuestionCount(questionCount);
			summary.setSubmittedAnswerCount(submittedCount);

			return summary;
		}).collect(Collectors.toList());

		dto.setModuleSummary(moduleSummaryList);

		return dto;
	}

	@Transactional
	public EmployeeAppraisalWork saveOrUpdate(EmployeeAppraisalWorkDTO dto) {
		if (dto.getType() == null) {
			throw new IllegalArgumentException("Type is required");
		}

		EmployeeAppraisalWork entity;
		if (dto.getWorkId() != null) {
			entity = employeeAppraisalWorkRepository.findById(dto.getWorkId())
					.orElseThrow(() -> new RuntimeException("Record not found to update"));
		} else {
			entity = new EmployeeAppraisalWork();
		}

		if ("submit".equalsIgnoreCase(dto.getType())) {
			dto.setStatus(1002);
		} else if ("draft".equalsIgnoreCase(dto.getType())) {
			dto.setStatus(1001);
		} else {
			throw new IllegalArgumentException("Invalid type value: " + dto.getType());
		}

		updateEntityFromDto(entity, dto);
		return employeeAppraisalWorkRepository.save(entity);
	}

	private void updateEntityFromDto(EmployeeAppraisalWork entity, EmployeeAppraisalWorkDTO dto) {
		entity.setEmployeeId(dto.getEmployeeId());

		if (dto.getFinancialYearId() != null) {
			MasterFinancialYear fy = masterFinancialYearRepository.findById(dto.getFinancialYearId()).orElseThrow(
					() -> new RuntimeException("Financial Year not found with id " + dto.getFinancialYearId()));
			entity.setFinancialYear(fy);
		} else {
			entity.setFinancialYear(null);
		}

		if (dto.getQuarterId() != null) {
			MasterQuarter quarter = masterQuarterRepository.findById(dto.getQuarterId())
					.orElseThrow(() -> new RuntimeException("Quarter not found with id " + dto.getQuarterId()));
			entity.setQuarter(quarter);
		} else {
			entity.setQuarter(null);
		}

		entity.setWorkTitle(dto.getWorkTitle());
		entity.setWorkDescription(dto.getWorkDescription());
		if (dto.getWorkTypeId() != null) {
			MasterWorkType workType = masterWorkTypeRepository.findById(dto.getWorkTypeId())
					.orElseThrow(() -> new RuntimeException("Work Type not found with id " + dto.getWorkTypeId()));
			entity.setWorkType(workType);
		} else {
			entity.setWorkType(null);
		}
		entity.setCompletionPercentage(dto.getCompletionPercentage() != null ? dto.getCompletionPercentage() : 0);
		entity.setOutcome(dto.getOutcome());
		entity.setStatus(dto.getStatus() != null ? dto.getStatus() : 1001);
		if (entity.getCreatedDate() == null) {
			entity.setCreatedDate(dto.getCreatedDate() != null ? dto.getCreatedDate() : LocalDateTime.now());
		}
	}
	// @Cacheable(value = "employeeWorks", key = "#employeeId")
	public List<EmployeeAppraisalWorkResponseDTO> getWorksByEmployeeId(Integer employeeId) {
	    List<EmployeeAppraisalWork> works =
	            employeeAppraisalWorkRepository.findByEmployeeIdOrderByCreatedDateDesc(employeeId);
	    Map<String, List<EmployeeAppraisalWorkDTO>> worksByYear = works.stream()

	            .map(this::mapToDTO)

	            .collect(Collectors.groupingBy(
	                    dto -> dto.getFinancialYear() == null ? "Unknown" : dto.getFinancialYear(),
	                    LinkedHashMap::new, 
	                    Collectors.toList()
	            ));
	    return worksByYear.entrySet()
	            .stream()
	            .map(entry -> {
	                EmployeeAppraisalWorkResponseDTO responseDTO = new EmployeeAppraisalWorkResponseDTO();
	                responseDTO.setFinancialYear(entry.getKey());
	                responseDTO.setWorks(entry.getValue());
	                return responseDTO;
	            })
	            .collect(Collectors.toList());
	}
	private EmployeeAppraisalWorkDTO mapToDTO(EmployeeAppraisalWork work) {
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
	    if (work.getStatus() != null) {
	        switch (work.getStatus()) {
	            case 1001:
	                dto.setStatusName("Draft");
	                break;
	            case 1002:
	                dto.setStatusName("Submitted");
	                break;
	            default:
	                dto.setStatusName("Unknown");
	        }
	    }
	    dto.setCreatedDate(work.getCreatedDate());
	    dto.setUpdatedDate(work.getUpdatedDate());
	    return dto;
	}

//	@Cacheable(value = "workDetails", key = "#workId")
	public EmployeeAppraisalWorkDTO getWorkById(Integer workId) {
		EmployeeAppraisalWork work = employeeAppraisalWorkRepository.findByWorkId(workId);
		if (work == null) {
			throw new InvalidCredentialsException("Work with ID " + workId + " not found");
		}

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
		if (work.getWorkType() != null) {
			dto.setWorkTypeId(work.getWorkType().getWorkTypeId());
			dto.setWorkType(work.getWorkType().getWorkType());
		}
		dto.setWorkDescription(work.getWorkDescription());
		dto.setCompletionPercentage(work.getCompletionPercentage());
		dto.setOutcome(work.getOutcome());
		dto.setStatus(work.getStatus());
		if (work.getStatus() != null) {
			if (work.getStatus() == 1001) {
				dto.setStatusName("saveOrDraft");
			} else if (work.getStatus() == 1002) {
				dto.setStatusName("Submitted");
			}
		}
		dto.setCreatedDate(work.getCreatedDate());
		dto.setUpdatedDate(work.getUpdatedDate());

		return dto;
	}
	public byte[] downloadFormat() {

	    try (Workbook workbook = new XSSFWorkbook();
	         ByteArrayOutputStream out = new ByteArrayOutputStream()) {

	        Sheet sheet = workbook.createSheet("Work Format");

	        // ================= HEADER STYLE =================
	        CellStyle headerStyle = workbook.createCellStyle();
	        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	        Font headerFont = workbook.createFont();
	        headerFont.setBold(true);
	        headerFont.setColor(IndexedColors.BLACK.getIndex());

	        headerStyle.setFont(headerFont);
	        headerStyle.setAlignment(HorizontalAlignment.CENTER);
	        headerStyle.setBorderTop(BorderStyle.THIN);
	        headerStyle.setBorderBottom(BorderStyle.THIN);
	        headerStyle.setBorderLeft(BorderStyle.THIN);
	        headerStyle.setBorderRight(BorderStyle.THIN);

	        // ================= HEADER ROW =================
	        Row headerRow = sheet.createRow(0);
	        headerRow.setHeightInPoints(22);

	        String[] headers = {
	                "Work Title",
	                "Work Description",
	                "Work Type",
	                "Completion Percentage",
	                "Outcome"
	        };

	        for (int i = 0; i < headers.length; i++) {
	            Cell cell = headerRow.createCell(i);
	            cell.setCellValue(headers[i]);
	            cell.setCellStyle(headerStyle);
	            sheet.autoSizeColumn(i);
	        }

	        // ================= WORK TYPE DROPDOWN =================
	        List<MasterWorkType> workTypes = masterWorkTypeRepository.findAll()
	                .stream()
	                .filter(w -> w.getStatus() == 1001)
	                .collect(Collectors.toList());

	        Sheet hiddenSheet = workbook.createSheet("worktype_hidden");

	        for (int i = 0; i < workTypes.size(); i++) {
	            Row row = hiddenSheet.createRow(i);
	            row.createCell(0).setCellValue(workTypes.get(i).getWorkType());
	        }

	        workbook.setSheetHidden(workbook.getSheetIndex(hiddenSheet), true);

	        DataValidationHelper helper = sheet.getDataValidationHelper();

	        String workTypeFormula = "worktype_hidden!$A$1:$A$" + workTypes.size();
	        DataValidationConstraint workTypeConstraint =
	                helper.createFormulaListConstraint(workTypeFormula);

	        CellRangeAddressList workTypeRange =
	                new CellRangeAddressList(1, 1000, 2, 2);

	        DataValidation workTypeValidation =
	                helper.createValidation(workTypeConstraint, workTypeRange);

	        workTypeValidation.setShowErrorBox(true);
	        workTypeValidation.setSuppressDropDownArrow(true);

	        sheet.addValidationData(workTypeValidation);

	        // ================= COMPLETION PERCENTAGE DROPDOWN =================
	        String[] percentages = { "0", "20", "40", "60", "80", "100" };

	        Sheet percentHidden = workbook.createSheet("percent_hidden");

	        for (int i = 0; i < percentages.length; i++) {
	            Row row = percentHidden.createRow(i);
	            row.createCell(0).setCellValue(percentages[i]);
	        }

	        workbook.setSheetHidden(workbook.getSheetIndex(percentHidden), true);

	        String percentFormula = "percent_hidden!$A$1:$A$" + percentages.length;
	        DataValidationConstraint percentConstraint =
	                helper.createFormulaListConstraint(percentFormula);

	        CellRangeAddressList percentRange =
	                new CellRangeAddressList(1, 1000, 3, 3);

	        DataValidation percentValidation =
	                helper.createValidation(percentConstraint, percentRange);

	        percentValidation.setShowErrorBox(true);
	        percentValidation.setSuppressDropDownArrow(true);

	        sheet.addValidationData(percentValidation);

	        // ================= WRITE FILE =================
	        workbook.write(out);
	        return out.toByteArray();

	    } catch (Exception e) {
	        throw new RuntimeException("Error generating Excel format", e);
	    }
	}

	@Transactional
	public ApiResponse uploadWorkFile(MultipartFile file, Integer employeeId, Integer financialYearId,
			Integer quarterId, String type) {
		log.info("Uploading work file for employeeId={}, FY={}, Quarter={}, type={}", employeeId, financialYearId,
				quarterId, type);
		if (file == null || file.isEmpty()) {
			return ApiResponse.error("File is required");
		}
		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {

			String storedFilePath = saveFileIfPresent(file);
			Sheet sheet = workbook.getSheetAt(0);
			MasterFinancialYear fy = masterFinancialYearRepository.findById(financialYearId)
					.orElseThrow(() -> new RuntimeException("Invalid Financial Year"));
			MasterQuarter quarter = masterQuarterRepository.findById(quarterId)
					.orElseThrow(() -> new RuntimeException("Invalid Quarter"));
			final int status;
			if ("submit".equalsIgnoreCase(type)) {
				status = 1002;
			} else if ("draft".equalsIgnoreCase(type)) {
				status = 1001;
			} else {
				return ApiResponse.error("Invalid type (submit/draft)");
			}
			List<EmployeeAppraisalWork> works = IntStream.rangeClosed(1, sheet.getLastRowNum()).mapToObj(i -> {

				Row row = sheet.getRow(i);
				if (row == null)
					return null;

				String workTitle = getString(row.getCell(0));
				String workDesc = getString(row.getCell(1));
				String workTypeText = getString(row.getCell(2));
				Integer completion = getInteger(row.getCell(3));
				String outcome = getString(row.getCell(4));
				if (workTitle == null || workTypeText == null) {
					return null;
				}
				final int rowIndex = i + 1;
				MasterWorkType workType = masterWorkTypeRepository.findByWorkTypeAndStatus(workTypeText, 1001)
						.orElseThrow(() -> new RuntimeException("Invalid Work Type at row " + rowIndex));

				if (completion == null)
					completion = 0;
				if (completion < 0 || completion > 100) {
					throw new RuntimeException("Completion % must be 0–100 at row " + rowIndex);
				}

				EmployeeAppraisalWork work = new EmployeeAppraisalWork();
				work.setEmployeeId(employeeId);
				work.setFinancialYear(fy);
				work.setQuarter(quarter);
				work.setWorkType(workType);
				work.setWorkTitle(workTitle);
				work.setWorkDescription(workDesc);
				work.setCompletionPercentage(completion);
				work.setOutcome(outcome);
				work.setStatus(status);
				work.setCreatedDate(LocalDateTime.now());
				work.setFilePath(storedFilePath);
				return work;
			}).filter(Objects::nonNull).collect(Collectors.toList());

			if (works.isEmpty()) {
				return ApiResponse.error("No valid records found in Excel");
			}

			employeeAppraisalWorkRepository.saveAll(works);
			log.info("Upload successful" + employeeId);

			return ApiResponse.success("File uploaded successfully", works.size());

		} catch (Exception e) {
			log.error("Upload failed", e);
			return ApiResponse.error("Upload failed", e.getMessage());
		}
	}

	private String saveFileIfPresent(MultipartFile file) throws IOException {

		if (file == null || file.isEmpty()) {
			return null;
		}
		File dir = new File(uploadWorks);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String fileName = file.getOriginalFilename();
		Path path = Paths.get(uploadWorks, fileName);
		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		return path.toString();
	}

	private String getString(Cell cell) {
		if (cell == null)
			return null;
		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue().trim();
		}
		if (cell.getCellType() == CellType.NUMERIC) {
			return String.valueOf((int) cell.getNumericCellValue());
		}

		return null;
	}

	private Integer getInteger(Cell cell) {
		if (cell == null)
			return null;

		if (cell.getCellType() == CellType.NUMERIC) {
			return (int) cell.getNumericCellValue();
		}

		if (cell.getCellType() == CellType.STRING) {
			String val = cell.getStringCellValue().trim();
			return val.isEmpty() ? null : Integer.parseInt(val);
		}

		return null;
	}
	public List<Map<String, String>> getGoalDocumentsBase64(Integer goalId) {

	    PerformanceGoal goal = performanceGoalRepository.findById(goalId)
	            .orElseThrow(() -> new RuntimeException("Goal not found"));

	    if (goal.getDocumentPath() == null || goal.getDocumentPath().isEmpty()) {
	        throw new RuntimeException("No documents attached");
	    }

	    String[] filePaths = goal.getDocumentPath().split(",");
	    String[] fileNames = goal.getDocumentName().split(",");

	    List<Map<String, String>> documents = new ArrayList<>();

	    for (int i = 0; i < filePaths.length; i++) {
	        try {
	            Path path = Paths.get(filePaths[i].trim());

	            if (!Files.exists(path)) {
	                continue; // skip missing files
	            }

	            byte[] fileBytes = Files.readAllBytes(path);
	            String base64 = Base64.getEncoder().encodeToString(fileBytes);

	            Map<String, String> fileInfo = new HashMap<>();
	            fileInfo.put("fileName", fileNames[i].trim());
	            fileInfo.put("base64", base64);

	            documents.add(fileInfo);

	        } catch (IOException e) {
	            throw new RuntimeException("Error reading file: " + filePaths[i]);
	        }
	    }

	    return documents;
	}
//
//	public List<PerformanceGoalDTO> getGoalsByEmployeeAndDate(
//	        Integer employeeId, String activeFromDate, String activeToDate) {
//
//	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//
//	    LocalDateTime fromDate = LocalDate.parse(activeFromDate, formatter).atStartOfDay();
//	    LocalDateTime toDate = LocalDate.parse(activeToDate, formatter).atTime(23, 59, 59);
//
//	    List<PerformanceGoal> goals =
//	            performanceGoalRepository.findByEmployeeIdAndStatusAndCreatedDateBetween(
//	                    employeeId,
//	                    1002,
//	                    fromDate,
//	                    toDate
//	            );
//	    return goals.stream()
//	            .map(this::convertToDto)  
//	            .toList();
//	}
//	public ResponseEntity<?> getGoalsAndWorksByEmployeeAndDate(
//	        Integer employeeId, String activeFromDate, String activeToDate) {
//
//	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//
//	    LocalDateTime fromDate = LocalDate.parse(activeFromDate, formatter).atStartOfDay();
//	    LocalDateTime toDate = LocalDate.parse(activeToDate, formatter).atTime(23, 59, 59);
////	    List<PerformanceGoal> goals =
////	            performanceGoalRepository.findByEmployeeIdAndStatusAndCreatedDateBetween(
////	                    employeeId, 1002, fromDate, toDate);
//	    List<PerformanceGoal> goals =
//	            performanceGoalRepository.findByEmployeeIdAndStatusNotAndCreatedDateBetween(
//	                    employeeId, 1001, fromDate, toDate);
//
//	    List<EmployeeAppraisalWorkDTO> works =
//	            getWorksByEmployeeIdAndFinanicalYearId(employeeId, fromDate, toDate);
//	    if (goals.size() < 3 && works.size() < 2) {
//	        return ResponseEntity.ok().body(
//	                "Minimum 3 goals and 2 works are required for appraisal. Kindly update your goals and work details.");
//	    }
//
//	    if (goals.size() < 3) {
//	        return ResponseEntity.ok().body(
//	                "At least 3 goals are required for appraisal. Kindly add more goals.");
//	    }
//
//	    if (works.size() < 2) {
//	        return ResponseEntity.ok().body(
//	                "At least 2 work entries are required for appraisal. Kindly add your work details.");
//	    }
//	    List<PerformanceGoalDTO> goalDTOs = goals.stream()
//	            .map(this::convertToDto)
//	            .toList();
//	    Map<String, Object> response = new HashMap<>();
//	    response.put("success", true);
//	    response.put("goals", goalDTOs);
//	    response.put("works", works);
//
//	    return ResponseEntity.ok(response);
//	}
	public ResponseEntity<?> getGoalsAndWorksByEmployeeAndDate(
	        Integer employeeId, String activeFromDate, String activeToDate) {

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	    LocalDateTime fromDate = LocalDate.parse(activeFromDate, formatter).atStartOfDay();
	    LocalDateTime toDate = LocalDate.parse(activeToDate, formatter).atTime(23, 59, 59);

	    List<PerformanceGoal> goals =
	            performanceGoalRepository.findByEmployeeIdAndStatusNotAndCreatedDateBetween(
	                    employeeId, 1001, fromDate, toDate);

	    List<EmployeeAppraisalWorkDTO> works =
	            getWorksByEmployeeIdAndFinanicalYearId(employeeId, fromDate, toDate);

	    int goalCount = goals.size();
	    int workCount = works.size();

	    if (goalCount < 3 && workCount < 2) {
	        return ResponseEntity.ok().body(
	                "Minimum 3 goals and 2 works are required for appraisal. Kindly update your goals and work details.");
	    }

	    if (goalCount < 3) {
	        return ResponseEntity.ok().body(
	                "At least 3 goals are required for appraisal. Kindly add more goals.");
	    }

	    if (workCount < 2) {
	        return ResponseEntity.ok().body(
	                "At least 2 work entries are required for appraisal. Kindly add your work details.");
	    }

	    Map<String, Object> response = new HashMap<>();
	    response.put("success", true);
	    response.put("goalCount", goalCount);
	    response.put("workCount", workCount);

	    return ResponseEntity.ok(response);
	}

	public List<EmployeeAppraisalWorkDTO> getWorksByEmployeeIdAndFinanicalYearId(Integer employeeId,
			LocalDateTime fromDate, LocalDateTime toDate) {
		log.info("Fetching works for employeeId={} and financialYearId={}", employeeId, fromDate, toDate);
		List<Integer> statuses = Arrays.asList(1002,1003,1004,1005); // approved works

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

}
