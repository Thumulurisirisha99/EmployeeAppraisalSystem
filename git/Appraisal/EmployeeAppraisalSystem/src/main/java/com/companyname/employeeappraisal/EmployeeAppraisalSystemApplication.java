package com.companyname.employeeappraisal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@EnableCaching
public class EmployeeAppraisalSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(EmployeeAppraisalSystemApplication.class, args);
	}
}
