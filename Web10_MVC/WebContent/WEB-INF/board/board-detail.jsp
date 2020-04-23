<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${boardVO.title }</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<body>
	<h2>글 보기</h2>
	<div>
		<p>글 번호 : ${boardVO.bno }</p>
	</div>
	<div>
		<p>
			제목 : <input type="text" value="${boardVO.title }" readonly="readonly" />
		</p>
	</div>
	<div>
		<p>작성자 : ${boardVO.userid }</p>
		<p>작성일 : ${boardVO.cdate }</p>
	</div>
	<div>
		<p>
			내용<br>
			<textarea rows="20" cols="120" readonly="readonly">${boardVO.content }</textarea>
	</div>
	<div>
		<a href="board-update.do?bno=${boardVO.bno }"><input type="button" value="글수정" /></a>
		<a href="board-delete.do?bno=${boardVO.bno }"><input type="button" value="글삭제" /></a>
	</div>

	<hr>
	<div style="text-align: center;">
		<div>
			<input type="text" id="bno" value="${boardVO.bno }"	style="display: none;">
			<input type="text" id="replyid"	value="${userid }" style="display: none;">
			<input type="text" id="content"	placeholder="댓글 입력">
			<button type="button" id="btn_add">작성</button>
		</div>
	</div>

	<hr>
	<div style="text-align: center;">
		<div id="replies"></div>
	</div>

	<script type="text/javascript">
    $(document).ready(function() {
      var bno = $("#bno").val(); // 게시판 번호 값
      
      $("#content").click(function() {
        var replyid = $("#replyid").val(); // 댓글 아이디 값
        if(replyid == null || replyid == "") { // userid세션이 없을때
          var check = confirm("로그인이 필요한 서비스입니다.\n로그인 페이지로 이동하시겠습니까?");
          if(check == true) {
            location.href="login?url=board-detail.do?bno=" + bno;
         	 }
    	  }
      });
      
      // 댓글 입력 기능
      $("#btn_add").click(function() {
        // 댓글 아이디, 댓글 내용의 값을 가져와서
        // get 방식으로 전송
        // url : "localhost:8080/Web10_MVC/replies/add"
        // data : 게시판 번호, 댓글 내용, 댓글 아이디 

        // **content와 replyid는 null값을 처리해야함** 
        var content = $("#content").val(); // 댓글 내용 값
        var replyid = $("#replyid").val(); // 댓글 아이디 값
        console.log(replyid);  
        if(replyid == null || replyid == "") { // userid세션이 없을때
          var check = confirm("로그인이 필요한 서비스입니다.\n로그인 페이지로 이동하시겠습니까?");
          if(check == true) {
            location.href="login?url=board-detail.do?bno=" + bno;
          }
        } else { // userid세션이 있을때
          $("#content").off("click");
          if(content == null || content == "") { // 댓글 내용이 없을때
            alert("내용을 입력하세요.");
          
          } else { // 댓글 내용이 있을때
          var obj = {
              "bno" : bno,
              "content" : content,
              "replyid" : replyid
            }; // 보낼 데이터를 JSON 형식으로 저장?
                
         // $.ajax로 송신
            $.ajax({
              type : "get",
              url : "replies/add",
              data : obj,
              success : function(result) {
                console.log(result);
                if (result == "success") {
                  alert("댓글 입력 성공");
                  $("#content").val("");
                  getAllReplies(); // 메소드 호출
                }
              }
            }); // end ajax()
          }
        }
      }); // end btn_add click()

      getAllReplies(); // 메소드 호출

      // 게시판의 댓글 전체 가져오기 (board-detail로 접근했을때 실행)
      function getAllReplies() {
        var url = "replies/all?bno=" + bno;
        $.getJSON(url, function(jsonData) {
          console.log(jsonData);
          var list = ""; // JSON 데이터에서 데이터를 꺼내 태그 + 데이터 형식으로 저장할 변수
          $(jsonData).each( // jsonData를 사용하는 each 반복문
          function() {
            var date = new Date(this.cdate); // this : jsonData
            console.log("댓글 번호 : " + this.rno);
            list += '<div class="reply_item">' 
            	+ '<pre>'
            	+ '<input type="hidden" id="rno" value="' + this.rno + '"/><br>'
            	+ '<input type="hidden" id="userid_item" value="' + this.replyid + '"/><br>'
            	+ this.replyid
            	+ '&nbsp;&nbsp;'
            	+ '<input type="text" id="reply_content" value="' + this.content + '"/>'
            	+ '&nbsp;&nbsp;'
            	+ this.cdate
            	+ '&nbsp;&nbsp;'
            	+ '<button class="btn_update" type="button">수정</button>'
            	+ '&nbsp'
            	+ '<button class="btn_delete" type="button">삭제</button>'
            	+ '</pre>' + '</div>';
          }); // end each()
          $("#replies").html(list);
        } // end callback()
        ); // end getJSON
      } // end getAllReplies()
	
      // 수정 버튼 클릭하면 선택된 댓글 수정 [.on()은 이벤트처리 메소드]
      $('#replies').on('click', '.reply_item .btn_update', function() {
        // 해당 버튼의 rno, content 값을 읽음
        // prevAll() : this 형제요소 중 이전에 있는 전체 형제요소를 선택 
        var rno = $(this).prevAll('#rno').val(); // 이전의 전체 형제요소 중 id가 rno인 요소를 선택
        var content = $(this).prevAll('#reply_content').val(); // 이전의 전체 형제요소 중 id가 reply_content인 요소를 선택
        console.log("선택된 댓글 번호 : " + rno + ", 댓글 내용 : " + content);
        
        // ajax 요청(update)
        $.ajax({
          type : 'get',
          url : 'replies/update?rno=' + rno,
          data : {
            'bno' : bno,
            'content' : content
          },
          success : function(result) {
            if (result == 'success') { // 댓글 수정에 성공하면
              alert('댓글 수정 성공');
              getAllReplies(); // 다시 전체 댓글 정보를 가져오기
            }
          }
        }); // end ajax
      }); // end btn_update.click()
      
      // 삭제버튼을 클릭하면 선택한 댓글 삭제
      $('#replies').on('click', '.reply_item .btn_delete', function() {
        // 해당 버튼의 rno, content 값을 읽음
        // prevAll() : this 형제요소 중 이전에 있는 전체 형제요소를 선택 
        var rno = $(this).prevAll('#rno').val(); // 이전의 전체 형제요소 중 id가 rno인 요소를 선택
        console.log("선택된 댓글 번호 : " + rno);
        
        // ajax 요청 - ajax의 $.get(url, 추가 데이터, callback) 메소드 사용 (delete)
        $.get(
          'replies/delete?rno=' + rno,
          {'bno' : bno},
          function(result) {
            if (result == 'success') { // 댓글 삭제에 성공하면 (그러나, controller에서 ajax에 대한 응답[response]가 없으면 실행되지 않는다.)
              alert('댓글 삭제 성공');
              getAllReplies(); // 다시 전체 댓글 정보를 가져오기
            }
          }
        ); // end ajax
      }); // end btn_delete.click()
      
    }); // end document
  </script>
</body>
</html>