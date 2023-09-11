<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>사이드바</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
<div class="main-section"><!-- 사이드바스타일 설정을위해서 class이름지정 -->
	<!-- main.jsp를 클릭했을 때 보이는 화면 -->
	<ul class="main-ul">
		<li class="group"> Group</li>
		<li class="ware">Ware</li>
		<li><a class="main-li" href="main" >홈</a></li>
		<li><a class="main-li" href="myPage">마이 페이지</a></li>
		<li><a class="main-li" href="#">근태 관리</a></li>
		<li><a class="main-li" href="OrganUIServlet">조직도</a></li>
		<li><a class="main-li" href="/notices">공지사항</a></li>
		<li><a class="main-li" href="CommunityListServlet">자유게시판</a></li>
		<li><a class="main-li" href="FileBoard">자료함</a></li>
		<li><a class="main-li" href="DraftList">전자결재</a></li>
		<li><a class="main-li" href="MeetingRoom">회의실 예약</a></li>
		<li><a class="main-li" href="#">채팅</a></li>
		<li><a class="main-li" href="#">메일 쓰기</a></li>
	</ul>
</div>

</body>
</html>