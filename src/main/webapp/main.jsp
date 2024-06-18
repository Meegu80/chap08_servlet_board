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
</head>
<body>
<%
	//String memberId = request.getParameter("memberId");
%>
	<h3>여기는 main.jsp</h3>	
	<c:if test="${not empty sessionScope.member }">
		환영합니다. <span style="color: blue;">${sessionScope.member.memberId }님</span>
		<br>
		<a href="${contextPath}/board">게시물 등록</a>
	</c:if>
</body>
</html>