package com.companyname.employeeappraisal.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.companyname.employeeappraisal.dto.ApiResponse;
import com.companyname.employeeappraisal.dto.AppModuleDTO;
import com.companyname.employeeappraisal.dto.AppraisalApproverStatusDTO;
import com.companyname.employeeappraisal.dto.AppraisalStatusDTO;
import com.companyname.employeeappraisal.dto.EmployeeAppraisalDTO;
import com.companyname.employeeappraisal.dto.EmployeeAppraisalDetailDTO;
import com.companyname.employeeappraisal.dto.EmployeeAppraisalWorkDTO;
import com.companyname.employeeappraisal.dto.FeedbackParticipantDTO;
import com.companyname.employeeappraisal.dto.FeedbackRequestDTO;
import com.companyname.employeeappraisal.dto.HodFeedbackDTO;
import com.companyname.employeeappraisal.dto.ManagerFeedbackDTO;
import com.companyname.employeeappraisal.dto.PerformanceGoalDTO;
import com.companyname.employeeappraisal.dto.QuestionAnswerDTO;
import com.companyname.employeeappraisal.dto.TeamAppraisalDetailsDTO;
import com.companyname.employeeappraisal.dto.UploadErrorDTO;
import com.companyname.employeeappraisal.exception.InvalidCredentialsException;
import com.companyname.employeeappraisal.model.AppraisalDeptModule;
import com.companyname.employeeappraisal.model.AppraisalEligibleEmployee;
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
import com.companyname.employeeappraisal.model.MasterQuarter;
import com.companyname.employeeappraisal.model.PerformanceGoal;
import com.companyname.employeeappraisal.repository.AppraisalDeptModuleRepository;
import com.companyname.employeeappraisal.repository.AppraisalEligibleEmployeeRepository;
import com.companyname.employeeappraisal.repository.AppraisalManagerDeptQuestionRepository;
import com.companyname.employeeappraisal.repository.Employee360FeedbackAnswerRepository;
import com.companyname.employeeappraisal.repository.Employee360FeedbackParticipantRepository;
import com.companyname.employeeappraisal.repository.EmployeeAppraisalApproversRepository;
import com.companyname.employeeappraisal.repository.EmployeeAppraisalDetailsRepository;
import com.companyname.employeeappraisal.repository.EmployeeAppraisalRepository;
import com.companyname.employeeappraisal.repository.EmployeeAppraisalWorkRepository;
import com.companyname.employeeappraisal.repository.HodAppraisalDeptQuestionRepository;
import com.companyname.employeeappraisal.repository.HodEmployeeAppraisalRepository;
import com.companyname.employeeappraisal.repository.ManagerEmployeeAppraisalRepository;
import com.companyname.employeeappraisal.repository.MasterFinancialYearRepository;
import com.companyname.employeeappraisal.repository.MasterQuarterRepository;
import com.companyname.employeeappraisal.repository.MasterRepository;
import com.companyname.employeeappraisal.repository.PerformanceGoalRepository;

@Service
public class HRService {

	private AppraisalEligibleEmployeeRepository appraisalEligibleEmployeeRepository;
	private MasterRepository masterRepository;
	private MasterQuarterRepository masterQuarterRepository;
	private MasterFinancialYearRepository masterFinancialYearRepository;
	private Employee360FeedbackParticipantRepository participantRepository;
	private EmployeeAppraisalRepository employeeAppraisalRepository;
	private EmployeeAppraisalApproversRepository employeeAppraisalApproversRepository;
	private AppraisalDeptModuleRepository appraisalDeptModuleRepository;
	private ManagerEmployeeAppraisalRepository managerEmployeeAppraisalRepository;
	private AppraisalManagerDeptQuestionRepository appraisalManagerDeptQuestionRepository;
	private HodEmployeeAppraisalRepository hodEmployeeAppraisalRepository;
	private HodAppraisalDeptQuestionRepository hodAppraisalDeptQuestionRepository;
	private Employee360FeedbackAnswerRepository employee360FeedbackAnswerRepository;
	private Employee360FeedbackParticipantRepository employee360FeedbackParticipantRepository;
	private EmployeeAppraisalWorkRepository employeeAppraisalWorkRepository;
	private PerformanceGoalRepository performanceGoalRepository;
	private EmployeeAppraisalDetailsRepository employeeAppraisalDetailsRepository;
	public HRService(AppraisalEligibleEmployeeRepository appraisalEligibleEmployeeRepository,
			MasterRepository masterRepository, MasterQuarterRepository masterQuarterRepository,
			MasterFinancialYearRepository masterFinancialYearRepository,
			Employee360FeedbackParticipantRepository participantRepository,
			EmployeeAppraisalRepository employeeAppraisalRepository,
			EmployeeAppraisalApproversRepository employeeAppraisalApproversRepository,
			AppraisalDeptModuleRepository appraisalDeptModuleRepository,
			ManagerEmployeeAppraisalRepository managerEmployeeAppraisalRepository,
			AppraisalManagerDeptQuestionRepository appraisalManagerDeptQuestionRepository,
			HodEmployeeAppraisalRepository hodEmployeeAppraisalRepository,
			HodAppraisalDeptQuestionRepository hodAppraisalDeptQuestionRepository,
			Employee360FeedbackAnswerRepository employee360FeedbackAnswerRepository,
			Employee360FeedbackParticipantRepository employee360FeedbackParticipantRepository,
			EmployeeAppraisalWorkRepository employeeAppraisalWorkRepository,
			PerformanceGoalRepository performanceGoalRepository,EmployeeAppraisalDetailsRepository employeeAppraisalDetailsRepository) {
		this.appraisalEligibleEmployeeRepository = appraisalEligibleEmployeeRepository;
		this.masterRepository = masterRepository;
		this.masterQuarterRepository = masterQuarterRepository;
		this.masterFinancialYearRepository = masterFinancialYearRepository;
		this.participantRepository = participantRepository;
		this.employeeAppraisalRepository = employeeAppraisalRepository;
		this.employeeAppraisalApproversRepository = employeeAppraisalApproversRepository;
		this.appraisalDeptModuleRepository = appraisalDeptModuleRepository;
		this.managerEmployeeAppraisalRepository = managerEmployeeAppraisalRepository;
		this.appraisalManagerDeptQuestionRepository = appraisalManagerDeptQuestionRepository;
		this.hodEmployeeAppraisalRepository = hodEmployeeAppraisalRepository;
		this.hodAppraisalDeptQuestionRepository = hodAppraisalDeptQuestionRepository;
		this.employee360FeedbackAnswerRepository = employee360FeedbackAnswerRepository;
		this.employee360FeedbackParticipantRepository = employee360FeedbackParticipantRepository;
		this.employeeAppraisalWorkRepository = employeeAppraisalWorkRepository;
		this.performanceGoalRepository = performanceGoalRepository;
		this.employeeAppraisalDetailsRepository=employeeAppraisalDetailsRepository;
	}

	private static final Logger log = LoggerFactory.getLogger(HRService.class);

	/*
	 * public byte[] downloadFormat(String financialYear, String quarter) {
	 * log.info("Generating appraisal format for FY: {}, Quarter: {}",
	 * financialYear, quarter); try (Workbook workbook = new XSSFWorkbook();
	 * ByteArrayOutputStream bos = new ByteArrayOutputStream()) { Sheet sheet =
	 * workbook.createSheet("Appraisal Format"); CellStyle headerStyle =
	 * workbook.createCellStyle();
	 * headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	 * headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); Font headerFont
	 * = workbook.createFont(); headerFont.setBold(true);
	 * headerFont.setColor(IndexedColors.RED.getIndex());
	 * headerStyle.setFont(headerFont); Row headerRow = sheet.createRow(0); String[]
	 * headers = { "Employee ID", "Employee Name", "Division", "Financial Year",
	 * "Quarter" }; log.debug("Excel headers created"); IntStream.range(0,
	 * headers.length).forEach(i -> { Cell cell = headerRow.createCell(i);
	 * cell.setCellValue(headers[i]); cell.setCellStyle(headerStyle);
	 * sheet.autoSizeColumn(i); }); DataValidationHelper helper =
	 * sheet.getDataValidationHelper(); createDropdown(sheet, helper, new String[] {
	 * financialYear }, 1, 1000, 3, 3); createDropdown(sheet, helper, new String[] {
	 * quarter }, 1, 1000, 4, 4); Row row = sheet.createRow(1);
	 * row.createCell(3).setCellValue(financialYear);
	 * row.createCell(4).setCellValue(quarter); workbook.write(bos);
	 * log.info("Appraisal format generated successfully"); return
	 * bos.toByteArray(); } catch (Exception e) {
	 * log.error("Error while generating appraisal format", e); throw new
	 * RuntimeException("Error creating appraisal format", e); } }
	 * 
	 * private void createDropdown(Sheet sheet, DataValidationHelper helper,
	 * String[] values, int firstRow, int lastRow, int firstCol, int lastCol) {
	 * DataValidationConstraint constraint =
	 * helper.createExplicitListConstraint(values); CellRangeAddressList addressList
	 * = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
	 * DataValidation validation = helper.createValidation(constraint, addressList);
	 * validation.setShowErrorBox(true); validation.createErrorBox("Invalid Value",
	 * "You must select from dropdown only"); sheet.addValidationData(validation); }
	 */
	public byte[] downloadFormat(Integer financialYearId, Integer quarterId) {

		MasterFinancialYear fy = masterFinancialYearRepository.findById(financialYearId)
				.orElseThrow(() -> new RuntimeException("Invalid Financial Year ID"));

		MasterQuarter q = masterQuarterRepository.findById(quarterId)
				.orElseThrow(() -> new RuntimeException("Invalid Quarter ID"));

		String financialYear = fy.getFinancialYearCode();
		String quarter = q.getQuarterCode();

		log.info("Generating appraisal format for FY: {}, Quarter: {}", financialYear, quarter);

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

			Sheet sheet = workbook.createSheet("Appraisal Format");

			// Header Style
			CellStyle headerStyle = workbook.createCellStyle();
			headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.RED.getIndex());
			headerStyle.setFont(headerFont);

			// Header Row
			Row headerRow = sheet.createRow(0);
			String[] headers = { "Employee ID", "Employee Name", "Division", "Financial Year", "Quarter" };

			IntStream.range(0, headers.length).forEach(i -> {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
				cell.setCellStyle(headerStyle);
				sheet.autoSizeColumn(i);
			});

			// Dropdowns
			DataValidationHelper helper = sheet.getDataValidationHelper();

			createDropdown(sheet, helper, new String[] { financialYear }, 1, 1000, 3, 3);
			createDropdown(sheet, helper, new String[] { quarter }, 1, 1000, 4, 4);

			// Default Values
			Row row = sheet.createRow(1);
			row.createCell(3).setCellValue(financialYear);
			row.createCell(4).setCellValue(quarter);

			workbook.write(bos);
			return bos.toByteArray();

		} catch (Exception e) {
			log.error("Error while generating appraisal format", e);
			throw new RuntimeException("Error creating appraisal format", e);
		}
	}

	private void createDropdown(Sheet sheet, DataValidationHelper helper, String[] values, int firstRow, int lastRow,
			int firstCol, int lastCol) {

		DataValidationConstraint constraint = helper.createExplicitListConstraint(values);
		CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);

		DataValidation validation = helper.createValidation(constraint, addressList);
		validation.setShowErrorBox(true);
		validation.createErrorBox("Invalid Value", "You must select from dropdown only");

		sheet.addValidationData(validation);
	}
	public ApiResponse uploadAppraisalSheet(MultipartFile file, Integer loginid, Integer financialYearId,
			Integer quarterId) {

		log.info("Appraisal sheet upload started by user: {}, FY_ID: {}, Q_ID: {}", loginid, financialYearId,
				quarterId);

		List<UploadErrorDTO> errors = new ArrayList<>();

		try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {

			Sheet sheet = workbook.getSheetAt(0);
			MasterFinancialYear financialYear = masterFinancialYearRepository.findById(financialYearId).orElse(null);

			MasterQuarter quarter = masterQuarterRepository.findById(quarterId).orElse(null);

			if (financialYear == null) {
				return ApiResponse.error("Invalid Financial Year selected");
			}

			if (quarter == null) {
				return ApiResponse.error("Invalid Quarter selected");
			}

			Row firstDataRow = sheet.getRow(1);
			if (firstDataRow != null) {

				try {
					String excelFY = firstDataRow.getCell(3).getStringCellValue().trim();
					String excelQuarter = firstDataRow.getCell(4).getStringCellValue().trim();

					if (!excelFY.equalsIgnoreCase(financialYear.getFinancialYearCode())) {
						return ApiResponse.error("Excel Financial Year mismatch");
					}

					if (!excelQuarter.equalsIgnoreCase(quarter.getQuarterCode())) {
						return ApiResponse.error("Excel Quarter mismatch");
					}

				} catch (Exception e) {
					return ApiResponse.error("Unable to read Financial Year / Quarter from Excel");
				}
			}
			Set<Integer> excelEmployeeIds = new HashSet<>();

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null)
					continue;

				try {
					excelEmployeeIds.add((int) row.getCell(0).getNumericCellValue());
				} catch (Exception e) {
					errors.add(new UploadErrorDTO(i + 1, null, List.of("Invalid Employee ID format")));
				}
			}
			List<Integer> activeEmployees = masterRepository.findActiveEmployeeIds(excelEmployeeIds);

			Set<Integer> invalidEmployees = new HashSet<>(excelEmployeeIds);
			invalidEmployees.removeAll(activeEmployees);

			AtomicInteger successCount = new AtomicInteger(0);
			IntStream.rangeClosed(1, sheet.getLastRowNum()).forEach(i -> {

				Row row = sheet.getRow(i);
				if (row == null)
					return;

				int rowNum = i + 1;
				List<String> rowErrors = new ArrayList<>();
				Integer employeeId;

				try {
					employeeId = (int) row.getCell(0).getNumericCellValue();
				} catch (Exception e) {
					errors.add(new UploadErrorDTO(rowNum, null, List.of("Invalid Employee ID")));
					return;
				}

				if (invalidEmployees.contains(employeeId)) {
					rowErrors.add("Employee not active");
				}

				Integer divisionId = masterRepository.findDivisionIdByEmployeeId(employeeId);

				if (divisionId == null) {
					rowErrors.add("Division not found");
				}
				if (rowErrors.isEmpty()) {

					boolean exists = appraisalEligibleEmployeeRepository
							.findByEmployeeIdAndQuarter_QuarterIdAndFinancialYear_FinancialYearIdAndStatus(employeeId,
									quarterId, financialYearId, 1001)
							.isPresent();

					if (exists) {
						rowErrors.add("Duplicate record");
					}
				}

				if (!rowErrors.isEmpty()) {
					errors.add(new UploadErrorDTO(rowNum, employeeId, rowErrors));
					return;
				}
				AppraisalEligibleEmployee entity = new AppraisalEligibleEmployee();
				entity.setEmployeeId(employeeId);
				entity.setDivision(divisionId);
				entity.setFinancialYear(financialYear);
				entity.setQuarter(quarter);
				entity.setCreatedBy(loginid);
				entity.setStatus(1001);

				appraisalEligibleEmployeeRepository.save(entity);

				successCount.incrementAndGet();
			});
			if (!errors.isEmpty()) {
				log.warn("Upload completed with errors. Success: {}, Errors: {}", successCount.get(), errors.size());

				return ApiResponse.error("Upload completed with errors",
						Map.of("insertedRecords", successCount.get(), "errors", errors));
			}

			log.info("Upload completed successfully. Inserted: {}", successCount.get());

			return ApiResponse.success("All records uploaded successfully",
					Map.of("insertedRecords", successCount.get()));

		} catch (Exception e) {

			log.error("Upload failed", e);

			return ApiResponse.error("Upload failed : " + e.getMessage());
		}
	}

//	public List<TeamAppraisalDetailsDTO> getTeamAppraisalEmployees(Integer financialYearId, Integer quarterId) {
//		log.info("Fetching team appraisal employees for financialYear: {}", financialYearId);
//		List<Map<String, Object>> rows = masterRepository.findTeamAppraisalDetails(financialYearId, quarterId);
//		log.debug("Rows fetched from DB: {}", rows.size());
//		return rows.stream().map(row -> {
//
//			TeamAppraisalDetailsDTO dto = new TeamAppraisalDetailsDTO();
//			dto.setEmployeeId(row.get("empId") != null ? ((Number) row.get("empId")).intValue() : null);
//			dto.setEmployeeName(row.get("callname") != null ? row.get("callname").toString() : "--");
//			dto.setDepartmentId(row.get("departmentid") != null ? ((Number) row.get("departmentid")).intValue() : null);
//			dto.setDepartment(row.get("department") != null ? row.get("department").toString() : "--");
//			dto.setDesignation(row.get("designation") != null ? row.get("designation").toString() : "--");
//			dto.setGender(row.get("gender") != null ? row.get("gender").toString() : "--");
//			dto.setGoalStatus(row.get("goalStatus") != null ? row.get("goalStatus").toString() : "--");
//			dto.setAppraisalStatus(row.get("appraisalStatus") != null ? row.get("appraisalStatus").toString() : "--");
//			dto.setAppraisalId(row.get("appraisalId") != null ? ((Number) row.get("appraisalId")).intValue() : null);
//			dto.setStatus(row.get("Status") != null ? row.get("Status").toString() : "--");
//			dto.setMngReview("Pending");
//			dto.setHodReview("Pending");
//			dto.setOverallRating("--");
//			dto.setEnable("N");
//			dto.setEligibleId(row.get("eligibleId") != null ? ((Number) row.get("eligibleId")).intValue() : null);
//			if (dto.getAppraisalId() != null) {
//
//				Integer appraisalId = Integer.valueOf(dto.getAppraisalId());
//				log.debug("Fetching participants for appraisalId: {}", appraisalId);
//				List<Employee360FeedbackParticipant> participants = participantRepository
//						.findByAppraisalId(appraisalId);
//				log.debug("Participants count for appraisalId {}: {}", appraisalId, participants.size());
//				List<FeedbackRequestDTO> feedbackRequestList = participants.stream().map(p -> {
//
//					FeedbackRequestDTO feedbackDto = new FeedbackRequestDTO();
//
//					feedbackDto.setAppraisalId(p.getAppraisalId());
//					feedbackDto.setManagerId(p.getManagerId());
//					feedbackDto.setDueDate(p.getDueDate());
//					feedbackDto.setReviewerEmployeeId(p.getReviewerEmployeeId());
//					feedbackDto.setParticipantStatus(p.getParticipantStatus());
//
//					Map<String, Object> reviewer = masterRepository.getEmployeeDetails(p.getReviewerEmployeeId());
//
//					feedbackDto.setReviewerName(
//							reviewer != null && reviewer.get("fullName") != null ? reviewer.get("fullName").toString()
//									: "--");
//					log.debug("ReviewerId: {}, ReviewerName: {}", p.getReviewerEmployeeId(),
//							feedbackDto.getReviewerName());
//					return feedbackDto;
//
//				}).collect(Collectors.toList());
//
//				dto.setFeedbackRequestDTO(feedbackRequestList);
//
//				List<Map<String, Object>> approverRows = masterRepository.findApproverStatuses(appraisalId);
//				log.debug("Manager approver rows for appraisalId {}: {}", appraisalId, approverRows.size());
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
//				boolean anyMngPending = managerReviewList.stream()
//						.anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1001);
//
//				boolean anyMngInProgress = managerReviewList.stream()
//						.anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1002);
//
//				boolean allMngSubmitted = !managerReviewList.isEmpty() && managerReviewList.stream()
//						.allMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1003);
//
//				if (anyMngPending) {
//					dto.setMngReview("Pending");
//				} else if (anyMngInProgress) {
//					dto.setMngReview("In Progress");
//				} else if (allMngSubmitted) {
//					dto.setMngReview("Completed");
//				}
//				log.debug("Manager review status for appraisalId {}: {}", appraisalId, dto.getMngReview());
//
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
//					boolean allHodSubmitted = hodRows.stream().allMatch(hr -> {
//						Integer status = hr.get("approverStatus") != null
//								? ((Number) hr.get("approverStatus")).intValue()
//								: null;
//						return status != null && status == 1003;
//					});
//
//					if (anyHodInProgress) {
//						dto.setHodReview("Pending");
//					} else if (allHodSubmitted) {
//						dto.setHodReview("Completed");
//					}
//				}
//
//				boolean managerSubmitted = managerReviewList.stream()
//						.anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1003);
//
//				boolean managerInProgress = managerReviewList.stream()
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
//				log.debug("HOD review status for appraisalId {}: {}", appraisalId, dto.getHodReview());
//			}
//
//			return dto;
//
//		}).collect(Collectors.toList());
//	}
	public List<TeamAppraisalDetailsDTO> getTeamAppraisalEmployees(Integer financialYearId, Integer quarterId) {
	    log.info("Fetching team appraisal employees for financialYear: {}", financialYearId);
	    List<Map<String, Object>> rows = masterRepository.findTeamAppraisalDetails(financialYearId, quarterId);
	    log.debug("Rows fetched from DB: {}", rows.size());

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
	        dto.setStatus(row.get("Status") != null ? row.get("Status").toString() : "--");
	        dto.setMngReview("Pending");
	        dto.setHodReview("Pending");
	        dto.setOverallRating("--");
	        dto.setEnable("N");
	        dto.setEligibleId(row.get("eligibleId") != null ? ((Number) row.get("eligibleId")).intValue() : null);

	        if (dto.getAppraisalId() != null) {
	            Integer appraisalId = dto.getAppraisalId();

	            // -----------------------------
	            // 1️⃣ Compute Overall Rating
	            // -----------------------------
	            List<EmployeeAppraisalDetails> selfDetails = employeeAppraisalDetailsRepository
	                    .findByAppraisal_AppraisalId(appraisalId);

	            List<ManagerEmployeeAppraisal> managerDetails = managerEmployeeAppraisalRepository
	                    .findByEmployeeAppraisal_AppraisalId(appraisalId);

	            List<HodEmployeeAppraisal> hodDetails = hodEmployeeAppraisalRepository
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

	            List<Double> validRatings = new ArrayList<>();
	            if (selfAvg > 0) validRatings.add(selfAvg);
	            if (managerAvg > 0) validRatings.add(managerAvg);
	            if (hodAvg > 0) validRatings.add(hodAvg);

	            Double overallAvg = validRatings.stream()
	                    .mapToDouble(Double::doubleValue)
	                    .average()
	                    .orElse(0.0);

	            dto.setOverallRating(overallAvg > 0 ? String.valueOf(Math.round(overallAvg)) : "--");

	            // -----------------------------
	            // 2️⃣ Existing Feedback/Approver logic
	            // -----------------------------
	            List<Employee360FeedbackParticipant> participants = participantRepository.findByAppraisalId(appraisalId);
	            List<FeedbackRequestDTO> feedbackRequestList = participants.stream().map(p -> {
	                FeedbackRequestDTO feedbackDto = new FeedbackRequestDTO();
	                feedbackDto.setAppraisalId(p.getAppraisalId());
	                feedbackDto.setManagerId(p.getManagerId());
	                feedbackDto.setDueDate(p.getDueDate());
	                feedbackDto.setReviewerEmployeeId(p.getReviewerEmployeeId());
	                feedbackDto.setParticipantStatus(p.getParticipantStatus());

	                Map<String, Object> reviewer = masterRepository.getEmployeeDetails(p.getReviewerEmployeeId());
	                feedbackDto.setReviewerName(reviewer != null && reviewer.get("fullName") != null
	                        ? reviewer.get("fullName").toString() : "--");
	                return feedbackDto;
	            }).collect(Collectors.toList());
	            dto.setFeedbackRequestDTO(feedbackRequestList);

	            List<Map<String, Object>> approverRows = masterRepository.findApproverStatuses(appraisalId);
	            List<AppraisalApproverStatusDTO> managerReviewList = approverRows.stream().map(ar -> {
	                AppraisalApproverStatusDTO m = new AppraisalApproverStatusDTO();
	                m.setApproverStatus(ar.get("approverStatus") != null ? ((Number) ar.get("approverStatus")).intValue() : null);
	                m.setManagerId(ar.get("managerId") != null ? ((Number) ar.get("managerId")).intValue() : null);
	                m.setManagerName(ar.get("managerName") != null ? ar.get("managerName").toString() : "--");
	                m.setStatus(ar.get("status") != null ? ar.get("status").toString() : "--");
	                return m;
	            }).collect(Collectors.toList());
	            dto.setManagerReview(managerReviewList);

	            boolean anyMngPending = managerReviewList.stream().anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1001);
	            boolean anyMngInProgress = managerReviewList.stream().anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1002);
	            boolean allMngSubmitted = !managerReviewList.isEmpty() && managerReviewList.stream().allMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1003);

	            if (anyMngPending) dto.setMngReview("Pending");
	            else if (anyMngInProgress) dto.setMngReview("In Progress");
	            else if (allMngSubmitted) dto.setMngReview("Completed");

	            List<Map<String, Object>> hodRows = masterRepository.findHodApproverStatuses(appraisalId);
	            if (hodRows != null && !hodRows.isEmpty()) {
	                boolean anyHodInProgress = hodRows.stream().anyMatch(hr -> {
	                    Integer status = hr.get("approverStatus") != null ? ((Number) hr.get("approverStatus")).intValue() : null;
	                    return status != null && status == 1002;
	                });
	                boolean allHodSubmitted = hodRows.stream().allMatch(hr -> {
	                    Integer status = hr.get("approverStatus") != null ? ((Number) hr.get("approverStatus")).intValue() : null;
	                    return status != null && status == 1003;
	                });

	                if (anyHodInProgress) dto.setHodReview("Pending");
	                else if (allHodSubmitted) dto.setHodReview("Completed");
	            }

	            boolean managerSubmitted = managerReviewList.stream().anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1003);
	            boolean managerInProgress = managerReviewList.stream().anyMatch(m -> m.getApproverStatus() != null && m.getApproverStatus() == 1002);
	            boolean hodSubmitted = hodRows != null && hodRows.stream().anyMatch(hr -> ((Number) hr.get("approverStatus")).intValue() == 1003);
	            boolean hodInProgress = hodRows != null && hodRows.stream().anyMatch(hr -> ((Number) hr.get("approverStatus")).intValue() == 1002);

	            if (managerSubmitted || hodSubmitted) dto.setEnable("Y");
	            else if (managerInProgress || hodInProgress) dto.setEnable("K");
	            else dto.setEnable("N");
	        }

	        return dto;
	    }).collect(Collectors.toList());
	}
	public EmployeeAppraisalDTO getFeedBackByAppraisalId(Integer appraisalId) {
		log.info("Fetching feedback for appraisalId: {}", appraisalId);

//	    EmployeeAppraisal appraisal = employeeAppraisalRepository
//	            .findByAppraisalIdAndStatus(appraisalId, 1002)
//	            .orElseThrow(() -> new RuntimeException("Appraisal not found"));
		EmployeeAppraisal appraisal = employeeAppraisalRepository
				.findByAppraisalIdAndStatusIn(appraisalId, Arrays.asList(1002, 1003))
				.orElseThrow(() -> new RuntimeException("Appraisal not found"));

		Integer employeeId = appraisal.getEmployeeId();
		log.debug("Appraisal found. EmployeeId: {}", employeeId);
		Integer deptId = masterRepository.findDepartmentIdByEmployeeCode(String.valueOf(employeeId));
		final Integer departmentId = (deptId != null) ? deptId : 0;
		log.debug("Resolved departmentId: {}", departmentId);
		EmployeeAppraisalDTO dto = new EmployeeAppraisalDTO();

		dto.setAppraisalId(appraisal.getAppraisalId());
		dto.setEmployeeId(employeeId);
		dto.setFinancialYear(appraisal.getFinancialYear().getFinancialYearCode());
		dto.setFinancialYearId(appraisal.getFinancialYear().getFinancialYearId());
		dto.setQuarterId(appraisal.getQuarter() != null ? appraisal.getQuarter().getQuarterId() : null);
		dto.setStatus(appraisal.getStatus());
		// dto.setAcknowledgeFlag(appraisal.getAcknowledgmentFlag());
		dto.setCreatedDate(appraisal.getCreatedDate());
		 dto.setAppModule(buildEmployeeModulesOnly(departmentId));
		List<EmployeeAppraisalApprovers> allManagers = employeeAppraisalApproversRepository
				.findByAppraisal_AppraisalIdAndApproverRole(appraisalId, "M");
		dto.setPreviousManagerFeedback(buildPreviousManagerFeedback(allManagers, departmentId, appraisalId));
		MasterFinancialYear financialYear = appraisal.getFinancialYear();
		if (financialYear == null) {
			throw new InvalidCredentialsException("Financial year details not found");
		}
		LocalDate fromDate = financialYear.getStartDate();
		LocalDate toDate = financialYear.getEndDate();
		LocalDateTime fromDateTime = fromDate.atStartOfDay();
		LocalDateTime toDateTime = toDate.atTime(LocalTime.MAX);
		log.debug("Fetching goals for employeeId: {}, financialYearId: {}", employeeId,
				appraisal.getFinancialYear().getFinancialYearId());

//		List<PerformanceGoalDTO> goals = getEmployeeGoalsByFinancialYearId(employeeId,
//				appraisal.getFinancialYear().getFinancialYearId());
		List<PerformanceGoalDTO> goals = getEmployeeGoalsByFinancialYearId(employeeId, fromDateTime, toDateTime);

		dto.setPerformanceGoalDTO(goals);
		log.debug("Fetching works for employeeId: {}, financialYearId: {}", employeeId,
				appraisal.getFinancialYear().getFinancialYearId());

		List<EmployeeAppraisalWorkDTO> works = getWorksByEmployeeIdAndFinanicalYearId(employeeId, fromDateTime,
				toDateTime);
		dto.setEmployeeAppraisalWorkDTO(works);
		employeeAppraisalApproversRepository.findByAppraisal_AppraisalIdAndApproverRole(appraisalId, "H").stream()
				.findFirst().ifPresent(hod -> {
					log.debug("HOD found → hodId: {}, level: {}", hod.getApproverEmpId(), hod.getApproverLevel());
					HodFeedbackDTO hodFeedback = buildHodFeedbackWithPending(hod.getApproverEmpId(), departmentId,
							appraisal, hod.getApproverLevel());

					dto.setHodFeedback(hodFeedback);
				});

		// 🔹 360 FEEDBACK
		dto.setFeedbackRequestDTO(build360Feedback(appraisalId));
		log.info("Feedback successfully built for appraisalId: {}", appraisalId);
		return dto;
	}

	private List<AppModuleDTO> buildEmployeeModulesOnly(Integer departmentId) {

	    log.debug("Fetching modules only → departmentId: {}", departmentId);

	    List<AppraisalDeptModule> deptModules =
	            appraisalDeptModuleRepository.findByDepartmentIdAndStatus(departmentId, 1001);

	    if (deptModules.isEmpty() && departmentId != 0) {
	        deptModules = appraisalDeptModuleRepository.findByDepartmentIdAndStatus(0, 1001);
	    }

	    if (deptModules.isEmpty()) {
	        log.warn("No modules configured → departmentId: {}", departmentId);
	        return Collections.emptyList();
	    }

	    return deptModules.stream()
	            .map(dm -> {
	                AppraisalModule module = dm.getAppraisalModule();
	                if (module == null) return null;

	                return new AppModuleDTO(
	                        module.getAppraisalModuleId(),
	                        module.getAppraisalModuleName()
	                );
	            })
	            .filter(Objects::nonNull)
	            .collect(Collectors.toList());
	}



	private HodFeedbackDTO buildHodFeedbackWithPending(Integer hodId, Integer departmentId, EmployeeAppraisal appraisal,
			Integer level) {
		log.debug("Building HOD feedback → hodId: {}, appraisalId: {}", hodId, appraisal.getAppraisalId());
		Map<String, Object> empMap = masterRepository.getEmployeeDetails(hodId);

		HodFeedbackDTO hodDTO = new HodFeedbackDTO();
		hodDTO.setManagerId(hodId);
		hodDTO.setManagerName(empMap != null ? (String) empMap.get("fullName") : null);
		hodDTO.setApproverLevel(level);
//		List<HodEmployeeAppraisal> hodAnswers = hodEmployeeAppraisalRepository
//				.findByHodIdAndEmployeeAppraisal_AppraisalId(hodId, appraisal.getAppraisalId());
		List<HodEmployeeAppraisal> hodAnswers = hodEmployeeAppraisalRepository
				.findByHodIdAndEmployeeAppraisal_AppraisalIdAndStatus(hodId, appraisal.getAppraisalId(), 1002 // ✅ Only
																												// submitted
				);

		if (hodAnswers.isEmpty()) {
			hodDTO.setModules(Collections.emptyList());
			return hodDTO;
		}
		List<AppraisalDeptModule> deptModules = appraisalDeptModuleRepository.findByDepartmentIdAndStatus(departmentId,
				1001);

		if (deptModules.isEmpty() && departmentId != 0) {
			deptModules = appraisalDeptModuleRepository.findByDepartmentIdAndStatus(0, 1001);
		}

		Map<Integer, HodEmployeeAppraisal> answerMap = new HashMap<>();
		for (HodEmployeeAppraisal ans : hodAnswers) {
			if (ans.getQuestion() != null) {
				answerMap.put(ans.getQuestion().getQuestionId(), ans);
			}
		}

		List<AppModuleDTO> moduleDTOs = new ArrayList<>();

		for (AppraisalDeptModule deptModule : deptModules) {

			AppraisalModule module = deptModule.getAppraisalModule();
			if (module == null)
				continue;

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
						}
						return dto;
					}).collect(Collectors.toList());

			moduleDTOs.add(new AppModuleDTO(module.getAppraisalModuleId(), module.getAppraisalModuleName(), null,
					questionDTOs));
		}
		log.debug("HOD feedback built successfully → hodId: {}", hodId);
		hodDTO.setModules(moduleDTOs);
		return hodDTO;
	}

	private List<FeedbackRequestDTO> build360Feedback(Integer appraisalId) {

		log.debug("Fetching 360 feedback participants → appraisalId: {}", appraisalId);
		List<Employee360FeedbackParticipant> participants = employee360FeedbackParticipantRepository
				.findByAppraisalIdAndParticipantStatus(appraisalId, 1003);

		return participants.stream().map(participant -> {

			FeedbackRequestDTO requestDTO = new FeedbackRequestDTO();
			requestDTO.setAppraisalId(participant.getAppraisalId());
			requestDTO.setManagerId(participant.getManagerId());
			requestDTO.setReviewerEmployeeId(participant.getReviewerEmployeeId());
			requestDTO.setDueDate(participant.getDueDate());
			requestDTO.setParticipantStatus(participant.getParticipantStatus());
			List<Employee360FeedbackAnswer> answers = employee360FeedbackAnswerRepository
					.findByParticipantParticipantId(participant.getParticipantId());
			Map<MasterFeedbackCategory, List<Employee360FeedbackAnswer>> grouped = answers.stream()
					.collect(Collectors.groupingBy(Employee360FeedbackAnswer::getCategory));

			List<FeedbackParticipantDTO> categoryDTOs = grouped.entrySet().stream().map(entry -> {

				MasterFeedbackCategory category = entry.getKey();

				FeedbackParticipantDTO catDTO = new FeedbackParticipantDTO();
				catDTO.setCategoryId(category.getCategoryId());
				catDTO.setCategoryName(category.getCategoryName());

				List<QuestionAnswerDTO> qaList = entry.getValue().stream().map(ans -> {
					QuestionAnswerDTO qa = new QuestionAnswerDTO();
					qa.setAnswerId(ans.getAnswerId());
					qa.setQuestionText(ans.getQuestionText());
					qa.setAnswerText(ans.getAnswerText());
					return qa;
				}).collect(Collectors.toList());

				catDTO.setAnswers(qaList);
				return catDTO;

			}).collect(Collectors.toList());

			requestDTO.setParticipants(categoryDTOs);
			log.debug("Fetching 360 feedback Data participants → appraisalId: {}", requestDTO);
			return requestDTO;

		}).collect(Collectors.toList());
	}

	private List<ManagerFeedbackDTO> buildPreviousManagerFeedback(List<EmployeeAppraisalApprovers> previousManagers,
			Integer departmentId, Integer appraisalId) {
		log.debug("Building previous manager feedback → appraisalId: {}, departmentId: {}", appraisalId, departmentId);
		final List<AppraisalDeptModule> deptModules;
		List<AppraisalDeptModule> fetchedModules = appraisalDeptModuleRepository
				.findByDepartmentIdAndStatus(departmentId, 1001);

		deptModules = fetchedModules.isEmpty() && departmentId != 0
				? appraisalDeptModuleRepository.findByDepartmentIdAndStatus(0, 1001)
				: fetchedModules;

		return previousManagers.stream().map(manager -> {

			Integer managerId = manager.getApproverEmpId();
			Map<String, Object> empMap = masterRepository.getEmployeeDetails(managerId);
			String managerName = empMap != null ? (String) empMap.get("fullName") : null;

			List<ManagerEmployeeAppraisal> answers = managerEmployeeAppraisalRepository
					.findByManagerIdAndEmployeeAppraisal_AppraisalIdAndStatus(managerId, appraisalId, 1002);

			ManagerFeedbackDTO dto = new ManagerFeedbackDTO();
			dto.setManagerId(managerId);
			dto.setManagerName(managerName);
			dto.setApproverLevel(manager.getApproverLevel());
			if (answers.isEmpty()) {
				dto.setModules(Collections.emptyList());
				return dto;
			}
			Map<Integer, ManagerEmployeeAppraisal> answerMap = answers.stream()
					.collect(Collectors.toMap(a -> a.getQuestion().getQuestionId(), Function.identity(), (o, n) -> n));
			log.debug("Answer map built → managerId: {}, mappedQuestions: {}", managerId, answerMap.size());
			List<AppModuleDTO> moduleDTOs = deptModules.stream().map(deptModule -> {

				AppraisalModule module = deptModule.getAppraisalModule();

				List<EmployeeAppraisalDetailDTO> questionDTOs = appraisalManagerDeptQuestionRepository
						.findByDeptModule_DeptModuleIdAndStatus(deptModule.getDeptModuleId(), 1001).stream().map(q -> {

							ManagerEmployeeAppraisal ans = answerMap.get(q.getQuestionId());

							EmployeeAppraisalDetailDTO qdto = new EmployeeAppraisalDetailDTO();

							qdto.setQuestionId(q.getQuestionId());
							qdto.setQuestionName(q.getQuestionText());

							if (ans != null) {
								qdto.setAppraisalDetailId(ans.getId());
								qdto.setRatingId(ans.getRatingId());
								qdto.setRatingComment(ans.getComment());
							}

							return qdto;
						}).collect(Collectors.toList());

				return new AppModuleDTO(module.getAppraisalModuleId(), module.getAppraisalModuleName(), null,
						questionDTOs);

			}).collect(Collectors.toList());

			dto.setModules(moduleDTOs);
			log.debug("Manager feedback built successfully → managerId: {}", managerId);
			return dto;

		}).collect(Collectors.toList());
	}

	private List<PerformanceGoalDTO> getEmployeeGoalsByFinancialYearId(Integer employeeId, LocalDateTime fromDate,
			LocalDateTime toDate) {
		List<Integer> statuses = Arrays.asList(1002, 1003, 1004);
		log.info("Fetching goals for employeeId={}, fromDate={}, toDate={}, statuses={}", employeeId, fromDate, toDate,
				statuses);

		List<PerformanceGoal> goals = performanceGoalRepository
				.findByEmployeeIdAndStatusInAndCreatedDateBetween(employeeId, statuses, fromDate, toDate);
		log.info("Found {} goals for employeeId={}", goals.size(), employeeId);
		System.err.println("-----: " + goals.toString());
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
		return dto;
	}

	public List<EmployeeAppraisalWorkDTO> getWorksByEmployeeIdAndFinanicalYearId(Integer employeeId,
			LocalDateTime fromDate, LocalDateTime toDate) {
		log.debug("Fetching works → employeeId: {}, financialYearId: {}", employeeId, fromDate, toDate);

		List<Integer> statuses = Arrays.asList(1002); // approved works

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

			// ✅ Status Name Logic
			if (work.getStatus() != null) {
				if (work.getStatus() == 1001) {
					dto.setStatusName("Draft");
				} else if (work.getStatus() == 1002) {
					dto.setStatusName("Submitted");
				}
			}

			dto.setCreatedDate(work.getCreatedDate());
			dto.setUpdatedDate(work.getUpdatedDate());

			return dto;
		}).collect(Collectors.toList());
		log.debug("Work DTO conversion completed");
		return dtos;
	}

	public byte[] generateExcel(String financialYear, String quarter) {
		List<AppraisalStatusDTO> data = masterRepository.getAppraisalStatus(financialYear, quarter);
		if (data == null || data.isEmpty()) {
			throw new InvalidCredentialsException("No data found for selected Financial Year / Quarter");
		}
		Workbook workbook = null;
		ByteArrayOutputStream out = null;

		try {
			workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Appraisal Status");
			CellStyle headerStyle = workbook.createCellStyle();
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerStyle.setFont(headerFont);
			headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerStyle.setBorderTop(BorderStyle.THIN);
			headerStyle.setBorderBottom(BorderStyle.THIN);
			headerStyle.setBorderLeft(BorderStyle.THIN);
			headerStyle.setBorderRight(BorderStyle.THIN);
			Row headerRow = sheet.createRow(0);

			String[] columns = { "Employee ID", "Employee Name", "Department", "Self Appraisal", "Approver Status" };
			IntStream.range(0, columns.length).forEach(i -> {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i]);
				cell.setCellStyle(headerStyle);
			});
			AtomicInteger rowIdx = new AtomicInteger(1);

			data.stream().forEach(dto -> {
				Row row = sheet.createRow(rowIdx.getAndIncrement());

				row.createCell(0).setCellValue(dto.getEmployeeId());
				row.createCell(1).setCellValue(dto.getEmployeeName());
				row.createCell(2).setCellValue(dto.getDepartment());
				row.createCell(3).setCellValue(dto.getSelfAppraisal());
				row.createCell(4).setCellValue(dto.getApproverStatus());
			});
			IntStream.range(0, columns.length).forEach(sheet::autoSizeColumn);

			out = new ByteArrayOutputStream();
			workbook.write(out);

			return out.toByteArray();

		} catch (Exception e) {
			throw new RuntimeException("Excel generation failed", e);

		} finally {
			try {
				if (workbook != null)
					workbook.close();
				if (out != null)
					out.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
