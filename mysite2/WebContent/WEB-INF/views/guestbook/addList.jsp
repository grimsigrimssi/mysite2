<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <!-- if else문 사용시 임포트  -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/guestbook.css" rel="stylesheet" type="text/css">

</head>
<body>
	
		<div id="wrap">
	
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<!-- //header & navi -->
		
		<c:import url="/WEB-INF/views/includes/asideUser.jsp"></c:import>
		<!-- //aside -->
	
		<div id="content">
			<h3>방명록</h3>
			<div id="location">
					<ul>
						<li>홈</li>
						<li>방명록</li>
						<li class="last">일반방명록</li>			
					</ul>		
			</div>
			<div class="clear"></div>
         </div>
         <!-- //content-head -->
			
		
		<div id="guestbook">
		
				<form action="/WEB-INF/views/guestbook/addList.jsp" method="get">
			
					<table border="1">
						<colgroup>
							<col style="width: 70px;">
							<col>
							<col style="width: 70px;">
							<col>
						</colgroup>	
						<tbody>						
							<tr> 
								<th><label class="form-text" for="input-uname">이름</label></td>
								<td><input id="input-uname" type="text" name="name"></td>
								<th><label class="form-text" for="input-pass">패스워드</label></td>
								<td><input id="input-pass"type="password" name="pass"></td>
							</tr>
							<tr>
								<td colspan="4"><textarea name="content" cols="72" rows="5"></textarea></td>
							</tr>
							<tr class="button-area">
								<td colspan="4"><button type="submit">등록</button></td>
							</tr>
						</tbody>
						
					</table>
					<!-- //guestWrite -->
					<input type="hidden" name="action" value="add">
										
				</form><br>
				
					
				<c:forEach items="${gList }" var="vo" varStatus="status">
					<table class="guestRead">
						<colgroup>
							<col style="width: 10%;">
							<col style="width: 40%;">
							<col style="width: 40%;">
							<col style="width: 10%;">
						</colgroup>		
						<tr>
							<td>${vo.no }</td>
							<td>${vo.name }</td>
							<td>${vo.regDate }</td>
							<td><a href="/mysite2/guestbook?action=deleteForm&delGeust=${vo.no }">삭제</a></td>
						</tr>
						<tr>
						 	<td colspan="4" class="text-left">${vo.content }</td>
						</tr>
					</table>
					<!-- //guestRead -->	
				</c:forEach>							
											
			</div>
			<!-- //guestbook -->

		</div>
		<!-- //content -->
		<div class="clear"></div>
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		<!-- //footer -->
	
	</div>
	<!-- //wrap -->

</body>
</html>