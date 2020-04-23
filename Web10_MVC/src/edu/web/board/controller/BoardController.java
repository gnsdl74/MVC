package edu.web.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.web.board.domain.BoardVO;
import edu.web.board.persistance.BoardDAO;
import edu.web.board.persistance.BoardDAOImple;
import edu.web.board.util.PageCriteria;
import edu.web.board.util.PageMaker;

@WebServlet("*.do") // *.do : ~.do로 선언된 HTTP 호출에 대해 반응
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String BOARD_MAIN = "board-main";
	private static final String BOARD_LIST = "board-list";
	private static final String BOARD_REGISTER = "board-register";
	private static final String BOARD_DETAIL = "board-detail";
	private static final String BOARD_UPDATE = "board-update";
	private static final String BOARD_DELETE = "board-delete";
	private static final String BOARD_URL = "WEB-INF/board/";
	private static final String EXTENSION = ".jsp";

	private static BoardDAO dao;

	public BoardController() {
		dao = BoardDAOImple.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		controlURI(request, response);
	} // end doGet()
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	} // end doPost()
	
	private void controlURI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		System.out.println("호출 경로 : " + requestURI);

		if (requestURI.contains(BOARD_LIST + ".do")) {
			System.out.println("list 호출 확인");
			boardList(request, response);
			
		} else if (requestURI.contains(BOARD_DETAIL + ".do")) {
			System.out.println("detail 호출 확인");
			boardDetail(request, response);
			
		} else if (requestURI.contains(BOARD_REGISTER + ".do")) {
			System.out.println("register 호출 확인");
			String method = request.getMethod(); // get, post방식을 구별하기 위해 정보 가져오기
			System.out.println(method);
			if (method.equals("GET")) {
				HttpSession session = request.getSession();
				String userid = (String) session.getAttribute("userid");
				
				if(userid != null && !userid.equals("")) { // userid 세션이 존재
					// board-register.jsp로 이동
					// response.sendRedirect(path) : 브라우저에 URL을 입력하고 접근하는 것과 동일, WEB-INF 하위 파일에 접근 불가
					// RequestDispatcher로 WEB-INF 하위 파일에 접근 가능
					RequestDispatcher dispatcher = request.getRequestDispatcher(BOARD_URL + BOARD_REGISTER + EXTENSION);
					dispatcher.forward(request, response);
				} else { // userid 세션이 존재하지 않으면
					System.out.println("Board userid session : " + userid);
					session.setAttribute("targetURL", "board-register.do"); // 목표 url 설정 : login하면 targetURL로 이동
					response.sendRedirect("login");
				}
				
			} else { // POST 방식으로 오면
				// 데이터를 DB에 저장
				boardRegister(request, response);
			}
			
		} else if (requestURI.contains(BOARD_UPDATE + ".do")) {
			System.out.println("update 호출 확인");
			String method = request.getMethod(); // get, post방식을 구별하기 위해 정보 가져오기
			System.out.println(method);
			if(method.equals("GET")) { // board-update.jsp 페이지로 이동
				boardUpdatePage(request, response);
			} else { // 수정된 데이터를 DB에 저장
				boardUpdate(request, response);
			}
			
		} else if (requestURI.contains(BOARD_DELETE + ".do")) {
			System.out.println("delete 호출 확인");
			boardDelete(request, response);
			
		}
	} // end controlURI()

	// 게시판 전체 내용(List) DB에서 가져오고, 해당 데이터를 board-list.jsp 페이지로 보내기
	// 1차 수정 >> 전체 게시물을 가져오는 것이 아닌 10개씩 끊어 가져옴
	private void boardList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		List<BoardVO> boardList = dao.select();
		
		// 페이지 번호와 게시글 수를 결정하는 클래스 PageCriteria
		PageCriteria c = new PageCriteria();
		// request에서 name이 "page"인 값을 저장
		String page = request.getParameter("page");
		System.out.println(page);
		
		// 처음 main에서 시작하면 page의 값은 null -> 첫번째 페이지를 보여줌
		// 다른 페이지를 선택하면 해당 페이지의 번호를 c에 저장
		if(page != null) {
			c.setPage(Integer.parseInt(page)); // 페이지 번호
		}
		
		// 해당 페이지에 게시물의 갯수를 제한하여 DB에서 가져옴
		List<BoardVO> boardList = dao.select(c);

		RequestDispatcher dispatcher = request.getRequestDispatcher(BOARD_URL + BOARD_LIST + EXTENSION);
		
		// 페이지 링크 번호에 대한 정보를 구성하여
		// board-list.jsp 페이지에 전송
		System.out.println("페이지 링크 번호 구성");
		
		// 페이징 처리를 담당하는 클래스 PageMaker
		PageMaker m = new PageMaker();
		m.setCriteria(c); // 시작 페이지 및 한 페이지당 게시글 정보 저장
		int totalCount = dao.getTotalNums(); // 전체 게시글 수 가져오기
		m.setTotalCount(totalCount); // 전체 게시글 수 저장
		m.setPageData(); // 저장된 데이터를 바탕으로 page 데이터(링크 번호 등) 생성
		request.setAttribute("pageMaker", m); // 페이지 링크를 board-list.jsp에 attribute 전송
		
		System.out.println("전체 게시글 수 : " + m.getTotalCount());
		System.out.println("한 페이지 당 게시글 수 : " + c.getNumsPerPage());
		System.out.println("페이지 번호 링크의 개수 : " + m.getNumsOfPageLinks());
		System.out.println("시작 페이지 링크 번호 : " + m.getStartPageNo());
		System.out.println("끝 페이지 링크 번호 : " + m.getEndPageNo());
		
		request.setAttribute("boardList", boardList); // 한페이지의 게시글을 board-list.jsp에 attribute 전송
		dispatcher.forward(request, response);
	} // end boardList()

	// bno 번호에 맞는 게시물 데이터 DB에서 가져오고, board-detail.jsp에 보내기
	private void boardDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int bno = Integer.parseInt(request.getParameter("bno"));
		System.out.println(bno);
		BoardVO vo = dao.select(bno);

		RequestDispatcher dispatcher = request.getRequestDispatcher(BOARD_URL + BOARD_DETAIL + EXTENSION);
		request.setAttribute("boardVO", vo);
		dispatcher.forward(request, response);
	} // end boardDetail()

	// 게시판 새 글 데이터 DB 등록 후, board-main.jsp 페이지로 이동
	private void boardRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String userid = request.getParameter("userid");
		BoardVO vo = new BoardVO(0, title, content, userid, "");
		int result = dao.insert(vo);

		if (result == 1) {
			PrintWriter out = response.getWriter();
			out.print("<head>" + "<meta charset='UTF-8'>" + "</head>");
			out.print("<script>alert('게시글 등록 성공')</script>");
			out.print("<script>location.href='" + BOARD_MAIN + EXTENSION + "'</script>");
		} else {
			PrintWriter out = response.getWriter();
			out.print("<head>" + "<meta charset='UTF-8'>" + "</head>");
			out.print("<script>alert('게시글 등록 실패<br>새로고침 후 다시 시도하세요.')</script>");
		}
	} // end boardRegister()
	
	// bno 번호를 바탕으로 특정 게시물 내용을 board-update.jsp로 보내기
	private void boardUpdatePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bno = Integer.parseInt(request.getParameter("bno"));
		BoardVO vo = dao.select(bno);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(BOARD_URL + BOARD_UPDATE + EXTENSION);
		request.setAttribute("boardVO", vo);
		dispatcher.forward(request, response);
	} // end boardUpdatePage()
	
	// bno 번호에 맞는 특정 게시물 수정 내용을 DB 저장하고 board-main.jsp로 이동
	private void boardUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int bno = Integer.parseInt(request.getParameter("bno"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		BoardVO vo = new BoardVO(bno, title, content, "", "");
		int result = dao.update(vo);
		
		if (result == 1) {
			PrintWriter out = response.getWriter();
			out.print("<head>" + "<meta charset='UTF-8'>" + "</head>");
			out.print("<script>alert('게시글 수정 성공')</script>");
			out.print("<script>location.href='" + BOARD_MAIN + EXTENSION + "'</script>");
		} else {
			PrintWriter out = response.getWriter();
			out.print("<head>" + "<meta charset='UTF-8'>" + "</head>");
			out.print("<script>alert('게시글 수정 실패<br>새로고침 후 다시 시도하세요.')</script>");
		}
		
	} // end boardUpdate()
	
	// bno 번호를 바탕으로 특정 게시물 DB에서 삭제하고 board-main.jsp로 이동
	private void boardDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String strBno = request.getParameter("bno");
		if(strBno == null) {
			PrintWriter out = response.getWriter();
			out.print("<head>" + "<meta charset='UTF-8'>" + "</head>");
			out.print("<script>alert('삭제된 게시글입니다.')</script>");
			out.print("<script>location.href='" + BOARD_MAIN + EXTENSION + "'</script>");
		}
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		int result = dao.delete(bno);
		
		if (result == 1) {
			PrintWriter out = response.getWriter();
			out.print("<head>" + "<meta charset='UTF-8'>" + "</head>");
			out.print("<script>alert('게시글 삭제 성공')</script>");
			out.print("<script>location.href='" + BOARD_MAIN + EXTENSION + "'</script>");
		} else {
			PrintWriter out = response.getWriter();
			out.print("<head>" + "<meta charset='UTF-8'>" + "</head>");
			out.print("<script>alert('게시글 삭제 실패<br>새로고침 후 다시 시도하세요.')</script>");
		}
	} // end boardDelete()
	
} // end BoardController