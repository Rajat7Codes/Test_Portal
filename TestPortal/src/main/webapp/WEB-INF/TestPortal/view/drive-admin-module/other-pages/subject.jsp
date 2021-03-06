<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<title>Subject</title>
</head>
<body>
	<div class="row">
		<div class="col-lg-12">
			<div class="element-wrapper">
				<h5 class="element-header">Subject</h5>

				<div class="row">
					<div class="col-lg-4">
						<div class="element-wrapper">
							<div class="element-box">
								<form:form
									action="${pageContext.request.contextPath }/drive/admin/subject/save"
									modelAttribute="subject" name="subject" id="subjectForm"
									method="post">

									<form:hidden path="subjectId" />

									<h5 class="form-header">Add Subject</h5>

									<div class="form-group">
										<form:label class="col-form-label" path="subjectName"> Subject Name</form:label>
										<form:input class="form-control"
											placeholder="Enter Subject Name" path="subjectName"
											name="subjectName" id="subjectName" type="text" />
									</div>

									<div class="form-group">
										<form:label class="col-form-label" path="status"> Status</form:label>
										<form:select path="status" name="status" id="status"
											class="form-control">
											<form:option value="true">Active</form:option>
											<form:option value="false">Inactive</form:option>
										</form:select>
									</div>

									<div class="form-buttons-w">
										<button class="btn btn-secondary" type="reset">Reset</button>
										<button class="btn btn-primary" type="submit">Submit</button>
									</div>
								</form:form>
							</div>
						</div>
					</div>

					<div class=" col-lg-8">
						<div class="element-wrapper">
							<div class="element-box">
								<h5 class="form-header">Subject List</h5>
								<div class="table-responsive">
									<table class="table table-striped table-lightfont text-center">
										<thead>
											<tr>
												<th>Id</th>
												<th>subject Name</th>
												<th>Status</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody id="tableBody">
											<c:forEach var="subject" items="${subjectList}"
												varStatus="ind">
												<tr>
													<td class="id">${subject.subjectId}</td>
													<td class="subject_">${subject.subjectName}</td>
													<c:if test="${subject.status == true}">
														<td class="status"><c:out value="Active" /></td>
													</c:if>
													<c:if test="${subject.status == false}">
														<td class="status"><c:out value="Inactive" /></td>
													</c:if>
													<td class="row-actions"><a title="Edit"
														href="<c:url value='/drive/admin/subject/edit/${subject.subjectId }' />"><i
															class="os-icon os-icon-ui-49"></i></a> <a class="danger"
														title="Delete"
														href="<c:url value='/drive/admin/subject/delete/${subject.subjectId }' />"><i
															class="os-icon os-icon-ui-15"></i></a></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>