package com.companyname.employeeappraisal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_master_goal_category")
public class GoalCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "goal_category_id")
	private Integer goalCategoryId;

	@Column(name = "category_name", nullable = false, length = 100)
	private String categoryName;

	@Column(name = "status", nullable = false)
	private Integer status = 1001;

	public GoalCategory() {
		this.status = 1001;
	}

	public GoalCategory(String categoryName) {
		this.categoryName = categoryName;
		this.status = 1001;
	}

	public Integer getGoalCategoryId() {
		return goalCategoryId;
	}

	public void setGoalCategoryId(Integer goalCategoryId) {
		this.goalCategoryId = goalCategoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
