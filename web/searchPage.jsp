<%-- 
    Document   : searchPage
    Created on : Sep 16, 2020, 11:26:29 AM
    Author     : MinHiu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="./assets/css/custom.css">
	<title>Home Page</title>
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
				    <a class="card-body text-white disabled font-weight-bold" href="">Home</a>
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
		    <h1>Home Page</h1>
		</div>
		<c:set var="searchContent" value="${param.txtSearch}" />

		<form class="form-inline ml-3" action="DispatchController">
		    <div class="form-group">
			<label class="mr-3">Search By Content:</label>
			<input class="form-control mr-3" type="text" name="txtSearch" value="${searchContent}" />
			<input class="btn btn-primary" type="submit" value="Search Article" name="btAction" />
		    </div>
		</form>
		<br>

		<c:if test="${not empty searchContent}">
		    <c:set var="result" value="${requestScope.SEARCH_RESULT}" />
		    <c:if test="${not empty result}">
			<c:set var="currentPage" value="${requestScope.CURRENT_PAGE}" />
			<c:set var="numberOfPage" value="${requestScope.NUMBER_OF_PAGE}" />

			<c:forEach var="article" items="${result}">
			    <div class="col-12">
				<div class="card mb-3">
				    <div class="card-header">
					<p class="font-weight-bold">
					    ${article.datePost}, posted by ${article.userEmail}
					</p>
				    </div>

				    <div class="card-body">
					<img class="img-responsive card-img-top m-1" style="height: 80vh; width: auto;"
					     src="data:image/jpeg;base64,${article.image}"><br>
					<label class="font-weight-bold" for="articleTitle">Post Title:</label>
					<p id="articleTitle">
					    ${article.postTitle}-${article.userEmail}
					</p>
					<label class="font-weight-bold" for="articleContent">Post Content:</label>
					<p id="articleContent">
					    ${article.postDescription}
					</p>
				    </div>

				    <div class="card-footer">
					<c:url var="viewLink" value="DispatchController">
					    <c:param name="btAction" value="View Article" />
					    <c:param name="postId" value="${article.postId}" />
					</c:url>
					<a class="btn btn-outline-primary" href="${viewLink}">View Detail</a>

					<c:if test="${article.userEmail eq sessionScope.CURRENT_USER.email}">
					    <c:url var="deleteLink" value="DispatchController">
						<c:param name="btAction" value="Delete Article" />
						<c:param name="postId" value="${article.postId}" />
					    </c:url>
					    <a class="btn btn-outline-primary" href="${deleteLink}"
					       onclick="return confirm('Do you still want to delete this article?')">Delete
						Article</a>
					    </c:if>
				    </div>
				</div>
			    </div>
			</c:forEach>
			<br>

			<ul class="pagination m-3">
			    <c:forEach begin="1" end="${numberOfPage}" varStatus="counter">
				<c:if test="${counter.count ne currentPage}">
				    <c:url var="pageLink" value="DispatchController">
					<c:param name="btAction" value="Search Article" />
					<c:param name="txtSearch" value="${searchContent}" />
					<c:param name="txtPage" value="${counter.count}" />
				    </c:url>
				    <li class="page-item"><a class="page-link" href="${pageLink}">${counter.count}</a>
				    </li>
				</c:if>

				<c:if test="${counter.count eq currentPage}">
				    ${currentPage}
				    <li class="page-item disabled"><a class="page-link"
								      href="${pageLink}">${currentPage}</a></li>
				    </c:if>
				</c:forEach>
			</ul>
		    </c:if>
		    <c:if test="${empty result}">
			<h4>No record is matched.</h4>
		    </c:if>
		</c:if>
	    </div>
	</div>
    </body>

</html>