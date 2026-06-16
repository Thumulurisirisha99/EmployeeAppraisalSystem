package com.companyname.employeeappraisal.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.companyname.employeeappraisal.dto.AppraisalStatusDTO;
import com.companyname.employeeappraisal.dto.AppraisalTeamMemberDTO;
import com.companyname.employeeappraisal.model.EmployeeLogin;

public interface MasterRepository extends JpaRepository<EmployeeLogin, Integer> {

	// login query

	@Query(value = "SELECT " + "l.EMPLOYEECODE AS employeeCode, " + "l.PASSWORD AS password, "
			+ "p.callname AS fullName, " + "d.name AS department, " + "prof.departmentid AS departmentid, "
			+ "des.name AS designation " + "FROM hclhrm_prod.tbl_employee_login l "
			+ "LEFT JOIN hclhrm_prod.tbl_employee_primary p ON p.employeesequenceno = l.employeecode "
			+ "LEFT JOIN hclhrm_prod.tbl_employee_professional_details prof ON prof.employeeid = p.employeeid "
			+ "LEFT JOIN hcladm_prod.tbl_department d ON d.departmentid = prof.departmentid "
			+ "LEFT JOIN hcladm_prod.tbl_designation des ON des.designationid = prof.designationid "
			+ "WHERE l.EMPLOYEECODE = :empCode "
			+ "AND l.PASSWORD = MD5(:password) AND p.status=1001", nativeQuery = true)
	EmployeeLoginDTO findByEmployeeCodeAndPassword(@Param("empCode") String empCode,
			@Param("password") String password);

	/**
	 * -- Employee unique sequence number, -- Employee name, -- Department name, --
	 * Designation name, -- Total number of goals for the employee,-- Latest goal
	 * status code,-- Human-readable status name for the latest goal, -- Last
	 * updated date of the latest goal
	 **/

	@Query(value = "SELECT " + "E.employeesequenceno AS employee_id, " + "E.callname AS employee_name, "
			+ "D.name AS department, " + "DES.name AS designation, " + "COUNT(G.goal_id) AS total_goal_count, "
			+ "LG.status AS last_goal_status, " + "MS.status_name AS status_name, " + "LG.lupdate AS last_updated_date "
			+

			"FROM hclhrm_prod.tbl_employee_professional_details P " +

			"INNER JOIN hclhrm_prod.tbl_employee_primary E " + "        ON E.employeeid = P.employeeid " +

			"LEFT JOIN hcladm_prod.tbl_department D " + "        ON D.departmentid = P.departmentid " +

			"LEFT JOIN hcladm_prod.tbl_designation DES " + "        ON DES.designationid = P.designationid "
			+ "LEFT JOIN performance_appraisal_system.tbl_performance_goals G "
			+ "       ON G.employeeid = E.employeesequenceno " + "      AND G.status IN (1002,1003,1004) "
			+ "LEFT JOIN ( " + "    SELECT t1.employeeid, t1.status, t1.lupdate "
			+ "    FROM performance_appraisal_system.tbl_performance_goals t1 " + "    INNER JOIN ( "
			+ "        SELECT employeeid, MAX(lupdate) AS max_lupdate "
			+ "        FROM performance_appraisal_system.tbl_performance_goals "
			+ "        WHERE status IN (1002,1003,1004) " + "        GROUP BY employeeid " + "    ) t2 "
			+ "      ON t1.employeeid = t2.employeeid " + "     AND t1.lupdate = t2.max_lupdate "
			+ ") LG ON LG.employeeid = E.employeesequenceno " +

			"LEFT JOIN performance_appraisal_system.tbl_master_status MS " + "       ON MS.status_code = LG.status " +

			"WHERE P.managerid IN ( " + "        SELECT employeeid " + "        FROM hclhrm_prod.tbl_employee_primary "
			+ "        WHERE employeesequenceno = :managerSeqNo " + "      ) "
			+ "  AND E.status IN (1001,1061,1092,1401) " +

			"GROUP BY " + "  E.employeesequenceno, " + "  E.callname, " + "  D.name, " + "  DES.name, "
			+ "  LG.status, " + "  MS.status_name, " + "  LG.lupdate " +

			"ORDER BY E.callname", nativeQuery = true)
	List<Map<String, Object>> findEmployeesWithGoals(@Param("managerSeqNo") int managerSeqNo);

	/**
	 * -- total team members, -- submitted goals count,-- pending approval goals, --
	 * approved goals, -- rejected goals,-- total goals
	 **/
//	@Query(value = "SELECT " + "COUNT(DISTINCT E.employeesequenceno) AS total_employees, "
//			+ "SUM(COALESCE(GS.submitted_goals, 0)) AS submitted_goals, "
//			+ "SUM(COALESCE(GS.pending_approval, 0)) AS pending_approval, "
//			+ "SUM(COALESCE(GS.approved_goals, 0)) AS approved_goals, "
//			+ "SUM(COALESCE(GS.rejected_goals, 0)) AS rejected_goals, "
//			+ "SUM(COALESCE(GS.total_goal_count, 0)) AS total_goal_count " +
//
//			"FROM hclhrm_prod.tbl_employee_professional_details P " + "INNER JOIN hclhrm_prod.tbl_employee_primary E "
//			+ "        ON E.employeeid = P.employeeid " +
//
//			"LEFT JOIN ( " + "    SELECT employeeid, "
//			+ "           SUM(CASE WHEN status = 1002 THEN 1 ELSE 0 END) AS submitted_goals, "
//			+ "           SUM(CASE WHEN status = 100 THEN 1 ELSE 0 END) AS pending_approval, "
//			+ "           SUM(CASE WHEN status = 1003 THEN 1 ELSE 0 END) AS approved_goals, "
//			+ "           SUM(CASE WHEN status = 1004 THEN 1 ELSE 0 END) AS rejected_goals, "
//			+ "           COUNT(goal_id) AS total_goal_count "
//			+ "    FROM performance_appraisal_system.tbl_performance_goals " + "    GROUP BY employeeid "
//			+ ") GS ON GS.employeeid = E.employeesequenceno " +
//
//			"WHERE P.managerid IN ( " + "        SELECT employeeid " + "        FROM hclhrm_prod.tbl_employee_primary "
//			+ "        WHERE employeesequenceno = :managerSeqNo " + "      ) "
//			+ "AND E.status IN (1001, 1061, 1092, 1401)", nativeQuery = true)
	@Query(value = "SELECT " + "COUNT(DISTINCT E.employeesequenceno) AS total_employees, "
			+ "SUM(COALESCE(GS.submitted_goals, 0)) AS submitted_goals, "
			+ "SUM(COALESCE(GS.pending_approval, 0)) AS pending_approval, "
			+ "SUM(COALESCE(GS.approved_goals, 0)) AS approved_goals, "
			+ "SUM(COALESCE(GS.document_uploaded_goals, 0)) AS document_uploaded_goals, "
			+ "SUM(COALESCE(GS.total_goal_count, 0)) AS total_goal_count " +

			"FROM hclhrm_prod.tbl_employee_professional_details P " + "INNER JOIN hclhrm_prod.tbl_employee_primary E "
			+ "   ON E.employeeid = P.employeeid " +

			"LEFT JOIN ( " + "    SELECT employeeid, "
			+ "           SUM(CASE WHEN status = 1002 THEN 1 ELSE 0 END) AS submitted_goals, "
			+ "           SUM(CASE WHEN status = 1002 THEN 1 ELSE 0 END) AS pending_approval, "
			+ "           SUM(CASE WHEN status = 1003 THEN 1 ELSE 0 END) AS approved_goals, "
			+ "           SUM(CASE WHEN status = 1004 THEN 1 ELSE 0 END) AS document_uploaded_goals, "
			+ "           SUM(CASE WHEN status = 1005 THEN 1 ELSE 0 END) AS Completed, "
			+ "           COUNT(goal_id) AS total_goal_count "
			+ "    FROM performance_appraisal_system.tbl_performance_goals " + "    GROUP BY employeeid "
			+ ") GS ON GS.employeeid = E.employeesequenceno " +

			"WHERE P.managerid IN ( " + "    SELECT employeeid " + "    FROM hclhrm_prod.tbl_employee_primary "
			+ "    WHERE employeesequenceno = :managerSeqNo " + ") "
			+ "AND E.status IN (1001, 1061, 1092, 1401)", nativeQuery = true)
	Map<String, Object> getManagerGoalSummary(@Param("managerSeqNo") int managerId);

	@Query(value = "SELECT " + "E.employeesequenceno AS employeeId, " + "E.callname AS fullName, "
			+ "D.name AS department, " + "DES.name AS designation " + "FROM hclhrm_prod.tbl_employee_primary E "
			+ "LEFT JOIN hclhrm_prod.tbl_employee_professional_details P " + "ON P.employeeid = E.employeeid "
			+ "LEFT JOIN hcladm_prod.tbl_department D " + "ON D.departmentid = P.departmentid "
			+ "LEFT JOIN hcladm_prod.tbl_designation DES " + "ON DES.designationid = P.designationid "
			+ "WHERE E.employeesequenceno = :employeeId", nativeQuery = true)
	Map<String, Object> getEmployeeDetails(@Param("employeeId") Integer employeeId);

	@Query(value = "SELECT b.employeesequenceno " + "FROM hclhrm_prod.tbl_employee_professional_details a "
			+ "LEFT JOIN hclhrm_prod.tbl_employee_primary b " + "       ON a.managerid = b.employeeid "
			+ "WHERE a.employeeid = ( " + "       SELECT emp.employeeid "
			+ "       FROM hclhrm_prod.tbl_employee_primary emp "
			+ "       WHERE emp.employeesequenceno = :empSeq )", nativeQuery = true)
	Integer findDirectManager(@Param("empSeq") Integer empSeq);

	@Query(value = "SELECT a.employeesequenceno " + "FROM hclhrm_prod.tbl_employee_primary a "
			+ "WHERE a.status = 1001 " + "  AND a.employeeid IN ( " + "        SELECT p.employeeid "
			+ "        FROM hclhrm_prod.tbl_employee_professional_details p " + "        WHERE p.managerid = ( "
			+ "            SELECT mgr.employeeid " + "            FROM hclhrm_prod.tbl_employee_primary mgr "
			+ "            WHERE mgr.employeesequenceno = 10423 " + "              AND mgr.status = 1001 ) "
			+ "     )", nativeQuery = true)
	List<Integer> findEmployeesUnder();

	@Query(value = "SELECT " + "emp.callname AS managerName,appr.approver_status as approverStatus, " + "CASE "
			+ "   WHEN appr.approver_status = 1003 THEN 'Submitted' "
			+ "   WHEN appr.approver_status = 1002 THEN 'In Progress' "
			+ "   WHEN appr.approver_status = 1001 THEN 'Pending' " + "   ELSE 'Unknown' " + "END AS status "
			+ "FROM performance_appraisal_system.tbl_employee_appraisal ea "
			+ "JOIN performance_appraisal_system.tbl_employee_appraisal_approvers appr "
			+ "    ON ea.appraisal_id = appr.appraisal_id " + "LEFT JOIN hclhrm_prod.tbl_employee_primary emp "
			+ "    ON appr.approver_emp_id = emp.employeesequenceno " + "WHERE ea.eligible_id = :eligibleId "
			+ "ORDER BY appr.approver_level ASC", nativeQuery = true)
	List<Object[]> getApproverStatus(@Param("eligibleId") Integer eligibleId);

	@Query(value = "SELECT " + " a.approver_status AS approverStatus, " + " e.employeesequenceno AS managerId, "
			+ " e.callname AS managerName, " + " CASE " + "   WHEN a.approver_status = 1001 THEN 'Pending' "
			+ "   WHEN a.approver_status = 1002 THEN 'In Progress' "
			+ "   WHEN a.approver_status = 1003 THEN 'Submitted' " + "   ELSE '--' " + " END AS status "
			+ "FROM performance_appraisal_system.tbl_employee_appraisal_approvers a "
			+ "JOIN hclhrm_prod.tbl_employee_primary e " + "     ON e.employeesequenceno = a.approver_emp_id "
			+ "WHERE a.appraisal_id = :appraisalId " + "  AND a.approver_role = 'M' "
			+ "ORDER BY a.approver_level ASC", nativeQuery = true)
	List<Map<String, Object>> findApproverStatuses(@Param("appraisalId") Integer appraisalId);

	@Query(value = "SELECT " + " a.approver_status AS approverStatus, " + " e.employeesequenceno AS hodId, "
			+ " e.callname AS hodName, " + " CASE " + "   WHEN a.approver_status = 1001 THEN 'Pending' "
			+ "   WHEN a.approver_status = 1002 THEN 'In Progress' "
			+ "   WHEN a.approver_status = 1003 THEN 'Submitted' " + "   ELSE '--' " + " END AS status "
			+ "FROM performance_appraisal_system.tbl_employee_appraisal_approvers a "
			+ "JOIN hclhrm_prod.tbl_employee_primary e " + "     ON e.employeesequenceno = a.approver_emp_id "
			+ "WHERE a.appraisal_id = :appraisalId " + "  AND a.approver_role = 'H' "
			+ "ORDER BY a.approver_level ASC", nativeQuery = true)
	List<Map<String, Object>> findHodApproverStatuses(@Param("appraisalId") Integer appraisalId);

//	@Query(value =
//			" SELECT DISTINCT " +
//			"   e.employeesequenceno AS empId, " +
//			"   e.callname, " +
//			"   d.name AS department, " +
//			"   p.departmentid AS departmentid, " +
//			"   des.name AS designation, " +
//			"   GEN.name AS gender, " +
//			"   ea.appraisal_id AS appraisalId, " +
//
//			"   CASE " +
//			"     WHEN g.max_status = 1002 THEN 'Submitted' " +
//			"     WHEN g.max_status = 1003 THEN 'Completed' " +
//			"     ELSE 'Pending' END AS goalStatus, " +
//
//			"   CASE " +
//			"     WHEN ea.status = 1002 THEN 'Submitted' " +
//			"     WHEN ea.status = 1003 THEN 'Completed' " +
//			"     ELSE 'Not Started' END AS appraisalStatus " +
//
//			" FROM performance_appraisal_system.tbl_appraisal_eligible_employees el " +
//
//			" INNER JOIN hclhrm_prod.tbl_employee_primary e " +
//			"   ON e.employeesequenceno = el.employeeid " +
//			"   AND e.status = 1001 " +
//
//			" INNER JOIN hclhrm_prod.tbl_employee_professional_details p " +
//			"   ON p.employeeid = e.employeeid " +
//
//			" LEFT JOIN performance_appraisal_system.tbl_employee_appraisal ea " +
//			"   ON ea.eligible_id = el.eligible_id " +
//
//			" LEFT JOIN performance_appraisal_system.tbl_master_financial_year fy " +
//			"   ON fy.financial_year_id = el.financial_year_id " +
//
//			" LEFT JOIN hcladm_prod.tbl_department d " +
//			"   ON d.departmentid = p.departmentid " +
//
//			" LEFT JOIN hcladm_prod.tbl_designation des " +
//			"   ON des.designationid = p.designationid " +
//
//			" LEFT JOIN hcladm_prod.tbl_gender GEN " +
//			"   ON e.gender = GEN.gender " +
//
//			" LEFT JOIN ( " +
//			"   SELECT employeeid, MAX(status) AS max_status " +
//			"   FROM performance_appraisal_system.tbl_performance_goals " +
//			"   GROUP BY employeeid " +
//			" ) g ON g.employeeid = el.employeeid " +
//
//			" WHERE el.employeeid IN (:subordinates) " +
//			"   AND el.status IN (1001,1003) ",
//			nativeQuery = true)
//
//
//	List<Map<String, Object>> findTeamAppraisalDetails(@Param("subordinates") List<Integer> subordinates);
	@Query(value = " SELECT DISTINCT " + " e.employeesequenceno AS empId, " + " e.callname, "
			+ " d.name AS department, " + " p.departmentid, " + " des.name AS designation, " + " GEN.name AS gender, "
			+ " ea.appraisal_id AS appraisalId, " +

			" CASE " + "   WHEN g.max_status = 1002 THEN 'Submitted' " + "   WHEN g.max_status = 1003 THEN 'Approved' "
			+ "   WHEN g.max_status = 1005 THEN 'Completed' " + "   ELSE 'Pending' END AS goalStatus, " +

			" CASE " + "   WHEN ea.status = 1002 THEN 'Submitted' " + "   WHEN ea.status = 1003 THEN 'Completed' "
			+ "   ELSE 'Not Started' END AS appraisalStatus " +

			" FROM performance_appraisal_system.tbl_appraisal_eligible_employees el " +

			" INNER JOIN hclhrm_prod.tbl_employee_primary e "
			+ " ON e.employeesequenceno = el.employeeid AND e.status = 1001 " +

			" INNER JOIN hclhrm_prod.tbl_employee_professional_details p " + " ON p.employeeid = e.employeeid " +

			" LEFT JOIN performance_appraisal_system.tbl_employee_appraisal ea "
			+ " ON ea.eligible_id = el.eligible_id " +

			" LEFT JOIN hcladm_prod.tbl_department d ON d.departmentid = p.departmentid "
			+ " LEFT JOIN hcladm_prod.tbl_designation des ON des.designationid = p.designationid "
			+ " LEFT JOIN hcladm_prod.tbl_gender GEN ON e.gender = GEN.gender " +

			" LEFT JOIN ( " + "   SELECT employeeid, MAX(status) AS max_status "
			+ "   FROM performance_appraisal_system.tbl_performance_goals " + "   GROUP BY employeeid "
			+ " ) g ON g.employeeid = el.employeeid " +

			" WHERE el.employeeid IN (:subordinates) " + " AND el.employeeid <> :loginId "
			+ " AND el.status IN (1001,1003) ", nativeQuery = true)

	List<Map<String, Object>> findTeamAppraisalDetails(@Param("subordinates") List<Integer> subordinates,
			@Param("loginId") Integer loginId);

	@Query(value = "SELECT " + "E.employeesequenceno AS employeeId, " + "E.employeeid AS empid, " + "PD.departmentid, "
			+ "E.callname AS callName, " + "P.DATEOFJOIN, " + "D.name AS departmentName, "
			+ "DES.name AS designationName, " + "GEN.name AS gender, "

			+ "CONCAT( " + "    FLOOR(TIMESTAMPDIFF(MONTH, P.DATEOFJOIN, "
			+ "        IF(HR.LASTDATE IS NULL OR HR.LASTDATE = '0000-00-00', CURDATE(), HR.LASTDATE)) / 12), "
			+ "    '.', " + "    MOD(TIMESTAMPDIFF(MONTH, P.DATEOFJOIN, "
			+ "        IF(HR.LASTDATE IS NULL OR HR.LASTDATE = '0000-00-00', CURDATE(), HR.LASTDATE)), 12)"
			+ ") AS currentExp, "

			+ "CONCAT( " + "    FLOOR(IFNULL(SUM(EED.EXPERIENCE), 0) / 12), " + "    '.', "
			+ "    MOD(IFNULL(SUM(EED.EXPERIENCE), 0), 12)" + ") AS previousExp, "

			+ "CONCAT( " + "    FLOOR((TIMESTAMPDIFF(MONTH, P.DATEOFJOIN, "
			+ "        IF(HR.LASTDATE IS NULL OR HR.LASTDATE = '0000-00-00', CURDATE(), HR.LASTDATE)) "
			+ "    + IFNULL(SUM(EED.EXPERIENCE), 0)) / 12), " + "    '.', "
			+ "    MOD((TIMESTAMPDIFF(MONTH, P.DATEOFJOIN, "
			+ "        IF(HR.LASTDATE IS NULL OR HR.LASTDATE = '0000-00-00', CURDATE(), HR.LASTDATE)) "
			+ "    + IFNULL(SUM(EED.EXPERIENCE), 0)), 12)" + ") AS totalExp, "

			+ "IFNULL(HQLOC.NAME, '') AS HQ, "

			+ "DATEDIFF( " + "    IFNULL(HR.LASTDATE, IFNULL(P.DATEOFRESIGNATION, CURDATE())), " + "    P.DATEOFJOIN "
			+ ") AS EmployeeDaysCount, "

			+ "IFNULL(HR.LASTDATE, 'NA') AS dateOfResignation "

			+ "FROM hclhrm_prod.tbl_employee_primary E "

			+ "INNER JOIN hclhrm_prod.tbl_employee_professional_details PD " + "     ON E.employeeid = PD.employeeid "

			+ "LEFT JOIN hclhrm_prod.tbl_employee_profile P " + "     ON E.employeeid = P.employeeid "

			+ "LEFT JOIN ( " + "    SELECT EMPLOYEEID, MAX(LASTWORKINGDATE) AS LASTDATE "
			+ "    FROM hclhrm_prod.tbl_employee_hractions " + "    GROUP BY EMPLOYEEID "
			+ ") HR ON E.employeeid = HR.EMPLOYEEID "

			+ "LEFT JOIN hclhrm_prod.tbl_employee_experience_details EED " + "     ON E.employeeid = EED.EMPLOYEEID "

			+ "LEFT JOIN hcllcm_prod.tbl_location HQLOC " + "     ON PD.WORKLOCATIONID = HQLOC.LOCATIONID "

			+ "LEFT JOIN hcladm_prod.tbl_department D " + "     ON PD.departmentid = D.departmentid "

			+ "LEFT JOIN hcladm_prod.tbl_designation DES " + "     ON PD.designationid = DES.designationid "

			+ "LEFT JOIN hcladm_prod.tbl_gender GEN " + "     ON E.gender = GEN.gender "

			+ "WHERE E.employeesequenceno = :employeeid " + "AND E.status IN (1001, 1061, 1092, 1401) "

			+ "GROUP BY " + "E.employeesequenceno, " + "E.employeeid, " + "PD.departmentid, " + "E.callname, "
			+ "P.DATEOFJOIN, " + "HR.LASTDATE, " + "HQLOC.NAME, " + "D.name, " + "DES.name, "
			+ "GEN.name", nativeQuery = true)
	List<Map<String, Object>> findEmployeeDetailsByEmployeeSeqNo(@Param("employeeid") Long employeeid);

//	@Query(value = "select qualificationid,yearofpassing from hclhrm_prod.tbl_employee_education_details where employeeid=:empId", nativeQuery = true)
//	List<Map<String, Object>> findEducationDetailsOfEmployee(@Param("empId") Integer empId);

	@Query(value = "SELECT qualificationid, yearofpassing " + "FROM hclhrm_prod.tbl_employee_education_details "
			+ "WHERE employeeid = :empId " + "ORDER BY yearofpassing DESC", nativeQuery = true)
	List<Map<String, Object>> findEducationDetailsOfEmployee(@Param("empId") Integer empId);

	@Query(value = "CALL `procedure`.GetAllSubordinates(:manager_seq)", nativeQuery = true)
	List<Integer> getAllSubordinates(@Param("manager_seq") Integer managerSeqNo);

	@Query(value = "SELECT f.businessunitid " + "FROM hclhrm_prod.tbl_employee_primary a "
			+ "LEFT JOIN hcladm_prod.tbl_businessunit f ON f.businessunitid = a.companyid "
			+ "WHERE a.employeesequenceno = :empId AND a.status = 1001", nativeQuery = true)
	Integer findDivisionByEmployeeId(@Param("empId") Integer empId);

//	@Query(value = "SELECT businessunitid FROM hcladm_prod.tbl_businessunit WHERE LOWER(name) = LOWER(:businessUnitName) LIMIT 1", nativeQuery = true)
//	Integer findBusinessUnitIdByName(@Param("businessUnitName") String businessUnitName);

	@Query(value = "SELECT employeesequenceno " + "FROM hclhrm_prod.tbl_employee_primary " + "WHERE status = 1001 "
			+ "AND employeesequenceno IN (:empIds)", nativeQuery = true)
	List<Integer> findActiveEmployeeIds(@Param("empIds") Set<Integer> empIds);

	@Query(value = "SELECT companyid " + "FROM hclhrm_prod.tbl_employee_primary " + "WHERE employeesequenceno = :empId "
			+ "AND status = 1001", nativeQuery = true)
	Integer findDivisionIdByEmployeeId(@Param("empId") Integer empId);

	@Query(value = "SELECT prof.departmentid " + "FROM hclhrm_prod.tbl_employee_login l "
			+ "LEFT JOIN hclhrm_prod.tbl_employee_primary p ON p.employeesequenceno = l.employeecode "
			+ "LEFT JOIN hclhrm_prod.tbl_employee_professional_details prof ON prof.employeeid = p.employeeid "
			+ "WHERE l.EMPLOYEECODE = :empCode AND p.status = 1001", nativeQuery = true)
	Integer findDepartmentIdByEmployeeCode(@Param("empCode") String empCode);

//	@Query(value = "SELECT DISTINCT " + " e.employeesequenceno AS employeeId, " + " e.callname AS employeeName, "
//			+ " d.name AS department, " + " des.name AS designation, " + " a.appraisal_id AS appraisalId "
//			+ "FROM hclhrm_prod.tbl_employee_primary e "
//			+ "INNER JOIN hclhrm_prod.tbl_employee_professional_details p ON p.employeeid = e.employeeid "
//			+ "INNER JOIN performance_appraisal_system.tbl_appraisal_eligible_employees el "
//			+ "   ON el.employeeid = e.employeesequenceno "
//			+ "LEFT JOIN hcladm_prod.tbl_department d ON d.departmentid = p.departmentid "
//			+ "LEFT JOIN hcladm_prod.tbl_designation des ON des.designationid = p.designationid "
//			+ "LEFT JOIN performance_appraisal_system.tbl_employee_appraisal a "
//			+ "   ON a.employeeid = e.employeesequenceno " + "  AND a.status IN (1002,1003) "
//			+ "LEFT JOIN performance_appraisal_system.tbl_master_financial_year fy "
//			+ "   ON fy.financial_year_id = a.financial_year_id " + "WHERE e.employeesequenceno IN (:subordinates) "
//			+ "  AND e.status = 1001 ", nativeQuery = true)
//	List<Map<String, Object>> findTeamAppraisalEmployees(@Param("subordinates") List<Integer> subordinates);
//	@Query(value = "SELECT DISTINCT " +
//	        " e.employeesequenceno AS employeeId, " +
//	        " e.callname AS employeeName, " +
//	        " d.name AS department, " +
//	        " des.name AS designation, " +
//	        " CASE WHEN fp.appraisal_id IS NOT NULL THEN 'YES' ELSE 'NO' END AS appraisalPresent, " +
//	        " a.appraisal_id AS appraisalId, " +
//	        " fp.created_on AS updated_on, " +
//	        " COALESCE(peerFeedback.submittedCount, 0) AS peerSubmitted, " +
//	        " COALESCE(peerFeedback.requestedCount, 0) AS peerRequested " +
//	        " COALESCE( (SELECT MAX(responded_on)   FROM employee_360_feedback_participant p        WHERE p.appraisal_id = a.appraisal_id       AND p.manager_id = :managerId       AND p.participant_status IN (1002,1003)       ), NULL   ) AS lastUpdated"
//	        "FROM hclhrm_prod.tbl_employee_primary e " +
//	        "INNER JOIN hclhrm_prod.tbl_employee_professional_details p ON p.employeeid = e.employeeid " +
//	        "INNER JOIN performance_appraisal_system.tbl_appraisal_eligible_employees el " +
//	        "   ON el.employeeid = e.employeesequenceno " +
//	        "LEFT JOIN hcladm_prod.tbl_department d ON d.departmentid = p.departmentid " +
//	        "LEFT JOIN hcladm_prod.tbl_designation des ON des.designationid = p.designationid " +
//	        "LEFT JOIN performance_appraisal_system.tbl_employee_appraisal a " +
//	        "   ON a.employeeid = e.employeesequenceno AND a.status IN (1002, 1003) " +
//	        "LEFT JOIN performance_appraisal_system.tbl_master_financial_year fy " +
//	        "   ON fy.financial_year_id = a.financial_year_id " +
//	        "LEFT JOIN employee_360_feedback_participant fp " +
//	        "   ON fp.appraisal_id = a.appraisal_id " +
//	        "LEFT JOIN ( " +
//	        "   SELECT appraisal_id, " +
//	        "          SUM(CASE WHEN participant_status = 1002 THEN 1 ELSE 0 END) AS requestedCount, " +
//	        "          SUM(CASE WHEN participant_status = 1003 THEN 1 ELSE 0 END) AS submittedCount " +
//	        "   FROM employee_360_feedback_participant " +
//	        "   GROUP BY appraisal_id " +
//	        ") peerFeedback ON peerFeedback.appraisal_id = a.appraisal_id " +
//
//	        "WHERE e.employeesequenceno IN (:subordinates) " +
//	        "  AND e.status = 1001",
//	        nativeQuery = true)
//	List<Map<String, Object>> findTeamAppraisalEmployees(@Param("subordinates") List<Integer> subordinates);
	@Query(value = "SELECT DISTINCT " + " e.employeesequenceno AS employeeId, " + " e.callname AS employeeName, "
			+ " d.name AS department, " + " des.name AS designation, "
			+ " CASE WHEN fp.appraisal_id IS NOT NULL THEN 'YES' ELSE 'NO' END AS appraisalPresent, "
			+ " a.appraisal_id AS appraisalId, " + " fp.created_on AS updated_on, "
			+ " COALESCE(peerFeedback.submittedCount, 0) AS peerSubmitted, "
			+ " COALESCE(peerFeedback.requestedCount, 0) AS peerRequested, " + " COALESCE( (SELECT MAX(p.responded_on) "
			+ "            FROM employee_360_feedback_participant p "
			+ "            WHERE p.appraisal_id = a.appraisal_id " + "              AND p.manager_id = :managerId "
			+ "              AND p.participant_status IN (1002,1003) " + "           ), NULL) AS lastUpdated "
			+ "FROM hclhrm_prod.tbl_employee_primary e "
			+ "INNER JOIN hclhrm_prod.tbl_employee_professional_details p ON p.employeeid = e.employeeid "
			+ "INNER JOIN performance_appraisal_system.tbl_appraisal_eligible_employees el "
			+ "   ON el.employeeid = e.employeesequenceno "
			+ "LEFT JOIN hcladm_prod.tbl_department d ON d.departmentid = p.departmentid "
			+ "LEFT JOIN hcladm_prod.tbl_designation des ON des.designationid = p.designationid "
			+ "LEFT JOIN performance_appraisal_system.tbl_employee_appraisal a "
			+ "   ON a.employeeid = e.employeesequenceno AND a.status IN (1002, 1003) "
			+ "LEFT JOIN performance_appraisal_system.tbl_master_financial_year fy "
			+ "   ON fy.financial_year_id = a.financial_year_id " + "LEFT JOIN employee_360_feedback_participant fp "
			+ "   ON fp.appraisal_id = a.appraisal_id " + "LEFT JOIN ( " + "   SELECT appraisal_id, "
			+ "          SUM(CASE WHEN participant_status = 1002 THEN 1 ELSE 0 END) AS requestedCount, "
			+ "          SUM(CASE WHEN participant_status = 1003 THEN 1 ELSE 0 END) AS submittedCount "
			+ "   FROM employee_360_feedback_participant " + "   GROUP BY appraisal_id "
			+ ") peerFeedback ON peerFeedback.appraisal_id = a.appraisal_id "
			+ "WHERE e.employeesequenceno IN (:subordinates) " + "  AND e.status = 1001", nativeQuery = true)
	List<Map<String, Object>> findTeamAppraisalEmployees(@Param("subordinates") List<Integer> subordinates,
			@Param("managerId") Integer managerId); // <-- add this param

	@Query(value = "SELECT DISTINCT " + "e.employeesequenceno AS employeeId, " + "e.callname AS employeeName, "
			+ "dept.name AS department, " + "des.name AS designation, " + "j.email AS emailId, "
			+ "DATE_FORMAT(p.created_on, '%d-%m-%Y') AS requestedDate, "
			+ "IFNULL(p.participant_status, 0) AS actionStatus, " + "p.participant_id AS participantId "
			+ "FROM hclhrm_prod.tbl_employee_primary e "
			+ "INNER JOIN hclhrm_prod.tbl_employee_professional_details pr " + "    ON pr.employeeid = e.employeeid "
			+ "LEFT JOIN hcladm_prod.tbl_designation des " + "    ON des.designationid = pr.designationid "
			+ "LEFT JOIN hcladm_prod.tbl_department dept " + "    ON dept.departmentid = pr.departmentid "
			+ "LEFT JOIN hclhrm_prod.tbl_employee_professional_contact j " + "    ON j.employeeid = e.employeeid "
			+ "LEFT JOIN performance_appraisal_system.employee_360_feedback_participant p "
			+ "    ON p.reviewer_employee_id = e.employeesequenceno " + "    AND p.appraisal_id = :appraisalId "
			+ "    AND p.manager_id = :managerId " + // ✅ IMPORTANT
			"WHERE e.employeesequenceno IN (:subordinates) " + "AND e.employeesequenceno <> :selfSeq "
			+ "AND e.employeesequenceno <> :managerSeq " + "AND e.status = 1001", nativeQuery = true)
	List<Map<String, Object>> findTeamMembersExceptSelfAndManagerMap(@Param("subordinates") List<Integer> subordinates,
			@Param("selfSeq") Integer selfSeq, @Param("managerSeq") Integer managerSeq,
			@Param("appraisalId") Integer appraisalId, @Param("managerId") Integer managerId);

	@Query(value = "SELECT e.employeesequenceno AS employeeId, e.callname AS employeeName, "
			+ "d.name AS department, des.name AS designation " + "FROM hclhrm_prod.tbl_employee_primary e "
			+ "INNER JOIN hclhrm_prod.tbl_employee_professional_details p ON p.employeeid = e.employeeid "
			+ "LEFT JOIN hcladm_prod.tbl_department d ON d.departmentid = p.departmentid "
			+ "LEFT JOIN hcladm_prod.tbl_designation des ON des.designationid = p.designationid "
			+ "WHERE e.employeesequenceno IN (:subordinates)", nativeQuery = true)
	List<AppraisalTeamMemberDTO> findTeamMembers(@Param("subordinates") List<Integer> subordinates);

//	@Query(value = "SELECT DISTINCT " + " e.employeesequenceno AS empId, " + " e.callname, " + " d.name AS department, "
//			+ " p.departmentid AS departmentid, " + " des.name AS designation, " + " GEN.name AS gender, "
//			+ " ea.appraisal_id AS appraisalId, "
//
//			+ " CASE " + "   WHEN g.max_status = 1002 THEN 'Submitted' "
//			+ "   WHEN g.max_status = 1003 THEN 'Completed' " + "   ELSE '--' END AS goalStatus, "
//
//			+ " CASE " + "   WHEN ea.status = 1001 THEN 'In Progress' " + "   WHEN ea.status = 1002 THEN 'Submitted' "
//			+ "   WHEN ea.status = 1003 THEN 'Completed' " + "   ELSE '--' END AS appraisalStatus, "
//
//			+ " CASE " + "   WHEN ea.acknowledgment_flag = 'N' THEN '--' " + "   ELSE 'Acknowledged' "
//			+ " END AS Status "
//
//			+ " FROM hclhrm_prod.tbl_employee_primary e "
//
//			+ " INNER JOIN hclhrm_prod.tbl_employee_professional_details p " + "   ON p.employeeid = e.employeeid "
//
//			+ " INNER JOIN performance_appraisal_system.tbl_appraisal_eligible_employees el "
//			+ "   ON el.employeeid = e.employeesequenceno "
//
//			+ " LEFT JOIN performance_appraisal_system.tbl_employee_appraisal ea "
//			+ "   ON ea.eligible_id = el.eligible_id "
//			+ " LEFT JOIN performance_appraisal_system.tbl_master_financial_year mfy "
//			+ "   ON mfy.financial_year_id = el.financial_year_id "
//
//			+ " LEFT JOIN hcladm_prod.tbl_department d " + "   ON d.departmentid = p.departmentid "
//
//			+ " LEFT JOIN hcladm_prod.tbl_designation des " + "   ON des.designationid = p.designationid "
//
//			+ " LEFT JOIN hcladm_prod.tbl_gender GEN " + "   ON e.gender = GEN.gender "
//
//			+ " LEFT JOIN ( " + "   SELECT employeeid, MAX(status) AS max_status "
//			+ "   FROM performance_appraisal_system.tbl_performance_goals " + "   GROUP BY employeeid "
//			+ " ) g ON g.employeeid = e.employeesequenceno "
//
//			+ " WHERE e.status = 1001 " + " AND el.status IN (1001,1003) "
//			+ " AND mfy.year = :financialYear ", nativeQuery = true)
//	List<Map<String, Object>> findTeamAppraisalDetails(@Param("financialYearId") Integer financialYearId,@Param("quarterId") Integer quarterId);
	@Query(value = "SELECT DISTINCT " + " e.employeesequenceno AS empId, " + " e.callname, " + " d.name AS department, "
			+ " p.departmentid AS departmentid, " + " des.name AS designation, " + " GEN.name AS gender, "
			+ " ea.appraisal_id AS appraisalId,el.eligible_id as eligibleId, " +

			" CASE " + "   WHEN g.max_status = 1002 THEN 'Submitted' " + "   WHEN g.max_status = 1003 THEN 'Approved' "
			+ "   WHEN g.max_status = 1005 THEN 'Completed' " + "   ELSE '--' END AS goalStatus, " +

			" CASE " + "   WHEN ea.status = 1001 THEN 'In Progress' " + "   WHEN ea.status = 1002 THEN 'Submitted' "
			+ "   WHEN ea.status = 1003 THEN 'Completed' " + "   ELSE '--' END AS appraisalStatus, " +

//			" CASE " + "   WHEN ea.acknowledgment_flag = 'N' THEN '--' " + "   ELSE 'Acknowledged' " + " END AS Status "
			" CASE " + "   WHEN ea.acknowledgment_flag = 'N' THEN '--' "
			+ "   WHEN ea.acknowledgment_flag = 'K' THEN 'Acknowledgment' " + "   ELSE '--' " + " END AS Status " +

			" FROM hclhrm_prod.tbl_employee_primary e " +

			" INNER JOIN hclhrm_prod.tbl_employee_professional_details p " + "   ON p.employeeid = e.employeeid " +

			" INNER JOIN performance_appraisal_system.tbl_appraisal_eligible_employees el "
			+ "   ON el.employeeid = e.employeesequenceno " +

			" LEFT JOIN performance_appraisal_system.tbl_employee_appraisal ea "
			+ "   ON ea.eligible_id = el.eligible_id " +

			" LEFT JOIN performance_appraisal_system.tbl_master_financial_year mfy "
			+ "   ON mfy.financial_year_id = el.financial_year_id "
			+ " LEFT JOIN performance_appraisal_system.tbl_master_quarter q " + "   ON q.quarter_id = el.quarter_id " +

			" LEFT JOIN hcladm_prod.tbl_department d " + "   ON d.departmentid = p.departmentid " +

			" LEFT JOIN hcladm_prod.tbl_designation des " + "   ON des.designationid = p.designationid " +

			" LEFT JOIN hcladm_prod.tbl_gender GEN " + "   ON e.gender = GEN.gender " +

			" LEFT JOIN ( " + "   SELECT employeeid, MAX(status) AS max_status "
			+ "   FROM performance_appraisal_system.tbl_performance_goals " + "   GROUP BY employeeid "
			+ " ) g ON g.employeeid = e.employeesequenceno " +

			" WHERE e.status = 1001 " + " AND el.status IN (1001,1003) "
			+ " AND mfy.financial_year_id = :financialYearId "
			+ " AND (:quarterId IS NULL OR q.quarter_id = :quarterId) ", nativeQuery = true)
	List<Map<String, Object>> findTeamAppraisalDetails(@Param("financialYearId") Integer financialYearId,
			@Param("quarterId") Integer quarterId);

	@Query(value = "SELECT DISTINCT " + " e.employeesequenceno AS empId, " + " e.callname, " + " d.name AS department, "
			+ " p.departmentid AS departmentid, " + " des.name AS designation, " + " GEN.name AS gender, "
			+ " ea.appraisal_id AS appraisalId, " +

			" CASE " + "   WHEN g.max_status = 1001 THEN 'In Progress' "
			+ "   WHEN g.max_status = 1002 THEN 'Submitted' " + "   WHEN g.max_status = 1003 THEN 'Approved' "
			+ "   WHEN g.max_status = 1005 THEN 'Completed' " + "   ELSE '--' END AS goalStatus, " +

			" CASE " + "   WHEN ea.status = 1001 THEN 'In Progress' " + "   WHEN ea.status = 1002 THEN 'Submitted' "
			+ "   WHEN ea.status = 1003 THEN 'Completed' " + "   ELSE '--' END AS appraisalStatus, " +

//			" CASE " + "   WHEN ea.acknowledgment_flag = 'N' THEN '--' "
//			+ "   ELSE 'Acknowledged' END AS acknowledgmentStatus " +
			" CASE " + "   WHEN ea.acknowledgment_flag = 'N' THEN '--' "
			+ "   WHEN ea.acknowledgment_flag = 'K' THEN 'Acknowledgment' " + "   ELSE '--' " + " END AS Status " +

			" FROM hclhrm_prod.tbl_employee_primary e " +

			" INNER JOIN hclhrm_prod.tbl_employee_professional_details p " + "   ON p.employeeid = e.employeeid " +

			" INNER JOIN performance_appraisal_system.tbl_appraisal_eligible_employees el "
			+ "   ON el.employeeid = e.employeesequenceno " +

			" LEFT JOIN performance_appraisal_system.tbl_employee_appraisal ea "
			+ "   ON ea.eligible_id = el.eligible_id "
			+ " LEFT JOIN performance_appraisal_system.tbl_master_financial_year mfy "
			+ "   ON mfy.financial_year_id = el.financial_year_id " +

			" LEFT JOIN hcladm_prod.tbl_department d " + "   ON d.departmentid = p.departmentid " +

			" LEFT JOIN hcladm_prod.tbl_designation des " + "   ON des.designationid = p.designationid " +

			" LEFT JOIN hcladm_prod.tbl_gender GEN " + "   ON e.gender = GEN.gender " +

			" LEFT JOIN ( " + "   SELECT employeeid, MAX(status) AS max_status "
			+ "   FROM performance_appraisal_system.tbl_performance_goals " + "   GROUP BY employeeid "
			+ " ) g ON g.employeeid = e.employeesequenceno " +

			" WHERE e.employeesequenceno IN (:subordinates) " + " AND e.status = 1001 " + " AND el.status = 1003 "
			+ " AND mfy.financial_year_id = :financialYearId", nativeQuery = true)
	List<Map<String, Object>> findTeamEmployeeAppraisalDetails(@Param("subordinates") List<Integer> subordinates,
			@Param("financialYearId") Integer financialYearId);

	@Query(value = "SELECT e.employeesequenceno AS employeeId, " + "e.callname AS employeeName, "
			+ "d.name AS department, " + "CASE " + "  WHEN ea.status = 1001 THEN 'In Progress' "
			+ "  WHEN ea.status = 1002 THEN 'Submitted' " + "  WHEN ea.status = 1003 THEN 'Approved' "
			+ "  WHEN ea.status = 1005 THEN 'Completed' " + "  ELSE '--' END AS selfAppraisal, "
			+ "GROUP_CONCAT(CONCAT('Level ', a.approver_level, ': ', approver.callname, ' (', " + "  CASE "
			+ "    WHEN a.approver_status = 1001 THEN 'In Progress' "
			+ "    WHEN a.approver_status = 1002 THEN 'Submitted' "
			+ "    WHEN a.approver_status = 1003 THEN 'Completed' "
			+ "    ELSE '--' END, ')') ORDER BY a.approver_level ASC SEPARATOR ', ') AS approverStatus "
			+ "FROM hclhrm_prod.tbl_employee_primary e "
			+ "INNER JOIN hclhrm_prod.tbl_employee_professional_details p ON p.employeeid = e.employeeid "
			+ "INNER JOIN tbl_appraisal_eligible_employees el ON el.employeeid = e.employeesequenceno "
			+ "LEFT JOIN tbl_employee_appraisal ea ON ea.eligible_id = el.eligible_id "
			+ "LEFT JOIN tbl_employee_appraisal_approvers a ON a.appraisal_id = ea.appraisal_id "
			+ "LEFT JOIN hclhrm_prod.tbl_employee_primary approver ON approver.employeesequenceno = a.approver_emp_id "
			+ "LEFT JOIN hcladm_prod.tbl_department d ON d.departmentid = p.departmentid "
			+ "LEFT JOIN tbl_master_financial_year mfy ON mfy.financial_year_id = el.financial_year_id "
			+ "LEFT JOIN tbl_master_quarter q ON q.quarter_id = el.quarter_id " + "WHERE e.status = 1001 "
			+ "AND el.status IN (1001, 1003) " + "AND mfy.financial_year_id = :financialYear "
			+ "AND (:quarter IS NULL OR q.quarter_id = :quarter) "
			+ "GROUP BY e.employeesequenceno, e.callname, d.name, ea.status", nativeQuery = true)
	List<AppraisalStatusDTO> getAppraisalStatus(@Param("financialYear") String financialYear,
			@Param("quarter") String quarter);

	@Query(value = "SELECT e.employeesequenceno AS employeeId, " + "       e.callname AS employeeName, "
			+ "       d.name AS department, " +

			"       CASE " + "         WHEN ea.status = 1001 THEN 'In Progress' "
			+ "         WHEN ea.status = 1002 THEN 'Submitted' " + "         WHEN ea.status = 1003 THEN 'Approved' "
			+ "         WHEN ea.status = 1005 THEN 'Completed' " + "         ELSE '--' END AS selfAppraisal, " +

			"       GROUP_CONCAT(CONCAT('Level ', a.approver_level, ': ', approver.callname, ' (', " + "         CASE "
			+ "           WHEN a.approver_status = 1001 THEN 'In Progress' "
			+ "           WHEN a.approver_status = 1002 THEN 'Submitted' "
			+ "           WHEN a.approver_status = 1003 THEN 'Completed' "
			+ "           ELSE '--' END, ')') ORDER BY a.approver_level ASC SEPARATOR ', ') AS approverStatus, " +

			"       COALESCE(MAX(a.updated_date), ea.updated_date, ea.created_date) AS lastActivityDate, " +

			"       MIN(CASE WHEN a.approver_status = 1001 THEN a.approver_level END) AS pendingLevel " +

			"FROM hclhrm_prod.tbl_employee_primary e "
			+ "INNER JOIN hclhrm_prod.tbl_employee_professional_details p ON p.employeeid = e.employeeid "
			+ "INNER JOIN tbl_appraisal_eligible_employees el ON el.employeeid = e.employeesequenceno "
			+ "LEFT JOIN tbl_employee_appraisal ea ON ea.eligible_id = el.eligible_id "
			+ "LEFT JOIN tbl_employee_appraisal_approvers a ON a.appraisal_id = ea.appraisal_id "
			+ "LEFT JOIN hclhrm_prod.tbl_employee_primary approver ON approver.employeesequenceno = a.approver_emp_id "
			+ "LEFT JOIN hcladm_prod.tbl_department d ON d.departmentid = p.departmentid "
			+ "LEFT JOIN tbl_master_financial_year mfy ON mfy.financial_year_id = el.financial_year_id "
			+ "LEFT JOIN tbl_master_quarter q ON q.quarter_id = el.quarter_id " +

			"WHERE e.status = 1001 " + "AND el.status IN (1001, 1003) " + "AND mfy.financial_year_id = :financialYear "
			+ "AND (:quarter IS NULL OR q.quarter_id = :quarter) " +

			"GROUP BY e.employeesequenceno, e.callname, d.name, ea.status", nativeQuery = true)
	List<Object[]> getAppraisalStavtusRaw(@Param("financialYear") String financialYear,
			@Param("quarter") String quarter);
	
	@Query(value =
	        "SELECT emp.employeesequenceno AS employeeid, " +
	        "COALESCE(used_leaves.USEDLEAVECOUNT, 0) AS used, " +
	        "COALESCE(lop_data.LOPCOUNT, 0) AS lop " +

	        "FROM hclhrm_prod.tbl_employee_primary emp " +

	        "LEFT JOIN ( " +
	        "   SELECT r.employeeid, SUM(r.leave_count) AS USEDLEAVECOUNT " +
	        "   FROM hclhrm_prod_others.tbl_emp_leave_report r " +
	        "   WHERE r.manager_status IN ('A','P') " +
	        "     AND r.leave_type IN ('CL','EL','SL') " +
	        "     AND r.leaveon BETWEEN :fromDate AND :toDate " +
	        "   GROUP BY r.employeeid " +
	        ") used_leaves ON used_leaves.employeeid = emp.employeesequenceno " +

	        "LEFT JOIN ( " +
	        "   SELECT pp.employeesequenceno, SUM(lop.lopcount) AS LOPCOUNT " +
	        "   FROM hclhrm_prod.tbl_employee_lop lop " +
	        "   LEFT JOIN hclhrm_prod.tbl_employee_primary pp " +
	        "       ON pp.employeeid = lop.employeeid " +
	        "   WHERE lop.loptransactionid BETWEEN " +
	        "       DATE_FORMAT(:fromDate,'%Y%m') AND DATE_FORMAT(:toDate,'%Y%m') " +
	        "   GROUP BY pp.employeesequenceno " +
	        ") lop_data ON lop_data.employeesequenceno = emp.employeesequenceno " +

	        "WHERE emp.employeesequenceno = :empSeqNo",
	        nativeQuery = true)
	List<Map<String, Object>> findUsedAndLopBetweenDates(
	        @Param("empSeqNo") Long empSeqNo,
	        @Param("fromDate") LocalDate fromDate,
	        @Param("toDate") LocalDate toDate);

}
