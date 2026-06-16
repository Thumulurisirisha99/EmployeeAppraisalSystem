package com.companyname.employeeappraisal.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyname.employeeappraisal.model.AppraisalManagerDeptQuestion;

public interface AppraisalManagerDeptQuestionRepository extends JpaRepository<AppraisalManagerDeptQuestion, Integer> {

	List<AppraisalManagerDeptQuestion> findByDeptModule_DeptModuleIdAndDeptModule_Status(Integer moduleId, int i);

	List<AppraisalManagerDeptQuestion> findByDeptModule_DepartmentIdAndStatus(Integer departmentId, int i);

	List<AppraisalManagerDeptQuestion> findByDeptModule_Status(int i);

	List<AppraisalManagerDeptQuestion> findByDeptModule_DeptModuleIdAndStatus(Integer deptModuleId, int i);

	
}
