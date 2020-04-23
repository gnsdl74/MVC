<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 수정 페이지</title>
</head>
<body>
	<h2>글 수정</h2>
	<form action="board-update.do" method="post">
		<div>
			<p>글 번호 : ${boardVO.bno }</p>
		</div>
		<!-- 화면에 보이지않고 데이터를 숨겨서 보내기 -->
		<input type="hidden" name="bno" value="${boardVO.bno }" />
		<div>
			<p>제목 : <input type="text" name="title" value="${boardVO.title }" /></p>
		</div>
		<div>
			<p>작성자 : ${boardVO.userid }</p>
			<p>작성일 : ${boardVO.cdate }</p>
		</div>
		<div>
			<p>내용<br>
			<textarea rows="20" cols="120" name="content">${boardVO.content }</textarea>
		</div>
		<div>
			<input type="submit" value="게시글 수정" />
		</div>
	</form>
</body>
</html>