package com.iceico.testportal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iceico.testportal.Model.Subject;

/**
 * @author puja
 *
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
