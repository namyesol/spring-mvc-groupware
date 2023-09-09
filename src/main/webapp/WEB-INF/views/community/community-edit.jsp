<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Insert title here</title>
</head>
<body>
<div class="container-fluid">
	<div class="row">
	    <div class="sidebar col-md-3 col-lg-2 p-0">
      		<jsp:include page="/common/mainMenu.jsp" flush="true" /> <br> 
      	</div>
      	<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
      		<section class="mt-3 mb-3">
				<form action="/EditCommunityServlet?comNum=${community.comNum}"" method="post">
					<div class="mb-3">
						<label for="title" class="form-label">제목</label>
						<input type="text" name="title" class="form-control" value="${community.title}">
					</div>
					<div class="mb-3">
						<label for="content" class="form-label">내용</label>
						<textarea name="content" class="form-control" rows="3">${community.content}</textarea>
					</div>
					
					<div class="button-group d-flex justify-content-center gap-2">
						<button type="submit" class="btn btn-outline-primary">수정</button>
			            <a href="/CommunityDetailsServlet?comNum=${community.comNum}" class="btn btn-outline-dark">뒤로가기</a>
					</div>
				</form>
			</section>
      	</main>
   	</div>
</div>

</body>
</html>