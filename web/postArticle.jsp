<%-- 
    Document   : postArticle
    Created on : Sep 21, 2020, 12:28:08 AM
    Author     : MinHiu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Post Article Page</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="./assets/css/custom.css">
</head>

<body>
	<div class="container h-100mh mw-100">
		<div class="col-sm-2">
			<div class="bg-primary fixed-top h-100 col-sm-2 text-center ml-2">
				<c:set var="user" value="${sessionScope.CURRENT_USER}" />
				<c:if test="${not empty user}">
					<p class="text-center text-white h4 p-3 pt-5">
						Welcome ${user.name}
					</p>
				</c:if>

				<div class="col-12">
					<ul class="nav flex-column mt-4">
						<li class="nav-item">
							<div class="card bg-primary">
								<a class="card-body text-white-50" href="searchPage.jsp">Home</a>
							</div>
						</li>
						<li class="nav-item mt-4">
							<div class="card bg-primary">
								<a class="card-body text-white disabled font-weight-bold" href="postArticle.jsp">Post Article</a>
							</div>
						</li>
						<li class="nav-item mt-4">
							<c:url var="notificationLink" value="DispatchController">
								<c:param name="btAction" value="View Notification" />
							</c:url>
							<div class="card bg-primary">
								<a class="card-body text-white-50" href="${notificationLink}">Notifications</a>
							</div>
						</li>
					</ul>
				</div>

				<div class="fixed-bottom col-sm-2 text-center mb-3">
					<form action="DispatchController">
						<input class="btn btn-danger" type="submit" value="Logout" name="btAction" />
					</form>
				</div>
			</div>
		</div>

		<div class="col-sm-10 p-3 justify-content-center align-items-center float-right">
			<div class="pb-4 ml-3">
				<h1>Post Article</h1>
			</div>

			<div>
				<c:set var="error" value="${requestScope.CREATE_ARTICLE_ERROR}" />
				<form action="DispatchController" enctype="multipart/form-data" method="POST">
					<div class="form-group">
						<div class="custom-file">
							<input type="file" accept=".jpg,.gif,.png" class="custom-file-input" name="imageSelect" />
							<label class="custom-file-label" for="inputGroupFile04">Choose file</label>
						</div>
					</div>

					<div class="form-group">
						<label for="postTitle">Post Title</label>
						<input type="text" class="form-control" id="postTitle" name="txtContent" value="" />
						<p class="text-danger">${error.postContentLengthError}</p>
					</div>

					<div class="form-group">
						<label for="postContent">Post Content</label>
						<textarea class="form-control" id="postContent" name="txtDescription" cols="50"
							rows="10"></textarea>
						<p class="text-danger">${error.postDescriptionLengthError}</p>
					</div>

					<div class="form-group">
						<input class="btn btn-primary" type="submit" value="Post Article" name="btAction" />
					</div>
				</form>
			</div>
		</div>
	</div>
</body>

</html>