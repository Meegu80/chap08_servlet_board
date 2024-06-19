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
        	<h3>게시물 목록</h3>
        	<c:if test="${empty boardList}">
                <p>게시물이 존재하지 않습니다.</p>
            </c:if>
            
            <c:if test="${not empty boardList}">
				<table border="1">
					<tr>
						<th>게시물</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일자</th>
						<th>조회수</th>
					</tr>
					<c:forEach var="board" items="${boardList }">
						<tr>
							<td>${board.bno }</td>
							<td><a href="${contextPath }/boardDetail?bno=${board.bno}"> ${board.title }</a></td>
							<td>${board.memberId }</td>
							<td><fmt:formatDate value="${board.regDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${board.hitNo }</td>
						</tr>		
					</c:forEach>
				</table>
			</c:if>
			<br>
			<a href="${contextPath }/boardInsertForm.jsp">게시물 작성</a>
		</main>
	</div>
</body>
</html>	
