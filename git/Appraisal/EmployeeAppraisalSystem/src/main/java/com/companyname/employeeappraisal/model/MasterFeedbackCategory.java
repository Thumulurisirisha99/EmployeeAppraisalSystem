package com.companyname.employeeappraisal.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_master_feedback_category",
       schema = "performance_appraisal_system")
public class MasterFeedbackCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;

    @Column(name = "status")
    private Integer status = 1001; 

    @Column(name = "created_on", updatable = false, insertable = false)
    private LocalDateTime createdOn;

    public MasterFeedbackCategory() {
    }

    public MasterFeedbackCategory(String categoryName) {
        this.categoryName = categoryName;
        this.status = 1001;
    }
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
}
