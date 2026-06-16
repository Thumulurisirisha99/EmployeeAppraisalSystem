package com.companyname.employeeappraisal.dto;
public class SummaryDTO {
    private String title;
    private int completed;
    private int total;

    public SummaryDTO(String title, int completed, int total) {
        this.title = title;
        this.completed = completed;
        this.total = total;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCompleted() { return completed; }
    public void setCompleted(int completed) { this.completed = completed; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
}
