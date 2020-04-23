package edu.web.board.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.web.board.domain.ReplyVO;
import edu.web.dbcp.connmgr.ConnMgr;

public class ReplyDAOImple implements ReplyDAO, ReplyQuery {
	// Singleton
	private static ReplyDAOImple instance = null;
	
	private ReplyDAOImple() {}
	
	public static ReplyDAOImple getInstance() {
		if(instance == null) {
			instance = new ReplyDAOImple();
		}
		return instance;
	}
	// end Singleton
	
	@Override
	public int insert(ReplyVO vo) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		conn = ConnMgr.getConnection();
		try {
			pstmt  = conn.prepareStatement(SQL_REPLY_INSERT);
			pstmt.setInt(1, vo.getBno());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getReplyid());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt);
		}
		
		return result;
	} // end insert()

	@Override
	public List<ReplyVO> select(int bno) {
		List<ReplyVO> list = new ArrayList<ReplyVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		conn = ConnMgr.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL_REPLY_SELECT);
			pstmt.setInt(1, bno);
			
			rs = pstmt.executeQuery();
			ReplyVO vo = null;
			int rno;
			String content;
			String replyid;
			String cdate;
			while(rs.next()) {
				rno = rs.getInt(COL_RNO);
				content = rs.getString(COL_CONTENT);
				replyid = rs.getString(COL_REPLYID);
				cdate = rs.getString(COL_CDATE);
				vo = new ReplyVO(rno, bno, content, replyid, cdate);
				list.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt, rs);
		}
		
		return list;
	} // end select()

	@Override
	public int update(ReplyVO vo) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		conn = ConnMgr.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL_REPLY_UPDATE);
			pstmt.setString(1, vo.getContent());
			pstmt.setInt(2, vo.getRno());
			pstmt.setInt(3, vo.getBno());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt);
		}
		
		return result;
	} // end update

	@Override
	public int delete(int rno, int bno) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		conn = ConnMgr.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL_REPLY_DELETE);
			pstmt.setInt(1, rno);
			pstmt.setInt(2, bno);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt);
		}
		
		return result;
	} // end delete

} // end ReplyDAOImple
