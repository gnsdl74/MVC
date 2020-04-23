package edu.web.login.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.web.member.service.MemberService;
import edu.web.member.service.MemberServiceImple;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService service;
       
	// MemberService를 생성하면 MemberDAO까지 사용가능.
    public LoginServlet() {
    	service = MemberServiceImple.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("LoginServlet doGet() 호출");
		String RequestURL = request.getParameter("url");
		System.out.println(RequestURL);
		HttpSession session = request.getSession();
		if(RequestURL == null || RequestURL.equals("")) {
			request.getRequestDispatcher("/WEB-INF/login/login-form.jsp").forward(request, response);
		} else {
			session.setAttribute("targetURL", RequestURL);
			request.getRequestDispatcher("/WEB-INF/login/login-form.jsp").forward(request, response);
		}
		
	} // end doGet() - jsp파일로 연결하는 역할

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("LoginServlet doPost() 호출");
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		if(service.loginCheck(userid, password)) { // form에서 받아온 값으로 loginCheck() 호출
			// 로그인 성공 -> 세션 속성 저장 -> 페이지 이동 : (세션으로 로그인 상태 유지)
			HttpSession session = request.getSession();
			session.setAttribute("userid", userid);
			System.out.println("Login userid session : " + session.getAttribute("userid"));
			session.setMaxInactiveInterval(30);
			String url = (String) session.getAttribute("targetURL");
			System.out.println(url);
			if(url != null && !url.equals("")) { // 항상 오탈자 확인 !!
				response.sendRedirect(url);
			} else {
				response.sendRedirect("board-main.jsp");
			}
			
		} else {
			// 로그인 실패 -> 로그인 페이지(재로그인)
			response.sendRedirect("login");
		}
	} // end doPost() - 데이터를 전송하고 보내주는 역할

} // end LoginServlet
