package com.iceico.testportal.Service;

import java.util.List;

import com.iceico.testportal.Exceptions.ResourceNotFoundException;
import com.iceico.testportal.Model.Subject;

/**
 * @author puja
 *
 *
 *
 *         Modified date : 21/02/2020 Modified by : SAMEER KADGAYE
 */
public interface SubjectService {

	public void saveSubject(Subject subject);

	public void deleteSubject(Long subjectId);

	public List<Subject> getSubjectList();

	public Subject getSubjectById(Long subjectId) throws ResourceNotFoundException;

	public List<Subject> getActiveSubject();

	public Subject getBySubject(String subjectName);

}
