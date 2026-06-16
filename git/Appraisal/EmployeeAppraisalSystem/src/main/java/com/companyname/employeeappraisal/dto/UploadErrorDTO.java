package com.companyname.employeeappraisal.dto;

import java.util.List;

public class UploadErrorDTO {

    private int rowNumber;
    private Integer employeeId;
    private List<String> error;

    public UploadErrorDTO(int rowNumber, Integer employeeId, List<String> error) {
        this.rowNumber = rowNumber;
        this.employeeId = employeeId;
        this.error = error;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public List<String> getError() {
        return error;
    }
}
