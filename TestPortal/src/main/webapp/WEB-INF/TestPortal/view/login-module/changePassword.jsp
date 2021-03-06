<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags-->
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="au theme template">
<meta name="author" content="Hau Nguyen">
<meta name="keywords" content="au theme template">

<!-- Title Page-->
<%-- <title><tiles:insertAttribute name="title" /></title> --%>

<!-- Bootstrap CSS-->
<link
	href="${pageContext.request.contextPath}/static/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" media="all">


<link href="${pageContext.request.contextPath}/static/css/login.css"
	rel="stylesheet" type="text/css" media="all">


<link href="${pageContext.request.contextPath}/static/css/main5739.css"
	rel="stylesheet" type="text/css">

<link rel="stylesheet"
	href="//cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.2/css/bootstrapValidator.min.css" />

<style>
.logo-w {
	padding: 40px 100px !important
}

.reg-form {
	padding-bottom: 30px !important
}

.auth-box-w {
	margin: auto !important
}
</style>

</head>
<body class="auth-wrapper">
	<div class="all-wrapper menu-side with-pattern mt-5" style="height: 100vh">
		<div class="auth-box-w">
			<div class="logo-w">
				<a href="index-2.html"><img width="200px" alt=""
					src="${pageContext.request.contextPath}/static/img/Iceico_logo.png"></a>
			</div>
			<h4 class="auth-header">Change Password</h4>

			<!-- Change Password Form -->
			<form id="change-password" class="pt-0"
				action="${pageContext.request.contextPath}/java/change/password"
				data-bv-feedbackicons-valid="glyphicon glyphicon-ok"
				data-bv-feedbackicons-invalid="glyphicon glyphicon-remove"
				data-bv-feedbackicons-validating="glyphicon glyphicon-refresh"
				method="post">

				<div class="row">
					<div class="col-sm-6">
						<div class="form-group">
							<label for=""> Password</label> <input class="form-control"
								placeholder="Password" required id="password1" name="password"
								type="password" data-bv-identical="true"
								data-bv-identical-field="confirmPassword"
								data-bv-identical-message=" ">
							<div class="pre-icon os-icon os-icon-fingerprint"></div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<label for="">Confirm Password</label> <input
								name="confirmPassword" required class="form-control"
								placeholder="Password" type="password" data-bv-identical="true"
								data-bv-identical-field="password"
								data-bv-identical-message="Password mismatch">
						</div>
					</div>
				</div>
				<div class="buttons-w text-center">
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
				<div class="buttons-w text-center">
					<label class="form-check-label"> <a
						class="btn btn-outline-light text-dark"
						href="${pageContext.request.contextPath}/login"> Go To Login </a>
					</label>
				</div>

			</form>
		</div>
	</div>
</body>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<script type="text/javascript"
	src="//cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.2/js/bootstrapValidator.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#change-password').bootstrapValidator({
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				password : {
					validators : {
						identical : {
							field : 'password'
						}
					}
				},
				confirmPassword : {
					validators : {
						identical : {
							field : 'confirmPassword'
						}
					}
				}
			}
		});
	});
</script>

</html>
<!-- end document-->