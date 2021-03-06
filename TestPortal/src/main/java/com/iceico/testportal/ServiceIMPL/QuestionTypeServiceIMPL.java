package com.iceico.testportal.ServiceIMPL;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iceico.testportal.Exceptions.ResourceNotFoundException;
import com.iceico.testportal.Model.QuestionType;
import com.iceico.testportal.Repository.QuestionTypeRepository;
import com.iceico.testportal.Service.QuestionTypeService;

/**
 * @author puja
 *
 *
 *         Modified date : 21/02/2020 Modified by : SAMEER KADGAYE
 */

@Service
@Transactional
public class QuestionTypeServiceIMPL implements QuestionTypeService {

	@Autowired
	private QuestionTypeRepository questionTypeRepository;

	@Autowired
	private EntityManager entityManager;

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	@Override
	public void saveQuestionType(QuestionType questionType) {
		this.questionTypeRepository.save(questionType);
	}

	@Override
	public void deleteQuestionType(Long questionTypeId) {
		this.questionTypeRepository.deleteById(questionTypeId);
	}

	@Override
	public List<QuestionType> getQuestionTypeList() {
		return this.questionTypeRepository.findAll();
	}

	@Override
	public QuestionType getQuestionTypeById(Long questionTypeId) throws ResourceNotFoundException {
		return this.questionTypeRepository.findById(questionTypeId)
				.orElseThrow(() -> new ResourceNotFoundException("QuestionTypeId not found" + questionTypeId));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionType> getActiveQuestionType() {
		return this.getSession().createQuery("from QuestionType where status=:status").setParameter("status", true)
				.list();
	}
}
