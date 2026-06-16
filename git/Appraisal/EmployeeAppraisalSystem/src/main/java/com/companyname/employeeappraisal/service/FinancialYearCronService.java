package com.companyname.employeeappraisal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class FinancialYearCronService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Scheduled(cron = "0 0 0 * * ?")
	@Transactional
	public void updateFinancialYearStatus() {

		System.out.println("Financial Year Cron Started...");

		jdbcTemplate.update(
				"UPDATE performance_appraisal_system.tbl_master_financial_year SET status = 1001 WHERE CURDATE() BETWEEN start_date AND end_date");
		jdbcTemplate.update(
				"UPDATE performance_appraisal_system.tbl_master_financial_year SET status = 1002 WHERE CURDATE() > end_date");

		StringBuffer insertQuery = new StringBuffer();

		insertQuery.append(" INSERT INTO performance_appraisal_system.tbl_master_financial_year ");
		insertQuery.append(" (financial_year_code, year, start_date, end_date, status) ");

		insertQuery.append(" SELECT * FROM ( ");
		insertQuery.append("   SELECT ");
		insertQuery.append("   CONCAT(YEAR(CURDATE()), '-', YEAR(CURDATE()) + 1), ");
		insertQuery.append("   YEAR(CURDATE()), ");
		insertQuery.append("   STR_TO_DATE(CONCAT(YEAR(CURDATE()), '-04-01'), '%Y-%m-%d'), ");
		insertQuery.append("   STR_TO_DATE(CONCAT(YEAR(CURDATE()) + 1, '-03-31'), '%Y-%m-%d'), ");
		insertQuery.append("   1001 ");
		insertQuery.append(" ) x ");

		insertQuery.append(" WHERE NOT EXISTS ( ");
		insertQuery.append("   SELECT 1 FROM performance_appraisal_system.tbl_master_financial_year ");
		insertQuery.append("   WHERE start_date = ");
		insertQuery.append("     STR_TO_DATE(CONCAT(YEAR(CURDATE()), '-04-01'), '%Y-%m-%d') ");
		insertQuery.append("   AND end_date = ");
		insertQuery.append("     STR_TO_DATE(CONCAT(YEAR(CURDATE()) + 1, '-03-31'), '%Y-%m-%d') ");
		insertQuery.append(" ) ");

		jdbcTemplate.update(insertQuery.toString());

		System.out.println("Financial Year Cron Completed...");
	}

}
