package com.companyname.employeeappraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.companyname.employeeappraisal.model.AppModule;

@Repository
public interface AppModuleRepository extends JpaRepository<AppModule, Integer> {

	List<AppModule> findByModuleIdInAndStatus(List<Integer> moduleIds, int status);

	List<AppModule> findByStatus(int i);

}
