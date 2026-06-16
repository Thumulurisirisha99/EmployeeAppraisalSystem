package com.companyname.employeeappraisal.dto;
public class FileUploadInfo {

    private final String fileName;
    private final String filePath;

    public FileUploadInfo(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }
}
