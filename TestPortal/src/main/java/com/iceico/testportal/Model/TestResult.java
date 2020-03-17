/**
 * 
 */
package com.iceico.testportal.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.iceico.testportal.audit.Auditable.Auditable;

/**
 * @author sameer
 *
 */
@Entity
@Table(name = "tab_test_result")
@EntityListeners(AuditingEntityListener.class)
public class TestResult extends Auditable<String> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2626775903899645920L;

	/**
	 * 
	 */
	public TestResult() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "test_result_id")
	private Long testResultId;

	@Column(name = "test_name")
	private String testName;

	@Column(name = "marks")
	private Double Marks;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date")
	private Date date;

	@Column(name = "negative_marks")
	private Double negativeMarks;

	@Column(name = "attempted")
	private Integer Attempted;

	@Column(name = "result_status")
	private String resultStatus;

	/**
	 * @param testResultId
	 * @param testName
	 * @param marks
	 * @param date
	 * @param negativeMarks
	 * @param attempted
	 * @param resultStatus
	 */
	public TestResult(Long testResultId, String testName, Double marks, Date date, Double negativeMarks,
			Integer attempted, String resultStatus) {
		super();
		this.testResultId = testResultId;
		this.testName = testName;
		Marks = marks;
		this.date = date;
		this.negativeMarks = negativeMarks;
		Attempted = attempted;
		this.resultStatus = resultStatus;
	}

	/**
	 * @return the testResultId
	 */
	public Long getTestResultId() {
		return testResultId;
	}

	/**
	 * @param testResultId the testResultId to set
	 */
	public void setTestResultId(Long testResultId) {
		this.testResultId = testResultId;
	}

	/**
	 * @return the testName
	 */
	public String getTestName() {
		return testName;
	}

	/**
	 * @param testName the testName to set
	 */
	public void setTestName(String testName) {
		this.testName = testName;
	}

	/**
	 * @return the marks
	 */
	public Double getMarks() {
		return Marks;
	}

	/**
	 * @param marks the marks to set
	 */
	public void setMarks(Double marks) {
		Marks = marks;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the negativeMarks
	 */
	public Double getNegativeMarks() {
		return negativeMarks;
	}

	/**
	 * @param negativeMarks the negativeMarks to set
	 */
	public void setNegativeMarks(Double negativeMarks) {
		this.negativeMarks = negativeMarks;
	}

	/**
	 * @return the attempted
	 */
	public Integer getAttempted() {
		return Attempted;
	}

	/**
	 * @param attempted the attempted to set
	 */
	public void setAttempted(Integer attempted) {
		Attempted = attempted;
	}

	/**
	 * @return the resultStatus
	 */
	public String getResultStatus() {
		return resultStatus;
	}

	/**
	 * @param resultStatus the resultStatus to set
	 */
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
}
