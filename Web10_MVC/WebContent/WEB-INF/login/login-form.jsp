<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
</head>
<body>
	<form action="login" method="post">
		<input type="text" name="userid" placeholder="아이디" required autofocus/>
		<br>
		<input type="password" name="password" placeholder="비밀번호" required/>
		<br>
		<input type="submit" value="로그인"/>
	</form>
</body>
</html>