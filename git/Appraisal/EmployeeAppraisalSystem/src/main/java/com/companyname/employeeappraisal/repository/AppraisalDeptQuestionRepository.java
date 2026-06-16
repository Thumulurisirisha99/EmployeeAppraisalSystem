package com.companyname.employeeappraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyname.employeeappraisal.model.AppraisalDeptQuestion;

public interface AppraisalDeptQuestionRepository extends JpaRepository<AppraisalDeptQuestion, Integer> {

	List<AppraisalDeptQuestion> findByDeptModule_DeptModuleIdIn(List<Integer> deptModuleIds);

	List<AppraisalDeptQuestion> findBydeptModuleDeptModuleIdAndDeptModuleStatus(Integer moduleId, int status);

	List<AppraisalDeptQuestion> findByDeptModule_DeptModuleIdAndStatus(Integer moduleId, int i);

}
