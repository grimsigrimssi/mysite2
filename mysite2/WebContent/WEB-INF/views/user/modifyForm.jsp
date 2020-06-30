<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <!-- if else문 사용시 임포트  -->
      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/user.css" rel="stylesheet" type="text/css">

</head>
<body>
	<div id="wrap">
	
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<!-- //header & navi -->
		
		<c:import url="/WEB-INF/views/includes/asideUser.jsp"></c:import>
		<!-- //aside -->

		<div id="content">
			
			<div id="content-head">
            	<h3>회원정보</h3>
            	<div id="location">
            		<ul>
            			<li>홈</li>
            			<li>회원</li>
            			<li class="last">회원정보</li>
            		</ul>
            	</div>
                <div class="clear"></div>
            </div>
             <!-- //content-head -->

			<div id="user">
				<div id="modifyForm">		
					<form action="/mysite2/user" method="get">
					
						<input type="hidden" name="action" value="login">
						
						<!-- 아이디 -->
						<div class="form-group">
							<label class="form-text" for="input-uid">아이디</label> 
							<span class="text-large bold">${sessionScope.authUser.id }</span>
						</div>

						<!-- 비밀번호 -->
						<div class="form-group">
							<label class="form-text" for="input-pass">패스워드</label> 
							<input type="password" id="input-pass" name="password" value="${sessionScope.authUser.password }" placeholder="비밀번호를 입력하세요"	>
						</div>
						
						
						<!-- 이름 -->
						<div class="form-group">
							<label class="form-text" for="input-name">이름</label> 
							<input type="text" id="input-name" name="name" value="${sessionScope.authUser.name }" placeholder="이름을 입력하세요">
						</div>

						<!-- //나이 -->
						<div class="form-group">
							<span class="form-text">성별</span>
													
							<c:choose>
								<c:when test="${sessionScope.authUser.gender eq male }">
									<label for="rdo-male">남</label> 
									<input type="radio" id="rdo-male" name="gender" value="male" checked="checked">
									
									<label for="rdo-female">여</label> 
									<input type="radio" id="rdo-female" name="gender" value="female" >
								</c:when>
								<c:when test="${sessionScope.authUser.gender eq female }">
									<label for="rdo-male">남</label> 
									<input type="radio" id="rdo-male" name="gender" value="male" > 
								
									<label for="rdo-female">여</label> 
									<input type="radio" id="rdo-female" name="gender" value="female" checked="checked">								
								</c:when>
							</c:choose>
						</div>

						<!-- 버튼영역 -->
		                <div class="button-area">
		                    <button type="submit" id="btn-submit">회원정보수정</button>
		                </div>
		                <input type="text" name="action" value="modify">
						
					</form>
				
				
				</div>
				<!-- //modifyForm -->
			</div>
			<!-- //user -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		<!-- //footer -->
		
	</div>
	<!-- //wrap -->
	
</body>
</html>