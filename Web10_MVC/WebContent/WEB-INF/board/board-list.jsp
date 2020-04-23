<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
table, th, td {
	border-style: solid;
	border-width: 1px;
	text-align : center;
}

ul {
	list-style-type: none;
}

li {
	display: inline-block; 
}
</style>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<% session.removeAttribute("targetURL"); %>
<meta charset="UTF-8">
<title>게시판 메인 페이지</title>
</head>
<body>
	<h1>마음의 소리 게시판</h1>
	<hr>
	<c:if test="${empty userid }">
		<a href="login">로그인</a>
	</c:if>
	<c:if test="${not empty userid }">
		${userid }님, 환영합니다!!<br>
		<a href="logout">로그아웃</a>
	</c:if>
	<a href="board-register.do"><input type="button" value="글작성"/></a>
	<br><br>
	<table>
		<thead>
			<tr>
				<th style="width: 60px;">번호</th>
				<th style="width: 700px;">제목</th>
				<th style="width: 60px;">작성자</th>
				<th style="width: 100px;">작성일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="vo" items="${boardList }">
				<tr>
					<td>${vo.bno }</td>
					<td><a class="goToDetail" href="board-detail.do?bno=${vo.bno }">${vo.title }</a></td>
					<td>${vo.userid }</td>
					<td>${vo.cdate }</td>
				</tr>			
			</c:forEach>
		</tbody>
	</table>
	
	<hr>
	<div>
		<ul class="pager">
			<c:if test="${pageMaker.hasPrev }">
				<li><a href="${pageMaker.startPageNo - 1 }">이전</a></li>
			</c:if>
			<c:forEach begin="${pageMaker.startPageNo }" end="${pageMaker.endPageNo }" var="num">
				<li><a href="${num }">${num }</a></li>
			</c:forEach>
			<c:if test="${pageMaker.hasNext }">
				<li><a href="${pageMaker.endPageNo + 1 }">다음</a></li>
			</c:if>
		</ul>
	</div>
	
	<div>
		<form id="pagingForm" style="display: none;">
			<input type="text" name="page">
		</form>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
		  // 클릭한 a태그의 정보를 가져오는 코드
		  $(".pager li a").click(function() {
		    // .pager 클래스의 하위 li 요소의 하위 a 요소를 찾아감
		    event.preventDefault(); // a 태그의 기본 동작(페이지 이동)을 금지
		    var targetPage = $(this).attr('href'); // a 태그 href 속성의 값을 저장
		    console.log(targetPage);
		    
		    var frm = $('#pagingForm'); // form의 정보를 frm에 저장
		    frm.find('[name="page"]').val(targetPage); // name="page"를 찾아서 value=targetPage를 저장
		    frm.attr('action', 'board-list.do'); // form에 action 속성 추가
		    frm.attr('method', 'get'); // form에 method 속성 추가
		    frm.submit(); // form 데이터 전송
		  }); // end click()
		}); // end document
	</script>
</body>
</html>