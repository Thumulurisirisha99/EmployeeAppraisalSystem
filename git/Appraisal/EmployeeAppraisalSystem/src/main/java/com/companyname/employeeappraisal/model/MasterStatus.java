package com.companyname.employeeappraisal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_master_status")
public class MasterStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "status_id")
	private Integer statusId;

	@Column(name = "status_code", nullable = false)
	private Integer statusCode;

	@Column(name = "status_name", nullable = false, length = 100)
	private String statusName;

	public MasterStatus() {
	}

	public MasterStatus(Integer statusId, Integer statusCode, String statusName) {
		this.statusId = statusId;
		this.statusCode = statusCode;
		this.statusName = statusName;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	
	public String toString() {
		return "MasterStatus [statusId=" + statusId + ", statusCode=" + statusCode + ", statusName=" + statusName + "]";
	}

}
