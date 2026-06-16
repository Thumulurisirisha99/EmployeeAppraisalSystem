package com.companyname.employeeappraisal.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.companyname.employeeappraisal.dto.EmployeeAppraisalDetailDTO;
import com.companyname.employeeappraisal.dto.ManagerEmployeeAppraisalDTO;
import com.companyname.employeeappraisal.exception.InvalidCredentialsException;
import com.companyname.employeeappraisal.model.AppraisalEligibleEmployee;
import com.companyname.employeeappraisal.model.EmployeeAppraisal;
import com.companyname.employeeappraisal.model.EmployeeAppraisalApprovers;
import com.companyname.employeeappraisal.model.HodAppraisalDeptQuestion;
import com.companyname.employeeappraisal.model.HodEmployeeAppraisal;
import com.companyname.employeeappraisal.repository.AppraisalEligibleEmployeeRepository;
import com.companyname.employeeappraisal.repository.EmployeeAppraisalApproversRepository;
import com.companyname.employeeappraisal.repository.EmployeeAppraisalRepository;
import com.companyname.employeeappraisal.repository.HodAppraisalDeptQuestionRepository;
import com.companyname.employeeappraisal.repository.HodEmployeeAppraisalRepository;

@Service
public class HodService {
	private final EmployeeAppraisalRepository employeeAppraisalRepository;
	private final EmployeeAppraisalApproversRepository employeeAppraisalApproversRepository;
	private final HodEmployeeAppraisalRepository hodEmployeeAppraisalRepository;
	private final HodAppraisalDeptQuestionRepository hodAppraisalDeptQuestionRepository;
	private AppraisalEligibleEmployeeRepository appraisalEligibleEmployeeRepository;

	public HodService(EmployeeAppraisalRepository employeeAppraisalRepository,
			EmployeeAppraisalApproversRepository employeeAppraisalApproversRepository,
			HodEmployeeAppraisalRepository hodEmployeeAppraisalRepository,
			HodAppraisalDeptQuestionRepository hodAppraisalDeptQuestionRepository,
			AppraisalEligibleEmployeeRepository appraisalEligibleEmployeeRepository) {

		this.employeeAppraisalRepository = employeeAppraisalRepository;
		this.employeeAppraisalApproversRepository = employeeAppraisalApproversRepository;
		this.hodEmployeeAppraisalRepository = hodEmployeeAppraisalRepository;
		this.hodAppraisalDeptQuestionRepository = hodAppraisalDeptQuestionRepository;
		this.appraisalEligibleEmployeeRepository = appraisalEligibleEmployeeRepository;
	}
	private static final Logger log = LoggerFactory.getLogger(HodService.class);
	@Transactional
	public String saveAppraisal(ManagerEmployeeAppraisalDTO dto) {
		  log.info("HOD appraisal save started → appraisalId={}, hodId={}, type={}",
		            dto.getAppraisalId(), dto.getManagerId(), dto.getType());
		EmployeeAppraisal employeeAppraisal = employeeAppraisalRepository.findById(dto.getAppraisalId())
				.orElseThrow(() -> new RuntimeException("Appraisal not found"));

		int status = "submit".equalsIgnoreCase(dto.getType()) ? 1002 : 1001;

		saveQuestionBasedRating(dto, status, employeeAppraisal);

		if (status == 1002) {

			EmployeeAppraisalApprovers approver = employeeAppraisalApproversRepository
					.findByAppraisal_AppraisalIdAndApproverEmpId(dto.getAppraisalId(), dto.getManagerId())
					.orElseThrow(() -> new RuntimeException("Approver not found"));

			approver.setApproverStatus(1003);
			approver.setSubmittedDate(LocalDateTime.now());
			approver.setLupdate(LocalDateTime.now());

			employeeAppraisalApproversRepository.save(approver);

			List<AppraisalEligibleEmployee> list = appraisalEligibleEmployeeRepository
					.findByEmployeeIdAndFinancialYear_FinancialYearIdAndQuarter_QuarterId(
							employeeAppraisal.getEmployeeId(),
							employeeAppraisal.getFinancialYear().getFinancialYearId(),
							employeeAppraisal.getQuarter().getQuarterId());

			if (list.isEmpty()) {
				throw new RuntimeException("Eligible employee not found");
			}

			AppraisalEligibleEmployee eligible = list.get(0);
			eligible.setStatus(1003);
			eligible.setUpdatedBy(dto.getManagerId());
			eligible.setUpdatedOn(LocalDateTime.now());
			employeeAppraisal.setStatus(1003);
			employeeAppraisalRepository.save(employeeAppraisal);
			   log.info("HOD appraisal submitted successfully → appraisalId={}, employeeId={}",
		                dto.getAppraisalId(), employeeAppraisal.getEmployeeId());
			appraisalEligibleEmployeeRepository.save(eligible);

		}
		 log.info("HOD appraisal save completed → appraisalId={}, finalStatus={}",
		            dto.getAppraisalId(), status);
		return status == 1002 ? "Appraisal submitted successfully." : "Appraisal saved as draft.";
	}

	private void saveQuestionBasedRating(ManagerEmployeeAppraisalDTO dto, int status,
			EmployeeAppraisal employeeAppraisal) {
	    log.debug("Saving question ratings → appraisalId={}, hodId={}, totalQuestions={}",
	            dto.getAppraisalId(), dto.getManagerId(), dto.getDetails().size());
		for (EmployeeAppraisalDetailDTO detail : dto.getDetails()) {
			 if (detail.getRatingId() == null) {
			        log.warn("Missing rating for questionId={}", detail.getQuestionId());
			        throw new InvalidCredentialsException(
			                "Please provide rating for all questions."
			        );
			    }

			    if (detail.getRatingComment() == null || detail.getRatingComment().trim().isEmpty()) {
			        log.warn("Missing comment for questionId={}", detail.getQuestionId());
			        throw new InvalidCredentialsException(
			                "Please provide comment for all questions."
			        );
			    }

			List<HodEmployeeAppraisal> list = hodEmployeeAppraisalRepository
					.findByEmployeeAppraisal_AppraisalIdAndHodIdAndQuestion_QuestionId(dto.getAppraisalId(),
							dto.getManagerId(), detail.getQuestionId());
			HodEmployeeAppraisal entity;
			if (!list.isEmpty()) {
				entity = list.get(0);
			} else {
				entity = new HodEmployeeAppraisal();
				entity.setCreatedDate(LocalDateTime.now());
				HodAppraisalDeptQuestion question = hodAppraisalDeptQuestionRepository.findById(detail.getQuestionId())
						.orElseThrow(() -> new RuntimeException("Question not found"));
				entity.setEmployeeAppraisal(employeeAppraisal);
				entity.setHodId(dto.getManagerId());
				entity.setQuestion(question);
			}
			entity.setRatingId(detail.getRatingId());
			entity.setComment(detail.getRatingComment());
			entity.setStatus(status);
			entity.setLastUpdated(LocalDateTime.now());

			hodEmployeeAppraisalRepository.save(entity);
			   log.trace("Rating saved → questionId={}, ratingId={}",
		                detail.getQuestionId(), detail.getRatingId());
		}
		 log.debug("All question ratings saved → appraisalId={}", dto.getAppraisalId());
	}

}
