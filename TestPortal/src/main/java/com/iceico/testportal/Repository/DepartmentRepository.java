package com.iceico.testportal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iceico.testportal.Model.Department;
/**
 * @author puja
 *
 */

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
