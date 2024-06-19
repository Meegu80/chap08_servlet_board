<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.sql.*, java.util.*, com.javalab.vo.*" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
			<c:if test="${ not empty sessionScope.member }">
				<h3>게시물 수정</h3>
				<form action="${contextPath }/boardUpdate" method="post">
					<input type="hidden" name="bno" value="${boardVO.bno }">
					<div>
						<label for="title">제목</label>
						<input type="text" id="title" name="title" value="${boardVO.title }" required>
					</div> 
					<div>
						<label for="title">내용</label>
						<textarea id="content" name="content" required>${boardVO.content }</textarea>
					</div> 
					<div>
						<input type="submit" value="저장">
						<input type="reset" value="다시쓰기">
					</div>	
				</form>
			</c:if>
			<c:if test="${ empty sessionScope.member }">
				<script>
					alert('회원만 게시물을 수정할 수 있습니다');
					window.location.href="${contextPath}/loginForm.jsp";
				</script>
			</c:if>	
		</main>
	</div>		
</body>
</html>