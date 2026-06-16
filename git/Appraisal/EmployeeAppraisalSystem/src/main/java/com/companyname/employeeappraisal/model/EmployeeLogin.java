package com.companyname.employeeappraisal.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_employee_login", schema = "hclhrm_prod")
public class EmployeeLogin {

    @Id
    @Column(name = "EMPLOYEEID")
    private Integer employeeId;

    @Column(name = "EMPLOYEECODE", length = 15)
    private String employeeCode;

    @Column(name = "PASSWORD", length = 100)
    private String password;

    @Column(name = "STATUS")
    private Integer status;


    @Column(name = "LOGID")
    private Long logId;

    @Column(name = "CREATEDBY")
    private Integer createdBy;

    @Column(name = "DATECREATED")
    private LocalDateTime dateCreated;

    @Column(name = "MODIFIEDBY")
    private Integer modifiedBy;

    @Column(name = "DATEMODIFIED")
    private LocalDateTime dateModified;

    @Column(name = "VERIFIEDBY")
    private Integer verifiedBy;

    @Column(name = "DATEVERIFIED")
    private LocalDateTime dateVerified;

    @Column(name = "LUPDATE")
    private LocalDateTime lastUpdate;

    public Integer getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getLogId() {
        return logId;
    }
    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }
    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getDateModified() {
        return dateModified;
    }
    public void setDateModified(LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public Integer getVerifiedBy() {
        return verifiedBy;
    }
    public void setVerifiedBy(Integer verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public LocalDateTime getDateVerified() {
        return dateVerified;
    }
    public void setDateVerified(LocalDateTime dateVerified) {
        this.dateVerified = dateVerified;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
