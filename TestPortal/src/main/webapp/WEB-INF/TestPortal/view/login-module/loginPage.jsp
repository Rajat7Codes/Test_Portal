<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TEST PORTAL | LOGIN</title>

<!-- Bootstrap CSS-->
<link
	href="${pageContext.request.contextPath}/static/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" media="all">


<link href="${pageContext.request.contextPath}/static/css/login.css"
	rel="stylesheet" type="text/css" media="all">


<link href="${pageContext.request.contextPath}/static/css/main5739.css"
	rel="stylesheet" type="text/css">

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

	<c:url var="loginUrl" value="/login" />

	<c:if test="${alertMsg == \"Password Updated\"}">
		<div class="alert alert-success">
			<p>${ alertMsg }</p>
		</div>
	</c:if>

	<c:if test="${expireDate == true}">
		<div class="alert alert-danger">
			<p>YOUR APPLICATION DATE IS EXPIRED......!</p>
		</div>
	</c:if>

	<c:if test="${param.error != null}">
		<div class="alert alert-danger m-0">
			<p>Invalid Credentials</p>
		</div>
	</c:if>
	<c:if test="${param.logout != null}">
		<div class="alert alert-success m-0">
			<p>Logged Out Successfully</p>
		</div>
	</c:if>

	<div class="all-wrapper menu-side with-pattern">
		<div class="auth-box-w">
			<div class="logo-w">
				<a href="index-2.html"><img width="200px" alt=""
					src="${pageContext.request.contextPath}/static/img/Iceico_logo.png"></a>
			</div>
			<h4 class="auth-header">Login Form</h4>

			<!-- Login Form -->
			<form action="${loginUrl}" method="post">
				<c:if test="${alertMsg == \"Password Updated\"}">
					<div class="alert alert-success">
						<p>${ alertMsg }</p>
					</div>
				</c:if>

				<c:if test="${expireDate == true}">
					<div class="alert alert-danger">
						<p>YOUR APPLICATION DATE IS EXPIRED......!</p>
					</div>
				</c:if>

				<c:if test="${param.error != null}">
					<div class="alert alert-danger m-0">
						<p>Invalid Credentials</p>
					</div>
				</c:if>
				<c:if test="${param.logout != null}">
					<div class="alert alert-success m-0">
						<p>Logged Out Successfully</p>
					</div>
				</c:if>
				<div class="form-group">
					<label for="">Username</label> <input class="form-control"
						name="ssoId" placeholder="Enter your username">
					<div class="pre-icon os-icon os-icon-user-male-circle"></div>
				</div>
				<div class="form-group">
					<label for="">Password</label> <input type="password"
						name="password" id="password" class="form-control"
						placeholder="Enter your password">
					<div class="pre-icon os-icon os-icon-fingerprint"></div>
				</div>
				<div class="buttons-w">
					<button class="btn btn-primary">Log me in</button>
					<div class="form-check-inline">
						<label class="form-check-label"><input
							class="form-check-input" type="checkbox">Remember Me</label>
					</div>
				</div>
				<div class="buttons-w mt-3 text-center">
					<label class="form-check-label"> <a
						href="${pageContext.request.contextPath}/forgot/password">
							Forgot Password </a>
					</label>
				</div>
			</form>



			<!-- <input type="text" id="login" class="form-group fadeIn second"
				name="ssoId" placeholder="Username"> <input type="password"
				id="password" class="form-group fadeIn third" name="password"
				placeholder="Password"> <label style="width: 100%"
				class="text-center pt-4 pb-3 text-secondary"> <input
				type="checkbox" name="remember-me" id="rememberme"> Remember
				Me
			</label> <input type="submit" class="form-group fadeIn fourth" value="Log In">
			</form> -->

			<!-- Remind Passowrd -->
			<%-- <div id="formFooter">
					<a class="underlineHover" href="${pageContext.request.contextPath }/forgot/password">Forgot Password?</a>
				</div> --%>

		</div>
	</div>
</body>

</html>
<!-- end document-->