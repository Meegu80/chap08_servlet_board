<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.sql.*, java.util.*, com.javalab.vo.*" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardList.jsp</title>
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
			<table border="1">
				<tr>
					<th>게시물번호</th>
					<td>${boardVO.bno }</td>
				</tr>
				<tr>	
					<th>제목</th>
					<td>${boardVO.title }</td>
				</tr>
				<tr>	
					<th>내용</th>
					<td>${boardVO.content }</td>
				</tr>
				<tr>		
					<th>작성자</th>
					<td>${boardVO.memberId }</td>
				</tr>
				<tr>		
					<th>작성일자</th>
					<td><fmt:formatDate value="${boardVO.regDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				<tr>		
					<th>조회수</th>
					<td>${boardVO.hitNo }</td>
				</tr>
			</table>
			<br>
			<a href="${contextPath }/boardList">목록</a>
			<a href="${contextPath }/boardUpdate?bno=${boardVO.bno}">수정</a>
			<form action="${contextPath}/boardDelete" method="post">
				<input type="hidden" name="bno" value="${boardVO.bno}" >
				<input type="submit" value="삭제" onclick="return confirm('정말 삭제하시겠습니까?');">
			</form>
		</main>
	</div>	
</body>
</html>	
