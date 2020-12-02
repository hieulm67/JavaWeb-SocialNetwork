<%-- 
    Document   : ViewArticle
    Created on : Sep 23, 2020, 11:27:33 AM
    Author     : MinHiu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

    <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="./assets/css/custom.css">
	<title>View Detail Page</title>
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
		<c:set var="article" value="${requestScope.POST_DETAIL}" />

		<div class="col-12">
		    <c:if test="${not empty article}">
			<div class="card mb-3">
			    <div class="card-header">
				<p class="font-weight-bold">
				    ${article.datePost}-${article.userEmail}
				</p>
			    </div>
			    <div class="card-body">
				<img class="img-responsive card-img-top m-1" style="height: 80vh; width: auto;"
				     src="data:image/jpeg;base64,${article.image}" /><br>
				<label class="font-weight-bold" for="articleTitle">Post Title:</label>
				<p id="articleTitle">
				    ${article.postTitle}-${article.userEmail}
				</p>
				<label class="font-weight-bold" for="articleContent">Post Content:</label>
				<p id="articleContent">
				    ${article.postDescription}
				</p>
				<p class="mt-1">
				    <c:set var="totalLike" value="${requestScope.TOTAL_LIKE}" />
				    <c:set var="totalDislike" value="${requestScope.TOTAL_DISLIKE}" />
				    <c:if test="${not empty totalLike}">
					<img src="./assets/img/currentLike.png" style="height: 30px; width: 30px;" />${totalLike}
				    </c:if>
				    <c:if test="${not empty totalDislike}">
					<img src="./assets/img/currentDislike.png" style="height: 30px; width: 30px;" />${totalDislike}
				    </c:if>
				</p>
				<!-- <font style="font-weight: bold">${article.postTitle}</font><br>
				${article.postDescription} -->
			    </div>

			    <div class="card-footer">
				<c:set var="sourceImageLike" value="${requestScope.SOURCE_IMAGE_LIKE}" />
				<c:set var="sourceImageDislike" value="${requestScope.SOURCE_IMAGE_DISLIKE}" />

				<div class="float-left">
				    <form action="DispatchController">
					<div class="btn-group">
					    <input type="hidden" name="btAction" value="React Emotion" />
					    <button class="btn btn-success" name="emotionType" value="Like"><img
						    src="${sourceImageLike}" style="height: 30px; width: 30px;" />Like</button>
					    <button class="btn btn-warning" name="emotionType" value="Dislike"><img
						    src="${sourceImageDislike}"
						    style="height: 30px; width: 30px;" />Dislike</button>
					    <input type="hidden" name="postId" value="${article.postId}" />
					    <input type="hidden" name="userEmail" value="${sessionScope.CURRENT_USER.email}" />
					</div>
				    </form>
				</div>

				<div class="float-right">
				    <c:if test="${article.userEmail eq sessionScope.CURRENT_USER.email}">
					<c:url var="deleteLink" value="DispatchController">
					    <c:param name="btAction" value="Delete Article" />
					    <c:param name="postId" value="${article.postId}" />
					</c:url>
					<div class="m-2">
					    <a class="btn btn-outline-danger" href="${deleteLink}"
					       onclick="return confirm('Do you still want to delete this article?')">Delete
						This Article</a>
					</div>
				    </c:if>
				</div>
			    </div>

			    <div class="mt-2 ml-3">
				<h4>Post Comment</h4>
				<c:set var="comments" value="${requestScope.LIST_COMMENTS}" />
				<c:if test="${not empty comments}">
				    <c:forEach var="comment" items="${comments}">
					<div class="p-1">
					    ${comment.userEmail}-${comment.commentDate}:<br>
					    ${comment.commentContent}
					</div>
					<c:if
					    test="${(comment.userEmail eq sessionScope.CURRENT_USER.email) || (article.userEmail eq sessionScope.CURRENT_USER.email)}">
					    <c:url var="deleteLink" value="DispatchController">
						<c:param name="btAction" value="Delete Comment" />
						<c:param name="commentId" value="${comment.commentId}" />
						<c:param name="postId" value="${article.postId}" />
					    </c:url>
					    <a class="btn btn-outline-danger" href="${deleteLink}"
					       onclick="return confirm('Do you still want to delete this comment?')">Delete
						Comment</a><br>
					    </c:if>
					<br>
				    </c:forEach>
				</c:if>
			    </div>

			    <div class="ml-3">
				<form action="DispatchController">
				    <font style="font-weight: bold">Comment as ${sessionScope.CURRENT_USER.email}:</font><br>
				    <textarea name="txtComment" cols="80" rows="5"></textarea><br>
				    <input type="hidden" name="postId" value="${article.postId}" />
				    <input class="btn btn-primary m-1 mb-2" type="submit" value="Comment Article" name="btAction" />
				</form>
			    </div>
			</div>
		    </c:if>
		    
		    <c:if test="${empty article}">
			Something went wrong..
		    </c:if>
		</div>
	    </div>
	</div>
    </body>

</html>