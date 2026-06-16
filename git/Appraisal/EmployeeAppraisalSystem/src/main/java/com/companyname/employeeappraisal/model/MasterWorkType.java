package com.companyname.employeeappraisal.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(
    name = "tbl_master_work_type",
    schema = "performance_appraisal_system"
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MasterWorkType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_type_id")
    private Integer workTypeId;

    @Column(name = "work_type", nullable = false, unique = true, length = 100)
    private String workType;

    @Column(name = "status", nullable = false)
    private Integer status = 1001;

    @Column(name = "created_on", nullable = false, updatable = false, insertable = false)
    private Timestamp createdOn;

    // ---------- GETTERS & SETTERS ----------

    public Integer getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(Integer workTypeId) {
        this.workTypeId = workTypeId;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }
}
