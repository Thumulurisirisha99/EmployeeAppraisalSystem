package com.companyname.employeeappraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.companyname.employeeappraisal.model.LoginRight;

@Repository
public interface LoginRightRepository extends JpaRepository<LoginRight, Integer> {
//    @Query("SELECT lr.module.moduleId FROM LoginRight lr WHERE lr.role.roleId IN :roleIds")
//    List<Integer> findModuleIdsByRoleIds(@Param("roleIds") List<Integer> roleIds);
	@Query("SELECT lr.module.moduleId FROM LoginRight lr WHERE lr.role.roleId IN :roleIds AND lr.status = 1001")
	List<Integer> findActiveModuleIdsByRoleIds(List<Integer> roleIds);


}
