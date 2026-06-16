package com.companyname.employeeappraisal.model;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_master_hod_appraisal_modes", schema = "performance_appraisal_system")
public class HodAppraisalMode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mode_id")
    private Integer modeId;

    @Column(name = "mode_description", nullable = false, length = 50)
    private String modeDescription;

    @Column(name = "status", nullable = false)
    private Integer status = 1001; 

    public HodAppraisalMode() {}

    public HodAppraisalMode(String modeDescription) {
        this.modeDescription = modeDescription;
        this.status = 1001;
    }

    public Integer getModeId() {
        return modeId;
    }

    public void setModeId(Integer modeId) {
        this.modeId = modeId;
    }

    public String getModeDescription() {
        return modeDescription;
    }

    public void setModeDescription(String modeDescription) {
        this.modeDescription = modeDescription;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
