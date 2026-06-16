package com.companyname.employeeappraisal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.companyname.employeeappraisal.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
