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
			<%-- 
			<div class="element-wrapper">
				<div class="element-box">
					<h5 class="element-header mb-4">Test List</h5>
					<table class="table text-center">
						<thead>
							<tr>
								<th>Sr No.</th>
								<th>Test name</th>
								<th>Time</th>
								<th>date</th>
								<th>Action</th>
							</tr>
						</thead>

						<tbody>
							<c:forEach var="test" items="${list}" varStatus="ind">
								<c:if test="${test[0] != true }">
									<tr>
										<td>${ind.index+1}</td>
										<td>${test[1]}</td>
										<td>${test[2]}</td>
										<td>${test[3]}</td>
										<td><a class="text-decoration-none !important"
											href="${pageContext.request.contextPath }/java/student/start/test/${test[4]}">start
												test</a></td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
						
						<tbody>
							<c:forEach var="test" items="${testList}" varStatus="ind">
								<c:if test="${ test.isDeleted!= true }">
									<tr>
										<td>${ind.index+1}</td>
										<td>${test.testName}</td>
										<td>${test.date}</td>
										<td>${test.time}</td>
										<td><a class="text-decoration-none !important"
											href="${pageContext.request.contextPath }/java/student/start/test/${test.addTestId}">start
												test</a></td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>

			</div> --%>

			<div class="row">
				<div class="col-lg-12">
					<div class="padded-lg">
						<div class="projects-list">
							<c:forEach var="test" items="${list}" varStatus="ind">
								<c:if test="${test[0] != true }">
									<div class="project-box">
										<div class="project-head">
											<div class="project-title">
												<h5>${test[1]}</h5>
											</div>
										</div>
										<div class="project-info">
											<div class="row align-items-center">
												<div class="col-sm-12">
													<div class="row">
														<div class="col-4">
															<div class="el-tablo highlight">
																<div class="label font-weight-bold  ">Total time</div>
																<h5 class="text-primary font-weight-bold">${test[2]}</h5>
															</div>
														</div>
														<div class="col-4">
															<div class="el-tablo highlight">
																<div class="label font-weight-bold ">Test uploaded
																	date</div>
																<h5 class="text-primary font-weight-bold">${test[3]}</h5>
															</div>
														</div>
														<div class="col-4">
															<div class="el-tablo highlight text-center">
																<div class="label font-weight-bold mb-1">click
																	below start test</div>

																<a class="badge badge-pill  badge-primary px-3 py-1"
																	href="${pageContext.request.contextPath }/java/student/start/test/${test[4]}">start
																	test</a>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>