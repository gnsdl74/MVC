package edu.web.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.web.board.domain.ReplyVO;
import edu.web.board.persistance.ReplyDAO;
import edu.web.board.persistance.ReplyDAOImple;

@WebServlet("/replies/*")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ReplyDAO dao;

	public ReplyController() {
		dao = ReplyDAOImple.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ReplyController doGet() 호출");
		controlURI(request, response);
	} // end doGet()

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ReplyController doPost() 호출");
		doGet(request, response);
	} // end doPost()

	private void controlURI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI(); // 호출된 URI 경로
		System.out.println(requestURI);

		if (requestURI.contains("add")) {
			System.out.println("add 호출 확인");
			replyAdd(request, response);

		} else if (requestURI.contains("all")) {
			System.out.println("all 호출 확인");
			replyList(request, response);
		} else if (requestURI.contains("update")) {
			System.out.println("update 호출 확인");
			replyUpdate(request, response);
		} else if(requestURI.contains("delete")) {
			System.out.println("delete 호출 확인");
			replyDelete(request, response);
		}

	} // end controlURI()

	private void replyAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bno = Integer.parseInt(request.getParameter("bno"));
		String content = request.getParameter("content");
		String replyid = request.getParameter("replyid");
		ReplyVO vo = new ReplyVO(0, bno, content, replyid, "");
		System.out.println(vo);

		int result = dao.insert(vo);
		System.out.println("insert 결과 : " + result);
		if (result == 1) {
			response.getWriter().append("success");
		}
	} // end replyAdd()

	@SuppressWarnings("unchecked")
	private void replyList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bno = Integer.parseInt(request.getParameter("bno"));
		List<ReplyVO> list = dao.select(bno);

		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("rno", list.get(i).getRno());
			jsonObj.put("bno", list.get(i).getBno());
			jsonObj.put("content", list.get(i).getContent());
			jsonObj.put("replyid", list.get(i).getReplyid());
			jsonObj.put("cdate", list.get(i).getCdate());
			jsonArray.add(jsonObj);
		}
		System.out.println(jsonArray.toString());
		response.getWriter().append(jsonArray.toString());
	} // end replyAll()

	private void replyUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int rno = Integer.parseInt(request.getParameter("rno"));
		int bno = Integer.parseInt(request.getParameter("bno"));
		String content = request.getParameter("content");
		ReplyVO vo = new ReplyVO(rno, bno, content, "", "");
		System.out.println(vo);
		
		int result = dao.update(vo);
		System.out.println(result);
		
		if(result == 1) {
			response.getWriter().append("success");
		}
		
		
	} // end replyUpdate

	private void replyDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int rno = Integer.parseInt(request.getParameter("rno"));
		int bno = Integer.parseInt(request.getParameter("bno"));
		System.out.println("댓글 번호 : " + rno + ", 게시글 번호 : " + bno);
		
		int result = dao.delete(rno, bno);
		System.out.println(result);
		
		if(result == 1) { // 삭제가 정상적으로 실행되고 
			response.getWriter().append("success"); // ajax에 응답(success) 전달
		}
		
	} // end replyDelete()
	
} // end ReplyController
