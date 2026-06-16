package com.companyname.employeeappraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyname.employeeappraisal.model.GoalCategory;

public interface GoalCategoryRepository extends JpaRepository<GoalCategory, Integer> {

	List<GoalCategory> findByStatus(int Status);

}
