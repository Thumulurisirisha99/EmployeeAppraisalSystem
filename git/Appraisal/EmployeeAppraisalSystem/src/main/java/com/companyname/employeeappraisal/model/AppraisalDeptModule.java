package com.companyname.employeeappraisal.model;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_master_appraisal_dept_modules", schema = "performance_appraisal_system")
public class AppraisalDeptModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_module_id")
    private Integer deptModuleId;

    @Column(name = "DEPARTMENTID", nullable = false)
    private Integer departmentId;

    @ManyToOne
    @JoinColumn(name = "appraisal_module_id", nullable = false)
    private AppraisalModule appraisalModule;

    @Column(name = "status")
    private Integer status = 1001;

    @OneToMany(mappedBy = "deptModule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AppraisalDeptQuestion> questions;


    public Integer getDeptModuleId() {
        return deptModuleId;
    }

    public void setDeptModuleId(Integer deptModuleId) {
        this.deptModuleId = deptModuleId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public AppraisalModule getAppraisalModule() {
        return appraisalModule;
    }

    public void setAppraisalModule(AppraisalModule appraisalModule) {
        this.appraisalModule = appraisalModule;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<AppraisalDeptQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<AppraisalDeptQuestion> questions) {
        this.questions = questions;
    }
}
