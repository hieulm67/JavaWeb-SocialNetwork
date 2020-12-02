<%-- 
    Document   : viewNotificationPage
    Created on : Sep 27, 2020, 6:50:49 PM
    Author     : MinHiu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>View Notification Page</title>
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
								<a class="card-body text-white-50" href="">Home</a>
							</div>
						</li>
						<li class="nav-item mt-4">
							<div class="card bg-primary">
								<a class="card-body text-white-50" href="postArticle.jsp">Post Article</a>
							</div>
						</li>
						<li class="nav-item mt-4">
							<c:url var="notificationLink" value="DispatchController">
								<c:param name="btAction" value="View Notification" />
							</c:url>
							<div class="card bg-primary">
								<a class="card-body text-white disabled font-weight-bold"
									href="${notificationLink}">Notifications</a>
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
				<h1>Notifications</h1>
			</div>
			<c:set var="listNotification" value="${requestScope.LIST_NOTIFICATION}" />
			<c:if test="${not empty listNotification}">
				<c:forEach var="notification" items="${listNotification}" varStatus="counter">
					<div class="col-12">
						<div class="card mb-3">
							<div class="card-header">
								<p class="font-weight-bold">
									${notification.notificationDate}
								</p>
							</div>
							<div class="card-body">
								${notification.notificationContent} to your post.
							</div>
							<div class="card-footer">
								<c:url var="viewArticleLink" value="DispatchController">
									<c:param name="btAction" value="View Article" />
									<c:param name="postId" value="${notification.postId}" />
								</c:url>
								<a class="btn btn-outline-primary" href="${viewArticleLink}">View Detail</a>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${empty listNotification}">
				<div class="m-3">
					You don't have news notifications.
				</div>
			</c:if>
		</div>
	</div>
</body>

</html>