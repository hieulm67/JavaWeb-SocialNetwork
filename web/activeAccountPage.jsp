<%-- 
    Document   : activeAccountPage
    Created on : Sep 28, 2020, 2:50:26 AM
    Author     : MinHiu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Active Page</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="./assets/css/custom.css">
</head>

<body>
	<div class="container h-100">
		<div class="row h-100 justify-content-center align-items-center">
			<div class="col-6 text-center">
				<p class="h1 text-warning">
					Active Page
				</p>
				<c:set var="email" value="${requestScope.USER_EMAIL}" />
				<form action="DispatchController" method="POST">
					<label>Please enter the activation code:</label>
					<input class="form-control mb-2" type="text" name="txtCode" value="" />
					<input class="btn btn-primary" type="submit" value="Active Account" name="btAction" /><br>
					<p class="text-danger">
						${requestScope.ACTIVE_ERROR}<br>
					</p>
					<input type="hidden" name="txtEmail" value="${email}" />
					<input type="hidden" name="activeCode" value="${requestScope.ACTIVATION_CODE}" />
				</form>

				<c:url var="confirmEmailLink" value="DispatchController">
					<c:param name="btAction" value="Send Code" />
					<c:param name="txtEmail" value="${email}" />
				</c:url>
				Don't receive code? <a href="${confirmEmailLink}">Send again.</a>
			</div>
		</div>
	</div>
</body>

</html>