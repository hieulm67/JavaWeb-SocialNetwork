<%-- 
    Document   : registerPage
    Created on : Sep 16, 2020, 11:44:47 AM
    Author     : MinHiu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Register Page</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="./assets/css/custom.css">
</head>

<body>
	<div class="container h-100">
		<div class="row h-100 justify-content-center align-items-center">
			<div class="col-lg-6">
				<div class="text-center pb-3">
					<h1>Register Page</h1>
				</div>
				<form action="DispatchController" method="POST">
					<c:set var="error" value="${requestScope.ERROR_CREATE_ACCOUNT}" />
					<div class="form-group">
						<label>Email</label>
						<input type="text" class="form-control" placeholder="For example abc@gmail.com" name="txtEmail"
							value="${param.txtEmail}" />
						<small class="text-muted">
							Must be 10-100 characters.
						</small><br>
						<p class="text-danger">${error.emailFormatError}</p>
					</div>
					<div class="form-group">
						<label>Name</label>
						<input type="text" class="form-control" placeholder="Your Fullname" name="txtName"
							value="${param.txtName}" />
						<small class="text-muted">
							Must be 2-50 characters.
						</small><br>
						<p class="text-danger">${error.nameLengthError}</p>
					</div>
					<div class="form-group">
						<label>Password</label>
						<input type="password" class="form-control" placeholder="Password" name="txtPassword"
							value="" />
						<small class="text-muted">
							Must be 6-30 characters.
						</small><br>
						<p class="text-danger">${error.passwordLengthError}</p>
					</div>
					<div class="form-group">
						<label>Confirm Password</label>
						<input type="password" class="form-control" placeholder="Confirm Password"
							name="txtConfirmPassword" value="" />
						<p class="text-danger">${error.confirmNoMatchedError}</p>
					</div>
					<div class="form-group">
						<input type="submit" class="btn btn-primary" value="Create New Account" name="btAction" />
						<input type="reset" class="btn btn-primary" value="Reset" />
						<p class="text-danger">${error.emailIsExisted}</p>
					</div>
				</form>
				<a href="login.html">
					< Return to login page.</a>
			</div>
		</div>
	</div>
</body>

</html>