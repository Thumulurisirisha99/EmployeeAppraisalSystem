
package com.companyname.employeeappraisal.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "tbl_master_quarter", schema = "performance_appraisal_system")

public class MasterQuarter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quarter_id")
    private Integer quarterId;

    @Column(name = "quarter_code", nullable = false, unique = true, length = 5)
    private String quarterCode;

    @Column(name = "months_included", nullable = false, length = 100)
    private String monthsIncluded;

    @Column(name = "start_date", nullable = false, length = 10)
    private String startDate;

    @Column(name = "end_date", nullable = false, length = 10)
    private String endDate;

    @Column(name = "created_on", nullable = false, updatable = false, insertable = false)
    private Timestamp createdOn;


    public Integer getQuarterId() {
        return quarterId;
    }

    public void setQuarterId(Integer quarterId) {
        this.quarterId = quarterId;
    }

    public String getQuarterCode() {
        return quarterCode;
    }

    public void setQuarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
    }

    public String getMonthsIncluded() {
        return monthsIncluded;
    }

    public void setMonthsIncluded(String monthsIncluded) {
        this.monthsIncluded = monthsIncluded;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }
}
