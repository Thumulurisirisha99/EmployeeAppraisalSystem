package com.companyname.employeeappraisal.globalException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.companyname.employeeappraisal.dto.ApiResponse;
import com.companyname.employeeappraisal.exception.InvalidCredentialsException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ApiResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
		ApiResponse response = ApiResponse.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

}
