<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<title>Test List</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
</head>
<body>
	<div class="content-i">
		<div class="content-box">
			<div class="element-wrapper">
				<div class="element-box">
					<h5 class="element-header mb-4">Test History List</h5>
					<table class="table text-center">
						<thead>
							<tr>
								<th>Sr No.</th>
								<th>Test name</th>
								<th>Result</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="test" items="${resultList}" varStatus="ind">
								<c:if test="${test.userId==user.id}">
									<tr>
										<td>${ind.index+1}</td>
										<td>${test.testName }</td>
										<td>${test.resultStatus }</td>
										<td><a class="text-decoration-none !important"
											href="${pageContext.request.contextPath }/java/student/view/test/result/${test.testResultId}">View</a></td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>