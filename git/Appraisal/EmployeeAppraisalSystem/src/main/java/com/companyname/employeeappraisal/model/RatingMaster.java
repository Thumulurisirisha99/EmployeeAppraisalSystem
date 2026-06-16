package com.companyname.employeeappraisal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_master_rating")
public class RatingMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Integer ratingId;

    @Column(name = "rating_name", nullable = false)
    private String ratingName;

    @Column(name = "rating_score", nullable = false)
    private Integer ratingScore;

    @Column(name = "status", nullable = false)
    private Integer status = 1001; 

    // Getters and Setters
    public Integer getRatingId() {
        return ratingId;
    }

    public void setRatingId(Integer ratingId) {
        this.ratingId = ratingId;
    }

    public String getRatingName() {
        return ratingName;
    }

    public void setRatingName(String ratingName) {
        this.ratingName = ratingName;
    }

    public Integer getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(Integer ratingScore) {
        this.ratingScore = ratingScore;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
