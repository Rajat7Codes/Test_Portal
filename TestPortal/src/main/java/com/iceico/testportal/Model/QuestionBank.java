/**
 * 
 */
package com.iceico.testportal.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author sameer
 *
 */
@Entity
@Table(name = "question_bank")
public class QuestionBank {

	/**
	 * 
	 */
	public QuestionBank() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_bank_Id")
	private Long questionBankId;

	@Column(name = "marks")
	private String marks;

	@Column(name = "subject")
	private String subject;

	@Column(name = "description")
	private String description;

	/**
	 * @param questionBankId
	 * @param marks
	 * @param subject
	 * @param description
	 */
	public QuestionBank(Long questionBankId, String marks, String subject, String description) {
		super();
		this.questionBankId = questionBankId;
		this.marks = marks;
		this.subject = subject;
		this.description = description;
	}

	/**
	 * @return the questionBankId
	 */
	public Long getQuestionBankId() {
		return questionBankId;
	}

	/**
	 * @param questionBankId the questionBankId to set
	 */
	public void setQuestionBankId(Long questionBankId) {
		this.questionBankId = questionBankId;
	}

	/**
	 * @return the marks
	 */
	public String getMarks() {
		return marks;
	}

	/**
	 * @param marks the marks to set
	 */
	public void setMarks(String marks) {
		this.marks = marks;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
