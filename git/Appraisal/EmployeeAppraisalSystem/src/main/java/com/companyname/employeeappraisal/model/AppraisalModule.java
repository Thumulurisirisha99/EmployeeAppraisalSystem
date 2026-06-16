package com.companyname.employeeappraisal.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_master_appraisal_modules", schema = "performance_appraisal_system")
public class AppraisalModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appraisal_module_id")
    private Integer appraisalModuleId;

    @Column(name = "appraisal_module_name", nullable = false, length = 200)
    private String appraisalModuleName;

    @Column(name = "status")
    private Integer status = 1001;


    public Integer getAppraisalModuleId() {
        return appraisalModuleId;
    }

    public void setAppraisalModuleId(Integer appraisalModuleId) {
        this.appraisalModuleId = appraisalModuleId;
    }

    public String getAppraisalModuleName() {
        return appraisalModuleName;
    }

    public void setAppraisalModuleName(String appraisalModuleName) {
        this.appraisalModuleName = appraisalModuleName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
