<%-- 
    Document   : confirmMailPage
    Created on : Sep 28, 2020, 3:18:50 AM
    Author     : MinHiu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Confirm Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="./assets/css/custom.css">
</head>

<body>
    
    <div class="container h-100">
		<div class="row h-100 justify-content-center align-items-center">
			<div class="col-6 text-center">
				<p class="h1 text-warning">
					Confirm Mail Page
				</p>
				<form action="DispatchController">
					<label>Your email:</label>
                    <input class="form-control mb-2" type="text" placeholder="abc@gmail.com" name="txtEmail" value="" />
                    <p class="text-danger mb-2">${requestScope.CONFIRM_MAIL_ERROR}</p><br>
					<input class="btn btn-primary" type="submit" value="Send Code" name="btAction" />
				</form>
			</div>
		</div>
	</div>
</body>

</html>