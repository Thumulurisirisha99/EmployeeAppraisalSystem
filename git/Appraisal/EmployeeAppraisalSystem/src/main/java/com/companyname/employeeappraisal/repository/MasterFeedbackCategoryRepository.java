package com.companyname.employeeappraisal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyname.employeeappraisal.model.MasterFeedbackCategory;

public interface MasterFeedbackCategoryRepository extends JpaRepository<MasterFeedbackCategory,Integer> {

	List<MasterFeedbackCategory> findByStatus(int status);

	Optional<MasterFeedbackCategory> findById(Integer categoryId);

	Optional<MasterFeedbackCategory> findByCategoryName(String categoryName);

}
