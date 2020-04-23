package edu.web.board.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.web.board.domain.BoardVO;
import edu.web.board.util.PageCriteria;
import edu.web.dbcp.connmgr.ConnMgr;

public class BoardDAOImple implements BoardDAO, BoardQuery {
	private static BoardDAOImple instance = null;

	// Singleton 시작
	private BoardDAOImple() {}
	
	public static BoardDAOImple getInstance() {
		if (instance == null) {
			instance = new BoardDAOImple();
		}
		return instance;
	}
	// end Singleton
	
	// 게시글 등록
	@Override
	public int insert(BoardVO vo) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_INSERT);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getUserid());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt);
		}

		return result;
	} // end insert()

	// 전체 게시글 가져오기
	@Override
	public List<BoardVO> select() {
		List<BoardVO> list = new ArrayList<BoardVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_SELECT_ALL);

			rs = pstmt.executeQuery();

			int bno;
			String title;
			String content;
			String userid;
			String cdate;
			BoardVO vo = null;

			while (rs.next()) {
				bno = rs.getInt(COL_BNO);
				title = rs.getString(COL_TITLE);
				content = rs.getString(COL_CONTENT);
				userid = rs.getString(COL_USERID);
				cdate = rs.getString(COL_CDATE);
				vo = new BoardVO(bno, title, content, userid, cdate);
				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt, rs);
		}

		return list;
	} // end select() - 전체 게시글 가져오기
	
	// 글번호로 게시글 하나 가져오기
	@Override
	public BoardVO select(int bno) {
		BoardVO vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_SELECT);
			pstmt.setInt(1, bno);

			rs = pstmt.executeQuery();

			String title;
			String content;
			String userid;
			String cdate;
			if (rs.next()) {
				title = rs.getString(COL_TITLE);
				content = rs.getString(COL_CONTENT);
				userid = rs.getString(COL_USERID);
				cdate = rs.getString(COL_CDATE);
				vo = new BoardVO(bno, title, content, userid, cdate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt, rs);
		}

		return vo;
	} // end select() - 게시글번호로 검색해서 가져오기
	
	// 게시글 내용 수정
	@Override
	public int update(BoardVO vo) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_UPDATE);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getBno());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt);
		}
		
		return result;
	} // end update()

	// 게시글 삭제
	@Override
	public int delete(int bno) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_DELETE);
			pstmt.setInt(1, bno);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt);
		}
		
		return result;
	} // end delete()

	// 한 페이지 내에 10개씩 게시글을 가져오도록 지정
	@Override
	public List<BoardVO> select(PageCriteria c) {
		System.out.println("select() 호출 - 전체 게시글 나눠 가져오기");
		List<BoardVO> list = new ArrayList<BoardVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 보여지는 게시글의 시작 일련번호(rn), 끝 일련번호(rn)을 console에 출력
		System.out.println("게시글 시작 일련번호 : " + c.getStart());
		System.out.println("게시글 끝 일련번호 : " + c.getEnd());
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_SELECT_PAGESCOPE);
			pstmt.setInt(1, c.getStart()); // 보여지는 게시글의 시작 일련번호(rn)
			pstmt.setInt(2, c.getEnd()); // 보여지는 게시글의 끝 일련번호(rn)

			rs = pstmt.executeQuery();

			// cnt는 반복문 실행 횟수
			int cnt = 0;
			int bno;
			String title;
			String content;
			String userid;
			String cdate;
			BoardVO vo = null;

			while (rs.next()) {
				// 반복문 실행 횟수 및 각 실행 횟수에 bno가 몇번인지 출력
				cnt++;
				bno = rs.getInt(COL_BNO);
				System.out.println("게시글 번호(bno) : " + bno);
				title = rs.getString(COL_TITLE);
				content = rs.getString(COL_CONTENT);
				userid = rs.getString(COL_USERID);
				cdate = rs.getString(COL_CDATE);
				vo = new BoardVO(bno, title, content, userid, cdate);
				list.add(vo);
			}
			System.out.println("반복문 실행 횟수 : " + cnt);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt, rs);
		}

		return list;
	} // end select(PageCriteria c)
	
	// 전체 게시글의 갯수를 가져옴
	@Override
	public int getTotalNums() {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_TOTAL_CNT);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cnt = rs.getInt("total_cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt, rs);
		}
		
		return cnt;
	} // end getTotalNums()

} // end BoardDAOImple
