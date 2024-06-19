<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!-- 컨텍스트패스(진입점폴더) 변수 설정 -->
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main.jsp</title>
<link rel="stylesheet" type="text/css" href="css/board.css" />

</head>
<body>
<div class="container">
		<header class="header">
            <nav>
                <a href="#">회원제 게시판</a>
            </nav>
            <div class="user-info">
                <c:if test="${not empty sessionScope.member}">
                    <p>${sessionScope.member.name} 님</p>
                    <a href="${contextPath}/logout">Logout</a>
                </c:if>
                <c:if test="${empty sessionScope.member}">
                    <a href="${contextPath}/login">Login</a>
                </c:if>
            </div>
        </header>
		<main>
			<h3>여기는 main.jsp</h3>	
			<c:if test="${not empty sessionScope.member }">
				<a href="${contextPath}/board">게시물 등록</a>
			</c:if>
			<br>
			<a href="${contextPath}/boardList">게시물 목록</a>
		</main>
	</div>	
</body>
</html>