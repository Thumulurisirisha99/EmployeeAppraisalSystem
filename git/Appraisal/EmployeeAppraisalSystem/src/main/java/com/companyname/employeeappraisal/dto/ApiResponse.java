package com.companyname.employeeappraisal.dto;

public class ApiResponse {
    private String status;
    private String message;
    private Object data;

    public ApiResponse() {
    }

    public ApiResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String status, String message) {
        this(status, message, null);
    }

    public static ApiResponse success(Object data) {
        return new ApiResponse("success", "Operation successful", data);
    }

    public static ApiResponse success(String message, Object data) {
        return new ApiResponse("success", message, data);
    }

    // Added this method to support error responses with data
    public static ApiResponse error(String message, Object data) {
        return new ApiResponse("error", message, data);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse("error", message);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
