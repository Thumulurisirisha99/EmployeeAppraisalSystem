package com.companyname.employeeappraisal.repository;
public interface EmployeeLoginDTO {

	Integer  getEmployeeId();
    Integer getEmployeeCode();

    String getFullName();
    String getCallName();

    Integer getDepartmentId();
    String getDepartment();

    Integer getDesignationId();
    String getDesignation();

    String getEmail();
    String getMobileNo();
}
