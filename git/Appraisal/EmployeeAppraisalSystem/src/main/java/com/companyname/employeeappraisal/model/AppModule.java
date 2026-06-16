package com.companyname.employeeappraisal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_master_modules")
public class AppModule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "module_id")
	private Integer moduleId;

	@Column(name = "module_name", nullable = false, length = 100)
	private String moduleName;

	@Column(name = "status")
	private Integer status = 1001;

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
