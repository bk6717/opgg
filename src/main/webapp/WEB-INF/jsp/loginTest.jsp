<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!--     현재사용불가 json이용중 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/oauth/jwt/common" method="post">
		<input type="text" name="username">
		<input type="password" name="password">
	<button>일반 회원가입 로그인</button>
	</form>
</body>
</html>