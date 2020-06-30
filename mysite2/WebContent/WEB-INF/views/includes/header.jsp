<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    
	
	<div id="header">
		<h1><a href="/mysite2/main">MySite</a></h1>
		
		<c:choose>
			<c:when test="${empty authUser }"> <!-- ==를 eq로 쓸수 있음. 간혹 오류가 날 때 eq로 사용할 것 -->
				<!-- 로그인실패시 or 로그인 전 -->
				<ul>
					<li><a href="/mysite2/user?action=loginForm">로그인</a></li>
					<li><a href="/mysite2/user?action=joinForm">회원가입</a></li>
				</ul>
			</c:when>
			<c:when test="${authUser.name != null }"> 
				<!-- 로그인 성공했을 때 -->
				<ul>
					<li>${authUser.name } 님 안녕하세요^^</li>
					<li><a href="/mysite2/user?action=logout">로그아웃</a></li>
					<li><a href="/mysite2/user?action=modifyForm&no=${authUser.no }">회원정보수정</a></li> <!-- authUser에서 정보(no)를 가져오기 -->
				</ul>
			</c:when>
		</c:choose>
	</div>
	
	<!-- navi -->
	<div id="nav">
		<ul>
			<li><a href="/mysite2/guestbook?action=gAddList">방명록</a></li>
			<li><a href="">갤러리</a></li>
			<li><a href="">게시판</a></li>
			<li><a href="">입사지원서</a></li>
		</ul>
		<div class="clear"></div>
	</div>    							